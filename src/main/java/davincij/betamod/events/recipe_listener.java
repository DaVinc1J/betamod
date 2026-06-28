package davincij.betamod.events;

import net.mine_diver.unsafeevents.listener.EventListener;

import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import davincij.betamod.blocks;
import davincij.betamod.items;

import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.recipe.SmeltingRegistry;
import net.modificationstation.stationapi.api.recipe.CraftingRegistry;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;


public class recipe_listener {
  @Entrypoint.Namespace
  public static Namespace NAMESPACE;

  @EventListener
  public void registerRecipes(RecipeRegisterEvent event) {  
    RecipeRegisterEvent.Vanilla type = RecipeRegisterEvent.Vanilla.fromType(event.recipeId);  

    if (type == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPED) {
      CraftingRegistry.addShapedRecipe(
        new ItemStack(blocks.copper_block), // Output
        "ooo", "ooo", "ooo",
        'o', new ItemStack(items.copper_ingot)
      );
    }

    if (type == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPELESS) {
    }  

    if (type == RecipeRegisterEvent.Vanilla.SMELTING) {
      SmeltingRegistry.addSmeltingRecipe(
        new ItemStack(blocks.copper_ore),
        new ItemStack(items.copper_ingot, 2)
        );
    }  
  }
}
