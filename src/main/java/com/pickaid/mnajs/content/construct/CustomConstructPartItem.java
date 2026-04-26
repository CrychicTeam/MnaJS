package com.pickaid.mnajs.content.construct;

import com.mna.api.entities.construct.ConstructCapability;
import com.mna.api.entities.construct.ConstructMaterial;
import com.mna.api.entities.construct.ConstructSlot;
import com.mna.api.entities.construct.ItemConstructPart;
import com.pickaid.mnajs.kubejs.id.MnaConstructCapabilityId;
import com.pickaid.mnajs.kubejs.id.MnaConstructMaterialId;
import com.pickaid.mnajs.kubejs.id.MnaConstructSlotId;
import com.pickaid.mnajs.kubejs.id.MnaTypedIdLookups;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class CustomConstructPartItem extends ItemConstructPart {
    private final Builder builder;

    public CustomConstructPartItem(Builder builder) {
        super(builder.initialMaterial(), builder.slot, builder.modelMutex);
        this.builder = builder;
    }

    private ConstructMaterial resolvedMaterial() {
        if (builder.material != null) {
            return builder.material;
        }
        if (builder.materialId != null) {
            ConstructMaterial resolved = MnaConstructMaterialSupport.find(builder.materialId.location());
            if (resolved != null) {
                builder.material = resolved;
                return resolved;
            }
        }
        return super.getMaterial();
    }

    @Override
    public ConstructMaterial getMaterial() {
        return resolvedMaterial();
    }

    @Override
    public boolean isFireResistant() {
        return builder.fireResistant != null ? builder.fireResistant : resolvedMaterial() == ConstructMaterial.OBSIDIAN;
    }

    @Override
    public float getAttackDamage() {
        return builder.attackDamage != null ? builder.attackDamage : super.getAttackDamage();
    }

    @Override
    public float getRangedAttackDamage() {
        return builder.rangedAttackDamage != null ? builder.rangedAttackDamage : super.getRangedAttackDamage();
    }

    @Override
    public float getKnockbackBonus() {
        return builder.knockbackBonus != null ? builder.knockbackBonus : super.getKnockbackBonus();
    }

    @Override
    public float getBonusSpeed() {
        return builder.bonusSpeed != null ? builder.bonusSpeed : super.getBonusSpeed();
    }

    @Override
    public int getArmor() {
        return builder.armor != null ? builder.armor : super.getArmor();
    }

    @Override
    public int getToughness() {
        return builder.toughness != null ? builder.toughness : super.getToughness();
    }

    @Override
    public int getIntelligenceBonus() {
        return builder.intelligenceBonus != null ? builder.intelligenceBonus : super.getIntelligenceBonus();
    }

    @Override
    public int getPerceptionDistanceBonus() {
        return builder.perceptionDistanceBonus != null ? builder.perceptionDistanceBonus : super.getPerceptionDistanceBonus();
    }

    @Override
    public int getManaCapacity() {
        return builder.manaCapacity != null ? builder.manaCapacity : super.getManaCapacity();
    }

    @Override
    public int getFluidCapacity() {
        return builder.fluidCapacity != null ? builder.fluidCapacity : super.getFluidCapacity();
    }

    @Override
    public float getActionSpeed(ConstructCapability capability) {
        return builder.actionSpeedOverrides.containsKey(capability)
                ? builder.actionSpeedOverrides.get(capability)
                : super.getActionSpeed(capability);
    }

    @Override
    public int getAttackSpeedModifier() {
        return builder.attackSpeedModifier != null ? builder.attackSpeedModifier : super.getAttackSpeedModifier();
    }

    @Override
    public ConstructCapability[] getEnabledCapabilities() {
        if (builder.enabledCapabilities == null) {
            return super.getEnabledCapabilities();
        }
        return builder.enabledCapabilities.toArray(ConstructCapability[]::new);
    }

    @Override
    public int getInventorySizeBonus() {
        return builder.inventorySizeBonus != null ? builder.inventorySizeBonus : super.getInventorySizeBonus();
    }

    @Override
    public int getBackpackCapacityBoost() {
        return builder.backpackCapacityBoost != null ? builder.backpackCapacityBoost : super.getBackpackCapacityBoost();
    }

    @Override
    public boolean canBeInLoot(LootContext context) {
        return builder.allowInLoot != null ? builder.allowInLoot : super.canBeInLoot(context);
    }

    public static class Builder extends ItemBuilder {
        private ConstructMaterial material;
        private MnaConstructMaterialId materialId;
        private ConstructSlot slot;
        private MnaConstructSlotId slotId;
        private int modelMutex;
        private Boolean fireResistant;
        private Float attackDamage;
        private Float rangedAttackDamage;
        private Float knockbackBonus;
        private Float bonusSpeed;
        private Integer armor;
        private Integer toughness;
        private Integer intelligenceBonus;
        private Integer perceptionDistanceBonus;
        private Integer manaCapacity;
        private Integer fluidCapacity;
        private final EnumMap<ConstructCapability, Float> actionSpeedOverrides = new EnumMap<>(ConstructCapability.class);
        private Integer attackSpeedModifier;
        private List<ConstructCapability> enabledCapabilities;
        private Integer inventorySizeBonus;
        private Integer backpackCapacityBoost;
        private Boolean allowInLoot;

        public Builder(ResourceLocation id) {
            super(id);
        }

        @Info(value = "Copy the public stat surface from an existing construct part item.", params = {
                @Param(name = "template", value = "Existing construct part item or item id such as mna:wood_basic_head.")
        })
        public Builder basedOn(ItemConstructPart template) {
            material(template.getMaterial());
            slot(template.getSlot());
            this.modelMutex = template.getModelTypeMutex();
            this.fireResistant = template.isFireResistant();
            this.attackDamage = template.getAttackDamage();
            this.rangedAttackDamage = template.getRangedAttackDamage();
            this.knockbackBonus = template.getKnockbackBonus();
            this.bonusSpeed = template.getBonusSpeed();
            this.armor = template.getArmor();
            this.toughness = template.getToughness();
            this.intelligenceBonus = template.getIntelligenceBonus();
            this.perceptionDistanceBonus = template.getPerceptionDistanceBonus();
            this.manaCapacity = template.getManaCapacity();
            this.fluidCapacity = template.getFluidCapacity();
            this.attackSpeedModifier = template.getAttackSpeedModifier();
            this.inventorySizeBonus = template.getInventorySizeBonus();
            this.backpackCapacityBoost = template.getBackpackCapacityBoost();
            this.enabledCapabilities = new ArrayList<>(List.of(template.getEnabledCapabilities()));
            this.actionSpeedOverrides.clear();
            for (ConstructCapability capability : ConstructCapability.values()) {
                this.actionSpeedOverrides.put(capability, template.getActionSpeed(capability));
            }
            return this;
        }

        @Info("Set the construct material used by this construct part.")
        public Builder material(MnaConstructMaterialId material) {
            this.material = null;
            this.materialId = material;
            return this;
        }

        @HideFromJS
        public Builder material(ConstructMaterial material) {
            this.material = material;
            this.materialId = MnaTypedIdLookups.wrapConstructMaterial(material);
            return this;
        }

        @Info("Set the construct slot occupied by this construct part.")
        public Builder slot(MnaConstructSlotId slot) {
            this.slot = null;
            this.slotId = slot;
            return this;
        }

        @HideFromJS
        public Builder slot(ConstructSlot slot) {
            this.slot = slot;
            this.slotId = MnaTypedIdLookups.wrapConstructSlot(slot);
            return this;
        }

        @Info("Set the M&A model mutex used by this construct part.")
        public Builder modelMutex(int modelMutex) {
            this.modelMutex = modelMutex;
            return this;
        }

        @Info("Override whether this construct part is fire resistant.")
        public Builder fireResistant(boolean fireResistant) {
            this.fireResistant = fireResistant;
            return this;
        }

        @Info("Override the melee damage contribution of this construct part.")
        public Builder attackDamage(float attackDamage) {
            this.attackDamage = attackDamage;
            return this;
        }

        @Info("Override the ranged damage contribution of this construct part.")
        public Builder rangedAttackDamage(float rangedAttackDamage) {
            this.rangedAttackDamage = rangedAttackDamage;
            return this;
        }

        @Info("Override the knockback bonus contributed by this construct part.")
        public Builder knockbackBonus(float knockbackBonus) {
            this.knockbackBonus = knockbackBonus;
            return this;
        }

        @Info("Override the movement speed bonus contributed by this construct part.")
        public Builder bonusSpeed(float bonusSpeed) {
            this.bonusSpeed = bonusSpeed;
            return this;
        }

        @Info("Override the armor contribution of this construct part.")
        public Builder armor(int armor) {
            this.armor = armor;
            return this;
        }

        @Info("Override the toughness contribution of this construct part.")
        public Builder toughness(int toughness) {
            this.toughness = toughness;
            return this;
        }

        @Info("Override the intelligence bonus contributed by this construct part.")
        public Builder intelligenceBonus(int intelligenceBonus) {
            this.intelligenceBonus = intelligenceBonus;
            return this;
        }

        @Info("Override the perception-distance bonus contributed by this construct part.")
        public Builder perceptionDistanceBonus(int perceptionDistanceBonus) {
            this.perceptionDistanceBonus = perceptionDistanceBonus;
            return this;
        }

        @Info("Override the mana capacity contributed by this construct part.")
        public Builder manaCapacity(int manaCapacity) {
            this.manaCapacity = manaCapacity;
            return this;
        }

        @Info("Override the fluid capacity contributed by this construct part.")
        public Builder fluidCapacity(int fluidCapacity) {
            this.fluidCapacity = fluidCapacity;
            return this;
        }

        @Info("Override the action-speed multiplier for one construct capability.")
        public Builder actionSpeed(MnaConstructCapabilityId capability, float speed) {
            return actionSpeed(MnaTypedIdLookups.requireConstructCapability(capability, "capability"), speed);
        }

        @HideFromJS
        public Builder actionSpeed(ConstructCapability capability, float speed) {
            if (capability != null) {
                this.actionSpeedOverrides.put(capability, speed);
            }
            return this;
        }

        @Info("Replace the enabled capability set of this construct part.")
        public Builder enabledCapabilities(MnaConstructCapabilityId... capabilities) {
            this.enabledCapabilities = new ArrayList<>();
            if (capabilities != null) {
                for (MnaConstructCapabilityId capability : capabilities) {
                    if (capability != null) {
                        this.enabledCapabilities.add(MnaTypedIdLookups.requireConstructCapability(capability, "capability"));
                    }
                }
            }
            return this;
        }

        @HideFromJS
        public Builder enabledCapabilities(ConstructCapability... capabilities) {
            this.enabledCapabilities = new ArrayList<>();
            if (capabilities != null) {
                for (ConstructCapability capability : capabilities) {
                    if (capability != null) {
                        this.enabledCapabilities.add(capability);
                    }
                }
            }
            return this;
        }

        @Info("Append one enabled capability to this construct part.")
        public Builder addCapability(MnaConstructCapabilityId capability) {
            return addCapability(MnaTypedIdLookups.requireConstructCapability(capability, "capability"));
        }

        @HideFromJS
        public Builder addCapability(ConstructCapability capability) {
            if (capability == null) {
                return this;
            }
            if (this.enabledCapabilities == null) {
                this.enabledCapabilities = new ArrayList<>();
            }
            this.enabledCapabilities.add(capability);
            return this;
        }

        @Info("Override the attack-speed modifier of this construct part.")
        public Builder attackSpeedModifier(int attackSpeedModifier) {
            this.attackSpeedModifier = attackSpeedModifier;
            return this;
        }

        @Info("Override the inventory-size bonus of this construct part.")
        public Builder inventorySizeBonus(int inventorySizeBonus) {
            this.inventorySizeBonus = inventorySizeBonus;
            return this;
        }

        @Info("Override the backpack-capacity bonus of this construct part.")
        public Builder backpackCapacityBoost(int backpackCapacityBoost) {
            this.backpackCapacityBoost = backpackCapacityBoost;
            return this;
        }

        @Info("Set whether this construct part is allowed to appear in loot.")
        public Builder allowInLoot(boolean allowInLoot) {
            this.allowInLoot = allowInLoot;
            return this;
        }

        @Override
        public CustomConstructPartItem createObject() {
            if (slot == null && slotId != null) {
                slot = MnaTypedIdLookups.requireConstructSlot(slotId, "slot");
            }
            if (material == null && materialId == null) {
                throw new IllegalArgumentException("Custom construct part " + id + " must define material");
            }
            if (slot == null) {
                throw new IllegalArgumentException("Custom construct part " + id + " must define slot");
            }
            return new CustomConstructPartItem(this);
        }

        private ConstructMaterial initialMaterial() {
            return material != null ? material : ConstructMaterial.UNKNOWN;
        }
    }
}
