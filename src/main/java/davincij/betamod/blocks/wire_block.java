package davincij.betamod.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class wire_block extends TemplateBlock {
  public enum Direction {
    NORTH(0, 0, 0, -1),
    SOUTH(1, 0, 0, 1),
    EAST(2, 1, 0, 0),
    WEST(3, -1, 0, 0),
    UP(4, 0, 1, 0),
    DOWN(5, 0, -1, 0);

    public final int bit;
    public final int dx, dy, dz;

    Direction(int bit, int dx, int dy, int dz) {
      this.bit = bit;
      this.dx = dx;
      this.dy = dy;
      this.dz = dz;
    }
  }

  public wire_block(Identifier identifier, Material material, float hardness, float resistance, BlockSoundGroup sg) {
    super(identifier, material);
    setTranslationKey(identifier.namespace, identifier.path);
    setHardness(hardness);
    setResistance(resistance);
    setSoundGroup(sg);
    setBoundingBox(0.375F, 0.375F, 0.375F, 0.625F, 0.625F, 0.625F);
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
  public Box getCollisionShape(World world, int x, int y, int z) {
    return super.getCollisionShape(world, x, y, z);
  }

  @Override
  public void onPlaced(World world, int x, int y, int z, LivingEntity living) {
    update_boundings(world, x, y, z);
  }

  @Override
  public void neighborUpdate(World world, int x, int y, int z, int neighborId) {
    update_boundings(world, x, y, z);
  }

  @Override
  public void updateBoundingBox(BlockView view, int x, int y, int z) {
    float min = 0.375F, max = 0.625F;
    float x0 = min, y0 = min, z0 = min, x1 = max, y1 = max, z1 = max;
    
    if (view.getBlockId(x, y, z) == this.id) {
      int metadata = view.getBlockMeta(x, y, z);
      
      if (is_connected(metadata, Direction.WEST))  x0 = 0.0F;
      if (is_connected(metadata, Direction.EAST))  x1 = 1.0F;
      if (is_connected(metadata, Direction.DOWN))  y0 = 0.0F;
      if (is_connected(metadata, Direction.UP))    y1 = 1.0F;
      if (is_connected(metadata, Direction.NORTH)) z0 = 0.0F;
      if (is_connected(metadata, Direction.SOUTH)) z1 = 1.0F;
    }
    setBoundingBox(x0, y0, z0, x1, y1, z1);
  }

  @Override
  public void addIntersectingBoundingBox(World world, int x, int y, int z, Box box, ArrayList boxes) {
    if (!is_wire(world, x, y, z)) return;
    
    float min = 0.375F, max = 0.625F;
    int metadata = world.getBlockMeta(x, y, z);

    add_box(boxes, box, x, y, z, min, min, min, max, max, max); // core node
    
    if (is_connected(metadata, Direction.NORTH))
      add_box(boxes, box, x, y, z, min, min, 0.0F, max, max, min);
    if (is_connected(metadata, Direction.SOUTH))
      add_box(boxes, box, x, y, z, min, min, max, max, max, 1.0F);
    if (is_connected(metadata, Direction.WEST))
      add_box(boxes, box, x, y, z, 0.0F, min, min, min, max, max);
    if (is_connected(metadata, Direction.EAST))
      add_box(boxes, box, x, y, z, max, min, min, 1.0F, max, max);
    if (is_connected(metadata, Direction.DOWN))
      add_box(boxes, box, x, y, z, min, 0.0F, min, max, min, max);
    if (is_connected(metadata, Direction.UP))
      add_box(boxes, box, x, y, z, min, max, min, max, 1.0F, max);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private void add_box(ArrayList boxes, Box mask, int x, int y, int z,
                       float x0, float y0, float z0, float x1, float y1, float z1) {
    Box piece = Box.createCached(x + x0, y + y0, z + z0, x + x1, y + y1, z + z1);
    if (mask.intersects(piece)) boxes.add(piece);
  }

  private void update_boundings(World world, int x, int y, int z) {
    update_connections(world, x, y, z);
    updateBoundingBox(world, x, y, z);
  }

  private void update_connections(World world, int x, int y, int z) {
    int metadata = 0;
    
    for (Direction dir : Direction.values()) {
      int nx = x + dir.dx;
      int ny = y + dir.dy;
      int nz = z + dir.dz;
      if (is_wire(world, nx, ny, nz)) {
        metadata |= (1 << dir.bit);
      }
    }
    
    world.setBlockMeta(x, y, z, metadata);
    world.blockUpdateEvent(x, y, z);
  }

  private boolean is_wire(World world, int x, int y, int z) {
    return world.getBlockId(x, y, z) == this.id;
  }

  private boolean is_connected(int metadata, Direction dir) {
    return (metadata & (1 << dir.bit)) != 0;
  }
}
