package net.bymarcin.evenmoreutilities.proxy;

import net.bymarcin.evenmoreutilities.registry.EMURegistry;
import net.bymarcin.evenmoreutilities.registry.IProxy;

public class CommonProxy extends EMURegistry{
	
    public void register() {
    	for(IProxy p: proxy)
    		p.serverSide();
    }  
}
