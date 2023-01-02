package org.dawnoftimebuilder.block.japanese;

import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import org.dawnoftimebuilder.block.templates.PaneBlockDoTB;

public class CharredSpruceFancyRailingBlock extends PaneBlockDoTB {

    private static final BooleanProperty HANGING = BlockStateProperties.HANGING;

    public CharredSpruceFancyRailingBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, false).setValue(HANGING, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction clickedFace = context.getClickedFace();
        BlockPos pos = context.getClickedPos();
        BlockState state = super.getStateForPlacement(context);
        if(state == null) state = this.defaultBlockState();
        return state.setValue(HANGING, clickedFace == Direction.DOWN || (clickedFace != Direction.UP && context.getClickLocation().y - pos.getY() > 0.5D));
    }


}
