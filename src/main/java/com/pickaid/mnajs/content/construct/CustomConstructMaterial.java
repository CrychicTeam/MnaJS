package com.pickaid.mnajs.content.construct;

import com.mna.api.entities.construct.ConstructCapability;
import com.mna.api.entities.construct.ConstructMaterial;
import com.mna.api.entities.construct.ConstructSlot;
import com.mna.api.entities.construct.IConstruct;
import com.pickaid.mnajs.kubejs.MnaJSPlugin;
import com.pickaid.mnajs.kubejs.id.MnaConstructCapabilityId;
import com.pickaid.mnajs.kubejs.id.MnaConstructMaterialId;
import com.pickaid.mnajs.kubejs.id.MnaConstructSlotId;
import com.pickaid.mnajs.kubejs.id.MnaTypedIdLookups;
import com.pickaid.mnajs.kubejs.texture.MnaTexture;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

public class CustomConstructMaterial extends ConstructMaterial {
    private final ResourceLocation id;
    private final ResourceLocation texture;
    private final int health;
    private final float buoyancy;
    private final float speed;
    private final float knockbackResistance;
    private final float explosionResistance;
    private final Tier equivalentTier;
    private final EnumMap<ConstructCapability, Float> cooldownMultipliers;
    private final EnumMap<ConstructSlot, Integer> armorBonuses;
    private final EnumMap<ConstructSlot, Integer> toughnessBonuses;
    private final float damageBonus;
    private final float rangedDamageBonus;
    private final float rangedManaCost;
    private final int manaStorage;
    private final int intelligenceBonus;
    private final int backpackCapacityBoost;
    private final int castingTierEquivalent;
    private final List<ItemStack> deathLoot;
    private final ConstructMaterial delegatedDeathLootMaterial;
    private final DeathLootCallback deathLootCallback;
    private final String modelSet;
    private final Set<String> headModels;
    private final Set<String> torsoModels;
    private final Set<String> legModels;
    private final Set<String> armModels;

    public CustomConstructMaterial(Builder builder) {
        this.id = builder.id;
        this.texture = builder.texture;
        this.health = builder.health;
        this.buoyancy = builder.buoyancy;
        this.speed = builder.speed;
        this.knockbackResistance = builder.knockbackResistance;
        this.explosionResistance = builder.explosionResistance;
        this.equivalentTier = builder.equivalentTier;
        this.cooldownMultipliers = new EnumMap<>(builder.cooldownMultipliers);
        this.armorBonuses = new EnumMap<>(builder.armorBonuses);
        this.toughnessBonuses = new EnumMap<>(builder.toughnessBonuses);
        this.damageBonus = builder.damageBonus;
        this.rangedDamageBonus = builder.rangedDamageBonus;
        this.rangedManaCost = builder.rangedManaCost;
        this.manaStorage = builder.manaStorage;
        this.intelligenceBonus = builder.intelligenceBonus;
        this.backpackCapacityBoost = builder.backpackCapacityBoost;
        this.castingTierEquivalent = builder.castingTierEquivalent;
        this.deathLoot = builder.deathLoot.stream().map(ItemStack::copy).toList();
        this.delegatedDeathLootMaterial = builder.delegatedDeathLootMaterial;
        this.deathLootCallback = builder.deathLootCallback;
        this.modelSet = builder.modelSet;
        this.headModels = Set.copyOf(builder.headModels);
        this.torsoModels = Set.copyOf(builder.torsoModels);
        this.legModels = Set.copyOf(builder.legModels);
        this.armModels = Set.copyOf(builder.armModels);
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public float getBuoyancy() {
        return buoyancy;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public ResourceLocation getTexture() {
        return texture;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }

    @Override
    public float getExplosionResistance() {
        return explosionResistance;
    }

    @Override
    public Tier getEquivalentTier() {
        return equivalentTier;
    }

    @Override
    public float getCooldownMultiplierFor(ConstructCapability capability) {
        return cooldownMultipliers.getOrDefault(capability, 1.0F);
    }

    @Override
    public List<ItemStack> getDeathLootMaterialDrops(IConstruct<?> construct, DamageSource damageSource) {
        if (deathLootCallback != null) {
            List<ItemStack> drops = deathLootCallback.apply(construct, damageSource);
            if (drops == null || drops.isEmpty()) {
                return List.of();
            }
            return drops.stream().filter(Objects::nonNull).map(ItemStack::copy).toList();
        }
        if (delegatedDeathLootMaterial != null) {
            return delegatedDeathLootMaterial.getDeathLootMaterialDrops(construct, damageSource).stream()
                    .filter(Objects::nonNull)
                    .map(ItemStack::copy)
                    .toList();
        }
        return deathLoot.stream().map(ItemStack::copy).toList();
    }

    @Override
    public int getArmorBonus(ConstructSlot slot) {
        return armorBonuses.getOrDefault(slot, 0);
    }

    @Override
    public int getToughnessBonus(ConstructSlot slot) {
        return toughnessBonuses.getOrDefault(slot, 0);
    }

    @Override
    public float getDamageBonus() {
        return damageBonus;
    }

    @Override
    public float getRangedDamageBonus() {
        return rangedDamageBonus;
    }

    @Override
    public float getRangedManaCost() {
        return rangedManaCost;
    }

    @Override
    public int getManaStorage() {
        return manaStorage;
    }

    @Override
    public int getIntelligenceBonus() {
        return intelligenceBonus;
    }

    @Override
    public int getBackpackCapacityBoost() {
        return backpackCapacityBoost;
    }

    @Override
    public int getCastingTierEquivalent() {
        return castingTierEquivalent;
    }

    public String modelSet() {
        return modelSet;
    }

    public Set<String> headModels() {
        return headModels;
    }

    public Set<String> torsoModels() {
        return torsoModels;
    }

    public Set<String> legModels() {
        return legModels;
    }

    public Set<String> armModels() {
        return armModels;
    }

    @Info("Callback interface for generating construct-material death loot dynamically.")
    public interface DeathLootCallback {
        List<ItemStack> apply(IConstruct<?> construct, DamageSource damageSource);
    }

    public static class Builder extends BuilderBase<CustomConstructMaterial> {
        private ResourceLocation texture;
        private int health = 1;
        private float buoyancy = 0.0F;
        private float speed = 0.1F;
        private float knockbackResistance = 0.0F;
        private float explosionResistance = 0.0F;
        private Tier equivalentTier = Tiers.WOOD;
        private final EnumMap<ConstructCapability, Float> cooldownMultipliers = new EnumMap<>(ConstructCapability.class);
        private final EnumMap<ConstructSlot, Integer> armorBonuses = new EnumMap<>(ConstructSlot.class);
        private final EnumMap<ConstructSlot, Integer> toughnessBonuses = new EnumMap<>(ConstructSlot.class);
        private float damageBonus = 0.0F;
        private float rangedDamageBonus = 0.0F;
        private float rangedManaCost = 0.0F;
        private int manaStorage = 0;
        private int intelligenceBonus = 0;
        private int backpackCapacityBoost = 0;
        private int castingTierEquivalent = 0;
        private final List<ItemStack> deathLoot = new ArrayList<>();
        private ConstructMaterial delegatedDeathLootMaterial;
        private DeathLootCallback deathLootCallback;
        private String modelSet;
        private final LinkedHashSet<String> headModels = new LinkedHashSet<>();
        private final LinkedHashSet<String> torsoModels = new LinkedHashSet<>();
        private final LinkedHashSet<String> legModels = new LinkedHashSet<>();
        private final LinkedHashSet<String> armModels = new LinkedHashSet<>();

        public Builder(ResourceLocation id) {
            super(id);
        }

        @Override
        public RegistryInfo<ConstructMaterial> getRegistryType() {
            return MnaJSPlugin.CONSTRUCT_MATERIAL_REGISTRY.get();
        }

        @Info(value = "Copy the full numeric/material behavior surface from an existing construct material.", params = {
                @Param(name = "template", value = "Existing construct material instance or material id such as mna:wood.")
        })
        public Builder basedOn(MnaConstructMaterialId template) {
            return basedOn(MnaTypedIdLookups.requireConstructMaterial(template, "template"));
        }

        @HideFromJS
        public Builder basedOn(ConstructMaterial template) {
            this.texture = template.getTexture();
            this.health = template.getHealth();
            this.buoyancy = template.getBuoyancy();
            this.speed = template.getSpeed();
            this.knockbackResistance = template.getKnockbackResistance();
            this.explosionResistance = template.getExplosionResistance();
            this.equivalentTier = template.getEquivalentTier();
            this.cooldownMultipliers.clear();
            for (ConstructCapability capability : ConstructCapability.values()) {
                this.cooldownMultipliers.put(capability, template.getCooldownMultiplierFor(capability));
            }
            this.armorBonuses.clear();
            this.toughnessBonuses.clear();
            for (ConstructSlot slot : ConstructSlot.values()) {
                this.armorBonuses.put(slot, template.getArmorBonus(slot));
                this.toughnessBonuses.put(slot, template.getToughnessBonus(slot));
            }
            this.damageBonus = template.getDamageBonus();
            this.rangedDamageBonus = template.getRangedDamageBonus();
            this.rangedManaCost = template.getRangedManaCost();
            this.manaStorage = template.getManaStorage();
            this.intelligenceBonus = template.getIntelligenceBonus();
            this.backpackCapacityBoost = template.getBackpackCapacityBoost();
            this.castingTierEquivalent = template.getCastingTierEquivalent();
            this.delegatedDeathLootMaterial = template;
            this.deathLoot.clear();
            this.deathLootCallback = null;
            if (this.modelSet == null || this.modelSet.isBlank()) {
                this.modelSet = template.getId().getPath();
            }
            if (template instanceof CustomConstructMaterial customMaterial) {
                replaceModels(this.headModels, customMaterial.headModels().toArray(String[]::new));
                replaceModels(this.torsoModels, customMaterial.torsoModels().toArray(String[]::new));
                replaceModels(this.legModels, customMaterial.legModels().toArray(String[]::new));
                replaceModels(this.armModels, customMaterial.armModels().toArray(String[]::new));
                this.modelSet = customMaterial.modelSet();
            }
            return this;
        }

        @Info(value = "Set the material texture used by the construct renderer.", params = {
                @Param(name = "texture", value = "Texture id such as mna:textures/entity/animated_construct/wheatwood.png.")
        })
        public Builder texture(MnaTexture texture) {
            this.texture = texture.location();
            return this;
        }

        @Info("Set the health contribution of this construct material.")
        public Builder health(int health) {
            this.health = health;
            return this;
        }

        @Info("Set the buoyancy multiplier of this construct material.")
        public Builder buoyancy(float buoyancy) {
            this.buoyancy = buoyancy;
            return this;
        }

        @Info("Set the speed contribution of this construct material.")
        public Builder speed(float speed) {
            this.speed = speed;
            return this;
        }

        @Info("Set the knockback-resistance contribution of this construct material.")
        public Builder knockbackResistance(float knockbackResistance) {
            this.knockbackResistance = knockbackResistance;
            return this;
        }

        @Info("Set the explosion-resistance contribution of this construct material.")
        public Builder explosionResistance(float explosionResistance) {
            this.explosionResistance = explosionResistance;
            return this;
        }

        @Info("Set the vanilla tool tier used by this construct material.")
        public Builder equivalentTier(Tier equivalentTier) {
            this.equivalentTier = equivalentTier;
            return this;
        }

        @Info("Override the cooldown multiplier for one construct capability.")
        public Builder cooldownMultiplier(MnaConstructCapabilityId capability, float multiplier) {
            return cooldownMultiplier(MnaTypedIdLookups.requireConstructCapability(capability, "capability"), multiplier);
        }

        @HideFromJS
        public Builder cooldownMultiplier(ConstructCapability capability, float multiplier) {
            if (capability != null) {
                this.cooldownMultipliers.put(capability, multiplier);
            }
            return this;
        }

        @Info("Override the armor bonus for one construct slot.")
        public Builder armorBonus(MnaConstructSlotId slot, int amount) {
            return armorBonus(MnaTypedIdLookups.requireConstructSlot(slot, "slot"), amount);
        }

        @HideFromJS
        public Builder armorBonus(ConstructSlot slot, int amount) {
            if (slot != null) {
                this.armorBonuses.put(slot, amount);
            }
            return this;
        }

        @Info("Override the toughness bonus for one construct slot.")
        public Builder toughnessBonus(MnaConstructSlotId slot, int amount) {
            return toughnessBonus(MnaTypedIdLookups.requireConstructSlot(slot, "slot"), amount);
        }

        @HideFromJS
        public Builder toughnessBonus(ConstructSlot slot, int amount) {
            if (slot != null) {
                this.toughnessBonuses.put(slot, amount);
            }
            return this;
        }

        @Info("Set the melee damage bonus of this construct material.")
        public Builder damageBonus(float damageBonus) {
            this.damageBonus = damageBonus;
            return this;
        }

        @Info("Set the ranged damage bonus of this construct material.")
        public Builder rangedDamageBonus(float rangedDamageBonus) {
            this.rangedDamageBonus = rangedDamageBonus;
            return this;
        }

        @Info("Set the ranged mana cost modifier of this construct material.")
        public Builder rangedManaCost(float rangedManaCost) {
            this.rangedManaCost = rangedManaCost;
            return this;
        }

        @Info("Set the mana storage bonus of this construct material.")
        public Builder manaStorage(int manaStorage) {
            this.manaStorage = manaStorage;
            return this;
        }

        @Info("Set the intelligence bonus of this construct material.")
        public Builder intelligenceBonus(int intelligenceBonus) {
            this.intelligenceBonus = intelligenceBonus;
            return this;
        }

        @Info("Set the backpack-capacity bonus of this construct material.")
        public Builder backpackCapacityBoost(int backpackCapacityBoost) {
            this.backpackCapacityBoost = backpackCapacityBoost;
            return this;
        }

        @Info("Set the casting tier equivalent used by this construct material.")
        public Builder castingTierEquivalent(int castingTierEquivalent) {
            this.castingTierEquivalent = castingTierEquivalent;
            return this;
        }

        @Info("Replace static death loot drops for this construct material.")
        public Builder deathLoot(ItemStack... stacks) {
            this.delegatedDeathLootMaterial = null;
            this.deathLootCallback = null;
            this.deathLoot.clear();
            if (stacks != null) {
                for (ItemStack stack : stacks) {
                    if (stack != null && !stack.isEmpty()) {
                        this.deathLoot.add(stack.copy());
                    }
                }
            }
            return this;
        }

        @Info("Set a dynamic death loot callback for this construct material.")
        public Builder deathLoot(DeathLootCallback callback) {
            this.delegatedDeathLootMaterial = null;
            this.deathLoot.clear();
            this.deathLootCallback = callback;
            return this;
        }

        @Info("""
                Set the construct model folder name used by Mana and Artifice.
                This follows the native construct model naming scheme, for example wood, stone, or a custom folder name.
                """)
        public Builder modelSet(String modelSet) {
            this.modelSet = modelSet == null ? null : modelSet.trim();
            return this;
        }

        @Info("Restrict available head model variants. Leave unset to allow every built-in variant name.")
        public Builder headModels(String... models) {
            replaceModels(this.headModels, models);
            return this;
        }

        @Info("Restrict available torso model variants. Leave unset to allow every built-in variant name.")
        public Builder torsoModels(String... models) {
            replaceModels(this.torsoModels, models);
            return this;
        }

        @Info("Restrict available leg model variants. Leave unset to allow every built-in variant name.")
        public Builder legModels(String... models) {
            replaceModels(this.legModels, models);
            return this;
        }

        @Info("Restrict available arm model variants. Leave unset to allow every built-in variant name.")
        public Builder armModels(String... models) {
            replaceModels(this.armModels, models);
            return this;
        }

        @Override
        public CustomConstructMaterial createObject() {
            if (texture == null) {
                throw new IllegalArgumentException("Custom construct material " + id + " must define texture");
            }
            if (equivalentTier == null) {
                throw new IllegalArgumentException("Custom construct material " + id + " must define equivalentTier");
            }
            if (health < 1) {
                throw new IllegalArgumentException("Custom construct material " + id + " must define health >= 1");
            }
            if (modelSet == null || modelSet.isBlank()) {
                modelSet = id.getPath();
            }

            CustomConstructMaterial material = new CustomConstructMaterial(this);
            MnaConstructMaterialSupport.register(material);
            return material;
        }

        private static void replaceModels(Set<String> target, String[] models) {
            target.clear();
            if (models == null) {
                return;
            }
            for (String model : models) {
                String normalized = normalizeModel(model);
                if (normalized != null) {
                    target.add(normalized);
                }
            }
        }

        private static String normalizeModel(String value) {
            if (value == null) {
                return null;
            }
            String normalized = value.trim().toLowerCase(Locale.ROOT);
            return normalized.isEmpty() ? null : normalized;
        }
    }
}
