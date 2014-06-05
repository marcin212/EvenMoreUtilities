package net.bymarcin.evenmoreutilities.mods.sensor;

import net.minecraft.world.World;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;

public class CCPeripheralProvider implements IPeripheralProvider{
	@Override
	public IPeripheral getPeripheral(World world, int x, int y, int z, int side) {
		if(world.getBlockTileEntity(x, y, z)!=null && world.getBlockTileEntity(x, y, z) instanceof TileEntitySensor)
			return (IPeripheral) world.getBlockTileEntity(x, y, z);
		else
			return null;
	}
}
