package tp.org.tp_50_50;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;


public final class Tp_50_50 extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        getCommand("tp5050").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("tp5050")){
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (args.length == 3) {
                    double x = Double.parseDouble(args[0]);
                    double y = Double.parseDouble(args[1]);
                    double z = Double.parseDouble(args[2]);
                    double chance = Math.random();
                    if (chance < 0.5) {
                        Location destination = new Location(player.getWorld(), x, y, z);
                        player.teleport(destination);
                        player.sendMessage("Teleporte realizado com sucesso!");
                    } else {
                        World world = player.getWorld();
                        double randomX = Math.random() * 2000 - 1000;
                        double randomZ = Math.random() * 2000 - 1000;
                        double randomY = world.getHighestBlockYAt((int) randomX, (int) randomZ) + 1;

                        Location randomLocation = new Location(world, randomX, randomY, randomZ);
                        player.teleport(randomLocation);
                        player.sendMessage("O teleporte falhou!");
                    }
                } else {
                    player.sendMessage("Formato inválido. Use: /tp5050 <x> <y> <z>");
                }
            } else {
                sender.sendMessage("Este comando só pode ser executado por jogadores.");
            }
            return true;
        }
        return false;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
