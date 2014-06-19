package net.bymarcin.evenmoreutilities.mods.vanillautils;

import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class KinderSurprise extends Item{
	private static float topChance=0;
	private static float topLivingChance=0;
	private static TreeMap<Float, Item> drops = new TreeMap<Float, Item>();
	private static Random rand = new Random();
	public static KinderSurprise instance =new KinderSurprise(VanillaUtils.kinderSurpriseID);
	public static TreeMap<Float, EntityLiving> livingDrops = new TreeMap<Float, EntityLiving>();
	
	
	public KinderSurprise(int id) {
		super(id);
		setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		setMaxStackSize(16);
		setUnlocalizedName("emu.kindersurprise");
		
	}

	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		
		if (item != null){
			player.dropPlayerItemWithRandomChoice(getRandomDrop(), false);
			item.stackSize-=1;
		}
		if(!world.isRemote){
			Entity a = EntityList.createEntityByID(EntityList.getEntityID(new EntityZombie(world)), world);
			a.setPosition(player.posX, player.posY, player.posZ);
			world.spawnEntityInWorld(a);
		}
				return item; 
	}
	
	private static void getRandomEntity(){
		int mobId = (int)(rand.nextFloat() * EntityList.IDtoClassMapping.size());
	}
	
	private static ItemStack getRandomDrop() {
		float chance = rand.nextFloat()*topChance;
		System.out.println("chance:"+chance);
	    Entry<Float, Item> e = drops.ceilingEntry(chance);

	    System.out.println("preintfewqgwwggw" + e.toString());
	    return new ItemStack(e.getValue(),1);
	}
	
	public static void addDrop(float chance,Item item){
		topChance+=chance;
		drops.put(topChance,item);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) {
		itemIcon = icon.registerIcon(StaticValues.modId + ":" + "kindersurprise_item");
	}
}
