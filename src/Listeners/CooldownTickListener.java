package Listeners;

import Utility.ConsumableItemType;
import org.dreambot.api.script.listener.GameTickListener;

import java.util.Map;


public class CooldownTickListener implements GameTickListener {

    private final Map<ConsumableItemType, Integer> itemCooldowns;

    public CooldownTickListener(Map<ConsumableItemType, Integer> itemCooldowns) {
        this.itemCooldowns = itemCooldowns;
    }

    @Override
    public void onGameTick() {
        updateCooldowns();
    }

    private void updateCooldowns() {
        for (Map.Entry<ConsumableItemType, Integer> entry : itemCooldowns.entrySet()) {
            ConsumableItemType itemType = entry.getKey();
            int cooldown = entry.getValue();
            if (cooldown > 0) {
                itemCooldowns.put(itemType, cooldown - 1);
            }
        }
    }
}
