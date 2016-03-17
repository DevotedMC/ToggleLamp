package com.bigbrainiac10.togglelamp;

import org.bukkit.configuration.file.FileConfiguration;

public class TLConfigManager {

	private static FileConfiguration config;
	
	public TLConfigManager(FileConfiguration con){
		config = con;
	}
	
	public static boolean debugMode(){
		return config.getBoolean("debug");
	}
	
}
