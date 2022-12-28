package org.dawnoftimebuilder.block.japanese;

import javax.annotation.Nullable;

import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.registry.DoTBBlocksRegistry;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

public class MappleTrunk extends BlockDoTB
{
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public MappleTrunk(Properties properties) {
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

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
    	World level = context.getLevel();
    	BlockPos bottomCenter = context.getClickedPos();
    	BlockState state = level.getBlockState(new BlockPos(bottomCenter.getX(),bottomCenter.getY() - 1,bottomCenter.getZ()));

    	if(!Tags.Blocks.DIRT.contains(state.getBlock()))
    		return Blocks.AIR.defaultBlockState();

    	if(context.getPlayer().position().x >= bottomCenter.getX() - 1.75f &&
    	   context.getPlayer().position().y >= bottomCenter.getY() - 0.75f &&
    	   context.getPlayer().position().z >= bottomCenter.getZ() - 1.75f &&
    	   context.getPlayer().position().x <= bottomCenter.getX() + 1.75f &&
    	   context.getPlayer().position().y <= bottomCenter.getY() + 2.75f &&
    	   context.getPlayer().position().z <= bottomCenter.getZ() + 1.75f)
    		return Blocks.AIR.defaultBlockState();

    	state = level.getBlockState(bottomCenter);

    	if(!(state.getBlock() instanceof AirBlock) || !state.canBeReplaced(context))
    	{
    		return Blocks.AIR.defaultBlockState();
    	}


        for(int x = -1; x <= 1; x ++)
        {
            for(int y = 0; y <= 1; y ++)
            {
                for(int z = -1; z <= 1; z ++)
                {
                	state = level.getBlockState(new BlockPos(bottomCenter.getX() + x, bottomCenter.getY() + y + 1, bottomCenter.getZ() + z));

                	if(state == null || (!(state.getBlock() instanceof AirBlock) || !state.canBeReplaced(context)))
                	{
                		return Blocks.AIR.defaultBlockState();
                	}
                }
            }
        }

        return super.getStateForPlacement(context).setValue(FACING, Direction.Plane.HORIZONTAL.getRandomDirection(level.random));
    }

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
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
        worldIn.setBlock(pos, state
        		.setValue(FACING, state.getValue(FACING)), 10);
    	
        for(int x = -1; x <= 1; x ++)
        {
            for(int y = 0; y <= 1; y ++)
            {
                for(int z = -1; z <= 1; z ++)
                {
                	BlockPos newBlockPosition = new BlockPos(pos.getX() + x, pos.getY() + y+1, pos.getZ() + z);

                    worldIn.setBlock(newBlockPosition, DoTBBlocksRegistry.MAPPLE_LEAVES.get().defaultBlockState()
                    		.setValue(FACING, state.getValue(FACING))
                    		.setValue(MappleLeaves.MULTIBLOCK_X, x+1)
                    		.setValue(MappleLeaves.MULTIBLOCK_Y, y)
                    		.setValue(MappleLeaves.MULTIBLOCK_Z, z+1), 10);
                }
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
    	/**if(facingState.getBlock() == this)
    	{
    		
    	}**/
     	
        return stateIn;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }
}