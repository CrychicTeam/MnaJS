package com.pickaid.mnajs.kubejs.compat;

import com.mna.Registries;
import com.mna.api.capabilities.resource.CastingResourceIDs;
import com.mna.api.events.ProgressionEventIDs;
import com.mna.api.faction.FactionIDs;
import com.mna.capabilities.playerdata.magic.resources.CastingResourceRegistry;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.server.ServerLifecycleHooks;

public final class MnaPiKubeJSCompatIdCandidates {
    private MnaPiKubeJSCompatIdCandidates() {
    }

    public static List<String> progressionEventIds() {
        return resourceLocationIds(() -> {
            LinkedHashSet<ResourceLocation> ids = new LinkedHashSet<>();
            for (Field field : ProgressionEventIDs.class.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers()) || field.getType() != ResourceLocation.class) {
                    continue;
                }

                try {
                    field.setAccessible(true);
                    Object value = field.get(null);
                    if (value instanceof ResourceLocation id) {
                        ids.add(id);
                    }
                } catch (ReflectiveOperationException ignored) {
                }
            }
            return ids;
        });
    }

    public static List<String> factionIds() {
        return resourceLocationIds(() -> {
            LinkedHashSet<ResourceLocation> ids = new LinkedHashSet<>(rawRegistryIds(Registries.Factions));
            ids.add(FactionIDs.COUNCIL);
            ids.add(FactionIDs.DEMONS);
            ids.add(FactionIDs.FEY);
            ids.add(FactionIDs.UNDEAD);
            return ids;
        });
    }

    public static List<String> castingResourceIds() {
        return resourceLocationIds(() -> {
            LinkedHashSet<ResourceLocation> ids = new LinkedHashSet<>();
            ids.add(CastingResourceIDs.MANA);
            ids.add(CastingResourceIDs.COUNCIL_MANA);
            ids.add(CastingResourceIDs.SOULS);
            ids.add(CastingResourceIDs.BRIMSTONE);
            ids.add(CastingResourceIDs.SUMMER_FIRE);
            ids.add(CastingResourceIDs.WINTER_ICE);

            try {
                Field field = CastingResourceRegistry.class.getDeclaredField("_registry");
                field.setAccessible(true);
                Object raw = field.get(CastingResourceRegistry.Instance);
                if (raw instanceof Map<?, ?> map) {
                    for (Object key : map.keySet()) {
                        if (key instanceof ResourceLocation id) {
                            ids.add(id);
                        }
                    }
                }
            } catch (ReflectiveOperationException ignored) {
            }

            return ids;
        });
    }

    public static List<String> mobEffectIds() {
        return registryIds(() -> ForgeRegistries.MOB_EFFECTS);
    }

    public static List<String> spellEffectIds() {
        return registryIds(Registries.SpellEffect);
    }

    public static List<String> ritualEffectIds() {
        return registryIds(Registries.RitualEffect);
    }

    public static List<String> shapeIds() {
        return registryIds(Registries.Shape);
    }

    public static List<String> modifierIds() {
        return registryIds(Registries.Modifier);
    }

    public static List<String> constructTaskIds() {
        return registryIds(Registries.ConstructTasks);
    }

    public static List<String> lootTableIds() {
        return resourceLocationIds(() -> {
            MinecraftServer server = currentServer();
            if (server == null) {
                return List.of();
            }
            return server.getLootData().getKeys(LootDataType.TABLE);
        });
    }

    public static List<String> structureIds() {
        return resourceLocationIds(() -> {
            MinecraftServer server = currentServer();
            if (server == null) {
                return List.of();
            }
            return server.getStructureManager().listTemplates().toList();
        });
    }

    public static List<String> textureIds() {
        return List.of();
    }

    public static List<String> soundIds() {
        return registryIds(() -> ForgeRegistries.SOUND_EVENTS);
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

    private static MinecraftServer currentServer() {
        try {
            return ServerLifecycleHooks.getCurrentServer();
        } catch (RuntimeException exception) {
            return null;
        }
    }

    private static List<String> resourceLocationIds(Supplier<? extends Collection<ResourceLocation>> supplier) {
        try {
            Collection<ResourceLocation> values = supplier.get();
            if (values == null || values.isEmpty()) {
                return List.of();
            }
            return values.stream()
                    .filter(java.util.Objects::nonNull)
                    .map(ResourceLocation::toString)
                    .sorted()
                    .distinct()
                    .toList();
        } catch (RuntimeException exception) {
            return List.of();
        }
    }
}
