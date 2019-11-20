/**
 * Dies ist ein Plugin von FrameDev
 * Bitte nichts ?ndern, @Copyright by FrameDev 
 */
package de.framedev.frameapibungeecord.main;


import net.md_5.bungee.api.ProxyServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Executors;

/**
 * @author Darryl
 *
 */
public class MYSQL {

    public static String MySQLPrefix = "§a[§bMySQL§a]";
    public static String host = Config.getConfig().getString("MySQL.Host");
    public static String user = Config.getConfig().getString("MySQL.User");
    public static String password = Config.getConfig().getString("MySQL.Password");
    public static String database = Config.getConfig().getString("MySQL.Database");
    public static String port = "3306";
    public static Connection con;

    public MYSQL() {
    }

    public static Connection getConnection() {
        if (con == null) {
            close();
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
                return con;
            } catch (SQLException ex) {

            }
        } else {
            close();
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
                return con;
            } catch (SQLException ex) {

            }
        }
        return con;
    }

    // connect
    @SuppressWarnings( "deprecation" )
    public static void connect() {
        if (con == null) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
                con.setNetworkTimeout(Executors.newFixedThreadPool(100), 1000000);
                ProxyServer.getInstance().getConsole().sendMessage("§aMySQL Connected!");
            } catch (SQLException e) {
            }
        }
    }

    public static void close() {
        if (con != null) {
            try {
                if (con != null) {
                    con.close();
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
