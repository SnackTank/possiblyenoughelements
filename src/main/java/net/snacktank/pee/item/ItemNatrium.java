package net.snacktank.pee.item;

import net.minecraft.item.ItemFood;
import net.snacktank.pee.PossiblyEnoughElements;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;

public class ItemNatrium extends ItemFood{

	public ItemNatrium(int healAmount, float saturationModifier, boolean isWolfsFavoriteMeat) {
		super(healAmount, saturationModifier, isWolfsFavoriteMeat);
		setAlwaysEdible();
		setRegistryName(PossiblyEnoughElements.MODID, "natrium").setUnlocalizedName(PossiblyEnoughElements.MODID + "." + "natrium").setCreativeTab(PossiblyEnoughElements.PEE_DRAWER);
	}

	@Override
	protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
		entityPlayer.sendStatusMessage(new TextComponentTranslation("item.possiblyenoughelements.natrium.yummy", new Object[0]), true);
		EntityTNTPrimed TNT = new EntityTNTPrimed(world, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, null);
		TNT.setFuse(0);
		world.spawnEntity(TNT);
		//entityPlayer.addPotionEffect(new PotionEffect(MobEffects.SPEED, 200, 1));
	}
}
