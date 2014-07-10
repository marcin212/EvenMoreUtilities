package net.bymarcin.evenmoreutilities;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;

public class ModManager {
	HashSet<ModDescription> mods = new HashSet<ModManager.ModDescription>();
	private static String modsClassPath = "net.bymarcin.evenmoreutilities.mods.";

	
	private void addMods(){
		addMod("quarryfixer.QuarryFixerMod", 					"$('BuildCraft|Energy')" , 	"QuarryFixerMod");
		addMod("energysiphon.EnergySiphonMod", 					"$('ThermalExpansion')", 	"EnergySiphonMod");
		addMod("sensor.SensorMod",  		"$('ThermalExpansion') && ($('ComputerCraft') || $('OpenComponents'))" , "SensorMod");
		addMod("scanner.ScannerMod", 							"$('OpenComponents')", 		"ScannerMod");
		addMod("redstonemitter.RedstoneEmitterMod", 			"", 						"RedstoneEmitterMod");
		addMod("nfc.NFCMod",									"$('OpenComponents')" ,		"NFCMod");
		addMod("additionalconverters.ModAdditionalConverters",	"$('OpenComponents')" , 	"AdditionalConverters");
		addMod("yelloriumenhancement.YE",						"$('ExtraBees') && $('BigReactors')","YE");
		addMod("vanillautils.VanillaUtils",						"",							"VanillaUtils");
		addMod("bigbattery.BigBatteryMod", "" ,"BigBattery");
		addMod("additionalsounds.SoundsMod", "", "SoundsMod");
	}
	
	
	
	private void addMod(String path, String dependencies, String name) {
		mods.add(new ModDescription(modsClassPath + path, dependencies, EvenMoreUtilities.instance.config.get("Mods", name, true).getBoolean(true)));
	}

	public void preInit(){
		HashSet<String> mods = new HashSet<String>();
		
		for( ModContainer mod :Loader.instance().getModList()){
			mods.add(mod.getModId());
		}
		
		binding.put("mods", mods);
		
		addMods();
	}
	
	
	
	public void init() {
		for (ModDescription mod : mods) {
			if (mod.toLoad) {
				loadMod(mod);
			}
			if (mod.isLoaded) {
				mod.mod.init();
				System.out.println("Loaded: " + mod.classPath);
			}
		}
	}

	public void postInit() {
		for (ModDescription mod : mods) {
			if (mod.isLoaded)
				mod.mod.postInit();
		}
	}

	private void loadMod(ModDescription mod) {
		try {
			Class<?> modClass = Class.forName(mod.classPath);
			Constructor<?> c = modClass.getConstructor();
			mod.mod = (IMod) c.newInstance();
			mod.isLoaded = mod.mod != null;
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

	
	private ScriptEngine engine = new ScriptEngineManager(null).getEngineByName("js");
	private Bindings binding = engine.createBindings();
	
	
	private class ModDescription {
		
		Boolean toLoad = false;
		Boolean isLoaded = false;
		IMod mod = null;
		String classPath;
		
		public ModDescription(String classPath, String dependencies, Boolean toLoad) {
			this.classPath = classPath;
			this.toLoad = toLoad && eval(dependencies);		
		}
		private boolean eval(String dependencies){
			String script = "function $(modId){"
					+ "return mods.contains(modId)}; true; ";
			try {
				return (Boolean)engine.eval(script + dependencies, binding);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}

}
