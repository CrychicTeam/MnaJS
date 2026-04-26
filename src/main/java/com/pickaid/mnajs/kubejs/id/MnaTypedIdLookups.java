package com.pickaid.mnajs.kubejs.id;

import com.mna.Registries;
import com.mna.api.entities.construct.ConstructCapability;
import com.mna.api.entities.construct.ConstructMaterial;
import com.mna.api.entities.construct.ConstructSlot;
import com.mna.api.entities.construct.ItemConstructPart;
import com.mna.api.entities.construct.ai.ConstructTask;
import com.mna.api.faction.IFaction;
import com.mna.api.rituals.RitualEffect;
import com.mna.api.spells.parts.Modifier;
import com.mna.api.spells.parts.Shape;
import com.mna.api.spells.parts.SpellEffect;
import com.mna.factions.Factions;
import com.pickaid.mnajs.content.construct.MnaConstructMaterialSupport;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public final class MnaTypedIdLookups {
    private MnaTypedIdLookups() {
    }

    public static Item requireItem(MnaItemId id, String fieldName) {
        Objects.requireNonNull(id, fieldName + " can't be null");
        Item item = ForgeRegistries.ITEMS.getValue(id.location());
        if (item == null) {
            throw new IllegalArgumentException("Unknown item id for " + fieldName + ": " + id.id());
        }
        return item;
    }

    public static ItemStack stack(MnaItemId id, String fieldName) {
        return new ItemStack(requireItem(id, fieldName));
    }

    public static IFaction requireFaction(MnaFactionId id, String fieldName) {
        Objects.requireNonNull(id, fieldName + " can't be null");
        IFaction faction = findFaction(id);
        if (faction == null) {
            throw new IllegalArgumentException("Unknown faction id for " + fieldName + ": " + id.id());
        }
        return faction;
    }

    public static IFaction findFaction(MnaFactionId id) {
        if (id == null) {
            return null;
        }

        try {
            return Factions.INSTANCE.getFaction(id.location());
        } catch (NullPointerException ignored) {
            return null;
        }
    }

    public static IFaction[] factions(MnaFactionId[] ids, String fieldName) {
        if (ids == null || ids.length == 0) {
            return new IFaction[0];
        }
        return Arrays.stream(ids)
                .map(id -> requireFaction(id, fieldName))
                .toArray(IFaction[]::new);
    }

    public static MnaFactionId wrapFaction(IFaction faction) {
        if (faction == null) {
            return null;
        }

        for (ResourceLocation id : Registries.Factions.get().getKeys()) {
            IFaction resolved = Factions.INSTANCE.getFaction(id);
            if (resolved == faction || faction.is(id)) {
                return MnaFactionId.of(id);
            }
        }

        return null;
    }

    public static SoundEvent requireSound(MnaSoundId id, String fieldName) {
        Objects.requireNonNull(id, fieldName + " can't be null");
        SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(id.location());
        if (sound == null) {
            throw new IllegalArgumentException("Unknown sound id for " + fieldName + ": " + id.id());
        }
        return sound;
    }

    public static MobEffect requireMobEffect(MnaMobEffectId id, String fieldName) {
        Objects.requireNonNull(id, fieldName + " can't be null");
        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(id.location());
        if (effect == null) {
            throw new IllegalArgumentException("Unknown mob effect id for " + fieldName + ": " + id.id());
        }
        return effect;
    }

    public static ResourceLocation[] locations(MnaCastingResourceId... ids) {
        if (ids == null || ids.length == 0) {
            return new ResourceLocation[0];
        }
        return Arrays.stream(ids)
                .filter(Objects::nonNull)
                .map(MnaCastingResourceId::location)
                .toArray(ResourceLocation[]::new);
    }

    public static MnaCastingResourceId[] wrapCastingResources(ResourceLocation[] ids) {
        if (ids == null || ids.length == 0) {
            return new MnaCastingResourceId[0];
        }
        return Arrays.stream(ids)
                .filter(Objects::nonNull)
                .map(MnaCastingResourceId::of)
                .toArray(MnaCastingResourceId[]::new);
    }

    public static MnaCastingResourceId wrapCastingResource(ResourceLocation id) {
        return id == null ? null : MnaCastingResourceId.of(id);
    }

    public static ConstructMaterial requireConstructMaterial(MnaConstructMaterialId id, String fieldName) {
        Objects.requireNonNull(id, fieldName + " can't be null");
        ConstructMaterial material = MnaConstructMaterialSupport.find(id.location());
        if (material == null) {
            throw new IllegalArgumentException("Unknown construct material id for " + fieldName + ": " + id.id());
        }
        return material;
    }

    public static ConstructSlot requireConstructSlot(MnaConstructSlotId id, String fieldName) {
        Objects.requireNonNull(id, fieldName + " can't be null");
        return MnaEnumIds.requireEnum(ConstructSlot.class, id.id(), fieldName);
    }

    public static ConstructCapability requireConstructCapability(MnaConstructCapabilityId id, String fieldName) {
        Objects.requireNonNull(id, fieldName + " can't be null");
        return MnaEnumIds.requireEnum(ConstructCapability.class, id.id(), fieldName);
    }

    public static ItemConstructPart requireConstructPart(MnaItemId id, String fieldName) {
        Item item = requireItem(id, fieldName);
        if (item instanceof ItemConstructPart part) {
            return part;
        }
        throw new IllegalArgumentException("Item id for " + fieldName + " is not a construct part: " + id.id());
    }

    public static RitualEffect requireRitualEffect(MnaRitualEffectId id, String fieldName) {
        Objects.requireNonNull(id, fieldName + " can't be null");
        RitualEffect effect = Registries.RitualEffect.get().getValue(id.location());
        if (effect == null) {
            throw new IllegalArgumentException("Unknown ritual effect id for " + fieldName + ": " + id.id());
        }
        return effect;
    }

    public static SpellEffect requireSpellEffect(MnaSpellEffectId id, String fieldName) {
        Objects.requireNonNull(id, fieldName + " can't be null");
        SpellEffect effect = Registries.SpellEffect.get().getValue(id.location());
        if (effect == null) {
            throw new IllegalArgumentException("Unknown spell effect id for " + fieldName + ": " + id.id());
        }
        return effect;
    }

    public static Shape requireShape(MnaShapeId id, String fieldName) {
        Objects.requireNonNull(id, fieldName + " can't be null");
        Shape shape = Registries.Shape.get().getValue(id.location());
        if (shape == null) {
            throw new IllegalArgumentException("Unknown shape id for " + fieldName + ": " + id.id());
        }
        return shape;
    }

    public static Modifier requireModifier(MnaModifierId id, String fieldName) {
        Objects.requireNonNull(id, fieldName + " can't be null");
        Modifier modifier = Registries.Modifier.get().getValue(id.location());
        if (modifier == null) {
            throw new IllegalArgumentException("Unknown modifier id for " + fieldName + ": " + id.id());
        }
        return modifier;
    }

    public static ConstructTask requireConstructTask(MnaConstructTaskId id, String fieldName) {
        Objects.requireNonNull(id, fieldName + " can't be null");
        ConstructTask task = Registries.ConstructTasks.get().getValue(id.location());
        if (task == null) {
            throw new IllegalArgumentException("Unknown construct task id for " + fieldName + ": " + id.id());
        }
        return task;
    }

    public static MnaProgressionEventId wrapProgressionEvent(ResourceLocation id) {
        return id == null ? null : MnaProgressionEventId.of(id);
    }

    public static MnaProgressionEventId[] wrapProgressionEvents(Collection<ResourceLocation> ids) {
        if (ids == null || ids.isEmpty()) {
            return new MnaProgressionEventId[0];
        }

        return ids.stream()
                .filter(Objects::nonNull)
                .map(MnaProgressionEventId::of)
                .toArray(MnaProgressionEventId[]::new);
    }

    public static MnaRitualEffectId wrapRitualEffect(RitualEffect effect) {
        if (effect == null) {
            return null;
        }
        ResourceLocation id = Registries.RitualEffect.get().getKey(effect);
        return id == null ? null : MnaRitualEffectId.of(id);
    }

    public static MnaSpellEffectId wrapSpellEffect(SpellEffect effect) {
        if (effect == null) {
            return null;
        }
        ResourceLocation id = Registries.SpellEffect.get().getKey(effect);
        return id == null ? null : MnaSpellEffectId.of(id);
    }

    public static MnaShapeId wrapShape(Shape shape) {
        if (shape == null) {
            return null;
        }
        ResourceLocation id = Registries.Shape.get().getKey(shape);
        return id == null ? null : MnaShapeId.of(id);
    }

    public static MnaModifierId wrapModifier(Modifier modifier) {
        if (modifier == null) {
            return null;
        }
        ResourceLocation id = Registries.Modifier.get().getKey(modifier);
        return id == null ? null : MnaModifierId.of(id);
    }

    public static MnaConstructTaskId wrapConstructTask(ConstructTask task) {
        if (task == null) {
            return null;
        }
        ResourceLocation id = Registries.ConstructTasks.get().getKey(task);
        return id == null ? null : MnaConstructTaskId.of(id);
    }

    public static MnaConstructMaterialId wrapConstructMaterial(ConstructMaterial material) {
        if (material == null || material.getId() == null) {
            return null;
        }
        return MnaConstructMaterialId.of(material.getId());
    }

    public static MnaConstructSlotId wrapConstructSlot(ConstructSlot slot) {
        return slot == null ? null : MnaConstructSlotId.of(MnaEnumIds.externalName(slot));
    }

    public static MnaConstructCapabilityId wrapConstructCapability(ConstructCapability capability) {
        return capability == null ? null : MnaConstructCapabilityId.of(MnaEnumIds.externalName(capability));
    }
}
