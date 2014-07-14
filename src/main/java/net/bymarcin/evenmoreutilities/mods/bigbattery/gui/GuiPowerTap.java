package net.bymarcin.evenmoreutilities.mods.bigbattery.gui;

import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityPowerTap;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;

public class GuiPowerTap extends GuiContainer{
	TileEntityPowerTap tile;
	GuiButton plus5;
	GuiButton minus5;
	
	GuiButton plus10;
	GuiButton minus10;
	
	GuiButton plus100;
	GuiButton minus100;
	
	GuiButton plus1000;
	GuiButton minus1000;
	
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
        plus5 = new GuiButton(1, posX+10, posY+10, 20, 20, "+5");
        minus5 = new GuiButton(2, posX+10, posY+60, 20, 20, "-5");
        
        plus10 = new GuiButton(3, posX+35, posY+10, 25, 20, "+10");
        minus10 = new GuiButton(4, posX+35, posY+60, 25, 20, "-10");
        
        plus100 = new GuiButton(5, posX+65, posY+10, 30, 20, "+100");
        minus100 = new GuiButton(6, posX+65, posY+60, 30, 20, "-100");
        
        plus1000 = new GuiButton(7, posX+100, posY+10, 40, 20, "+1000");
        minus1000 = new GuiButton(8, posX+100, posY+60, 40, 20, "-1000");
        
        buttonList.add(minus5);
        buttonList.add(plus5);
        
        buttonList.add(minus10);
        buttonList.add(plus10);
        
        buttonList.add(minus100);
        buttonList.add(plus100);
        
        buttonList.add(minus1000);
        buttonList.add(plus1000);
        
		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int posX = (this.width - xSize) / 2;
		int posY = (this.height - ySize) / 2;
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(guiTexture);
		drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);
		fontRenderer.drawString("Current transfer: "+String.valueOf(tile.getTransferCurrent())+" RF/t",posX+10, posY+10+20+10, 0);
		
	}

	
	@Override
	protected void actionPerformed(GuiButton button) {
		switch(button.id){
			case 1: PacketDispatcher.sendPacketToServer(new PowerTapUpdatePacket(tile.xCoord, tile.yCoord, tile.zCoord, tile.getTransferCurrent()+5, PowerTapUpdatePacket.PLUS).makePacket()); break;
			case 2: PacketDispatcher.sendPacketToServer(new PowerTapUpdatePacket(tile.xCoord, tile.yCoord, tile.zCoord, tile.getTransferCurrent()-5, PowerTapUpdatePacket.MINUS).makePacket()); break;
			
			case 3: PacketDispatcher.sendPacketToServer(new PowerTapUpdatePacket(tile.xCoord, tile.yCoord, tile.zCoord, tile.getTransferCurrent()+10, PowerTapUpdatePacket.MINUS).makePacket()); break;
			case 4: PacketDispatcher.sendPacketToServer(new PowerTapUpdatePacket(tile.xCoord, tile.yCoord, tile.zCoord, tile.getTransferCurrent()-10, PowerTapUpdatePacket.MINUS).makePacket()); break;
			
			case 5: PacketDispatcher.sendPacketToServer(new PowerTapUpdatePacket(tile.xCoord, tile.yCoord, tile.zCoord, tile.getTransferCurrent()+100, PowerTapUpdatePacket.MINUS).makePacket()); break;
			case 6: PacketDispatcher.sendPacketToServer(new PowerTapUpdatePacket(tile.xCoord, tile.yCoord, tile.zCoord, tile.getTransferCurrent()-100, PowerTapUpdatePacket.MINUS).makePacket()); break;		
		
			case 7: PacketDispatcher.sendPacketToServer(new PowerTapUpdatePacket(tile.xCoord, tile.yCoord, tile.zCoord, tile.getTransferCurrent()+1000, PowerTapUpdatePacket.MINUS).makePacket()); break;
			case 8: PacketDispatcher.sendPacketToServer(new PowerTapUpdatePacket(tile.xCoord, tile.yCoord, tile.zCoord, tile.getTransferCurrent()-1000, PowerTapUpdatePacket.MINUS).makePacket()); break;		
		
		}
		
		super.actionPerformed(button);
	}
	
	
	
	
	
	
}
