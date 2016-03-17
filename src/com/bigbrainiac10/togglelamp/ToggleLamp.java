package com.bigbrainiac10.togglelamp;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import com.bigbrainiac10.togglelamp.listeners.InteractListener;
import com.bigbrainiac10.togglelamp.listeners.PhysicsListener;

public class ToggleLamp extends JavaPlugin{

	private static Logger logger;
	public static ToggleLamp instance;
	
	private static List<Material> redstoneMats = new ArrayList<Material>();
	
	public void onEnable(){
		logger = this.getLogger();
		instance = this;
		
		saveDefaultConfig();
		reloadConfig();
		new TLConfigManager(getConfig());
		
		setupRedstoneMaterials();
		registerListeners();
	}
	
	public void onDisable(){
	}
	
	public void Log(String message){
		logger.log(Level.INFO, message);
	}
	
	public static boolean isRedstoneMaterial(Material mat){
		if (redstoneMats.contains(mat)) {
			return true;
		}
		
		return false;
	}
	
	private void registerListeners(){
		instance.getServer().getPluginManager().registerEvents(new InteractListener(instance), instance);
		instance.getServer().getPluginManager().registerEvents(new PhysicsListener(instance), instance);
	}
	
	private void setupRedstoneMaterials(){
		redstoneMats.add(Material.DETECTOR_RAIL);
		redstoneMats.add(Material.POWERED_RAIL);
		redstoneMats.add(Material.REDSTONE_WIRE);
		redstoneMats.add(Material.REDSTONE_BLOCK);
		redstoneMats.add(Material.PISTON_MOVING_PIECE);
		redstoneMats.add(Material.REDSTONE_TORCH_OFF);
		redstoneMats.add(Material.REDSTONE_TORCH_ON);
		redstoneMats.add(Material.DIODE_BLOCK_OFF);
		redstoneMats.add(Material.DIODE_BLOCK_ON);
		redstoneMats.add(Material.REDSTONE_COMPARATOR_OFF);
		redstoneMats.add(Material.REDSTONE_COMPARATOR_ON);
		redstoneMats.add(Material.DIODE_BLOCK_ON);
		redstoneMats.add(Material.LEVER);
		redstoneMats.add(Material.STONE_BUTTON);
		redstoneMats.add(Material.WOOD_BUTTON);
		redstoneMats.add(Material.GOLD_PLATE);
		redstoneMats.add(Material.IRON_PLATE);
		redstoneMats.add(Material.TRIPWIRE);
		redstoneMats.add(Material.TRIPWIRE_HOOK);
		redstoneMats.add(Material.DAYLIGHT_DETECTOR);
		redstoneMats.add(Material.DAYLIGHT_DETECTOR_INVERTED);
		redstoneMats.add(Material.WOOD_PLATE);
		redstoneMats.add(Material.STONE_PLATE);
	}
	
}
