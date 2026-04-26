package com.pickaid.mnajs.kubejs.probe;

import com.mna.Registries;
import com.mna.api.cantrips.ICantrip;
import com.mna.api.capabilities.resource.CastingResourceIDs;
import com.mna.api.entities.construct.ConstructCapability;
import com.mna.api.entities.construct.ConstructSlot;
import com.pickaid.mnajs.content.construct.MnaConstructMaterialSupport;
import com.mna.api.events.ProgressionEventIDs;
import com.mna.api.faction.FactionIDs;
import com.mna.cantrips.CantripRegistry;
import com.mna.capabilities.playerdata.magic.resources.CastingResourceRegistry;
import com.mna.recipes.RecipeInit;
import com.pickaid.mnajs.kubejs.id.MnaAdvancementId;
import com.pickaid.mnajs.kubejs.id.MnaBlockId;
import com.pickaid.mnajs.kubejs.id.MnaCantripId;
import com.pickaid.mnajs.kubejs.id.MnaCastingResourceId;
import com.pickaid.mnajs.kubejs.id.MnaConstructMaterialId;
import com.pickaid.mnajs.kubejs.id.MnaConstructCapabilityId;
import com.pickaid.mnajs.kubejs.id.MnaConstructSlotId;
import com.pickaid.mnajs.kubejs.id.MnaEnumIds;
import com.pickaid.mnajs.kubejs.id.MnaConstructTaskId;
import com.pickaid.mnajs.kubejs.id.MnaFactionId;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.id.MnaLootTableId;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.kubejs.id.MnaMobEffectId;
import com.pickaid.mnajs.kubejs.id.MnaModifierId;
import com.pickaid.mnajs.kubejs.id.MnaProgressionEventId;
import com.pickaid.mnajs.kubejs.id.MnaRitualEffectId;
import com.pickaid.mnajs.kubejs.id.MnaRitualId;
import com.pickaid.mnajs.kubejs.id.MnaShapeId;
import com.pickaid.mnajs.kubejs.id.MnaSoundId;
import com.pickaid.mnajs.kubejs.id.MnaSpellEffectId;
import com.pickaid.mnajs.kubejs.id.MnaStructureId;
import com.pickaid.mnajs.kubejs.texture.MnaTexture;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

final class MnaJSLegacyProbeIdAliases {
    private static final List<IdAlias> ID_ALIASES = List.of(
            new IdAlias("MnaAdvancementId", MnaAdvancementId.class, MnaJSLegacyProbeIdAliases::advancementIds),
            new IdAlias("MnaProgressionEventId", MnaProgressionEventId.class, MnaJSLegacyProbeIdAliases::progressionEventIds),
            new IdAlias("MnaFactionId", MnaFactionId.class, MnaJSLegacyProbeIdAliases::factionIds),
            new IdAlias("MnaCastingResourceId", MnaCastingResourceId.class, MnaJSLegacyProbeIdAliases::castingResourceIds),
            new IdAlias("MnaConstructMaterialId", MnaConstructMaterialId.class, () -> resourceLocationIds(MnaConstructMaterialSupport::resourceLocationIds)),
            new IdAlias("MnaConstructSlotId", MnaConstructSlotId.class, () -> MnaEnumIds.valuesOf(ConstructSlot.class)),
            new IdAlias("MnaConstructCapabilityId", MnaConstructCapabilityId.class, () -> MnaEnumIds.valuesOf(ConstructCapability.class)),
            new IdAlias("MnaMobEffectId", MnaMobEffectId.class, () -> registryIds(() -> ForgeRegistries.MOB_EFFECTS), "MobEffect"),
            new IdAlias("MnaRitualEffectId", MnaRitualEffectId.class, () -> registryIds(Registries.RitualEffect)),
            new IdAlias("MnaSpellEffectId", MnaSpellEffectId.class, () -> registryIds(Registries.SpellEffect)),
            new IdAlias("MnaShapeId", MnaShapeId.class, () -> registryIds(Registries.Shape)),
            new IdAlias("MnaModifierId", MnaModifierId.class, () -> registryIds(Registries.Modifier)),
            new IdAlias("MnaConstructTaskId", MnaConstructTaskId.class, () -> registryIds(Registries.ConstructTasks)),
            new IdAlias("MnaRitualId", MnaRitualId.class, () -> recipeIds(() -> RecipeInit.RITUAL_TYPE.get())),
            new IdAlias("MnaManaweavePatternId", MnaManaweavePatternId.class, () -> recipeIds(() -> RecipeInit.MANAWEAVING_PATTERN_TYPE.get())),
            new IdAlias("MnaCantripId", MnaCantripId.class, MnaJSLegacyProbeIdAliases::cantripIds),
            IdAlias.rawType("MnaItemId", MnaItemId.class, "Item"),
            IdAlias.rawType("MnaBlockId", MnaBlockId.class, "Block"),
            IdAlias.rawType("MnaItemOrTag", MnaItemOrTag.class, "Item | ItemTag"),
            new IdAlias("MnaLootTableId", MnaLootTableId.class, MnaJSLegacyProbeIdAliases::lootTableIds),
            new IdAlias("MnaSoundId", MnaSoundId.class, () -> registryIds(() -> ForgeRegistries.SOUND_EVENTS), "SoundEvent"),
            new IdAlias("MnaStructureId", MnaStructureId.class, MnaJSLegacyProbeIdAliases::structureIds),
            new IdAlias("MnaTexture", MnaTexture.class, List::of, "Texture")
    );

    private MnaJSLegacyProbeIdAliases() {
    }

    static List<IdAlias> all() {
        return ID_ALIASES;
    }

    private static List<String> advancementIds() {
        return resourceLocationIds(() -> {
            MinecraftServer server = currentServer();
            if (server == null) {
                return List.of();
            }

            return server.getAdvancements().getAllAdvancements().stream()
                    .map(Advancement::getId)
                    .toList();
        });
    }

    private static List<String> progressionEventIds() {
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

    private static List<String> castingResourceIds() {
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

    private static List<String> cantripIds() {
        return resourceLocationIds(() -> CantripRegistry.INSTANCE.getCantrips().stream()
                .map(ICantrip::getId)
                .toList());
    }

    private static List<String> lootTableIds() {
        return resourceLocationIds(() -> {
            MinecraftServer server = currentServer();
            if (server == null) {
                return List.of();
            }
            return server.getLootData().getKeys(LootDataType.TABLE);
        });
    }

    private static List<String> structureIds() {
        return resourceLocationIds(() -> {
            MinecraftServer server = currentServer();
            if (server == null) {
                return List.of();
            }
            return server.getStructureManager().listTemplates().toList();
        });
    }

    private static List<String> recipeIds(Supplier<? extends RecipeType<?>> recipeTypeSupplier) {
        return resourceLocationIds(() -> loadedRecipeIds(recipeTypeSupplier));
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Collection<ResourceLocation> loadedRecipeIds(Supplier<? extends RecipeType<?>> recipeTypeSupplier) {
        if (recipeTypeSupplier == null) {
            return List.of();
        }

        MinecraftServer server = currentServer();
        if (server == null) {
            return List.of();
        }

        try {
            RecipeManager recipeManager = server.getRecipeManager();
            RecipeType<?> recipeType = recipeTypeSupplier.get();
            if (recipeManager == null || recipeType == null) {
                return List.of();
            }

            LinkedHashSet<ResourceLocation> ids = new LinkedHashSet<>();
            List<? extends Recipe<?>> recipes = recipeManager.getAllRecipesFor((RecipeType) recipeType);
            for (Recipe<?> recipe : recipes) {
                if (recipe != null && recipe.getId() != null) {
                    ids.add(recipe.getId());
                }
            }
            return ids;
        } catch (RuntimeException exception) {
            return List.of();
        }
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

    record IdAlias(String alias, Class<?> wrapperClass, Supplier<List<String>> ids, String specialTypeReference) {
        IdAlias(String alias, Class<?> wrapperClass, Supplier<List<String>> ids) {
            this(alias, wrapperClass, ids, null);
        }

        static IdAlias rawType(String alias, Class<?> wrapperClass, String specialTypeReference) {
            return new IdAlias(alias, wrapperClass, List::of, specialTypeReference);
        }
    }
}
