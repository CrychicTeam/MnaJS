package com.pickaid.mnajs.content;

import com.mna.api.rituals.IRitualContext;
import com.mna.api.rituals.RitualEffect;
import com.pickaid.mnajs.kubejs.id.MnaRitualId;
import com.pickaid.mnajs.kubejs.MnaJSPlugin;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Custom Ritual Effect implementation for KubeJS integration with Mana and Artifice.
 * @author M1hono
 */
public class CustomRitualEffect extends RitualEffect {
    private final Builder builder;

    public CustomRitualEffect(Builder builder) {
        super(builder.ritualName);
        this.builder = builder;
    }

    @Override
    protected boolean applyRitualEffect(IRitualContext context) {
        if (builder.applyEffect != null) {
            return builder.applyEffect.apply(context);
        }
        return false;
    }

    @Override
    protected int getApplicationTicks(IRitualContext context) {
        if (builder.applicationTicks != null) {
            return builder.applicationTicks.apply(context);
        }
        return builder.defaultApplicationTicks;
    }

    @Override
    protected boolean matchReagents(IRitualContext context) {
        if (builder.matchReagentsCallback != null) {
            return builder.matchReagentsCallback.apply(context);
        }
        return super.matchReagents(context);
    }

    @Override
    protected boolean modifyRitualReagentsAndPatterns(ItemStack dataStack, IRitualContext context) {
        if (builder.modifyReagentsCallback != null) {
            return builder.modifyReagentsCallback.apply(dataStack, context);
        }
        return super.modifyRitualReagentsAndPatterns(dataStack, context);
    }

    @Override
    public Component canRitualStart(IRitualContext context) {
        if (builder.canStartCallback != null) {
            return builder.canStartCallback.apply(context);
        }
        return super.canRitualStart(context);
    }

    @Override
    public boolean applyStartCheckInCreative() {
        return builder.applyInCreative;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean spawnRitualParticles(IRitualContext context) {
        if (builder.particlesCallback != null) {
            return builder.particlesCallback.apply(context);
        }
        return super.spawnRitualParticles(context);
    }

    @Override
    public SoundEvent getLoopSound(IRitualContext context) {
        if (builder.loopSoundCallback != null) {
            return builder.loopSoundCallback.apply(context);
        }
        return super.getLoopSound(context);
    }

    @Info("Callback interface for handling the actual ritual effect application.")
    public interface ApplyEffectCallback {
        boolean apply(IRitualContext context);
    }

    @Info("Callback interface for determining how many ticks the ritual takes to complete.")
    public interface ApplicationTicksCallback {
        int apply(IRitualContext context);
    }

    @Info("Callback interface for custom reagent matching logic.")
    public interface MatchReagentsCallback {
        boolean apply(IRitualContext context);
    }

    @Info("Callback interface for modifying ritual reagents and patterns dynamically.")
    public interface ModifyReagentsCallback {
        boolean apply(ItemStack dataStack, IRitualContext context);
    }

    @Info("Callback interface for determining if the ritual can start.")
    public interface CanStartCallback {
        Component apply(IRitualContext context);
    }

    @Info("Callback interface for custom particle spawning logic.")
    public interface ParticlesCallback {
        boolean apply(IRitualContext context);
    }

    @Info("Callback interface for custom loop sound selection.")
    public interface LoopSoundCallback {
        SoundEvent apply(IRitualContext context);
    }

    public static class Builder extends BuilderBase<CustomRitualEffect> {
        private ResourceLocation ritualName = ResourceLocation.fromNamespaceAndPath("mna", "none");
        private ApplyEffectCallback applyEffect;
        private ApplicationTicksCallback applicationTicks;
        private int defaultApplicationTicks = 0;
        private MatchReagentsCallback matchReagentsCallback;
        private ModifyReagentsCallback modifyReagentsCallback;
        private CanStartCallback canStartCallback;
        private boolean applyInCreative = false;
        private ParticlesCallback particlesCallback;
        private LoopSoundCallback loopSoundCallback;

        public Builder(ResourceLocation id, ResourceLocation ritualName) {
            super(id);
            this.ritualName = ritualName;
        }

        public Builder(ResourceLocation id) {
            super(id);
        }

        @Override
        public RegistryInfo getRegistryType() {
            return MnaJSPlugin.RITUAL_EFFECT_REGISTRY.get();
        }

        @Info(value = "Set the ritual id handled by this ritual effect.", params = {
                @Param(name = "name", value = "Ritual recipe id such as mna:rituals/alteration or kubejs:lightning_ritual.")
        })
        public Builder ritualName(MnaRitualId name) {
            this.ritualName = name.location();
            return this;
        }

        @Info("Sets the callback that implements the actual ritual effect logic.")
        public Builder applyEffect(ApplyEffectCallback callback) {
            this.applyEffect = callback;
            return this;
        }

        @Info("Sets the callback that determines how many ticks the ritual takes to complete.")
        public Builder applicationTicks(ApplicationTicksCallback callback) {
            this.applicationTicks = callback;
            return this;
        }

        @Info("Sets a fixed number of ticks for the ritual application.")
        public Builder applicationTicks(int ticks) {
            this.defaultApplicationTicks = ticks;
            return this;
        }

        @Info("Sets the callback for custom reagent matching logic.")
        public Builder matchReagents(MatchReagentsCallback callback) {
            this.matchReagentsCallback = callback;
            return this;
        }

        @Info("Sets the callback for modifying ritual reagents and patterns dynamically.")
        public Builder modifyReagents(ModifyReagentsCallback callback) {
            this.modifyReagentsCallback = callback;
            return this;
        }

        @Info("Sets the callback that determines if the ritual can start.")
        public Builder canStart(CanStartCallback callback) {
            this.canStartCallback = callback;
            return this;
        }

        @Info("Sets whether the start check should apply in creative mode.")
        public Builder applyStartCheckInCreative(boolean apply) {
            this.applyInCreative = apply;
            return this;
        }

        @Info("Sets the callback for custom particle spawning logic.")
        public Builder particles(ParticlesCallback callback) {
            this.particlesCallback = callback;
            return this;
        }

        @Info("Sets the callback for custom loop sound selection.")
        public Builder loopSound(LoopSoundCallback callback) {
            this.loopSoundCallback = callback;
            return this;
        }

        @Override
        public CustomRitualEffect createObject() {
            return new CustomRitualEffect(this);
        }
    }
}
