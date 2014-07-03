package net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity;

import net.bymarcin.evenmoreutilities.mods.bigbattery.BigBattery;
import erogenousbeef.core.multiblock.MultiblockControllerBase;
import erogenousbeef.core.multiblock.MultiblockValidationException;
import erogenousbeef.core.multiblock.rectangular.RectangularMultiblockTileEntityBase;

public class TileEntityGlass extends RectangularMultiblockTileEntityBase{

	@Override
	public void isGoodForFrame() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Glass may not be placed in the battery's frame", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));	
	}

	@Override
	public void isGoodForSides() throws MultiblockValidationException {
		
	}

	@Override
	public void isGoodForTop() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Glass may not be placed in the battery's top", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));		
	}

	@Override
	public void isGoodForBottom() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Glass may not be placed in the battery's bottom", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));		
	}

	@Override
	public void isGoodForInterior() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Glass may not be placed in the battery's interior", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));		
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
