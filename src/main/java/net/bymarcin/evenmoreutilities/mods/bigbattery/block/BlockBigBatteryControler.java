package net.bymarcin.evenmoreutilities.mods.bigbattery.block;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityControler;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import erogenousbeef.core.multiblock.BlockMultiblockBase;

public class BlockBigBatteryControler extends BlockMultiblockBase{
	public static Icon icon ;
	public BlockBigBatteryControler(int id) {
		super(id, Material.iron);
		this.setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		this.setHardness(3.0F);
		this.setUnlocalizedName("emu.bigBatteryControler");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityControler();
	}

	@Override
	public void registerIcons(IconRegister iconRegister) {
		//iconRegister.registerIcon(StaticValues.modId+":");
	}
}
