package net.bymarcin.evenmoreutilities.mods.bigbattery.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityPowerTap;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public class GuiPowerTap extends GuiContainer{
	GuiButton plus;
	GuiButton minus;
	TileEntityPowerTap tile;
	
	public GuiPowerTap(Container arg0, TileEntityPowerTap tile) {
		super(arg0);
		this.tile = tile;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		buttonList.clear();
							//id, x, y, width, height, text
        plus = new GuiButton(1, 200, 52, 20, 20, "+");
        minus = new GuiButton(2, 200, 74, 20, 20, "-");
        buttonList.add(minus);
        buttonList.add(plus);
        
		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		fontRenderer.drawString(String.valueOf(tile.getTransferCurrent()),40, 52, 0);
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
