package com.blakebr0.cucumber.network.messages;

import com.blakebr0.cucumber.guide.GuideBookHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageUpdateGuideNBT {

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

	public static MessageUpdateGuideNBT read(PacketBuffer buf) {
		return new MessageUpdateGuideNBT(buf.readInt(), buf.readInt(), buf.readInt());
	}

	public static void write(MessageUpdateGuideNBT message, PacketBuffer buf) {
		buf.writeInt(message.topicsPage);
		buf.writeInt(message.entryPage);
		buf.writeInt(message.entryId);
	}

	public static void handle(MessageUpdateGuideNBT message, Supplier<NetworkEvent.Context> ctx) {
		EntityPlayer player = ctx.get().getSender();
		if (player != null) {
			ctx.get().enqueueWork(() -> {
				ItemStack stack = player.getHeldItem(player.getActiveHand());

				GuideBookHelper.setTopicsPage(stack, message.topicsPage);
				GuideBookHelper.setEntryPage(stack, message.entryPage);
				GuideBookHelper.setEntryId(stack, message.entryId);
			});
		}

		ctx.get().setPacketHandled(true);
	}
}
