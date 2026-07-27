package net.snacktank.pee.machine;

import java.util.LinkedList;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class TickHandler {
	
	public static LinkedList<MachineElectrolysis> machines = new LinkedList<MachineElectrolysis>();
	private int j = 0;
	public TickHandler() {
		
	}
	
	@SubscribeEvent
	public void onServerTick(ServerTickEvent event) {
		j++;
		
		if(j == 200) {
			for(int i = 0; i < machines.size(); i++) {
				machines.get(i).update();
			}
			j = 0;
		}

	}
}
