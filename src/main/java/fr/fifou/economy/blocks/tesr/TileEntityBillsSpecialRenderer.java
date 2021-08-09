package fr.fifou.economy.blocks.tesr;

import fr.fifou.economy.blocks.models.ModelBills;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockBills;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileEntityBillsSpecialRenderer extends TileEntitySpecialRenderer
{
    private static ModelBills modelBlock = new ModelBills();
    public static ResourceLocation texture = new ResourceLocation("economy", "textures/blocks_models/block_bills_0.png");

    public void renderTileEntityBillsAt(TileEntityBlockBills tile, double posX, double posY, double posZ, float partialTicks, int damageCount, float alpha)
    {
        GlStateManager.pushMatrix();

        if (tile != null)
        {
            switch (tile.getDirection())
            {
                case 0:
                    GlStateManager.translate(posX + 0.125D, posY + 0.5299999713897705D, posZ + 0.25D);
                    break;

                case 1:
                    GlStateManager.translate(posX + 0.75D, posY + 0.5299999713897705D, posZ + 0.125D);
                    break;

                case 2:
                    GlStateManager.translate(posX + 0.875D, posY + 0.5299999713897705D, posZ + 0.75D);
                    break;

                case 3:
                    GlStateManager.translate(posX + 0.25D, posY + 0.5299999713897705D, posZ + 0.875D);
            }
        }

        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(90.0F * (float)tile.getDirection(), 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(0.333F, 0.333F, 0.333F);
        this.checkBillRef(tile);
        this.bindTexture(texture);
        modelBlock.renderAll(tile);
        GlStateManager.popMatrix();
    }

    public void func_192841_a(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        this.renderTileEntityBillsAt((TileEntityBlockBills)te, x, y, z, partialTicks, destroyStage, alpha);
    }

    public void checkBillRef(TileEntityBlockBills tile)
    {
        String s = tile.getBill();
        byte b0 = -1;

        switch (s.hashCode())
        {
            case -2095573238:
                if (s.equals("item.item_fivehundreedb"))
                {
                    b0 = 7;
                }

                break;

            case -1956797619:
                if (s.equals("item.item_oneb"))
                {
                    b0 = 0;
                }

                break;

            case -1956657034:
                if (s.equals("item.item_tenb"))
                {
                    b0 = 2;
                }

                break;

            case -539628161:
                if (s.equals("item.item_fiveb"))
                {
                    b0 = 1;
                }

                break;

            case 1033927264:
                if (s.equals("item.item_twentyb"))
                {
                    b0 = 3;
                }

                break;

            case 1094075866:
                if (s.equals("item.item_fiftybe"))
                {
                    b0 = 4;
                }

                break;

            case 1452084658:
                if (s.equals("item.item_twohundreedb"))
                {
                    b0 = 6;
                }

                break;

            case 1599489276:
                if (s.equals("item.item_hundreedb"))
                {
                    b0 = 5;
                }
        }

        switch (b0)
        {
            case 0:
                texture = new ResourceLocation("economy", "textures/blocks_models/block_bills_1.png");
                break;

            case 1:
                texture = new ResourceLocation("economy", "textures/blocks_models/block_bills_5.png");
                break;

            case 2:
                texture = new ResourceLocation("economy", "textures/blocks_models/block_bills_10.png");
                break;

            case 3:
                texture = new ResourceLocation("economy", "textures/blocks_models/block_bills_20.png");
                break;

            case 4:
                texture = new ResourceLocation("economy", "textures/blocks_models/block_bills_50.png");
                break;

            case 5:
                texture = new ResourceLocation("economy", "textures/blocks_models/block_bills_100.png");
                break;

            case 6:
                texture = new ResourceLocation("economy", "textures/blocks_models/block_bills_200.png");
                break;

            case 7:
                texture = new ResourceLocation("economy", "textures/blocks_models/block_bills_500.png");
        }
    }
}
