package net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity;

import net.bymarcin.evenmoreutilities.mods.bigbattery.BigBattery;
import net.bymarcin.evenmoreutilities.mods.bigbattery.block.BlockBigBatteryWall;
import cpw.mods.fml.common.FMLLog;
import erogenousbeef.core.common.CoordTriplet;
import erogenousbeef.core.multiblock.MultiblockControllerBase;
import erogenousbeef.core.multiblock.MultiblockValidationException;
import erogenousbeef.core.multiblock.rectangular.RectangularMultiblockTileEntityBase;

public class TileEntityWall extends RectangularMultiblockTileEntityBase {

	@Override
	public void onMachineBroken() {
		super.onMachineBroken();
		this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, BlockBigBatteryWall.CASING_METADATA_BASE, 2);
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
		throw new MultiblockValidationException(
				String.format(
						"%d, %d, %d - Wall may not be placed in the battery's interior",
						new Object[] { Integer.valueOf(this.xCoord),
								Integer.valueOf(this.yCoord),
								Integer.valueOf(this.zCoord) }));
	}

	@Override
	public void onMachineAssembled(MultiblockControllerBase controller) {
		super.onMachineAssembled(controller);

		if (controller == null) {
			throw new IllegalArgumentException(
					"Being assembled into a null controller. This should never happen. Please report this stacktrace.");
		}

		if (this.getMultiblockController() == null) {
			FMLLog.warning(
					"Battery part at (%d, %d, %d) is being assembled without being attached to a battery. Attempting to auto-heal. Fully destroying and re-building this reactor is recommended if errors persist.",
					xCoord, yCoord, zCoord);
			this.onAttached(controller);
		}

			this.setCasingMetadataBasedOnWorldPosition();
	}

	private void setCasingMetadataBasedOnWorldPosition() {
		MultiblockControllerBase controller = this.getMultiblockController();
		assert (controller != null);
		CoordTriplet minCoord = controller.getMinimumCoord();
		CoordTriplet maxCoord = controller.getMaximumCoord();

		int extremes = 0;
		@SuppressWarnings("unused")
		boolean xExtreme, yExtreme, zExtreme;
		xExtreme = yExtreme = zExtreme = false;

		if (xCoord == minCoord.x) {
			extremes++;
			xExtreme = true;
		}
		if (yCoord == minCoord.y) {
			extremes++;
			yExtreme = true;
		}
		if (zCoord == minCoord.z) {
			extremes++;
			zExtreme = true;
		}

		if (xCoord == maxCoord.x) {
			extremes++;
			xExtreme = true;
		}
		if (yCoord == maxCoord.y) {
			extremes++;
			yExtreme = true;
		}
		if (zCoord == maxCoord.z) {
			extremes++;
			zExtreme = true;
		}

		if (extremes == 3) {
			// Corner
			this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord,
					this.zCoord, BlockBigBatteryWall.CASING_CORNER, 2);
		} else if (extremes == 2) {
			if (!xExtreme) {
				// Y/Z - must be east/west
				this.worldObj.setBlockMetadataWithNotify(this.xCoord,
						this.yCoord, this.zCoord,
						BlockBigBatteryWall.CASING_EASTWEST, 2);
			} else if (!zExtreme) {
				// X/Y - must be north-south
				this.worldObj.setBlockMetadataWithNotify(this.xCoord,
						this.yCoord, this.zCoord,
						BlockBigBatteryWall.CASING_NORTHSOUTH, 2);
			} else {
				// Not a y-extreme, must be vertical
				this.worldObj.setBlockMetadataWithNotify(this.xCoord,
						this.yCoord, this.zCoord,
						BlockBigBatteryWall.CASING_VERTICAL, 2);
			}
		} else if (extremes == 1) {
			this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord,
					this.zCoord, BlockBigBatteryWall.CASING_CENTER, 2);
		} else {
			// This shouldn't happen.
			this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord,
					this.zCoord, BlockBigBatteryWall.CASING_METADATA_BASE, 2);
		}
	}

}
