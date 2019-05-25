package io.github.mpcs;

import io.github.mpcs.container.BackpackContainer;
import io.github.mpcs.container.BackpackScreen;
import io.github.mpcs.init.Items;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;

public class ModularBackpacksModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenProviderRegistry.INSTANCE.registerFactory(Items.SMALL_BACKPACK_CONTAINTER,(syncId, identifier, player, buf) -> new BackpackScreen(syncId, player));

        ContainerProviderRegistry.INSTANCE.registerFactory(Items.SMALL_BACKPACK_CONTAINTER, (syncId, identifier, player, buf) -> new BackpackContainer(syncId, player.inventory));
    }
}
