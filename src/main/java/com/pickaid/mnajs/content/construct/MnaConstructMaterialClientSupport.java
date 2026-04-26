package com.pickaid.mnajs.content.construct;

import com.mna.entities.models.constructs.modular.ConstructModelRegistry;

import java.util.Set;
import java.util.function.Predicate;

final class MnaConstructMaterialClientSupport {
    private MnaConstructMaterialClientSupport() {
    }

    static void registerModels(CustomConstructMaterial material) {
        new ConstructModelRegistry().register(
                material,
                material.modelSet(),
                whitelist(material.headModels()),
                whitelist(material.torsoModels()),
                whitelist(material.legModels()),
                whitelist(material.armModels())
        );
    }

    private static Predicate<String> whitelist(Set<String> values) {
        if (values == null || values.isEmpty()) {
            return ignored -> true;
        }
        return values::contains;
    }
}
