package davincij.betamod.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Box;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.world.BlockStateView;

public class wire_block extends TemplateBlock {
  public enum Direction {
    NORTH("north", 0, 0, -1),
    SOUTH("south", 0, 0, 1),
    EAST("east", 1, 0, 0),
    WEST("west", -1, 0, 0),
    UP("up", 0, 1, 0),
    DOWN("down", 0, -1, 0);

    public final BooleanProperty property;
    public final int dx, dy, dz;

    Direction(String name, int dx, int dy, int dz) {
      this.property = BooleanProperty.of(name);
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
    
    BlockState defaultState = getDefaultState();
    for (Direction dir : Direction.values()) {
      defaultState = defaultState.with(dir.property, false);
    }
    setDefaultState(defaultState);
  }

  @Override
  public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder);
    for (Direction dir : Direction.values()) {
      builder.add(dir.property);
    }
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
    
    if (view instanceof BlockStateView stateView && view.getBlockId(x, y, z) == this.id) {
      BlockState s = stateView.getBlockState(x, y, z);
      
      if (s.get(Direction.WEST.property))  x0 = 0.0F;
      if (s.get(Direction.EAST.property))  x1 = 1.0F;
      if (s.get(Direction.DOWN.property))  y0 = 0.0F;
      if (s.get(Direction.UP.property))    y1 = 1.0F;
      if (s.get(Direction.NORTH.property)) z0 = 0.0F;
      if (s.get(Direction.SOUTH.property)) z1 = 1.0F;
    }
    setBoundingBox(x0, y0, z0, x1, y1, z1);
  }

  @Override
  public void addIntersectingBoundingBox(World world, int x, int y, int z, Box box, ArrayList boxes) {
    if (!is_wire(world, x, y, z)) return;
    
    float min = 0.375F, max = 0.625F;
    BlockState s = world.getBlockState(x, y, z);

    add_box(boxes, box, x, y, z, min, min, min, max, max, max); // core node
    
    if (s.get(Direction.NORTH.property))
      add_box(boxes, box, x, y, z, min, min, 0.0F, max, max, min);
    if (s.get(Direction.SOUTH.property))
      add_box(boxes, box, x, y, z, min, min, max, max, max, 1.0F);
    if (s.get(Direction.WEST.property))
      add_box(boxes, box, x, y, z, 0.0F, min, min, min, max, max);
    if (s.get(Direction.EAST.property))
      add_box(boxes, box, x, y, z, max, min, min, 1.0F, max, max);
    if (s.get(Direction.DOWN.property))
      add_box(boxes, box, x, y, z, min, 0.0F, min, max, min, max);
    if (s.get(Direction.UP.property))
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
    BlockState state = getDefaultState();
    
    for (Direction dir : Direction.values()) {
      int nx = x + dir.dx;
      int ny = y + dir.dy;
      int nz = z + dir.dz;
      state = state.with(dir.property, is_wire(world, nx, ny, nz));
    }
    
    world.setBlockStateWithoutNotifyingNeighbors(x, y, z, state);
  }

  private boolean is_wire(World world, int x, int y, int z) {
    return world.getBlockId(x, y, z) == this.id;
  }
}
