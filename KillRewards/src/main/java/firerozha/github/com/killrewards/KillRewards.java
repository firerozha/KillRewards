package firerozha.github.com.killrewards;

import firerozha.github.com.killrewards.Listeners.DeathReward;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public final class KillRewards extends JavaPlugin {

    PluginManager pm = Bukkit.getPluginManager();
    public static Economy econ = null;

    @Override
    public void onEnable() {
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }






        //commands


        //events
        pm.registerEvents(new DeathReward(this), this);


        //config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // other
        getLogger().info("The plugin DeathControl has launched!");


    }

    public boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}



