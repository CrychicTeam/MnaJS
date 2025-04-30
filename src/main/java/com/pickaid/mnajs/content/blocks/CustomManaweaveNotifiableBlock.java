package com.pickaid.mnajs.content.blocks;

import com.mna.api.blocks.IManaweaveNotifiable;
import com.mna.api.recipes.IManaweavePattern;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class CustomManaweaveNotifiableBlock extends Block implements IManaweaveNotifiable {
    private final Builder builder;

    public CustomManaweaveNotifiableBlock(Builder builder) {
        super(builder.createProperties());
        this.builder = builder;
    }

    @Override
    public boolean notify(Level level, BlockPos pos, BlockState state, List<IManaweavePattern> patterns, @Nullable LivingEntity caster) {
        if (builder.notifyCallback != null) {
            return builder.notifyCallback.apply(level, pos, state, patterns, caster);
        }
        return false;
    }

    @Info("Callback interface for manaweave pattern notification")
    public interface NotifyCallback {
        boolean apply(Level level, BlockPos pos, BlockState state, List<IManaweavePattern> patterns, @Nullable LivingEntity caster);
    }

    public static class Builder extends BlockBuilder {
        private NotifyCallback notifyCallback;

        public Builder(ResourceLocation i) {
            super(i);
        }

        @Info("Sets the callback for manaweave pattern notification")
        public Builder onNotify(NotifyCallback callback) {
            this.notifyCallback = callback;
            return this;
        }

        @Override
        public Block createObject() {
            return new CustomManaweaveNotifiableBlock(this);
        }
    }
}