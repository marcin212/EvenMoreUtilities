package net.bymarcin.evenmoreutilities.mods.energysiphon;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockEnergySiphon extends BlockContainer{
	public static BlockEnergySiphon instance = new BlockEnergySiphon(EnergySiphonMod.EnergySiphonID);
	private static Icon siphonIcon;
	protected BlockEnergySiphon(int par1) {
		super(par1,Material.iron);
		this.setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		this.setHardness(3.0F);
		this.setUnlocalizedName("emu.energySiphon");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySiphon();
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		siphonIcon = par1IconRegister.registerIcon(StaticValues.modId + ":energy_siphon");
		super.registerIcons(par1IconRegister);
	}
	
	@Override
	public Icon getIcon(int par1, int par2) {
		return siphonIcon;
	}
}
