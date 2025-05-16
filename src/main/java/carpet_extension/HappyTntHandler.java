package carpet_extension;

import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public final class HappyTntHandler {
    private HappyTntHandler() {} // no instantiation

    /** Must be called once on startup */
    public static void register() {
        ServerMessageEvents.ALLOW_CHAT_MESSAGE.register((message, sender, params) -> {
            if (!ExampleSimpleSettings.happyTNT) return true;              // feature off
            // use getContent() now
            if (!"!!tnt".equalsIgnoreCase(message.getContent().getString())) return true;

            // 直接使用TitleS2CPacket构造函数
            // 发送主标题和空副标题
            sender.networkHandler.sendPacket(new TitleS2CPacket(Text.literal("Hahaha…")));

            return false;  // consume the chat packet
        });
    }
}