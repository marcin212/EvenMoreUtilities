package net.bymarcin.evenmoreutilities.mods.nfc;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockNFCProgrammer extends BlockContainer{
	public static BlockNFCProgrammer instance = new BlockNFCProgrammer(NFCMod.NFCProgrammerID);
	Icon iconTop;
	Icon iconSidesOn;
	Icon iconSidesOff;
	
	protected BlockNFCProgrammer(int par1) {
		super(par1, Material.iron);
		this.setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		this.setHardness(3.0F);
		this.setUnlocalizedName("emu.NFCProgrammer");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityNFCProgrammer();
	}
	
	@Override
	public boolean onBlockActivated(World world, int par2, int par3,
			int par4, EntityPlayer player, int par6, float par7,float par8, float par9) {
		TileEntity tile = world.getBlockTileEntity(par2, par3, par4);
		if(tile instanceof TileEntityNFCProgrammer && ((TileEntityNFCProgrammer)tile).NFCData!=null){
			TileEntityNFCProgrammer tilenfc = (TileEntityNFCProgrammer)tile;
			if(player.getHeldItem()!=null){
				if(player.getHeldItem().getItem() instanceof ItemPrivateCardNFC){
					if(ItemPrivateCardNFC.getOwner(player.getHeldItem())!=null && player.getDisplayName().equals(ItemPrivateCardNFC.getOwner(player.getHeldItem()))){
						ItemPrivateCardNFC.setNFCData(tilenfc.writeCardNFC(),player.getHeldItem());
						player.addChatMessage("NFC data written!");
						world.setBlockMetadataWithNotify(par2, par3, par4, 0, 2);
						return true;
					}else{
						player.addChatMessage("You are not the owner or the owner is not set!");
						return false;
					}
				}else if(player.getHeldItem().getItem() instanceof ItemCardNFC){
					ItemCardNFC.setNFCData(tilenfc.writeCardNFC(),player.getHeldItem());
					player.addChatMessage("NFC data written!");
					world.setBlockMetadataWithNotify(par2, par3, par4, 0, 2);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public Icon getIcon(int par1, int par2) {
		switch(par1){
		case 0:
		case 1:
			return iconTop;
		}
		if(par2==0){
			return iconSidesOff;
		}else{
			return iconSidesOn;
		}
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister) {
		iconTop = iconRegister.registerIcon(StaticValues.modId + ":" + "nfc_block_top");
		iconSidesOn = iconRegister.registerIcon(StaticValues.modId + ":" + "nfc_programmer_on");
		iconSidesOff = iconRegister.registerIcon(StaticValues.modId + ":" + "nfc_programmer_off");
	}
}
