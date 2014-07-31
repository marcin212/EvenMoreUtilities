package net.bymarcin.evenmoreutilities.mods.superconductor;

import java.util.ArrayList;

import net.bymarcin.evenmoreutilities.mods.superconductor.tileentity.TileEntityControler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import erogenousbeef.core.multiblock.IMultiblockPart;
import erogenousbeef.core.multiblock.MultiblockControllerBase;
import erogenousbeef.core.multiblock.MultiblockValidationException;

public class SuperConductor extends MultiblockControllerBase{
	ArrayList<TileEntityControler> controlers;
	FluidTank tank = new FluidTank(0);
	int ticksFromLastDrain = 0;
	public SuperConductor(World world) {
		super(world);
		controlers = new ArrayList<TileEntityControler>();
	}

	public FluidTank getTank() {
		return tank;
	}
	
	@Override
	public void decodeDescriptionPacket(NBTTagCompound arg0) {
		tank.readFromNBT(arg0);	
	}

	@Override
	public void formatDescriptionPacket(NBTTagCompound arg0) {
		tank.writeToNBT(arg0);	
	}

	@Override
	protected int getMaximumXSize() {
		return Integer.MAX_VALUE;
	}

	@Override
	protected int getMaximumYSize() {
		return Integer.MAX_VALUE;
	}

	@Override
	protected int getMaximumZSize() {
		return Integer.MAX_VALUE;
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 3;
	}

	@Override
	protected void isMachineWhole() throws MultiblockValidationException {
		if(controlers.size()<3){
			throw new MultiblockValidationException("Wire must have minimum 2 controlers");
		}
		
	}

	@Override
	protected void onAssimilate(MultiblockControllerBase arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onAssimilated(MultiblockControllerBase arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart arg0,
			NBTTagCompound arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onBlockAdded(IMultiblockPart arg0) {
		if(arg0 instanceof TileEntityControler){
			controlers.add((TileEntityControler) arg0);
		}
		tank.setCapacity(tank.getCapacity()+4000);	
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart arg0) {
		if(arg0 instanceof TileEntityControler){
			controlers.remove((TileEntityControler) arg0);
		}
		tank.setCapacity(Math.max(0,tank.getCapacity()-4000));
	}

	@Override
	protected void onMachineAssembled() {
		System.out.println("ASSEMBLKED");
		
	}

	@Override
	protected void onMachineDisassembled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMachinePaused() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMachineRestored() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readFromNBT(NBTTagCompound arg0) {
		tank.readFromNBT(arg0);	
		if(arg0.hasKey("ticksFromLastDrain"))
			ticksFromLastDrain = arg0.getInteger("ticksFromLastDrain");
	}

	@Override
	protected void updateClient() {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	protected boolean updateServer() {
		if(ticksFromLastDrain%10==0){
			for(TileEntityControler c : controlers)
				c.updateTick();
		}
		
		if(ticksFromLastDrain>=864){
			tank.drain(1, true);
			ticksFromLastDrain =0;
		}
		ticksFromLastDrain++;
		
		if((float)tank.getFluidAmount()/(float)tank.getCapacity()<0.5) return true;
		
		long energyNeed=0;
		for(TileEntityControler c : controlers){
			if(c.isOutput()){
				energyNeed += c.getEnergyNeed();
			}
		}
		System.out.println("ENERGY NEED: " + energyNeed);
		long currentEnergy = 0;
		for(TileEntityControler c : controlers){
			if(!c.isOutput()){
				int temp = c.getEnergy(energyNeed>Integer.MAX_VALUE?Integer.MAX_VALUE:(int)energyNeed);
				System.out.println("RECIVED:" + temp);
				currentEnergy += temp;
				energyNeed -= temp;	
				if(energyNeed<=0) break;
			}
		}
		
		for(TileEntityControler c : controlers){
			if(c.isOutput()){
				currentEnergy -= c.putEnergy(currentEnergy>Integer.MAX_VALUE?Integer.MAX_VALUE:(int)currentEnergy);
				if(currentEnergy<=0) break;
			}
		}
		System.out.println("ENERGY WASTE:" + currentEnergy);
		
		

		return true;
	}

	@Override
	public void writeToNBT(NBTTagCompound arg0) {
		tank.writeToNBT(arg0);
		arg0.setInteger("ticksFromLastDrain", ticksFromLastDrain);
	}

}
