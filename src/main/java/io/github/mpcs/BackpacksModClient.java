package io.github.mpcs;

import io.github.mpcs.container.BackpackContainer;
import io.github.mpcs.container.BackpackScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.item.DyeableItem;

public class BackpacksModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((itemStack, i) -> i > 0 ? -1 : ((DyeableItem)itemStack.getItem()).getColor(itemStack), BackpacksMod.SMALL_BACKPACK, BackpacksMod.MEDIUM_BACKPACK, BackpacksMod.BIG_BACKPACK);
        ScreenProviderRegistry.INSTANCE.registerFactory(BackpacksMod.BACKPACK_CONTAINTER,(syncId, identifier, player, buf) -> new BackpackScreen(syncId, player, buf));
        ContainerProviderRegistry.INSTANCE.registerFactory(BackpacksMod.BACKPACK_CONTAINTER, (syncId, identifier, player, buf) -> new BackpackContainer(syncId, player.inventory, buf));
    }
}
