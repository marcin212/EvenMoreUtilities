package net.bymarcin.evenmoreutilities.mods.superconductor;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.IMod;
import net.bymarcin.evenmoreutilities.mods.superconductor.block.BlockControler;
import net.bymarcin.evenmoreutilities.mods.superconductor.block.BlockWire;
import net.bymarcin.evenmoreutilities.mods.superconductor.gui.ContainerControler;
import net.bymarcin.evenmoreutilities.mods.superconductor.gui.GuiControler;
import net.bymarcin.evenmoreutilities.mods.superconductor.gui.PacketUpdateFluidAmount;
import net.bymarcin.evenmoreutilities.mods.superconductor.render.GlowingRender;
import net.bymarcin.evenmoreutilities.mods.superconductor.tileentity.TileEntityControler;
import net.bymarcin.evenmoreutilities.mods.superconductor.tileentity.TileEntityWire;
import net.bymarcin.evenmoreutilities.registry.EMURegistry;
import net.bymarcin.evenmoreutilities.registry.IGUI;
import net.bymarcin.evenmoreutilities.registry.IProxy;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class SuperConductorMod implements IMod, IGUI, IProxy{
	public static int blockWireID;
	public static int blockControlerID;
	
	@Override
	public void init() {
		blockWireID = EvenMoreUtilities.instance.config.getBlock("BlocksId","blockWireID", 1030).getInt();
		blockControlerID = EvenMoreUtilities.instance.config.getBlock("BlocksId","blockControlerID", 1031).getInt();
		
		GameRegistry.registerBlock(BlockWire.instance, StaticValues.modId+":"+BlockWire.class.getSimpleName());
		GameRegistry.registerBlock(BlockControler.instance, StaticValues.modId+":"+BlockControler.class.getSimpleName());
		
		GameRegistry.registerTileEntity(TileEntityControler.class, StaticValues.modId+":"+TileEntityControler.class.getSimpleName());
		GameRegistry.registerTileEntity(TileEntityWire.class, StaticValues.modId+":"+TileEntityWire.class.getSimpleName());
		
		EMURegistry.registerGUI(this);
		EMURegistry.registerPacket(3, PacketUpdateFluidAmount.class);
		EMURegistry.registerProxy(this);
	}

	@Override
	public void postInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getServerGuiElement(int id, TileEntity blockEntity,
			EntityPlayer player, World world, int x, int y, int z) {
			if(blockEntity instanceof TileEntityControler){
				return new ContainerControler(player,(TileEntityControler) blockEntity);
			}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, TileEntity blockEntity,
			EntityPlayer player, World world, int x, int y, int z) {
		if(blockEntity instanceof TileEntityControler){
			return new GuiControler(player,(TileEntityControler) blockEntity);
		}
		return null;
	}

	
	public static int glowRenderID;
	public static int pass;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void clientSide() {
		glowRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new GlowingRender());
	}

	@Override
	@SideOnly(Side.SERVER)
	public void serverSide() {

	}

}
