package fr.fifou.economy.blocks.tesr;

import fr.fifou.economy.blocks.models.ModelVault;
import fr.fifou.economy.blocks.models.ModelVault_PAD;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultCracked;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityVaultCrackedSpecialRenderer extends TileEntitySpecialRenderer
{
    private static ModelVault modelBlock = new ModelVault();
    private static ModelVault_PAD modelBlock_add = new ModelVault_PAD();
    public static ResourceLocation texture;

    public void renderTileEntityVaultCrackedAt(TileEntityBlockVaultCracked tile, double posX, double posY, double posZ, float partialTicks, int damageCount, float alpha)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX + 0.5D, posY + 0.75D, posZ + 0.5D);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        GlStateManager.rotate(90.0F * (float)tile.getDirection(), 0.0F, 1.0F, 0.0F);

        if (tile.hasItems().booleanValue())
        {
            texture = new ResourceLocation("economy", "textures/blocks_models/block_vault.png");
        }
        else
        {
            texture = new ResourceLocation("economy", "textures/blocks_models/block_vault_withoutgold.png");
        }

        this.bindTexture(texture);
        modelBlock.renderAll();
        GlStateManager.scale(0.5D, 0.5D, 0.35D);
        GlStateManager.translate(-0.93D, 0.8D, 0.8D);
        modelBlock_add.renderAll();
        GlStateManager.popMatrix();
    }

    public void func_192841_a(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        this.renderTileEntityVaultCrackedAt((TileEntityBlockVaultCracked)te, x, y, z, partialTicks, destroyStage, alpha);
    }
}
