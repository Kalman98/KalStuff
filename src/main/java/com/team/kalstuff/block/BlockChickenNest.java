package com.team.kalstuff.block;

import com.team.KalStuff;
import com.team.kalstuff.StartupCommon;
import com.team.kalstuff.tileentity.TileEntityChickenNest;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockChickenNest extends BlockContainer {

	public BlockChickenNest() {
		super(Material.plants);
		this.setStepSound(soundTypeGrass);
		this.setCreativeTab(StartupCommon.kalStuffTab);
		this.setHardness(0.4F);
		this.setBlockBounds(0.0f, 0.0f, 0.0f, 16/16.0f, 3/16.0f, 16/16.0f);
	}

    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
    {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 16/16.0f, 3/16.0f, 16/16.0f);
    }
	/*
 	
	//These lines are presumed unnecessary, but were in the BlockSlab file. If problems arise
	// related to the bounding boxes, try un-commenting this.
   	public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity)
    {
        this.setBlockBoundsBasedOnState(worldIn, pos);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
    }
    
  	public void setBlockBoundsForItemRender() {
	 	this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}
	*/
    
	// Called when the block is placed or loaded client side to get the tile entity for the block
	// Should return a new instance of the tile entity for the block
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityChickenNest();
	}
    
	// Called when the block is right clicked
		// In this block it is used to open the blocks gui when right clicked by a player
		@Override
		public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
			// Uses the gui handler registered to your mod to open the gui for the given gui id
			// open on the server side only  (not sure why you shouldn't open client side too... vanilla doesn't, so we better not either)
			if (worldIn.isRemote) return true;

			if (playerIn.isSneaking()) {
				TileEntityChickenNest tileentitychickennest = (TileEntityChickenNest) worldIn.getTileEntity(pos);
				tileentitychickennest.dropChicken();
			} else playerIn.openGui(KalStuff.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
			
			return true;
		}
		
		
		// This is where you can do something when the block is broken. In this case drop the inventory's contents
		@Override
		public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

			IInventory inventory = worldIn.getTileEntity(pos) instanceof IInventory ? (IInventory)worldIn.getTileEntity(pos) : null;

			if (inventory != null){
				// For each slot in the inventory
				for (int i = 0; i < inventory.getSizeInventory(); i++){
					// If the slot is not empty
					if (inventory.getStackInSlot(i) != null)
					{
						// Create a new entity item with the item stack in the slot
						EntityItem item = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, inventory.getStackInSlot(i));

						// Apply some random motion to the item
						float multiplier = 0.1f;
						float motionX = worldIn.rand.nextFloat() - 0.5f;
						float motionY = worldIn.rand.nextFloat() - 0.5f;
						float motionZ = worldIn.rand.nextFloat() - 0.5f;

						item.motionX = motionX * multiplier;
						item.motionY = motionY * multiplier;
						item.motionZ = motionZ * multiplier;

						// Spawn the item in the world
						worldIn.spawnEntityInWorld(item);
					}
				}

				// Clear the inventory so nothing else (such as another mod) can do anything with the items
				inventory.clear();
			}

			// Super MUST be called last because it removes the tile entity
			super.breakBlock(worldIn, pos, state);
		}
    
    
    
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.SOLID;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return 3;
	}
	
}