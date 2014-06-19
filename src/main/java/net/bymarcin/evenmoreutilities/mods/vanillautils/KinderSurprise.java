package net.bymarcin.evenmoreutilities.mods.vanillautils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import net.bymarcin.evenmoreutilities.EvenMoreUtilities;
import net.bymarcin.evenmoreutilities.utils.StaticValues;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
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
	public static TreeMap<Float,Class<? extends EntityLiving>> livingDrops = new TreeMap<Float, Class<? extends EntityLiving>>();
	
	public KinderSurprise(int id) {
		super(id);
		setCreativeTab(EvenMoreUtilities.instance.tabCustom);
		setMaxStackSize(16);
		setUnlocalizedName("emu.kindersurprise");
		
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		float decision = rand.nextFloat() * 10;
		if(decision <8.0F){
				player.dropPlayerItemWithRandomChoice(getRandomDrop(), false);	
		}else{
			if(!world.isRemote){
				spawnMob(world,player);
			}
		}
		item.stackSize-=1;
		return item; 
	}
	
	private static void spawnMob(World world, EntityPlayer player){
		Entity mob = null;
		try {
			 mob = EntityList.createEntityByID(EntityList.getEntityID(getRandomEntity().getConstructor(World.class).newInstance(world)), world);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		if(mob != null){
			mob.setPosition(player.posX, player.posY, player.posZ);
			world.spawnEntityInWorld(mob);
		}
	}
	
	private static Class<? extends EntityLiving> getRandomEntity(){
		float chance = rand.nextFloat()*topLivingChance;
		Entry<Float, Class<? extends EntityLiving>> e = livingDrops.ceilingEntry(chance);
		return e.getValue();
	}
	
	private static ItemStack getRandomDrop() {
		float chance = rand.nextFloat()*topChance;
	    Entry<Float, Item> e = drops.ceilingEntry(chance);
	    return new ItemStack(e.getValue(),1);
	}
	
	public static void addDrop(float chance,Item item){
		topChance+=chance;
		drops.put(topChance,item);
	}
	public static void addDrop(float chance,Class<? extends EntityLiving> c){
		topLivingChance+=chance;
		livingDrops.put(topLivingChance,c);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) {
		itemIcon = icon.registerIcon(StaticValues.modId + ":" + "kindersurprise_item");
	}
}
