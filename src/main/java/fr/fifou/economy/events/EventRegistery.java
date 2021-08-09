package fr.fifou.economy.events;

import fr.fifou.economy.RegisteringHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class EventRegistery
{
    public static void register(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new RegisteringHandler());

        if (event.getSide().isClient())
        {
            MinecraftForge.EVENT_BUS.register(new EventClassClient());
            MinecraftForge.EVENT_BUS.register(EventClassClient.class);
        }

        MinecraftForge.EVENT_BUS.register(new EventClassCommon());
        MinecraftForge.EVENT_BUS.register(EventClassCommon.class);
    }
}
