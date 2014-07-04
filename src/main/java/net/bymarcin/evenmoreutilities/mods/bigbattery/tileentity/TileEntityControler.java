package net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity;

import net.bymarcin.evenmoreutilities.mods.bigbattery.BigBattery;
import erogenousbeef.core.multiblock.MultiblockControllerBase;
import erogenousbeef.core.multiblock.MultiblockValidationException;
import erogenousbeef.core.multiblock.rectangular.RectangularMultiblockTileEntityBase;

public class TileEntityControler extends RectangularMultiblockTileEntityBase{

	@Override
	public void isGoodForFrame() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Controler may only be placed in the battery frame", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));		
	}

	@Override
	public void isGoodForSides() throws MultiblockValidationException {
		
	}

	@Override
	public void isGoodForTop() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Controler may only be placed in the battery top", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));		
	}

	@Override
	public void isGoodForBottom() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Controler may only be placed in the battery bottom", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));			
	}

	@Override
	public void isGoodForInterior() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Controler may only be placed in the battery interior", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));		
	}

	@Override
	public void onMachineActivated() {
		
	}

	@Override
	public void onMachineDeactivated() {
		
	}
	
	@Override
	public void onMachineAssembled(MultiblockControllerBase controller) {
		super.onMachineAssembled(controller);
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
	}
	
	@Override
	public void onMachineBroken() {
		super.onMachineBroken();
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
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
