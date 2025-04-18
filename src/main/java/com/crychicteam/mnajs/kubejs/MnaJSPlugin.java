package com.crychicteam.mnajs.kubejs;

import com.crychicteam.mnajs.content.CustomFaction;
import com.crychicteam.mnajs.content.items.CustomManaBatteryItem;
import com.crychicteam.mnajs.recipes.RecipesHelper;
import com.mna.api.faction.IFaction;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;

public class MnaJSPlugin extends KubeJSPlugin {
    public static final Lazy<RegistryInfo<IFaction>> FACTION_REGISTRY =
			Lazy.of(() -> RegistryInfo.of(
					ResourceKey.createRegistryKey(new ResourceLocation("mna:factions")), IFaction.class)
			);
	
	@Override
	public void registerEvents() {
		MnaJSEvents.GROUP.register();
	}
	
	@Override
	public void init() {
		FACTION_REGISTRY.get().addType("basic", CustomFaction.Builder.class, CustomFaction.Builder::new);

		RegistryInfo.ITEM.addType("mana_battery_item" , CustomManaBatteryItem.Builder.class, CustomManaBatteryItem.Builder::new);
	}

	@Override
	public void registerBindings(BindingsEvent event) {
		event.add("MNARecipesHelper", RecipesHelper.class);
	}
}

