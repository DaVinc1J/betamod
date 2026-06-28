package davincij.betamod.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(AxeItem.class)
public class axe_item {

  // Shadow the vanilla effective blocks array so we can reference it
  @Shadow private static Block[] blocksEffectiveAgainst;

  @Inject(method = "<clinit>", at = @At("TAIL"))
  private static void addCustomBlocksToPickaxe(CallbackInfo ci) {
    // 1. Convert the original hardcoded array to a mutable list
    List<Block> mutableBlocks = new ArrayList<>(Arrays.asList(blocksEffectiveAgainst));

    // 2. Add your custom mod blocks to the list
    // Replace 'MyMod.MY_CUSTOM_ORE' with your actual registered block instance
    mutableBlocks.add(MyMod.MY_CUSTOM_ORE);
    mutableBlocks.add(MyMod.ANOTHER_CUSTOM_BLOCK);

    // 3. Re-assign the expanded array back to the vanilla class field
    blocksEffectiveAgainst = mutableBlocks.toArray(new Block[0]);
  }
}
