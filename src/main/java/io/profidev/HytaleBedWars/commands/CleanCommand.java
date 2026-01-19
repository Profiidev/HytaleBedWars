package io.profidev.HytaleBedWars.commands;

import javax.annotation.Nonnull;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.NotificationUtil;

public class CleanCommand extends AbstractPlayerCommand {
  public CleanCommand() {
    super("clean", "Clean up the BedWars world");

    this.requirePermission("bw.clean");
  }

  @Override
  protected void execute(@Nonnull CommandContext ctx, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref,
      @Nonnull PlayerRef playerRef, @Nonnull World world) {
    NotificationUtil.sendNotification(playerRef.getPacketHandler(), Message.raw("Cleaning up BedWars world..."));

    var universe = Universe.get();
    universe.removeWorld(BWCommand.WORLD_NAME);

    NotificationUtil.sendNotification(playerRef.getPacketHandler(),
        Message.raw("BedWars world cleaned up successfully!"));
  }
}
