package firerozha.github.com.killrewards.Listeners;

import firerozha.github.com.killrewards.KillRewards;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Random;

import static firerozha.github.com.killrewards.KillRewards.econ;


public class DeathReward implements Listener {

    private final KillRewards plugin;

    public DeathReward(KillRewards plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void OnDeath(PlayerDeathEvent e) {
        Boolean randomreward = plugin.getConfig().getBoolean("randomreward");
        int between1 = plugin.getConfig().getInt("between1");
        int between2 = plugin.getConfig().getInt("between2");
        int specifiedkillreward = plugin.getConfig().getInt("specifiedkillreward");
        Boolean killreward = plugin.getConfig().getBoolean("killreward");
        if (killreward) {
            Player p = e.getEntity();
            if (p.isDead()) {
                Player killer = p.getKiller();
                if (randomreward) {
                    Random rand = new Random();
                    int reward = rand.nextInt(between2) + between1;
                    EconomyResponse r = econ.depositPlayer(killer, reward);
                    if (r.transactionSuccess()) {
                        String rewardmessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("rewardmessage"));
                        rewardmessage = rewardmessage.replaceAll("\\{money}", String.valueOf(reward));
                        rewardmessage = rewardmessage.replaceAll("\\{player}", p.getName());
                        killer.sendMessage(rewardmessage);


                    }
                } else {
                    EconomyResponse r = econ.depositPlayer(killer, specifiedkillreward);
                    if(r.transactionSuccess()) {
                        String rewardmessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("rewardmessage"));
                        rewardmessage = rewardmessage.replaceAll("\\{money}", String.valueOf(specifiedkillreward));
                        rewardmessage = rewardmessage.replaceAll("\\{player}", p.getName());
                        killer.sendMessage(rewardmessage);
                    }

                }
            }
        }
    }
}
