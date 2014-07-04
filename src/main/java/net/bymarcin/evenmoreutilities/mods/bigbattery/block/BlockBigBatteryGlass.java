package net.bymarcin.evenmoreutilities.mods.bigbattery.block;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityGlass;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import erogenousbeef.core.multiblock.BlockMultiblockBase;

public class BlockBigBatteryGlass extends BlockMultiblockBase{
	public static Icon icon ;
	public BlockBigBatteryGlass(int id) {
		super(id, Material.iron);
		this.setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		this.setHardness(3.0F);
		this.setUnlocalizedName("emu.bigBatteryGlass");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityGlass();
	}
	@Override
	public Icon getIcon(int par1, int par2) {
		return icon;
	}
	@Override
	public void registerIcons(IconRegister iconRegister) {
		icon = iconRegister.registerIcon(StaticValues.modId+":bb_glass");
	}
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
