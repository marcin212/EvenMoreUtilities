package net.bymarcin.evenmoreutilities.mods.superconductor.block;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.mods.superconductor.SuperConductorMod;
import net.bymarcin.evenmoreutilities.mods.superconductor.render.Glowing;
import net.bymarcin.evenmoreutilities.mods.superconductor.tileentity.TileEntityControler;
import net.bymarcin.evenmoreutilities.utils.Sides;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cofh.api.energy.IEnergyHandler;
import erogenousbeef.core.multiblock.BlockMultiblockBase;

public class BlockControler extends BlockMultiblockBase implements Glowing{
	public static BlockControler instance = new BlockControler(SuperConductorMod.blockControlerID);
	public static Icon icons[][] = new Icon[2][16];
	public static Icon glowIcons[][] = new Icon[2][16];
	public static Icon transparentIcon;
	protected BlockControler(int id) {
		super(id, Material.iron);
		setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		setUnlocalizedName(StaticValues.modId+"."+getClass().getSimpleName());
		setHardness(3.0F);
	}

	@Override
	public void registerIcons(IconRegister iconRegister) {
		for(int i=0; i<16; i++){
			icons[0][i] = iconRegister.registerIcon(StaticValues.modId + ":sc_conductor_output_"+i);
			icons[1][i] = iconRegister.registerIcon(StaticValues.modId + ":sc_conductor_input_"+i);
			glowIcons[0][i] = iconRegister.registerIcon(StaticValues.modId + ":sc_conductor_output_glow_"+i);
			glowIcons[1][i] = iconRegister.registerIcon(StaticValues.modId + ":sc_conductor_input_glow_"+i);
		}
			transparentIcon = iconRegister.registerIcon(StaticValues.modId + ":bb_transparent");
	}
	
	@Override
	public Icon getIcon(int side, int metadata) {
		return icons[metadata&1][0];
	}
	
	public int getBlockType(IBlockAccess blockAccess, int x, int y, int z){
		return blockAccess.getBlockMetadata(x, y, z)&1;
	}
	
	@Override
	public boolean onBlockActivated(World world, int par2, int par3,
			int par4, EntityPlayer player, int par6, float par7,
			float par8, float par9) {
		if(player.getCurrentEquippedItem()==null && player.isSneaking()){
			world.setBlockMetadataWithNotify(par2, par3, par4, world.getBlockMetadata(par2, par3, par4)^1, 2);
			return true;
		}
		
		if(!player.isSneaking() && world.getBlockTileEntity(par2, par3, par4) instanceof TileEntityControler){
			if(((TileEntityControler)world.getBlockTileEntity(par2, par3, par4)).getMultiblockController().isAssembled()){ 
				player.openGui(EvenMoreUtilities.instance, 4, world, par2, par3, par4);
				return true;
			}
		}
		
		return false;
	}

	
	@Override
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		ForgeDirection[] dirsToCheck = Sides.neighborsBySide[side];
		ForgeDirection dir;
		int myBlockId = blockAccess.getBlockId(x, y, z);
		World world = blockAccess.getBlockTileEntity(x, y, z)!=null?blockAccess.getBlockTileEntity(x, y, z).worldObj:null;

		// First check if we have a block in front of us of the same type - if
		// so, just be completely transparent on this side
		ForgeDirection out = ForgeDirection.getOrientation(side);
		if (blockAccess.getBlockId(x + out.offsetX, y + out.offsetY, z + out.offsetZ) == BlockWire.instance.blockID || blockAccess.getBlockId(x + out.offsetX, y + out.offsetY, z + out.offsetZ) == myBlockId) {
			return transparentIcon;
		}
		if(isEnergyHandler(world, x + out.offsetX, y + out.offsetY, z + out.offsetZ))
			return icons[getBlockType(blockAccess,x, y, z)][15];

		// Calculate icon index based on whether the blocks around this block
		// match it
		// Icons use a naming pattern so that the bits correspond to:
		// 1 = Connected on top, 2 = connected on bottom, 4 = connected on left,
		// 8 = connected on right
		int iconIdx = 0;
		for (int i = 0; i < dirsToCheck.length; i++) {
			dir = dirsToCheck[i];
			// Same blockID and metadata on this side?
			if (blockAccess.getBlockId(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == BlockWire.instance.blockID || blockAccess.getBlockId(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == myBlockId || isEnergyHandler(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) {
				// Connected!
				iconIdx |= 1 << i;
			}
		}

		return icons[getBlockType(blockAccess,x, y, z)][iconIdx];
	}
	
	public boolean isEnergyHandler(World world, int x, int y, int z){
		if(world!=null && world.getBlockTileEntity(x, y, z) instanceof IEnergyHandler){
			return true;
		}
		return false;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityControler();
	}

	@Override
	public Icon getGlowIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		ForgeDirection[] dirsToCheck = Sides.neighborsBySide[side];
		ForgeDirection dir;
		int myBlockId = blockAccess.getBlockId(x, y, z);
		World world = blockAccess.getBlockTileEntity(x, y, z)!=null?blockAccess.getBlockTileEntity(x, y, z).worldObj:null;

		// First check if we have a block in front of us of the same type - if
		// so, just be completely transparent on this side
		ForgeDirection out = ForgeDirection.getOrientation(side);
		if (blockAccess.getBlockId(x + out.offsetX, y + out.offsetY, z + out.offsetZ) == BlockWire.instance.blockID || blockAccess.getBlockId(x + out.offsetX, y + out.offsetY, z + out.offsetZ) == myBlockId) {
			return null;
		}
		if(isEnergyHandler(world, x + out.offsetX, y + out.offsetY, z + out.offsetZ))
			return glowIcons[getBlockType(blockAccess,x, y, z)][15];

		// Calculate icon index based on whether the blocks around this block
		// match it
		// Icons use a naming pattern so that the bits correspond to:
		// 1 = Connected on top, 2 = connected on bottom, 4 = connected on left,
		// 8 = connected on right
		int iconIdx = 0;
		for (int i = 0; i < dirsToCheck.length; i++) {
			dir = dirsToCheck[i];
			// Same blockID and metadata on this side?
			if (blockAccess.getBlockId(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == BlockWire.instance.blockID || blockAccess.getBlockId(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == myBlockId || isEnergyHandler(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) {
				// Connected!
				iconIdx |= 1 << i;
			}
		}

		return glowIcons[getBlockType(blockAccess,x, y, z)][iconIdx];
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
