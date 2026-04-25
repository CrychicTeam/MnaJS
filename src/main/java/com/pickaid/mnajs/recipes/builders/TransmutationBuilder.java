package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.pickaid.mnajs.kubejs.id.MnaBlockId;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaLootTableId;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.rhino.util.HideFromJS;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class TransmutationBuilder extends MABaseBuilder {
    private MnaBlockId targetBlock;
    private MnaBlockId replaceBlock;
    private MnaLootTableId lootTable;
    private MnaItemId representationItem;

    @Info("Set the target block for the Transmutation recipe")
    public TransmutationBuilder targetBlock(MnaBlockId block) {
        if (!ForgeRegistries.BLOCKS.containsKey(block.location())) {
            throw new IllegalStateException("targetBlock must be valid");
        }
        this.targetBlock = block;
        return this;
    }

    @HideFromJS
    public TransmutationBuilder targetBlock(ResourceLocation block) {
        return targetBlock(MnaBlockId.of(block));
    }

    @HideFromJS
    public TransmutationBuilder targetBlock(Block block) {
        return targetBlock(MnaBlockId.parse(block));
    }

    @HideFromJS
    public TransmutationBuilder targetBlock(String block) {
        return targetBlock(MnaBlockId.parse(block));
    }

    @Info("Set the replacement block for the Transmutation recipe")
    public TransmutationBuilder replaceBlock(MnaBlockId block) {
        if (!ForgeRegistries.BLOCKS.containsKey(block.location())) {
            throw new IllegalStateException("replaceBlock must be valid");
        }
        this.replaceBlock = block;
        return this;
    }

    @HideFromJS
    public TransmutationBuilder replaceBlock(ResourceLocation block) {
        return replaceBlock(MnaBlockId.of(block));
    }

    @HideFromJS
    public TransmutationBuilder replaceBlock(Block block) {
        return replaceBlock(MnaBlockId.parse(block));
    }

    @HideFromJS
    public TransmutationBuilder replaceBlock(String block) {
        return replaceBlock(MnaBlockId.parse(block));
    }

    @Info("Set the loot table for the Transmutation recipe")
    public TransmutationBuilder lootTable(MnaLootTableId lootTable) {
        this.lootTable = lootTable;
        return this;
    }

    @HideFromJS
    public TransmutationBuilder lootTable(ResourceLocation lootTable) {
        return lootTable(MnaLootTableId.of(lootTable));
    }

    @HideFromJS
    public TransmutationBuilder lootTable(String lootTable) {
        return lootTable(MnaLootTableId.parse(lootTable));
    }

    @Info("Set the representation item for the Transmutation recipe")
    public TransmutationBuilder representationItem(MnaItemId item) {
        if (!ForgeRegistries.ITEMS.containsKey(item.location())) {
            throw new IllegalStateException("representationItem must be valid");
        }
        this.representationItem = item;
        return this;
    }

    @HideFromJS
    public TransmutationBuilder representationItem(ResourceLocation item) {
        return representationItem(MnaItemId.of(item));
    }

    @HideFromJS
    public TransmutationBuilder representationItem(Item item) {
        return representationItem(MnaItemId.parse(item));
    }

    @HideFromJS
    public TransmutationBuilder representationItem(String item) {
        return representationItem(MnaItemId.parse(item));
    }

    @Info("get the JsonObject for event.custom()")
    public JsonObject build() {
        JsonObject json = super.build();
        json.addProperty("type", "mna:transmutation");
        if (targetBlock == null) {
            throw new IllegalStateException("Target block cannot be null");
        } else {
            json.addProperty("targetBlock", targetBlock.id());
        }
        if (replaceBlock == null && lootTable == null) {
            throw new IllegalStateException("Either replace block or loot table must be specified");
        }

        if (replaceBlock != null) {
            json.addProperty("replaceBlock", replaceBlock.id());
        }

        if (lootTable != null) {
            json.addProperty("lootTable", lootTable.id());

            if (representationItem == null) {
                throw new IllegalStateException("Representation item must be specified when using loot table");
            }
            json.addProperty("representationItem", representationItem.id());
        } else if (representationItem != null) {
            json.addProperty("representationItem", representationItem.id());
        }

        return json;
    }
}
