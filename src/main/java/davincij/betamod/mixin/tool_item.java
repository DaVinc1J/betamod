package davincij.betamod.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.ToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ToolItem.class)
public interface tool_item {
  // instance field -> non-static accessor methods (no "static", no AssertionError body needed)
  @Accessor("effectiveOnBlocks")
  Block[] getEffectiveOnBlocks();

  @Accessor("effectiveOnBlocks")
  void setEffectiveOnBlocks(Block[] blocks);
}
