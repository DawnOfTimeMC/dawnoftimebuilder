package org.dawnoftimebuilder.blocks.japanese;

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
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeTaiga;
import org.dawnoftimebuilder.blocks.IBlockFlowerGen;
import org.dawnoftimebuilder.blocks.general.DoTBBlockDoubleCrops;
import org.dawnoftimebuilder.entities.EntitySilkmoth;
import org.dawnoftimebuilder.enums.EnumsBlock;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.dawnoftimebuilder.items.DoTBItems.*;

public class BlockMulberry extends DoTBBlockDoubleCrops implements IBlockFlowerGen {

	private static final AxisAlignedBB AABB_LEVEL_0 = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.5D, 0.625D);
	private static final AxisAlignedBB AABB_LEVEL_1 = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
	private static final AxisAlignedBB AABB_BOTTOM = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
	private static final AxisAlignedBB AABB_LEVEL_2 = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.3125D, 0.75D);
	private static final AxisAlignedBB AABB_LEVEL_3 = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.6875D, 1.0D);

	private static final PropertyInteger AGE_LOWER = PropertyInteger.create("age", 0, 4);
	private static final PropertyBool CUT = PropertyBool.create("cut");

	public BlockMulberry() {
		super("mulberry", Blocks.GRASS);
		this.setDefaultState(this.getDefaultState().withProperty(AGE_LOWER, 0).withProperty(CUT, false).withProperty(HALF, EnumsBlock.EnumHalf.BOTTOM));
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		if(!worldIn.isRemote){
			int dayTime = (int) (worldIn.getWorldTime() % 23999);
			int bound = 900;
			if(dayTime >= 12000){
				if(dayTime <= 20000) bound /= 6;
				if(dayTime <= 16000) bound /= 2;
				if(dayTime <= 13800) bound /= 5;
			}
			if(rand.nextInt(bound) == 0){
				if(!this.isBottomCrop(worldIn, pos)){
					BlockPos spawnPos = new BlockPos(pos.getX() - 3 + rand.nextInt(7), pos.getY() - 1 + rand.nextInt(3), pos.getZ() - 3 + rand.nextInt(7));
					if(worldIn.getBlockState(spawnPos).getCollisionBoundingBox(worldIn, spawnPos) == NULL_AABB){
						EntitySilkmoth entity = new EntitySilkmoth(worldIn);
						entity.setPosition(spawnPos.getX() + 0.5D, spawnPos.getY() + 0.5D, spawnPos.getZ() + 0.5D);
						worldIn.spawnEntity(entity);
					}
				}
			}
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if(state.getValue(HALF) == EnumsBlock.EnumHalf.TOP){
			switch (this.getAge(state)) {
				default:
				case 2:
					return AABB_LEVEL_2;

				case 3:
					return state.getValue(CUT) ? FULL_BLOCK_AABB : AABB_LEVEL_3;

				case 4:
					return FULL_BLOCK_AABB;
			}
		}else{
			switch (this.getAge(state)) {
				case 0:
					return AABB_LEVEL_0;

				case 1:
					return AABB_LEVEL_1;

				default:
				case 2:
					return AABB_BOTTOM;
			}
		}
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return (this.getAge(state) == 0)? NULL_AABB : this.getBoundingBox(state, worldIn, pos);
	}

	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE_LOWER, CUT, HALF);
    }

	@Override
	public int getAgeReachingTopBlock() {
		return 2;
	}

	@Override
    protected PropertyInteger getAgeProperty() {
        return AGE_LOWER;
    }

	@Override
	public IBlockState setAge(IBlockState state, int age) {
		return state.withProperty(AGE_LOWER, age);
	}

	@Override
	public int getMaxAge() {
		return 4;
	}

    protected Item getSeed()
    {
        return mulberry;
    }

    protected Item getCrop()
    {
        return mulberry_leaves;
    }

    @Override
    public void getDoubleCropsDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		drops.add(new ItemStack(Items.STICK, 1));
		drops.add(new ItemStack(Items.STICK, 1));
	    for (int i = (int) Math.floor((state.getValue(this.getAgeProperty()) + 1)/ 3.0d); i > 0; i--){
			drops.add(new ItemStack(this.getCrop(), 1));
		}
        if(this.isMaxAge(state)) {
			Random rand = new Random();
			if(rand.nextInt(2) == 0) drops.add(new ItemStack(this.getSeed(), 1));
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
				if(rand.nextInt(2) == 0) spawnAsEntity(worldIn, pos, new ItemStack(this.getSeed(), 1));
				worldIn.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
				worldIn.setBlockState(pos, state.withProperty(AGE_LOWER, 3).withProperty(CUT, true));
				if(state.getValue(HALF) == EnumsBlock.EnumHalf.BOTTOM){
					worldIn.setBlockState(pos.up(), state.withProperty(AGE_LOWER, 3).withProperty(CUT, true).withProperty(HALF, EnumsBlock.EnumHalf.TOP));
				}else{
					worldIn.setBlockState(pos.down(), state.withProperty(AGE_LOWER, 3).withProperty(CUT, true).withProperty(HALF, EnumsBlock.EnumHalf.BOTTOM));
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState();
		if(meta >= 8){
			state = state.withProperty(HALF, EnumsBlock.EnumHalf.TOP);
			meta -= 8;
		}
		if(meta == 5){
			state = state.withProperty(CUT, true);
			meta = 3;
		}
		return state.withProperty(AGE_LOWER, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = this.getAge(state);
		if(state.getValue(CUT)) meta = 5;
		if(state.getValue(HALF) == EnumsBlock.EnumHalf.TOP) meta += 8;
		return meta;
	}

	@Override
	public List<String> getAcceptedBiomes() {
		return Arrays.asList(
				"taiga_hills",
				"taiga",
				"redwood_taiga",
				"redwood_taiga_hills",
				"mutated_forest"
		);
	}

	@Override
	public void spawnInWorld(World world, BlockPos pos, Random rand) {
		if(world.getBlockState(pos.down()).getBlock() == Blocks.GRASS){
			boolean canGrow = world.getBlockState(pos.up()).getBlock() == Blocks.AIR;
			if(canGrow){
				int age = rand.nextInt(5);
				world.setBlockState(pos, this.withAge(age).withProperty(HALF, EnumsBlock.EnumHalf.BOTTOM), 2);
				if(age >= this.getAgeReachingTopBlock()) world.setBlockState(pos.up(), this.withAge(age).withProperty(HALF, EnumsBlock.EnumHalf.TOP), 2);
			}else world.setBlockState(pos, this.withAge(rand.nextInt(2)));
		}
	}

	@Override
	public int getPatchSize() {
		return 6;
	}

	@Override
	public int getPatchChance() {
		return 30;
	}

	@Override
	public int getPatchQuantity() {
		return 2;
	}

	@Override
	public int getPatchDensity() {
		return 4;
	}
}
