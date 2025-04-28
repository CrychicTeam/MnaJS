package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonObject;
import com.mna.api.tools.MATags;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class TransmutationBuilder extends MABaseBuilder {
    private ResourceLocation targetBlock;
    private ResourceLocation replaceBlock;
    private ResourceLocation lootTable;
    private ResourceLocation representationItem;

    @Info("Set the target block for the Transmutation recipe")
    public TransmutationBuilder targetBlock(ResourceLocation block) {
        if (MATags.smartLookupBlock(block).isEmpty())
            throw new IllegalStateException("targetBlock must be valid");
        this.targetBlock = block;
        return this;
    }

    @Info("Set the target block for the Transmutation recipe using a Block")
    public TransmutationBuilder targetBlock(Block block) {
        this.targetBlock = ForgeRegistries.BLOCKS.getKey(block);
        return this;
    }

    @Info("Set the target block for the Transmutation recipe using a string")
    public TransmutationBuilder targetBlock(String block) {
        return targetBlock(new ResourceLocation(block));
    }

    @Info("Set the replacement block for the Transmutation recipe")
    public TransmutationBuilder replaceBlock(ResourceLocation block) {
        if (MATags.smartLookupBlock(block).isEmpty())
            throw new IllegalStateException("replaceBlock must be valid");
        return this;
    }

    @Info("Set the replacement block for the Transmutation recipe using a Block")
    public TransmutationBuilder replaceBlock(Block block) {
        this.replaceBlock = ForgeRegistries.BLOCKS.getKey(block);
        return this;
    }

    @Info("Set the replacement block for the Transmutation recipe using a string")
    public TransmutationBuilder replaceBlock(String block) {
        return replaceBlock(new ResourceLocation(block));
    }

    @Info("Set the loot table for the Transmutation recipe")
    public TransmutationBuilder lootTable(ResourceLocation lootTable) {
        this.lootTable = lootTable;
        return this;
    }

    @Info("Set the loot table for the Transmutation recipe using a string")
    public TransmutationBuilder lootTable(String lootTable) {
        this.lootTable = new ResourceLocation(lootTable);
        return this;
    }

    @Info("Set the representation item for the Transmutation recipe")
    public TransmutationBuilder representationItem(ResourceLocation item) {
        this.representationItem = MATags.lookupItem(item).getItem().kjs$getIdLocation();
        return this;
    }

    @Info("Set the representation item for the Transmutation recipe using an Item")
    public TransmutationBuilder representationItem(Item item) {
        this.representationItem = ForgeRegistries.ITEMS.getKey(item);
        return this;
    }

    @Info("Set the representation item for the Transmutation recipe using a string")
    public TransmutationBuilder representationItem(String item) {
        return representationItem(new ResourceLocation(item));
    }

    @Info("get the JsonObject for event.custom()")
    public JsonObject build() {
        JsonObject json = super.build();
        json.addProperty("type", "mna:transmutation");
        if (targetBlock == null) {
            throw new IllegalStateException("Target block cannot be null");
        } else {
            json.addProperty("targetBlock", targetBlock.toString());
        }
        if (replaceBlock == null && lootTable == null) {
            throw new IllegalStateException("Either replace block or loot table must be specified");
        }

        if (replaceBlock != null) {
            json.addProperty("replaceBlock", replaceBlock.toString());
        }

        if (lootTable != null) {
            json.addProperty("lootTable", lootTable.toString());

            if (representationItem == null) {
                throw new IllegalStateException("Representation item must be specified when using loot table");
            }
            json.addProperty("representationItem", representationItem.toString());
        } else if (representationItem != null) {
            json.addProperty("representationItem", representationItem.toString());
        }

        return json;
    }
}