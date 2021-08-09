package fr.fifou.economy;

import fr.fifou.economy.blocks.tesr.TileEntityBillsSpecialRenderer;
import fr.fifou.economy.blocks.tesr.TileEntityVault2by2SpecialRenderer;
import fr.fifou.economy.blocks.tesr.TileEntityVaultCrackedSpecialRenderer;
import fr.fifou.economy.blocks.tesr.TileEntityVaultSpecialRenderer;
import fr.fifou.economy.blocks.tesr.inventory.TileEntityInventoryRenderHelper;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockBills;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultCracked;
import fr.fifou.economy.entity.EntityInformater;
import fr.fifou.economy.entity.renderer.RenderInformater;
import fr.fifou.economy.items.ItemsRegistery;
import fr.fifou.economy.model.ModelInformater;
import java.io.File;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class EconomyClient extends EconomyCommon
{
    public void preInit(File configFile)
    {
        super.preInit(configFile);
        RenderingRegistry.registerEntityRenderingHandler(EntityInformater.class, (rm) ->
        {
            return new RenderInformater(rm, new ModelInformater(), 0.5F);
        });
    }

    public void init()
    {
        super.init();
        ItemsRegistery.registerItemsModels();
        TileEntityItemStackRenderer.instance = new TileEntityInventoryRenderHelper();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockVault.class, new TileEntityVaultSpecialRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockVault2by2.class, new TileEntityVault2by2SpecialRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockVaultCracked.class, new TileEntityVaultCrackedSpecialRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockBills.class, new TileEntityBillsSpecialRenderer());
    }
}
