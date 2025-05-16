package carpet_extension;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.settings.SettingsManager;
import carpet.utils.Messenger;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class ExampleExtension implements CarpetExtension
{
    public static void noop() { }
    private static SettingsManager mySettingManager;
    static
    {
        mySettingManager = new SettingsManager("1.0","examplemod","Example Mod");
        CarpetServer.manageExtension(new ExampleExtension());
    }

    @Override
    public void onGameStarted()
    {
        // let /carpet handle our simple settings
        CarpetServer.settingsManager.parseSettingsClass(ExampleSimpleSettings.class);
        // our own independent settings
        mySettingManager.parseSettingsClass(ExampleOwnSettings.class);

        // observe rule changes
        // CarpetServer.settingsManager.addRuleObserver( (source, currentRuleState, original) ->
        // {
        //     if (currentRuleState.categories.contains("examplemod"))
        //     {
        //         Messenger.m(
        //                 source,
        //                 "gi Psssst... make sure not to touch original carpet rules"
        //         );
        //     }
        //     else
        //     {
        //         try
        //         {
        //             Messenger.print_server_message(
        //                     source.getMinecraftServer(),
        //                     "Ehlo everybody, "+source.getPlayer().getName().getString()+" is cheating..."
        //             );
        //         }
        //         catch (CommandSyntaxException ignored) { }
        //     }
        // });
    }

    @Override
    public void onServerLoaded(MinecraftServer server)
    {
        // reloading handled by carpet / our own manager
    }

    @Override
    public void onTick(MinecraftServer server) { }

    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        ExampleCommand.register(dispatcher);
    }

    @Override
    public SettingsManager customSettingsManager()
    {
        return mySettingManager;
    }

    @Override
    public void onPlayerLoggedIn(ServerPlayerEntity player) { }

    @Override
    public void onPlayerLoggedOut(ServerPlayerEntity player) { }
}
