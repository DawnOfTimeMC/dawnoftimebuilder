package org.dawnoftimebuilder.blocks.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.blocks.global.DoTBBlock;

import static org.dawnoftimebuilder.blocks.DoTBBlocks.cast_iron_teapot;
import static org.dawnoftimebuilder.blocks.DoTBBlocks.iron_chain;

public class BlockFirepit extends DoTBBlock {

    private static final AxisAlignedBB HALF_BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    private static final AxisAlignedBB TEAPOT_AABB = new AxisAlignedBB(0.3D, 0.6D, 0.3D, 0.7D, 1.0D, 0.7D);
    private static PropertyBool LIT = PropertyBool.create("lit");
    private static PropertyInteger DESIGN = PropertyInteger.create("design", 0, BlockCastIronTeapot.EnumType.values().length);

    public BlockFirepit() {
        super("irori_firepit", Material.SAND);
        this.setDefaultState(this.getDefaultState().withProperty(LIT, false).withProperty(DESIGN, 0));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if((player.getHeldItem(hand).getItem() == Item.getItemFromBlock(Blocks.TORCH) || player.getHeldItem(hand).getItem() == Items.FLINT_AND_STEEL) && !world.getBlockState(pos).getValue(LIT)) {
            world.setBlockState(pos, state.withProperty(LIT, true), 3);
            return true;
        }

        if(player.getHeldItem(hand).getItem() == Items.WATER_BUCKET && world.getBlockState(pos).getValue(LIT)) {
            world.setBlockState(pos, state.withProperty(LIT, false), 3);
            return true;
        }

        if(player.getHeldItem(hand).getItem() == Item.getItemFromBlock(cast_iron_teapot) && world.getBlockState(pos).getValue(DESIGN) == 0){
            if(world.getBlockState(pos.up()).getBlock() == iron_chain) {
                player.getHeldItem(hand).shrink(1);
                world.setBlockState(pos, state.withProperty(DESIGN, player.getHeldItem(hand).getMetadata() + 1), 3);
            }
            return true;
        }

        if (player.getHeldItem(hand).isEmpty() && world.getBlockState(pos).getValue(DESIGN) > 0) {
            spawnTeapotInWorld(world, pos, state.getValue(DESIGN) - 1);
            world.setBlockState(pos, state.withProperty(DESIGN, 0), 3);
            return true;
        }

        return false;
    }

    private void spawnTeapotInWorld(World world, BlockPos pos, int design){
        if(!world.isRemote) {
            EntityItem item = new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Item.getItemFromBlock(cast_iron_teapot), 1, design));
            world.spawnEntity(item);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
        if(pos.up().equals(fromPos) && state.getValue(DESIGN) > 0){
            spawnTeapotInWorld(world, pos, state.getValue(DESIGN) - 1);
            world.setBlockState(pos, state.withProperty(DESIGN, 0), 3);
        }
    }

    @Deprecated
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        if(state.getValue(DESIGN) > 0) {
            return HALF_BLOCK_AABB.union(TEAPOT_AABB);
        }
        return HALF_BLOCK_AABB;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return state.getValue(LIT) ? 14 : 0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        boolean isOn = false;
        if(meta >= 10) {
            meta -= 10;
            isOn = true;
        }
        return this.getDefaultState().withProperty(LIT, isOn).withProperty(DESIGN, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int designNumber =state.getValue(DESIGN);
        int onValue = state.getValue(LIT) ? 10 : 0;
        return designNumber + onValue;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return (new BlockStateContainer.Builder(this)).add(LIT).add(DESIGN).build();
    }

    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        return BlockFaceShape.UNDEFINED;
    }
}
