package com.pickaid.mnajs.kubejs.probe;

import com.mna.Registries;
import com.mna.api.cantrips.ICantrip;
import com.mna.api.faction.FactionIDs;
import com.mna.cantrips.CantripRegistry;
import com.pickaid.mnajs.kubejs.id.MnaCantripId;
import com.pickaid.mnajs.kubejs.id.MnaBlockId;
import com.pickaid.mnajs.kubejs.id.MnaFactionId;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.id.MnaLootTableId;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.kubejs.id.MnaModifierId;
import com.pickaid.mnajs.kubejs.id.MnaRitualEffectId;
import com.pickaid.mnajs.kubejs.id.MnaRitualId;
import com.pickaid.mnajs.kubejs.id.MnaShapeId;
import com.pickaid.mnajs.kubejs.id.MnaSpellEffectId;
import com.pickaid.mnajs.kubejs.texture.MnaTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

final class MnaJSLegacyProbeIdAliases {
    private static final List<IdAlias> ID_ALIASES = List.of(
            new IdAlias("MnaFactionId", MnaFactionId.class, MnaJSLegacyProbeIdAliases::factionIds),
            new IdAlias("MnaRitualEffectId", MnaRitualEffectId.class, () -> registryIds(Registries.RitualEffect)),
            new IdAlias("MnaSpellEffectId", MnaSpellEffectId.class, () -> registryIds(Registries.SpellEffect)),
            new IdAlias("MnaShapeId", MnaShapeId.class, () -> registryIds(Registries.Shape)),
            new IdAlias("MnaModifierId", MnaModifierId.class, () -> registryIds(Registries.Modifier)),
            new IdAlias("MnaRitualId", MnaRitualId.class, () -> recipeIds("rituals")),
            new IdAlias("MnaManaweavePatternId", MnaManaweavePatternId.class, () -> recipeIds("manaweave_patterns")),
            new IdAlias("MnaCantripId", MnaCantripId.class, MnaJSLegacyProbeIdAliases::cantripIds),
            IdAlias.rawType("MnaItemId", MnaItemId.class, "Item"),
            IdAlias.rawType("MnaBlockId", MnaBlockId.class, "Block"),
            IdAlias.rawType("MnaItemOrTag", MnaItemOrTag.class, "Item | ItemTag"),
            new IdAlias("MnaLootTableId", MnaLootTableId.class, MnaJSLegacyProbeIdAliases::lootTableIds),
            new IdAlias("MnaTexture", MnaTexture.class, MnaJSLegacyProbeIdAliases::textureIds)
    );

    private MnaJSLegacyProbeIdAliases() {
    }

    static List<IdAlias> all() {
        return ID_ALIASES;
    }

    private static List<String> factionIds() {
        return resourceLocationIds(() -> {
            LinkedHashSet<ResourceLocation> ids = new LinkedHashSet<>(rawRegistryIds(Registries.Factions));
            ids.add(FactionIDs.COUNCIL);
            ids.add(FactionIDs.DEMONS);
            ids.add(FactionIDs.FEY);
            ids.add(FactionIDs.UNDEAD);
            return ids;
        });
    }

    private static List<String> cantripIds() {
        return resourceLocationIds(() -> CantripRegistry.INSTANCE.getCantrips().stream()
                .map(ICantrip::getId)
                .toList());
    }

    private static List<String> registryIds(Supplier<? extends IForgeRegistry<?>> registrySupplier) {
        return resourceLocationIds(() -> rawRegistryIds(registrySupplier));
    }

    private static Collection<ResourceLocation> rawRegistryIds(Supplier<? extends IForgeRegistry<?>> registrySupplier) {
        try {
            IForgeRegistry<?> registry = registrySupplier.get();
            return registry == null ? List.of() : registry.getKeys();
        } catch (RuntimeException exception) {
            return List.of();
        }
    }

    private static List<String> recipeIds(String folder) {
        return resourceLocationIds(() -> {
            LinkedHashSet<ResourceLocation> ids = new LinkedHashSet<>();
            ids.addAll(scanRecipeFolderFromCodeSource(Registries.class, folder));
            ids.addAll(scanRecipeFolderFromProject(folder));
            return ids;
        });
    }

    private static List<String> lootTableIds() {
        return resourceLocationIds(() -> {
            LinkedHashSet<ResourceLocation> ids = new LinkedHashSet<>();
            ids.addAll(scanLootTableFolderFromCodeSource(Registries.class));
            ids.addAll(scanLootTableFolderFromProject());
            return ids;
        });
    }

    private static List<String> textureIds() {
        return resourceLocationIds(() -> {
            LinkedHashSet<ResourceLocation> ids = new LinkedHashSet<>();
            ids.addAll(scanTextureFolderFromCodeSource(Registries.class));
            ids.addAll(scanTextureFolderFromProject());
            return ids;
        });
    }

    private static Collection<ResourceLocation> scanRecipeFolderFromCodeSource(Class<?> anchor, String folder) {
        try {
            Path codeSourcePath = Paths.get(anchor.getProtectionDomain().getCodeSource().getLocation().toURI());
            if (Files.isDirectory(codeSourcePath)) {
                return scanRecipeFolderInDirectory(codeSourcePath, folder);
            }
            if (Files.isRegularFile(codeSourcePath)) {
                return scanRecipeFolderInJar(codeSourcePath, folder);
            }
        } catch (RuntimeException | URISyntaxException ignored) {
        }
        return List.of();
    }

    private static Collection<ResourceLocation> scanRecipeFolderFromProject(String folder) {
        Path root = findProjectRoot();
        if (root == null) {
            return List.of();
        }

        LinkedHashSet<ResourceLocation> ids = new LinkedHashSet<>();
        ids.addAll(scanRecipeFolderInDirectory(root.resolve("src/main/resources"), folder));
        ids.addAll(scanRecipeFolderInDirectory(root.resolve("src/generated/resources"), folder));
        ids.addAll(scanRecipeFolderInDirectory(root.resolve("build/resources/main"), folder));
        return ids;
    }

    private static Collection<ResourceLocation> scanLootTableFolderFromCodeSource(Class<?> anchor) {
        try {
            Path codeSourcePath = Paths.get(anchor.getProtectionDomain().getCodeSource().getLocation().toURI());
            if (Files.isDirectory(codeSourcePath)) {
                return scanLootTableFolderInDirectory(codeSourcePath);
            }
            if (Files.isRegularFile(codeSourcePath)) {
                return scanLootTableFolderInJar(codeSourcePath);
            }
        } catch (RuntimeException | URISyntaxException ignored) {
        }
        return List.of();
    }

    private static Collection<ResourceLocation> scanLootTableFolderFromProject() {
        Path root = findProjectRoot();
        if (root == null) {
            return List.of();
        }

        LinkedHashSet<ResourceLocation> ids = new LinkedHashSet<>();
        ids.addAll(scanLootTableFolderInDirectory(root.resolve("src/main/resources")));
        ids.addAll(scanLootTableFolderInDirectory(root.resolve("src/generated/resources")));
        ids.addAll(scanLootTableFolderInDirectory(root.resolve("build/resources/main")));
        return ids;
    }

    private static Collection<ResourceLocation> scanTextureFolderFromCodeSource(Class<?> anchor) {
        try {
            Path codeSourcePath = Paths.get(anchor.getProtectionDomain().getCodeSource().getLocation().toURI());
            if (Files.isDirectory(codeSourcePath)) {
                return scanTextureFolderInDirectory(codeSourcePath);
            }
            if (Files.isRegularFile(codeSourcePath)) {
                return scanTextureFolderInJar(codeSourcePath);
            }
        } catch (RuntimeException | URISyntaxException ignored) {
        }
        return List.of();
    }

    private static Collection<ResourceLocation> scanTextureFolderFromProject() {
        Path root = findProjectRoot();
        if (root == null) {
            return List.of();
        }

        LinkedHashSet<ResourceLocation> ids = new LinkedHashSet<>();
        ids.addAll(scanTextureFolderInDirectory(root.resolve("src/main/resources")));
        ids.addAll(scanTextureFolderInDirectory(root.resolve("src/generated/resources")));
        ids.addAll(scanTextureFolderInDirectory(root.resolve("build/resources/main")));
        return ids;
    }

    private static Path findProjectRoot() {
        Path current = Paths.get("").toAbsolutePath().normalize();
        for (Path cursor = current; cursor != null; cursor = cursor.getParent()) {
            if (Files.exists(cursor.resolve("build.gradle")) || Files.exists(cursor.resolve("settings.gradle"))) {
                return cursor;
            }
        }
        return null;
    }

    private static Collection<ResourceLocation> scanRecipeFolderInDirectory(Path root, String folder) {
        if (root == null || !Files.exists(root)) {
            return List.of();
        }

        Path dataRoot = root.resolve("data");
        if (!Files.exists(dataRoot)) {
            return List.of();
        }

        LinkedHashSet<ResourceLocation> ids = new LinkedHashSet<>();
        try (var stream = Files.walk(dataRoot)) {
            stream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(path -> addIfRecipePath(ids, dataRoot.relativize(path), folder));
        } catch (IOException ignored) {
        }
        return ids;
    }

    private static Collection<ResourceLocation> scanRecipeFolderInJar(Path jarPath, String folder) {
        LinkedHashSet<ResourceLocation> ids = new LinkedHashSet<>();
        try (ZipFile zip = new ZipFile(jarPath.toFile())) {
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().endsWith(".json")) {
                    addIfRecipePath(ids, Paths.get(entry.getName()), folder);
                }
            }
        } catch (IOException ignored) {
        }
        return ids;
    }

    private static Collection<ResourceLocation> scanLootTableFolderInDirectory(Path root) {
        if (root == null || !Files.exists(root)) {
            return List.of();
        }

        Path dataRoot = root.resolve("data");
        if (!Files.exists(dataRoot)) {
            return List.of();
        }

        LinkedHashSet<ResourceLocation> ids = new LinkedHashSet<>();
        try (var stream = Files.walk(dataRoot)) {
            stream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"))
                    .forEach(path -> addIfLootTablePath(ids, dataRoot.relativize(path)));
        } catch (IOException ignored) {
        }
        return ids;
    }

    private static Collection<ResourceLocation> scanLootTableFolderInJar(Path jarPath) {
        LinkedHashSet<ResourceLocation> ids = new LinkedHashSet<>();
        try (ZipFile zip = new ZipFile(jarPath.toFile())) {
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().endsWith(".json")) {
                    addIfLootTablePath(ids, Paths.get(entry.getName()));
                }
            }
        } catch (IOException ignored) {
        }
        return ids;
    }

    private static Collection<ResourceLocation> scanTextureFolderInDirectory(Path root) {
        if (root == null || !Files.exists(root)) {
            return List.of();
        }

        Path assetsRoot = root.resolve("assets");
        if (!Files.exists(assetsRoot)) {
            return List.of();
        }

        LinkedHashSet<ResourceLocation> ids = new LinkedHashSet<>();
        try (var stream = Files.walk(assetsRoot)) {
            stream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".png"))
                    .forEach(path -> addIfTexturePath(ids, assetsRoot.relativize(path)));
        } catch (IOException ignored) {
        }
        return ids;
    }

    private static Collection<ResourceLocation> scanTextureFolderInJar(Path jarPath) {
        LinkedHashSet<ResourceLocation> ids = new LinkedHashSet<>();
        try (ZipFile zip = new ZipFile(jarPath.toFile())) {
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory() && entry.getName().endsWith(".png")) {
                    addIfTexturePath(ids, Paths.get(entry.getName()));
                }
            }
        } catch (IOException ignored) {
        }
        return ids;
    }

    private static void addIfRecipePath(Set<ResourceLocation> ids, Path relativePath, String folder) {
        int startIndex = "data".equals(relativePath.getName(0).toString()) ? 1 : 0;
        if (relativePath.getNameCount() < startIndex + 4) {
            return;
        }

        if (!"recipes".equals(relativePath.getName(startIndex + 1).toString())) {
            return;
        }

        if (!folder.equals(relativePath.getName(startIndex + 2).toString())) {
            return;
        }

        String namespace = relativePath.getName(startIndex).toString();
        StringBuilder recipePath = new StringBuilder(folder);
        for (int i = startIndex + 3; i < relativePath.getNameCount(); i++) {
            String segment = relativePath.getName(i).toString();
            if (i == relativePath.getNameCount() - 1) {
                segment = segment.substring(0, segment.length() - 5);
            }
            recipePath.append('/').append(segment);
        }
        ids.add(ResourceLocation.fromNamespaceAndPath(namespace, recipePath.toString()));
    }

    private static void addIfLootTablePath(Set<ResourceLocation> ids, Path relativePath) {
        int startIndex = "data".equals(relativePath.getName(0).toString()) ? 1 : 0;
        if (relativePath.getNameCount() < startIndex + 3) {
            return;
        }

        if (!"loot_tables".equals(relativePath.getName(startIndex + 1).toString())) {
            return;
        }

        String namespace = relativePath.getName(startIndex).toString();
        StringBuilder lootTablePath = new StringBuilder();
        for (int i = startIndex + 2; i < relativePath.getNameCount(); i++) {
            String segment = relativePath.getName(i).toString();
            if (i == relativePath.getNameCount() - 1) {
                segment = segment.substring(0, segment.length() - 5);
            }
            if (lootTablePath.length() > 0) {
                lootTablePath.append('/');
            }
            lootTablePath.append(segment);
        }
        ids.add(ResourceLocation.fromNamespaceAndPath(namespace, lootTablePath.toString()));
    }

    private static void addIfTexturePath(Set<ResourceLocation> ids, Path relativePath) {
        int startIndex = "assets".equals(relativePath.getName(0).toString()) ? 1 : 0;
        if (relativePath.getNameCount() < startIndex + 3) {
            return;
        }

        if (!"textures".equals(relativePath.getName(startIndex + 1).toString())) {
            return;
        }

        String namespace = relativePath.getName(startIndex).toString();
        StringBuilder texturePath = new StringBuilder();
        for (int i = startIndex + 1; i < relativePath.getNameCount(); i++) {
            if (texturePath.length() > 0) {
                texturePath.append('/');
            }
            texturePath.append(relativePath.getName(i));
        }
        ids.add(ResourceLocation.fromNamespaceAndPath(namespace, texturePath.toString()));
    }

    private static List<String> resourceLocationIds(Supplier<? extends Collection<ResourceLocation>> supplier) {
        try {
            Collection<ResourceLocation> values = supplier.get();
            if (values == null || values.isEmpty()) {
                return List.of();
            }
            return values.stream()
                    .filter(java.util.Objects::nonNull)
                    .map((ResourceLocation id) -> id.toString())
                    .sorted()
                    .distinct()
                    .toList();
        } catch (RuntimeException exception) {
            return List.of();
        }
    }

    record IdAlias(String alias, Class<?> wrapperClass, Supplier<List<String>> ids, String specialTypeReference) {
        IdAlias(String alias, Class<?> wrapperClass, Supplier<List<String>> ids) {
            this(alias, wrapperClass, ids, null);
        }

        static IdAlias rawType(String alias, Class<?> wrapperClass, String specialTypeReference) {
            return new IdAlias(alias, wrapperClass, List::of, specialTypeReference);
        }
    }
}
