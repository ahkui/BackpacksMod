package io.github.mpcs.init;

import io.github.mpcs.BackpackItem;
import io.github.mpcs.ModularBackpacksMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Items {
    public static final Identifier SMALL_BACKPACK_CONTAINTER = new Identifier(ModularBackpacksMod.modid, "small_backpack");

    public static Item BACKPACK = new BackpackItem(new Item.Settings().itemGroup(ItemGroup.TOOLS).stackSize(1));

    public static void init() {
        Registry.register(Registry.ITEM, new Identifier(ModularBackpacksMod.modid, "backpack"), BACKPACK);
    }
}