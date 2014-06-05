package net.bymarcin.evenmoreutilities.mods.redstonemitter;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneEmitter extends BlockContainer{
	static BlockRedstoneEmitter instance =new BlockRedstoneEmitter(RedstoneEmitterMod.RedstoneEmitterID);
	Icon emitterIcon;
	public BlockRedstoneEmitter(int id) {
		super(id, Material.rock);
		this.setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		this.setHardness(3.0F);
		this.setUnlocalizedName("emu.RedstoneEmitter");
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
        TileEntity tileEntity = par1World.getBlockTileEntity( par2, par3, par4);
        if (tileEntity == null || par5EntityPlayer.isSneaking()) {
                return false;
        }
        par5EntityPlayer.openGui(EvenMoreUtilities.instance, 0, par1World, par2, par3, par4);
        return true;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess par1iBlockAccess, int par2,
			int par3, int par4, int par5) {
		TileEntityRedstoneEmitter tile  = (TileEntityRedstoneEmitter)par1iBlockAccess.getBlockTileEntity(par2, par3, par4);
		if(tile!=null)
			return tile.getRedstoneSignal();
		else
			return 0;
	}
	@Override
	public boolean canProvidePower() {
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityRedstoneEmitter();
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		emitterIcon = par1IconRegister.registerIcon(StaticValues.modId + ":redstone_block");
		super.registerIcons(par1IconRegister);
	}
	
	@Override
	public Icon getIcon(int par1, int par2) {
		return emitterIcon;
	}
}
