package davincij.betamod.event;

import davincij.betamod.item.simple_item;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

public class item {

  public static simple_item
    copper_ingot;

  @Entrypoint.Namespace
  public static Namespace NAMESPACE;


  @EventListener
  public void registerItems(ItemRegistryEvent event) {
    copper_ingot = new simple_item(NAMESPACE.id("copper_ingot"));
  }
}
