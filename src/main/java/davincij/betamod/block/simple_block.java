package davincij.betamod.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class simple_block extends TemplateBlock {
  private static final int TOP = 1;
  private static final int BOTTOM = 0;
  private static final int FRONT = 2;
  private static final int BACK = 3;
  private static final int LEFT = 4;
  private static final int RIGHT = 5;

  private final int[] textures = new int[6];
  private boolean directional = false;

  public simple_block(Identifier identifier, Material material, float hardness, float resistance, BlockSoundGroup sg) {
    super(identifier, material);
    setTranslationKey(identifier.namespace, identifier.path);
    setHardness(hardness);
    setResistance(resistance);
    setSoundGroup(sg);
  }

  @Override
  public simple_block setHardness(float hardness) {
    return (simple_block) super.setHardness(hardness);
  }

  public void set_textures(int top, int bottom, int front, int back, int left, int right) {
    textures[TOP] = top;
    textures[BOTTOM] = bottom;
    textures[FRONT] = front;
    textures[BACK] = back;
    textures[LEFT] = left;
    textures[RIGHT] = right;
    directional = true;
  }

  public void set_all_sides(int top, int bottom, int front, int back, int left, int right) {
    set_textures(top, bottom, front, back, left, right);
  }

  public void set_uniform(int tex) {
    set_textures(tex, tex, tex, tex, tex, tex);
    directional = false;
  }

  public void set_top_bottom(int top, int bottom, int sides) {
    set_textures(top, bottom, sides, sides, sides, sides);
    directional = false;
  }

  public void set_front_back(int front, int back, int sides) {
    set_textures(sides, sides, front, back, sides, sides);
  }

  public void set_directional_full(int top_bottom, int front, int back, int left, int right) {
    set_textures(top_bottom, top_bottom, front, back, left, right);
  }

  public void set_front_only(int front, int sides) {
    set_textures(sides, sides, front, sides, sides, sides);
  }

  public void set_top_front(int top, int front, int sides) {
    set_textures(top, sides, front, sides, sides, sides);
  }

  @Override
  public int getTexture(int face, int metadata) {
    if (!directional) {
      return textures[face];
    }

    int rotation = metadata & 3;
    return get_rotated_texture(face, rotation);
  }

  private int get_rotated_texture(int face, int rotation) {
    if (face == TOP || face == BOTTOM) {
      return textures[face];
    }

    return switch (rotation) {
      case 0 -> textures[face];
      case 1 -> rotate_face_90(face);
      case 2 -> rotate_face_180(face);
      case 3 -> rotate_face_270(face);
      default -> textures[face];
    };
  }

  private int rotate_face_90(int face) {
    return switch (face) {
      case FRONT -> textures[LEFT];
      case LEFT -> textures[BACK];
      case BACK -> textures[RIGHT];
      case RIGHT -> textures[FRONT];
      default -> textures[face];
    };
  }

  private int rotate_face_180(int face) {
    return switch (face) {
      case FRONT -> textures[BACK];
      case BACK -> textures[FRONT];
      case LEFT -> textures[RIGHT];
      case RIGHT -> textures[LEFT];
      default -> textures[face];
    };
  }

  private int rotate_face_270(int face) {
    return switch (face) {
      case FRONT -> textures[RIGHT];
      case RIGHT -> textures[BACK];
      case BACK -> textures[LEFT];
      case LEFT -> textures[FRONT];
      default -> textures[face];
    };
  }

  @Override
  public void onPlaced(World level, int x, int y, int z, LivingEntity living) {
    if (!directional) return;
    int facing = MathHelper.floor((double)(living.yaw * 4.0F / 360.0F) + 0.5D) & 3;
    level.setBlockMeta(x, y, z, facing);
  }
}
