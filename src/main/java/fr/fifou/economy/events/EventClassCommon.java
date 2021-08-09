package fr.fifou.economy.events;

import fr.fifou.economy.ConfigFile;
import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import fr.fifou.economy.capability.ProviderMoney;
import fr.fifou.economy.items.ItemsRegistery;
import fr.fifou.economy.mysql.MySQL;
import fr.fifou.economy.world.saveddata.ChunksWorldSavedData;
import fr.fifou.economy.world.saveddata.PlotsChunkData;
import fr.fifou.economy.world.saveddata.PlotsData;
import fr.fifou.economy.world.saveddata.PlotsWorldSavedData;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

public class EventClassCommon
{
    private static final Random rand = new Random();

    @SubscribeEvent
    public void onSmeltingEvent(ItemSmeltedEvent event)
    {
        if (ConfigFile.goldNuggetRecipe && event.smelting.getItem().equals(ItemsRegistery.ITEM_GOLDNUGGET))
        {
            double d0 = rand.nextDouble();
            String s = Double.toString(d0);
            String s1 = s.substring(0, 4);
            event.smelting.setTagInfo("weight", new NBTTagString(s1));
        }
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof EntityPlayer)
        {
            event.addCapability(new ResourceLocation("economy", ":MONEY"), new ProviderMoney());
        }
    }

    @SubscribeEvent
    public void clonePlayer(Clone event)
    {
        IMoney imoney = CapabilityLoading.getMoneyHandler(event.getOriginal());
        IMoney imoney1 = CapabilityLoading.getMoneyHandler(event.getEntity());
        imoney1.setMoney(imoney.getMoney());
        imoney1.setName(imoney.getName());
        imoney1.setOnlineUUID(imoney.getOnlineUUID());
        imoney1.sync(event.getEntityPlayer());
    }

    @SubscribeEvent
    public void onplayerLoggedInEvent(PlayerLoggedInEvent event)
    {
        ((IMoney)event.player.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).sync((EntityPlayerMP)event.player);
        ((IMoney)event.player.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setName(event.player.getName());
        ((IMoney)event.player.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setOnlineUUID(event.player.getUniqueID().toString());

        if (ConfigFile.connectDB)
        {
            MySQL.check(event.player);
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedOutEvent(PlayerLoggedOutEvent event)
    {
        if (ConfigFile.connectDB)
        {
            MySQL.update(event.player);
        }
    }

    @SubscribeEvent
    public void onPlacedBlock(PlaceEvent event)
    {
        World world = event.getWorld();
        List<ChunkPos> list = new ArrayList<ChunkPos>();
        MapStorage mapstorage = world.getPerWorldStorage();
        ChunksWorldSavedData chunksworldsaveddata = (ChunksWorldSavedData)mapstorage.getOrLoadData(ChunksWorldSavedData.class, "economy_PlotsChunkData");

        if (chunksworldsaveddata != null)
        {
            List<PlotsChunkData> list1 = ChunksWorldSavedData.get(world).getListContainer();

            for (int i = 0; i < list1.size(); ++i)
            {
                PlotsChunkData plotschunkdata = list1.get(i);

                for (int j = 0; j < plotschunkdata.getList().size(); ++j)
                {
                    String s = (String)plotschunkdata.getList().get(j);
                    int k = Integer.valueOf(s.substring(s.indexOf("[") + 1, s.indexOf(","))).intValue();
                    int l = Integer.valueOf(s.substring(s.indexOf(",") + 2, s.indexOf("]"))).intValue();
                    list.add(new ChunkPos(k, l));
                }
            }
        }

        for (ChunkPos chunkpos : list)
        {
            if ((new ChunkPos(event.getPos())).equals(chunkpos))
            {
                Vec3d vec3d = new Vec3d((double)event.getPos().getX(), (double)event.getPos().getY(), (double)event.getPos().getZ());
                List<AxisAlignedBB> list2 = new ArrayList<AxisAlignedBB>();
                PlotsWorldSavedData plotsworldsaveddata = (PlotsWorldSavedData)mapstorage.getOrLoadData(PlotsWorldSavedData.class, "economy_PlotsData");
                UUID uuid = null;

                if (plotsworldsaveddata != null)
                {
                    List<PlotsData> list3 = PlotsWorldSavedData.get(world).getListContainer();

                    for (int i1 = 0; i1 < list3.size(); ++i1)
                    {
                        PlotsData plotsdata = (PlotsData)PlotsWorldSavedData.get(world).getListContainer().get(i1);

                        for (int j1 = 0; j1 < plotsdata.getList().size(); ++j1)
                        {
                            uuid = UUID.fromString((String)plotsdata.getList().get(1));
                            int k1 = Integer.valueOf((String)plotsdata.getList().get(2)).intValue();
                            int l1 = Integer.valueOf((String)plotsdata.getList().get(3)).intValue();
                            int i2 = Integer.valueOf((String)plotsdata.getList().get(4)).intValue();
                            int j2 = Integer.valueOf((String)plotsdata.getList().get(5)).intValue();
                            list2.add((new AxisAlignedBB((double)k1, 0.0D, (double)l1, (double)i2, 255.0D, (double)j2)).expand(2.0D, 1.0D, 2.0D));
                        }
                    }
                }

                for (AxisAlignedBB axisalignedbb : list2)
                {
                    if (axisalignedbb.contains(vec3d) && uuid != null)
                    {
                        if (!event.getPlayer().getUniqueID().equals(uuid))
                        {
                            event.setCanceled(true);
                        }
                        else if (event.getPlayer().isCreative())
                        {
                            event.setCanceled(false);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onBreakBlock(BreakEvent event)
    {
        World world = event.getWorld();
        List<ChunkPos> list = new ArrayList<ChunkPos>();
        MapStorage mapstorage = world.getPerWorldStorage();
        ChunksWorldSavedData chunksworldsaveddata = (ChunksWorldSavedData)mapstorage.getOrLoadData(ChunksWorldSavedData.class, "economy_PlotsChunkData");

        if (chunksworldsaveddata != null)
        {
            List<PlotsChunkData> list1 = ChunksWorldSavedData.get(world).getListContainer();

            for (int i = 0; i < list1.size(); ++i)
            {
                PlotsChunkData plotschunkdata = list1.get(i);

                for (int j = 0; j < plotschunkdata.getList().size(); ++j)
                {
                    String s = (String)plotschunkdata.getList().get(j);
                    int k = Integer.valueOf(s.substring(s.indexOf("[") + 1, s.indexOf(","))).intValue();
                    int l = Integer.valueOf(s.substring(s.indexOf(",") + 2, s.indexOf("]"))).intValue();
                    list.add(new ChunkPos(k, l));
                }
            }
        }

        for (ChunkPos chunkpos : list)
        {
            if ((new ChunkPos(event.getPos())).equals(chunkpos))
            {
                Vec3d vec3d = new Vec3d((double)event.getPos().getX(), (double)event.getPos().getY(), (double)event.getPos().getZ());
                List<AxisAlignedBB> list2 = new ArrayList<AxisAlignedBB>();
                PlotsWorldSavedData plotsworldsaveddata = (PlotsWorldSavedData)mapstorage.getOrLoadData(PlotsWorldSavedData.class, "economy_PlotsData");
                UUID uuid = null;

                if (plotsworldsaveddata != null)
                {
                    List<PlotsData> list3 = PlotsWorldSavedData.get(world).getListContainer();

                    for (int i1 = 0; i1 < list3.size(); ++i1)
                    {
                        PlotsData plotsdata = (PlotsData)PlotsWorldSavedData.get(world).getListContainer().get(i1);

                        for (int j1 = 0; j1 < plotsdata.getList().size(); ++j1)
                        {
                            uuid = UUID.fromString((String)plotsdata.getList().get(1));
                            int k1 = Integer.valueOf((String)plotsdata.getList().get(2)).intValue();
                            int l1 = Integer.valueOf((String)plotsdata.getList().get(3)).intValue();
                            int i2 = Integer.valueOf((String)plotsdata.getList().get(4)).intValue();
                            int j2 = Integer.valueOf((String)plotsdata.getList().get(5)).intValue();
                            list2.add((new AxisAlignedBB((double)k1, 0.0D, (double)l1, (double)i2, 255.0D, (double)j2)).expand(2.0D, 1.0D, 2.0D));
                        }
                    }
                }

                for (AxisAlignedBB axisalignedbb : list2)
                {
                    if (axisalignedbb.contains(vec3d) && uuid != null)
                    {
                        if (!event.getPlayer().getUniqueID().equals(uuid))
                        {
                            event.setCanceled(true);
                        }
                        else if (event.getPlayer().isCreative())
                        {
                            event.setCanceled(false);
                        }
                    }
                }
            }
        }
    }
}
