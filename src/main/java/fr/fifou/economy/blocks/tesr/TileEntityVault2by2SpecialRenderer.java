package fr.fifou.economy.blocks.tesr;

import fr.fifou.economy.blocks.models.ModelVault2by2;
import fr.fifou.economy.blocks.models.ModelVault_PAD;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault2by2;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class TileEntityVault2by2SpecialRenderer<T extends TileEntityBlockVault2by2> extends TileEntitySpecialRenderer<T>
{
    private static ModelVault2by2 modelBlock = new ModelVault2by2();
    private static ModelVault_PAD modelBlock_add = new ModelVault_PAD();
    public static ResourceLocation texture;

    public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        this.checkTextures(te);
        GlStateManager.pushMatrix();
        GlStateManager.enableLighting();
        this.translateFromDirection(te, x, y, z);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);

        switch (te.getDirection())
        {
            case 0:
                GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
                break;

            case 1:
                GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
                break;

            case 2:
                GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                break;

            case 3:
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        }

        this.bindTexture(texture);
        GlStateManager.disableLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 1.0F, 112.0F);
        modelBlock.renderAll();
        GlStateManager.scale(0.5D, 0.5D, 0.35D);
        GlStateManager.translate(-0.93D, 0.8D, 0.8D);
        modelBlock_add.renderAll();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    public boolean isGlobalRenderer(T te)
    {
        return true;
    }

    public void checkTextures(T te)
    {
        if (te.hasItems().booleanValue())
        {
            texture = new ResourceLocation("economy", "textures/blocks_models/block_vault.png");
        }
        else
        {
            texture = new ResourceLocation("economy", "textures/blocks_models/block_vault_withoutgold.png");
        }
    }

    public void translateFromDirection(T te, double x, double y, double z)
    {
        switch (te.getDirection())
        {
            case 0:
                GlStateManager.translate(x, y + 1.5D, z);
                break;

            case 1:
                GlStateManager.translate(x + 1.0D, y + 1.5D, z);
                break;

            case 2:
                GlStateManager.translate(x + 1.0D, y + 1.5D, z + 1.0D);
                break;

            case 3:
                GlStateManager.translate(x, y + 1.5D, z + 1.0D);
        }
    }
}
