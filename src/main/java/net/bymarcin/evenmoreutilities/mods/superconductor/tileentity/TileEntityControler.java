package net.bymarcin.evenmoreutilities.mods.superconductor.tileentity;

import java.util.ArrayList;

import net.bymarcin.evenmoreutilities.mods.superconductor.SuperConductor;
import net.bymarcin.evenmoreutilities.mods.superconductor.gui.PacketUpdateFluidAmount;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import cofh.api.energy.IEnergyHandler;
import cofh.util.BlockHelper;
import cofh.util.EnergyHelper;
import cofh.util.ServerHelper;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import erogenousbeef.core.multiblock.MultiblockControllerBase;

public class TileEntityControler extends TileEntityBase implements
		IEnergyHandler, IFluidHandler {
	ArrayList<Player> players = new ArrayList<Player>();

	
	public int putEnergy(int energy) {
		int ownEnergy = energy;
		int consumeEnergy = 0;
		if (ServerHelper.isClientWorld(worldObj) || !isOutput())
			return 0;
		
		for(ForgeDirection d: ForgeDirection.VALID_DIRECTIONS){
			TileEntity tile = BlockHelper.getAdjacentTileEntity(this, d);
			int energyGet = 0;
			if (EnergyHelper.isEnergyHandlerFromSide(tile, ForgeDirection.VALID_DIRECTIONS[(d.ordinal() ^ 0x1)])) {
				energyGet = ((IEnergyHandler) tile).receiveEnergy(ForgeDirection.VALID_DIRECTIONS[(d.ordinal() ^ 0x1)], ownEnergy, false);
			}
			ownEnergy-=energyGet;
			consumeEnergy+=energyGet;
			if(ownEnergy<=0) break;
		}
		return consumeEnergy;
	}


	protected Packet getUpdatePacket(){
	     return new PacketUpdateFluidAmount(xCoord, yCoord, zCoord, getTank().getFluidAmount(), ((getTank().getFluid()!=null)?getTank().getFluid().fluidID:0)).makePacket();
	}
	
	public void updateTick(){
		for(Player p  : players)
			PacketDispatcher.sendPacketToPlayer(getUpdatePacket(), p);
	}
	
	public void addPlayerToUpdate(Player p){
		players.add(p);
		PacketDispatcher.sendPacketToPlayer(getUpdatePacket(), p);
	}
	
	public void removePlayerFromUpdate(Player p){
		players.remove(p);
	}
	
	public int getEnergy(int amountEnergy) {
		int queryEnergy = amountEnergy;
		int responseEnergy = 0;
		
		if (ServerHelper.isClientWorld(worldObj) || isOutput())
			return 0;
		
		for(ForgeDirection d: ForgeDirection.VALID_DIRECTIONS){
			TileEntity tile = BlockHelper.getAdjacentTileEntity(this,d);
			int energyGet = 0;
			if (EnergyHelper.isEnergyHandlerFromSide(tile,ForgeDirection.VALID_DIRECTIONS[(d.ordinal() ^ 0x1)])) {
				energyGet = ((IEnergyHandler) tile).extractEnergy(ForgeDirection.VALID_DIRECTIONS[(d.ordinal() ^ 0x1)], queryEnergy,false);
			}
			responseEnergy+=energyGet;
			queryEnergy-=energyGet;
			if(queryEnergy<=0) break;
		}
		return responseEnergy;
	}

	public long getEnergyNeed() {
		if (ServerHelper.isClientWorld(worldObj) || !isOutput())
			return 0;
		int energyGet = 0;
		for(ForgeDirection d: ForgeDirection.VALID_DIRECTIONS){
			TileEntity tile = BlockHelper.getAdjacentTileEntity(this,d);
			if (EnergyHelper.isEnergyHandlerFromSide(tile,ForgeDirection.VALID_DIRECTIONS[(d.ordinal() ^ 0x1)])) {
				energyGet += ((IEnergyHandler) tile).receiveEnergy(ForgeDirection.VALID_DIRECTIONS[(d.ordinal() ^ 0x1)],Integer.MAX_VALUE, true);
			}
		}
		return energyGet;
	}

	public boolean isOutput() {
		return worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 0;
	}

	@Override
	public void onMachineActivated() {

	}

	@Override
	public void onMachineAssembled(MultiblockControllerBase arg0) {

	}

	@Override
	public void onMachineBroken() {

	}

	@Override
	public void onMachineDeactivated() {

	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract,boolean simulate) {
		return 0;
	}

	@Override
	public boolean canInterface(ForgeDirection from) {
		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
	}

	FluidTank getTank(){
		SuperConductor c = (SuperConductor) getMultiblockController();
		return c!=null?c.getTank():null;
	}
	
	/* IFluidHandler */
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
    	if(resource.fluidID == FluidRegistry.getFluidID("cryotheum") && getTank()!=null)
    		return getTank().fill(resource, doFill);
    	else
    		return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if (resource == null || !resource.isFluidEqual(getTank().getFluid()))
        {
            return null;
        }
        return getTank().drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        return getTank().drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid)
    {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid)
    {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[] { getTank().getInfo() };
    }
}
