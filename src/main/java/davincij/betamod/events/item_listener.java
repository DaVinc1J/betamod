package davincij.betamod.events;

import davincij.betamod.items;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

public class item_listener {
  @Entrypoint.Namespace
  public static Namespace NAMESPACE;

  @EventListener
  public void registerItems(ItemRegistryEvent event) {
    items.copper_ingot = item("copper_ingot");
    items.copper_wire = item("copper_wire");
  }

  private static Item item(String name) {
    return new TemplateItem(NAMESPACE.id(name)).setTranslationKey(NAMESPACE, name);
  }
}
