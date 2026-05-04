// priority: 0

console.info("[MnaJS] Loaded server examples");

const ENABLE_MNA_SERVER_EXAMPLES = true;

function exampleCommonMnaBindings(player, level) {
    const magic = PlayerMagic.of(player);
    const progression = PlayerProgression.of(player);
    const manaBefore = magic == null ? 0.0 : magic.getMana();
    const castingResource = magic == null ? null : magic.getCastingResource();
    const worldMagic = WorldMagic.of(level);

    if (magic != null) {
        magic.addMana(10);
        magic.subtractMana(5);
        magic.setMana(manaBefore);
    }

    if (worldMagic != null) {
        worldMagic.addNode(player.blockPosition(), Affinity.ARCANE, 8.0, false);
        const arcanePower = worldMagic.getPower(player, Affinity.ARCANE);
        if (arcanePower != null) {
            worldMagic.addPower(player, Affinity.ARCANE, 1.0);
            worldMagic.subtractPower(player, Affinity.ARCANE, 1.0);
            worldMagic.setPower(player, Affinity.ARCANE, arcanePower);
        }
    }

    const ritualGrid = MnaPatternHelper.ritualRows("0 1 0", "1 1 1", "0 1 0");
    const reagentGrid = MnaPatternHelper.reagentRows(" A ", "BCD", " A ");
    const manaweaveGrid = MnaPatternHelper.manaweaveGrid();
    const filledManaweave = MnaPatternHelper.manaweaveFilled(1);
    const blankRitualGrid = MnaPatternHelper.ritualGrid(5);
    const blankReagents = MnaPatternHelper.reagentGrid(5);
    const genericGrid = MnaPatternHelper.filledGrid(2, 4, 7);
    const randomAffinity = CollectionUtils.getRandom([
        Affinity.ARCANE,
        Affinity.FIRE,
        Affinity.WIND
    ]);
    const clampedCost = MathUtils.clamp(12.5, 0.0, 20.0);
    const smoothed = MathUtils.lerpf(0.0, 1.0, 0.5);

    const ResourceLocation = Java.loadClass(
        "net.minecraft.resources.ResourceLocation"
    );
    const ironTag = new ResourceLocation(
        "forge",
        "ingots/iron"
    );
    const logTag = new ResourceLocation("minecraft", "logs");
    const allBiomes = BiomeUtils.getAllBiomeIDs(level);
    const allStructures = StructureUtils.getAllStructureIDs(level);
    const insideVillage = StructureUtils.isPointInStructure(
        level,
        player.blockPosition(),
        new ResourceLocation("minecraft", "village_plains"),
        1
    );
    const tagMatch = MATags.isItemEqual(player.getMainHandItem(), ironTag);
    const tagItems = MATags.getItemTagContents(ironTag);
    const tagBlocks = MATags.getBlockTagContents(logTag);

    return {
        progression: progression,
        castingResource: castingResource,
        ritualGrid: ritualGrid,
        reagentGrid: reagentGrid,
        manaweaveGrid: manaweaveGrid,
        filledManaweave: filledManaweave,
        blankRitualGrid: blankRitualGrid,
        blankReagents: blankReagents,
        genericGrid: genericGrid,
        randomAffinity: randomAffinity,
        clampedCost: clampedCost,
        smoothed: smoothed,
        allBiomes: allBiomes,
        allStructures: allStructures,
        insideVillage: insideVillage,
        tagMatch: tagMatch,
        tagItems: tagItems,
        tagBlocks: tagBlocks
    };
}

function exampleInventoryAndRecipeUtilities(
    level,
    itemHandler,
    sampleStack,
    craftingInputs
) {
    return {
        hasRoom: InventoryUtilities.hasRoomForAny(itemHandler, sampleStack),
        recipe: RecipeUtil.lookupCraftingRecipe(level, craftingInputs)
    };
}

function exampleProjectileHelpers(caster, projectile) {
    ProjectileHelper.ReflectProjectile(caster, projectile, false, 1.0);
}

function exampleEntityAndSummonHelpers(
    level,
    player,
    origin,
    direction,
    predicate
) {
    const nearbyEntities = EntityUtil.getEntitiesWithinCone(
        level,
        origin,
        direction,
        12.0,
        45.0,
        16.0,
        predicate
    );
    const summons = SummonUtils.getSummons(player);
    SummonUtils.setBonusSummons(player, 2);
    return {
        nearbyEntities: nearbyEntities,
        summons: summons
    };
}

function exampleShearingAndManaItems(level, blockState, pos, stack) {
    return {
        canShear: ShearHelper.canBlockBeSheared(level, blockState, pos),
        charges: ManaItemUtil.getCharges(stack)
    };
}

function examplePresentationAndRaid(level, player, sampleStack) {
    const Vec3 = Java.loadClass("net.minecraft.world.phys.Vec3");
    const itemEntity = new MnaEntityHelper().createPresentItemEntity(
        level,
        player.getX(),
        player.getY() + 1.0,
        player.getZ(),
        sampleStack
    );
    const spawnedRaid = new MnaFactionRaidHelper().spawnRaidAt(
        player,
        MnaFactionUtil.COUNCIL,
        1,
        new Vec3(player.getX(), player.getY(), player.getZ()),
        false
    );
    return {
        itemEntity: itemEntity,
        spawnedRaid: spawnedRaid
    };
}

function exampleEventCustomBuilderUsage(event) {
    event.recipes.mna
        .progression()
        .advancement("minecraft:story/root")
        .desc("kubejs.progression.mna_example")
        .tier(1)
        .faction("mna:none");

    event.custom({
        type: "mna:multiblock",
        structure: "kubejs:verdant_shrine",
        symmetrical: true,
        matchers: [
            {
                matcher: "mna:stairs",
                block: "minecraft:stone_brick_stairs"
            }
        ],
        replacements: [
            {
                id: "lit",
                data: [
                    {
                        offset: {
                            X: 0,
                            Y: 0,
                            Z: 0
                        },
                        state: {
                            name: "minecraft:sea_lantern"
                        }
                    }
                ]
            }
        ],
        tags: [],
        rawBlockChecks: ["minecraft:gold_block"]
    });
}

MnaEvent.wanderingWizardSelectingTrade((event) => {
    if (!ENABLE_MNA_SERVER_EXAMPLES) {
        return;
    }

    const wizard = event.getWanderingWizard();
    const offers = event.getOffers();
    const newTrades = event.getNewTrades();
    const maxTrades = event.getMaxNumbers();
    console.info(
        "[MnaJS] Wizard trade selection",
        wizard,
        offers,
        newTrades,
        maxTrades
    );
});

MnaPlayerEvent.ritualCompleteEvent((event) => {
    if (!ENABLE_MNA_SERVER_EXAMPLES) {
        return;
    }

    const player = event.getEntity();
    const level = player.level();
    const info = exampleCommonMnaBindings(player, level);
    console.info(
        "[MnaJS] Ritual completed at",
        event.getCenter(),
        event.getRitual(),
        info
    );
});

MnaPlayerEvent.affinityChangedEvent((event) => {
    if (!ENABLE_MNA_SERVER_EXAMPLES) {
        return;
    }

    if (event.getAffinity() === Affinity.ARCANE) {
        event.setShift(event.getShift() * 1.25);
    }
});

MnaPlayerEvent.genericProgression((event) => {
    if (!ENABLE_MNA_SERVER_EXAMPLES) {
        return;
    }

    if (event.getId().id() === ProgressionEvents.OPEN_CACHE.id()) {
        console.info("[MnaJS] Player opened a cache", event.getEntity());
    }
});

MnaPlayerEvent.levelUp((event) => {
    if (!ENABLE_MNA_SERVER_EXAMPLES) {
        return;
    }

    console.info(
        "[MnaJS] New magic level",
        event.getEntity(),
        event.getMagicLevel()
    );
});

MnaPlayerEvent.magicXPGained((event) => {
    if (!ENABLE_MNA_SERVER_EXAMPLES) {
        return;
    }

    event.setAmount(event.getAmount() + 5);
});

MnaPlayerEvent.masteryGained((event) => {
    if (!ENABLE_MNA_SERVER_EXAMPLES) {
        return;
    }

    event.setAmount(event.getAmount() + 0.25);
});

MnaPlayerEvent.roteProgression((event) => {
    if (!ENABLE_MNA_SERVER_EXAMPLES) {
        return;
    }

    event.setAmount(event.getAmount() + 0.1);
});

SpellEvent.costingMana((event) => {
    if (!ENABLE_MNA_SERVER_EXAMPLES) {
        return;
    }

    event.setCost(Math.max(0, event.getCost() - 2));
});

SpellEvent.casted((event) => {
    if (!ENABLE_MNA_SERVER_EXAMPLES) {
        return;
    }

    const entity = event.getEntity();
    if (
        entity != null &&
        entity.getType().toString() !== "minecraft:armor_stand"
    ) {
        console.info(
            "[MnaJS] Spell cast",
            event.getSpell(),
            event.getSource(),
            event.getContext(),
            event.getStack()
        );
    }
});

SpellEvent.calculatingCooldown((event) => {
    if (!ENABLE_MNA_SERVER_EXAMPLES) {
        return;
    }

    event.setCooldown(Math.max(0, event.getCooldown() - 10));
});

SpellEvent.componentApplying((event) => {
    if (!ENABLE_MNA_SERVER_EXAMPLES) {
        return;
    }

    console.info(
        "[MnaJS] Component applying",
        event.getComponent(),
        event.getSource(),
        event.getTarget(),
        event.getContext()
    );
});

RuneForgeEvent.shouldActivate((event) => {
    if (!ENABLE_MNA_SERVER_EXAMPLES) {
        return;
    }

    console.info(
        "[MnaJS] Runeforge activation check",
        event.getPattern(),
        event.getMaterial()
    );
});

RuneForgeEvent.itemUsed((event) => {
    if (!ENABLE_MNA_SERVER_EXAMPLES) {
        return;
    }

    console.info(
        "[MnaJS] Runeforge item used",
        event.getEntity(),
        event.getPattern(),
        event.getMaterial(),
        event.getCatalyst()
    );
});

ServerEvents.recipes((event) => {
    if (!ENABLE_MNA_SERVER_EXAMPLES) {
        return;
    }

    event.recipes.mna
        .ritual()
        .tier(1)
        .patternRows("1")
        .displayPatternRows("1")
        .reagentRows("A")
        .reagent(MnaRitualReagent.of("A", "minecraft:amethyst_shard"))
        .innerColor(0xffd54f)
        .outerColor(0xfff176)
        .beamColor(0xfbc02d)
        .connectBeam(true)
        .outputItem("minecraft:lightning_rod")
        .id("kubejs:lightning_ritual");

    event.recipes.mna
        .crushing()
        .input("minecraft:amethyst_block")
        .output("minecraft:amethyst_shard")
        .outputQuantity(4)
        .tier(1);

    event.recipes.mna
        .arcaneFurnace()
        .input("minecraft:raw_gold")
        .output("minecraft:gold_ingot")
        .burnTime(200)
        .outputQuantity(1)
        .tier(1);

    event.recipes.mna
        .runeforging()
        .pattern("minecraft:stick")
        .material("minecraft:gold_ingot")
        .output("minecraft:blaze_rod")
        .hits(6)
        .outputQuantity(1)
        .tier(2);

    event.recipes.mna
        .runescribing()
        .output("minecraft:paper")
        .hMutex(0b00000000001)
        .vMutex(0b00000010000)
        .tier(1);

    event.recipes.mna
        .component()
        .output("kubejs:arcane_pulse")
        .input("minecraft:lapis_lazuli")
        .addInput("#forge:gems/quartz")
        .pattern("mna:circle")
        .addPattern("mna:square")
        .outputQuantity(1)
        .tier(1)
        .faction("mna:council");

    event.recipes.mna
        .modifier()
        .output("kubejs:stable_radius")
        .input("minecraft:amethyst_shard")
        .addInput("minecraft:redstone")
        .pattern("mna:circle")
        .addPattern("mna:slash")
        .outputQuantity(1)
        .tier(1);

    event.recipes.mna
        .shape()
        .output("kubejs:anchored_burst")
        .input("minecraft:echo_shard")
        .addInput("#forge:gems/quartz")
        .pattern("mna:circle")
        .addPattern("mna:square")
        .outputQuantity(1)
        .tier(2);

    event.recipes.mna
        .manaweavingAltar()
        .outputStack(Item.of("minecraft:enchanted_book"))
        .inputs("minecraft:book", "minecraft:lapis_lazuli")
        .addInput("#forge:gems/quartz")
        .patterns("mna:circle")
        .addPattern("mna:square")
        .enchantment("minecraft:sharpness")
        .magnitude(3)
        .copyNBT(false)
        .outputQuantity(1)
        .tier(2);

    event.recipes.mna
        .progression()
        .advancement("minecraft:story/root")
        .desc("kubejs.progression.mna_example")
        .tier(1)
        .faction("mna:none");

    event.recipes.mna
        .transmutation()
        .targetBlock("minecraft:stone")
        .replaceBlock("minecraft:gold_block")
        .tier(2);

    event.recipes.mna
        .transmutation()
        .targetBlock("minecraft:grass_block")
        .lootTable("minecraft:chests/simple_dungeon")
        .representationItem("minecraft:chest")
        .tier(2);

    event.recipes.mna
        .eldrinFume()
        .item("#forge:sand")
        .powerProvided("FIRE", 100)
        .tier(1);

    event.recipes.mna
        .eldrinAltar()
        .outputStack(Item.of("minecraft:diamond"))
        .inputs("minecraft:emerald", "minecraft:lapis_lazuli")
        .addInput("#forge:gems/quartz")
        .powerRequirement(Affinity.ARCANE, 250)
        .addPowerRequirement(Affinity.WIND, 100)
        .colors(0x4fc3f7, 0x81d4fa)
        .count(1)
        .tier(3);

    event.recipes.mna
        .cacheEffect()
        .effect("minecraft:speed")
        .duration(200, 400)
        .magnitude(2)
        .tier(2)
        .faction("mna:fey");

    event.recipes.mna
        .pattern(MnaPatternHelper.manaweaveGrid())
        .cell(5, 5, 1)
        .cell(5, 4, 1)
        .cell(5, 6, 1)
        .cell(4, 5, 1)
        .cell(6, 5, 1)
        .tier(1);

    exampleEventCustomBuilderUsage(event);
});
