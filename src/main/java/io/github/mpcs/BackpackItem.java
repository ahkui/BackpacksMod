package io.github.mpcs;

import com.sun.istack.internal.Nullable;
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
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Iterator;
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


    @Environment(EnvType.CLIENT)
    public void buildTooltip(ItemStack itemStack_1, @Nullable BlockView blockView_1, List<Text> list_1, TooltipContext tooltipContext_1) {
        this.buildTooltip(itemStack_1, blockView_1, list_1, tooltipContext_1);
        CompoundTag compoundTag_1 = itemStack_1.getSubTag("BlockEntityTag");
        if (compoundTag_1 != null) {
            if (compoundTag_1.containsKey("LootTable", 8)) {
                list_1.add(new LiteralText("???????"));
            }

            if (compoundTag_1.containsKey("Items", 9)) {
                DefaultedList<ItemStack> defaultedList_1 = DefaultedList.ofSize(27, ItemStack.EMPTY);
                Inventories.fromTag(compoundTag_1, defaultedList_1);
                int int_1 = 0;
                int int_2 = 0;
                Iterator var9 = defaultedList_1.iterator();

                while(var9.hasNext()) {
                    ItemStack itemStack_2 = (ItemStack)var9.next();
                    if (!itemStack_2.isEmpty()) {
                        ++int_2;
                        if (int_1 <= 4) {
                            ++int_1;
                            Text text_1 = itemStack_2.getName().deepCopy();
                            text_1.append(" x").append(String.valueOf(itemStack_2.getCount()));
                            list_1.add(text_1);
                        }
                    }
                }

                if (int_2 - int_1 > 0) {
                    list_1.add((new TranslatableText("container.shulkerBox.more", new Object[]{int_2 - int_1})).formatted(Formatting.ITALIC));
                }
            }
        }

    }
}
