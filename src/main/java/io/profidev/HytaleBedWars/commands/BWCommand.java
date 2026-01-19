package io.profidev.HytaleBedWars.commands;

import javax.annotation.Nonnull;

import com.hypixel.hytale.server.core.command.system.basecommands.AbstractCommandCollection;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;

public class BWCommand extends AbstractCommandCollection {
  public static final String WORLD_NAME = "bed_wars_world";

  public BWCommand(@Nonnull JavaPlugin plugin) {
    super("bw", "Command to manage BedWars");

    this.addSubCommand(new CreateCommand(plugin));
    this.addSubCommand(new CleanCommand());
    this.addSubCommand(new JoinCommand());
    this.addSubCommand(new LeaveCommand());
  }
}
