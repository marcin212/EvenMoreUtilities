package net.bymarcin.evenmoreutilities.mods.sensor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.common.ForgeDirection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.TileEnergyHandler;
import cofh.util.BlockHelper;
import cofh.util.EnergyHelper;
import cofh.util.ServerHelper;
import cpw.mods.fml.common.network.PacketDispatcher;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;

public class TileEntitySensor extends TileEnergyHandler implements IPeripheral{
	private static final int maxFlow = 10000;
	CCMethods ccManager = new CCMethods(this);
	int config = ((1)|(0<<3));
	
	double avg = 0;
	boolean isBlockedFlow = false;
	
	boolean isLimited =false;
	int limit = 0;
	
	int in=ForgeDirection.UP.ordinal();
	int out=ForgeDirection.DOWN.ordinal();
	
	int outLimit = maxFlow;
	
	String name;
	
	public TileEntitySensor() {
		storage.setCapacity(maxFlow);
		storage.setMaxTransfer(maxFlow);
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	public Icon getBlockTexture(int side){
		if(side == in)
			return BlockSensor.instance.sensorInIcon;
		else if(side == out){
			return BlockSensor.instance.sensorOutIcon;
		}
		return null;
	}
	
	
	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		if(from.ordinal()==out && !isBlockedFlow)
			return super.extractEnergy(from, maxExtract, simulate);
		else
			return 0;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		if(from.ordinal()==in)
			return super.receiveEnergy(from, maxReceive, simulate);
		else
			return 0;
	}
	
	@Override
	public void updateEntity() {
		if(ServerHelper.isClientWorld(worldObj)) return;
		  TileEntity tile = BlockHelper.getAdjacentTileEntity(this, out);
		  int energyGet=0;
		  if(!isBlockedFlow){
			  if (EnergyHelper.isEnergyHandlerFromSide(tile,ForgeDirection.VALID_DIRECTIONS[(out ^ 0x1)])){
				  if(isLimited){
					  energyGet = ((IEnergyHandler)tile).receiveEnergy(ForgeDirection.VALID_DIRECTIONS[(out ^ 0x1)], Math.min(Math.min(outLimit, this.storage.getEnergyStored()),limit), false);
					  limit-=energyGet;
				  }else{
					  energyGet = ((IEnergyHandler)tile).receiveEnergy(ForgeDirection.VALID_DIRECTIONS[(out ^ 0x1)], Math.min(outLimit, this.storage.getEnergyStored()), false); 
				 }  
				  this.storage.modifyEnergyStored(-energyGet);
			  }
	      }
		avg= (avg * 9.0 + (double)energyGet)/10.0;	
	}

//Computer craft integration

	@Override
	public String getType() {
		return "Flow_Sensor";
	}

	@Override
	public String[] getMethodNames() {
		return new String[]{
				"canFlow",
				"allowOutput",
				"isLimited",
				"setLimited",
				"getLimit",
				"setLimit",
				"getIn",
				"setIn",
				"getOut",
				"setOut",
				"getAvg",
				"getRateLimit",
				"setRateLimit",
				"listMethods",
				"getName",
				"setName"};
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context,
			int method, Object[] arguments) throws Exception {
		switch(method){
		case 0: return ccManager.canFlow();
		case 1: return ccManager.allowOutput(arguments);
		case 2: return ccManager.isLimited();
		case 3: return ccManager.setLimited(arguments);
		case 4: return ccManager.getLimit();
		case 5: return ccManager.setLimit(arguments);
		case 6: return ccManager.getIn();
		case 7: return ccManager.setIn(arguments);
		case 8: return ccManager.getOut();
		case 9: return ccManager.setOut(arguments);
		case 10: return ccManager.getAvg();
		case 11: return ccManager.getRateLimit();
		case 12: return ccManager.setRateLimit(arguments);
		case 13: return ccManager.listMethods();
		case 14: return ccManager.getName(arguments);
		case 15: return ccManager.setName(arguments);
		}
		return null;
	}

	@Override
	public void attach(IComputerAccess computer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void detach(IComputerAccess computer) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean equals(IPeripheral other) {
		return other==this;
	}

	public boolean canFlow() {
		return isBlockedFlow;
	}

	public void allowOutput(boolean isBlockedFlow) {
		this.isBlockedFlow = isBlockedFlow;
	}

	public boolean isLimited() {
		return isLimited;
	}

	public void setLimited(boolean isLimited) {
		this.isLimited = isLimited;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getIn() {		
		return in;
	}

	public boolean setIn(int in) {
		if(in!=this.out){
			this.in = in;
			config=((in)|(56&config));	
			PacketDispatcher.sendPacketToAllPlayers(getDescriptionPacket());
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			if((meta&8)==8){
				meta  =(meta&7);
			}else{
				meta = (meta|8);
			}
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 2);
			return true;
		}else{
			return false;
		}
	}

	public int getOut() {
		return out;
	}

	public boolean setOut(int out) {
		if(out!=this.in){
			this.out = out;
			config=((out<<3)|(7&config));
			PacketDispatcher.sendPacketToAllPlayers(getDescriptionPacket());
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			if((meta&8)==8){
				meta  =(meta&7);
			}else{
				meta = (meta|8);
			}
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 2);
			return true;
		}else{
			return false;
		}
	}

	public double getAvg() {
		return avg;
	}

	public int getRateLimit() {
		return outLimit;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public boolean setRateLimit(int outLimit) {
		if (outLimit<=maxFlow){
			this.outLimit = outLimit;
			return true;
		}else{
			return false;
		}		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("config", config);
		nbt.setDouble("avg", avg);
		nbt.setBoolean("isBlockedFlow", isBlockedFlow);
		nbt.setBoolean("isLimited", isLimited);
		nbt.setInteger("limit", limit);
		nbt.setInteger("in", in);
		nbt.setInteger("out", out);
		nbt.setInteger("outLimit", outLimit);
		nbt.setString("sensorName", name);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		config = nbt.getInteger("config");
		avg =nbt.getDouble("avg");
		isBlockedFlow = nbt.getBoolean("isBlockedFlow");
		isLimited = nbt.getBoolean("isLimited");
		limit = nbt.getInteger("limit");
		in = nbt.getInteger("in");
		out = nbt.getInteger("out");
		outLimit = nbt.getInteger("outLimit");
		name = nbt.getString("sensorName");
	}
    @Override
    public Packet getDescriptionPacket()
    {
     NBTTagCompound var1 = new NBTTagCompound();
     this.writeToNBT(var1);
     return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 2, var1);
    }
            
    @Override
    public void onDataPacket(INetworkManager netManager, Packet132TileEntityData packet)
    {
     readFromNBT(packet.data);
    }
}
