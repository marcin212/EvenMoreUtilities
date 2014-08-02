package net.bymarcin.evenmoreutilities.mods.superconductor.block;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.mods.superconductor.SuperConductor;
import net.bymarcin.evenmoreutilities.mods.superconductor.SuperConductorMod;
import net.bymarcin.evenmoreutilities.mods.superconductor.render.Glowing;
import net.bymarcin.evenmoreutilities.mods.superconductor.tileentity.TileEntityWire;
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
import erogenousbeef.core.multiblock.MultiblockTileEntityBase;

public class BlockWire extends BlockMultiblockBase implements Glowing {
	public static BlockWire instance = new BlockWire(SuperConductorMod.blockWireID);
	public static Icon[] icons = new Icon[16];
	public static Icon[] glowIcons = new Icon[16];
	public static Icon transparentIcon;

	protected BlockWire(int id) {
		super(id, Material.iron);
		setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		setUnlocalizedName(StaticValues.modId + "." + getClass().getSimpleName());
		setHardness(3.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityWire();
	}

	@Override
	public Icon getIcon(int side, int metadata) {
		return icons[0];
	}

	@Override
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {

		ForgeDirection[] dirsToCheck = Sides.neighborsBySide[side];
		ForgeDirection dir;
		int myBlockId = blockAccess.getBlockId(x, y, z);

		// First check if we have a block in front of us of the same type - if
		// so, just be completely transparent on this side
		ForgeDirection out = ForgeDirection.getOrientation(side);
		if (blockAccess.getBlockId(x + out.offsetX, y + out.offsetY, z + out.offsetZ) == myBlockId || blockAccess.getBlockId(x + out.offsetX, y + out.offsetY, z + out.offsetZ) == BlockControler.instance.blockID) {
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
			if (blockAccess.getBlockId(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == myBlockId || blockAccess.getBlockId(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == BlockControler.instance.blockID) {
				// Connected!
				iconIdx |= 1 << i;
			}
		}

		return icons[iconIdx];
	}

	@Override
	public void registerIcons(IconRegister iconRegister) {
		for (int i = 0; i < 16; i++) {
			icons[i] = iconRegister.registerIcon(StaticValues.modId + ":sc_conductor_body_" + i);
			glowIcons[i] = iconRegister.registerIcon(StaticValues.modId + ":sc_conductor_body_glow_" + i);
		}

		transparentIcon = iconRegister.registerIcon(StaticValues.modId + ":bb_transparent");
	}

	@Override
	public Icon getGlowIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		ForgeDirection[] dirsToCheck = Sides.neighborsBySide[side];
		ForgeDirection dir;
		int myBlockId = blockAccess.getBlockId(x, y, z);

		// First check if we have a block in front of us of the same type -
		// if
		// so, just be completely transparent on this side
		ForgeDirection out = ForgeDirection.getOrientation(side);
		if (blockAccess.getBlockId(x + out.offsetX, y + out.offsetY, z + out.offsetZ) == myBlockId || blockAccess.getBlockId(x + out.offsetX, y + out.offsetY, z + out.offsetZ) == BlockControler.instance.blockID) {
			return null;
		}

		// Calculate icon index based on whether the blocks around this
		// block
		// match it
		// Icons use a naming pattern so that the bits correspond to:
		// 1 = Connected on top, 2 = connected on bottom, 4 = connected on
		// left,
		// 8 = connected on right
		int iconIdx = 0;
		for (int i = 0; i < dirsToCheck.length; i++) {
			dir = dirsToCheck[i];
			// Same blockID and metadata on this side?
			if (blockAccess.getBlockId(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == myBlockId || blockAccess.getBlockId(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == BlockControler.instance.blockID) {
				// Connected!
				iconIdx |= 1 << i;
			}
		}

		return glowIcons[iconIdx];

	}

	@Override
	public int getRenderType() {
		return SuperConductorMod.glowRenderID;
	}

	@Override
	public boolean canRenderInPass(int pass) {
		SuperConductorMod.pass = pass;
		return true;
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return 4;
	}

}
