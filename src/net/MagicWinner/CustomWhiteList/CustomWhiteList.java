package net.MagicWinner.CustomWhiteList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomWhiteList extends JavaPlugin implements Listener {
    public CustomWhiteList() {
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getConfig().addDefault("message", "&cТут будет ваш текст");
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        System.out.println("[CWL] Статус плагина: запущен");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("cwl")) {
            if (!sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "У вас недостаточно прав для выполнения данной команды");
            } else if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Введи: /cwl change <Текст>");
            } else if (args[0].equalsIgnoreCase("change")) {
                StringBuilder sb = new StringBuilder();

                for (int i = 1; i < args.length; i++) {
                    sb.append(args[i]);
                    sb.append(" ");

                }
                String text = sb.toString();
                this.getConfig().set("message", text);
                this.saveConfig();
                sender.sendMessage(ChatColor.GREEN + "Сообщение было успешно изменено на " + ChatColor.translateAlternateColorCodes('&', text));
            }else{
                sender.sendMessage(ChatColor.RED + "Введи: /cwl change <Текст>");
            }
        }
        return true;
    }
    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent e) {
        if (e.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST) {
            String r = this.getConfig().getString("message");
            r = r.replaceAll("&", "§");
            e.setKickMessage(r);
        }
    }
    public void onDisable() {
        System.out.println("[CWL] Конфиг сохранен успешно. Выключаюсь");
    }
}
