package net.snacktank.pee.command;

import net.snacktank.pee.machine.*;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class CommandMachine extends CommandBase{

	//machine
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] params) throws CommandException {
		World world = server.getEntityWorld();
		EntityPlayer player = world.getPlayerEntityByUUID(sender.getCommandSenderEntity().getUniqueID());
		MachineElectrolysis machine = new MachineElectrolysis(world, player);
		TickHandler.machines.add(machine);
	}

	@Override
	public String getName() {
		return "machine";
	}

	@Override
	public String getUsage(ICommandSender arg0) {
		return "REPLACE";
	}

}
