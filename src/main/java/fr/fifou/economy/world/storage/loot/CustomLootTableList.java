package fr.fifou.economy.world.storage.loot;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class CustomLootTableList
{
    public static final ResourceLocation CHESTS_SHOP_VILLAGE = CustomLootTableList.RegistrationHandler.create("chests/shop_bonus_chest");

    public static void registerLootTables()
    {
        CustomLootTableList.RegistrationHandler.LOOT_TABLES.forEach(LootTableList::register);
    }

    public static class RegistrationHandler
    {
        private static final Set<ResourceLocation> LOOT_TABLES = new HashSet<ResourceLocation>();

        protected static ResourceLocation create(String id)
        {
            ResourceLocation resourcelocation = new ResourceLocation("economy", id);
            LOOT_TABLES.add(resourcelocation);
            return resourcelocation;
        }
    }
}
