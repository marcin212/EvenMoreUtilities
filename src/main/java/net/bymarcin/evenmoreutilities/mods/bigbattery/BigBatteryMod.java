package net.bymarcin.evenmoreutilities.mods.bigbattery;

import net.bymarcin.evenmoreutilities.IMod;
import net.bymarcin.evenmoreutilities.mods.bigbattery.block.BlockBigBatteryControler;
import net.bymarcin.evenmoreutilities.mods.bigbattery.block.BlockBigBatteryElectrode;
import net.bymarcin.evenmoreutilities.mods.bigbattery.block.BlockBigBatteryGlass;
import net.bymarcin.evenmoreutilities.mods.bigbattery.block.BlockBigBatteryPowerTap;
import net.bymarcin.evenmoreutilities.mods.bigbattery.block.BlockBigBatteryWall;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityControler;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityElectrode;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityGlass;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityPowerTap;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityWall;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import erogenousbeef.core.multiblock.MultiblockClientTickHandler;
import erogenousbeef.core.multiblock.MultiblockServerTickHandler;

public class BigBatteryMod implements IMod{
	
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
		

	}

	
	
	@Override
	public void load() {
		
	}
	

	public static void server(){
		TickRegistry.registerTickHandler(new MultiblockServerTickHandler(), Side.SERVER);
	}
	
	public static void client(){
		TickRegistry.registerTickHandler(new MultiblockClientTickHandler(), Side.CLIENT);
	}
	

}
