package net.snacktank.pee.command;

import net.snacktank.pee.machine.*;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandMachine extends CommandBase{

	//machine
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] params) throws CommandException {
		//Now the question is... does the command or the machine itself have the logic? I'm thinking the machine.
		MachineElectrolysis machine = new MachineElectrolysis(server, sender);
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
