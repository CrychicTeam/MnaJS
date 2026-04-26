// priority: 0

console.info("[MnaJS] Loaded startup examples");

const ENABLE_MNA_STARTUP_EXAMPLES = true;
const STARTUP_MNA_BINDINGS = {
    ComponentApplicationResult: ComponentApplicationResult,
    SpellTarget: SpellTarget
};
const EntityType = Java.loadClass("net.minecraft.world.entity.EntityType");

MnaEvent.registerGuideBook((event) => {
    if (!ENABLE_MNA_STARTUP_EXAMPLES) {
        return;
    }

    const registry = event.getRegistry();
    registry.addGuidebookPath("kubejs:guidebooks/mna_example.json");
    registry.registerGuidebookCategory(
        "kubejs.mna_examples",
        "mna:textures/gui/cantrips/ward.png"
    );
});

MnaEvent.registerCantrip((event) => {
    if (!ENABLE_MNA_STARTUP_EXAMPLES) {
        return;
    }

    event
        .create("kubejs:flare_orb")
        .tier(1)
        .delay(20)
        .requiredAdvancement("mna:tier_4/craft_a_t4_spell")
        .sound("geckolib:jack_in_the_box_music")
        .icon("mna:textures/gui/affinity/air.png")
        .pattern("mna:bolt", "mna:square")
        .spellStack("minecraft:fire_charge")
        .dynamicItem("minecraft:fire_charge")
        .effect((player, cantrip, hand) => {
            PlayerMagic.addMana(player, 5);
        })
        .register();

    event
        .create("kubejs:delayed_orbit")
        .tier(1)
        .delay(40)
        .requiredAdvancement("minecraft:story/root")
        .sound("minecraft:block.amethyst_block.chime")
        .icon("mna:textures/gui/cantrips/gust.png")
        .pattern("mna:circle", "mna:square")
        .spellStack("minecraft:paper")
        .dynamicItemProvider((player) => player.getMainHandItem().copy())
        .delayedEffect((id, data) => {
            const player = data.getLeft();
            PlayerMagic.addMana(player, 2);
        })
        .register();

    event
        .create("kubejs:ward_ping")
        .tier(2)
        .delay(10)
        .icon("mna:textures/gui/cantrips/ward.png")
        .pattern("mna:circle", "mna:square")
        .spellStack("minecraft:amethyst_shard")
        .clearDynamicItem()
        .builtInEffect("ward")
        .register();

    event.removeCantrip("kubejs:debug_removed_cantrip");
});

StartupEvents.registry("mna:factions", (event) => {
    if (!ENABLE_MNA_STARTUP_EXAMPLES) {
        return;
    }

    event
        .create("kubejs:verdant_court", "basic")
        .factionGrimoire("minecraft:book")
        .tokenItem("minecraft:emerald")
        .raidSound("minecraft:event.raid.horn")
        .hornSound("minecraft:item.goat_horn.sound.0")
        .factionIcon("mna:textures/gui/cantrips/ward.png")
        .manaweaveRGB("rgb(116, 182, 79)")
        .sanctumStructure("mna:multiblock/council_circle_of_power")
        .castingResources("mna:mana", "mna:council_mana")
        .factionIconTextureSize(16)
        .maxModifierBonus(Attribute.DAMAGE, 0.25)
        .minModifierBonus(Attribute.DAMAGE, -0.1)
        .resourceSelector((player, availableResources) => {
            return availableResources.length > 0 ? availableResources[0] : null;
        });
});

StartupEvents.registry("mna:ritual-effects", (event) => {
    if (!ENABLE_MNA_STARTUP_EXAMPLES) {
        return;
    }

    /**
     * @type {Internal.CustomRitualEffect$Builder}
     */
    const lightningRitual = event.create(
        "kubejs:lightning_ritual_effect",
        "basic"
    );

    lightningRitual
        .ritualName("kubejs:lightning_ritual")
        .applicationTicks(20)
        .applyStartCheckInCreative(true)
        .applyEffect((context) => {
            const level = context.getLevel();
            if (level == null) {
                return false;
            }

            const lightning = EntityType.LIGHTNING_BOLT.create(level);
            if (lightning == null) {
                return false;
            }

            lightning.setPos(
                context.getCenter().x,
                context.getCenter().y,
                context.getCenter().z
            );
            level.addFreshEntity(lightning);
            return true;
        });
});

StartupEvents.registry("mna:components", (event) => {
    if (!ENABLE_MNA_STARTUP_EXAMPLES) {
        return;
    }

    /**
     * `event.create(id, type)` does not reliably narrow builder completion on its
     * own, so examples keep an explicitly typed builder local first.
     * @type {Internal.CustomSpellEffect$Builder}
     */
    const arcanePulse = event.create("kubejs:arcane_pulse", "basic");

    arcanePulse
        .guiIcon("mna:textures/gui/cantrips/ignite.png")
        .affinity(Affinity.ARCANE)
        .baselineCooldown(20)
        .soundEffect("minecraft:entity.allay.item_given")
        .factionRequirement("mna:council")
        .addReagent("minecraft:lapis_lazuli")
        .applyEffect((source, target, modificationData, context) => {
            return ComponentApplicationResult.SUCCESS;
        });

    /**
     * @type {Internal.CustomDamageComponent$Builder}
     */
    const searingArc = event.create("kubejs:searing_arc", "damage");

    searingArc
        .guiIcon("mna:textures/gui/cantrips/ignite.png")
        .affinity(Affinity.FIRE)
        .baselineCooldown(30)
        .soundEffect("minecraft:entity.blaze.shoot")
        .factionRequirement("mna:demons")
        .addDamageAttribute(4.0, 2.0, 8.0, 1.0, 1.0)
        .addOptionalReagent("minecraft:blaze_powder")
        .damageEntity((source, target, damage, context) => {
            return ComponentApplicationResult.SUCCESS;
        });

    /**
     * @type {Internal.CustomPotionEffectComponent$Builder}
     */
    const swiftstep = event.create("kubejs:swiftstep", "potion");

    swiftstep
        .guiIcon("mna:textures/gui/cantrips/gust.png")
        .affinity(Affinity.WIND)
        .effect("minecraft:speed")
        .modifiesDuration(true)
        .addDurationAttribute(60, 20, 180, 20, 1.0)
        .modifiesMagnitude(true)
        .addMagnitudeAttribute(1, 1, 3, 1, 1.25)
        .soundEffect("minecraft:entity.player.levelup")
        .factionRequirement("mna:fey")
        .addReagent("minecraft:sugar")
        .addPermanencyReagent("minecraft:ghast_tear")
        .permanentFor("mna:fey");
});

StartupEvents.registry("mna:shapes", (event) => {
    if (!ENABLE_MNA_STARTUP_EXAMPLES) {
        return;
    }

    event
        .create("kubejs:anchored_burst", "basic")
        .guiIcon("mna:textures/gui/cantrips/ward.png")
        .initialComplexity(1.5)
        .spawnsTargetEntity(false)
        .isChanneled(false)
        .baselineCooldown(10)
        .affectsCaster(true)
        .addReagent("minecraft:amethyst_shard")
        .target((source, level, modificationData, recipe) => {
            return [SpellTarget.NONE];
        });
});

StartupEvents.registry("mna:modifiers", (event) => {
    if (!ENABLE_MNA_STARTUP_EXAMPLES) {
        return;
    }

    /**
     * @type {Internal.CustomModifier$Builder}
     */
    const steadyFocus = event.create("kubejs:steady_focus", "basic");

    steadyFocus
        .guiIcon("mna:textures/gui/cantrips/ignite.png")
        .attributes(Attribute.RANGE, Attribute.PRECISION)
        .tier(1)
        .requiredXPForRote(75);
});

StartupEvents.registry("mna:construct_task", (event) => {
    if (!ENABLE_MNA_STARTUP_EXAMPLES) {
        return;
    }

    /**
     * @type {Internal.CustomConstructTask$Builder}
     */
    const scriptedWait = event.create("kubejs:scripted_wait", "basic");

    scriptedWait
        .basedOn(MnaConstructTasks.WAIT)
        .icon("mna:textures/gui/cantrips/ignite.png")
        .outputs(2)
        .lowTierAssignable(true);
});

StartupEvents.registry("mnajs:construct_material", (event) => {
    if (!ENABLE_MNA_STARTUP_EXAMPLES) {
        return;
    }

    /**
     * @type {Internal.CustomConstructMaterial$Builder}
     */
    const moonsteel = event.create("kubejs:moonsteel", "basic");

    moonsteel
        .basedOn("mna:iron")
        .texture("mna:textures/entity/animated_construct/armor_iron.png")
        .modelSet("iron")
        .health(5)
        .damageBonus(2.0)
        .manaStorage(750)
        .cooldownMultiplier("cast_spell", 0.85)
        .armorBonus("head", 3)
        .toughnessBonus("torso", 1);
});

StartupEvents.registry("item", (event) => {
    if (!ENABLE_MNA_STARTUP_EXAMPLES) {
        return;
    }

    /**
     * @type {Internal.CustomConstructPartItem$Builder}
     */
    const moonsteelHead = event.create("kubejs:moonsteel_basic_head", "construct_part");

    moonsteelHead
        .material("kubejs:moonsteel")
        .slot("head")
        .modelMutex(ConstructMutexHead.BASIC)
        .armor(3)
        .intelligenceBonus(10);
});

StartupEvents.registry("item", (event) => {
    if (!ENABLE_MNA_STARTUP_EXAMPLES) {
        return;
    }

    event
        .create("kubejs:reserve_battery", "mana_battery_item")
        .maxMana(500)
        .manaPerTick(10)
        .manaPerOperation(25)
        .curiosTick((entity, index, stack) => true)
        .tickEffect((stack, player, level, slot, mana, selected) => {
            if (selected && mana >= 25) {
                PlayerMagic.addMana(player, 1);
                return true;
            }
            return false;
        });

    event
        .create("kubejs:court_signet", "tiered_item")
        .tier(2)
        .faction("mna:council")
        .minIre(0.0)
        .maxIre(0.02)
        .sneakBypass(true)
        .doesSneakBypassUse((stack, level, pos, player) => true)
        .usedByPlayer((player) => {
            PlayerMagic.addMana(player, 10);
        });
});

StartupEvents.registry("block", (event) => {
    if (!ENABLE_MNA_STARTUP_EXAMPLES) {
        return;
    }

    event
        .create("kubejs:spell_echo_anchor", "spell_interactible")
        .onHitBySpell((level, pos, spell) => true);

    event
        .create("kubejs:manaweave_listener", "manaweave_notifiable")
        .onNotify((level, pos, state, patterns, caster) => {
            return patterns != null && !patterns.isEmpty();
        });
});
