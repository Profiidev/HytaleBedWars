package io.profidev.HytaleBedWars.events;

import javax.annotation.Nonnull;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.AddPlayerToWorldEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import io.profidev.HytaleBedWars.TestHud;
import io.profidev.HytaleBedWars.commands.BWCommand;

public class PlayerHudEvent {
  private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

  public static void onAddPlayerToWorld(@Nonnull AddPlayerToWorldEvent event) {
    var playerRef = event.getHolder().getComponent(PlayerRef.getComponentType());
    var player = event.getHolder().getComponent(Player.getComponentType());
    if (playerRef == null || player == null) {
      LOGGER.atWarning().log("PlayerRef component is missing for entity");
      return;
    }

    var hudManager = player.getHudManager();

    if (event.getWorld().getName().equals(BWCommand.WORLD_NAME)) {
      hudManager.setCustomHud(playerRef, new TestHud(playerRef));
    } else {
      hudManager.setCustomHud(playerRef, null);
    }
  }
}
