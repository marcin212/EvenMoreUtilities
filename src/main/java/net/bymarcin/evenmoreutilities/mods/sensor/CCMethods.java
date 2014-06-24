package net.bymarcin.evenmoreutilities.mods.sensor;

import net.bymarcin.evenmoreutilities.utils.Sides;
import net.minecraftforge.common.ForgeDirection;

public class CCMethods {
	TileEntitySensor sensor;
	public static final int[] metadataToForgeDirections = {3,4,2,5};
	
	public CCMethods(TileEntitySensor t) {
		sensor = t;
	}
	
	public String[] listMethods(){
		return new String[]{
		"boolean canFlow()",
		"void allowOutput(boolean)",
		"boolean isLimited()",
		"void setLimited(boolean)",		
		"int getLimit()",
		"void setLimit(int)",
		"int getIn()",
		"boolean setIn(String Direction)",
		"int getOut()",
		"boolean setOut(String Direction)",
		"double getAvg()",
		"int getRateLimit()",
		"boolean setRateLimit(int outLimit<max 10000>)",
		"String getName()",
		"boolean setName(String name)"};
	}
	
	public Object[] canFlow(){
		return new Object[]{sensor.canFlow()};
	}

	public Object[] allowOutput(Object[] arg){
		if(arg.length==1)
			sensor.allowOutput((Boolean)arg[0]);
			return new Object[]{null};
	}

	public Object[] isLimited(){
		return new Object[]{sensor.isLimited()};
	}

	public Object[] setLimited(Object[] args){
		if(args.length==1)
			sensor.setLimited((Boolean)args[0]);
		return new Object[]{null};
	}
		
	public Object[] getLimit(){
		return new Object[]{sensor.getLimit()};
	}

	public Object[] setLimit(Object[] args){
		if(args.length==1 && ((Double)args[0]).intValue()>=0){
			sensor.setLimit(((Double)args[0]).intValue());
		}
		return new Object[]{null};	
	}

	public Object[] getIn(){
		return new Object[]{ForgeDirection.getOrientation(sensor.getIn()).toString()};
	}
	
	public Object[] setIn(Object[] args){ 
		if(args.length==1){
			try{
				return new Object[]{sensor.setIn(ForgeDirection.valueOf(((String)args[0]).toUpperCase()).ordinal())};
			}catch(Exception e){
				int face = metadataToForgeDirections[sensor.blockMetadata & 3];
				ForgeDirection front =  ForgeDirection.getOrientation(face);
				return new Object[]{sensor.setIn(Sides.valueOf(((String)args[0]).toUpperCase()).getForgeDirection(front).ordinal())};
			}
		}else{
			return new Object[]{false};
		}		
	}

	public Object[] getOut(){
		return new Object[]{ForgeDirection.getOrientation(sensor.getOut()).toString()};
	}
	
	public Object[] setOut(Object[] args){
		if(args.length==1){
			try{
				return new Object[]{sensor.setIn(ForgeDirection.valueOf(((String)args[0]).toUpperCase()).ordinal())};
			}catch(Exception e){
				int face = metadataToForgeDirections[sensor.blockMetadata & 3];
				ForgeDirection front =  ForgeDirection.getOrientation(face);
				return new Object[]{sensor.setOut(Sides.valueOf(((String)args[0]).toUpperCase()).getForgeDirection(front).ordinal())};
			}
		}else{
			return new Object[]{false};
		}		
	}

	public Object[] getAvg(){
		return new Object[]{sensor.getAvg()};
	}

	public Object[] getRateLimit(){
		return new Object[]{sensor.getRateLimit()};
	}

	public Object[] setRateLimit(Object[] args){
		if(args.length==1 && ((Double)args[0]).intValue()>=0)
			return new Object[]{sensor.setRateLimit(((Double)args[0]).intValue())};
		else
			return new Object[]{false};
	}
	
	public Object[] getName(Object[] args){
		return new Object[]{sensor.getName()};
	}
	
	public Object[] setName(Object[] args){
		if(args.length == 1){
			sensor.setName((String)args[0]);
			return new Object[]{true};
		}else{
			return new Object[]{false};
		}
	}
}
