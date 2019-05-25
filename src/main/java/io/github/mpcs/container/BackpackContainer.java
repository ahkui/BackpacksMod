package io.github.mpcs.container;

import io.github.mpcs.BackpackItem;
import net.minecraft.container.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Hand;

public class BackpackContainer extends Container {
    private final BackpackInventory inv;
    private final BlockContext context;
    private final PlayerEntity player;

    public BackpackContainer(int syncId, PlayerInventory playerInv) {
        this(syncId, playerInv, BlockContext.EMPTY);
    }

    public BackpackContainer(int syncId, PlayerInventory playerInv, BlockContext ctx) {
        super(null, syncId);
        this.inv = new BackpackInventory(this, playerInv.player);
        this.context = ctx;
        this.player = playerInv.player;

        //backpack slots
        for(int int_8 = 0; int_8 < 3; ++int_8) {
            for(int int_7 = 0; int_7 < 9; ++int_7) {
                this.addSlot(new Slot(inv, int_7 + int_8 * 9, 8 + int_7 * 18, 18 + int_8 * 18));
            }
        }
        //player inventory
        for(int int_6 = 0; int_6 < 3; ++int_6) {
            for(int int_5 = 0; int_5 < 9; ++int_5) {
                this.addSlot(new Slot(playerInv, int_5 + int_6 * 9 + 9, 8 + int_5 * 18, 84 + int_6 * 18));
            }
        }
        //player hotbar
        for(int int_6 = 0; int_6 < 9; ++int_6) {
            this.addSlot(new Slot(playerInv, int_6, 8 + int_6 * 18, 142));
        }
        DefaultedList<ItemStack> ad = DefaultedList.create(3*9, ItemStack.EMPTY);
        BackpackItem.getInventory(player.getStackInHand(Hand.MAIN_HAND), ad);
        if(ad.size() == 0)
            return;

        for(int x = 0; x < 9; x++)
            for(int y = 0; y < 3; y++) {
                this.getSlot(x + y * 9).setStack(ad.get(x+y*9));
            }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public void onContentChanged(Inventory inv) {
        super.onContentChanged(inv);
        if (inv == this.inv) {
            this.updateInv();
        }
    }

    public void close(PlayerEntity player) {
        super.close(player);
        DefaultedList<ItemStack> items = DefaultedList.create(3*9, ItemStack.EMPTY);
        items = inv.getList(items);
        BackpackItem.setInventory(player.getStackInHand(Hand.MAIN_HAND), items);
        this.context.run((world, pos) -> {
            this.dropInventory(player, world, this.inv);
        });
    }

    public void updateInv() {
        this.sendContentUpdates();
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int slotNum) {
        ItemStack itemStack_1 = ItemStack.EMPTY;
        Slot slot_1 = this.slotList.get(slotNum);
        if (slot_1 != null && slot_1.hasStack()) {
            ItemStack itemStack_2 = slot_1.getStack();
            itemStack_1 = itemStack_2.copy();
            if (slotNum < 3 * 9) {
                if (!this.insertItem(itemStack_2, 3 * 9, this.slotList.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(itemStack_2, 0, 3 * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack_2.isEmpty()) {
                slot_1.setStack(ItemStack.EMPTY);
            } else {
                slot_1.markDirty();
            }
        }

        return itemStack_1;
    }

    @Override
    public ItemStack onSlotClick(int int_1, int int_2, SlotActionType slotActionType_1, PlayerEntity playerEntity_1) {
        if(slotActionType_1 == SlotActionType.PICKUP && int_1 > 0) {
            if (this.getSlot(int_1).getStack().getItem() instanceof BackpackItem)
                return ItemStack.EMPTY;
        }
        return super.onSlotClick(int_1, int_2, slotActionType_1, playerEntity_1);
    }
}
