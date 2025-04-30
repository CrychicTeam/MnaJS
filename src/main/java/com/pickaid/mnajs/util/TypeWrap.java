package com.pickaid.mnajs.util;

import dev.latvian.mods.kubejs.registry.BuilderBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class TypeWrap {
    public record FactionHolder(ResourceLocation getLocation) implements ResourceHolder<FactionHolder> {
        public static FactionHolder of(Object o) {
            return ResourceHolder.of(o, FactionHolder::new);
        }
    }

    @SuppressWarnings("rawtypes")
    public interface ResourceHolder<T extends ResourceHolder<T>> {
        static <T extends ResourceHolder<T>> T of(Object o, Function<ResourceLocation, T> constructor){
            if (o instanceof String str) {
                return constructor.apply(new ResourceLocation(str));
            }
            if (o instanceof ResourceLocation rl) {
                return constructor.apply(rl);
            }
            if (o instanceof RegistryObject reg) {
                return constructor.apply(reg.getId());
            }
            if (o instanceof BuilderBase builder){
                return constructor.apply(builder.id);
            }
            throw new IllegalArgumentException("Object " + o + " of class " + o.getClass().getName() + " is not valid, should be a String or ResourceLocation.");
        }
    }
}
