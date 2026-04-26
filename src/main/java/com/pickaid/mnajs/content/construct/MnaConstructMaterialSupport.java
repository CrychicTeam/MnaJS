package com.pickaid.mnajs.content.construct;

import com.mna.api.entities.construct.ConstructMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class MnaConstructMaterialSupport {
    private static final Map<ResourceLocation, ConstructMaterial> CUSTOM_MATERIALS = new LinkedHashMap<>();

    private MnaConstructMaterialSupport() {
    }

    public static synchronized void register(CustomConstructMaterial material) {
        Objects.requireNonNull(material, "material");
        ResourceLocation id = material.getId();
        Objects.requireNonNull(id, "material id");

        ConstructMaterial existing = find(id);
        if (existing != null && existing != material) {
            throw new IllegalArgumentException("Construct material id already exists: " + id);
        }

        CUSTOM_MATERIALS.put(id, material);

        boolean alreadyPresent = ConstructMaterial.ALL_MATERIALS != null && ConstructMaterial.ALL_MATERIALS.stream()
                .filter(Objects::nonNull)
                .anyMatch(candidate -> id.equals(candidate.getId()));
        if (!alreadyPresent && ConstructMaterial.ALL_MATERIALS != null) {
            ConstructMaterial.ALL_MATERIALS.add(material);
        }

        if (FMLEnvironment.dist == Dist.CLIENT) {
            MnaConstructMaterialClientSupport.registerModels(material);
        }
    }

    public static synchronized ConstructMaterial find(ResourceLocation id) {
        if (id == null) {
            return null;
        }

        if (ConstructMaterial.ALL_MATERIALS != null) {
            for (ConstructMaterial material : ConstructMaterial.ALL_MATERIALS) {
                if (material != null && id.equals(material.getId())) {
                    return material;
                }
            }
        }

        return CUSTOM_MATERIALS.get(id);
    }

    public static synchronized List<ConstructMaterial> allMaterials() {
        LinkedHashMap<ResourceLocation, ConstructMaterial> materials = new LinkedHashMap<>();

        if (ConstructMaterial.ALL_MATERIALS != null) {
            for (ConstructMaterial material : ConstructMaterial.ALL_MATERIALS) {
                if (material != null && material.getId() != null) {
                    materials.putIfAbsent(material.getId(), material);
                }
            }
        }

        for (Map.Entry<ResourceLocation, ConstructMaterial> entry : CUSTOM_MATERIALS.entrySet()) {
            materials.putIfAbsent(entry.getKey(), entry.getValue());
        }

        return new ArrayList<>(materials.values());
    }

    public static synchronized List<ResourceLocation> resourceLocationIds() {
        return allMaterials().stream()
                .map(ConstructMaterial::getId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }
}
