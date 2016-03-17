package com.bigbrainiac10.togglelamp.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import com.bigbrainiac10.togglelamp.TLConfigManager;
import com.bigbrainiac10.togglelamp.ToggleLamp;

import net.minecraft.server.v1_9_R1.World;
import vg.civcraft.mc.citadel.Citadel;
import vg.civcraft.mc.citadel.ReinforcementManager;
import vg.civcraft.mc.citadel.reinforcement.PlayerReinforcement;
import vg.civcraft.mc.citadel.reinforcement.Reinforcement;

public class InteractListener implements Listener {
	
	private ToggleLamp plugin;
	private ReinforcementManager rm = Citadel.getReinforcementManager();
	
	public InteractListener(ToggleLamp p) {
		this.plugin = p;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onInteract(PlayerInteractEvent event){
		
		if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
			return;
		
		Player eventPlayer = event.getPlayer();
		Block clickedBlock = event.getClickedBlock();
		
		Material clickedBlockMat = clickedBlock.getType();
		
		if(eventPlayer.isSneaking())
			return;
		
		if(!(clickedBlockMat.equals(Material.REDSTONE_LAMP_ON) || clickedBlockMat.equals(Material.REDSTONE_LAMP_OFF)))
			return;
		
		if(clickedBlock.hasMetadata("ToggleLamp_NextToggle")){
			MetadataValue val = clickedBlock.getMetadata("ToggleLamp_NextToggle").get(0);
			if(!(((long)val.value()) <= System.currentTimeMillis()))
				return;
		}
		
		event.setCancelled(true);
		
		if(rm.isReinforced(clickedBlock)){
			Reinforcement rein = rm.getReinforcement(clickedBlock);
			
			if(rein instanceof PlayerReinforcement){
				PlayerReinforcement r = (PlayerReinforcement) rm.getReinforcement(clickedBlock);
				if(!r.getGroup().isMember(eventPlayer.getUniqueId()))
					return;
			}
		}
		
		/*BlockState state = clickedBlock.getState();
		state.setType(clickedBlockMat.equals(Material.REDSTONE_LAMP_ON) ? Material.REDSTONE_LAMP_OFF : Material.REDSTONE_LAMP_ON);
		state.update(true);*/
		
		try {
			switchLamp(clickedBlock, !clickedBlockMat.equals(Material.REDSTONE_LAMP_ON));
			if(TLConfigManager.debugMode())
				plugin.Log("Toggled Lamp: " + clickedBlock.getType().toString());
		} catch (Exception e) {
			if(TLConfigManager.debugMode())
				plugin.Log("Failed to toggle lamp.");
		}
		
		
		eventPlayer.getWorld().playSound(clickedBlock.getLocation(), Sound.BLOCK_LEVER_CLICK, 0.5F, 1.0F);
		
	}
	
	public void switchLamp(Block b, boolean lighting) throws Exception
	{
		World w = ((CraftWorld)b.getWorld()).getHandle();
		
		if (lighting == true)
		{
			setWorldStatic(w, true);
			b.setType(Material.REDSTONE_LAMP_ON);
			setWorldStatic(w, false);
		}
		else
		{
			b.setType(Material.REDSTONE_LAMP_OFF);
		}
		
		b.setMetadata("ToggleLamp_NextToggle", new FixedMetadataValue(plugin, System.currentTimeMillis()+100));
	}
	
	private static void setWorldStatic(World world, boolean static_boolean) throws Exception {
		java.lang.reflect.Field static_field = World.class.getDeclaredField("isClientSide");
		
		static_field.setAccessible(true);
		static_field.set(world, static_boolean);
	}

	
}
