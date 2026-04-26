package com.pickaid.mnajs.content.spell;

import com.mna.api.affinity.Affinity;
import com.mna.api.faction.IFaction;
import com.mna.api.spells.SpellCraftingContext;
import com.mna.api.spells.SpellPartTags;
import com.mna.api.spells.SpellReagent;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.attributes.AttributeValuePair;
import com.mna.api.spells.base.IModifiedSpellPart;
import com.mna.api.spells.base.ISpellDefinition;
import com.mna.api.spells.base.SpellBlacklistResult;
import com.mna.api.spells.parts.Shape;
import com.mna.api.spells.targeting.SpellSource;
import com.mna.api.spells.targeting.SpellTarget;
import com.pickaid.mnajs.kubejs.MnaJSPlugin;
import com.pickaid.mnajs.kubejs.id.MnaFactionId;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaTypedIdLookups;
import com.pickaid.mnajs.kubejs.texture.MnaTexture;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Custom Shape implementation for KubeJS integration with Mana and Artifice.
 */
public class CustomShape extends Shape {
    private final Builder builder;
    @Nullable
    private final Shape delegate;
    private ArrayList<SpellReagent> reagents = null;

    public CustomShape(Builder builder) {
        super(builder.guiIcon, builder.attributeValuePairs.toArray(new AttributeValuePair[0]));
        this.builder = builder;
        this.delegate = builder.delegate;

        if (builder.reagents != null && !builder.reagents.isEmpty()) {
            this.reagents = new ArrayList<>(builder.reagents);
        }
    }

    @Override
    public List<SpellTarget> Target(SpellSource source, Level world, IModifiedSpellPart<Shape> modificationData, ISpellDefinition recipe) {
        if (builder.targetCallback != null) {
            return builder.targetCallback.apply(source, world, modificationData, recipe);
        }
        if (delegate != null) {
            return delegate.Target(source, world, modificationData, recipe);
        }
        return Arrays.asList(SpellTarget.NONE);
    }

    @Override
    public List<SpellTarget> TargetNPCCast(SpellSource source, Level world, IModifiedSpellPart<Shape> modificationData, ISpellDefinition recipe, SpellTarget targetHint) {
        if (builder.targetNPCCastCallback != null) {
            return builder.targetNPCCastCallback.apply(source, world, modificationData, recipe, targetHint);
        }
        if (delegate != null) {
            return delegate.TargetNPCCast(source, world, modificationData, recipe, targetHint);
        }
        return super.TargetNPCCast(source, world, modificationData, recipe, targetHint);
    }

    @Override
    public float initialComplexity() {
        return builder.initialComplexity;
    }

    @Override
    public boolean spawnsTargetEntity() {
        return builder.spawnsTargetEntity;
    }

    @Override
    public boolean isCraftable(SpellCraftingContext context) {
        if (builder.isCraftableCallback != null) {
            return builder.isCraftableCallback.apply(context);
        }
        if (delegate != null) {
            return delegate.isCraftable(context);
        }
        return super.isCraftable(context);
    }

    @Override
    public boolean isChanneled() {
        return builder.isChanneled;
    }

    @Override
    public boolean allowChanneledComponents() {
        if (builder.allowChanneledComponentsCallback != null) {
            return builder.allowChanneledComponentsCallback.apply();
        }
        if (builder.allowChanneledComponents != null) {
            return builder.allowChanneledComponents;
        }
        if (delegate != null) {
            return delegate.allowChanneledComponents();
        }
        return super.allowChanneledComponents();
    }

    @Override
    public boolean grantComponentRoteXP() {
        return builder.grantComponentRoteXP;
    }

    @Override
    public SpellBlacklistResult canBeCastAt(Level world, Vec3 position) {
        if (builder.canBeCastAtCallback != null) {
            return builder.canBeCastAtCallback.apply(world, position);
        }
        if (delegate != null) {
            return delegate.canBeCastAt(world, position);
        }
        return super.canBeCastAt(world, position);
    }

    @Override
    public int baselineCooldown() {
        return builder.baselineCooldown;
    }

    @Override
    public int maxChannelTime(IModifiedSpellPart<Shape> shape) {
        if (builder.maxChannelTimeCallback != null) {
            return builder.maxChannelTimeCallback.apply(shape);
        }
        if (delegate != null) {
            return delegate.maxChannelTime(shape);
        }
        return super.maxChannelTime(shape);
    }

    @Override
    public boolean isUseableByPlayers() {
        return builder.isUseableByPlayers;
    }

    @Override
    @Nullable
    public List<SpellReagent> getRequiredReagents(@Nullable Player caster) {
        if (this.reagents == null) {
            return null;
        }
        return this.reagents.stream().filter((r) -> !r.isIgnoredBy(caster)).toList();
    }

    @Override
    public boolean affectsCaster() {
        return builder.affectsCaster;
    }

    @Override
    public SpellPartTags getUseTag() {
        return builder.useTag;
    }

    @Override
    public int getTier(Level level) {
        if (builder.tier != null) {
            return builder.tier;
        }
        if (delegate != null) {
            return delegate.getTier(level);
        }
        return super.getTier(level);
    }

    @Override
    public int requiredXPForRote() {
        return builder.requiredXPForRote;
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

    @Info("Callback interface for targeting")
    public interface TargetCallback {
        List<SpellTarget> apply(SpellSource source, Level world, IModifiedSpellPart<Shape> modificationData, ISpellDefinition recipe);
    }

    @Info("Callback interface for NPC targeting")
    public interface TargetNPCCastCallback {
        List<SpellTarget> apply(SpellSource source, Level world, IModifiedSpellPart<Shape> modificationData, ISpellDefinition recipe, SpellTarget targetHint);
    }

    @Info("Callback interface for checking if the spell is craftable")
    public interface IsCraftableCallback {
        boolean apply(SpellCraftingContext context);
    }

    @Info("Callback interface for determining if the spell allows channeled components")
    public interface AllowChanneledComponentsCallback {
        boolean apply();
    }

    @Info("Callback interface for determining if the spell can be cast at a location")
    public interface CanBeCastAtCallback {
        SpellBlacklistResult apply(Level world, Vec3 position);
    }

    @Info("Callback interface for determining the max channel time")
    public interface MaxChannelTimeCallback {
        int apply(IModifiedSpellPart<Shape> shape);
    }

    public static class Builder extends BuilderBase<CustomShape> {
        private ResourceLocation guiIcon;
        private float initialComplexity = 1.0F;
        private boolean spawnsTargetEntity = false;
        private boolean isChanneled = false;
        private Boolean allowChanneledComponents;
        private boolean grantComponentRoteXP = true;
        private int baselineCooldown = 0;
        private boolean isUseableByPlayers = true;
        private boolean affectsCaster = false;
        private SpellPartTags useTag = SpellPartTags.NEUTRAL;
        private int requiredXPForRote = 100;
        private boolean isSilverSpell = false;
        private String addingModName = null;
        @Nullable
        private Integer tier;
        @Nullable
        private Shape delegate;
        private List<AttributeValuePair> attributeValuePairs = new ArrayList<>();
        private ArrayList<SpellReagent> reagents = new ArrayList<>();

        private TargetCallback targetCallback;
        private TargetNPCCastCallback targetNPCCastCallback;
        private IsCraftableCallback isCraftableCallback;
        private AllowChanneledComponentsCallback allowChanneledComponentsCallback;
        private CanBeCastAtCallback canBeCastAtCallback;
        private MaxChannelTimeCallback maxChannelTimeCallback;

        public Builder(ResourceLocation id) {
            super(id);
        }

        @Override
        public RegistryInfo getRegistryType() {
            return MnaJSPlugin.SPELL_SHAPE.get();
        }

        @Info(value = "Copy the public configuration surface from an existing shape id.", params = {
                @Param(name = "template", value = "Existing shape instance such as MnaShapes.SELF.")
        })
        public Builder basedOn(Shape template) {
            this.delegate = template;
            this.guiIcon = template.getGuiIcon();
            this.initialComplexity = template.initialComplexity();
            this.spawnsTargetEntity = template.spawnsTargetEntity();
            this.isChanneled = template.isChanneled();
            this.allowChanneledComponents = template.allowChanneledComponents();
            this.grantComponentRoteXP = template.grantComponentRoteXP();
            this.baselineCooldown = template.baselineCooldown();
            this.isUseableByPlayers = template.isUseableByPlayers();
            this.affectsCaster = template.affectsCaster();
            this.useTag = template.getUseTag();
            this.requiredXPForRote = template.requiredXPForRote();
            this.isSilverSpell = template.isSilverSpell();
            this.addingModName = template.getAddingModName();
            this.attributeValuePairs.clear();
            this.attributeValuePairs.addAll(template.getModifiableAttributes());
            this.reagents.clear();
            List<SpellReagent> templateReagents = template.getRequiredReagents(null);
            if (templateReagents != null) {
                this.reagents.addAll(templateReagents);
            }
            this.targetCallback = null;
            this.targetNPCCastCallback = null;
            this.isCraftableCallback = null;
            this.allowChanneledComponentsCallback = null;
            this.canBeCastAtCallback = null;
            this.maxChannelTimeCallback = null;
            try {
                this.tier = template.getTier(null);
            } catch (RuntimeException ignored) {
                this.tier = null;
            }
            return this;
        }

        @Info(value = "Set the GUI icon for this spell shape.", params = {
                @Param(name = "icon", value = "Texture id such as mna:textures/gui/guide_book.png.")
        })
        public Builder guiIcon(MnaTexture icon) {
            this.guiIcon = icon.location();
            return this;
        }

        @Info("Sets the initial complexity value for this spell shape")
        public Builder initialComplexity(float complexity) {
            this.initialComplexity = complexity;
            return this;
        }

        @Info("Adds a modifiable attribute to this spell shape")
        public Builder addAttribute(Attribute attribute, float baseValue, float minValue, float maxValue, float stepSize, float complexity) {
            this.attributeValuePairs.add(new AttributeValuePair(attribute, baseValue, minValue, maxValue, stepSize, complexity));
            return this;
        }

        @Info("Sets whether this spell shape spawns a target entity")
        public Builder spawnsTargetEntity(boolean spawns) {
            this.spawnsTargetEntity = spawns;
            return this;
        }

        @Info("Sets whether this spell shape is channeled")
        public Builder isChanneled(boolean channeled) {
            this.isChanneled = channeled;
            return this;
        }

        @Info("Sets whether this spell shape statically allows channeled components")
        public Builder allowsChanneledComponents(boolean allow) {
            this.allowChanneledComponents = allow;
            this.allowChanneledComponentsCallback = null;
            return this;
        }

        @Info("Sets whether this spell shape grants component rote XP")
        public Builder grantComponentRoteXP(boolean grants) {
            this.grantComponentRoteXP = grants;
            return this;
        }

        @Info("Sets the baseline cooldown for this spell shape in ticks")
        public Builder baselineCooldown(int cooldown) {
            this.baselineCooldown = cooldown;
            return this;
        }

        @Info("Sets whether this spell shape is useable by players")
        public Builder isUseableByPlayers(boolean isUseable) {
            this.isUseableByPlayers = isUseable;
            return this;
        }

        @Info("Sets whether this spell shape affects the caster")
        public Builder affectsCaster(boolean affects) {
            this.affectsCaster = affects;
            return this;
        }

        @Info("Sets the use tag for this spell shape (BENEFICIAL, HARMFUL, or NEUTRAL)")
        public Builder useTag(SpellPartTags tag) {
            this.useTag = tag;
            return this;
        }

        @Info("Sets the required XP for rote learning")
        public Builder requiredXPForRote(int xp) {
            this.requiredXPForRote = xp;
            return this;
        }

        @Info("Sets the displayed spell tier for this shape")
        public Builder tier(int tier) {
            this.tier = tier;
            return this;
        }

        @Info("Sets whether this is a silver spell")
        public Builder isSilverSpell(boolean isSilver) {
            this.isSilverSpell = isSilver;
            return this;
        }

        @Info("Sets the mod name that will be displayed as the creator of this spell")
        public Builder addingModName(String modName) {
            this.addingModName = modName;
            return this;
        }

        @Info(value = "Add a reagent requirement to this spell shape.", params = {
                @Param(name = "reagent", value = "Concrete item id required by the spell shape."),
                @Param(name = "compareNBT", value = "Whether the reagent should compare NBT."),
                @Param(name = "ignoreDurability", value = "Whether durability should be ignored."),
                @Param(name = "consume", value = "Whether the reagent should be consumed."),
                @Param(name = "ignoredBy", value = "Factions that can ignore this reagent requirement.")
        })
        public Builder addReagent(MnaItemId reagent, boolean compareNBT, boolean ignoreDurability, boolean consume, MnaFactionId... ignoredBy) {
            SpellReagent reagentEntry = new SpellReagent(
                    null,
                    MnaTypedIdLookups.stack(reagent, "reagent"),
                    compareNBT,
                    ignoreDurability,
                    consume,
                    false,
                    MnaTypedIdLookups.factions(ignoredBy, "ignoredBy")
            );
            this.reagents.add(reagentEntry);
            return this;
        }

        @Info(value = "Add a reagent requirement to this spell shape with default settings.", params = {
                @Param(name = "reagent", value = "Concrete item id required by the spell shape."),
                @Param(name = "ignoredBy", value = "Factions that can ignore this reagent requirement.")
        })
        public Builder addReagent(MnaItemId reagent, MnaFactionId... ignoredBy) {
            return addReagent(reagent, false, false, true, ignoredBy);
        }

        @Info(value = "Add an optional reagent to this spell shape.", params = {
                @Param(name = "reagent", value = "Concrete item id used as an optional reagent."),
                @Param(name = "compareNBT", value = "Whether the reagent should compare NBT."),
                @Param(name = "ignoreDurability", value = "Whether durability should be ignored."),
                @Param(name = "consume", value = "Whether the reagent should be consumed."),
                @Param(name = "ignoredBy", value = "Factions that can ignore this reagent requirement.")
        })
        public Builder addOptionalReagent(MnaItemId reagent, boolean compareNBT, boolean ignoreDurability, boolean consume, MnaFactionId... ignoredBy) {
            SpellReagent reagentEntry = new SpellReagent(
                    null,
                    MnaTypedIdLookups.stack(reagent, "reagent"),
                    compareNBT,
                    ignoreDurability,
                    consume,
                    true,
                    MnaTypedIdLookups.factions(ignoredBy, "ignoredBy")
            );
            this.reagents.add(reagentEntry);
            return this;
        }

        @Info(value = "Add an optional reagent to this spell shape with default settings.", params = {
                @Param(name = "reagent", value = "Concrete item id used as an optional reagent."),
                @Param(name = "ignoredBy", value = "Factions that can ignore this reagent requirement.")
        })
        public Builder addOptionalReagent(MnaItemId reagent, MnaFactionId... ignoredBy) {
            return addOptionalReagent(reagent, false, false, true, ignoredBy);
        }

        @Info("Sets the callback for targeting")
        public Builder target(TargetCallback callback) {
            this.targetCallback = callback;
            return this;
        }

        @Info("Sets the callback for NPC targeting")
        public Builder targetNPCCast(TargetNPCCastCallback callback) {
            this.targetNPCCastCallback = callback;
            return this;
        }

        @Info("Sets the callback for checking if the spell is craftable")
        public Builder isCraftable(IsCraftableCallback callback) {
            this.isCraftableCallback = callback;
            return this;
        }

        @Info("Sets the callback for determining if the spell allows channeled components")
        public Builder allowChanneledComponents(AllowChanneledComponentsCallback callback) {
            this.allowChanneledComponents = null;
            this.allowChanneledComponentsCallback = callback;
            return this;
        }

        @Info("Sets the callback for determining if the spell can be cast at a location")
        public Builder canBeCastAt(CanBeCastAtCallback callback) {
            this.canBeCastAtCallback = callback;
            return this;
        }

        @Info("Sets the callback for determining the max channel time")
        public Builder maxChannelTime(MaxChannelTimeCallback callback) {
            this.maxChannelTimeCallback = callback;
            return this;
        }

        @Override
        public CustomShape createObject() {
            return new CustomShape(this);
        }
    }
}
