package davincij.betamod.events.init;

import davincij.betamod.bm_blocks;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

public class block_listener {
  @Entrypoint.Namespace
  public static Namespace NAMESPACE;

  @EventListener
  public void registerBlocks(BlockRegistryEvent event) {
    bm_blocks.greatwood_log = simple_block("greatwood_log", Material.WOOD);
    bm_blocks.silverwood_log = simple_block("silverwood_log", Material.WOOD);
    bm_blocks.greatwood_planks = simple_block("greatwood_planks", Material.WOOD);
    bm_blocks.silverwood_planks = simple_block("silverwood_planks", Material.WOOD);
  }

  private static Block simple_block(String name, Material material) {
    TemplateBlock block = new TemplateBlock(NAMESPACE.id(name), material);
    block.setHardness(2.0F);
    block.setTranslationKey(NAMESPACE, name);
    return block;
  }
}
