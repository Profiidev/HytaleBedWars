package io.profidev.HytaleBedWars.commands;

import java.nio.file.Files;
import java.util.Random;

import javax.annotation.Nonnull;

import com.hypixel.hytale.builtin.hytalegenerator.assets.props.prefabprop.PrefabLoader;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.AssetModule;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.WorldConfig;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.universe.world.worldgen.provider.VoidWorldGenProvider;
import com.hypixel.hytale.server.core.util.NotificationUtil;
import com.hypixel.hytale.server.core.util.PrefabUtil;

public class CreateCommand extends AbstractPlayerCommand {
  private static final String SPAWN_PREFAB_PATH = "Server/Prefabs/Spawn.prefab.json";

  @Nonnull
  private final JavaPlugin plugin;

  public CreateCommand(@Nonnull JavaPlugin plugin) {
    super("create", "Create a new BedWars game");

    this.requirePermission("bw.create");
    this.plugin = plugin;
  }

  @Override
  protected void execute(@Nonnull CommandContext ctx, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref,
      @Nonnull PlayerRef playerRef, @Nonnull World defaultWorld) {
    NotificationUtil.sendNotification(playerRef.getPacketHandler(), Message.raw("Creating new BedWars game..."));

    var universe = Universe.get();
    if (universe.getWorld(BWCommand.WORLD_NAME) != null) {
      NotificationUtil.sendNotification(playerRef.getPacketHandler(), Message.raw("A BedWars world already exists!"));
      return;
    }

    var config = new WorldConfig();
    config.setWorldGenProvider(new VoidWorldGenProvider());

    var path = universe.getPath().resolve("worlds").resolve(BWCommand.WORLD_NAME);
    path.toFile().mkdirs();

    World world;
    try {
      world = universe.makeWorld(BWCommand.WORLD_NAME, path, config).get();
    } catch (Exception e) {
      NotificationUtil.sendNotification(playerRef.getPacketHandler(),
          Message.raw("Failed to create world: " + e.getMessage()));
      return;
    }
    if (world == null) {
      NotificationUtil.sendNotification(playerRef.getPacketHandler(),
          Message.raw("Failed to create world for unknown reasons."));
      return;
    }

    var packName = this.plugin.getIdentifier().toString();
    var pack = AssetModule.get().getAssetPack(packName);
    if (pack == null) {
      NotificationUtil.sendNotification(playerRef.getPacketHandler(),
          Message.raw("Could not find asset pack: " + packName));
      return;
    }

    var prefabFs = pack.getFileSystem();
    if (prefabFs == null) {
      NotificationUtil.sendNotification(playerRef.getPacketHandler(),
          Message.raw("Could not access asset pack file system: " + packName));
      return;
    }

    var prefabPath = prefabFs.getPath(SPAWN_PREFAB_PATH);
    if (prefabPath == null || !Files.exists(prefabPath)) {
      NotificationUtil.sendNotification(playerRef.getPacketHandler(),
          Message.raw("Could not find prefab at path: " + SPAWN_PREFAB_PATH));
      return;
    }

    var prefab = PrefabLoader.loadPrefabBufferAt(prefabPath);
    if (prefab == null) {
      NotificationUtil.sendNotification(playerRef.getPacketHandler(),
          Message.raw("Failed to load prefab from path: " + SPAWN_PREFAB_PATH));
      return;
    }

    var bwStore = world.getEntityStore().getStore();
    if (bwStore == null) {
      NotificationUtil.sendNotification(playerRef.getPacketHandler(),
          Message.raw("Failed to access BedWars world entity store."));
      return;
    }

    world.execute(() -> {
      PrefabUtil.paste(prefab.newAccess(), world, new Vector3i(0, 200, 0), Rotation.None, true, new Random(), bwStore);
    });
    NotificationUtil.sendNotification(playerRef.getPacketHandler(), Message.raw("BedWars world created successfully!"));
  }
}
