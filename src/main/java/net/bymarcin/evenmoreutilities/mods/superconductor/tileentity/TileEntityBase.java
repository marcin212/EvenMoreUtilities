package net.bymarcin.evenmoreutilities.mods.superconductor.tileentity;

import net.bymarcin.evenmoreutilities.mods.superconductor.SuperConductor;
import erogenousbeef.core.multiblock.MultiblockControllerBase;
import erogenousbeef.core.multiblock.MultiblockTileEntityBase;

public abstract class TileEntityBase extends MultiblockTileEntityBase{

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new SuperConductor(worldObj);
	}

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return SuperConductor.class;
	}

}
