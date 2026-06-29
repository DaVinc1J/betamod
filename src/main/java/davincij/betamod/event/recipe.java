package davincij.betamod.event;

import net.mine_diver.unsafeevents.listener.EventListener;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import davincij.betamod.event.block;
import davincij.betamod.event.item;

import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.recipe.SmeltingRegistry;
import net.modificationstation.stationapi.api.recipe.CraftingRegistry;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;


public class recipe {
  @Entrypoint.Namespace
  public static Namespace NAMESPACE;

  @EventListener
  public void registerRecipes(RecipeRegisterEvent event) {  
    RecipeRegisterEvent.Vanilla type = RecipeRegisterEvent.Vanilla.fromType(event.recipeId);  

    if (type == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPED) {

      CraftingRegistry.addShapedRecipe(
          new ItemStack(block.copper_block), 
          "ooo", "ooo", "ooo", 
          'o', new ItemStack(item.copper_ingot));

      CraftingRegistry.addShapedRecipe(
          new ItemStack(block.shaft, 4),
          "I",
          "I",
          'I', new ItemStack(Item.IRON_INGOT));
    }

    if (type == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPELESS) {
      CraftingRegistry.addShapelessRecipe(
          new ItemStack(item.copper_ingot, 9),
          new ItemStack(block.copper_block)
          );

      CraftingRegistry.addShapelessRecipe(
          new ItemStack(block.copper_wire, 4),
          new ItemStack(item.copper_ingot)
          );
    }  

    if (type == RecipeRegisterEvent.Vanilla.SMELTING) {
      SmeltingRegistry.addSmeltingRecipe(
          new ItemStack(block.copper_ore),
          new ItemStack(item.copper_ingot, 2)
          );
    }  
  }
}
