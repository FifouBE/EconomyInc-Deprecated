package fr.fifou.economy.world.saveddata;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class ChunksWorldSavedData extends WorldSavedData
{
    private static final String DATA_NAME = "economy_PlotsChunkData";
    List<PlotsChunkData> listContainer = new ArrayList<PlotsChunkData>();

    public ChunksWorldSavedData()
    {
        super("economy_PlotsChunkData");
    }

    public ChunksWorldSavedData(String name)
    {
        super(name);
    }

    /**
     * reads in data from the NBTTagCompound into this MapDataBase
     */
    public void readFromNBT(NBTTagCompound nbt)
    {
        NBTTagList nbttaglist = nbt.getTagList("listContainer", 9);
        List<ChunkPos> list = new ArrayList<ChunkPos>();

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagList nbttaglist1 = (NBTTagList)nbttaglist.get(i);

            for (int j = 0; j < nbttaglist1.tagCount(); ++j)
            {
                String s = nbttaglist1.getStringTagAt(j);
                int k = Integer.valueOf(s.substring(s.indexOf("[") + 1, s.indexOf(","))).intValue();
                int l = Integer.valueOf(s.substring(s.indexOf(",") + 2, s.indexOf("]"))).intValue();
                ChunkPos chunkpos = new ChunkPos(k, l);
                list.add(chunkpos);
            }

            PlotsChunkData plotschunkdata = new PlotsChunkData(list);
            this.listContainer.add(plotschunkdata);
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.listContainer.size(); ++i)
        {
            NBTTagList nbttaglist1 = new NBTTagList();

            for (int j = 0; j < ((PlotsChunkData)this.listContainer.get(i)).getList().size(); ++j)
            {
                String s = (String)(this.listContainer.get(i)).getList().get(j);

                if (s != null)
                {
                    nbttaglist1.appendTag(new NBTTagString(s));
                }
            }

            nbttaglist.appendTag(nbttaglist1);
        }

        compound.setTag("listContainer", nbttaglist);
        return compound;
    }

    public static ChunksWorldSavedData get(World world)
    {
        MapStorage mapstorage = world.getPerWorldStorage();
        ChunksWorldSavedData chunksworldsaveddata = (ChunksWorldSavedData)mapstorage.getOrLoadData(ChunksWorldSavedData.class, "economy_PlotsChunkData");

        if (chunksworldsaveddata == null)
        {
            chunksworldsaveddata = new ChunksWorldSavedData();
            world.setData("economy_PlotsChunkData", chunksworldsaveddata);
        }
        else
        {
            world.setData("economy_PlotsChunkData", chunksworldsaveddata);
        }

        return chunksworldsaveddata;
    }

    public List<PlotsChunkData> getListContainer()
    {
        return this.listContainer;
    }
}
