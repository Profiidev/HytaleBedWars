package io.profidev.HytaleBedWars;

import java.nio.file.Files;
import java.util.Random;

import javax.annotation.Nonnull;

import com.hypixel.hytale.builtin.hytalegenerator.assets.props.prefabprop.PrefabLoader;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.asset.AssetModule;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.Rotation;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.PrefabUtil;

public class TestCommand extends AbstractPlayerCommand {

  public TestCommand() {
    super("test", "A test command");
  }

  @Override
  protected void execute(@Nonnull CommandContext ctx, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref,
      @Nonnull PlayerRef playerRef, @Nonnull World world) {
    var packs = AssetModule.get().getAssetPacks();
    for (var pack : packs) {
      LOGGER.atInfo().log("Asset Pack: " + pack.getName() + " at " + pack.getPackLocation());
      if (pack.getName().contains("BedWars")) {
        var path1 = pack.getFileSystem().getPath("Server/Prefabs/Spawn.prefab.json");
        LOGGER.atInfo().log("Found BedWars Spawn Prefab at: " + path1.toAbsolutePath() + "File Exists: "
            + Files.exists(path1));
        var buf = PrefabLoader.loadPrefabBufferAt(path1);
        PrefabUtil.paste(buf.newAccess(), world, new Vector3i(0, 200, 0), Rotation.None, true, new Random(), store);
      }
    }
  }
}
