package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.pickaid.mnajs.kubejs.id.MnaMobEffectId;
import com.pickaid.mnajs.kubejs.id.MnaTypedIdLookups;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.ForgeRegistries;

public class ManaweaveCacheEffectBuilder extends MABaseBuilder {
    private MobEffect effect;
    private int magnitude = 1;
    private int durationMin = 0;
    private int durationMax = 0;

    @Info(value = "Set the mob effect applied by this manaweave cache effect recipe.", params = {
            @Param(name = "effect", value = "Mob effect id such as minecraft:speed.")
    })
    public ManaweaveCacheEffectBuilder effect(MnaMobEffectId effect) {
        this.effect = MnaTypedIdLookups.requireMobEffect(effect, "effect");
        return this;
    }

    @Info("Set the magnitude for the Manaweave Cache Effect recipe")
    public ManaweaveCacheEffectBuilder magnitude(int magnitude) {
        this.magnitude = magnitude;
        return this;
    }

    @Info("Set the minimum duration for the Manaweave Cache Effect recipe in seconds")
    public ManaweaveCacheEffectBuilder durationMin(int durationMin) {
        this.durationMin = durationMin;
        return this;
    }

    @Info("Set the maximum duration for the Manaweave Cache Effect recipe in seconds")
    public ManaweaveCacheEffectBuilder durationMax(int durationMax) {
        this.durationMax = durationMax;
        return this;
    }

    @Info("Set both minimum and maximum duration to the same value in seconds")
    public ManaweaveCacheEffectBuilder duration(int duration) {
        this.durationMin = duration;
        this.durationMax = duration;
        return this;
    }

    @Info("Set the duration range for the Manaweave Cache Effect recipe in seconds")
    public ManaweaveCacheEffectBuilder durationRange(int min, int max) {
        this.durationMin = min;
        this.durationMax = max;
        return this;
    }

    @Info("get the JsonObject for event.custom()")
    public JsonObject build() {
        JsonObject json = super.build();
        json.addProperty("type", "mna:manaweave-cache-effect");

        if (effect == null) {
            throw new IllegalStateException("Effect cannot be null");
        }

        ResourceLocation effectId = ForgeRegistries.MOB_EFFECTS.getKey(effect);
        if (effectId == null) {
            throw new IllegalStateException("Effect id cannot be resolved");
        }
        json.addProperty("effect", effectId.toString());

        if (magnitude != 1) {
            json.addProperty("magnitude", magnitude);
        }

        if (durationMin < 0) {
            durationMin = 0;
        }

        if (durationMax < 0) {
            durationMax = 0;
        }

        if (durationMax < durationMin) {
            int temp = durationMin;
            durationMin = durationMax;
            durationMax = temp;
        }

        json.addProperty("duration_min", durationMin);
        json.addProperty("duration_max", durationMax);

        if (durationMin == 0) {
            System.err.println("Warning: Manaweave cache effect recipe has a duration minimum of 0, this may cause problems!");
        }

        if (durationMax == 0) {
            System.err.println("Warning: Manaweave cache effect recipe has a duration maximum of 0, this may cause problems!");
        }

        return json;
    }
}
