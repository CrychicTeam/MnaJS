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
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.pickaid.pikubejscompat.api.id.PiCandidateSources;

public final class MnaPiKubeJSCompatIdCandidates {
    private MnaPiKubeJSCompatIdCandidates() {
    }

    public static List<String> progressionEventIds() {
        return PiCandidateSources.resourceLocationIds(() -> {
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
        }).get();
    }

    public static List<String> factionIds() {
        return PiCandidateSources.resourceLocationIds(() -> {
            LinkedHashSet<ResourceLocation> ids = new LinkedHashSet<>(rawRegistryIds(Registries.Factions));
            ids.add(FactionIDs.COUNCIL);
            ids.add(FactionIDs.DEMONS);
            ids.add(FactionIDs.FEY);
            ids.add(FactionIDs.UNDEAD);
            return ids;
        }).get();
    }

    public static List<String> castingResourceIds() {
        return PiCandidateSources.resourceLocationIds(() -> {
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
        }).get();
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
        return PiCandidateSources.lootTableIds().get();
    }

    public static List<String> structureIds() {
        return PiCandidateSources.structureTemplateIds().get();
    }

    public static List<String> textureIds() {
        return List.of();
    }

    public static List<String> soundIds() {
        return registryIds(() -> ForgeRegistries.SOUND_EVENTS);
    }

    private static List<String> registryIds(Supplier<? extends IForgeRegistry<?>> registrySupplier) {
        return PiCandidateSources.resourceLocationIds(() -> rawRegistryIds(registrySupplier)).get();
    }

    private static Collection<ResourceLocation> rawRegistryIds(Supplier<? extends IForgeRegistry<?>> registrySupplier) {
        try {
            IForgeRegistry<?> registry = registrySupplier.get();
            return registry == null ? List.of() : registry.getKeys();
        } catch (RuntimeException exception) {
            return List.of();
        }
    }

}
