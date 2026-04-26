package com.pickaid.mnajs.content.spell;

import com.mna.api.spells.SpellCraftingContext;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.base.SpellBlacklistResult;
import com.mna.api.spells.parts.Modifier;
import com.pickaid.mnajs.kubejs.MnaJSPlugin;
import com.pickaid.mnajs.kubejs.texture.MnaTexture;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class CustomModifier extends Modifier {
    private final Builder builder;

    public CustomModifier(Builder builder) {
        super(builder.guiIcon, builder.requiredXPForRote, builder.governedAttributes.toArray(new Attribute[0]));
        this.builder = builder;
    }

    @Override
    public boolean isCraftable(SpellCraftingContext context) {
        if (builder.isCraftableCallback != null) {
            return builder.isCraftableCallback.apply(context);
        }
        return super.isCraftable(context);
    }

    @Override
    public boolean isUseableByPlayers() {
        return builder.isUseableByPlayers;
    }

    @Override
    public int requiredXPForRote() {
        return builder.requiredXPForRote;
    }

    @Override
    public SpellBlacklistResult canBeCastAt(Level world, Vec3 position) {
        if (builder.canBeCastAtCallback != null) {
            return builder.canBeCastAtCallback.apply(world, position);
        }
        return super.canBeCastAt(world, position);
    }

    @Override
    public int getTier(Level level) {
        return builder.tier != null ? builder.tier : super.getTier(level);
    }

    @Info("Callback interface for checking whether this modifier can be crafted.")
    public interface IsCraftableCallback {
        boolean apply(SpellCraftingContext context);
    }

    @Info("Callback interface for validating whether this modifier can be cast at a position.")
    public interface CanBeCastAtCallback {
        SpellBlacklistResult apply(Level world, Vec3 position);
    }

    public static class Builder extends BuilderBase<CustomModifier> {
        private ResourceLocation guiIcon;
        private final List<Attribute> governedAttributes = new ArrayList<>();
        private int requiredXPForRote = 100;
        private Integer tier;
        private boolean isUseableByPlayers = true;
        private IsCraftableCallback isCraftableCallback;
        private CanBeCastAtCallback canBeCastAtCallback;

        public Builder(ResourceLocation id) {
            super(id);
        }

        @Override
        public RegistryInfo<Modifier> getRegistryType() {
            return MnaJSPlugin.MODIFIER_REGISTRY.get();
        }

        @Info(value = "Copy the public behavior surface from an existing modifier.", params = {
                @Param(name = "template", value = "Existing modifier instance or modifier id such as mna:range.")
        })
        public Builder basedOn(Modifier template) {
            this.guiIcon = template.getGuiIcon();
            this.governedAttributes.clear();
            this.governedAttributes.addAll(template.getModifiedAttributes());
            this.requiredXPForRote = template.requiredXPForRote();
            this.isUseableByPlayers = template.isUseableByPlayers();
            try {
                this.tier = template.getTier(null);
            } catch (RuntimeException ignored) {
                this.tier = null;
            }
            return this;
        }

        @Info(value = "Set the GUI icon texture for this modifier.", params = {
                @Param(name = "icon", value = "Texture id such as mna:textures/gui/cantrips/ignite.png.")
        })
        public Builder guiIcon(MnaTexture icon) {
            this.guiIcon = icon.location();
            return this;
        }

        @Info(value = "Replace the full governed attribute list for this modifier.", params = {
                @Param(name = "attributes", value = "One or more spell attributes such as Attribute.RANGE or Attribute.PRECISION.")
        })
        public Builder attributes(Attribute... attributes) {
            this.governedAttributes.clear();
            if (attributes != null) {
                for (Attribute attribute : attributes) {
                    if (attribute != null) {
                        this.governedAttributes.add(attribute);
                    }
                }
            }
            return this;
        }

        @Info("Append one governed attribute to this modifier.")
        public Builder addAttribute(Attribute attribute) {
            if (attribute != null) {
                this.governedAttributes.add(attribute);
            }
            return this;
        }

        @Info("Set the rote XP required to learn this modifier.")
        public Builder requiredXPForRote(int xp) {
            this.requiredXPForRote = xp;
            return this;
        }

        @Info("Set the displayed spell tier for this modifier.")
        public Builder tier(int tier) {
            this.tier = tier;
            return this;
        }

        @Info("Set whether players are allowed to use this modifier.")
        public Builder isUseableByPlayers(boolean useableByPlayers) {
            this.isUseableByPlayers = useableByPlayers;
            return this;
        }

        @Info("Set a custom craftability callback for this modifier.")
        public Builder isCraftable(IsCraftableCallback callback) {
            this.isCraftableCallback = callback;
            return this;
        }

        @Info("Set a custom cast-location validation callback for this modifier.")
        public Builder canBeCastAt(CanBeCastAtCallback callback) {
            this.canBeCastAtCallback = callback;
            return this;
        }

        @Override
        public CustomModifier createObject() {
            if (guiIcon == null) {
                throw new IllegalArgumentException("Custom modifier " + id + " must define guiIcon");
            }
            if (governedAttributes.isEmpty()) {
                throw new IllegalArgumentException("Custom modifier " + id + " must define at least one governed attribute");
            }
            return new CustomModifier(this);
        }
    }
}
