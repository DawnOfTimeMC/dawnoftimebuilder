package org.dawnoftimebuilder.block.japanese;

import java.util.List;

import org.dawnoftimebuilder.block.ICustomBlockItem;
import org.dawnoftimebuilder.block.templates.BlockDoTB;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext.Builder;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.Tags;

public class MapleTrunkBlock extends BlockDoTB implements ICustomBlockItem
{
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public MapleTrunkBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.defaultBlockState()
        		.setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    // TODO MappleTrunk getShape ?
    /**@Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.getValue(MULTIBLOCK) == 2 ? VS_TOP : VS;
    }**/

    public void playerWillDestroy(World worldIn, BlockPos blockPosIn, BlockState blockStateIn, PlayerEntity playerEntityIn) 
    {
        if(!worldIn.isClientSide)
    	{
	        if (playerEntityIn.isCreative())
	        {
		    	BlockPos trunkBlockPos = new BlockPos(blockPosIn.getX(), blockPosIn.getY(), blockPosIn.getZ());
		    	BlockState state = worldIn.getBlockState(trunkBlockPos);
				worldIn.setBlock(trunkBlockPos, Blocks.AIR.defaultBlockState(), 35);
				worldIn.levelEvent(playerEntityIn, 2001, blockPosIn, Block.getId(state));
	        }

	        for(int x = -1; x <= 1; x ++)
	        {
	            for(int y = 1; y <= 2; y ++)
	            {
	                for(int z = -1; z <= 1; z ++)
	                {
	        	    	BlockPos baseBlock = new BlockPos(blockPosIn.getX() + x, blockPosIn.getY() + y, blockPosIn.getZ() + z);
	        	    	BlockState state = worldIn.getBlockState(baseBlock);
	        			worldIn.setBlock(baseBlock, Blocks.AIR.defaultBlockState(), 35);
	        			worldIn.levelEvent(playerEntityIn, 2001, blockPosIn, Block.getId(state));
	                }
	            }
	        }
    	}

        super.playerWillDestroy(worldIn, blockPosIn, blockStateIn, playerEntityIn);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
    	if(facing.equals(Direction.DOWN))
    	{
    		BlockState state = worldIn.getBlockState((currentPos.below()));
        	if(!Tags.Blocks.DIRT.contains(state.getBlock()))
        		return Blocks.AIR.defaultBlockState();
    	}
    	/**if(facingState.getBlock() == this)
    	{
    	}**/
     	
        return stateIn;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }
  
    @Override
    public Item getCustomBlockItem()
    {
    	return null;
    }

    /**
     * Glass code like
     */
    
    @OnlyIn(Dist.CLIENT)
    public float getShadeBrightness(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
       return 1.0F;
    }

    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
       return true;
    }
}