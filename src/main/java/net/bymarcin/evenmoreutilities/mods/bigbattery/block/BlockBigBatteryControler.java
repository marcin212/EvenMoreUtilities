package net.bymarcin.evenmoreutilities.mods.bigbattery.block;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityControler;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import erogenousbeef.core.multiblock.BlockMultiblockBase;

public class BlockBigBatteryControler extends BlockMultiblockBase{
	public static Icon iconSideOn;
	public static Icon iconSideOff;
	public static Icon icon;
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if(player.isSneaking()) {
			return false;
		}
		
		if(((TileEntityControler) world.getBlockTileEntity(x, y, z)).getMultiblockController().isAssembled()){
			player.openGui(EvenMoreUtilities.instance, 3, world, x, y, z);
			return true;
		}
		return false;
	}
	
	
	@Override
	public Icon getIcon(int par1, int par2) {
		if(par1 ==0 || par1 == 1) return icon;
		if(par2==0)
			return iconSideOff;
		else
			return iconSideOn;
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister) {
		icon = iconRegister.registerIcon(StaticValues.modId+":bb_part");
		iconSideOff = iconRegister.registerIcon(StaticValues.modId+":bb_controler_off");
		iconSideOn = iconRegister.registerIcon(StaticValues.modId+":bb_controler_on");
	}
}
