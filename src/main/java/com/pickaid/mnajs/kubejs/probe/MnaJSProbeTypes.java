package com.pickaid.mnajs.kubejs.probe;

import com.probejs.specials.assign.ClassAssignmentManager;
import dev.latvian.mods.kubejs.typings.desc.PrimitiveDescJS;
import dev.latvian.mods.kubejs.typings.desc.TypeDescJS;

import java.util.ArrayList;
import java.util.List;

final class MnaJSProbeTypes {
    private MnaJSProbeTypes() {
    }

    static void installAssignments() {
        for (MnaJSLegacyProbeIdAliases.IdAlias alias : MnaJSLegacyProbeIdAliases.all()) {
            ClassAssignmentManager.ASSIGNMENTS.removeAll(alias.wrapperClass());
            for (TypeDescJS type : assignmentTypes(alias)) {
                ClassAssignmentManager.ASSIGNMENTS.put(alias.wrapperClass(), type);
            }
        }
    }

    private static List<TypeDescJS> assignmentTypes(MnaJSLegacyProbeIdAliases.IdAlias alias) {
        if (alias.specialTypeReference() != null && !alias.specialTypeReference().isBlank()) {
            return List.of(new PrimitiveDescJS(alias.specialTypeReference()));
        }

        List<String> ids = safeIds(alias);
        List<TypeDescJS> literals = ids.stream()
                .sorted()
                .distinct()
                .map(MnaJSProbeTypes::stringLiteral)
                .toList();

        if (literals.size() <= 1) {
            return literals.isEmpty() ? List.of(TypeDescJS.STRING) : literals;
        }

        return List.of(TypeDescJS.any(literals.toArray(TypeDescJS[]::new)));
    }

    private static List<String> safeIds(MnaJSLegacyProbeIdAliases.IdAlias alias) {
        try {
            return alias.ids().get();
        } catch (Throwable throwable) {
            return List.of();
        }
    }

    private static TypeDescJS stringLiteral(String value) {
        return new PrimitiveDescJS("\"" + escapeTypeScript(value) + "\"");
    }

    private static String escapeTypeScript(String value) {
        StringBuilder builder = new StringBuilder(value.length() + 8);
        for (int index = 0; index < value.length(); index++) {
            char character = value.charAt(index);
            if (character == '\\' || character == '"') {
                builder.append('\\');
            }
            builder.append(character);
        }
        return builder.toString();
    }
}
