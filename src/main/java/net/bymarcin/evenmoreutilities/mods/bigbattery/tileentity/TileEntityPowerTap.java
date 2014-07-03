package net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity;

import net.bymarcin.evenmoreutilities.mods.bigbattery.BigBattery;
import net.minecraft.tileentity.TileEntity;
import erogenousbeef.core.multiblock.MultiblockControllerBase;
import erogenousbeef.core.multiblock.MultiblockValidationException;
import erogenousbeef.core.multiblock.rectangular.RectangularMultiblockTileEntityBase;

public class TileEntityPowerTap extends RectangularMultiblockTileEntityBase{

	@Override
	public void isGoodForFrame() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Power tap may not be placed in the battery's frame", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));	
	}

	@Override
	public void isGoodForSides() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Power tap may not be placed in the battery's sides", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));
	}

	@Override
	public void isGoodForTop() throws MultiblockValidationException {
		TileEntity entityBelow = this.worldObj.getBlockTileEntity(this.xCoord,  this.yCoord - 1, this.zCoord);
		if ((entityBelow instanceof TileEntityElectrode)) {
			return;
		}
		throw new MultiblockValidationException(String.format("%d, %d, %d - Power tap must be placed on electrode", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));
		
	}

	@Override
	public void isGoodForBottom() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Power tap may not be placed in the battery's bottom", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));
	}

	@Override
	public void isGoodForInterior() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Power tap may not be placed in the battery's interior", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));	
	}

	@Override
	public void onMachineActivated() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMachineDeactivated() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new BigBattery(this.worldObj);
	}

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return BigBattery.class;
	}

}
