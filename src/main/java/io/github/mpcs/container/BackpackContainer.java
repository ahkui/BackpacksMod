package io.github.mpcs.container;

import io.github.mpcs.BackpackItem;
import net.minecraft.container.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Hand;
import net.minecraft.util.PacketByteBuf;

public class BackpackContainer extends Container {
    private final BackpackInventory inv;
    private final BlockContext context;
    private final PlayerEntity player;
    private int slots;
    private Hand hand;

    public BackpackContainer(int syncId, PlayerInventory playerInv, PacketByteBuf buf) {
        this(syncId, playerInv, BlockContext.EMPTY, buf.readInt(), buf.readInt() == 1 ? Hand.MAIN_HAND : Hand.OFF_HAND);
    }

    public BackpackContainer(int syncId, PlayerInventory playerInv, BlockContext ctx, int slots, Hand hand) {
        super(null, syncId);
        this.inv = new BackpackInventory(slots, this, playerInv.player);
        this.context = ctx;
        this.player = playerInv.player;
        this.slots = slots;
        this.hand = hand;

        int spacing;
        if(slots % 9 == 0)
            spacing = 30 + (slots/9) * 18 + ((slots/9) < 5 ? 0 : 2);
        else
            spacing = 30 + (slots/9 + 1) * 18 + ((slots/9) < 5 ? 0 : 2);

        for(int y = 0; y < (slots/9); y++) {
            for(int x = 0; x < 9; ++x) {
                this.addSlot(new BackpackSlot(inv, x + y * 9, 8 + x * 18, 18 + y * 18));
            }
        }
        if((slots % 9) != 0)
            for(int x = 0; x < (slots % 9); x++) {
                this.addSlot(new BackpackSlot(inv, x + (slots/9) * 9, 8 + x * 18, 18 + (slots/9) * 18));
            }


        for(int y = 0; y < 3; ++y) {
            for(int x = 0; x < 9; ++x) {
                this.addSlot(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, spacing + y * 18));
            }
        }

        for(int x = 0; x < 9; ++x) {
            this.addSlot(new Slot(playerInv, x, 8 + x * 18, 58 + spacing));
        }

        DefaultedList<ItemStack> ad = DefaultedList.create(slots, ItemStack.EMPTY);
        BackpackItem.getInventory(player.getStackInHand(this.hand), ad);
        if(ad.size() == 0)
            return;

        for(int x = 0; x < 9; x++)
            for(int y = 0; y < slots/9; y++) {
                this.getSlot(x + y * 9).setStack(ad.get(x+y*9));
            }

        for(int x = 0; x < (slots % 9); x++) {
            this.getSlot(x + (slots/9)*9).setStack(ad.get(x+(slots/9)*9));
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
        DefaultedList<ItemStack> items = DefaultedList.create(slots *9, ItemStack.EMPTY);
        items = inv.getList(items);
        BackpackItem.setInventory(player.getStackInHand(this.hand), items);
        this.context.run((world, pos) -> {
            this.dropInventory(player, world, this.inv);
        });
    }

    public void updateInv() {
        this.sendContentUpdates();
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int slotNum) {
        ItemStack copy = ItemStack.EMPTY;
        Slot clickedSlot = this.slotList.get(slotNum);
        if (clickedSlot != null && clickedSlot.hasStack()) {
            ItemStack clickedStack = clickedSlot.getStack();
            copy = clickedStack.copy();
            if (slotNum < slots) {
                if (!this.insertItem(clickedStack, slots, this.slotList.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(clickedStack, 0, slots, false)) {
                return ItemStack.EMPTY;
            }

            if (clickedStack.isEmpty()) {
                clickedSlot.setStack(ItemStack.EMPTY);
            } else {
                clickedSlot.markDirty();
            }
        }

        return copy;
    }

    @Override
    public ItemStack onSlotClick(int int_1, int int_2, SlotActionType slotActionType_1, PlayerEntity playerEntity_1) {
        if(int_1 > 0) {
            if (this.getSlot(int_1).getStack().equals(playerEntity_1.getStackInHand(hand)))
                return ItemStack.EMPTY;
        }
        return super.onSlotClick(int_1, int_2, slotActionType_1, playerEntity_1);
    }
}
