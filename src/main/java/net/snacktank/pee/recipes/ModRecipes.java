package net.snacktank.pee.recipes;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.snacktank.pee.init.ModItems;

public class ModRecipes {
	public static void initSmelting() {
		GameRegistry.addSmelting(Items.CLOCK, new ItemStack(ModItems.FIRST_ITEM), 10.0F);
	}
}
