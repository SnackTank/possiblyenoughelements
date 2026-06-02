package net.snacktank.pee.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.snacktank.pee.tileentity.TileEntityGasum;

public class CommandSetGasum extends CommandBase{

	@Override
	//setgas <x> <y> <z> <name> <can_float> <can_explode>
	public void execute(MinecraftServer server, ICommandSender sender, String[] params) throws CommandException {
		
		//Check if the command is 'valid'
		if(params.length != 6) {
			sender.sendMessage(new TextComponentString("Usage: /setgas <x> <y> <z> <name> <can_float> <can_explode>"));
			return;
		}
		

		double x, y, z;
		x = Double.parseDouble(params[0]);
		y = Double.parseDouble(params[1]);
		z = Double.parseDouble(params[2]);
		
			TileEntity te = sender.getEntityWorld().getTileEntity(new BlockPos(x,y,z));
			
			if(te instanceof TileEntityGasum) {
				((TileEntityGasum) te).name = params[3];
				((TileEntityGasum) te).canFloat = getBool(params[4]);
				((TileEntityGasum) te).canExplode = getBool(params[5]);
				((TileEntityGasum) te).markDirty();
				
				NBTTagCompound nbt = te.writeToNBT(new NBTTagCompound());
	            sender.sendMessage(new TextComponentString("NEW :TM: Gas NBT: " + nbt.toString()));
			} else {
				sender.sendMessage(new TextComponentString("No gas here :("));
			}
	}

	public boolean getBool(String string) {
		switch(string) {
		case "true":
			return true;
		case "false":
			return false;
		}
		return false;
	}
	
	@Override
	public String getName() {
		return "setgas";
	}

	@Override
	public String getUsage(ICommandSender arg0) {
		return "command.possiblyenoughelements.setgas.usage";
	}

}
