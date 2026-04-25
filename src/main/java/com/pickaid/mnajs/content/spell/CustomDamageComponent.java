package com.pickaid.mnajs.content.spell;

import com.mna.api.affinity.Affinity;
import com.mna.api.faction.IFaction;
import com.mna.api.sound.SFX;
import com.mna.api.spells.ComponentApplicationResult;
import com.mna.api.spells.SpellCraftingContext;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.SpellReagent;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.attributes.AttributeValuePair;
import com.mna.api.spells.base.IDamageComponent;
import com.mna.api.spells.base.IModifiedSpellPart;
import com.mna.api.spells.base.ISpellDefinition;
import com.mna.api.spells.base.SpellBlacklistResult;
import com.mna.api.spells.parts.SpellEffect;
import com.mna.api.spells.targeting.SpellContext;
import com.mna.api.spells.targeting.SpellSource;
import com.mna.api.spells.targeting.SpellTarget;
import com.mna.config.GeneralConfig;
import com.mna.factions.Factions;
import com.pickaid.mnajs.kubejs.id.MnaFactionId;
import com.pickaid.mnajs.kubejs.MnaJSPlugin;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Custom Damage Component implementation for KubeJS integration with Mana and Artifice.
 * This component implements IDamageComponent for damage-based spell effects.
 */
public class CustomDamageComponent extends SpellEffect implements IDamageComponent {
    private final Builder builder;
    private ArrayList<SpellReagent> reagents = null;

    public CustomDamageComponent(Builder builder) {
        super(builder.guiIcon, builder.attributeValuePairs.toArray(new AttributeValuePair[0]));
        this.builder = builder;

        if (builder.reagents != null && !builder.reagents.isEmpty()) {
            this.reagents = new ArrayList<>(builder.reagents);
        }
    }

    @Override
    public ComponentApplicationResult ApplyEffect(SpellSource source, SpellTarget target, IModifiedSpellPart<SpellEffect> modificationData, SpellContext context) {
        if (builder.applyEffectCallback != null) {
            return builder.applyEffectCallback.apply(source, target, modificationData, context);
        }

        // Default implementation for damage effects if no callback is provided
        if (target.isLivingEntity()) {
            float damage = modificationData.getValue(Attribute.DAMAGE) * GeneralConfig.getDamageMultiplier();

            if (builder.damageEntityCallback != null) {
                return builder.damageEntityCallback.apply(source, target, damage, context);
            } else {
                // Basic damage implementation
                if (target.getLivingEntity().hurt(source.getCaster().damageSources().magic(), damage)) {
                    return ComponentApplicationResult.SUCCESS;
                }
            }
        }

        return ComponentApplicationResult.FAIL;
    }

    @Override
    public Affinity getAffinity() {
        return builder.affinity;
    }

    @Override
    public float initialComplexity() {
        return builder.initialComplexity;
    }

    @Override
    public SoundEvent SoundEffect() {
        if (builder.soundEffect != null) {
            return builder.soundEffect;
        }
        return super.SoundEffect();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void SpawnParticles(Level world, Vec3 impact_position, Vec3 normal, int age, @Nullable LivingEntity caster, @Nullable ISpellDefinition recipe) {
        if (builder.spawnParticlesCallback != null) {
            builder.spawnParticlesCallback.apply(world, impact_position, normal, age, caster, recipe);
        } else {
            super.SpawnParticles(world, impact_position, normal, age, caster, recipe);
        }
    }

    @Override
    public boolean isCraftable(SpellCraftingContext context) {
        if (builder.isCraftableCallback != null) {
            return builder.isCraftableCallback.apply(context);
        }
        return super.isCraftable(context);
    }

    @Override
    public int baselineCooldown() {
        return builder.baselineCooldown;
    }

    @Override
    public boolean canBeChanneled() {
        return builder.canBeChanneled;
    }

    @Override
    public boolean targetsEntities() {
        return builder.targetsEntities;
    }

    @Override
    public boolean targetsBlocks() {
        return builder.targetsBlocks;
    }

    @Override
    public boolean isHellfireBoosted(Attribute attr) {
        if (builder.isHellfireBoostedCallback != null) {
            return builder.isHellfireBoostedCallback.apply(attr);
        }
        return super.isHellfireBoosted(attr);
    }

    @Override
    public Direction defaultBlockFace() {
        return builder.defaultBlockFace;
    }

    @Override
    public boolean isUseableByPlayers() {
        return builder.isUseableByPlayers;
    }

    @Override
    public int requiredXPForRote() {
        return builder.requiredXPForRote;
    }

    @Override
    public boolean applyAtChanneledEntityPos(boolean clientSide) {
        if (builder.applyAtChanneledEntityPosCallback != null) {
            return builder.applyAtChanneledEntityPosCallback.apply(clientSide);
        }
        return super.applyAtChanneledEntityPos(clientSide);
    }

    @Override
    @Nullable
    public List<SpellReagent> getRequiredReagents(@Nullable Player caster, @Nullable InteractionHand hand) {
        if (this.reagents == null) {
            return null;
        }
        return this.reagents.stream().filter((r) -> !r.isIgnoredBy(caster)).toList();
    }

    @Override
    public void addReagentTooltip(Player player, @Nullable InteractionHand hand, List<Component> tooltip, SpellReagent reagent) {
        if (builder.addReagentTooltipCallback != null) {
            builder.addReagentTooltipCallback.apply(player, hand, tooltip, reagent);
        } else {
            super.addReagentTooltip(player, hand, tooltip, reagent);
        }
    }

    @Override
    public boolean autoConsumeReagents() {
        return builder.autoConsumeReagents;
    }

    @Override
    public SpellPartTags getUseTag() {
        return builder.useTag;
    }

    @Override
    public String getDescriptionTooltip(Attribute forAttr) {
        if (builder.getDescriptionTooltipCallback != null) {
            return builder.getDescriptionTooltipCallback.apply(forAttr);
        }
        return super.getDescriptionTooltip(forAttr);
    }

    @Override
    public IFaction getFactionRequirement() {
        return builder.factionRequirement;
    }

    @Override
    public boolean canBeOnRandomStaff() {
        if (builder.canBeOnRandomStaffCallback != null) {
            return builder.canBeOnRandomStaffCallback.apply();
        }
        return !this.isSilverSpell();
    }

    @Override
    public boolean replacesHeldItem() {
        return builder.replacesHeldItem;
    }

    @Override
    public boolean magnitudeHealthCheck(SpellSource source, SpellTarget target, int magnitude, int healthPerMagnitude) {
        if (builder.magnitudeHealthCheckCallback != null) {
            return builder.magnitudeHealthCheckCallback.apply(source, target, magnitude, healthPerMagnitude);
        }
        return super.magnitudeHealthCheck(source, target, magnitude, healthPerMagnitude);
    }

    @Override
    public boolean isTargetFriendlyToCaster(SpellSource source, Entity target) {
        if (builder.isTargetFriendlyCallback != null) {
            return builder.isTargetFriendlyCallback.apply(source, target);
        }
        return super.isTargetFriendlyToCaster(source, target);
    }

    @Override
    public boolean isSilverSpell() {
        return builder.isSilverSpell;
    }

    @Override
    public boolean isBaseMna() {
        return false; // Always false for custom spells
    }

    @Override
    public String getAddingModName() {
        return builder.addingModName != null ? builder.addingModName : super.getAddingModName();
    }

    @Override
    public float ire() {
        return builder.ire;
    }

    @Override
    public List<Affinity> getValidTinkerAffinities() {
        return builder.validTinkerAffinities;
    }

    @Override
    public float getSoundVolume() {
        return builder.soundVolume;
    }

    @Override
    public SpellBlacklistResult canBeCastAt(Level world, Vec3 position) {
        if (builder.canBeCastAtCallback != null) {
            return builder.canBeCastAtCallback.apply(world, position);
        }
        return super.canBeCastAt(world, position);
    }

    @Info("Callback interface for applying the spell effect")
    public interface ApplyEffectCallback {
        ComponentApplicationResult apply(SpellSource source, SpellTarget target, IModifiedSpellPart<SpellEffect> modificationData, SpellContext context);
    }

    @Info("Callback for damage-specific entity damaging logic")
    public interface DamageEntityCallback {
        ComponentApplicationResult apply(SpellSource source, SpellTarget target, float damage, SpellContext context);
    }

    @Info("Callback interface for checking if the spell is craftable")
    public interface IsCraftableCallback {
        boolean apply(SpellCraftingContext context);
    }

    @Info("Callback interface for determining if an attribute is hellfire boosted")
    public interface IsHellfireBoostedCallback {
        boolean apply(Attribute attr);
    }

    @Info("Callback interface for determining if the spell effect should apply at the channeled entity position")
    public interface ApplyAtChanneledEntityPosCallback {
        boolean apply(boolean clientSide);
    }

    @Info("Callback interface for custom particle spawning")
    public interface SpawnParticlesCallback {
        void apply(Level world, Vec3 impact_position, Vec3 normal, int age, @Nullable LivingEntity caster, @Nullable ISpellDefinition recipe);
    }

    @Info("Callback interface for adding custom reagent tooltips")
    public interface AddReagentTooltipCallback {
        void apply(Player player, @Nullable InteractionHand hand, List<Component> tooltip, SpellReagent reagent);
    }

    @Info("Callback interface for determining if a target is friendly to the caster")
    public interface IsTargetFriendlyCallback {
        boolean apply(SpellSource source, Entity target);
    }

    @Info("Callback interface for magnitude health checks")
    public interface MagnitudeHealthCheckCallback {
        boolean apply(SpellSource source, SpellTarget target, int magnitude, int healthPerMagnitude);
    }

    @Info("Callback interface for determining if the spell can be cast at a location")
    public interface CanBeCastAtCallback {
        SpellBlacklistResult apply(Level world, Vec3 position);
    }

    @Info("Callback interface for getting the description tooltip for an attribute")
    public interface GetDescriptionTooltipCallback {
        String apply(Attribute forAttr);
    }

    @Info("Callback interface for determining if the spell can be on a random staff")
    public interface CanBeOnRandomStaffCallback {
        boolean apply();
    }

    public static class Builder extends BuilderBase<CustomDamageComponent> {
        private ResourceLocation guiIcon;
        private Affinity affinity = Affinity.ARCANE;
        private float initialComplexity = 1.0F;
        private int baselineCooldown = 0;
        private boolean canBeChanneled = true;
        private boolean targetsEntities = true;
        private boolean targetsBlocks = false;
        private Direction defaultBlockFace = Direction.UP;
        private boolean isUseableByPlayers = true;
        private boolean autoConsumeReagents = true;
        private SpellPartTags useTag = SpellPartTags.HARMFUL; // Default to HARMFUL for damage components
        private List<Affinity> validTinkerAffinities = Arrays.asList(Affinity.ARCANE, Affinity.EARTH, Affinity.ENDER, Affinity.FIRE, Affinity.WATER, Affinity.WIND, Affinity.ICE, Affinity.LIGHTNING);
        private float soundVolume = 0.15F;
        private SoundEvent soundEffect = SFX.Spell.Impact.Single.ARCANE;
        private List<AttributeValuePair> attributeValuePairs = new ArrayList<>();
        private ArrayList<SpellReagent> reagents = new ArrayList<>();
        private int requiredXPForRote = 100;
        private boolean isSilverSpell = false;
        private float ire = 0.01F;
        private boolean replacesHeldItem = false;
        private IFaction factionRequirement = null;
        private String addingModName = null;

        private ApplyEffectCallback applyEffectCallback;
        private DamageEntityCallback damageEntityCallback;
        private IsCraftableCallback isCraftableCallback;
        private IsHellfireBoostedCallback isHellfireBoostedCallback;
        private ApplyAtChanneledEntityPosCallback applyAtChanneledEntityPosCallback;
        private SpawnParticlesCallback spawnParticlesCallback;
        private AddReagentTooltipCallback addReagentTooltipCallback;
        private IsTargetFriendlyCallback isTargetFriendlyCallback;
        private MagnitudeHealthCheckCallback magnitudeHealthCheckCallback;
        private CanBeCastAtCallback canBeCastAtCallback;
        private GetDescriptionTooltipCallback getDescriptionTooltipCallback;
        private CanBeOnRandomStaffCallback canBeOnRandomStaffCallback;

        public Builder(ResourceLocation id) {
            super(id);
        }

        @Override
        public RegistryInfo getRegistryType() {
            return MnaJSPlugin.SPELL_EFFECT.get();
        }

        @Info("Sets the GUI icon for this damage component")
        public Builder guiIcon(ResourceLocation icon) {
            this.guiIcon = icon;
            return this;
        }

        @Info("Sets the affinity for this damage component")
        public Builder affinity(Affinity affinity) {
            this.affinity = affinity;
            return this;
        }

        @Info("Sets the initial complexity value for this damage component")
        public Builder initialComplexity(float complexity) {
            this.initialComplexity = complexity;
            return this;
        }

        @Info("Adds a damage attribute to this spell effect")
        public Builder addDamageAttribute(float baseValue, float minValue, float maxValue, float stepSize, float complexity) {
            this.attributeValuePairs.add(new AttributeValuePair(Attribute.DAMAGE, baseValue, minValue, maxValue, stepSize, complexity));
            return this;
        }

        @Info("Adds a modifiable attribute to this damage component")
        public Builder addAttribute(Attribute attribute, float baseValue, float minValue, float maxValue, float stepSize, float complexity) {
            this.attributeValuePairs.add(new AttributeValuePair(attribute, baseValue, minValue, maxValue, stepSize, complexity));
            return this;
        }

        @Info("Sets the baseline cooldown for this damage component in ticks")
        public Builder baselineCooldown(int cooldown) {
            this.baselineCooldown = cooldown;
            return this;
        }

        @Info("Sets whether this damage component can be channeled")
        public Builder canBeChanneled(boolean canBeChanneled) {
            this.canBeChanneled = canBeChanneled;
            return this;
        }

        @Info("Sets whether this damage component targets entities")
        public Builder targetsEntities(boolean targetsEntities) {
            this.targetsEntities = targetsEntities;
            return this;
        }

        @Info("Sets whether this damage component targets blocks")
        public Builder targetsBlocks(boolean targetsBlocks) {
            this.targetsBlocks = targetsBlocks;
            return this;
        }

        @Info("Sets the default block face for this damage component")
        public Builder defaultBlockFace(Direction face) {
            this.defaultBlockFace = face;
            return this;
        }

        @Info("Sets whether this damage component is useable by players")
        public Builder isUseableByPlayers(boolean isUseable) {
            this.isUseableByPlayers = isUseable;
            return this;
        }

        @Info("Sets whether this damage component automatically consumes reagents")
        public Builder autoConsumeReagents(boolean autoConsume) {
            this.autoConsumeReagents = autoConsume;
            return this;
        }

        @Info("Sets the use tag for this damage component (usually HARMFUL for damage)")
        public Builder useTag(SpellPartTags tag) {
            this.useTag = tag;
            return this;
        }

        @Info("Sets the valid tinker affinities for this damage component")
        public Builder validTinkerAffinities(List<Affinity> affinities) {
            this.validTinkerAffinities = affinities;
            return this;
        }

        @Info("Sets the sound volume for this damage component")
        public Builder soundVolume(float volume) {
            this.soundVolume = volume;
            return this;
        }

        @Info("Sets the sound effect for this damage component")
        public Builder soundEffect(SoundEvent sound) {
            this.soundEffect = sound;
            return this;
        }

        @Info("Sets the required XP for rote learning")
        public Builder requiredXPForRote(int xp) {
            this.requiredXPForRote = xp;
            return this;
        }

        @Info("Sets whether this is a silver spell")
        public Builder isSilverSpell(boolean isSilver) {
            this.isSilverSpell = isSilver;
            return this;
        }

        @Info("Sets the IRE (Incidental Ritual Energy) value for this spell")
        public Builder ire(float ire) {
            this.ire = ire;
            return this;
        }

        @Info("Sets whether this spell replaces the held item")
        public Builder replacesHeldItem(boolean replaces) {
            this.replacesHeldItem = replaces;
            return this;
        }

        @Info("Sets the faction requirement for this spell")
        public Builder factionRequirement(IFaction faction) {
            this.factionRequirement = faction;
            return this;
        }

        @Info("Sets the faction requirement for this spell")
        public Builder factionRequirement(MnaFactionId faction) {
            this.factionRequirement = Factions.INSTANCE.getFaction(faction.location());
            return this;
        }

        @Info("Sets the mod name that will be displayed as the creator of this spell")
        public Builder addingModName(String modName) {
            this.addingModName = modName;
            return this;
        }

        @Info("Adds a reagent requirement to this damage component")
        public Builder addReagent(ItemStack reagentStack, boolean compareNBT, boolean ignoreDurability, boolean consume, IFaction... ignoredBy) {
            SpellReagent reagent = new SpellReagent(null, reagentStack, compareNBT, ignoreDurability, consume, false, ignoredBy);
            this.reagents.add(reagent);
            return this;
        }

        @Info("Adds a reagent requirement to this damage component with default settings")
        public Builder addReagent(ItemStack reagentStack, IFaction... ignoredBy) {
            return addReagent(reagentStack, false, false, true, ignoredBy);
        }

        @Info("Adds an optional reagent to this damage component")
        public Builder addOptionalReagent(ItemStack reagentStack, boolean compareNBT, boolean ignoreDurability, boolean consume, IFaction... ignoredBy) {
            SpellReagent reagent = new SpellReagent(null, reagentStack, compareNBT, ignoreDurability, consume, true, ignoredBy);
            this.reagents.add(reagent);
            return this;
        }

        @Info("Adds an optional reagent to this damage component with default settings")
        public Builder addOptionalReagent(ItemStack reagentStack, IFaction... ignoredBy) {
            return addOptionalReagent(reagentStack, false, false, true, ignoredBy);
        }

        @Info("Sets the callback for applying the spell effect")
        public Builder applyEffect(ApplyEffectCallback callback) {
            this.applyEffectCallback = callback;
            return this;
        }

        @Info("Sets the damage-specific callback for damaging entities")
        public Builder damageEntity(DamageEntityCallback callback) {
            this.damageEntityCallback = callback;
            return this;
        }

        @Info("Sets the callback for checking if the spell is craftable")
        public Builder isCraftable(IsCraftableCallback callback) {
            this.isCraftableCallback = callback;
            return this;
        }

        @Info("Sets the callback for determining if an attribute is hellfire boosted")
        public Builder isHellfireBoosted(IsHellfireBoostedCallback callback) {
            this.isHellfireBoostedCallback = callback;
            return this;
        }

        @Info("Sets the callback for determining if the spell effect should apply at the channeled entity position")
        public Builder applyAtChanneledEntityPos(ApplyAtChanneledEntityPosCallback callback) {
            this.applyAtChanneledEntityPosCallback = callback;
            return this;
        }

        @Info("Sets the callback for custom particle spawning")
        public Builder spawnParticles(SpawnParticlesCallback callback) {
            this.spawnParticlesCallback = callback;
            return this;
        }

        @Info("Sets the callback for adding custom reagent tooltips")
        public Builder addReagentTooltip(AddReagentTooltipCallback callback) {
            this.addReagentTooltipCallback = callback;
            return this;
        }

        @Info("Sets the callback for determining if a target is friendly to the caster")
        public Builder isTargetFriendly(IsTargetFriendlyCallback callback) {
            this.isTargetFriendlyCallback = callback;
            return this;
        }

        @Info("Sets the callback for magnitude health checks")
        public Builder magnitudeHealthCheck(MagnitudeHealthCheckCallback callback) {
            this.magnitudeHealthCheckCallback = callback;
            return this;
        }

        @Info("Sets the callback for determining if the spell can be cast at a location")
        public Builder canBeCastAt(CanBeCastAtCallback callback) {
            this.canBeCastAtCallback = callback;
            return this;
        }

        @Info("Sets the callback for getting the description tooltip for an attribute")
        public Builder getDescriptionTooltip(GetDescriptionTooltipCallback callback) {
            this.getDescriptionTooltipCallback = callback;
            return this;
        }

        @Info("Sets the callback for determining if the spell can be on a random staff")
        public Builder canBeOnRandomStaff(CanBeOnRandomStaffCallback callback) {
            this.canBeOnRandomStaffCallback = callback;
            return this;
        }

        @Override
        public CustomDamageComponent createObject() {
            return new CustomDamageComponent(this);
        }
    }
}
