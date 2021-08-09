package fr.fifou.economy;

import fr.fifou.economy.blocks.tileentity.TileEntityRegistery;
import fr.fifou.economy.capability.DefaultMoneyHandler;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.capability.StorageMoney;
import fr.fifou.economy.commands.CommandBalance;
import fr.fifou.economy.commands.CommandPlots;
import fr.fifou.economy.commands.CommandPlotsBuy;
import fr.fifou.economy.entity.EntityInformater;
import fr.fifou.economy.events.EventRegistery;
import fr.fifou.economy.gui.GuiHandler;
import fr.fifou.economy.items.ItemsRegistery;
import fr.fifou.economy.packets.PacketsRegistery;
import fr.fifou.economy.world.gen.structure.VillageComponentShop;
import fr.fifou.economy.world.gen.structure.VillageHandlerShop;
import fr.fifou.economy.world.storage.loot.CustomLootTableList;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;
import org.apache.logging.log4j.Logger;

@EventBusSubscriber
@Mod(
    modid = "economy",
    name = "Mod Economy",
    version = "1.5",
    acceptedMinecraftVersions = "[1.12.2]"
)
public class ModEconomy
{
    public static final String MODID = "economy";
    @Instance("economy")
    public static ModEconomy instance;
    @SidedProxy(
        clientSide = "fr.fifou.economy.EconomyClient",
        serverSide = "fr.fifou.economy.EconomyServer"
    )
    public static EconomyCommon proxy;
    public static Logger logger;
    public static CreativeTabs tabEconomy = new TabEconomy(CreativeTabs.getNextID(), "Economy Inc.");

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit(event.getSuggestedConfigurationFile());
        ConfigFile.init(event);

        if (ConfigFile.goldNuggetRecipe)
        {
            ItemStack itemstack = new ItemStack(ItemsRegistery.ITEM_GOLDNUGGET);
            GameRegistry.addSmelting(new ItemStack(Items.GOLD_INGOT), itemstack, 1.0F);
        }

        EventRegistery.register(event);
        TileEntityRegistery.register();
        PacketsRegistery.registerPackets(event);
        CustomLootTableList.registerLootTables();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        PermissionAPI.registerNode("economy.command.balance", DefaultPermissionLevel.OP, "Allows players to use the balance command");
        PermissionAPI.registerNode("economy.command.plot", DefaultPermissionLevel.OP, "Allows OP players to create plot");
        PermissionAPI.registerNode("economy.command.plotbuy", DefaultPermissionLevel.ALL, "Allows players to buy plots");
        PermissionAPI.registerNode("unlimited_stack", DefaultPermissionLevel.OP, "Allows OP players to use the unlimited stack");
        CapabilityManager.INSTANCE.register(IMoney.class, new StorageMoney(), DefaultMoneyHandler.class);

        if (ConfigFile.doesBankGenerateInVillages)
        {
            MapGenStructureIO.registerStructureComponent(VillageComponentShop.class, "economyVillageShopComponent");
            VillagerRegistry.instance().registerVillageCreationHandler(new VillageHandlerShop());
        }

        EntityRegistry.registerModEntity(new ResourceLocation("economy", "entityInformater"), EntityInformater.class, "entityInformater", 1, instance, 64, 1, true, 2566965, 6515058);
    }

    @EventHandler
    public void PostInit(FMLPostInitializationEvent event)
    {
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandBalance());
        event.registerServerCommand(new CommandPlots());
        event.registerServerCommand(new CommandPlotsBuy());
    }
}
