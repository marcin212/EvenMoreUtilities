package net.bymarcin.evenmoreutilities.mods.bigbattery.block;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityWall;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import erogenousbeef.core.multiblock.BlockMultiblockBase;

public class BlockBigBatteryWall extends BlockMultiblockBase{

	public BlockBigBatteryWall(int id) {
		super(id, Material.iron);
		this.setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		this.setHardness(3.0F);
		this.setUnlocalizedName("emu.bigBatteryWall");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityWall();
	}

}
