package net.bymarcin.evenmoreutilities.utils;

import net.minecraftforge.common.ForgeDirection;

public enum Sides {
	
	BOTTOM("bottom"),TOP("top"),BACK("back"),FRONT("front"),RIGHT("right"),LEFT("left");
	
	public String name;
	Sides(String name){
		this.name = name;
	}
	
	public ForgeDirection getForgeDirection(ForgeDirection front){
			switch(this){
				case BACK: return ForgeDirection.getOrientation(front.ordinal()).getOpposite();
				case FRONT: return ForgeDirection.getOrientation(front.ordinal());
				case RIGHT: return ForgeDirection.getOrientation(front.ordinal()%5+2);
				case LEFT: return ForgeDirection.getOrientation(front.ordinal()%5+2).getOpposite();
				case TOP:
				case BOTTOM: return ForgeDirection.getOrientation(ordinal());
			}
			return front;
	}	
}
