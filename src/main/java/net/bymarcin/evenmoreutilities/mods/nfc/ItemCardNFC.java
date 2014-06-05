package net.bymarcin.evenmoreutilities.mods.nfc;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCardNFC extends Item{
	public static ItemCardNFC instance =new ItemCardNFC(NFCMod.NFCCardID);
	public ItemCardNFC(int id) {
		super(id);
		setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		setMaxStackSize(1);
		setUnlocalizedName("emu.card.nfc");
	}
	
	public static void setNFCData(String NFCData, ItemStack stack) {
		if(stack.stackTagCompound==null)
			stack.setTagCompound( new NBTTagCompound( ) );
		stack.stackTagCompound.setString("NFCData", NFCData);
	}
	
	public static String getNFCData(ItemStack stack) {
		if(stack.stackTagCompound!=null)
			return stack.stackTagCompound.getString("NFCData");
		else
			return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) {
		itemIcon = icon.registerIcon(StaticValues.modId + ":" + "nfc_item");
	}
	
}
