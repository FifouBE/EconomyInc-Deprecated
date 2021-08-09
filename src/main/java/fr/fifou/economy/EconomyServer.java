package fr.fifou.economy;

import fr.fifou.economy.mysql.MySQL;
import java.io.File;
import java.sql.SQLException;

public class EconomyServer extends EconomyCommon
{
    public void preInit(File configFile)
    {
        super.preInit(configFile);
    }

    public void init()
    {
        if (ConfigFile.connectDB)
        {
            MySQL.login(ConfigFile.urlDB, ConfigFile.userDB, ConfigFile.passwordDB, ConfigFile.dbName);

            try
            {
                MySQL.createDataBase();
            }
            catch (SQLException sqlexception)
            {
                sqlexception.printStackTrace();
                System.out.println("Probl\u00ef\u00bf\u00bdme de connexion \u00ef\u00bf\u00bd la base de donn\u00ef\u00bf\u00bde ou tableau d\u00ef\u00bf\u00bdj\u00ef\u00bf\u00bd existant.");
            }
        }

        super.init();
    }
}
