package net.bymarcin.evenmoreutilities;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

import cpw.mods.fml.common.Loader;

public class ModManager {
	HashSet<ModDescription> mods = new HashSet<ModManager.ModDescription>();
	private static String modsClassPath = "net.bymarcin.evenmoreutilities.mods.";
	
	private boolean run(String modname){
		return EvenMoreUtilities.instance.config.get("Mods", modname, true).getBoolean(true);
	}
	
	public ModManager() {
		
		mods.add(new ModDescription(modsClassPath+"quarryfixer.QuarryFixerMod",new String[]{"BuildCraft|Energy"},run("QuarryFixerMod")));
		mods.add(new ModDescription(modsClassPath+"energysiphon.EnergySiphonMod",new String[]{"ThermalExpansion"},run("EnergySiphonMod")));
		mods.add(new ModDescription(modsClassPath+"sensor.SensorMod",new String[]{"ThermalExpansion","ComputerCraft"},run("SensorMod")));
		mods.add(new ModDescription(modsClassPath+"scanner.ScannerMod",new String[]{"OpenComponents"},run("ScannerMod")));
		mods.add(new ModDescription(modsClassPath+"redstonemitter.RedstoneEmitterMod",new String[]{},run("RedstoneEmitterMod")));
		mods.add(new ModDescription(modsClassPath+"nfc.NFCMod", new String[]{"OpenComponents"}, run("NFCMod")));
	}
	
	public void postInit(){
		for(ModDescription mod:mods){
			if(mod.toLoad){
				if(mod.dependencies!=null){
					for(String modName: mod.dependencies){
						mod.toLoad= Loader.isModLoaded(modName);
						if(!mod.toLoad) break;
					}
				}
			}
			if(mod.toLoad){
				loadMod(mod);
			}
			if(mod.isLoaded){ mod.mod.init(); System.out.println("Loaded: " + mod.classPath);}
		}
	}
	
	public void load(){
		for(ModDescription mod:mods){
			if(mod.isLoaded) mod.mod.load();
		}
	}
	
	private void loadMod(ModDescription mod){
			try {
				Class<?> modClass = Class.forName(mod.classPath);
				Constructor<?> c = modClass.getConstructor();
				mod.mod= (IMod) c.newInstance();
				mod.isLoaded = mod.mod!=null;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}	
	}
		
	private class ModDescription{
		Boolean toLoad =false;
		Boolean isLoaded=false;
		IMod mod=null;
		String classPath;
		String[] dependencies =null;
		public ModDescription(String classPath, String[] dependencies, Boolean toLoad){
			this.classPath = classPath;
			this.dependencies = dependencies;
			this.toLoad = toLoad;
		}
	}
	
}
