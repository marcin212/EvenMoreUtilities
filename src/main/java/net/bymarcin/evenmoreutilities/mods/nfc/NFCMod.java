package net.bymarcin.evenmoreutilities.mods.nfc;



import li.cil.oc.api.Items;
import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.IMod;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;


public class NFCMod implements IMod{
	public static int NFCReaderID;
	public static int NFCProgrammerID;
	
	public static int NFCCardID;
	public static int NFCPrivateCardID;
	
	
    ItemStack microChip1= Items.get("chip1").createItemStack(1);
    ItemStack microChip2= Items.get("chip2").createItemStack(1);
    
    ItemStack cpu1= Items.get("cpu1").createItemStack(1);
    ItemStack interweb= Items.get("interweb").createItemStack(1);
    ItemStack wifi= Items.get("wlanCard").createItemStack(1);
    ItemStack circuitBoard= Items.get("printedCircuitBoard").createItemStack(1);
    ItemStack paper= new ItemStack(net.minecraft.item.Item.paper,1);
    ItemStack iron= new ItemStack(net.minecraft.item.Item.ingotIron,1);
	@Override
	public void init() {
		NFCReaderID = EvenMoreUtilities.instance.config.getBlock("BlocksId","NFCReaderID", 3014).getInt();
        GameRegistry.registerBlock(BlockNFCReader.instance,StaticValues.modId+":nfcReader");
        GameRegistry.addRecipe(new ItemStack(BlockNFCReader.instance,1),
        		"i i","cnw","ibi",'i',iron,'c',microChip2,'n',interweb,'w',wifi,'b',circuitBoard);
        

        NFCCardID = EvenMoreUtilities.instance.config.getBlock("ItemsId","NFCCardID", 1002).getInt();
        GameRegistry.registerItem(ItemCardNFC.instance, StaticValues.modId+".NFCCard");
        GameRegistry.addRecipe(new ItemStack(ItemCardNFC.instance,1),
        		"ppp","pcp","ppp",'p',paper,'c',microChip1);
        
        
        NFCPrivateCardID = EvenMoreUtilities.instance.config.getBlock("ItemsId","NFCPrivateCardID", 1001).getInt();
        GameRegistry.registerItem(ItemPrivateCardNFC.instance, StaticValues.modId+":NFCPrivateCard");
        GameRegistry.addRecipe(new ItemStack(ItemPrivateCardNFC.instance,1),
        		"ppp","pcp","ppp",'p',paper,'c',microChip2);
        
       
		NFCProgrammerID = EvenMoreUtilities.instance.config.getBlock("BlocksId","NFCProgrammerID", 3015).getInt();
        GameRegistry.registerBlock(BlockNFCProgrammer.instance,StaticValues.modId+":NFCProgrammer");
        GameRegistry.addRecipe(new ItemStack(BlockNFCProgrammer.instance,1),
        		"i i","cnw","ibi",'i',iron,'c',cpu1,'n',interweb,'w',wifi,'b',circuitBoard);

        GameRegistry.registerTileEntity(TileEntityNFCReader.class, StaticValues.modId+".nfcReader");
        GameRegistry.registerTileEntity(TileEntityNFCProgrammer.class, StaticValues.modId+".NFCProgrammer");
	}

	@Override
	public void load() {
		
	}
	
}
