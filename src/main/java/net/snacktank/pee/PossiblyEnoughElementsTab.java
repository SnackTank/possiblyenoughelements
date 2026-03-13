package net.snacktank.pee;

import net.snacktank.pee.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PossiblyEnoughElementsTab extends CreativeTabs {
	public PossiblyEnoughElementsTab() {
		super(PossiblyEnoughElements.MODID);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.FIRST_ITEM);
	}
}
