package Listeners;

import Main.ConsumableCooldownsPlugin;
import Utility.ConsumableItemIds;
import Utility.ConsumableItemType;
import org.dreambot.api.script.listener.ItemContainerListener;
import org.dreambot.api.wrappers.items.Item;

import java.util.Map;

public class InventoryChangeListener implements ItemContainerListener {

    private final Map<ConsumableItemType, Integer> itemCooldowns;
    private final ConsumableCooldownsPlugin plugin;

    public InventoryChangeListener(Map<ConsumableItemType, Integer> itemCooldowns, ConsumableCooldownsPlugin plugin) {
        this.itemCooldowns = itemCooldowns;
        this.plugin = plugin;
    }

    @Override
    public void onInventoryItemAdded(Item item) {
        handleItemChange(item);
    }

    @Override
    public void onInventoryItemChanged(Item incoming, Item existing) {
        handleItemChange(existing);
    }

    @Override
    public void onInventoryItemRemoved(Item item) {
        handleItemChange(item);
    }

    @Override
    public void onInventoryItemSwapped(Item incoming, Item outgoing) {
        handleItemChange(outgoing);
    }

    private void handleItemChange(Item item) {
        if (item == null) return;

        ConsumableItemType itemType = getConsumableItemType(item.getID());

        if (itemType != null) {
            if (plugin.isEating() && isFoodType(itemType)) {
                applyCooldowns(itemType);
                plugin.setEating(false);
            } else if (plugin.isDrinking() && isDrinkType(itemType)) {
                applyCooldowns(itemType);
                plugin.setDrinking(false);
            }
        }
    }

    private boolean isFoodType(ConsumableItemType itemType) {
        return itemType == ConsumableItemType.FOOD ||
                itemType == ConsumableItemType.CAKE ||
                itemType == ConsumableItemType.F2P_FIRST_SLICE ||
                itemType == ConsumableItemType.F2P_SECOND_SLICE ||
                itemType == ConsumableItemType.P2P_PIE ||
                itemType == ConsumableItemType.COOKED_CRAB_MEAT ||
                itemType == ConsumableItemType.COMBO_FOOD;
    }

    private boolean isDrinkType(ConsumableItemType itemType) {
        return itemType == ConsumableItemType.DRINK;
    }

    private void applyCooldowns(ConsumableItemType itemType) {
        switch (itemType) {
            case FOOD:
            case CAKE:
            case F2P_FIRST_SLICE:
            case F2P_SECOND_SLICE:
            case P2P_PIE:
            case COOKED_CRAB_MEAT:
                setFoodCooldowns();
                break;
            case DRINK:
                setDrinkCooldowns();
                break;
            case COMBO_FOOD:
                setComboFoodCooldowns();
                break;
            default:
                break;
        }
    }

    private void setFoodCooldowns() {
        setCooldown(ConsumableItemType.FOOD, 3);
        setCooldown(ConsumableItemType.CAKE, 3);
        setCooldown(ConsumableItemType.F2P_FIRST_SLICE, 3);
        setCooldown(ConsumableItemType.F2P_SECOND_SLICE, 3);
        setCooldown(ConsumableItemType.P2P_PIE, 3);
        setCooldown(ConsumableItemType.COOKED_CRAB_MEAT, 3);
    }

    private void setDrinkCooldowns() {
        setCooldown(ConsumableItemType.DRINK, 3);
        setFoodCooldowns();
    }
    private void setComboFoodCooldowns() {
        setCooldown(ConsumableItemType.COMBO_FOOD, 3);
        setCooldown(ConsumableItemType.DRINK, 3);
        setFoodCooldowns();
    }
    private void setCooldown(ConsumableItemType itemType, int ticks) {
        itemCooldowns.put(itemType, ticks);
    }

    private ConsumableItemType getConsumableItemType(int itemId) {
        if (ConsumableItemIds.FOOD_ITEM_IDS.contains(itemId)) {
            return ConsumableItemType.FOOD;
        } else if (ConsumableItemIds.DRINK_ITEM_IDS.contains(itemId)) {
            return ConsumableItemType.DRINK;
        } else if (ConsumableItemIds.CAKE_ITEM_IDS.contains(itemId)) {
            return ConsumableItemType.CAKE;
        } else if (ConsumableItemIds.F2P_FIRST_SLICE_ITEM_IDS.contains(itemId)) {
            return ConsumableItemType.F2P_FIRST_SLICE;
        } else if (ConsumableItemIds.F2P_SECOND_SLICE_ITEM_IDS.contains(itemId)) {
            return ConsumableItemType.F2P_SECOND_SLICE;
        } else if (ConsumableItemIds.P2P_PIE_ITEM_IDS.contains(itemId)) {
            return ConsumableItemType.P2P_PIE;
        } else if (ConsumableItemIds.COOKED_CRAB_MEAT_ITEM_IDS.contains(itemId)) {
            return ConsumableItemType.COOKED_CRAB_MEAT;
        }else if (ConsumableItemIds.COMBO_FOOD_ITEM_IDS.contains(itemId)) {
            return ConsumableItemType.COMBO_FOOD;
        }
        return null;
    }

}
