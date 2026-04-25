package com.pickaid.mnajs.kubejs.pattern.editor;

import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.kubejs.id.MnaTypedIdFieldCodecs;
import net.minecraft.resources.ResourceLocation;
import org.pickaid.piserializekit.api.schema.PiField;
import org.pickaid.piserializekit.api.schema.PiSyncModel;
import org.pickaid.piserializekit.api.schema.PiSyncScope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@PiSyncModel(id = "mnajs:pattern_editor_document", version = 1)
public final class MnaPatternEditorDocumentState {
    @PiField(id = "kind", sync = PiSyncScope.MENU, persist = true)
    public MnaPatternDocumentKind kind = MnaPatternDocumentKind.MANAWEAVE;

    @PiField(id = "title", sync = PiSyncScope.MENU, persist = true)
    public String title = "";

    @PiField(id = "pattern_id", sync = PiSyncScope.MENU, persist = true, serializer = MnaTypedIdFieldCodecs.OptionalManaweavePatternIdCodec.class)
    public Optional<MnaManaweavePatternId> patternId = Optional.empty();

    @PiField(id = "grid", sync = PiSyncScope.MENU, persist = true)
    public final List<List<Integer>> grid = new ArrayList<>();

    @PiField(id = "reagent_rows", sync = PiSyncScope.MENU, persist = true)
    public final List<String> reagentRows = new ArrayList<>();

    @PiField(id = "key_bindings", sync = PiSyncScope.MENU, persist = true)
    public final Map<String, String> keyBindings = new LinkedHashMap<>();
}
