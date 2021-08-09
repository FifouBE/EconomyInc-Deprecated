package fr.fifou.economy.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ProviderMoney implements ICapabilitySerializable<NBTTagCompound>
{
    IMoney instance = (IMoney)CapabilityLoading.CAPABILITY_MONEY.getDefaultInstance();

    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == CapabilityLoading.CAPABILITY_MONEY;
    }

    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        return (T)(this.hasCapability(capability, facing) ? CapabilityLoading.CAPABILITY_MONEY.cast(this.instance) : null);
    }

    public NBTTagCompound serializeNBT()
    {
        return (NBTTagCompound)CapabilityLoading.CAPABILITY_MONEY.getStorage().writeNBT(CapabilityLoading.CAPABILITY_MONEY, this.instance, (EnumFacing)null);
    }

    public void deserializeNBT(NBTTagCompound nbt)
    {
        CapabilityLoading.CAPABILITY_MONEY.getStorage().readNBT(CapabilityLoading.CAPABILITY_MONEY, this.instance, (EnumFacing)null, nbt);
    }
}
