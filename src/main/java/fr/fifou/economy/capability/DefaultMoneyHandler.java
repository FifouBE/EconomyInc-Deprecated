package fr.fifou.economy.capability;

import fr.fifou.economy.packets.PacketMoneyData;
import fr.fifou.economy.packets.PacketsRegistery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class DefaultMoneyHandler implements IMoney
{
    private double money;
    private boolean linked = false;
    private String name = "";
    private String onlineUUID = "";

    public double getMoney()
    {
        return this.money;
    }

    public void setMoney(double money)
    {
        this.money = money;
    }

    public boolean getLinked()
    {
        return this.linked;
    }

    public void setLinked(boolean linked)
    {
        this.linked = linked;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getOnlineUUID()
    {
        return this.onlineUUID;
    }

    public void setOnlineUUID(String onUUID)
    {
        this.onlineUUID = onUUID;
    }

    public void sync(EntityPlayer player)
    {
        PacketsRegistery.network.sendTo(new PacketMoneyData(this.money, this.linked, this.name, this.onlineUUID), (EntityPlayerMP)player);
    }
}
