package com.pickaid.mnajs.kubejs.compat;

import com.mna.Registries;
import com.mna.api.faction.FactionIDs;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public final class MnaPiKubeJSCompatIdCandidates {
    private MnaPiKubeJSCompatIdCandidates() {
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
