/*
package org.dawnoftimebuilder.network;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import org.dawnoftimebuilder.DawnOfTimeBuilder;
import org.dawnoftimebuilder.tileentity.DisplayerTileEntity;

import java.util.function.Supplier;

public class DisplayerMessage implements IMessage<DisplayerMessage> {

	private int dimension;

	private BlockPos pos;

	private NonNullList<ItemStack> stacks;

	public DisplayerMessage(){}

	public DisplayerMessage(int dimension, BlockPos pos, NonNullList<ItemStack> stacks){
		this.dimension = dimension;
		this.pos = pos;
		this.stacks = stacks;
	}

	@Override
	public void encode(DisplayerMessage message, PacketBuffer buffer) {
		buffer.writeInt(message.dimension);
		buffer.writeInt(message.pos.getX());
		buffer.writeInt(message.pos.getY());
		buffer.writeInt(message.pos.getZ());
		for (ItemStack stack : message.stacks){
			buffer.writeItemStack(stack);
		}
	}

	@Override
	public DisplayerMessage decode(PacketBuffer buffer) {
		int dimension = buffer.readInt();
		BlockPos pos = new BlockPos(buffer.readInt(), buffer.readInt(), buffer.readInt());

		NonNullList<ItemStack> stacks = NonNullList.withSize(9, ItemStack.EMPTY);
		for (int i = 0; i < 9; i++){
			ItemStack itemStack = buffer.readItemStack();
			stacks.set(i, itemStack);
		}

		return new DisplayerMessage(dimension, pos, stacks);
	}

	@Override
	public void handle(DisplayerMessage message, Supplier<NetworkEvent.Context> supplier) {
		supplier.get().enqueueWork(() -> {
			World world = DawnOfTimeBuilder.PROXY.getClientWorld();
			if(world != null){
				TileEntity te = world.getTileEntity(message.pos);
				if(te instanceof DisplayerTileEntity){
					((DisplayerTileEntity) te).receiveMessageFromServer(message.stacks);
				}
			}
		});
		supplier.get().setPacketHandled(true);
	}
}
*/