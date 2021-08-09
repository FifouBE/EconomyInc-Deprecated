package fr.fifou.economy.blocks.tesr;

import fr.fifou.economy.blocks.models.ModelVault;
import fr.fifou.economy.blocks.models.ModelVault_PAD;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class TileEntityVaultSpecialRenderer<T extends TileEntityBlockVault> extends TileEntitySpecialRenderer<T>
{
    private static ModelVault modelBlock = new ModelVault();
    private static ModelVault_PAD modelBlock_add = new ModelVault_PAD();
    public static ResourceLocation texture;

    public void render(T te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        this.checkTextures(te);
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5D, y + 0.75D, z + 0.5D);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(0.5D, 0.5D, 0.5D);

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
        modelBlock.renderAll();
        GlStateManager.scale(0.5D, 0.5D, 0.35D);
        GlStateManager.translate(-0.93D, 0.8D, 0.8D);
        modelBlock_add.renderAll();
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
}
