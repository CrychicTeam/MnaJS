package com.pickaid.mnajs.util;

import com.mna.api.tools.CollectionUtils;
import com.mna.api.tools.MATags;
import com.mna.tools.BiomeUtils;
import com.mna.tools.EntityUtil;
import com.mna.tools.InventoryUtilities;
import com.mna.tools.ParticleConfigurations;
import com.mna.tools.ProjectileHelper;
import com.mna.tools.RecipeUtil;
import com.mna.tools.ShearHelper;
import com.mna.tools.StructureUtils;
import com.mna.tools.SummonUtils;
import com.mna.tools.math.MathUtils;
import com.mna.tools.render.GuiRenderUtils;

public class MnaUtils {
    public static Class<MATags> tags = MATags.class;
    public static Class<CollectionUtils> collectionUtil = CollectionUtils.class;

    public static Class<MathUtils> mathUtil = MathUtils.class;

    public static Class<BiomeUtils> biomeUtil = BiomeUtils.class;
    public static Class<StructureUtils> structureUtil = StructureUtils.class;

    public static Class<EntityUtil> entityUtil = EntityUtil.class;
    public static Class<SummonUtils> summonUtil = SummonUtils.class;
    public static Class<ProjectileHelper> projectileUtil = ProjectileHelper.class;

    public static Class<InventoryUtilities> inventoryUtil = InventoryUtilities.class;
    public static Class<RecipeUtil> recipeUtil = RecipeUtil.class;
    public static Class<ShearHelper> shearUtil = ShearHelper.class;

    public static Class<ParticleConfigurations> particleUtil = ParticleConfigurations.class;
    public static Class<GuiRenderUtils> renderUtil = GuiRenderUtils.class;
}