package net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity;

import net.bymarcin.evenmoreutilities.mods.bigbattery.BigBattery;
import net.minecraft.tileentity.TileEntity;
import erogenousbeef.core.multiblock.MultiblockControllerBase;
import erogenousbeef.core.multiblock.MultiblockValidationException;
import erogenousbeef.core.multiblock.rectangular.RectangularMultiblockTileEntityBase;

public class TileEntityElectrode extends RectangularMultiblockTileEntityBase{

	@Override
	public void isGoodForFrame() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Electrode may only be placed in the battery interior", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));	
	}

	@Override
	public void isGoodForSides() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Electrode may only be placed in the battery interior", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));	
	}

	@Override
	public void isGoodForTop() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Electrode may only be placed in the battery interior", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));	
	}

	@Override
	public void isGoodForBottom() throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Electrode may only be placed in the battery interior", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));	
	}

	@Override
	public void isGoodForInterior() throws MultiblockValidationException {
		TileEntity entityAbove = this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord + 1, this.zCoord);
		if ((!(entityAbove instanceof TileEntityElectrode)) && (!(entityAbove instanceof TileEntityPowerTap))) {
			  throw new MultiblockValidationException(String.format("Electrode at %d, %d, %d must be part of a vertical column that reaches the entire height of the battery, with a power tap on top.", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));
		}
		TileEntity entityBelow = this.worldObj.getBlockTileEntity(this.xCoord,  this.yCoord - 1, this.zCoord);
		if ((entityBelow instanceof TileEntityElectrode)) {
			return;
		}
		if ((entityBelow instanceof RectangularMultiblockTileEntityBase)) {
			((RectangularMultiblockTileEntityBase)entityBelow).isGoodForBottom();
			 return;
		}
		throw new MultiblockValidationException(String.format("Electrode at %d, %d, %d must be part of a vertical column that reaches the entire height of the battery, with a power tap on top.", new Object[] { Integer.valueOf(this.xCoord), Integer.valueOf(this.yCoord), Integer.valueOf(this.zCoord) }));	
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

}
