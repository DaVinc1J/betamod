package davincij.betamod.events;

import davincij.betamod.blocks;
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
    blocks.greatwood = wood("greatwood", Material.WOOD);
    blocks.silverwood = wood("silverwood", Material.WOOD);
    blocks.greatwood_planks = wood("greatwood_planks", Material.WOOD);
    blocks.silverwood_planks = wood("silverwood_planks", Material.WOOD);
  }

  private static Block wood(String name, Material material) {
    TemplateBlock block = new TemplateBlock(NAMESPACE.id(name), material);
    block.setHardness(2.0F);
    block.setResistance(5.0F);
    block.setSoundGroup(Block.WOOD_SOUND_GROUP);
    block.setTranslationKey(NAMESPACE, name);
    return block;
  }
}
