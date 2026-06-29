package davincij.betamod.event;

import davincij.betamod.block.entity.electrical_block_entity;
import davincij.betamod.block.entity.mechanical_block_entity;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

public class block_entity {

  @Entrypoint.Namespace
  public static Namespace NAMESPACE;

  @EventListener
  public void register_block_entity(BlockEntityRegisterEvent event) {
    event.register(NAMESPACE.id("copper_wire").toString(), electrical_block_entity.class);
    event.register(NAMESPACE.id("shaft").toString(), mechanical_block_entity.class);
  }
}
