package xyz.eclipseisoffline.capecommand.neoforge;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.event.RegisterConfigurationTasksEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import xyz.eclipseisoffline.capecommand.CapeCommand;

import java.nio.file.Path;
import java.util.function.Consumer;

@Mod(CapeCommand.MOD_ID)
public class CapeCommandNeoForge extends CapeCommand {
    private final IEventBus modBus;

    public CapeCommandNeoForge(IEventBus bus, Dist dist) {
        modBus = bus;
        initialize();
        if (dist.isClient()) {
            initializeClient();
        }
    }

    @Override
    protected void registerCommands(Consumer<CommandDispatcher<CommandSourceStack>> registerer) {
        NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, event -> registerer.accept(event.getDispatcher()));
    }

    @Override
    protected void registerConfigurationNetworkingHandler(Consumer<ServerConfigurationPacketListenerImpl> handler) {
        modBus.addListener(RegisterConfigurationTasksEvent.class, event -> handler.accept((ServerConfigurationPacketListenerImpl) event.getListener()));
    }

    @Override
    protected <T extends CustomPacketPayload> void registerClientboundConfigurationCustomPayloadType(CustomPacketPayload.Type<T> type, StreamCodec<? super FriendlyByteBuf, T> codec) {
        modBus.addListener(RegisterPayloadHandlersEvent.class, event -> event.registrar("0")
                .optional()
                .configurationToClient(type, codec, (payload, context) -> {}));
    }

    @Override
    protected boolean canSendCustomPayload(ServerConfigurationPacketListenerImpl configurationPacketListener, CustomPacketPayload.Type<?> type) {
        return configurationPacketListener.hasChannel(type);
    }

    @Override
    protected <T extends CustomPacketPayload> void registerClientboundCustomPayloadHandler(CustomPacketPayload.Type<T> type, Consumer<T> handler) {
        // The configuration payload handler is registered together with its codec above.
        // NeoForge 1.21.1 does not expose a separate client payload event.
    }

    @Override
    protected Path getConfigDir() {
        return FMLPaths.GAMEDIR.get().resolve("config");
    }
}
