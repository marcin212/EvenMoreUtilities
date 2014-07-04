package net.bymarcin.evenmoreutilities.mods.bigbattery;

import java.util.HashSet;
import java.util.Set;

import cofh.api.energy.EnergyStorage;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityControler;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityPowerTap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowing;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.BlockStationary;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import cpw.mods.fml.common.FMLLog;
import erogenousbeef.core.multiblock.IMultiblockPart;
import erogenousbeef.core.multiblock.MultiblockControllerBase;
import erogenousbeef.core.multiblock.MultiblockValidationException;
import erogenousbeef.core.multiblock.rectangular.RectangularMultiblockControllerBase;

public class BigBattery extends RectangularMultiblockControllerBase{

	private Set<TileEntityPowerTap> powerTaps;
	private Set<TileEntityControler> controlers;
	private int electrolyte = 0;
	private EnergyStorage storage = new EnergyStorage(Integer.MAX_VALUE,10000,10000);;
	int i = 0;
	
	public BigBattery(World world) {
		super(world);
		powerTaps = new HashSet<TileEntityPowerTap>();
		controlers = new HashSet<TileEntityControler>();
	}

	public EnergyStorage getStorage() {
		return storage;
	}
	
	@Override
	protected void onMachinePaused() {
		FMLLog.info("Machine %d PAUSED", hashCode());
	}
	
	@Override
	protected void onMachineAssembled() {	
		storage.setCapacity(electrolyte);
		FMLLog.info("Machine %d ASSEMBLED", hashCode());
		FMLLog.info("Zawieram %d -powerTaps and %d -controlers and %d-electrolyte", powerTaps.size(),controlers.size(), electrolyte);
	}
	
	public boolean isSourceFluid(int x, int y, int z){
		Block block = Block.blocksList[worldObj.getBlockId(x, y, z)];

		if(block instanceof BlockFluid && worldObj.getBlockMetadata(x, y, z)==0){
			return true;
		}
		
		if(block instanceof BlockFluidClassic  && worldObj.getBlockMetadata(x, y, z)==0){
			return true;
		}	
		
		if(block instanceof BlockStationary && worldObj.getBlockMetadata(x, y, z)==0){
			return true;
		}
		
		if(block instanceof BlockFlowing && worldObj.getBlockMetadata(x, y, z)==0){
			return true;
		}
		
		return false;
	}
	
	public int checkElectrolyte(int x, int y, int z){
		if(isSourceFluid(x, y, z))
			return 1;
		return 0;
	}
	
	@Override
	protected void onMachineDisassembled() {
		electrolyte=0;
		FMLLog.info("Machine %d DISASSEMBLED", hashCode());
	}

	@Override
	protected void onMachineRestored() {
		FMLLog.info("Machine %d RESTORED", hashCode());
	}
	
	@Override
	protected void isMachineWhole() throws MultiblockValidationException {
		if(powerTaps.size()==0){
			throw new MultiblockValidationException("BigBattery must have power tap");	
		}
		
		if(controlers.size() == 0){
			throw new MultiblockValidationException("BigBattery must have controler");	
		}
		
		if(controlers.size() > 1){
			throw new MultiblockValidationException("BigBattery have too many controlers");	
		}
		
		electrolyte = 0;
		for(int x=getMinimumCoord().x; x < getMaximumCoord().x; x++){
			for(int y=getMinimumCoord().y; y < getMaximumCoord().y; y++){
				for(int z=getMinimumCoord().z; z < getMaximumCoord().z; z++){
					electrolyte += checkElectrolyte(x,y,z)*50000000;
				}
			}
		}
		
		if(electrolyte == 0){
			throw new MultiblockValidationException("BigBattery must have electrolyte");	
		}
		
		if(electrolyte > Integer.MAX_VALUE){
			throw new MultiblockValidationException("Electrolyte overflow integer");	
		}
		
		super.isMachineWhole();
	}
	
	@Override
	protected void isBlockGoodForInterior(World world, int x, int y, int z)
			throws MultiblockValidationException {
		if(world.isAirBlock(x, y, z)) { return; }
		Material material = world.getBlockMaterial(x, y, z);
			if(material == net.minecraft.block.material.MaterialLiquid.water) {
				return;
		}
		int blockId = world.getBlockId(x, y, z);
		throw new MultiblockValidationException(String.format("%d, %d, %d - Unrecognized block with ID %d, not valid for the reactor's interior", x, y, z, blockId));	
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
		if(electrolyte==0) return false;
		for(TileEntityPowerTap powerTap: powerTaps){
			powerTap.onTransferEnergy();
		}
		return true;
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		readFromNBT(data);	
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
		
	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {
		
	}

	@Override
	protected void updateClient() {
		
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		data.setInteger("electrolyte", electrolyte);
		storage.writeToNBT(data);	
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		if(data.hasKey("electrolyte")){
			electrolyte = data.getInteger("electrolyte");
		}
		storage.readFromNBT(data);
	}

	@Override
	public void formatDescriptionPacket(NBTTagCompound data) {
		data.setInteger("electrolyte", electrolyte);
		storage.writeToNBT(data);
	}

	@Override
	public void decodeDescriptionPacket(NBTTagCompound data) {
		if(data.hasKey("electrolyte")){
			electrolyte = data.getInteger("electrolyte");
		}
		storage.readFromNBT(data);
	}
}
