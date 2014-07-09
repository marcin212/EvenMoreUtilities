package net.bymarcin.evenmoreutilities.mods.bigbattery;

import net.bymarcin.evenmoreutilities.IMod;
import net.bymarcin.evenmoreutilities.mods.bigbattery.block.BlockBigBatteryControler;
import net.bymarcin.evenmoreutilities.mods.bigbattery.block.BlockBigBatteryElectrode;
import net.bymarcin.evenmoreutilities.mods.bigbattery.block.BlockBigBatteryGlass;
import net.bymarcin.evenmoreutilities.mods.bigbattery.block.BlockBigBatteryPowerTap;
import net.bymarcin.evenmoreutilities.mods.bigbattery.block.BlockBigBatteryWall;
import net.bymarcin.evenmoreutilities.mods.bigbattery.gui.BigBatteryContainer;
import net.bymarcin.evenmoreutilities.mods.bigbattery.gui.EnergyUpdatePacket;
import net.bymarcin.evenmoreutilities.mods.bigbattery.gui.GuiControler;
import net.bymarcin.evenmoreutilities.mods.bigbattery.gui.GuiPowerTap;
import net.bymarcin.evenmoreutilities.mods.bigbattery.gui.PowerTapUpdatePacket;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityControler;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityElectrode;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityGlass;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityPowerTap;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityWall;
import net.bymarcin.evenmoreutilities.registry.EMURegistry;
import net.bymarcin.evenmoreutilities.registry.IGUI;
import net.bymarcin.evenmoreutilities.registry.IProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import erogenousbeef.core.multiblock.MultiblockClientTickHandler;
import erogenousbeef.core.multiblock.MultiblockServerTickHandler;

public class BigBatteryMod implements IMod, IGUI, IProxy{
	
	BlockBigBatteryControler blockBigBatteryControler;
	BlockBigBatteryElectrode blockBigBatteryElectrode;
	BlockBigBatteryGlass blockBigBatteryGlass;
	BlockBigBatteryPowerTap blockBigBatteryPowerTap;
	BlockBigBatteryWall blockBigBatteryWall;
	
	@Override
	public void init() {
		blockBigBatteryWall = new BlockBigBatteryWall(1123);
		GameRegistry.registerBlock(blockBigBatteryWall, "MultiBlockBigBatteryWall");
		GameRegistry.registerTileEntity(TileEntityWall.class, "BigBatteryTileEntityWall");
		
		blockBigBatteryPowerTap = new BlockBigBatteryPowerTap(1124);
		GameRegistry.registerBlock(blockBigBatteryPowerTap, "MultiBlockBigBatteryPowerTap");
		GameRegistry.registerTileEntity(TileEntityPowerTap.class, "BigBatteryTileEntityPowerTap");
		
		blockBigBatteryGlass = new BlockBigBatteryGlass(1125);
		GameRegistry.registerBlock(blockBigBatteryGlass, "MultiBlockBigBatteryGlass");
		GameRegistry.registerTileEntity(TileEntityGlass.class, "BigBatteryTileEntityGlass");
		
		blockBigBatteryElectrode = new BlockBigBatteryElectrode(1126);
		GameRegistry.registerBlock(blockBigBatteryElectrode, "MultiBlockBigBatteryElectrode");
		GameRegistry.registerTileEntity(TileEntityElectrode.class, "BigBatteryTileEntityElectrode");
		
		blockBigBatteryControler = new BlockBigBatteryControler(1127);
		GameRegistry.registerBlock(blockBigBatteryControler, "MultiBlockBigBatteryControler");
		GameRegistry.registerTileEntity(TileEntityControler.class, "BigBatteryTileEntityControler");
		
		EMURegistry.registerPacket(1, EnergyUpdatePacket.class);
		EMURegistry.registerPacket(2, PowerTapUpdatePacket.class);
		
		EMURegistry.registerGUI(this);
	}
	
	@Override
	public void postInit() {
		
	}

	@Override
	public Object getServerGuiElement(int id, TileEntity blockEntity,
			EntityPlayer player, World world, int x, int y, int z) {
   	 	if(blockEntity instanceof TileEntityControler){
   	 		return new BigBatteryContainer((TileEntityControler) blockEntity, player);
   	 	}
   	 	
   	 	if(blockEntity instanceof TileEntityPowerTap){
   	 		return ((TileEntityPowerTap) blockEntity).getContainer(player);
   	 	}
   	 	
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, TileEntity blockEntity,
			EntityPlayer player, World world, int x, int y, int z) {
        if(blockEntity instanceof TileEntityControler){
        	return new GuiControler((BigBattery) ((TileEntityControler)blockEntity).getMultiblockController(),
        			((BigBattery) ((TileEntityControler)blockEntity).getMultiblockController()).getContainer(player));
        }
        
        if(blockEntity instanceof TileEntityPowerTap){
        	return new GuiPowerTap(((TileEntityPowerTap) blockEntity).getContainer(player),(TileEntityPowerTap) blockEntity);
        }
        
		return null;
	}

	@Override
	public void clientSide() {
		TickRegistry.registerTickHandler(new MultiblockClientTickHandler(), Side.CLIENT);
	}

	@Override
	public void serverSide() {
		TickRegistry.registerTickHandler(new MultiblockServerTickHandler(), Side.SERVER);
	}
}
