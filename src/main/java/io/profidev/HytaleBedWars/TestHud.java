package io.profidev.HytaleBedWars;

import javax.annotation.Nonnull;

import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;

public class TestHud extends CustomUIHud {
  private final boolean clear;

  public TestHud(@Nonnull PlayerRef playerRef) {
    super(playerRef);
    this.clear = false;
  }

  public TestHud(@Nonnull PlayerRef playerRef, boolean clear) {
    super(playerRef);
    this.clear = clear;
  }

  @Override
  protected void build(@Nonnull UICommandBuilder uiCommandBuilder) {
    if (clear) {
      return;
    }
    uiCommandBuilder.append("Test.ui");
  }
}
