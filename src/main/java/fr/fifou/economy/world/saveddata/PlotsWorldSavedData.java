package fr.fifou.economy.world.saveddata;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class PlotsWorldSavedData extends WorldSavedData
{
    private static final String DATA_NAME = "economy_PlotsData";
    List<PlotsData> listContainer = new ArrayList<PlotsData>();

    public PlotsWorldSavedData()
    {
        super("economy_PlotsData");
    }

    public PlotsWorldSavedData(String name)
    {
        super(name);
    }

    /**
     * reads in data from the NBTTagCompound into this MapDataBase
     */
    public void readFromNBT(NBTTagCompound nbt)
    {
        NBTTagList nbttaglist = nbt.getTagList("listContainer", 9);
        String s = "";
        String s1 = "";
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;
        int i1 = 0;
        float f = 0.0F;
        boolean flag = false;

        for (int j1 = 0; j1 < nbttaglist.tagCount(); ++j1)
        {
            NBTTagList nbttaglist1 = (NBTTagList)nbttaglist.get(j1);
            s = nbttaglist1.getStringTagAt(0);
            s1 = nbttaglist1.getStringTagAt(1);
            i = Integer.valueOf(nbttaglist1.getStringTagAt(2)).intValue();
            j = Integer.valueOf(nbttaglist1.getStringTagAt(3)).intValue();
            k = Integer.valueOf(nbttaglist1.getStringTagAt(4)).intValue();
            l = Integer.valueOf(nbttaglist1.getStringTagAt(5)).intValue();
            i1 = Integer.valueOf(nbttaglist1.getStringTagAt(6)).intValue();
            f = Float.valueOf(nbttaglist1.getStringTagAt(7)).floatValue();
            flag = Boolean.getBoolean(nbttaglist1.getStringTagAt(8));
            PlotsData plotsdata = new PlotsData(s, s1, i, j, k, l, i1, (double)f, flag);
            this.listContainer.add(plotsdata);
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.listContainer.size(); ++i)
        {
            NBTTagList nbttaglist1 = new NBTTagList();

            for (int j = 0; j < ((PlotsData)this.listContainer.get(i)).getList().size(); ++j)
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

    public static PlotsWorldSavedData get(World world)
    {
        MapStorage mapstorage = world.getPerWorldStorage();
        PlotsWorldSavedData plotsworldsaveddata = (PlotsWorldSavedData)mapstorage.getOrLoadData(PlotsWorldSavedData.class, "economy_PlotsData");

        if (plotsworldsaveddata == null)
        {
            plotsworldsaveddata = new PlotsWorldSavedData();
            world.setData("economy_PlotsData", plotsworldsaveddata);
        }
        else
        {
            world.setData("economy_PlotsData", plotsworldsaveddata);
        }

        return plotsworldsaveddata;
    }

    public List<PlotsData> getListContainer()
    {
        return this.listContainer;
    }
}
