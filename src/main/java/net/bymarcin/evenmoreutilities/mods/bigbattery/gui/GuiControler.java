package net.bymarcin.evenmoreutilities.mods.bigbattery.gui;

import net.bymarcin.evenmoreutilities.mods.bigbattery.BigBattery;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import forestry.core.gui.GuiTextBox;

public class GuiControler extends GuiScreen{
	BigBattery battery;
	GuiTextBox capacity;
	GuiTextBox transfer;
	GuiTextBox storage;
	private ResourceLocation guiTexture = new ResourceLocation(StaticValues.modId, "textures/gui/guicube.png");
	public final int xSize = 176;
	public final int ySize = 88;
	
	public GuiControler(BigBattery battery) {
		this.battery = battery;
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void initGui() {
		int posX = (this.width - xSize) / 2;
		int posY = (this.height - ySize) / 2;
		capacity = new GuiTextBox(fontRenderer, posX + 1, posY + 1, 174, 15);
		transfer = new GuiTextBox(fontRenderer, posX + 1, posY + 17, 174, 15);
		storage = new GuiTextBox(fontRenderer, posX + 1, posY + 33, 174, 15);
	}
	
	@Override
	public void drawScreen(int x, int y, float f) {
		drawDefaultBackground();
		int posX = (this.width - xSize) / 2;
		int posY = (this.height - ySize) / 2;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(guiTexture);

		drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);

		storage.drawTextBox();
		capacity.drawTextBox();
		transfer.drawTextBox();
		

		super.drawScreen(x, y, f);
	}
	
	@Override
	public void updateScreen() {
		storage.setText("Storage: " + battery.getStorage().getRealEnergyStored() + "RF");
		capacity.setText("Capacity: " + battery.getStorage().getRealMaxEnergyStored() + " RF");
		transfer.setText("Max Transfer: " + battery.getStorage().getMaxReceive());
		super.updateScreen();
	}
	
	
	
	
	
	public void keyTyped(char keyChar, int keyID)
	{
		super.keyTyped(keyChar, keyID);
		if (keyID == Keyboard.KEY_ESCAPE || keyID == mc.gameSettings.keyBindInventory.keyCode)
		{
			this.mc.displayGuiScreen((GuiScreen) null);
			this.mc.setIngameFocus();
		}
	}
}
