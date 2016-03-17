package com.bigbrainiac10.togglelamp.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

import com.bigbrainiac10.togglelamp.ToggleLamp;

public class PhysicsListener implements Listener{
	
	private ToggleLamp plugin;
	
	public PhysicsListener(ToggleLamp p) {
		this.plugin = p;
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockPhysics(BlockPhysicsEvent e)
	{
		Material eventMaterial = e.getBlock().getType();
		
		if((eventMaterial.equals(Material.REDSTONE_LAMP_ON) || eventMaterial.equals(Material.REDSTONE_LAMP_OFF)) && (!plugin.isRedstoneMaterial(e.getChangedType()))){
			e.setCancelled(true);
		}
	}

}
