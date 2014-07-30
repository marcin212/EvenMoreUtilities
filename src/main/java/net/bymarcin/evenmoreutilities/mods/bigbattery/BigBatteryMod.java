package net.bymarcin.evenmoreutilities.mods.bigbattery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.IMod;
import net.bymarcin.evenmoreutilities.handler.BucketHandler;
import net.bymarcin.evenmoreutilities.mods.bigbattery.block.BlockBigBatteryComputerPort;
import net.bymarcin.evenmoreutilities.mods.bigbattery.block.BlockBigBatteryControler;
import net.bymarcin.evenmoreutilities.mods.bigbattery.block.BlockBigBatteryElectrode;
import net.bymarcin.evenmoreutilities.mods.bigbattery.block.BlockBigBatteryGlass;
import net.bymarcin.evenmoreutilities.mods.bigbattery.block.BlockBigBatteryPowerTap;
import net.bymarcin.evenmoreutilities.mods.bigbattery.block.BlockBigBatteryWall;
import net.bymarcin.evenmoreutilities.mods.bigbattery.block.BlockSulfur;
import net.bymarcin.evenmoreutilities.mods.bigbattery.fluid.AcidFluid;
import net.bymarcin.evenmoreutilities.mods.bigbattery.fluid.FluidBucket;
import net.bymarcin.evenmoreutilities.mods.bigbattery.gui.BigBatteryContainer;
import net.bymarcin.evenmoreutilities.mods.bigbattery.gui.EnergyUpdatePacket;
import net.bymarcin.evenmoreutilities.mods.bigbattery.gui.GuiControler;
import net.bymarcin.evenmoreutilities.mods.bigbattery.gui.GuiPowerTap;
import net.bymarcin.evenmoreutilities.mods.bigbattery.gui.PowerTapUpdatePacket;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityComputerPort;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityControler;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityElectrode;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityGlass;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityPowerTap;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityWall;
import net.bymarcin.evenmoreutilities.registry.EMURegistry;
import net.bymarcin.evenmoreutilities.registry.IGUI;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class BigBatteryMod implements IMod, IGUI{
	
	public static int  blockBigBatteryElectrodeID;
	public static int  blockBigBatteryGlassID;
	public static int  blockBigBatteryPowerTapID;
	public static int  blockBigBatteryWallID;
	public static int  blockBigBatteryComputerPortID;
	public static int  blockBigBatteryControlerID;
	public static int  itemAcidBucketID;
	
	public static int sulfurblockId;
	static HashMap<Fluid,Integer> electrolyteList = new HashMap<Fluid,Integer>();
	
	public static Fluid acid = new Fluid("sulfurousacid");
	public static int  blockAcidFluidID;
	/*Crafting items*/
	
	ItemStack obsidian;
	ItemStack gold;
	ItemStack sawDust;
	ItemStack redstone;
	ItemStack rfmeter;
	ItemStack enderFrame;
	ItemStack specialGlass;
	ItemStack electrum;
	ItemStack electrumFrame;
	ItemStack graphite;
	ItemStack sulfur;
	ItemStack gunpowder;
	
	@Override
	public void init() {
		
		sulfurblockId = EvenMoreUtilities.instance.config.getBlock("BlocksId","sulfurblockId", 1029).getInt();
		GameRegistry.registerBlock(BlockSulfur.instance,StaticValues.modId+":sulfurblock");
		
		blockAcidFluidID = EvenMoreUtilities.instance.config.getBlock("BlocksId","blockAcidFluidID", 1129).getInt();
		acid.setBlockID(blockAcidFluidID);
		FluidRegistry.registerFluid(acid);

		GameRegistry.registerBlock(AcidFluid.instance,StaticValues.modId+":sulfurousacid");
		
		itemAcidBucketID = EvenMoreUtilities.instance.config.getBlock("itemsid","AcidBucketID", 1010).getInt();
		GameRegistry.registerItem(FluidBucket.instance, StaticValues.modId+":AcidBucket");
		EMURegistry.registerBucket(blockAcidFluidID, FluidBucket.instance);
		
		
		FluidContainerRegistry.registerFluidContainer(
				FluidRegistry.getFluidStack(acid.getName(), FluidContainerRegistry.BUCKET_VOLUME),
				new ItemStack(FluidBucket.instance),
				new ItemStack(Item.bucketEmpty));
		MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
		
		blockBigBatteryWallID = EvenMoreUtilities.instance.config.getBlock("BlocksId","blockBigBatteryWallID", 1123).getInt();
		GameRegistry.registerBlock(BlockBigBatteryWall.instance, "MultiBlockBigBatteryWall");
		GameRegistry.registerTileEntity(TileEntityWall.class, "BigBatteryTileEntityWall");
		
		blockBigBatteryPowerTapID = EvenMoreUtilities.instance.config.getBlock("BlocksId","blockBigBatteryPowerTapID", 1124).getInt();
		GameRegistry.registerBlock(BlockBigBatteryPowerTap.instance, "MultiBlockBigBatteryPowerTap");
		GameRegistry.registerTileEntity(TileEntityPowerTap.class, "BigBatteryTileEntityPowerTap");
		
		blockBigBatteryGlassID = EvenMoreUtilities.instance.config.getBlock("BlocksId","blockBigBatteryGlassID", 1125).getInt();
		GameRegistry.registerBlock(BlockBigBatteryGlass.instance, "MultiBlockBigBatteryGlass");
		GameRegistry.registerTileEntity(TileEntityGlass.class, "BigBatteryTileEntityGlass");
		
		blockBigBatteryElectrodeID = EvenMoreUtilities.instance.config.getBlock("BlocksId","blockBigBatteryElectrodeID", 1126).getInt();
		GameRegistry.registerBlock(BlockBigBatteryElectrode.instance, "MultiBlockBigBatteryElectrode");
		GameRegistry.registerTileEntity(TileEntityElectrode.class, "BigBatteryTileEntityElectrode");
		
		blockBigBatteryControlerID = EvenMoreUtilities.instance.config.getBlock("BlocksId","blockBigBatteryControlerID", 1127).getInt();
		GameRegistry.registerBlock(BlockBigBatteryControler.instance, "MultiBlockBigBatteryControler");
		GameRegistry.registerTileEntity(TileEntityControler.class, "BigBatteryTileEntityControler");
		
		blockBigBatteryComputerPortID = EvenMoreUtilities.instance.config.getBlock("BlocksId","blockBigBatteryComputerPortID", 1128).getInt();
		GameRegistry.registerBlock(BlockBigBatteryComputerPort.instance, "MultiBlockBigBatteryComputerPort");
		GameRegistry.registerTileEntity(TileEntityComputerPort.class, "BigBatteryTileEntityComputerPort");
		
		EMURegistry.registerPacket(1, EnergyUpdatePacket.class);
		EMURegistry.registerPacket(2, PowerTapUpdatePacket.class);
		
		EMURegistry.registerGUI(this);
		
	}
	
	@Override
	public void postInit() {

		registerElectrolyte("redstone", 75000000);
		registerElectrolyte("ender", 100000000);
		registerElectrolyte("sulfurousacid", 150000000);
		
		redstone = new ItemStack(Item.redstone,1);
		obsidian = new ItemStack(Block.obsidian,1);
		gold = new ItemStack(Block.blockGold,1);
		gunpowder = new ItemStack(Item.gunpowder,1);
		
		electrum = GameRegistry.findItemStack("ThermalExpansion","ingotElectrum",1);
		sawDust = GameRegistry.findItemStack("ThermalExpansion","sawdustCompressed",1);
		specialGlass = GameRegistry.findItemStack("ThermalExpansion","lampFrame",1);
		
		rfmeter =GameRegistry.findItemStack("ThermalExpansion","multimeter",1);
		enderFrame =GameRegistry.findItemStack("ThermalExpansion","tesseractFrameEmpty",1);
		electrumFrame =GameRegistry.findItemStack("ThermalExpansion","cellReinforcedFrameEmpty",1);
		
		sulfur = GameRegistry.findItemStack("ThermalExpansion","dustSulfur",1);
		
		ArrayList<ItemStack> temp = OreDictionary.getOres("blockGraphite");
		if(temp!=null && temp.size()>0)
			graphite =  temp.get(0);

		if(electrum != null && sawDust != null && specialGlass != null && rfmeter != null &&
				enderFrame != null && electrumFrame != null && graphite != null && sulfur!=null && gunpowder!=null){ 
		
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockBigBatteryWall.instance,8), "ODE","OFE","ODE",
					'O',obsidian, 'D', sawDust, 'E', electrum, 'F', enderFrame));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(BlockBigBatteryControler.instance, "ODE","MRE","ODE",
					'O', obsidian, 'D', sawDust, 'E', electrum, 'M', rfmeter, 'R', electrumFrame));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(BlockBigBatteryElectrode.instance, "WGW","WGW","WWW",
					'W', graphite, 'G', gold));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockBigBatteryGlass.instance,4), "GGG","GFG","GGG",
					'G',specialGlass,'F', enderFrame));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(BlockBigBatteryPowerTap.instance, "ORO","DFD","EEE",
					'O', obsidian, 'D', sawDust, 'R', redstone, 'E', electrum, 'F', electrumFrame));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(BlockBigBatteryComputerPort.instance,"ODE","RMF","ODE",
					'O', obsidian, 'D', sawDust, 'E', electrum, 'M', rfmeter, 'R', redstone, 'F', enderFrame));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockSulfur.instance,1), "SGS","SSS","SSS",
					'S',sulfur, 'G', gunpowder));
		}
	}
	
	private void registerElectrolyte(String name, int valuePerBlock){
		Fluid temp = FluidRegistry.getFluid(name);
		if(temp!=null){
			electrolyteList.put(temp,valuePerBlock);
		}else{
			Logger.getLogger(StaticValues.modId).warning("Try add fluid" + name + "as electrolyte, but fluid not found!");
		}
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
	
	public static HashMap<Fluid, Integer> getElectrolyteList() {
		return electrolyteList;
	}
}
