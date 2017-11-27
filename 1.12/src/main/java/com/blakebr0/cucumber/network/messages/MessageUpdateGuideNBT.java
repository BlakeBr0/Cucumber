package com.blakebr0.cucumber.network.messages;


import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageUpdateGuideNBT implements IMessage {

	private int page;

	public MessageUpdateGuideNBT() {
		
	}

	public MessageUpdateGuideNBT(int page) {
		this.page = page;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.page = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(page);
	}

	public static class Handler implements IMessageHandler<MessageUpdateGuideNBT, IMessage> {

		@Override
		public IMessage onMessage(MessageUpdateGuideNBT message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(MessageUpdateGuideNBT message, MessageContext ctx) {
			ItemStack stack = ctx.getServerHandler().player.getHeldItem(ctx.getServerHandler().player.getActiveHand());
			stack.getTagCompound().setInteger("Page", message.page); // TODO: SKRRR
		}
	}
}
