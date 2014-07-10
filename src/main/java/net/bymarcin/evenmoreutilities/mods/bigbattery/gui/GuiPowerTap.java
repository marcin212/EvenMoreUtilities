package net.bymarcin.evenmoreutilities.mods.bigbattery.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityPowerTap;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiPowerTap extends GuiContainer{
	GuiButton plus;
	GuiButton minus;
	TileEntityPowerTap tile;
	
	
	
	private ResourceLocation guiTexture = new ResourceLocation(StaticValues.modId, "textures/gui/guicube.png");
	public final int xSize = 176;
	public final int ySize = 88;
	
	public GuiPowerTap(Container arg0, TileEntityPowerTap tile) {
		super(arg0);
		this.tile = tile;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		buttonList.clear();
		int posX = (this.width - xSize) / 2;
		int posY = (this.height - ySize) / 2;
							//id, x, y, width, height, text
        plus = new GuiButton(1, posX+145, posY+20, 20, 20, "+");
        minus = new GuiButton(2, posX+10, posY+20, 20, 20, "-");
        buttonList.add(minus);
        buttonList.add(plus);
        
		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int posX = (this.width - xSize) / 2;
		int posY = (this.height - ySize) / 2;
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(guiTexture);
		drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);
		fontRenderer.drawString(String.valueOf(tile.getTransferCurrent()),posX+10+20+10, posY+20+5, 0);
		
	}

	
	@Override
	protected void actionPerformed(GuiButton button) {
		switch(button.id){
			case 1: PacketDispatcher.sendPacketToServer(new PowerTapUpdatePacket(tile.xCoord, tile.yCoord, tile.zCoord, tile.getTransferCurrent()+100, PowerTapUpdatePacket.PLUS).makePacket()); break;
			case 2: PacketDispatcher.sendPacketToServer(new PowerTapUpdatePacket(tile.xCoord, tile.yCoord, tile.zCoord, tile.getTransferCurrent()-100, PowerTapUpdatePacket.MINUS).makePacket()); break;
		}
		
		super.actionPerformed(button);
	}
	
	
	
	
	
	
}
