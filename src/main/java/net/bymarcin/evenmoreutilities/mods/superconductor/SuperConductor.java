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
	public boolean active = false;
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
		boolean lactive = arg0.getBoolean("active");
		System.out.println(lactive);
		if(lactive != active){
			active = lactive;
			//XXX:HERE UPDATE RENDER
		}
	}

	@Override
	public void formatDescriptionPacket(NBTTagCompound arg0) {
		tank.writeToNBT(arg0);
		arg0.setBoolean("active", active);
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
		if(controlers.size()<2){
			throw new MultiblockValidationException("Wire must have minimum 2 controlers");
		}
		
	}

	@Override
	protected void onAssimilate(MultiblockControllerBase arg0) {
		if(arg0 instanceof SuperConductor){
			//XXX: TANK NULLEM w dziwnych momentach tank.getFluid().amount += ((SuperConductor) arg0).getTank().getFluidAmount();
		}
	}

	@Override
	protected void onAssimilated(MultiblockControllerBase arg0) {

	}

	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart arg0,
			NBTTagCompound arg1) {
		
	}

	@Override
	protected void onBlockAdded(IMultiblockPart arg0) {
		if(arg0 instanceof TileEntityControler){
			controlers.add((TileEntityControler) arg0);
		}
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart arg0) {
		if(arg0 instanceof TileEntityControler){
			controlers.remove((TileEntityControler) arg0);
		}
		
		if(arg0.worldObj.isRemote){
			//XXX:HERE UPDATE RENDER
			arg0.worldObj.markBlockForRenderUpdate(arg0.xCoord, arg0.yCoord, arg0.zCoord);
		}
	}

	@Override
	protected void onMachineAssembled() {
		tank.setCapacity(connectedParts.size()*4000);
		if(tank.getFluidAmount()>tank.getCapacity()){
			tank.getFluid().amount = tank.getCapacity();
		}
	}

	@Override
	protected void onMachineDisassembled() {
		
	}

	@Override
	protected void onMachinePaused() {

	}

	@Override
	protected void onMachineRestored() {
		tank.setCapacity(connectedParts.size()*4000);
		if(tank.getFluidAmount()>tank.getCapacity()){
			tank.getFluid().amount = tank.getCapacity();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound arg0) {
		tank.readFromNBT(arg0);	
		if(arg0.hasKey("ticksFromLastDrain"))
			ticksFromLastDrain = arg0.getInteger("ticksFromLastDrain");
	}

	@Override
	protected void updateClient() {
		
	}

	
	@Override
	protected boolean updateServer() {
		if(ticksFromLastDrain%10==0){
			for(TileEntityControler c : controlers)
				c.updateTick();
		}
		
		if(ticksFromLastDrain>= 12*60*60*20/1000){
			tank.drain(1, true);
			ticksFromLastDrain =0;
		}
		ticksFromLastDrain++;
		
		boolean newactive = (float)tank.getFluidAmount()/(float)tank.getCapacity()>=0.5;

		if(newactive != active){
			active = newactive;
			//XXX:HERE UPDATE RENDER
			worldObj.markBlockForUpdate(getReferenceCoord().x, getReferenceCoord().y,getReferenceCoord().z);
		}
		if(!active)
			return true;
		
		long energyNeed=0;
		for(TileEntityControler c : controlers){
			if(c.isOutput()){
				energyNeed += c.getEnergyNeed();
			}
		}
		long currentEnergy = 0;
		for(TileEntityControler c : controlers){
			if(!c.isOutput()){
				int temp = c.getEnergy(energyNeed>Integer.MAX_VALUE?Integer.MAX_VALUE:(int)energyNeed);
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
		return true;
	}

	@Override
	public void writeToNBT(NBTTagCompound arg0) {
		tank.writeToNBT(arg0);
		arg0.setInteger("ticksFromLastDrain", ticksFromLastDrain);
	}

}
