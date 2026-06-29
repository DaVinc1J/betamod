package davincij.betamod.events;

import davincij.betamod.blocks.simple_block;
import davincij.betamod.blocks.wire_block;
import davincij.betamod.blocks.shaft_block;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

public class block {
  
  public static simple_block 
    copper_ore,
    copper_block;

  public static wire_block
    copper_wire;

  public static shaft_block
    shaft;

  @Entrypoint.Namespace
  public static Namespace NAMESPACE;

  @EventListener
  public void registerBlocks(BlockRegistryEvent event) {
    copper_ore = new simple_block(NAMESPACE.id("copper_ore"), Material.STONE, 2.0F, 5.0F, Block.STONE_SOUND_GROUP);
    copper_block = new simple_block(NAMESPACE.id("copper_block"), Material.METAL, 3.0F, 10.0F, Block.METAL_SOUND_GROUP);
    copper_wire = new wire_block(NAMESPACE.id("copper_wire"), Material.METAL, 0.5F, 1.0F, Block.METAL_SOUND_GROUP);
    shaft = new shaft_block(NAMESPACE.id("shaft"), Material.METAL, 1.0F, 1.0F, Block.METAL_SOUND_GROUP);
  }
}

