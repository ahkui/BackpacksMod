package io.github.mpcs.container;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public class BackpackScreen extends AbstractContainerScreen<BackpackContainer> {
    private Identifier TEXTURE;
    private Identifier SLOT_TEXTURE = new Identifier("mbackpacks", "textures/gui/slot.png");
    private int slots;
    private String name;
    public BackpackScreen(int syncId, PlayerEntity player, PacketByteBuf buf) {
        super(new BackpackContainer(syncId, player.inventory, buf), player.inventory, new TranslatableText("container.mbackpacks.backpack"));
        name = buf.readString();
        slots = buf.readInt();
        TEXTURE = new Identifier("mbackpacks", "textures/gui/backpack" + ((slots/9) + (slots % 9 == 0 ? 0 : 1)) + ".png");
        if((slots/9) == 4)
            this.containerHeight = 184;
        else if((slots/9) > 4) {
            this.containerHeight = 222;
        }
    }

    @Override
    protected void init() {
        super.init();
        this.x = (this.width - this.containerWidth) / 2;
    }

    @Override
    protected void drawBackground(float var1, int var2, int var3) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        int guiX = this.x;
        int guiY = (this.height - this.containerHeight) / 2;
        this.blit(guiX, guiY, 0, 0, this.containerWidth, this.containerHeight);

        drawSlots(guiX, guiY);
    }

    private void drawSlots(int guiX, int guiY) {
        this.minecraft.getTextureManager().bindTexture(SLOT_TEXTURE);
        for(int y = 0; y < (slots/9); y++)
            for(int x = 0; x < 9; x++) {
                this.blit(guiX + 7 + (x * 18), guiY + 17 + (y * 18), 0, 0, 18, 18);
            }

        if((slots % 9) != 0)
            for(int x = 0; x < (slots % 9); x++) {
                this.blit(guiX + 7 + (x * 18), guiY + 17 + (slots/9 * 18), 0, 0, 18, 18);
            }
    }

    @Override
    protected void drawForeground(int int_1, int int_2) {
        this.font.draw(name, 8.0F, 6.0F, 4210752);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.drawMouseoverTooltip(mouseX, mouseY);
    }
}
