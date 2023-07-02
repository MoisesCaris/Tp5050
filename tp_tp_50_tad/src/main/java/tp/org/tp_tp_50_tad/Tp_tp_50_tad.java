package tp.org.tp_tp_50_tad;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.PriorityQueue;
import org.bukkit.World;
import org.bukkit.Bukkit;
public final class Tp_tp_50_tad extends JavaPlugin {
    private PriorityQueue<TeleportRequest> teleportQueue;

    @Override
    public void onEnable() {
        teleportQueue = new PriorityQueue<>();
        getCommand("tp5050").setExecutor(new Tp5050Executor());
        Bukkit.getScheduler().runTaskTimer(this, this::processTeleportQueue, 0, 100);
    }

    @Override
    public void onDisable() {
        processTeleportQueue();
    }

    private static class TeleportRequest implements Comparable<TeleportRequest> {
        private Player player;
        private Location destination;
        private int priority;

        public TeleportRequest(Player player, Location destination, int priority) {
            this.player = player;
            this.destination = destination;
            this.priority = priority;
        }

        @Override
        public int compareTo(TeleportRequest other) {
            return Integer.compare(other.priority, this.priority);
        }
    }

    private class Tp5050Executor implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (command.getName().equalsIgnoreCase("tp5050")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;

                    if (args.length == 3) {
                        double x = Double.parseDouble(args[0]);
                        double y = Double.parseDouble(args[1]);
                        double z = Double.parseDouble(args[2]);

                        Location destination = new Location(player.getWorld(), x, y, z);
                        int priority = 1; // Defina a prioridade desejada para o teleporte

                        teleportQueue.add(new TeleportRequest(player, destination, priority));

                        player.sendMessage("Pedido de teleporte adicionado à fila prioritária.");
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
    }

    private void processTeleportQueue() {
        while (!teleportQueue.isEmpty()) {
            TeleportRequest teleportRequest = teleportQueue.poll();
            Player player = teleportRequest.player;
            Location destination = teleportRequest.destination;

            // 50% de chance de teleporte bem-sucedido
            if (Math.random() < 0.5) {
                player.teleport(destination);
                player.sendMessage("Teleporte realizado com sucesso!");
            } else {
                // Teleporte falhou, teleportar para uma localização aleatória
                Location randomLocation = getRandomLocation(player.getWorld());
                player.teleport(randomLocation);
                player.sendMessage("O teleporte falhou! Você foi transportado para uma localização aleatória.");
            }
        }
    }

    private Location getRandomLocation(World world) {
        double randomX = Math.random() * 2000 - 1000;
        double randomZ = Math.random() * 2000 - 1000;
        double randomY = world.getHighestBlockYAt((int) randomX, (int) randomZ) + 1;

        return new Location(world, randomX, randomY, randomZ);
    }
}