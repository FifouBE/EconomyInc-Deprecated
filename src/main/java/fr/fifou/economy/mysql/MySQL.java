package fr.fifou.economy.mysql;

import fr.fifou.economy.capability.CapabilityLoading;
import fr.fifou.economy.capability.IMoney;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;

public class MySQL
{
    private static Statement statement = null;
    private static Connection conn = null;
    private static ResultSet resultSet = null;
    private static boolean loggedIn = false;

    public static void login(String url, String user, String pass, String dbName)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://" + url + "/" + dbName, user, pass);
            System.out.println("Connexion \u00c3\u00a0 l'h\u00c3\u00b4te r\u00c3\u00a9ussi.");
            loggedIn = true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void createDataBase() throws SQLException
    {
        Statement statement = null;

        if (loggedIn)
        {
            try
            {
                statement = conn.createStatement();
                String s = "CREATE TABLE EconomyInc(id INT(11) UNIQUE AUTO_INCREMENT,name VARCHAR(255),uuid VARCHAR(255),money DOUBLE,linked BOOLEAN,status VARCHAR(255),synchroBDDtoSERV BOOLEAN)";
                statement.executeUpdate(s);
                System.out.println("Base de donn\u00c3\u00a9e cr\u00c3\u00a9\u00c3\u00a9e avec succ\u00c3\u00a8s.");
            }
            catch (SQLException sqlexception1)
            {
                sqlexception1.printStackTrace();
                System.out.println("Base de donn\u00c3\u00a9e d\u00c3\u00a9j\u00c3\u00a0 existante ou autres probl\u00c3\u00a8mes(voir logs serveurs).");
            }
            finally
            {
                try
                {
                    if (statement != null && !statement.isClosed())
                    {
                        statement.close();
                    }
                }
                catch (SQLException sqlexception)
                {
                    sqlexception.printStackTrace();
                }
            }
        }
        else
        {
            System.out.println("Merci de vous connectez \u00c3\u00a0 la base de donn\u00c3\u00a9e en premier.");
        }
    }

    public static void check(EntityPlayer player)
    {
        PreparedStatement preparedstatement = null;
        PreparedStatement preparedstatement1 = null;
        String s = "";
        boolean flag = false;
        double d0 = 0.0D;

        if (loggedIn)
        {
            try
            {
                String s1 = "SELECT * FROM economyinc WHERE uuid = ?";
                preparedstatement = conn.prepareStatement(s1);
                preparedstatement.setString(1, player.getUniqueID().toString());

                for (ResultSet resultset = preparedstatement.executeQuery(); resultset.next(); d0 = resultset.getDouble("money"))
                {
                    s = resultset.getString("uuid");
                    flag = resultset.getBoolean("synchroBDDtoSERV");
                }

                if (!s.equals(player.getUniqueID().toString()))
                {
                    insert(player);
                }
                else
                {
                    putOnline(player);

                    if (flag)
                    {
                        ((IMoney)player.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).setMoney(d0);

                        try
                        {
                            String s2 = "UPDATE economyinc SET synchroBDDtoSERV = ? WHERE uuid = ?";
                            preparedstatement1 = conn.prepareStatement(s2);
                            preparedstatement1.setBoolean(1, false);
                            preparedstatement1.setString(2, player.getUniqueID().toString());
                            preparedstatement1.executeUpdate();
                        }
                        catch (SQLException sqlexception2)
                        {
                            sqlexception2.printStackTrace();
                        }
                        finally
                        {
                            try
                            {
                                if (preparedstatement1 != null && !preparedstatement1.isClosed())
                                {
                                    preparedstatement1.close();
                                }
                            }
                            catch (SQLException sqlexception1)
                            {
                                sqlexception1.printStackTrace();
                            }
                        }
                    }
                }
            }
            catch (SQLException sqlexception3)
            {
                sqlexception3.printStackTrace();
            }
            finally
            {
                try
                {
                    if (preparedstatement != null && !preparedstatement.isClosed())
                    {
                        preparedstatement.close();
                    }
                }
                catch (SQLException sqlexception)
                {
                    sqlexception.printStackTrace();
                }
            }
        }
    }

    public static void insert(EntityPlayer player)
    {
        PreparedStatement preparedstatement = null;

        if (loggedIn)
        {
            try
            {
                String s = "INSERT INTO economyinc(name,uuid,money,linked,status,synchroBDDtoSERV) VALUES (?,?,?,?,?,?)";
                preparedstatement = conn.prepareStatement(s);
                preparedstatement.setString(1, player.getName());
                preparedstatement.setString(2, player.getUniqueID().toString());
                preparedstatement.setDouble(3, ((IMoney)player.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney());
                preparedstatement.setBoolean(4, ((IMoney)player.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getLinked());
                preparedstatement.setString(5, "online");
                preparedstatement.setBoolean(6, false);
                preparedstatement.executeUpdate();
            }
            catch (SQLException sqlexception1)
            {
                sqlexception1.printStackTrace();
            }
            finally
            {
                try
                {
                    if (preparedstatement != null && !preparedstatement.isClosed())
                    {
                        preparedstatement.close();
                    }
                }
                catch (SQLException sqlexception)
                {
                    sqlexception.printStackTrace();
                }
            }
        }
    }

    public static void update(EntityPlayer player)
    {
        PreparedStatement preparedstatement = null;

        if (loggedIn)
        {
            try
            {
                String s = "UPDATE economyinc SET money = ?, linked = ?, status = ? WHERE uuid = ?";
                preparedstatement = conn.prepareStatement(s);
                preparedstatement.setDouble(1, ((IMoney)player.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getMoney());
                preparedstatement.setBoolean(2, ((IMoney)player.getCapability(CapabilityLoading.CAPABILITY_MONEY, (EnumFacing)null)).getLinked());
                preparedstatement.setString(3, "offline");
                preparedstatement.setString(4, player.getUniqueID().toString());
                preparedstatement.executeUpdate();
            }
            catch (SQLException sqlexception1)
            {
                sqlexception1.printStackTrace();
            }
            finally
            {
                try
                {
                    if (preparedstatement != null && !preparedstatement.isClosed())
                    {
                        preparedstatement.close();
                    }
                }
                catch (SQLException sqlexception)
                {
                    sqlexception.printStackTrace();
                }
            }
        }
    }

    public static void putOnline(EntityPlayer player)
    {
        PreparedStatement preparedstatement = null;

        try
        {
            String s = "UPDATE economyinc SET status = ? WHERE uuid = ?";
            preparedstatement = conn.prepareStatement(s);
            preparedstatement.setString(1, "online");
            preparedstatement.setString(2, player.getUniqueID().toString());
            preparedstatement.executeUpdate();
        }
        catch (SQLException sqlexception1)
        {
            sqlexception1.printStackTrace();
        }
        finally
        {
            try
            {
                if (preparedstatement != null && !preparedstatement.isClosed())
                {
                    preparedstatement.close();
                }
            }
            catch (SQLException sqlexception)
            {
                sqlexception.printStackTrace();
            }
        }
    }
}
