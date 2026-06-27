package davincij.betamod.events.init;

import davincij.betamod.bm_items;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

public class ItemListener {
  @Entrypoint.Namespace
  public static Namespace NAMESPACE;

  @EventListener
  public void registerItems(ItemRegistryEvent event) {
    bm_items.greatwood_log     = simpleItem("greatwood_log");
    bm_items.silverwood_log    = simpleItem("silverwood_log");
    bm_items.greatwood_planks  = simpleItem("greatwood_planks");
    bm_items.silverwood_planks = simpleItem("silverwood_planks");
    bm_items.greatwood_stick   = simpleItem("greatwood_stick");
    bm_items.silverwood_stick  = simpleItem("silverwood_stick");
    bm_items.iron_cap          = simpleItem("iron_cap");
    bm_items.silver_cap        = simpleItem("silver_cap");
    bm_items.thaumium_cap      = simpleItem("thaumium_cap");
  }

  private static Item simpleItem(String name) {
    return new TemplateItem(NAMESPACE.id(name)).setTranslationKey(NAMESPACE, name);
  }
}
