package fr.fifou.economy.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StorageMoney implements IStorage<IMoney>
{
    public NBTBase writeNBT(Capability<IMoney> capability, IMoney instance, EnumFacing side)
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setDouble("money", instance.getMoney());
        nbttagcompound.setBoolean("linked", instance.getLinked());
        nbttagcompound.setString("name", instance.getName());
        nbttagcompound.setString("onlineUUID", instance.getOnlineUUID());
        return nbttagcompound;
    }

    public void readNBT(Capability<IMoney> capability, IMoney instance, EnumFacing side, NBTBase nbt)
    {
        NBTTagCompound nbttagcompound = (NBTTagCompound)nbt;
        instance.setMoney(nbttagcompound.getDouble("money"));
        instance.setLinked(nbttagcompound.getBoolean("linked"));
        instance.setName(nbttagcompound.getString("name"));
        instance.setOnlineUUID(nbttagcompound.getString("onlineUUID"));
    }
}
