package io.github.mpcs.container;

import io.github.mpcs.BackpackItem;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.container.Slot;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class BackpackSlot extends Slot {
    public BackpackSlot(Inventory inventory_1, int int_1, int int_2, int int_3) {
        super(inventory_1, int_1, int_2, int_3);
    }

    @Override
    public boolean canInsert(ItemStack itemStack_1) {
        if(itemStack_1.getItem() instanceof BackpackItem || itemStack_1.getItem().equals(Items.SHULKER_BOX))
            return false;
        return true;
    }
}
