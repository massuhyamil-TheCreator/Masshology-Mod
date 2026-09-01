package com.massh.masshology.client.renderer;

import com.massh.masshology.ModEntities;
import com.massh.masshology.entity.draugr.DraugrEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class DraugrRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<DraugrEntity, R> {
    public DraugrRenderer(EntityRendererProvider.Context context) {
        super(context, ModEntities.DRAUGR_WARRIOR.get());
    }


    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0.0F;
    }
}
