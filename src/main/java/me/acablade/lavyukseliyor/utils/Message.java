package me.acablade.lavyukseliyor.utils;

import me.acablade.lavyukseliyor.LavYukseliyorPlugin;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Acablade/oz
 */
public enum Message {
    PREFIX("prefix"),
    LAVA_STARTED_RISING("lava-started-rising");

    private static final LavYukseliyorPlugin m = LavYukseliyorPlugin.getPlugin(LavYukseliyorPlugin.class);

    private static final Map<String, String> messages = new HashMap<>();

    public static void reload(){

        for (String key :m.getConfig().getConfigurationSection("messages").getKeys(true)){
            messages.put(key, m.getConfig().getString("messages." + key));
        }

    }

    static {
        reload();
    }



    private final String text;


    Message(final String text) {
        this.text = text;
    }

    public void send(CommandSender sender, Function<String, String> placeholder) {
        String msg = messages.get(text);
        if(placeholder!=null) msg = placeholder.apply(msg);

        sender.sendMessage(Colorize.format(messages.get(Message.PREFIX.text) + " " +  msg));
    }

    public String getText(Function<String, String> placeholder){
        String msg = messages.get(text);
        if(placeholder!=null) msg = placeholder.apply(msg);

        return msg;
    }


}
