package com.pickaid.mnajs.registry;

import com.mna.api.entities.construct.ConstructMaterial;
import com.pickaid.mnajs.MnaJS;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public final class MnaJSRegistries {
    public static final ResourceKey<Registry<ConstructMaterial>> CONSTRUCT_MATERIAL_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MnaJS.MOD_ID, "construct_material"));

    private static final DeferredRegister<ConstructMaterial> CONSTRUCT_MATERIALS =
            DeferredRegister.create(CONSTRUCT_MATERIAL_REGISTRY_KEY, MnaJS.MOD_ID);

    public static final Supplier<IForgeRegistry<ConstructMaterial>> CONSTRUCT_MATERIAL_REGISTRY =
            CONSTRUCT_MATERIALS.makeRegistry(() -> new RegistryBuilder<ConstructMaterial>()
                    .setName(CONSTRUCT_MATERIAL_REGISTRY_KEY.location()));

    private MnaJSRegistries() {
    }

    public static void init(IEventBus modBus) {
        CONSTRUCT_MATERIALS.register(modBus);
    }
}
