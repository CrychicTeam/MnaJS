package com.pickaid.mnajs.kubejs.probe;

import zzzank.probejs.docs.assignments.SpecialTypes;
import zzzank.probejs.lang.java.clazz.ClassPath;
import zzzank.probejs.lang.typescript.RequestAwareFiles;
import zzzank.probejs.lang.typescript.ScriptDump;
import zzzank.probejs.lang.typescript.TypeScriptFile;
import zzzank.probejs.lang.typescript.code.Code;
import zzzank.probejs.lang.typescript.code.member.TypeDecl;
import zzzank.probejs.lang.typescript.code.ts.Wrapped;
import zzzank.probejs.lang.typescript.code.type.BaseType;
import zzzank.probejs.lang.typescript.code.type.Types;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

final class MnaJSLegacyProbeTypes {
    private static final List<String> LEGACY_GLOBAL_FILES = List.of(
            "mnajs_special_types.d.ts",
            "mnajs_id_aliases.d.ts"
    );

    private MnaJSLegacyProbeTypes() {
    }

    static void assignWrappedTypes(ScriptDump scriptDump) {
        for (MnaJSLegacyProbeIdAliases.IdAlias alias : MnaJSLegacyProbeIdAliases.all()) {
            wrapperTypeDeclarations(alias).forEach(type -> scriptDump.assignType(alias.wrapperClass(), type.name, type.type));
        }
    }

    static Collection<TypeDecl> specialTypeDeclarations() {
        List<TypeDecl> declarations = new ArrayList<>();
        for (MnaJSLegacyProbeIdAliases.IdAlias alias : MnaJSLegacyProbeIdAliases.all()) {
            declarations.add(new TypeDecl(alias.alias(), specialType(alias)));
        }
        return declarations;
    }

    static void rewriteWrapperFiles(RequestAwareFiles files) {
        for (MnaJSLegacyProbeIdAliases.IdAlias alias : MnaJSLegacyProbeIdAliases.all()) {
            TypeScriptFile file = files.requestOrCreate(ClassPath.ofJava(alias.wrapperClass()));
            rewriteWrapperCodes(file.codes, alias.wrapperClass().getSimpleName(), alias.alias());
        }
    }

    static Set<Class<?>> deniedWrapperClasses() {
        LinkedHashSet<Class<?>> denied = new LinkedHashSet<>();
        for (MnaJSLegacyProbeIdAliases.IdAlias alias : MnaJSLegacyProbeIdAliases.all()) {
            denied.add(alias.wrapperClass());
        }
        return Set.copyOf(denied);
    }

    static void cleanupLegacyGlobalFiles(Path scriptRoot) {
        Path globalDir = scriptRoot.resolve("global");
        for (String fileName : LEGACY_GLOBAL_FILES) {
            Path path = globalDir.resolve(fileName);
            try {
                Files.deleteIfExists(path);
            } catch (IOException exception) {
                throw new IllegalStateException("Failed to delete legacy MnaJS ProbeJS file " + path, exception);
            }
        }
    }

    static void rewriteWrapperCodes(List<Code> codes, String wrapperSimpleName, String specialAliasName) {
        BaseType specialType = Types.primitive(SpecialTypes.dot(specialAliasName));
        String wrapperName = "$" + wrapperSimpleName;
        String wrapperTypeName = wrapperName + "$$Type";
        String globalAliasName = wrapperName + "_";

        codes.removeIf(code -> code instanceof TypeDecl typeDecl
                && (wrapperName.equals(typeDecl.name) || wrapperTypeName.equals(typeDecl.name)));
        codes.removeIf(code -> code instanceof Wrapped.Global);

        TypeDecl wrapperAlias = new TypeDecl(wrapperName, specialType);
        TypeDecl wrapperTypeAlias = new TypeDecl(wrapperTypeName, specialType);
        Wrapped.Global global = new Wrapped.Global();
        global.addCode(new TypeDecl(globalAliasName, Types.primitive(wrapperTypeName)));

        codes.add(0, global);
        codes.add(0, wrapperTypeAlias);
        codes.add(0, wrapperAlias);
    }

    private static Collection<TypeDecl> wrapperTypeDeclarations(MnaJSLegacyProbeIdAliases.IdAlias alias) {
        BaseType type = Types.primitive(SpecialTypes.dot(alias.alias()));
        String wrapperName = "$" + alias.wrapperClass().getSimpleName();
        return List.of(
                new TypeDecl(wrapperName, type),
                new TypeDecl(wrapperName + "$$Type", type)
        );
    }

    private static BaseType specialType(MnaJSLegacyProbeIdAliases.IdAlias alias) {
        if (alias.specialTypeReference() != null && !alias.specialTypeReference().isBlank()) {
            return Types.primitive(alias.specialTypeReference());
        }
        List<BaseType> literals = alias.ids().get().stream()
                .sorted()
                .distinct()
                .map(Types::literal)
                .map(BaseType.class::cast)
                .toList();
        if (literals.isEmpty()) {
            return Types.STRING;
        }
        if (literals.size() == 1) {
            return literals.get(0);
        }
        return Types.or(literals);
    }
}
