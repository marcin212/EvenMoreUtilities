package net.bymarcin.evenmoreutilities.mods.superconductor.gui;

import net.bymarcin.evenmoreutilities.mods.superconductor.tileentity.TileEntityControler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import cpw.mods.fml.common.network.Player;

public class ContainerControler extends Container{
	TileEntityControler tile;
	
	public ContainerControler(EntityPlayer entityplayer,TileEntityControler controler) {
		tile = controler;
		tile.addPlayerToUpdate((Player) entityplayer);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		super.onContainerClosed(par1EntityPlayer);
		if(tile != null){
			tile.removePlayerFromUpdate((Player) par1EntityPlayer);
		}
	}
}
