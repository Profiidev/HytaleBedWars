package io.profidev.HytaleBedWars.commands;

import javax.annotation.Nonnull;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.NotificationUtil;

public class JoinCommand extends AbstractPlayerCommand {
  public JoinCommand() {
    super("join", "Join a BedWars game");
  }

  @Override
  protected void execute(@Nonnull CommandContext ctx, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref,
      @Nonnull PlayerRef playerRef, @Nonnull World world) {
    var universe = Universe.get();
    var bedWarsWorld = universe.getWorld(BWCommand.WORLD_NAME);
    if (bedWarsWorld == null) {
      NotificationUtil.sendNotification(playerRef.getPacketHandler(),
          Message.raw("No BedWars game is currently running."));
      return;
    }

    var teleport = new Teleport(bedWarsWorld, new Vector3d(0.5, 201, 0.5), new Vector3f());
    world.execute(() -> {
      store.addComponent(ref, Teleport.getComponentType(), teleport);
    });

    NotificationUtil.sendNotification(playerRef.getPacketHandler(), Message.raw("Teleported to BedWars game!"));
  }
}
