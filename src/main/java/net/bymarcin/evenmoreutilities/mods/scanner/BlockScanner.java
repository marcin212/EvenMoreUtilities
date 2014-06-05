package net.bymarcin.evenmoreutilities.mods.scanner;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockScanner extends BlockContainer{
	public static BlockScanner instance = new BlockScanner(ScannerMod.scannerID);
	Icon IconSides;
	Icon IconTop;
	protected BlockScanner(int id) {
		super(id, Material.iron);
		this.setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		this.setHardness(3.0F);
		this.setUnlocalizedName("emu.OpenScanner");

	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		IconSides = par1IconRegister.registerIcon(StaticValues.modId + ":scanner_side");
		IconTop = par1IconRegister.registerIcon(StaticValues.modId + ":scanner_top");
		super.registerIcons(par1IconRegister);
	}
	
	@Override
	public Icon getIcon(int par1, int par2) {
		switch(par1){
		case 0:
		case 1:
			return IconTop;
		}
		return IconSides;
		
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityBlockScanner();
	}

}
