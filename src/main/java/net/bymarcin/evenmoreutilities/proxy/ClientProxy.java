package net.bymarcin.evenmoreutilities.proxy;

import net.bymarcin.evenmoreutilities.registry.IProxy;

public class ClientProxy extends CommonProxy{

    @Override
    public void register() {
    	for(IProxy p: proxy)
    		p.clientSide();
    }   
}
