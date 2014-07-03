package net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity;

import net.bymarcin.evenmoreutilities.mods.bigbattery.BigBattery;
import erogenousbeef.core.multiblock.MultiblockControllerBase;
import erogenousbeef.core.multiblock.MultiblockValidationException;
import erogenousbeef.core.multiblock.rectangular.RectangularMultiblockTileEntityBase;

public class TileEntityWall extends RectangularMultiblockTileEntityBase{

	@Override
	public void onMachineBroken() {	
	}

	@Override
	public void onMachineActivated() {
	}

	@Override
	public void onMachineDeactivated() {	
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new BigBattery(this.worldObj);
	}

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return BigBattery.class;
	}

	@Override
	public void isGoodForFrame() throws MultiblockValidationException {
		
	}

	@Override
	public void isGoodForSides() throws MultiblockValidationException {
		
	}

	@Override
	public void isGoodForTop() throws MultiblockValidationException {
		
	}

	@Override
	public void isGoodForBottom() throws MultiblockValidationException {
		
	}

	@Override
	public void isGoodForInterior() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Wall may not be placed in the battery's interior", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));
	}
}
