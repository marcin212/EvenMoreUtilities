package net.bymarcin.evenmoreutilities.utils;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class EMUBlock extends Block{
	public static EMUBlock instance;
	protected EMUBlock(int id, Material material) {
		super(id, material);
		setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		setUnlocalizedName(StaticValues.modId+"."+getClass().getSimpleName());
	}
}