package net.bymarcin.evenmoreutilities.mods.nfc;

import java.util.List;

import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemPrivateCardNFC extends ItemCardNFC{
	public static ItemPrivateCardNFC instance =new ItemPrivateCardNFC(NFCMod.NFCPrivateCardID);
	Icon ItemIconOwner;
	Icon ItemIconNotOwner;
	public ItemPrivateCardNFC(int par1) {
		super(par1);
		setUnlocalizedName("emu.private.card.nfc");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack itemStack,EntityPlayer player,List par3List, boolean par4) {
		if(itemStack.getItem()instanceof ItemPrivateCardNFC && getOwner(itemStack)!=null)
			par3List.add(getOwner(itemStack));
	}

	private static void setOwner(String name, ItemStack item){
		if(item.stackTagCompound==null)
			item.setTagCompound(new NBTTagCompound());
		
		item.stackTagCompound.setString("ownerNFC", name);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World par2World,
			EntityPlayer player) {
		if(itemStack.getItem() instanceof ItemPrivateCardNFC && player.isSneaking() && getOwner(itemStack)==null){
			setOwner(player.getDisplayName(), itemStack);
		}
		return itemStack;
	}
	
	public static String getOwner(ItemStack item) {
		if(item.stackTagCompound!=null)
			return item.stackTagCompound.getString("ownerNFC");
		else
			return null;
	}
	
	@Override
	public void registerIcons(IconRegister icon) {
		ItemIconNotOwner = icon.registerIcon(StaticValues.modId + ":" + "nfc_item_not_owner");
		ItemIconOwner = icon.registerIcon(StaticValues.modId + ":" + "nfc_item_owner");
		super.registerIcons(icon);
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		if(getOwner(stack)==null)
			return ItemIconNotOwner;
		else
			return ItemIconOwner;
	}
	
	@Override
	public Icon getIconIndex(ItemStack stack) {
		return getIcon(stack,0);
	}
}
