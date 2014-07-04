package net.bymarcin.evenmoreutilities.mods.bigbattery.gui;

import net.bymarcin.evenmoreutilities.mods.bigbattery.BigBattery;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityControler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class BigBatteryContainer extends Container{
		TileEntityControler part;

		public BigBatteryContainer(TileEntityControler batteryControler, EntityPlayer player) {
			part = batteryControler;
			if(((BigBattery)part.getMultiblockController())!=null){
				((BigBattery)part.getMultiblockController()).beginUpdatingPlayer(player);
			}
		}

		@Override
		public boolean canInteractWith(EntityPlayer entityplayer) {
			return true;
		}

		@Override
		public void putStackInSlot(int slot, ItemStack stack) {
			return;
		}

		@Override
		    public void onContainerClosed(EntityPlayer player) {
				super.onContainerClosed(player);
				if(part != null && part.getMultiblockController() != null)
					((BigBattery)part.getMultiblockController()).stopUpdatingPlayer(player);
		}
}

