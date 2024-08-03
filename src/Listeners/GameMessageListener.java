package Listeners;

import Main.ConsumableCooldownsPlugin;
import org.dreambot.api.script.listener.ChatListener;
import org.dreambot.api.wrappers.widgets.message.Message;
import java.util.regex.Pattern;

public class GameMessageListener implements ChatListener {

    private static final Pattern EAT_PATTERN = Pattern.compile("eat", Pattern.CASE_INSENSITIVE);
    private static final Pattern DRINK_PATTERN = Pattern.compile("drink", Pattern.CASE_INSENSITIVE);
    private final ConsumableCooldownsPlugin plugin;

    public GameMessageListener(ConsumableCooldownsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onMessage(Message message) {
        String msg = message.getMessage();
        if (EAT_PATTERN.matcher(msg).find()) {
            plugin.setEating(true);
        } else if (DRINK_PATTERN.matcher(msg).find()) {
            plugin.setDrinking(true);

        }
    }
}
