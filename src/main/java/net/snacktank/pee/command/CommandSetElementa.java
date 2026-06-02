package net.snacktank.pee.command;

import java.util.LinkedList;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandSetElementa extends CommandBase{

	/*setelement <id>
	 * id 0: read NBT			| /setelement 0
	 * id 1: set allergies		| /setelement 1 <name> <allergy-length> <allergies>
	 * 
	*/
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] params) throws CommandException {
		EntityPlayer player = sender.getCommandSenderEntity().getEntityWorld().getPlayerEntityByUUID(sender.getCommandSenderEntity().getUniqueID());
		Item item = player.getHeldItemMainhand().getItem();
		int id = Integer.parseInt(params[0]);
		if(id == 0) {
			String message = getNBT(player);
			if(message == null) return;
			sender.sendMessage(new TextComponentString(message));
			return;
		}
		
		//We're assuming the ID is 1 or above
		String name = params[1];
		int allergyLength = Integer.parseInt(params[2]);
		LinkedList<Integer> allergies = new LinkedList<Integer>();
		for(int i = 0; i < allergyLength; i++) {
			allergies.add(Integer.parseInt(params[i+3]));
		}
		int[] allergyArray = new int[allergies.size()];
		for(int i = 0; i < allergies.size(); i++) {
			allergyArray[i] = allergies.get(i);
		}
		
		//We're just going to override the NBT data, even if it already exists!
		NBTTagCompound blockEntityNBT = new NBTTagCompound();
		NBTTagCompound displayNBT = new NBTTagCompound();
		NBTTagCompound finalCompound = new NBTTagCompound();
		
		blockEntityNBT.setIntArray("allergies", allergyArray);
		blockEntityNBT.setString("name", name);
		
		displayNBT.setString("Name", name);
		
		finalCompound.setTag("BlockEntityTag", blockEntityNBT);
		finalCompound.setTag("display", displayNBT);
		
		player.getHeldItemMainhand().setTagCompound(finalCompound);
	}

	private String getNBT(EntityPlayer player) {
		Item item = player.getHeldItemMainhand().getItem();
		NBTTagCompound nbt = item.getNBTShareTag(player.getHeldItemMainhand());
		if(nbt == null) return null;
		return nbt.toString();
	}
	
	@Override
	public String getName() {
		return "setelement";
	}

	@Override
	public String getUsage(ICommandSender arg0) {
		return "TODO FIX THIS";
	}

}
