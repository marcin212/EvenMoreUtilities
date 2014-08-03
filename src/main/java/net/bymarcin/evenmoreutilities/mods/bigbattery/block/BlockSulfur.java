package net.bymarcin.evenmoreutilities.mods.bigbattery.block;

import java.util.Random;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.mods.bigbattery.BigBatteryMod;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BlockSulfur extends Block {
	protected Icon blockIconTop;
	public static BlockSulfur instance = new BlockSulfur(BigBatteryMod.sulfurblockId);

	public BlockSulfur(int id) {
		super(id, Material.rock);
		setHardness(3.0F);
		setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		setTickRandomly(true);
		setUnlocalizedName("emu.sulfurBlock");

	}

	@Override
	public boolean isFireSource(World world, int x, int y, int z, int metadata,
			ForgeDirection side) {
		return side == ForgeDirection.UP;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate(world));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		if (world.getBlockId(x, y + 1, z) == Block.fire.blockID) {
			for (int i = x - 1; i <= x + 1; i++) {
				for (int j = z - 1; j <= z + 1; j++) {
					Fluid f = FluidRegistry.lookupFluidForBlock(Block.blocksList[world.getBlockId(i, y, j)]);
					int fid = f != null ? f.getID() : -1;
					if (FluidRegistry.getFluid("water").getID() == fid && world.getBlockMetadata(i, y, j) == 0) {
						world.setBlock(i, y, j, BigBatteryMod.blockAcidFluidID, 0, 2);
					}
				}
			}
			world.setBlockToAir(x, y, z);
		} else {
			world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate(world));
		}
	}

	@Override
	public void registerIcons(IconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon(StaticValues.modId + ":sulfur_block");
		blockIconTop = iconRegister.registerIcon(StaticValues.modId + ":sulfur_block_top");
	}

	@Override
	public Icon getIcon(int side, int meta) {
		return side == 1 ? blockIconTop : blockIcon;
	}

	@Override
	public int tickRate(World par1World) {
		return 20 * 15;
	}
}
