package com.pickaid.mnajs.content.construct;

import com.mna.api.entities.construct.ai.ConstructAITask;
import com.mna.api.entities.construct.ai.ConstructTask;
import com.pickaid.mnajs.kubejs.MnaJSPlugin;
import com.pickaid.mnajs.kubejs.texture.MnaTexture;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.resources.ResourceLocation;

public class CustomConstructTask extends ConstructTask {
    public CustomConstructTask(Builder builder) {
        super(
                builder.iconTexture,
                builder.aiTaskClass,
                builder.outputs,
                builder.lodestarAssignable,
                builder.lowTierAssignable,
                builder.condition
        );
    }

    public static class Builder extends BuilderBase<CustomConstructTask> {
        private ResourceLocation iconTexture;
        private Class<? extends ConstructAITask<?>> aiTaskClass;
        private int outputs = 2;
        private boolean lodestarAssignable;
        private boolean lowTierAssignable;
        private boolean condition;

        public Builder(ResourceLocation id) {
            super(id);
        }

        @Override
        public RegistryInfo<ConstructTask> getRegistryType() {
            return MnaJSPlugin.CONSTRUCT_TASK_REGISTRY.get();
        }

        @Info(value = "Copy the metadata and AI class from an existing construct task.", params = {
                @Param(name = "template", value = "Existing construct task instance or construct task id such as mna:wait.")
        })
        public Builder basedOn(ConstructTask template) {
            this.iconTexture = template.getIconTexture();
            this.aiTaskClass = template.getAIClass();
            this.outputs = template.getOutputs();
            this.lodestarAssignable = template.isLodestarAssignable();
            this.lowTierAssignable = template.isLowTierAssignable();
            this.condition = template.isCondition();
            return this;
        }

        @Info(value = "Set the task icon texture shown by Mana and Artifice.", params = {
                @Param(name = "icon", value = "Texture id such as mna:textures/gui/cantrips/ignite.png.")
        })
        public Builder icon(MnaTexture icon) {
            this.iconTexture = icon.location();
            return this;
        }

        @Info(value = "Reuse the backing AI implementation from an existing construct task.", params = {
                @Param(name = "template", value = "Existing construct task instance or construct task id such as mna:wait.")
        })
        public Builder aiTask(ConstructTask template) {
            this.aiTaskClass = template.getAIClass();
            return this;
        }

        @HideFromJS
        public Builder aiTaskClass(Class<? extends ConstructAITask<?>> aiTaskClass) {
            this.aiTaskClass = aiTaskClass;
            return this;
        }

        @Info("Set how many output branches this construct task exposes.")
        public Builder outputs(int outputs) {
            this.outputs = outputs;
            return this;
        }

        @Info("Set whether this construct task can be assigned to a lodestar.")
        public Builder lodestarAssignable(boolean lodestarAssignable) {
            this.lodestarAssignable = lodestarAssignable;
            return this;
        }

        @Info("Set whether low-tier constructs may use this task.")
        public Builder lowTierAssignable(boolean lowTierAssignable) {
            this.lowTierAssignable = lowTierAssignable;
            return this;
        }

        @Info("Set whether this task behaves as a conditional branch.")
        public Builder condition(boolean condition) {
            this.condition = condition;
            return this;
        }

        @Override
        public CustomConstructTask createObject() {
            if (iconTexture == null) {
                throw new IllegalArgumentException("Custom construct task " + id + " must define icon");
            }
            if (aiTaskClass == null) {
                throw new IllegalArgumentException("Custom construct task " + id + " must define aiTaskClass or basedOn");
            }
            if (outputs < 1) {
                throw new IllegalArgumentException("Custom construct task " + id + " must define outputs >= 1");
            }
            return new CustomConstructTask(this);
        }
    }
}
