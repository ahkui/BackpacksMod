package io.github.mpcs;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class BackpackItem extends Item implements DyeableItem {
    private int slots;
    public BackpackItem(int slots, Settings item$Settings_1) {
        super(item$Settings_1);
        this.slots = slots;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world_1, PlayerEntity playerEntity_1, Hand hand_1) {
        if (!world_1.isClient)
            ContainerProviderRegistry.INSTANCE.openContainer(BackpacksMod.BACKPACK_CONTAINTER, playerEntity_1, buf -> {
                buf.writeInt(slots);
                buf.writeInt(hand_1 == Hand.MAIN_HAND ? 1 : 0) ;
                buf.writeString(playerEntity_1.getStackInHand(hand_1).getName().getString());
                buf.writeInt(slots);
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

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        CompoundTag tag = stack.getTag();
        if (tag != null) {
            if (tag.contains("Items", 9)) {
                DefaultedList<ItemStack> stacks = DefaultedList.ofSize(27, ItemStack.EMPTY);
                Inventories.fromTag(tag, stacks);
                int listedStacks = 0;
                int totalStacks = 0;

                for (ItemStack heldStack : stacks) {
                    if (!heldStack.isEmpty()) {
                        ++totalStacks;
                        if (listedStacks <= 4) {
                            ++listedStacks;
                            Text stackName = heldStack.getName().deepCopy();
                            stackName.append(" x").append(String.valueOf(heldStack.getCount()));
                            tooltip.add(stackName);
                        }
                    }
                }

                if (totalStacks - listedStacks > 0) {
                    tooltip.add((new TranslatableText("container.shulkerBox.more", new Object[]{totalStacks - listedStacks})).formatted(Formatting.ITALIC));
                }
            }
        }
    }
}
