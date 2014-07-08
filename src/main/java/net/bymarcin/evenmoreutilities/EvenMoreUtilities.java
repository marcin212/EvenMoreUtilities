package net.bymarcin.evenmoreutilities;

import java.util.logging.Logger;

import net.bymarcin.evenmoreutilities.proxy.CommonProxy;
import net.bymarcin.evenmoreutilities.registry.EMURegistry;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.LanguageRegistry;
import erogenousbeef.core.multiblock.MultiblockEventHandler;

@Mod(modid=StaticValues.modId, name=StaticValues.modName, version="0.0.1")
@NetworkMod(clientSideRequired=true, serverSideRequired=true)
public class EvenMoreUtilities {
	ModManager modManager;

	public Configuration config;
	public CreativeTabs tabCustom;
	
	
    @SidedProxy(clientSide="net.bymarcin.evenmoreutilities.proxy.ClientProxy", serverSide="net.bymarcin.evenmoreutilities.proxy.CommonProxy")
    public static CommonProxy proxy;

    private MultiblockEventHandler multiblockEventHandler;
    
    @Instance(value = StaticValues.modId)
    public static EvenMoreUtilities instance;
   
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Logger.getLogger(StaticValues.modId).info("Start preInit!");

		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();	
		modManager = new ModManager();
		modManager.preInit();
		
		Logger.getLogger(StaticValues.modId).info("Finish preInit!");
	}
    
    @EventHandler 
    public void init(FMLInitializationEvent event) {
    	Logger.getLogger(StaticValues.modId).info("Start init!");
        proxy.register();
        /*
         * 
         * Creative Tab
         * 
         */
        Logger.getLogger(StaticValues.modId).info("Register Creative Tab");
        tabCustom = new CreativeTabs("EMU") {
            public ItemStack getIconItemStack() {
                    return new ItemStack(Item.bakedPotato, 1, 0);
            }
        };
        LanguageRegistry.instance().addStringLocalization("itemGroup.EMU", "en_US", StaticValues.modName);
        
        modManager.postInit();
        /*
         * 
         * Handlers
         * 
         */
        EMURegistry.init();
        Logger.getLogger(StaticValues.modId).info("Finish init!");
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
    	Logger.getLogger(StaticValues.modId).info("Start postInit!");
    	modManager.postInit();
    	config.save();
    	EMURegistry.postInit();
    	Logger.getLogger(StaticValues.modId).info("Finish postInit!");
    }

	@EventHandler
	public void registerServer(FMLServerAboutToStartEvent evt) {
		multiblockEventHandler = new MultiblockEventHandler();
		MinecraftForge.EVENT_BUS.register(multiblockEventHandler);
	}
}
