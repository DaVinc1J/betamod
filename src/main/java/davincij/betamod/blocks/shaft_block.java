package davincij.betamod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class shaft_block extends TemplateBlock {
  public enum axis {
    X(0, 0.0F, 0.375F, 0.375F, 1.0F, 0.625F, 0.625F),
    Y(1, 0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F),
    Z(2, 0.375F, 0.375F, 0.0F, 0.625F, 0.625F, 1.0F);

    public final int bit;
    public final float x0, y0, z0, x1, y1, z1;

    axis(int bit, float x0, float y0, float z0, float x1, float y1, float z1) {
      this.bit = bit;
      this.x0 = x0;
      this.y0 = y0;
      this.z0 = z0;
      this.x1 = x1;
      this.y1 = y1;
      this.z1 = z1;
    }
  }

  public shaft_block(Identifier identifier, Material material, float hardness, float resistance, BlockSoundGroup sg) {
    super(identifier, material);
    setTranslationKey(identifier.namespace, identifier.path);
    setHardness(hardness);
    setResistance(resistance);
    setSoundGroup(sg);
    setBoundingBox(axis.X.x0, axis.X.y0, axis.X.z0, axis.X.x1, axis.X.y1, axis.X.z1);
  }

  @Override
  public boolean isOpaque() {
    return false;
  }

  @Override
  public boolean isFullCube() {
    return false;
  }

  @Override
  public void onPlaced(World world, int x, int y, int z, LivingEntity living) {
    axis axis = get_axis_from_player_look(living);
    world.setBlockMeta(x, y, z, axis.bit);
    world.blockUpdateEvent(x, y, z);
  }

  @Override
  public void updateBoundingBox(BlockView view, int x, int y, int z) {
    if (view.getBlockId(x, y, z) == this.id) {
      int metadata = view.getBlockMeta(x, y, z);
      axis axis = get_axis_from_meta(metadata);
      setBoundingBox(axis.x0, axis.y0, axis.z0, axis.x1, axis.y1, axis.z1);
    }
  }

  public void set_axis(World world, int x, int y, int z, axis axis) {
    world.setBlockMeta(x, y, z, axis.bit);
    world.blockUpdateEvent(x, y, z);
  }

  private axis get_axis_from_meta(int metadata) {
    int bit = metadata & 0x3;
    for (axis axis : axis.values()) {
      if (axis.bit == bit) {
        return axis;
      }
    }
    return axis.X;
  }

  private axis get_axis_from_player_look(LivingEntity living) {
    float pitch = living.pitch;
    
    if (Math.abs(pitch) > 45) {
      return axis.Y;
    }
    
    float yaw = living.yaw % 360;
    if (yaw < 0) yaw += 360;
    
    if ((yaw >= 45 && yaw < 135) || (yaw >= 225 && yaw < 315)) {
      return axis.X;
    } else {
      return axis.Z;
    }
  }
}
