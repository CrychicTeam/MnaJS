package com.crychicteam.mnajs.mixin;

import com.crychicteam.mnajs.kubejs.MnaJSEvents;
import com.crychicteam.mnajs.kubejs.events.server.ManaChangedEventJS;
import com.mna.api.capabilities.resource.SimpleCastingResource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SimpleCastingResource.class)
public class CastingResourceMixin {

    @Inject(method = "consume", at = @At("HEAD"), cancellable = true, remap = false)
    private void mnaJs$consume(LivingEntity caster, float amount, CallbackInfo ci) {
        if (caster instanceof Player player) {
           var event = MnaJSEvents.MANA_CHANGED.post(new ManaChangedEventJS(player, amount));
           if (event.interruptFalse()) {
               ci.cancel();
           }
        }
    }
}
