package com.team.kalstuff.item;

import com.team.kalstuff.KalStuffSoundEvents;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemClosedSoda extends Item {
	
	Item item;
	
	public ItemClosedSoda(Item item) {
		this.item = item;
		this.setMaxStackSize(6);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
		super.onItemRightClick(worldIn, playerIn, hand);
		
		worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, KalStuffSoundEvents.CAN_OPEN, SoundCategory.PLAYERS, 1.0F, 1.0F);
		
		ItemStack stack = playerIn.getHeldItem(hand);
		
		//TODO: these functions are going to change
		
		//pseudocode: stack.addAmount(-1);
		stack.func_190917_f(-1);
		
		//pseudocode: stack.getAmount() <= 0
        if (stack.func_190916_E() <= 0) return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(this.item));
        playerIn.inventory.addItemStackToInventory(new ItemStack(this.item));
        
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
	
}
