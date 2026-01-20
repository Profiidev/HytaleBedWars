package io.profidev.HytaleBedWars;

import javax.annotation.Nonnull;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.AddPlayerToWorldEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import io.profidev.HytaleBedWars.commands.BWCommand;
import io.profidev.HytaleBedWars.events.PlayerHudEvent;

public class HytaleBedWars extends JavaPlugin {

  private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

  public HytaleBedWars(@Nonnull JavaPluginInit init) {
    super(init);
  }

  @Override
  protected void setup() {
    LOGGER.atInfo().log("Hytale Plugin Template is setting up!");
    this.getCommandRegistry().registerCommand(new BWCommand(this));
    this.getCommandRegistry().registerCommand(new UICommand());
    this.getEventRegistry().registerGlobal(AddPlayerToWorldEvent.class, PlayerHudEvent::onAddPlayerToWorld);
  }
}
