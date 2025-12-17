package net.potionstudios.biomeswevegone.client.renderer.entity.pumpkinwarden;

import net.minecraft.resources.Identifier;
import net.potionstudios.biomeswevegone.BiomesWeveGone;
import net.potionstudios.biomeswevegone.world.entity.pumpkinwarden.PumpkinWarden;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

/**
 * Pumpkin Warden Model
 * @see GeoModel
 * @author YaBoiChips
 */
class PumpkinWardenModel<T extends PumpkinWarden> extends GeoModel<T> {

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return BiomesWeveGone.id("pumpkinwarden");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return renderState.getGeckolibData(PumpkinWardenRenderer.HIDING) ? BiomesWeveGone.id("textures/entity/pumpkin_warden/" + renderState.getGeckolibData(PumpkinWardenRenderer.VARIANT) + "_hiding.png") : BiomesWeveGone.id("textures/entity/pumpkin_warden/" + renderState.getGeckolibData(PumpkinWardenRenderer.VARIANT) + ".png");
    }

    @Override
    public Identifier getAnimationResource(T pumpkinWarden) {
        return BiomesWeveGone.id("pumpkinwarden");
    }
}