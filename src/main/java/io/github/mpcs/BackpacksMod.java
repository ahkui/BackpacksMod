package io.github.mpcs;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BackpacksMod implements ModInitializer {
	public static final String modid = "mbackpacks";

	protected static final Identifier BACKPACK_CONTAINTER = new Identifier(BackpacksMod.modid, "backpack");

	protected static Item SMALL_BACKPACK = new BackpackItem(2, new Item.Settings().itemGroup(ItemGroup.TOOLS).stackSize(1));
	protected static Item MEDIUM_BACKPACK = new BackpackItem(4, new Item.Settings().itemGroup(ItemGroup.TOOLS).stackSize(1));
	protected static Item BIG_BACKPACK = new BackpackItem(6, new Item.Settings().itemGroup(ItemGroup.TOOLS).stackSize(1));


	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier(BackpacksMod.modid, "small_backpack"), SMALL_BACKPACK);
		Registry.register(Registry.ITEM, new Identifier(BackpacksMod.modid, "medium_backpack"), MEDIUM_BACKPACK);
		Registry.register(Registry.ITEM, new Identifier(BackpacksMod.modid, "big_backpack"), BIG_BACKPACK);
	}
}
