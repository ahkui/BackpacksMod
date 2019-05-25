package io.github.mpcs;

import io.github.mpcs.init.Items;
import net.fabricmc.api.ModInitializer;

public class ModularBackpacksMod implements ModInitializer {
	public static final String modid = "mbackpacks";
	@Override
	public void onInitialize() {
		Items.init();
	}
}
