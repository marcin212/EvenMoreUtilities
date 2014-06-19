package net.bymarcin.evenmoreutilities.mods.vanillautils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockCharcoal extends Block{
	public static BlockCharcoal instance = new BlockCharcoal(VanillaUtils.blockCharcoalID);
	
	public BlockCharcoal(int id) {
		super(id, Material.rock);
		setHardness(5.0F);
		setResistance(10.0F);
		setStepSound(soundStoneFootstep);
		setUnlocalizedName("emu.blockCharcoalCoal");
		setCreativeTab(EvenMoreUtilities.instance.tabCustom);	
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) {
		blockIcon = icon.registerIcon(StaticValues.modId + ":" + "charcoal_block");
	}
}
