package com.pickaid.mnajs.kubejs.compat;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import org.pickaid.pikubejscompat.api.bootstrap.PiKubeJSCompatBootstrap;
import org.pickaid.pikubejscompat.api.bootstrap.PiKubeJSCompatModule;

public final class MnaPiKubeJSCompatModule implements PiKubeJSCompatModule {
    private final Supplier<List<String>> factionCandidates;
    private final Supplier<List<String>> spellEffectCandidates;
    private final Supplier<List<String>> soundCandidates;

    public MnaPiKubeJSCompatModule(
            Supplier<List<String>> factionCandidates,
            Supplier<List<String>> spellEffectCandidates,
            Supplier<List<String>> soundCandidates
    ) {
        this.factionCandidates = Objects.requireNonNull(factionCandidates, "factionCandidates");
        this.spellEffectCandidates = Objects.requireNonNull(spellEffectCandidates, "spellEffectCandidates");
        this.soundCandidates = Objects.requireNonNull(soundCandidates, "soundCandidates");
    }

    @Override
    public void contribute(PiKubeJSCompatBootstrap bootstrap) {
        Objects.requireNonNull(bootstrap, "bootstrap");
        bootstrap.addId(MnaPiKubeJSCompatIds.faction(factionCandidates));
        bootstrap.addId(MnaPiKubeJSCompatIds.spellEffect(spellEffectCandidates));
        bootstrap.addId(MnaPiKubeJSCompatIds.sound(soundCandidates));
    }
}
