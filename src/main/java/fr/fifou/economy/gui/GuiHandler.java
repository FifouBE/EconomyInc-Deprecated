package fr.fifou.economy.gui;

import fr.fifou.economy.blocks.tileentity.TileEntityBlockChanger;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockSeller;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVault2by2;
import fr.fifou.economy.blocks.tileentity.TileEntityBlockVaultCracked;
import fr.fifou.economy.containers.ContainerChanger;
import fr.fifou.economy.containers.ContainerInformaterTrade;
import fr.fifou.economy.containers.ContainerSeller;
import fr.fifou.economy.containers.ContainerVault;
import fr.fifou.economy.containers.ContainerVault2by2;
import fr.fifou.economy.containers.ContainerVaultCracked;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
    public static final int ITEM_CARD_GUI = 0;
    public static final int ITEMCARD_ATM_GUI = 1;
    public static final int BLOCK_VAULT_NEW = 2;
    public static final int BLOCK_SELLER = 3;
    public static final int BLOCK_SELLER_BUY = 4;
    public static final int BLOCK_CHANGER = 5;
    public static final int BLOCK_VAULT_2BY2 = 6;
    public static final int BLOCK_VAULT_SETTINGS = 7;
    public static final int BLOCK_VAULT2BY2_SETTINGS = 8;
    public static final int BLOCK_VAULT_CRACKED = 9;
    public static final int BLOCK_VAULT_CRACKING = 10;
    public static final int ENTITY_INFOR_TRADE = 11;

    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        BlockPos blockpos = new BlockPos(x, y, z);
        TileEntity tileentity = world.getTileEntity(blockpos);

        if (ID == 2)
        {
            return new ContainerVault(player.inventory, (TileEntityBlockVault)tileentity);
        }
        else if (ID == 3)
        {
            return new ContainerSeller(player.inventory, (TileEntityBlockSeller)tileentity);
        }
        else if (ID == 5)
        {
            return new ContainerChanger(player.inventory, (TileEntityBlockChanger)tileentity);
        }
        else if (ID == 6)
        {
            return new ContainerVault2by2(player.inventory, (TileEntityBlockVault2by2)tileentity);
        }
        else if (ID == 9)
        {
            return new ContainerVaultCracked(player.inventory, (TileEntityBlockVaultCracked)tileentity);
        }
        else
        {
            return ID == 11 ? new ContainerInformaterTrade(player.inventory) : null;
        }
    }

    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        BlockPos blockpos = new BlockPos(x, y, z);
        TileEntity tileentity = world.getTileEntity(blockpos);

        if (ID == 0)
        {
            return new GuiItem();
        }
        else if (ID == 2)
        {
            return new GuiVaultNew(player.inventory, (TileEntityBlockVault)tileentity);
        }
        else if (ID == 3)
        {
            return new GuiSeller(player.inventory, (TileEntityBlockSeller)tileentity);
        }
        else if (ID == 4)
        {
            return new GuiSellerBuy((TileEntityBlockSeller)tileentity);
        }
        else if (ID == 5)
        {
            return new GuiChanger(player.inventory, (TileEntityBlockChanger)tileentity);
        }
        else if (ID == 6)
        {
            return new GuiVault2by2(player.inventory, (TileEntityBlockVault2by2)tileentity);
        }
        else if (ID == 7)
        {
            return new GuiVaultSettings((TileEntityBlockVault)tileentity);
        }
        else if (ID == 8)
        {
            return new GuiVault2by2Settings((TileEntityBlockVault2by2)tileentity);
        }
        else if (ID == 1)
        {
            return new GuiItemATM();
        }
        else if (ID == 9)
        {
            return new GuiVaultCracked(player.inventory, (TileEntityBlockVaultCracked)tileentity);
        }
        else if (ID == 10)
        {
            return new GuiCracking((TileEntityBlockVaultCracked)tileentity);
        }
        else
        {
            return ID == 11 ? new GuiInformaterTrade(player.inventory, x) : null;
        }
    }
}
