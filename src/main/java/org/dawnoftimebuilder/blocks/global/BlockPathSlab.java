package org.dawnoftimebuilder.blocks.global;

import java.util.Random;

import org.dawnoftimebuilder.blocks.IBlockMeta;
import org.dawnoftimebuilder.blocks.vanilla.SlabWrapperBlock;
import org.dawnoftimebuilder.enums.IEnumMetaVariants;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockPathSlab extends SlabWrapperBlock implements IBlockMeta {
    public static final PropertyBool SEAMLESS = PropertyBool.create("seamless");
    public static final PropertyEnum<BlockPathSlab.EnumType> VARIANT = PropertyEnum.<BlockPathSlab.EnumType>create("variant", BlockPathSlab.EnumType.class);
    public static final PropertyBool FULL = PropertyBool.create("full");
    
    public BlockPathSlab(String name)
    {
        super(name, Material.GROUND, true);
        
        this.useNeighborBrightness = true;
        IBlockState iblockstate = this.blockState.getBaseState();

        if (this.isDouble())
        {
            iblockstate = iblockstate.withProperty(SEAMLESS, Boolean.valueOf(false));
        }
        else
        {
            iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
        }

        this.setDefaultState(iblockstate.withProperty(VARIANT, BlockPathSlab.EnumType.GRAVEL).withProperty(FULL, Boolean.valueOf(false)));
        
        this.setSoundType(SoundType.GROUND);
        this.setHardness(0.5F);

    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    /**
     * Returns the slab blocks name with the type associated with it
     */
    @Override
    public String getTranslationKey(int meta)
    {
        return super.getTranslationKey() + "." + BlockPathSlab.EnumType.byMetadata(meta).getTranslationKey();
    }
    
    @Override
    public IProperty<?> getVariantProperty()
    {
        return VARIANT;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return BlockPathSlab.EnumType.byMetadata(stack.getMetadata() & 7);
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for (BlockPathSlab.EnumType blockstoneslab$enumtype : BlockPathSlab.EnumType.values())
        {
        	items.add(new ItemStack(this, 1, blockstoneslab$enumtype.getMetadata()));

        }
    }


    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, BlockPathSlab.EnumType.byMetadata(meta & 7));

        if (this.isDouble())
        {
            iblockstate = iblockstate.withProperty(SEAMLESS, Boolean.valueOf((meta & 8) != 0));
        }
        else
        {
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }

        return iblockstate;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((BlockPathSlab.EnumType)state.getValue(VARIANT)).getMetadata();

        if (this.isDouble())
        {
            if (((Boolean)state.getValue(SEAMLESS)).booleanValue())
            {
                i |= 8;
            }
        }
        else if (state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
        {
            i |= 8;
        }

        return i;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    
    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_)
    {
        if (((BlockSlab)p_193383_2_.getBlock()).isDouble())
        {
            return BlockFaceShape.SOLID;
        }
        else if (p_193383_4_ == EnumFacing.UP && p_193383_2_.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
        {
            return BlockFaceShape.SOLID;
        }
        else
        {
            return p_193383_4_ == EnumFacing.DOWN && p_193383_2_.getValue(HALF) == BlockSlab.EnumBlockHalf.BOTTOM ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
        }
    }
    protected BlockStateContainer createBlockState()
    {
        return this.isDouble() ? new BlockStateContainer(this, new IProperty[] {SEAMLESS, VARIANT, FULL}) : new BlockStateContainer(this, new IProperty[] {HALF, VARIANT, FULL});
    }

	@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state.withProperty(FULL, Boolean.valueOf(this.isFull(worldIn, pos)));
    }
	
    private boolean isFull(IBlockAccess worldIn, BlockPos pos) {
//    	
    	Block block = worldIn.getBlockState(pos.up()).getBlock();
    	
    	return !(block instanceof BlockAir || block instanceof BlockLeaves || block instanceof BlockBush);
    	
	}
    
    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
    	boolean full = state.getValue(FULL);
    	boolean isDouble = this.isDouble();
    	
    	if(isDouble){
    	
	    	if(full && face != EnumFacing.UP){
	    		
	    		return false;
	    		
	    	}else if(face == EnumFacing.DOWN){
	    		
	    		return false;
	    		
	    	}
    	
    	}else{
    		
    		BlockSlab.EnumBlockHalf half = state.getValue(HALF);
    		
    		if(half == BlockSlab.EnumBlockHalf.BOTTOM){
    			
    			if(face == EnumFacing.DOWN){
    				
    				return false;
    				
    			}
    		}else{
    			
    			if(face == EnumFacing.UP && full){
    				
    				return false;
    				
    			}
    			
    		}
    	}
    	
    	return false;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return (state.getValue(VARIANT)).getMetadata();
    }

    @Override
    public IEnumMetaVariants[] getVariants() {
        return EnumType.values();
    }

    public static enum EnumType implements IEnumMetaVariants
    {
        GRAVEL(0, MapColor.GRAY ,"gravel"),
        STEPPING_STONES(1, MapColor.STONE, "stepping_stones"),
        COBBLED(2, MapColor.STONE, "cobbled"),
        SANDSTONE(3, MapColor.SAND, "sandstone"),
        OCHRE_TILES(4, MapColor.RED, "ochre_tiles"),
        DIRT(5, MapColor.DIRT, "dirt");

        private static final BlockPathSlab.EnumType[] META_LOOKUP = new BlockPathSlab.EnumType[values().length];
        private final int meta;
        private final MapColor color;
        private final String name;
        private final String unlocalizedName;

        private EnumType(int meta, MapColor color, String name)
        {
        	this.color = color;
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = name;
        }

        public int getMetadata()
        {
            return this.meta;
        }

        public MapColor getMapColor()
        {
            return this.color;
        }

        public String toString()
        {
            return this.name;
        }

        public static BlockPathSlab.EnumType byMetadata(int meta)
        {
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        public String getName()
        {
            return this.name;
        }

        public String getTranslationKey()
        {
            return this.unlocalizedName;
        }

        static
        {
            for (BlockPathSlab.EnumType blockstoneslab$enumtype : values())
            {
                META_LOOKUP[blockstoneslab$enumtype.getMetadata()] = blockstoneslab$enumtype;
            }
        }
    }
}