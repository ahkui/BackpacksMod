package io.github.mpcs;

import io.github.mpcs.container.BackpackContainer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class BackpacksMod implements ModInitializer {
	public static final String modid = "mbackpacks";

	protected static final Identifier BACKPACK_CONTAINTER = new Identifier(BackpacksMod.modid, "backpack");

	protected static Item SMALL_BACKPACK;
	protected static Item MEDIUM_BACKPACK;
	protected static Item BIG_BACKPACK;

	@Override
	public void onInitialize() {
		Config config = new Config("MpcsBackpacks");
		if(!config.exists()) {
			config.load();
			config.setInt("small_backpack", 9);
			config.setInt("medium_backpack", 18);
			config.setInt("big_backpack", 27);
			config.save();
		}
		config.load();
		int smallSize = config.getInt("small_backpack");
		if(smallSize > 54)
			smallSize = 54;
		if(smallSize < 1)
			smallSize = 1;
		SMALL_BACKPACK = new BackpackItem(smallSize, new Item.Settings().itemGroup(ItemGroup.TOOLS).stackSize(1));

		int mediumSize = config.getInt("medium_backpack");
		if(mediumSize > 54)
			mediumSize = 54;
		if(mediumSize < 1)
			mediumSize = 1;
		MEDIUM_BACKPACK = new BackpackItem(mediumSize, new Item.Settings().itemGroup(ItemGroup.TOOLS).stackSize(1));
		
		int bigSize = config.getInt("big_backpack");
		if(bigSize > 54)
			bigSize = 54;
		if(bigSize < 1)
			bigSize = 1;
		BIG_BACKPACK = new BackpackItem(bigSize, new Item.Settings().itemGroup(ItemGroup.TOOLS).stackSize(1));

		Registry.register(Registry.ITEM, new Identifier(BackpacksMod.modid, "small_backpack"), SMALL_BACKPACK);
		Registry.register(Registry.ITEM, new Identifier(BackpacksMod.modid, "medium_backpack"), MEDIUM_BACKPACK);
		Registry.register(Registry.ITEM, new Identifier(BackpacksMod.modid, "big_backpack"), BIG_BACKPACK);
		ContainerProviderRegistry.INSTANCE.registerFactory(BackpacksMod.BACKPACK_CONTAINTER, (syncId, identifier, player, buf) -> new BackpackContainer(syncId, player.inventory, buf));
	}
}
