package io.github.mpcs.container.modular;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Identifier;

public class ModularBackpackScreen extends ContainerScreen<ModularBackpackContainer> {
    private static final Identifier TEXTURE = new Identifier("mbackpacks", "textures/gui/backpack.png");
    private boolean isNarrow;

    public ModularBackpackScreen(int syncId, PlayerEntity player) {
        super(new ModularBackpackContainer(syncId, player.inventory), player.inventory, new TranslatableComponent("container.mpcsmod.resistortable"));
    }

    @Override
    protected void init() {
        super.init();
        this.isNarrow = this.width < 379;
        this.left = (this.width - this.containerWidth) / 2;
    }

    @Override
    protected void drawBackground(float var1, int var2, int var3) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        int guiX = this.left;
        int guiY = (this.height - this.containerHeight) / 2;
        this.blit(guiX, guiY, 0, 0, this.containerWidth, this.containerHeight);
    }

    @Override
    protected void drawForeground(int int_1, int int_2) {
        this.font.draw("Backpack", 50.0F, 6.0F, 4210752);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        this.drawBackground(partialTicks, mouseX, mouseY);
        super.render(mouseX, mouseY, partialTicks);
        this.drawMouseoverTooltip(mouseX, mouseY);
    }
}
