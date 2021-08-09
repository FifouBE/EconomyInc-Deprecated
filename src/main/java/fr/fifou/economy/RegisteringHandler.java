package fr.fifou.economy;

import fr.fifou.economy.blocks.BlocksRegistery;
import fr.fifou.economy.items.ItemsRegistery;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegisteringHandler
{
    @SubscribeEvent
    public void registerBlocks(Register<Block> event)
    {
        event.getRegistry().registerAll(new Block[] {BlocksRegistery.BLOCK_VAULT});
        event.getRegistry().registerAll(new Block[] {BlocksRegistery.BLOCK_SELLER});
        event.getRegistry().registerAll(new Block[] {BlocksRegistery.BLOCK_CHANGER});
        event.getRegistry().registerAll(new Block[] {BlocksRegistery.BLOCK_VAULT_2BY2});
        event.getRegistry().registerAll(new Block[] {BlocksRegistery.BLOCK_ATM});
        event.getRegistry().registerAll(new Block[] {BlocksRegistery.BLOCK_VAULT_CRACKED});
        event.getRegistry().registerAll(new Block[] {BlocksRegistery.BLOCK_BILLS});
    }

    @SubscribeEvent
    public void registerItems(Register<Item> event)
    {
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_CREDITCARD});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_ONEB});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_FIVEB});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_TENB});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_TWENTYB});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_FIFTYB});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_HUNDREEDB});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_TWOHUNDREEDB});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_FIVEHUNDREEDB});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_REMOVER});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_GEAR});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_GEARSMECHANISM});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_MICROCHIP});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_GOLDNUGGET});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_PACKET_ONEB});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_PACKET_FIVEB});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_PACKET_TENB});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_PACKET_TWENTYB});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_PACKET_FIFTYB});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_PACKET_HUNDREEDB});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_PACKET_TWOHUNDREEDB});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_PACKET_FIVEHUNDREEDB});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.ITEM_VAULT_CRACKER});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.BLOCK_VAULT_ITEM});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.BLOCK_SELLER_ITEM});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.BLOCK_CHANGER_ITEM});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.BLOCK_VAULT_2BY2_ITEM});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.BLOCK_ATM});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.BLOCK_VAULT_CRACKED});
        event.getRegistry().registerAll(new Item[] {ItemsRegistery.BLOCK_BILLS});
    }

    @SubscribeEvent
    public static void registerItemModels(ModelRegistryEvent event)
    {
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_CREDITCARD, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_ONEB, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_FIVEB, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_TENB, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_TWENTYB, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_FIFTYB, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_HUNDREEDB, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_TWOHUNDREEDB, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_FIVEHUNDREEDB, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_GEAR, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_GEARSMECHANISM, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_MICROCHIP, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_REMOVER, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_GOLDNUGGET, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_PACKET_ONEB, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_PACKET_FIVEB, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_PACKET_TENB, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_PACKET_TWENTYB, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_PACKET_FIFTYB, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_PACKET_HUNDREEDB, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_PACKET_TWOHUNDREEDB, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_PACKET_FIVEHUNDREEDB, 0);
        ItemsRegistery.registerModel(ItemsRegistery.ITEM_VAULT_CRACKER, 0);
        ItemsRegistery.registerModel(ItemsRegistery.BLOCK_VAULT_ITEM, 0);
        ItemsRegistery.registerModel(ItemsRegistery.BLOCK_SELLER_ITEM, 0);
        ItemsRegistery.registerModel(ItemsRegistery.BLOCK_CHANGER_ITEM, 0);
        ItemsRegistery.registerModel(ItemsRegistery.BLOCK_VAULT_2BY2_ITEM, 0);
        ItemsRegistery.registerModel(ItemsRegistery.BLOCK_ATM, 0);
        ItemsRegistery.registerModel(ItemsRegistery.BLOCK_VAULT_CRACKED, 0);
        ItemsRegistery.registerModel(ItemsRegistery.BLOCK_BILLS, 0);
    }

    @SubscribeEvent
    public static void registerBlockModels(ModelRegistryEvent event)
    {
        BlocksRegistery.registerModel(BlocksRegistery.BLOCK_VAULT, 0);
        BlocksRegistery.registerModel(BlocksRegistery.BLOCK_SELLER, 0);
        BlocksRegistery.registerModel(BlocksRegistery.BLOCK_CHANGER, 0);
        BlocksRegistery.registerModel(BlocksRegistery.BLOCK_VAULT_2BY2, 0);
        BlocksRegistery.registerModel(BlocksRegistery.BLOCK_ATM, 0);
        BlocksRegistery.registerModel(BlocksRegistery.BLOCK_VAULT_CRACKED, 0);
        BlocksRegistery.registerModel(BlocksRegistery.BLOCK_BILLS, 0);
    }
}
