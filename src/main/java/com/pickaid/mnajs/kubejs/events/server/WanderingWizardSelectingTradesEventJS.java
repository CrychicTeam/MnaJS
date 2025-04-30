package com.pickaid.mnajs.kubejs.events.server;

import com.mna.api.events.WanderingWizardSelectingTradesEvent;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffers;

public class WanderingWizardSelectingTradesEventJS extends EventJS {
    WanderingWizardSelectingTradesEvent event;

    public WanderingWizardSelectingTradesEventJS(WanderingWizardSelectingTradesEvent event) {
        this.event = event;
    }

    public Merchant getWanderingWizard() {
        return this.event.getWanderingWizard();
    }

    public MerchantOffers getOffers() {
        return this.event.getOffers();
    }

    public VillagerTrades.ItemListing[] getNewTrades() {
        return this.event.getNewTrades();
    }

    public int getMaxNumbers() {
        return this.event.getMaxNumbers();
    }
}
