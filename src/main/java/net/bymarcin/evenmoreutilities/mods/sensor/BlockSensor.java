package net.bymarcin.evenmoreutilities.mods.sensor;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSensor extends BlockContainer {
	public static BlockSensor instance = new BlockSensor(SensorMod.SensorID);
	Icon sensorTopIcon;
	Icon sensorFrontIcon;
	Icon sensorSideIcon;
	Icon sensorBottomIcon;
	Icon sensorInIcon;
	Icon sensorOutIcon;

	protected BlockSensor(int id) {
		super(id, Material.iron);
		this.setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		this.setHardness(3.0F);
		this.setUnlocalizedName("emu.MultimeterSensor");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySensor();
	}
	
	@Override
	public Icon getBlockTexture(IBlockAccess par1iBlockAccess, int par2,
			int par3, int par4, int par5) {
		if(par1iBlockAccess.getBlockTileEntity(par2, par3, par4)==null) return null;
		int metadata = ((TileEntitySensor)par1iBlockAccess.getBlockTileEntity(par2, par3, par4)).config;
		if((metadata&7)==par5) { return this.sensorInIcon;}
	    if((metadata&56)>>3==par5){return this.sensorOutIcon;}
	    if (par5==0) return this.sensorTopIcon;
	    if (par5==1) return this.sensorBottomIcon;
	    metadata = par1iBlockAccess.getBlockMetadata(par2, par3, par4);
        if ((metadata&7) == 2 && par5 == 2) return this.sensorFrontIcon;
        if ((metadata&7) == 3 && par5 == 5) return this.sensorFrontIcon;
        if ((metadata&7) == 0 && par5 == 3) return this.sensorFrontIcon;
        if ((metadata&7) == 1 && par5 == 4) return this.sensorFrontIcon;
        return this.sensorSideIcon;
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		sensorTopIcon = par1IconRegister.registerIcon(StaticValues.modId + ":rf_meter_side");
		sensorFrontIcon = par1IconRegister.registerIcon(StaticValues.modId + ":rf_meter_front");
		sensorSideIcon = par1IconRegister.registerIcon(StaticValues.modId + ":rf_meter_side");
		sensorBottomIcon = par1IconRegister.registerIcon(StaticValues.modId + ":rf_meter_side");
		sensorInIcon = par1IconRegister.registerIcon(StaticValues.modId + ":rf_meter_input");
		sensorOutIcon = par1IconRegister.registerIcon(StaticValues.modId + ":rf_meter_output");
	}
	@Override
	 public void onBlockPlacedBy(World par1World, int x, int y, int z, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
     {
         int whichDirectionFacing = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
         par1World.setBlockMetadataWithNotify(x, y, z, whichDirectionFacing, 2);
     }

	@Override
	public Icon getIcon(int side, int metadata) {
        if (side == 1) return this.sensorTopIcon;
        else if (side == 0) return this.sensorBottomIcon;
        else if (metadata == 2 && side == 2) return this.sensorFrontIcon;
          else if (metadata == 3 && side == 5) return this.sensorFrontIcon;
          else if (metadata == 0 && side == 3) return this.sensorFrontIcon;
          else if (metadata == 1 && side == 4) return this.sensorFrontIcon;
          else return this.sensorSideIcon;
	}	
	
}
