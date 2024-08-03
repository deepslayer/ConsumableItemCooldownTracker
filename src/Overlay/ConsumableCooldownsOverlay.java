package Overlay;

import Utility.ConsumableItemIds;
import Utility.ConsumableItemType;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;
import java.util.Map;

import static org.dreambot.api.utilities.Logger.log;

public class ConsumableCooldownsOverlay implements PaintListener {
    private final Map<ConsumableItemType, Integer> itemCooldowns;

    public ConsumableCooldownsOverlay(Map<ConsumableItemType, Integer> itemCooldowns) {
        this.itemCooldowns = itemCooldowns;
    }

    @Override
    public void onPaint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Composite originalComposite = g2d.getComposite();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Map.Entry<ConsumableItemType, Integer> entry : itemCooldowns.entrySet()) {
            ConsumableItemType itemType = entry.getKey();
            int cooldown = entry.getValue();
            if (cooldown > 0) {
                for (Item item : Inventory.all()) {
                    if (item != null && getConsumableItemType(item.getID()) == itemType) {
                        drawCooldownOverlay(g2d, item, cooldown);
                    }
                }
            }
        }

        g2d.setComposite(originalComposite);
    }

    private void drawCooldownOverlay(Graphics2D g2d, Item item, int cooldown) {
        Rectangle boundingBox = item.getDestination().getBoundingBox();

        int size = Math.min(boundingBox.width, boundingBox.height) - 5;
        int x = boundingBox.x + (boundingBox.width - size) / 2;
        int y = boundingBox.y + (boundingBox.height - size) / 2;

        g2d.setColor(new Color(0, 0, 0, 128));
        g2d.fillRect(x, y, size, size);

        g2d.setColor(new Color(0, 0, 0, 255));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(x, y, size, size);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        String countdownText = String.valueOf(cooldown);
        FontMetrics fm = g2d.getFontMetrics();
        int textX = x + (size - fm.stringWidth(countdownText)) / 2;
        int textY = y + ((size - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString(countdownText, textX, textY);
    }

    private ConsumableItemType getConsumableItemType(int itemId) {
        if (ConsumableItemIds.FOOD_ITEM_IDS.contains(itemId)) {
            return ConsumableItemType.FOOD;
        } else if (ConsumableItemIds.DRINK_ITEM_IDS.contains(itemId)) {
            return ConsumableItemType.DRINK;
        } else if (ConsumableItemIds.COMBO_FOOD_ITEM_IDS.contains(itemId)) {
            return ConsumableItemType.COMBO_FOOD;
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
        }
        return null;
    }
}
