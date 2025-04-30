package com.pickaid.mnajs.content.blocks;

import com.mna.api.blocks.ISpellInteractibleBlock;
import com.mna.api.spells.base.ISpellDefinition;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class CustomSpellInteractibleBlock extends Block implements ISpellInteractibleBlock<CustomSpellInteractibleBlock> {
    private final Builder builder;

    public CustomSpellInteractibleBlock(Builder builder) {
        super(builder.createProperties());
        this.builder = builder;
    }

    @Override
    public boolean onHitBySpell(Level level, BlockPos pos, ISpellDefinition spell) {
        if (builder.onHitBySpellCallback != null) {
            return builder.onHitBySpellCallback.apply(level, pos, spell);
        }
        return false;
    }

    @Info("Callback interface for spell interaction")
    public interface OnHitBySpellCallback {
        boolean apply(Level level, BlockPos pos, ISpellDefinition spell);
    }

    public static class Builder extends BlockBuilder {
        private OnHitBySpellCallback onHitBySpellCallback;

        public Builder(ResourceLocation id) {
            super(id);
        }

        @Override
        public CustomSpellInteractibleBlock createObject() {
            return new CustomSpellInteractibleBlock(this);
        }

        @Info("Sets the callback for when this block is hit by a spell")
        public Builder onHitBySpell(OnHitBySpellCallback callback) {
            this.onHitBySpellCallback = callback;
            return this;
        }
    }
}