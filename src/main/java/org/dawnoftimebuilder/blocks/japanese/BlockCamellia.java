package org.dawnoftimebuilder.blocks.japanese;

import net.minecraft.block.BlockGrass;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.IBlockFlowerGen;
import org.dawnoftimebuilder.blocks.general.DoTBBlockSoilCrops;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.dawnoftimebuilder.items.DoTBItems.camellia_leaves;
import static org.dawnoftimebuilder.items.DoTBItems.camellia_seed;

public class BlockCamellia extends DoTBBlockSoilCrops implements IBlockFlowerGen {

	private static final AxisAlignedBB[] AABB_BY_INDEX = new AxisAlignedBB[] {
			new AxisAlignedBB(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.34375D, 0.6875D),
			new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.5625D, 0.75D),
			new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.65625D, 0.8125D),
			new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.6875D, 0.875D),
			new AxisAlignedBB(0.09375D, 0.0D, 0.09375D, 0.90625D, 0.75D, 0.90625D),
			new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.75D, 0.9375D)};
	private static final PropertyInteger AGE = PropertyInteger.create("age", 0, 5);
	private static final PropertyBool CUT = PropertyBool.create("cut");

	public BlockCamellia() {
		super("camellia", Blocks.GRASS, "camellia_seed");
		this.setDefaultState(this.getDefaultState().withProperty(AGE, 0).withProperty(CUT, false));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return state.getValue(CUT) ? AABB_BY_INDEX[5] : AABB_BY_INDEX[this.getAge(state)];
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return (this.getAge(state) == 0)? NULL_AABB : this.getBoundingBox(state, worldIn, pos);
	}

	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE, CUT);
    }

    @Override
    protected PropertyInteger getAgeProperty() {
        return AGE;
    }

	@Override
	public int getMaxAge() {
		return 5;
	}

    protected Item getSeed()
    {
        return camellia_seed;
    }

    protected Item getCrop()
    {
        return camellia_leaves;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		drops.add(new ItemStack(Items.STICK, 1));
		drops.add(new ItemStack(Items.STICK, 1));
	    for (int i = (int) Math.floor((state.getValue(this.getAgeProperty()) + 1)/ 3.0d); i > 0; i--){
			drops.add(new ItemStack(this.getCrop(), 1));
		}
        if(this.isMaxAge(state)) {
			Random rand = new Random();
			if(rand.nextInt(4) == 0) drops.add(new ItemStack(this.getSeed(), 1));
        }
    }

	/**
	 * Called when the blocks is right clicked by a player.
	 */
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(this.isMaxAge(state)){
			if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) {
				ItemStack itemstack = playerIn.getHeldItem(hand);
				boolean holdShears = itemstack.getItem() == Items.SHEARS;
				if(holdShears) itemstack.damageItem(1, playerIn);
				for (int i = holdShears ? 3 : 2; i > 0; i--){
					spawnAsEntity(worldIn, pos, new ItemStack(this.getCrop(), 1));
				}
				Random rand = new Random();
				if(rand.nextInt(4) == 0) spawnAsEntity(worldIn, pos, new ItemStack(this.getSeed(), 1));
				worldIn.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
				worldIn.setBlockState(pos, state.withProperty(AGE, 3).withProperty(CUT, true));
				return true;
			}
		}
		return false;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.withAge(meta % 8).withProperty(CUT, meta >= 8);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return this.getAge(state) + ((state.getValue(CUT)) ? 8 : 0);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		this.checkAndDropBlock(worldIn, pos, state);

		if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
		if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
			int i = this.getAge(state);
			if (i < this.getMaxAge()) {
				float f = getGrowthChance(this, worldIn, pos);
				if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt((int)(25.0F / f) + 1) == 0)) {
					worldIn.setBlockState(pos, state.withProperty(AGE,i + 1), 2);
					net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
				}
			}
		}
	}

	@Override
	public List<String> getAcceptedBiomes() {
		return Arrays.asList(
				"mutated_forest",
				"jungle",
				"jungle_hills"
		);
	}

	@Override
	public void spawnInWorld(World world, BlockPos pos, Random rand) {
		if(world.getBlockState(pos.down()).getBlock() == Blocks.GRASS){
			world.setBlockState(pos, this.withAge(rand.nextInt(5)), 2);
		}
	}

	@Override
	public int getPatchSize() {
		return 5;
	}

	@Override
	public int getPatchChance() {
		return 10;
	}

	@Override
	public int getPatchQuantity() {
		return 2;
	}

	@Override
	public int getPatchDensity() {
		return 8;
	}
}
