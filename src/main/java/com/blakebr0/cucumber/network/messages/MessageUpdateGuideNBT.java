package com.blakebr0.cucumber.network.messages;


import com.blakebr0.cucumber.guide.GuideBookHelper;
import com.blakebr0.cucumber.helper.NBTHelper;

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

	private int topicsPage;
	private int entryPage;
	private int entryId;

	public MessageUpdateGuideNBT() {
		
	}

	public MessageUpdateGuideNBT(int topicsPage, int entryPage, int entryId) {
		this.topicsPage = topicsPage;
		this.entryPage = entryPage;
		this.entryId = entryId;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.topicsPage = buf.readInt();
		this.entryPage = buf.readInt();
		this.entryId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.topicsPage);
		buf.writeInt(this.entryPage);
		buf.writeInt(this.entryId);
	}

	public static class Handler implements IMessageHandler<MessageUpdateGuideNBT, IMessage> {

		@Override
		public IMessage onMessage(MessageUpdateGuideNBT message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(MessageUpdateGuideNBT message, MessageContext ctx) {
			ItemStack stack = ctx.getServerHandler().player.getHeldItem(ctx.getServerHandler().player.getActiveHand());
			
			GuideBookHelper.setTopicsPage(stack, message.topicsPage);
			GuideBookHelper.setEntryPage(stack, message.entryPage);
			GuideBookHelper.setEntryId(stack, message.entryId);
		}
	}
}
