package com.pickaid.mnajs.kubejs.compat;

import com.mna.Registries;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public final class MnaPiKubeJSCompatIdCandidates {
    private MnaPiKubeJSCompatIdCandidates() {
    }

    public static List<String> spellEffectIds() {
        return registryIds(Registries.SpellEffect);
    }

    public static List<String> soundIds() {
        return registryIds(() -> ForgeRegistries.SOUND_EVENTS);
    }

    private static List<String> registryIds(Supplier<? extends IForgeRegistry<?>> registrySupplier) {
        try {
            IForgeRegistry<?> registry = registrySupplier.get();
            return resourceLocationIds(registry == null ? List.of() : registry.getKeys());
        } catch (RuntimeException exception) {
            return List.of();
        }
    }

    private static List<String> resourceLocationIds(Collection<ResourceLocation> values) {
        if (values == null || values.isEmpty()) {
            return List.of();
        }
        return values.stream()
                .filter(java.util.Objects::nonNull)
                .map(ResourceLocation::toString)
                .sorted()
                .distinct()
                .toList();
    }
}
