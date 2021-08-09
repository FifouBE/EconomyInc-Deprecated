package fr.fifou.economy.entity.renderer;

import fr.fifou.economy.entity.EntityInformater;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderInformater extends RenderLiving<EntityInformater>
{
    public final ResourceLocation texture = new ResourceLocation("economy", "textures/entity/mob_informater.png");

    public RenderInformater(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn)
    {
        super(rendermanagerIn, modelbaseIn, shadowsizeIn);
    }

    protected ResourceLocation getEntityTexture(EntityInformater entity)
    {
        return this.texture;
    }
}
