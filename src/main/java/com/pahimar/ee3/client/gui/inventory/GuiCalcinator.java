package com.pahimar.ee3.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import com.pahimar.ee3.inventory.ContainerCalcinator;
import com.pahimar.ee3.reference.Textures;
import com.pahimar.ee3.tileentity.TileEntityCalcinator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCalcinator extends GuiContainer {

    private TileEntityCalcinator tileEntityCalcinator;

    public GuiCalcinator(InventoryPlayer inventoryPlayer, TileEntityCalcinator tileEntityCalcinator) {
        super(new ContainerCalcinator(inventoryPlayer, tileEntityCalcinator));
        ySize = 176;
        this.tileEntityCalcinator = tileEntityCalcinator;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        // NOOP
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(Textures.Gui.CALCINATOR);

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
        int scaleAdjustment;

        if (this.tileEntityCalcinator.getState() == 1) {
            scaleAdjustment = this.tileEntityCalcinator.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(
                    xStart + 46,
                    yStart + 22 + 23 - scaleAdjustment,
                    176,
                    12 - scaleAdjustment,
                    14,
                    scaleAdjustment + 2);
        }

        scaleAdjustment = this.tileEntityCalcinator.getCookProgressScaled(24);
        this.drawTexturedModalRect(xStart + 75, yStart + 30, 176, 14, scaleAdjustment + 1, 16);
    }
}
