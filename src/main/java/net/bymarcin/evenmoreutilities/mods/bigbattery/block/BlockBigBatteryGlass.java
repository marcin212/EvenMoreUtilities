package net.bymarcin.evenmoreutilities.mods.bigbattery.block;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.mods.bigbattery.BigBatteryMod;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityGlass;
import net.bymarcin.evenmoreutilities.utils.Sides;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import erogenousbeef.core.multiblock.BlockMultiblockBase;

public class BlockBigBatteryGlass extends BlockMultiblockBase {
	public static BlockBigBatteryGlass instance = new BlockBigBatteryGlass(BigBatteryMod.blockBigBatteryGlassID);
	public static Icon icons[] = new Icon[16];
	public static Icon transparentIcon;
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
	public void registerIcons(IconRegister iconRegister) {
		for(int i=0; i<16; i++){
			icons[i] = iconRegister.registerIcon(StaticValues.modId + ":bb_glass_"+i);
		}
		
			transparentIcon = iconRegister.registerIcon(StaticValues.modId + ":bb_transparent");
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		ForgeDirection[] dirsToCheck = Sides.neighborsBySide[side];
		ForgeDirection dir;
		int myBlockId = blockAccess.getBlockId(x, y, z);

		// First check if we have a block in front of us of the same type - if
		// so, just be completely transparent on this side
		ForgeDirection out = ForgeDirection.getOrientation(side);
		if (blockAccess.getBlockId(x + out.offsetX, y + out.offsetY, z + out.offsetZ) == myBlockId) {
			return transparentIcon;
		}

		// Calculate icon index based on whether the blocks around this block
		// match it
		// Icons use a naming pattern so that the bits correspond to:
		// 1 = Connected on top, 2 = connected on bottom, 4 = connected on left,
		// 8 = connected on right
		int iconIdx = 0;
		for (int i = 0; i < dirsToCheck.length; i++) {
			dir = dirsToCheck[i];
			// Same blockID and metadata on this side?
			if (blockAccess.getBlockId(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == myBlockId) {
				// Connected!
				iconIdx |= 1 << i;
			}
		}

		return icons[iconIdx];
	}
	
	@Override
	public Icon getIcon(int side, int metadata) {
		return icons[0];
	}
}
