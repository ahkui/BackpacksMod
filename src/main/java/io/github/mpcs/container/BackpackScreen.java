package io.github.mpcs.container;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.gui.ContainerScreen;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public class BackpackScreen extends ContainerScreen<BackpackContainer> {
    private Identifier TEXTURE;
    private int size;
    public BackpackScreen(int syncId, PlayerEntity player, PacketByteBuf buf) {
        super(new BackpackContainer(syncId, player.inventory, buf), player.inventory, new TranslatableComponent("container.mpcsmod.resistortable"));
        size = buf.readInt();
        TEXTURE = new Identifier("mbackpacks", "textures/gui/backpack" + size + ".png");
        if(size == 4)
            this.containerHeight = 184;
        else if(size == 6) {
            this.containerHeight = 222;
        }

    }

    @Override
    protected void init() {
        super.init();
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
        this.font.draw("Backpack", 8.0F, 6.0F, 4210752);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        this.drawBackground(partialTicks, mouseX, mouseY);
        super.render(mouseX, mouseY, partialTicks);
        this.drawMouseoverTooltip(mouseX, mouseY);
    }
}
