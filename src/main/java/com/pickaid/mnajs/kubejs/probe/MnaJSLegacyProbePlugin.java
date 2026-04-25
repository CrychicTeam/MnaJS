package com.pickaid.mnajs.kubejs.probe;

import zzzank.probejs.api.dump.CustomDump;
import zzzank.probejs.docs.assignments.SpecialTypes;
import zzzank.probejs.lang.snippet.SnippetDump;
import zzzank.probejs.lang.transpiler.Transpiler;
import zzzank.probejs.lang.typescript.RequestAwareFiles;
import zzzank.probejs.lang.typescript.ScriptDump;
import zzzank.probejs.lang.typescript.code.ts.Wrapped;
import zzzank.probejs.plugin.ProbeJSPlugin;

import java.util.Set;

public final class MnaJSLegacyProbePlugin implements ProbeJSPlugin {
    static String specialTypesGlobalName() {
        return "special_types";
    }

    @Override
    public void assignType(ScriptDump scriptDump) {
        MnaJSLegacyProbeTypes.assignWrappedTypes(scriptDump);
    }

    @Override
    public void denyTypes(Transpiler transpiler) {
        MnaJSLegacyProbeTypes.deniedWrapperClasses().forEach(transpiler::reject);
    }

    @Override
    public void modifyFiles(RequestAwareFiles files) {
        MnaJSLegacyProbeTypes.rewriteWrapperFiles(files);
    }

    @Override
    public void addGlobals(ScriptDump scriptDump) {
        scriptDump.addChild(new CustomDump(
                scriptDump.writeTo().resolve(".mnajs_legacy_cleanup"),
                path -> MnaJSLegacyProbeTypes.cleanupLegacyGlobalFiles(scriptDump.writeTo())
        ));
        Wrapped.Namespace special = new Wrapped.Namespace(SpecialTypes.NAMESPACE);
        MnaJSLegacyProbeTypes.specialTypeDeclarations().forEach(special::addCode);
        scriptDump.addGlobal(specialTypesGlobalName(), special);
    }

    @Override
    public Set<Class<?>> provideJavaClass(ScriptDump scriptDump) {
        return MnaJSLegacyProbeJava.providedClasses();
    }

    @Override
    public void addVSCodeSnippets(SnippetDump dump) {
        MnaJSLegacyProbeSnippets.addSnippets(dump);
    }
}
