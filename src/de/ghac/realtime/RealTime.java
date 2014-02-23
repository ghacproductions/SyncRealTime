package de.ghac.realtime;

import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RealTime extends JavaPlugin{
    public boolean announce = false;
    public java.util.List<String> worlds;
    public String[] worldsarray;
    
    @Override   
    public void onDisable() {

        System.out.println("[" + this.getName()+"] Plugin deaktiviert!");

    }
    
    @Override   
    public void onEnable() {
        System.out.println("[" + this.getName()+"] Plugin by ghac!");
        System.out.println("[" + this.getName()+"] Plugin aktiviert!");
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                setTime();
            }}, 0, 600);
        
        getConfig().addDefault("main.announce", false);
        getConfig().addDefault("worlds.world", true);
        getConfig().options().copyDefaults(true);
        this.saveConfig();  
}
    
    
    @SuppressWarnings("deprecation")
    public void setTime() {
        
        Date d = new Date();
        int h = d.getHours();
        int m = d.getMinutes();
        m = (100/6) * m;
        long ticks = (1000 * h) + m + 18000;       
        for (String world : getConfig().getConfigurationSection("worlds").getKeys(false)) {
            if(getConfig().getBoolean("worlds." + world)){               
               getServer().getWorld(world).setTime(ticks);
            }
           }     
        if(getConfig().getBoolean("main.announce")){
            System.out.println("[" + this.getName()+"] " + "Time set to " + ticks + " ticks.");
        }     
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args){
        if(cmd.getName().equalsIgnoreCase("realtime")){
        if(!(sender instanceof Player)){
            if(args.length > 0){
               setTime(); 
               System.out.println("[" + this.getName()+"] " + "Set time.");
               return true;
            }
        System.out.println("[" + this.getName()+"] " + "Realtime Plugin by ghac. All rights reserved. Version 1.0");
        return true;
        }
        else{
        Player p = (Player)sender;
        
        if(args.length > 0 && p.hasPermission("realtime.force")){
            setTime(); 
            p.sendMessage(ChatColor.AQUA + "[" + this.getName()+"] " + ChatColor.DARK_GREEN + "Set time.");
            return true;
         }
      p.sendMessage(ChatColor.AQUA + "[" + this.getName()+"] " + ChatColor.DARK_GREEN + "Realtime Plugin by ghac. All rights reserved. Version 1.0");
                return true;
          
        }
        }
            return true;      
    }
}
