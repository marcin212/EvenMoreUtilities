package net.bymarcin.evenmoreutilities.mods.bigbattery.gui;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;
import net.bymarcin.evenmoreutilities.mods.bigbattery.tileentity.TileEntityPowerTap;
import net.bymarcin.evenmoreutilities.utils.AbstractPacket;
import net.minecraft.entity.player.EntityPlayer;

public class PowerTapUpdatePacket extends AbstractPacket{
	public static final int MINUS = 1;
	public static final int PLUS = 2;
	public static final int UPDATE = 3;
	
	int transfer = 0;
	int typ= 0;
	int x= 0;
	int y= 0;
	int z= 0;
	
	
	public PowerTapUpdatePacket(int x, int y, int z, int transfer, int typ) {
		this.transfer  = transfer; 
		this.typ = typ;
		this.x =x;
		this.y =y;
		this.z =z;
	}
	
	public PowerTapUpdatePacket() {
	}

	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(typ);
		out.writeInt(transfer);
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);	
	}

	@Override
	public void read(ByteArrayDataInput in) throws ProtocolException {
		typ = in.readInt();
		transfer = in.readInt();
		x = in.readInt();
		y = in.readInt();
		z = in.readInt();
	}

	@Override
	public void execute(EntityPlayer player, Side side)
			throws ProtocolException {
		TileEntityPowerTap tile = (TileEntityPowerTap) player.worldObj.getBlockTileEntity(x, y, z);
		if(tile!=null)
			tile.onPacket(transfer, typ);
	}
}
