package io.github.mpcs;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class BackpackItem extends Item implements DyeableItem {
    private int size;
    public BackpackItem(int size, Settings item$Settings_1) {
        super(item$Settings_1);
        this.size = size;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world_1, PlayerEntity playerEntity_1, Hand hand_1) {
        if (!world_1.isClient)
            ContainerProviderRegistry.INSTANCE.openContainer(BackpacksMod.BACKPACK_CONTAINTER, playerEntity_1, buf -> {
                buf.writeInt(size);
                buf.writeInt(size);
        });
        return new TypedActionResult(ActionResult.PASS, playerEntity_1.getStackInHand(hand_1));
    }

    public static void setInventory(ItemStack backpack, DefaultedList<ItemStack> list) {
        CompoundTag nbt = backpack.getOrCreateTag();
        Inventories.toTag(nbt, list);
        backpack.setTag(nbt);
    }

    public static void getInventory(ItemStack backpack, DefaultedList<ItemStack> defaultedList) {
        CompoundTag nbt;
        if(backpack.hasTag()) {
            nbt = backpack.getTag();
        } else {
            nbt = new CompoundTag();
        }
        Inventories.fromTag(nbt, defaultedList);
        backpack.setTag(nbt);
    }
}
