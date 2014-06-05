package net.bymarcin.evenmoreutilities.mods.sensor;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.IMod;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.computercraft.api.ComputerCraftAPI;

public class SensorMod implements IMod{
	public static int SensorID;
	ItemStack coil;
	ItemStack machinframe;
	@Override
	public void init() {
		SensorID = EvenMoreUtilities.instance.config.getBlock("sensorID", 2048).getInt();
        GameRegistry.registerBlock(BlockSensor.instance,StaticValues.modId+":Sensor");
        GameRegistry.registerTileEntity(TileEntitySensor.class, StaticValues.modId+".Sensor");
        
    	coil = GameRegistry.findItemStack("ThermalExpansion", "powerCoilSilver", 1);
    	machinframe = GameRegistry.findItemStack("ThermalExpansion", "machineFrame", 1);       
    	GameRegistry.addRecipe(new ItemStack(BlockSensor.instance), "ggg", "xyx", "ggg",
            'x', coil, 'y', machinframe, 'g', Block.glass);
    	ComputerCraftAPI.registerPeripheralProvider(new CCPeripheralProvider());
	
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

}
