package Main;

import Listeners.CooldownTickListener;
import Listeners.GameMessageListener;
import Listeners.InventoryChangeListener;
import Overlay.ConsumableCooldownsOverlay;
import Utility.ConsumableItemType;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManager;
import org.dreambot.api.script.ScriptManifest;

import java.util.HashMap;
import java.util.Map;

@ScriptManifest(name = "Consumable Cooldowns", description = "Displays cooldowns on food & drink items in your inventory, Inspired by the Copy Pasta's Runelite plugin", author = "Deep Slayer", version = 1.0, category = Category.UTILITY)
public class ConsumableCooldownsPlugin extends AbstractScript {

    private final Map<ConsumableItemType, Integer> itemCooldowns = new HashMap<>();
    private boolean isEating = false;
    private boolean isDrinking = false;

    @Override
    public void onStart() {
        ScriptManager scriptManager = getScriptManager();
        scriptManager.addListener(new CooldownTickListener(itemCooldowns));
        scriptManager.addListener(new GameMessageListener(this));
        scriptManager.addListener(new InventoryChangeListener(itemCooldowns, this));
        scriptManager.addListener(new ConsumableCooldownsOverlay(itemCooldowns));
    }

    public void setEating(boolean isEating) {
        this.isEating = isEating;
    }

    public void setDrinking(boolean isDrinking) {
        this.isDrinking = isDrinking;
    }

    public boolean isEating() {
        return isEating;
    }

    public boolean isDrinking() {
        return isDrinking;
    }

    @Override
    public int onLoop() {
        return 100;
    }
}
