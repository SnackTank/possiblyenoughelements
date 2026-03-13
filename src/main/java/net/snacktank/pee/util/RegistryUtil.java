package net.snacktank.pee.util;

import net.snacktank.pee.PossiblyEnoughElements;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class RegistryUtil {
	public static Item setItemName(final Item item, final String name) {
		item.setRegistryName(PossiblyEnoughElements.MODID, name).setUnlocalizedName(PossiblyEnoughElements.MODID + "." + name);
		return item;
	}
	
	public static Block setBlockName(final Block block, final String name) {
		block.setRegistryName(PossiblyEnoughElements.MODID, name).setUnlocalizedName(PossiblyEnoughElements.MODID + "." + name);
		return block;
	}
}
