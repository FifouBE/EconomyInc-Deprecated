package fr.fifou.economy.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class BlocksRegistery
{
    public static Block BLOCK_VAULT = new BlockVault();
    public static Block BLOCK_SELLER = new BlockSeller();
    public static Block BLOCK_CHANGER = new BlockChanger();
    public static Block BLOCK_VAULT_2BY2 = new BlockVault2by2();
    public static Block BLOCK_ATM = new BlockAtm();
    public static Block BLOCK_VAULT_CRACKED = new BlockVaultCracked();
    public static Block BLOCK_BILLS = new BlockBills();

    public static void setBlockName(Block block, String name)
    {
        ((Block)block.setRegistryName("economy", name)).setUnlocalizedName("economy." + name);
    }

    public static void registerModel(Block block, int metadata)
    {
        if (metadata < 0)
        {
            metadata = 0;
        }

        String s = block.getRegistryName().toString();

        if (metadata > 0)
        {
            (new StringBuilder()).append(s).append("_m").append(String.valueOf(metadata)).toString();
        }

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), metadata, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }
}
