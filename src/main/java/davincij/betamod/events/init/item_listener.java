package davincij.betamod.events.init;

import davincij.betamod.bm_items;
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
    //
  }

  private static Item simple_item(String name) {
    return new TemplateItem(NAMESPACE.id(name)).setTranslationKey(NAMESPACE, name);
  }
}
