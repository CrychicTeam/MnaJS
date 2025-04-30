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
import com.mna.api.spells.base.IModifiedSpellPart;
import com.mna.api.spells.base.ISpellDefinition;
import com.mna.api.spells.base.SpellBlacklistResult;
import com.mna.api.spells.parts.SpellEffect;
import com.mna.api.spells.targeting.SpellContext;
import com.mna.api.spells.targeting.SpellSource;
import com.mna.api.spells.targeting.SpellTarget;
import com.mna.spells.SpellCaster;
import com.pickaid.mnajs.kubejs.MnaJSPlugin;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
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
 * Custom Potion Effect Component implementation for KubeJS integration with Mana and Artifice.
 */
public class CustomPotionEffectComponent extends SpellEffect {
    private final Builder builder;
    private ArrayList<SpellReagent> reagents = null;
    private ArrayList<SpellReagent> permanenceReagents = null;
    private List<IFaction> permanentForFactions = null;

    public CustomPotionEffectComponent(Builder builder) {
        super(builder.guiIcon, builder.attributeValuePairs.toArray(new AttributeValuePair[0]));
        this.builder = builder;

        if (builder.reagents != null && !builder.reagents.isEmpty()) {
            this.reagents = new ArrayList<>(builder.reagents);
        }

        if (builder.permanenceReagents != null && !builder.permanenceReagents.isEmpty()) {
            this.permanenceReagents = new ArrayList<>(builder.permanenceReagents);
        }

        if (builder.permanentForFactions != null && !builder.permanentForFactions.isEmpty()) {
            this.permanentForFactions = new ArrayList<>(builder.permanentForFactions);
        }
    }

    @Override
    public ComponentApplicationResult ApplyEffect(SpellSource source, SpellTarget target, IModifiedSpellPart<SpellEffect> modificationData, SpellContext context) {
        if (builder.applyEffectCallback != null) {
            return builder.applyEffectCallback.apply(source, target, modificationData, context);
        }

        // Default implementation for potion effects if no callback is provided
        if (builder.effect != null && target.isLivingEntity()) {
            boolean permanent = checkPermanencyReagents(source, target, context);
            int duration = permanent ? -1 : (builder.modifiesDuration ? (int) (modificationData.getValue(Attribute.DURATION) * 20.0F) : 1);
            if (duration < 0) {
                duration = -1;
            }

            int amp = builder.modifiesMagnitude ? getPotionAmplitude(source, target, modificationData, context) : 0;
            if (applicationPredicate(target.getLivingEntity())) {
                if (source.getCaster() == target.getEntity() && target.getLivingEntity().hasEffect(builder.effect)
                        && target.getLivingEntity().getEffect(builder.effect).isInfiniteDuration()) {
                    target.getLivingEntity().removeEffect(builder.effect);
                } else {
                    if (permanent && source.isPlayerCaster()) {
                        List<SpellReagent> permanentReagents = getPermanencyReagents(source.getPlayer(), source.getHand());
                        if (permanentReagents != null && !permanentReagents.isEmpty()) {
                            // Consume reagents for permanent effects
                            if (!SpellCaster.consumeReagents(source.getPlayer(), source.getHand(), permanentReagents)) {
                                return ComponentApplicationResult.FAIL;
                            }
                        }
                    }

                    target.getLivingEntity().addEffect(new MobEffectInstance(builder.effect, duration, amp, false, false));
                }

                if (builder.effect.getCategory() == MobEffectCategory.HARMFUL && source.hasCasterReference()) {
                    target.getLivingEntity().setLastHurtByMob(source.getCaster());
                }

                return ComponentApplicationResult.SUCCESS;
            }

            if (source.isPlayerCaster()) {
                source.getPlayer().sendSystemMessage(Component.translatable("mna:components/potion_effect_component.cannot_apply",
                        Component.translatable(target.getLivingEntity().getType().getDescriptionId()).getString()));
            }
        }

        return ComponentApplicationResult.FAIL;
    }

    protected boolean checkPermanencyReagents(SpellSource source, SpellTarget target, SpellContext context) {
        if (builder.checkPermanencyReagentsCallback != null) {
            return builder.checkPermanencyReagentsCallback.apply(source, target, context);
        }

        if (source.isPlayerCaster() && source.getCaster() == target.getEntity()) {
            // Check for faction-based permanency
            if (this.permanentForFactions != null && !this.permanentForFactions.isEmpty()) {
                Player player = source.getPlayer();
                // In a real implementation, you'd use PlayerProgressionProvider to check factions
                // This is a simplified placeholder implementation
                return true;
            }

            // Check for reagent-based permanency
            List<SpellReagent> permanentReagents = getPermanencyReagents(source.getPlayer(), source.getHand());
            if (permanentReagents != null && !permanentReagents.isEmpty()) {
                for (SpellReagent reagent : permanentReagents) {
                    if (context.isReagentMissing(reagent.getReagentStack().getItem())) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    protected int getPotionAmplitude(SpellSource source, SpellTarget target, IModifiedSpellPart<SpellEffect> modificationData, SpellContext context) {
        if (builder.getPotionAmplitudeCallback != null) {
            return builder.getPotionAmplitudeCallback.apply(source, target, modificationData, context);
        }

        return modificationData.getContainedAttributes().contains(Attribute.LESSER_MAGNITUDE) ?
                (int) modificationData.getValue(Attribute.LESSER_MAGNITUDE) - 1 :
                (int) modificationData.getValue(Attribute.MAGNITUDE) - 1;
    }

    protected boolean applicationPredicate(LivingEntity target) {
        if (builder.applicationPredicateCallback != null) {
            return builder.applicationPredicateCallback.apply(target);
        }
        return true;
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
        ArrayList<SpellReagent> allReagents = new ArrayList<>();

        if (this.reagents != null) {
            allReagents.addAll(this.reagents.stream().filter((r) -> !r.isIgnoredBy(caster)).toList());
        }

        List<SpellReagent> permanencyReagents = getPermanencyReagents(caster, hand);
        if (permanencyReagents != null && !permanencyReagents.isEmpty()) {
            allReagents.addAll(permanencyReagents);
        }

        return allReagents.isEmpty() ? null : allReagents;
    }

    protected List<SpellReagent> getPermanencyReagents(Player caster, @Nullable InteractionHand hand) {
        return (this.permanenceReagents == null) ? new ArrayList<>() :
                this.permanenceReagents.stream().filter((r) -> !r.isIgnoredBy(caster)).toList();
    }

    @Override
    public void addReagentTooltip(Player player, @Nullable InteractionHand hand, List<Component> tooltip, SpellReagent reagent) {
        if (builder.addReagentTooltipCallback != null) {
            builder.addReagentTooltipCallback.apply(player, hand, tooltip, reagent);
        } else {
            List<SpellReagent> permanencyReagents = getPermanencyReagents(player, hand);
            if (permanencyReagents != null && permanencyReagents.contains(reagent)) {
                MutableComponent c = Component.literal(String.format("%d x ", reagent.getReagentStack().getCount()))
                        .append(reagent.getReagentStack().getHoverName())
                        .append(Component.literal(" ("))
                        .append(Component.translatable("item.mna.spell.tooltip.permanent"));

                if (reagent.getOptional()) {
                    c.append(Component.literal(", "))
                            .append(Component.translatable("item.mna.spell.tooltip.optional"));
                }

                c.append(Component.literal(")"));
                tooltip.add(c);
            } else {
                super.addReagentTooltip(player, hand, tooltip, reagent);
            }
        }
    }

    @Override
    public boolean autoConsumeReagents() {
        return builder.autoConsumeReagents;
    }

    @Override
    public SpellPartTags getUseTag() {
        if (builder.useTag != null) {
            return builder.useTag;
        }

        if (builder.effect != null) {
            switch (builder.effect.getCategory()) {
                case BENEFICIAL:
                    return SpellPartTags.FRIENDLY;
                case HARMFUL:
                    return SpellPartTags.HARMFUL;
                case NEUTRAL:
                default:
                    return SpellPartTags.NEUTRAL;
            }
        }

        return SpellPartTags.NEUTRAL;
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

    @Info("Callback for checking if permanency reagents are available")
    public interface CheckPermanencyReagentsCallback {
        boolean apply(SpellSource source, SpellTarget target, SpellContext context);
    }

    @Info("Callback for calculating potion effect amplitude")
    public interface GetPotionAmplitudeCallback {
        int apply(SpellSource source, SpellTarget target, IModifiedSpellPart<SpellEffect> modificationData, SpellContext context);
    }

    @Info("Callback for determining if the effect can be applied to an entity")
    public interface ApplicationPredicateCallback {
        boolean apply(LivingEntity target);
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

    public static class Builder extends BuilderBase<CustomPotionEffectComponent> {
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
        private SpellPartTags useTag = null; // Will be determined by effect category
        private List<Affinity> validTinkerAffinities = Arrays.asList(Affinity.ARCANE, Affinity.EARTH, Affinity.ENDER, Affinity.FIRE, Affinity.WATER, Affinity.WIND, Affinity.ICE, Affinity.LIGHTNING);
        private float soundVolume = 0.15F;
        private SoundEvent soundEffect = SFX.Spell.Impact.Single.ARCANE;
        private List<AttributeValuePair> attributeValuePairs = new ArrayList<>();
        private ArrayList<SpellReagent> reagents = new ArrayList<>();
        private ArrayList<SpellReagent> permanenceReagents = new ArrayList<>();
        private ArrayList<IFaction> permanentForFactions = new ArrayList<>();
        private int requiredXPForRote = 100;
        private boolean isSilverSpell = false;
        private float ire = 0.01F;
        private boolean replacesHeldItem = false;
        private IFaction factionRequirement = null;
        private String addingModName = null;
        private MobEffect effect = null;
        private boolean modifiesDuration = false;
        private boolean modifiesMagnitude = false;

        private ApplyEffectCallback applyEffectCallback;
        private CheckPermanencyReagentsCallback checkPermanencyReagentsCallback;
        private GetPotionAmplitudeCallback getPotionAmplitudeCallback;
        private ApplicationPredicateCallback applicationPredicateCallback;
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

        @Info("Sets the GUI icon for this potion effect component")
        public Builder guiIcon(ResourceLocation icon) {
            this.guiIcon = icon;
            return this;
        }

        @Info("Sets the potion effect to apply")
        public Builder effect(MobEffect effect) {
            this.effect = effect;
            return this;
        }

        @Info("Sets whether this component modifies duration")
        public Builder modifiesDuration(boolean modifies) {
            this.modifiesDuration = modifies;
            return this;
        }

        @Info("Sets whether this component modifies magnitude")
        public Builder modifiesMagnitude(boolean modifies) {
            this.modifiesMagnitude = modifies;
            return this;
        }

        @Info("Sets the affinity for this potion effect component")
        public Builder affinity(Affinity affinity) {
            this.affinity = affinity;
            return this;
        }

        @Info("Sets the initial complexity value for this potion effect component")
        public Builder initialComplexity(float complexity) {
            this.initialComplexity = complexity;
            return this;
        }

        @Info("Adds a duration attribute to this potion effect")
        public Builder addDurationAttribute(float baseValue, float minValue, float maxValue, float stepSize, float complexity) {
            this.attributeValuePairs.add(new AttributeValuePair(Attribute.DURATION, baseValue, minValue, maxValue, stepSize, complexity));
            this.modifiesDuration = true;
            return this;
        }

        @Info("Adds a magnitude attribute to this potion effect")
        public Builder addMagnitudeAttribute(float baseValue, float minValue, float maxValue, float stepSize, float complexity) {
            this.attributeValuePairs.add(new AttributeValuePair(Attribute.MAGNITUDE, baseValue, minValue, maxValue, stepSize, complexity));
            this.modifiesMagnitude = true;
            return this;
        }

        @Info("Adds a lesser magnitude attribute to this potion effect")
        public Builder addLesserMagnitudeAttribute(float baseValue, float minValue, float maxValue, float stepSize, float complexity) {
            this.attributeValuePairs.add(new AttributeValuePair(Attribute.LESSER_MAGNITUDE, baseValue, minValue, maxValue, stepSize, complexity));
            this.modifiesMagnitude = true;
            return this;
        }

        @Info("Adds a modifiable attribute to this potion effect component")
        public Builder addAttribute(Attribute attribute, float baseValue, float minValue, float maxValue, float stepSize, float complexity) {
            this.attributeValuePairs.add(new AttributeValuePair(attribute, baseValue, minValue, maxValue, stepSize, complexity));
            return this;
        }

        @Info("Sets the baseline cooldown for this potion effect component in ticks")
        public Builder baselineCooldown(int cooldown) {
            this.baselineCooldown = cooldown;
            return this;
        }

        @Info("Sets whether this potion effect component can be channeled")
        public Builder canBeChanneled(boolean canBeChanneled) {
            this.canBeChanneled = canBeChanneled;
            return this;
        }

        @Info("Sets whether this potion effect component targets entities")
        public Builder targetsEntities(boolean targetsEntities) {
            this.targetsEntities = targetsEntities;
            return this;
        }

        @Info("Sets whether this potion effect component targets blocks")
        public Builder targetsBlocks(boolean targetsBlocks) {
            this.targetsBlocks = targetsBlocks;
            return this;
        }

        @Info("Sets the default block face for this potion effect component")
        public Builder defaultBlockFace(Direction face) {
            this.defaultBlockFace = face;
            return this;
        }

        @Info("Sets whether this potion effect component is useable by players")
        public Builder isUseableByPlayers(boolean isUseable) {
            this.isUseableByPlayers = isUseable;
            return this;
        }

        @Info("Sets whether this potion effect component automatically consumes reagents")
        public Builder autoConsumeReagents(boolean autoConsume) {
            this.autoConsumeReagents = autoConsume;
            return this;
        }

        @Info("Sets the use tag for this potion effect component (normally determined by effect category)")
        public Builder useTag(SpellPartTags tag) {
            this.useTag = tag;
            return this;
        }

        @Info("Sets the valid tinker affinities for this potion effect component")
        public Builder validTinkerAffinities(List<Affinity> affinities) {
            this.validTinkerAffinities = affinities;
            return this;
        }

        @Info("Sets the sound volume for this potion effect component")
        public Builder soundVolume(float volume) {
            this.soundVolume = volume;
            return this;
        }

        @Info("Sets the sound effect for this potion effect component")
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

        @Info("Sets the mod name that will be displayed as the creator of this spell")
        public Builder addingModName(String modName) {
            this.addingModName = modName;
            return this;
        }

        @Info("Adds a reagent requirement to this potion effect component")
        public Builder addReagent(ItemStack reagentStack, boolean compareNBT, boolean ignoreDurability, boolean consume, IFaction... ignoredBy) {
            SpellReagent reagent = new SpellReagent(null, reagentStack, compareNBT, ignoreDurability, consume, false, ignoredBy);
            this.reagents.add(reagent);
            return this;
        }

        @Info("Adds a reagent requirement to this potion effect component with default settings")
        public Builder addReagent(ItemStack reagentStack, IFaction... ignoredBy) {
            return addReagent(reagentStack, false, false, true, ignoredBy);
        }

        @Info("Adds an optional reagent to this potion effect component")
        public Builder addOptionalReagent(ItemStack reagentStack, boolean compareNBT, boolean ignoreDurability, boolean consume, IFaction... ignoredBy) {
            SpellReagent reagent = new SpellReagent(null, reagentStack, compareNBT, ignoreDurability, consume, true, ignoredBy);
            this.reagents.add(reagent);
            return this;
        }

        @Info("Adds an optional reagent to this potion effect component with default settings")
        public Builder addOptionalReagent(ItemStack reagentStack, IFaction... ignoredBy) {
            return addOptionalReagent(reagentStack, false, false, true, ignoredBy);
        }

        @Info("Adds a permanency reagent to this potion effect component")
        public Builder addPermanencyReagent(ItemStack reagentStack, boolean compareNBT, boolean ignoreDurability, boolean consume, IFaction... ignoredBy) {
            SpellReagent reagent = new SpellReagent(null, reagentStack, compareNBT, ignoreDurability, consume, true, ignoredBy);
            this.permanenceReagents.add(reagent);
            return this;
        }

        @Info("Adds a permanency reagent to this potion effect component with default settings")
        public Builder addPermanencyReagent(ItemStack reagentStack, IFaction... ignoredBy) {
            return addPermanencyReagent(reagentStack, false, false, true, ignoredBy);
        }

        @Info("Makes this potion effect permanent for specified factions")
        public Builder permanentFor(IFaction... factions) {
            this.permanentForFactions.addAll(Arrays.asList(factions));
            return this;
        }

        @Info("Sets the callback for applying the spell effect")
        public Builder applyEffect(ApplyEffectCallback callback) {
            this.applyEffectCallback = callback;
            return this;
        }

        @Info("Sets the callback for checking permanency reagents")
        public Builder checkPermanencyReagents(CheckPermanencyReagentsCallback callback) {
            this.checkPermanencyReagentsCallback = callback;
            return this;
        }

        @Info("Sets the callback for calculating potion amplitude")
        public Builder getPotionAmplitude(GetPotionAmplitudeCallback callback) {
            this.getPotionAmplitudeCallback = callback;
            return this;
        }

        @Info("Sets the callback for application predicate")
        public Builder applicationPredicate(ApplicationPredicateCallback callback) {
            this.applicationPredicateCallback = callback;
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
        public CustomPotionEffectComponent createObject() {
            return new CustomPotionEffectComponent(this);
        }
    }
}