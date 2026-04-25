package com.pickaid.mnajs.recipes.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pickaid.mnajs.recipes.builders.base.MABaseBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class MultiblockDefinitionBuilder extends MABaseBuilder {
    private ResourceLocation structure;
    private final List<BlockMatcherEntry> blockMatchers = new ArrayList<>();
    private final List<VariationEntry> variations = new ArrayList<>();
    private final List<TagEntry> tags = new ArrayList<>();
    private final List<ResourceLocation> rawBlockChecks = new ArrayList<>();
    private boolean symmetrical = false;

    public static final String STAIRS_MATCHER = "mna:stairs";
    public static final String STATELESS_MATCHER = "mna:stateless";
    public static final String CHALK_MATCHER = "mna:wizard_chalk";
    public static final String REFRACTION_LENS_MATCHER = "mna:refraction_lens";
    public static final String PEDESTAL_MATCHER = "mna:pedestal";

    public static class BlockMatcherEntry {
        private final ResourceLocation matcher;
        private final Object identifier; // Either Long (offset) or ResourceLocation (block)
        private final boolean isOffset;

        public BlockMatcherEntry(ResourceLocation matcher, long offset) {
            this.matcher = matcher;
            this.identifier = offset;
            this.isOffset = true;
        }

        public BlockMatcherEntry(ResourceLocation matcher, ResourceLocation block) {
            this.matcher = matcher;
            this.identifier = block;
            this.isOffset = false;
        }

        public JsonObject toJson() {
            JsonObject json = new JsonObject();
            json.addProperty("matcher", matcher.toString());
            if (isOffset) {
                json.addProperty("offset", (Long) identifier);
            } else {
                json.addProperty("block", ((ResourceLocation) identifier).toString());
            }
            return json;
        }
    }

    public static class VariationEntry {
        private final String identifier;
        private final List<ReplacementData> replacementData = new ArrayList<>();

        public VariationEntry(String identifier) {
            this.identifier = identifier;
        }

        public VariationEntry addReplacement(BlockPos offset, BlockState state) {
            this.replacementData.add(new ReplacementData(offset, state));
            return this;
        }

        public VariationEntry addReplacement(int x, int y, int z, Block block) {
            return addReplacement(new BlockPos(x, y, z), block.defaultBlockState());
        }

        public VariationEntry addReplacement(int x, int y, int z, String blockId) {
            Block block = ForgeRegistries.BLOCKS.getValue(ResourceLocation.parse(blockId));
            if (block == null) {
                throw new IllegalArgumentException("Unknown block: " + blockId);
            }
            return addReplacement(x, y, z, block);
        }

        public JsonObject toJson() {
            JsonObject json = new JsonObject();
            json.addProperty("id", identifier);

            JsonArray dataArray = new JsonArray();
            for (ReplacementData data : replacementData) {
                dataArray.add(data.toJson());
            }
            json.add("data", dataArray);

            return json;
        }
    }

    public static class ReplacementData {
        private final BlockPos offset;
        private final BlockState state;

        public ReplacementData(BlockPos offset, BlockState state) {
            this.offset = offset;
            this.state = state;
        }

        public JsonObject toJson() {
            JsonObject json = new JsonObject();

            JsonObject offsetJson = new JsonObject();
            offsetJson.addProperty("X", offset.getX());
            offsetJson.addProperty("Y", offset.getY());
            offsetJson.addProperty("Z", offset.getZ());
            json.add("offset", offsetJson);

            JsonObject stateJson = new JsonObject();
            stateJson.addProperty("name", ForgeRegistries.BLOCKS.getKey(state.getBlock()).toString());

            if (!state.getProperties().isEmpty()) {
                JsonObject propertiesJson = new JsonObject();
                state.getValues().forEach((property, value) -> {
                    propertiesJson.addProperty(property.getName(), value.toString());
                });
                stateJson.add("properties", propertiesJson);
            }

            json.add("state", stateJson);

            return json;
        }
    }

    public static class TagEntry {
        private final ResourceLocation block;
        private final ResourceLocation tag;

        public TagEntry(ResourceLocation block, ResourceLocation tag) {
            this.block = block;
            this.tag = tag;
        }

        public JsonObject toJson() {
            JsonObject json = new JsonObject();
            json.addProperty("block", block.toString());
            json.addProperty("tag", tag.toString());
            return json;
        }
    }

    @Info("Set the structure file path for the Multiblock Definition")
    public MultiblockDefinitionBuilder structure(ResourceLocation structure) {
        this.structure = structure;
        return this;
    }

    @Info("Set the structure file path for the Multiblock Definition using a string")
    public MultiblockDefinitionBuilder structure(String structure) {
        return structure(ResourceLocation.parse(structure));
    }

    @Info("Set whether the multiblock is symmetrical (can be rotated)")
    public MultiblockDefinitionBuilder symmetrical(boolean symmetrical) {
        this.symmetrical = symmetrical;
        return this;
    }

    @Info("Add a tag entry to the multiblock structure")
    public MultiblockDefinitionBuilder addTag(String block, String tag) {
        this.tags.add(new TagEntry(
                ResourceLocation.parse(block),
                ResourceLocation.parse(tag)
        ));
        return this;
    }

    @Info("Add a tag entry to the multiblock structure")
    public MultiblockDefinitionBuilder addTag(Block block, ResourceLocation tag) {
        this.tags.add(new TagEntry(
                ForgeRegistries.BLOCKS.getKey(block),
                tag
        ));
        return this;
    }

    @Info("Add a raw block check to the multiblock structure")
    public MultiblockDefinitionBuilder addRawBlockCheck(String blockId) {
        this.rawBlockChecks.add(ResourceLocation.parse(blockId));
        return this;
    }

    @Info("Add a raw block check to the multiblock structure")
    public MultiblockDefinitionBuilder addRawBlockCheck(Block block) {
        this.rawBlockChecks.add(ForgeRegistries.BLOCKS.getKey(block));
        return this;
    }

    @Info("Add a block matcher by position offset")
    public MultiblockDefinitionBuilder addBlockMatcher(String matcherType, long offset) {
        this.blockMatchers.add(new BlockMatcherEntry(ResourceLocation.parse(matcherType), offset));
        return this;
    }

    @Info("Add a block matcher by block type")
    public MultiblockDefinitionBuilder addBlockMatcher(String matcherType, Block block) {
        this.blockMatchers.add(new BlockMatcherEntry(
                ResourceLocation.parse(matcherType),
                ForgeRegistries.BLOCKS.getKey(block)
        ));
        return this;
    }

    @Info("Add a block matcher by block identifier")
    public MultiblockDefinitionBuilder addBlockMatcher(String matcherType, String blockId) {
        this.blockMatchers.add(new BlockMatcherEntry(
                ResourceLocation.parse(matcherType),
                ResourceLocation.parse(blockId)
        ));
        return this;
    }

    @Info("Add a variation to the multiblock definition")
    public MultiblockDefinitionBuilder addVariation(VariationEntry variation) {
        this.variations.add(variation);
        return this;
    }

    @Info("Create a new variation builder for this multiblock")
    public VariationEntry createVariation(String identifier) {
        VariationEntry variation = new VariationEntry(identifier);
        this.variations.add(variation);
        return variation;
    }

    @Info("Add stairs matcher for a specific block")
    public MultiblockDefinitionBuilder addStairsMatcher(Block block) {
        return addBlockMatcher(STAIRS_MATCHER, block);
    }

    @Info("Add stateless matcher for a specific block")
    public MultiblockDefinitionBuilder addStatelessMatcher(Block block) {
        return addBlockMatcher(STATELESS_MATCHER, block);
    }

    @Info("Add chalk matcher for a specific block")
    public MultiblockDefinitionBuilder addChalkMatcher(Block block) {
        return addBlockMatcher(CHALK_MATCHER, block);
    }

    @Info("Add refraction lens matcher for a specific block")
    public MultiblockDefinitionBuilder addRefractionLensMatcher(Block block) {
        return addBlockMatcher(REFRACTION_LENS_MATCHER, block);
    }

    @Info("Add pedestal matcher for a specific block")
    public MultiblockDefinitionBuilder addPedestalMatcher(Block block) {
        return addBlockMatcher(PEDESTAL_MATCHER, block);
    }

    @Info("get the JsonObject for event.custom()")
    public JsonObject build() {
        JsonObject json = super.build();
        json.addProperty("type", "mna:multiblock");

        if (structure == null) {
            throw new IllegalStateException("Structure path must be set");
        }

        json.addProperty("structure", structure.toString());

        if (tier != 1) {
            json.addProperty("tier", tier);
        }

        if (symmetrical) {
            json.addProperty("symmetrical", true);
        }

        if (!blockMatchers.isEmpty()) {
            JsonArray matchersArray = new JsonArray();
            for (BlockMatcherEntry entry : blockMatchers) {
                matchersArray.add(entry.toJson());
            }
            json.add("matchers", matchersArray);
        }

        if (!variations.isEmpty()) {
            JsonArray variationsArray = new JsonArray();
            for (VariationEntry entry : variations) {
                variationsArray.add(entry.toJson());
            }
            json.add("replacements", variationsArray);
        }

        if (!rawBlockChecks.isEmpty()) {
            JsonArray rawChecksArray = new JsonArray();
            for (ResourceLocation block : rawBlockChecks) {
                rawChecksArray.add(block.toString());
            }
            json.add("rawBlockChecks", rawChecksArray);
        }

        if (!tags.isEmpty()) {
            JsonArray tagsArray = new JsonArray();
            for (TagEntry tag : tags) {
                tagsArray.add(tag.toJson());
            }
            json.add("tags", tagsArray);
        }

        return json;
    }
}
