package net.bymarcin.evenmoreutilities.mods.bigbattery.gui;

import net.bymarcin.evenmoreutilities.mods.bigbattery.BigBattery;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityControler;
import net.bymarcin.evenmoreutilities.utils.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class EnergyUpdatePacket extends AbstractPacket{
	int x;
	int y;
	int z;
	long capacity;
	long energy;
	int transfer;
	
	public EnergyUpdatePacket() {
	}
	
	public EnergyUpdatePacket(int x, int y, int z, long energy, long capacity, int transfer) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.capacity = capacity;
		this.energy = energy;
		this.transfer = transfer;
		
	}
	
	
	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
		out.writeInt(transfer);
		out.writeLong(capacity);
		out.writeLong(energy);
		
	}

	@Override
	public void read(ByteArrayDataInput in) throws ProtocolException {
		x = in.readInt();
		y = in.readInt();
		z = in.readInt();
		transfer =in.readInt();
		capacity = in.readLong();
		energy = in.readLong();
		
	}

	@Override
	public void execute(EntityPlayer player, Side side)
			throws ProtocolException {
		if(side.isServer()) return;
		TileEntity tile= player.worldObj.getBlockTileEntity(x, y, z);
		if(tile instanceof TileEntityControler){
			((BigBattery) ((TileEntityControler) tile).getMultiblockController()  ).onPacket(capacity,energy,transfer);
		}
	}

}
