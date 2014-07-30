package net.bymarcin.evenmoreutilities.mods.bigbattery.fluid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.mods.bigbattery.BigBatteryMod;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class AcidFluid extends BlockFluidClassic{
	public static AcidFluid instance =  new AcidFluid(BigBatteryMod.blockAcidFluidID, BigBatteryMod.acid);
    @SideOnly(Side.CLIENT)
    public Icon stillIcon;
    @SideOnly(Side.CLIENT)
    public Icon flowingIcon;
    
	public AcidFluid(int id, Fluid fluid) {
		super(id,fluid, MaterialLiquid.water);
		  this.setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		  this.setUnlocalizedName("sulfurousacid");
	}

    @Override
    public Icon getIcon(int side, int meta) {
            return (side == 0 || side == 1)? stillIcon : flowingIcon;
    }
    
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(net.minecraft.client.renderer.texture.IconRegister register) {
        stillIcon = register.registerIcon(StaticValues.modId+":fluidAcidStill");
        flowingIcon = register.registerIcon(StaticValues.modId+":fluidAcidFlowing");
	
    };
   
    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
            if (world.getBlockMaterial(x,  y,  z).isLiquid()) return false;
            return super.canDisplace(world, x, y, z);
    }
   
    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) {
            if (world.getBlockMaterial(x,  y,  z).isLiquid()) return false;
            return super.displaceIfPossible(world, x, y, z);
    }
}
