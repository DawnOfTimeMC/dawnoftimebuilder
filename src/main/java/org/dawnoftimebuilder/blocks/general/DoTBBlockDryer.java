package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;
import org.dawnoftimebuilder.items.IItemCanBeDried;
import org.dawnoftimebuilder.items.general.DoTBItemDryer;
import org.dawnoftimebuilder.tileentity.DoTBTileEntityDryer;

public class DoTBBlockDryer extends DoTBBlockTileEntity implements IBlockCustomItem {

	public static final PropertyBool SIMPLE = PropertyBool.create("simple");
	private static final PropertyBool VERTICAL_CONNECTION = PropertyBool.create("vertical_connection");

	private static final AxisAlignedBB AABB_SIMPLE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
	private static final AxisAlignedBB AABB_DOUBLE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

	public DoTBBlockDryer(String name, Material materialIn, float hardness, SoundType sound) {
		super(name, materialIn, hardness, sound);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SIMPLE, true).withProperty(VERTICAL_CONNECTION, false));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new DoTBTileEntityDryer();
	}

	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SIMPLE, VERTICAL_CONNECTION);
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies properties not visible in the
	 * metadata, such as fence connections.
	 */
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if(state.getValue(SIMPLE)) return state.withProperty(VERTICAL_CONNECTION, false);
		else return state.withProperty(VERTICAL_CONNECTION, worldIn.getBlockState(pos.up()).getBlock() instanceof DoTBBlockDryer);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)  {
		return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
	}

	private boolean canBlockStay(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos.down());
		if(state.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID) return true;
		else if(state.getBlock() instanceof DoTBBlockDryer){
			return !state.getValue(SIMPLE);
		}else return false;
	}

	@Override
	protected boolean canSilkHarvest()
	{
		return false;
	}

	/**
	 * Called when a neighboring blocks was changed and marks that this state should perform any checks during a neighbor
	 * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
	 * blocks, etc.
	 */
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(!canBlockStay(worldIn, pos)){
			IBlockState iblockstate = worldIn.getBlockState(pos);

			if (!iblockstate.getBlock().isAir(iblockstate, worldIn, pos)){
				worldIn.playEvent(2001, pos, Block.getStateId(iblockstate));

				spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this), 1));
				if(!state.getValue(SIMPLE)) spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this), 1));

				worldIn.setBlockToAir(pos);
			}
		}
	}

	@Override
	public void getDrops(net.minecraft.util.NonNullList<ItemStack> drops, net.minecraft.world.IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		drops.add(new ItemStack(Item.getItemFromBlock(this), 1));
		if(!state.getValue(SIMPLE)) drops.add(new ItemStack(Item.getItemFromBlock(this), 1));
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
		TileEntity tE = worldIn.getTileEntity(pos);
		if(tE instanceof DoTBTileEntityDryer) {
			DoTBTileEntityDryer tileEntity = (DoTBTileEntityDryer) tE;
			//Two times to drop the two items.
			tileEntity.dropOneItem(worldIn, pos);
			tileEntity.dropOneItem(worldIn, pos);
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public int getMetaFromState(IBlockState state){
		return state.getValue(SIMPLE) ? 0 : 1;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(SIMPLE, meta == 0);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
		if(stack.hasDisplayName()){
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if(tileEntity instanceof TileEntityDispenser) ((TileEntityDispenser)tileEntity).setCustomName(stack.getDisplayName());
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(!worldIn.isRemote && !worldIn.restoringBlockSnapshots) {
			TileEntity tE = worldIn.getTileEntity(pos);
			if(tE instanceof DoTBTileEntityDryer) {
				DoTBTileEntityDryer tileEntity = (DoTBTileEntityDryer) tE;

				if(playerIn.isSneaking()) return tileEntity.dropOneItem(worldIn, pos);

				else {
					ItemStack itemstack = playerIn.getHeldItem(hand);
					Item item = itemstack.getItem();

					if(item instanceof IItemCanBeDried) {
						int quantityNeeded = ((IItemCanBeDried) item).getItemQuantity();
						if(quantityNeeded <= itemstack.getCount()){
							if(tileEntity.putUndriedItem((IItemCanBeDried) item, state.getValue(SIMPLE), worldIn, pos)) {
								if(!playerIn.isCreative()) itemstack.shrink(quantityNeeded);
								return true;
							}else return false;
						}
					}
					return tileEntity.dropOneDriedItem(worldIn, pos) >= 0;
				}
			}
		}
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		state = this.getActualState(state, worldIn, pos);
		if(state.getValue(SIMPLE)) return AABB_SIMPLE;
		else return (state.getValue(VERTICAL_CONNECTION)) ? FULL_BLOCK_AABB : AABB_DOUBLE;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing side) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public Item getCustomItemBlock() {
		return new DoTBItemDryer(this)
				.setTranslationKey(this.getTranslationKey())
				.setRegistryName(this.getRegistryName());
	}
}
