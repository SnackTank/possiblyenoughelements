package net.snacktank.pee.machine;

import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class TickHandler {
	
	public static LinkedList<MachineElectrolysis> machines = new LinkedList<MachineElectrolysis>();
	private static LinkedList<MachineElectrolysis> newMachines = new LinkedList<MachineElectrolysis>();
	private LinkedList<MachineElectrolysis> updateQueue = new LinkedList<MachineElectrolysis>();
	private LinkedList<MachineElectrolysis> deleteQueue = new LinkedList<MachineElectrolysis>();
	private int j = 0;
	private int k = 0;
	public TickHandler() {
		
	}
	
	@SubscribeEvent
	public void onServerTick(ServerTickEvent event) {
		j++;
		k++;
		
		if(j == 200) {
			for(int i = 0; i < machines.size(); i++) {
				if(machines.get(i) == null) {
					newMachines = machines;
					newMachines.remove(i);
					continue;
				}
				machines.get(i).update();
			}
			j = 0;
			machines = newMachines;
		}
		if(k == 10) {
			for(int i = 0; i < updateQueue.size(); i++) {
				updateQueue.get(i).machineGet();
				deleteQueue.add(updateQueue.get(i));
			}
			updateQueue.removeAll(deleteQueue);
			k = 0;
		}
	}
	
	@SubscribeEvent
	public void onBlockPlacedByEntity(EntityPlaceEvent event) {		
		Block placedBlock = event.getPlacedBlock().getBlock();
		Block goldBlock = Block.getBlockFromName("minecraft:gold_block");
		
		if(!placedBlock.equals(goldBlock)) return;
		
		
		int p = 0;
		boolean brokeOut = false;
		while(p < machines.size()) {
			if(event.getWorld().getChunkFromBlockCoords(event.getPos()) == machines.get(p).chunk) {
				//Block is in chunk
				MachineElectrolysis me = machines.get(p);
				updateQueue.add(me);
				k = 0;
				brokeOut = true;
				break;
			}
			p++;
		}
		if(!brokeOut) {
			//Block must be out of chunk OR no machines exist.
			MachineElectrolysis ec = new MachineElectrolysis(event.getWorld(), event.getWorld().getPlayerEntityByUUID(event.getEntity().getUniqueID()));
			machines.add(ec);
			updateQueue.add(ec);
			k = 0;
		}
	}
}
