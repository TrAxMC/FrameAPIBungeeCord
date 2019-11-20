package de.framedev.frameapibungeecord.main;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class FrameAPIBungee extends Plugin {

    private static FrameAPIBungee plugin;
    @Override
    public void onEnable() {
        plugin = this;
        try {
            if (!this.getPlugin().getDataFolder().exists())
                this.getPlugin().getDataFolder().mkdir();

            File file = new File(this.getPlugin().getDataFolder(), "config.yml");

            if (!file.exists()) {
                file.createNewFile();
            }
            Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.getPlugin().getDataFolder(), "config.yml"));
            if(configuration.get("MySQL.Host") == null) {
                configuration.set("MySQL.Host", " ");
                configuration.set("MySQL.User", " ");
                configuration.set("MySQL.Password", " ");
                configuration.set("MySQL.Database", " ");
            }
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration,file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        MYSQL.connect();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    private static boolean registered;
    /**
     * Get the Money from the Config
     * @param player Player
     * @return Integer Money in Config
     */
    public Double getMoney(ProxiedPlayer player) {
                    double money = getMoneyMySql(player);
                    return money;


    }
    public static FrameAPIBungee getPlugin() {
        return plugin;
    }
    /**
     * Add Money to Player and in Config
     * @param player Player
     * @param amount the amount from money
     */
    public void addMoney(ProxiedPlayer player, double amount) {
                if(IsTableExist.isExist("money")) { //Look if the table is exists
                    double money = getMoneyMySql(player);
                    money = money + amount;
                    saveMoneyInSQL(player, money);
                } else {
                    SQL.createTable("money","PlayerName TEXT(64),balance_money");
                    SQL.InsertData("PlayerName,balance_money","'" + player.getName() + "','" + 0 + "'","money");
        }
    }
    /**
     * Remove Money
     * @param player Player
     * @param amount the amount from money
     */
    public void removeMoney(ProxiedPlayer player,double amount) {
                double money = getMoneyMySql(player);

                money = money - amount;
                saveMoneyInSQL(player, money);
    }
    /**
     *Set amount for the Money
     * @param offline Player
     * @param amount the amount from money
     */
    public void setMoney(ProxiedPlayer offline, double amount) {
            saveMoneyInSQL(offline, amount);
    }

    /**
     * @param player Player
     * @param amount Amount of the Money
     */
    private void saveMoneyInSQL(ProxiedPlayer player, double amount) {
        if (IsTableExist.isExist("money")) {
            if (SQL.exists("PlayerName", player.getName(), "money")) {
                SQL.UpdateData("balance_money", "'" + amount + "'", "money", "PlayerName ='" + player.getName() + "'");
            } else {
                SQL.InsertData("PlayerName,balance_money", "'" + player.getName() + "','" + amount + "'", "money");
            }
        } else {
            SQL.createTable("money","PlayerName TEXT(64),balance_money DOUBLE,bankmoney DOUBLE");
            SQL.InsertData("PlayerName,balance_money", "'" + player.getName() + "','" + amount + "'", "money");
        }
    }
    /**
     * @param player the Players how  is in MYSQL
     * @return Money of MYSQL
     */
    private Double getMoneyMySql(ProxiedPlayer player) {
        if(IsTableExist.isExist("money")) {
            if(SQL.exists("PlayerName",player.getName(),"money")) {
                Object x = SQL.get("balance_money","PlayerName",player.getName(),"money");
                return Double.valueOf(x.toString());
            } else {
                return 0.0;
            }
        } else {
            SQL.createTable("money","PlayerName TEXT(64),balance_money DOUBLE,bankmoney DOUBLE");
            return 0.0;
        }

    }
    /**
     * @param player Player
     * @param amount Amount of the Money how will save in MYSQL Table BankMoney
     */
    public void SaveMoneyInBank(ProxiedPlayer player, double amount) {
        if (IsTableExist.isExist("money")) {
            if (SQL.exists("PlayerName", player.getName(), "money")) {
                SQL.UpdateData("bankmoney", "'" + amount + "'", "money", "PlayerName = '" + player.getName() + "'");
            } else {
                SQL.InsertData("PlayerName,bankmoney", "'" + player.getName() + "','" + 0.0 + "'", "money");
            }
        } else {
            SQL.createTable("money","PlayerName TEXT(64),balance_money DOUBLE,bankmoney DOUBLE");
            SQL.InsertData("PlayerName,bankmoney", "'" + player.getName() + "','" + 0.0 + "'", "money");
        }
    }
    /**
     * @param player Player
     * @return Money of MYSQL from Player
     */
    public Double getMoneyFromBankMySQL(ProxiedPlayer player) {
        double x = 0.0;
        if (IsTableExist.isExist("money")) {
            if (SQL.exists("PlayerName", player.getName(), "money")) {
                if(SQL.get("bankmoney","PlayerName",player.getName(),"money") != null) {
                    x = (double) SQL.get("bankmoney","PlayerName",player.getName(),"money");
                    return x;
                } else {
                    return 0.0;
                }
            } else {
                return 0.0;
            }
        } else {
            SQL.createTable("money","PlayerName TEXT(64),balance_money DOUBLE,bankmoney DOUBLE");
            return 0.0;
        }

    }
    public void RemoveMoneyFromBank(ProxiedPlayer player, double amount) {
        double money = getMoneyFromBankMySQL(player);
        money = money - amount;
        SaveMoneyInBank(player, money);
    }
    public void AddMoneyFromBank(ProxiedPlayer player, double amount) {
        double money = getMoneyFromBankMySQL(player);
        money = money + amount;
        SaveMoneyInBank(player, money);
    }
    public boolean b1 = false;
    public boolean hasPlayerAmount(ProxiedPlayer player ,double amount) {
        double money = getMoney(player);
        if(money < amount) {
            return false;
        } else {
            return true;
        }
    }
    public boolean b = false;
    public boolean hasPlayerMoneyBank(ProxiedPlayer player, double amount) {
        double money = getMoneyFromBankMySQL(player);
        if(money < amount) {
            return false;
        } else {
            return true;
        }
    }
}
