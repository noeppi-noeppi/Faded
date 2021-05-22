package io.github.noeppi_noeppi.mods.faded.mods.keksmagic;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.noeppi_noeppi.mods.faded.Faded;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import java.awt.*;

public class ScreenCookieAssembler extends ContainerScreen<ContainerCookieAssembler> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(Faded.getInstance().modid, "textures/container/keksmagic_cookie_assembler.png");

    private int relX;
    private int relY;
    
    protected ScreenCookieAssembler(ContainerCookieAssembler container, PlayerInventory inv, ITextComponent title) {
        super(container, inv, title);
        this.xSize = 176;
        this.ySize = 180;
    }

    @Override
    public void init(@Nonnull Minecraft mc, int x, int y) {
        super.init(mc, x, y);
        this.relX = (x - this.xSize) / 2;
        this.relY = (y - this.ySize) / 2;
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(@Nonnull MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        //noinspection deprecation
        RenderSystem.color4f(1, 1, 1, 1);
        //noinspection ConstantConditions
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        this.blit(matrixStack, this.relX, this.relY, 0, 0, this.xSize, this.ySize);
        CookieAssembler tile = this.container.tile;
        if (tile.getProgress() > 0) {
            int right = Math.round(((tile.getProgress() + 1) / (float) CookieAssembler.MAX_PROGRESS) * 24);
            this.blit(matrixStack, this.relX + 115, this.relY + 50, 176, 14, right, 17);
        }
        if (tile.getBurnTicks() > 0) {
            int up = Math.round((tile.getBurnTicks() / (float) tile.getMaxBurnTicks()) * 14);
            this.blit(matrixStack, this.relX + 13, this.relY + 79 - up, 176, 14 - up, 14, up);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(@Nonnull MatrixStack ms, int mouseX, int mouseY) {
        String s = this.title.getString();
        //noinspection IntegerDivisionInFloatingPointContext
        this.font.drawString(ms, s, (this.xSize / 2) - (this.font.getStringWidth(s) / 2), 5, Color.DARK_GRAY.getRGB());
        this.font.drawString(ms, this.playerInventory.getDisplayName().getString(), 8, this.ySize - 94, Color.DARK_GRAY.getRGB());
    }
}
