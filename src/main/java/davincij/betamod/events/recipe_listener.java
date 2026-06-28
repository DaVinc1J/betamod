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
      }
    
      if (type == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPELESS) {  
        CraftingRegistry.addShapelessRecipe(
            new ItemStack(blocks.greatwood_planks,4), 
            blocks.greatwood);

        CraftingRegistry.addShapelessRecipe(
            new ItemStack(blocks.silverwood_planks,4), 
            blocks.silverwood);
      }  
    
      if (type == RecipeRegisterEvent.Vanilla.SMELTING) {  
      }  
  }
}
