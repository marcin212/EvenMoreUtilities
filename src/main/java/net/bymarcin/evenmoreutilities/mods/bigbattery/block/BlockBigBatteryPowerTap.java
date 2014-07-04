package net.bymarcin.evenmoreutilities.mods.bigbattery.block;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityPowerTap;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import erogenousbeef.core.multiblock.BlockMultiblockBase;

public class BlockBigBatteryPowerTap extends BlockMultiblockBase{
	public static Icon icon ;
	public BlockBigBatteryPowerTap(int id) {
		super(id, Material.iron);
		this.setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		this.setHardness(3.0F);
		this.setUnlocalizedName("emu.bigBatteryPowerTap");
	}

	@Override
	public boolean onBlockActivated(World world, int par2, int par3,
			int par4, EntityPlayer player, int par6, float par7,
			float par8, float par9) {
		if(player.getCurrentEquippedItem()==null){
			if(world.getBlockMetadata(par2, par3, par4)==0){
				world.setBlockMetadataWithNotify(par2, par3, par4, 1, 2);
			}else{
				world.setBlockMetadataWithNotify(par2, par3, par4, 0, 2);
			}
			return true;
		}
		return false;
	}
	
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityPowerTap();
	}
	@Override
	public Icon getIcon(int par1, int par2) {
		if(par2==0)
			return icon;
		else
			return null;
	}
	@Override
	public void registerIcons(IconRegister iconRegister) {
		icon = iconRegister.registerIcon(StaticValues.modId+":powertap");
	}
}
