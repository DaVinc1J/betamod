package davincij.betamod.block;

import java.util.Locale;

import davincij.betamod.block.entity.mechanical_block_entity;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.StringIdentifiable;
import net.modificationstation.stationapi.api.world.BlockStateView;

public class shaft_block extends TemplateBlockWithEntity {
  public enum axis implements StringIdentifiable {
    X(0.0F, 0.375F, 0.375F, 1.0F, 0.625F, 0.625F),
    Y(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F),
    Z(0.375F, 0.375F, 0.0F, 0.625F, 0.625F, 1.0F);

    public final float x0, y0, z0, x1, y1, z1;

    axis(float x0, float y0, float z0, float x1, float y1, float z1) {
      this.x0 = x0;
      this.y0 = y0;
      this.z0 = z0;
      this.x1 = x1;
      this.y1 = y1;
      this.z1 = z1;
    }

    @Override
    public String asString() {
      return name().toLowerCase(Locale.ROOT);
    }
  }

  public static final EnumProperty<axis> AXIS = EnumProperty.of("axis", axis.class);

  public shaft_block(Identifier identifier, Material material, float hardness, float resistance, BlockSoundGroup sg) {
    super(identifier, material);
    setTranslationKey(identifier.namespace, identifier.path);
    setHardness(hardness);
    setResistance(resistance);
    setSoundGroup(sg);
    setDefaultState(getDefaultState().with(AXIS, axis.X));
    setBoundingBox(axis.X.x0, axis.X.y0, axis.X.z0, axis.X.x1, axis.X.y1, axis.X.z1);
  }

  @Override
  public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder);
    builder.add(AXIS);
  }

  @Override
  protected BlockEntity createBlockEntity() {
    mechanical_block_entity s = new mechanical_block_entity();
    s.set_rpm(20.0F);
    return s;
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
    axis a = get_axis_from_player_look(living);
    set_axis(world, x, y, z, a);
  }

  @Override
  public void updateBoundingBox(BlockView view, int x, int y, int z) {
    if (view instanceof BlockStateView stateView && view.getBlockId(x, y, z) == this.id) {
      axis a = stateView.getBlockState(x, y, z).get(AXIS);
      setBoundingBox(a.x0, a.y0, a.z0, a.x1, a.y1, a.z1);
    }
  }

  public void set_axis(World world, int x, int y, int z, axis a) {
    world.setBlockStateWithoutNotifyingNeighbors(x, y, z, getDefaultState().with(AXIS, a));
    world.blockUpdateEvent(x, y, z);
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
