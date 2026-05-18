package com.pickaid.mnajs.kubejs.compat;

import com.pickaid.mnajs.kubejs.id.MnaSpellEffectId;
import com.pickaid.mnajs.kubejs.id.MnaTypedIdPiSerializers;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import org.pickaid.pikubejscompat.api.id.PiCommonIdSpecs;
import org.pickaid.pikubejscompat.api.spec.PiIdSpec;
import org.pickaid.piserializekit.api.convert.PiResourceLocationConverter;
import org.pickaid.piserializekit.api.convert.PiTypedValueConverters;

public final class MnaPiKubeJSCompatIds {
    public static final String SPELL_EFFECT = "mna:spell_effect";

    private MnaPiKubeJSCompatIds() {
    }

    public static PiIdSpec<MnaSpellEffectId> spellEffect(Supplier<List<String>> candidates) {
        return PiIdSpec.builder(SPELL_EFFECT, MnaSpellEffectId.class)
                .converter("spellEffectId", PiTypedValueConverters.resourceLocationBacked(
                        MnaSpellEffectId.class,
                        MnaSpellEffectId::of,
                        MnaSpellEffectId::location,
                        PiResourceLocationConverter.builder().defaultNamespace("mna").build()
                ))
                .parser(MnaSpellEffectId::parse)
                .piSerializer(MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.SPELL_EFFECT_ID))
                .candidates(candidates)
                .build();
    }

    public static PiIdSpec<ResourceLocation> sound(Supplier<List<String>> candidates) {
        return PiCommonIdSpecs.sound(candidates);
    }
}
