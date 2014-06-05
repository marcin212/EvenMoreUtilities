package net.bymarcin.evenmoreutilities.mods.redstonemitter;

import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;

public class GuiTiny extends GuiScreen {
	public final int xSize = 176;
	public final int ySize = 88;
	TileEntityRedstoneEmitter tile;
	int x;
	int y;
	int z;
	private GuiTextField textfield_types;
	private ResourceLocation guiTexture = new ResourceLocation(
			StaticValues.modId, "textures/gui/guicube.png");

	public GuiTiny(TileEntityRedstoneEmitter te) {
		tile = te;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		int posX = (this.width - xSize) / 2;
		int posY = (this.height - ySize) / 2;

		this.buttonList.clear();
		this.buttonList
				.add(new GuiButton(0, posX + 5, posY + 47, 40, 20, "- 1"));
		this.buttonList.add(new GuiButton(1, posX + 130, posY + 47, 40, 20,
				"+ 1"));
		textfield_types = new GuiTextField(fontRenderer, posX + 45, posY + 50,
				85, 15);
		textfield_types.setFocused(false);
		textfield_types.setMaxStringLength(2);
	}

	@Override
	public void drawScreen(int x, int y, float f) {
		drawDefaultBackground();
		int posX = (this.width - xSize) / 2;
		int posY = (this.height - ySize) / 2;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(guiTexture);

		drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);

		textfield_types.drawTextBox();

		super.drawScreen(x, y, f);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void updateScreen() {
		textfield_types.setText(Integer.toString(tile.getRedstoneSignal()));
	}

	public void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 0:
			// -1
			PacketDispatcher.sendPacketToServer(new PacketRedstoneChange(tile.xCoord,tile.yCoord,tile.zCoord,-1).makePacket());
			break;
		case 1:
			//+1
			PacketDispatcher.sendPacketToServer(new PacketRedstoneChange(tile.xCoord,tile.yCoord,tile.zCoord,1).makePacket());
			break;
		default:
			break;
		}
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