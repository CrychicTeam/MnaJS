// priority: 0

console.info("[MnaJS] Loaded client examples");

const ENABLE_MNA_CLIENT_EXAMPLES = false;

// MnaJS does not add dedicated client-only events.
// The helpers below show how to use the client bindings that MnaJS exposes.

function exampleClientPatternHelpers() {
    return {
        ritual: MnaPatternHelper.ritualRows("0 1 0", "1 1 1", "0 1 0"),
        reagents: MnaPatternHelper.reagentRows(" A ", "BCD", " A "),
        manaweave: MnaPatternHelper.manaweaveGrid(),
        filledManaweave: MnaPatternHelper.manaweaveFilled(1)
    };
}

function exampleGuiRendering(guiGraphics, manaweavePattern) {
    GuiRenderUtils.renderSilverSpellBorder(guiGraphics, 8, 8, 160, 48);
    GuiRenderUtils.renderStandardPlayerInventory(guiGraphics, 8, 64);
    GuiRenderUtils.renderManaweavePattern(guiGraphics, 110, 84, 1.0, manaweavePattern);
}

function exampleWorldRendering(level, poseStack, bufferSource, fromVec, toVec, renderType, entity, manaweavePattern, quaternion) {
    WorldRenderUtils.renderRadiant(entity, poseStack, bufferSource, [255, 255, 64], [255, 224, 96], 0x00F000F0, 0.25);
    WorldRenderUtils.renderBeam(level, 0.0, poseStack, bufferSource, 0x00F000F0, fromVec, toVec, 0.25, [255, 255, 64], renderType);
    WorldRenderUtils.renderManaweavePattern(manaweavePattern, quaternion, poseStack, bufferSource, false);
}

function exampleClientParticles(level, position, itemNbt) {
    ParticleConfigurations.ArcaneParticleBurst(level, position);
    ParticleConfigurations.ItemPullParticle(level, position, itemNbt);
}

if (ENABLE_MNA_CLIENT_EXAMPLES) {
    console.info("[MnaJS] Client helper examples are enabled. Call the functions in this file from your own UI or render hooks.");
}
