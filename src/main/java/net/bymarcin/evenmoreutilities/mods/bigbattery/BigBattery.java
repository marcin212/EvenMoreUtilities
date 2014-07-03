package net.bymarcin.evenmoreutilities.mods.bigbattery;

import java.util.HashSet;
import java.util.Set;

import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityControler;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityPowerTap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;
import erogenousbeef.core.multiblock.IMultiblockPart;
import erogenousbeef.core.multiblock.MultiblockControllerBase;
import erogenousbeef.core.multiblock.rectangular.RectangularMultiblockControllerBase;

public class BigBattery extends RectangularMultiblockControllerBase{

	private Set<TileEntityPowerTap> powerTaps;
	private Set<TileEntityControler> controlers;
	
	
	public BigBattery(World world) {
		super(world);
		powerTaps = new HashSet<TileEntityPowerTap>();
		controlers = new HashSet<TileEntityControler>();
	}

	@Override
	protected void onMachinePaused() {
		FMLLog.info("Machine %d PAUSED", hashCode());
	}
	
	@Override
	protected void onMachineAssembled() {
		FMLLog.info("Machine %d ASSEMBLED", hashCode());
		FMLLog.info("Zawieram %d -powerTaps and %d -controlers", powerTaps.size(),controlers.size());
	}
	
	@Override
	protected void onMachineDisassembled() {
		FMLLog.info("Machine %d DISASSEMBLED", hashCode());
	}

	@Override
	protected void onMachineRestored() {
		FMLLog.info("Machine %d RESTORED", hashCode());
	}
	
	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 3*3*3;
	}

	@Override
	protected int getMaximumXSize() {
		return 32;
	}

	@Override
	protected int getMaximumZSize() {
		return 32;
	}

	@Override
	protected int getMaximumYSize() {
		return 32;
	}


	@Override
	protected boolean updateServer() {
		return false;
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part,
			NBTTagCompound data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		if(newPart instanceof TileEntityPowerTap){
			powerTaps.add((TileEntityPowerTap) newPart);
		}
		
		if(newPart instanceof TileEntityControler){
			controlers.add((TileEntityControler) newPart);
		}
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		if(oldPart instanceof TileEntityPowerTap){
			powerTaps.remove((TileEntityPowerTap) oldPart);
		}
		
		if(oldPart instanceof TileEntityControler){
			controlers.remove((TileEntityControler) oldPart);
		}
	}

	@Override
	protected void onAssimilate(MultiblockControllerBase assimilated) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateClient() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void formatDescriptionPacket(NBTTagCompound data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decodeDescriptionPacket(NBTTagCompound data) {
		// TODO Auto-generated method stub
		
	}



}
