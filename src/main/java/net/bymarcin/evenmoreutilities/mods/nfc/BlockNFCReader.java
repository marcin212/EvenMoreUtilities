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
import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")
public class BlockNFCReader extends BlockContainer{
	public static BlockNFCReader instance = new BlockNFCReader(NFCMod.NFCReaderID);
	Icon iconTop;
	Icon iconSides;
	
	
	protected BlockNFCReader(int par1) {
		super(par1, Material.iron);
		this.setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		this.setHardness(3.0F);
		this.setUnlocalizedName("emu.NFCReader");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityNFCReader();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, 
									EntityPlayer player, int l, float px, float py, float pz) {
		
		TileEntity tile =  world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileEntityNFCReader){
			if(player.getHeldItem()!=null){
				if(player.getHeldItem().getItem() instanceof ItemPrivateCardNFC){
					if(player.getDisplayName().equals(ItemPrivateCardNFC.getOwner(player.getHeldItem()))){
						((TileEntityNFCReader)tile).sendEvent(player.getDisplayName(),ItemPrivateCardNFC.getNFCData(player.getHeldItem()));
						return true;
					}
					return false;
				}else if(player.getHeldItem().getItem() instanceof ItemCardNFC){
					((TileEntityNFCReader)tile).sendEvent(player.getDisplayName(),ItemCardNFC.getNFCData(player.getHeldItem()));
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
		return iconSides;
	}
	
	@Override
	public void registerIcons(IconRegister iconRegister) {
		iconTop = iconRegister.registerIcon(StaticValues.modId + ":" + "nfc_block_top");
		iconSides = iconRegister.registerIcon(StaticValues.modId + ":" + "nfc_reader_sides");
	}
}
