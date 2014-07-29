package net.bymarcin.evenmoreutilities.mods.bigbattery.fluid;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.mods.bigbattery.BigBatteryMod;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class FluidBucket extends ItemBucket{
	public static FluidBucket instance = new FluidBucket(BigBatteryMod.itemAcidBucketID,BigBatteryMod.blockAcidFluidID) ;
	public FluidBucket(int itemId, int fluidId) {
		super(itemId, fluidId);
		setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		setUnlocalizedName("emu.acidBucket");
	}
	
	@Override
	public void registerIcons(IconRegister icon) {
		itemIcon = icon.registerIcon(StaticValues.modId + ":" + "bucket_acid");
		
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
			return itemIcon;
	}

}
