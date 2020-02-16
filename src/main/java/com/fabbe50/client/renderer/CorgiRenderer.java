package com.fabbe50.client.renderer;

import com.fabbe50.client.model.CorgiModel;
import com.fabbe50.corgis.entities.CorgiEntity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CorgiRenderer extends RenderLiving<CorgiEntity> {
    public CorgiRenderer(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new CorgiModel(), 0.4f);
    }

    protected float handleRotationFloat(CorgiEntity livingBase, float partialTicks) {
        return livingBase.getTailRotation();
    }

    public void doRender(CorgiEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (entity.isWet()) {
            float f = entity.getBrightness() * entity.getShadingWhileWet(partialTicks);
            GlStateManager.color(f, f, f);
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(CorgiEntity entity) {
        return entity.getCorgiType().getResourceLocation();
    }
}
