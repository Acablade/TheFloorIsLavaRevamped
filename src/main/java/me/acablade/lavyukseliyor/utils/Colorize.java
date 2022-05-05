package me.acablade.lavyukseliyor.utils;

import org.bukkit.ChatColor;

/**
 * @author Acablade/oz
 */
public class Colorize {

    public static String format(String message){
        return ChatColor.translateAlternateColorCodes('&',message);
    }

}
