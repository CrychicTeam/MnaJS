package com.pickaid.mnajs.kubejs.recipe;

import com.pickaid.mnajs.kubejs.id.MnaBlockId;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaLootTableId;
import com.pickaid.mnajs.recipes.schema.TransmutationSchema;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;

public final class TransmutationRecipeJS extends MnaBaseRecipeJS<TransmutationRecipeJS> {
    public TransmutationRecipeJS targetBlock(MnaBlockId value) {
        return setKey("targetBlock", value);
    }

    @HideFromJS
    public TransmutationRecipeJS targetBlock(String value) {
        return targetBlock(MnaBlockId.parse(value));
    }

    public TransmutationRecipeJS replaceBlock(MnaBlockId value) {
        return setKey("replaceBlock", value);
    }

    @HideFromJS
    public TransmutationRecipeJS replaceBlock(String value) {
        return replaceBlock(MnaBlockId.parse(value));
    }

    public TransmutationRecipeJS lootTable(MnaLootTableId value) {
        return setKey("lootTable", value);
    }

    @HideFromJS
    public TransmutationRecipeJS lootTable(ResourceLocation value) {
        return lootTable(MnaLootTableId.of(value));
    }

    @HideFromJS
    public TransmutationRecipeJS lootTable(String value) {
        return lootTable(MnaLootTableId.parse(value));
    }

    public TransmutationRecipeJS representationItem(MnaItemId value) {
        return setKey("representationItem", value);
    }

    @HideFromJS
    public TransmutationRecipeJS representationItem(String value) {
        return representationItem(MnaItemId.parse(value));
    }

    @Override
    protected void validateRecipe() {
        MnaBlockId targetBlock = getValue(TransmutationSchema.TARGET_BLOCK);
        MnaBlockId replaceBlock = getValue(TransmutationSchema.REPLACE_BLOCK);
        MnaLootTableId lootTable = getValue(TransmutationSchema.LOOTTABLE);
        MnaItemId representationItem = getValue(TransmutationSchema.REPRESENTATION_ITEM);

        require(targetBlock != null, "Transmutation recipe targetBlock must be set");
        require(replaceBlock != null || lootTable != null,
                "Transmutation recipe must define either replaceBlock or lootTable");
        require(lootTable == null || representationItem != null,
                "Transmutation recipe with lootTable must also define representationItem");
    }
}
