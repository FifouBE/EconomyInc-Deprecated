package fr.fifou.economy.capability;

import net.minecraft.entity.player.EntityPlayer;

public interface IMoney
{
    double getMoney();

    void setMoney(double var1);

    boolean getLinked();

    void setLinked(boolean var1);

    String getName();

    void setName(String var1);

    String getOnlineUUID();

    void setOnlineUUID(String var1);

    void sync(EntityPlayer var1);
}
