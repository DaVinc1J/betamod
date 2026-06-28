package davincij.betamod.events;

import davincij.betamod.blocks;
import davincij.betamod.mixin.tool_item;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.block.material.Material;
import net.minecraft.item.AxeItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

enum tool {
  axe,
  pickaxe,
  none
}

enum mat {
  wood,
  stone,
  metal,
  none
}

public class block_listener {
  @Entrypoint.Namespace
  public static Namespace NAMESPACE;

  @EventListener
  public void registerBlocks(BlockRegistryEvent event) {
    blocks.copper_ore = block("copper_ore", mat.stone);
    blocks.copper_block = block("copper_block", mat.metal);
  }

  private static final List<Block> AXE_EFFECTIVE = new ArrayList<>();
  private static final List<Block> PICKAXE_EFFECTIVE = new ArrayList<>();

  @EventListener
  public void registerItems(ItemRegistryEvent event) {
    for (Item item : Item.ITEMS) {
      if (item instanceof PickaxeItem) {
        applyEffectiveBlocks((tool_item) item, PICKAXE_EFFECTIVE);
        break; // Only apply once
      }
    }
  }

  private void applyEffectiveBlocks(tool_item tool, List<Block> effectiveBlocks) {
    if (effectiveBlocks.isEmpty()) return;
  
    List<Block> effective = new ArrayList<>(Arrays.asList(tool.getEffectiveOnBlocks()));
    effective.addAll(effectiveBlocks);
    tool.setEffectiveOnBlocks(effective.toArray(new Block[0]));
  }


  private static Block block(String name, mat material) {

    Material mat;
    BlockSoundGroup sg;
    float hardness;
    float resistance;
    tool required;

    switch (material) {
      case wood:
        mat = Material.WOOD;
        sg = Block.WOOD_SOUND_GROUP;
        hardness = 2.0F;
        resistance = 5.0F;
        required = tool.axe;
        break;
      case metal:
        mat = Material.METAL;
        sg = Block.METAL_SOUND_GROUP;
        hardness = 3.0F;
        resistance = 10.0F;
        required = tool.pickaxe;
        break;
      case stone:
        mat = Material.STONE;
        sg = Block.STONE_SOUND_GROUP;
        hardness = 3.0F;
        resistance = 10.0F;
        required = tool.pickaxe;
        break;
      default:
        mat = Material.STONE;
        sg = Block.STONE_SOUND_GROUP;
        hardness = 1.0F;
        resistance = 1.0F;
        required = tool.none;
        break;
    }

    TemplateBlock block = new TemplateBlock(NAMESPACE.id(name), mat);
    block.setHardness(hardness);
    block.setResistance(resistance);
    block.setSoundGroup(sg);
    block.setTranslationKey(NAMESPACE, name);

    switch (required) {
      case axe:
        AXE_EFFECTIVE.add(block);
        break;
      case pickaxe:
        PICKAXE_EFFECTIVE.add(block);
        break;
      default:
        break;
    }

    return block;
  }
  }
