package davincij.betamod.block.entity;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.block.entity.BlockEntity;

public class mechanical_block_entity extends BlockEntity {
  private float rpm;
  public float prev_angle;
  public float angle;

  public float get_rpm() {
    return rpm;
  }

  public void set_rpm(float rpm) {
    this.rpm = rpm;
  }

  public void tick_client() {
    prev_angle = angle;
    angle = (angle + rpm * 0.3f) % 360f;
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    super.readNbt(nbt);
    this.rpm = nbt.getFloat("rpm");
  }

  @Override
  public void writeNbt(NbtCompound nbt) {
    super.writeNbt(nbt);
    nbt.putFloat("rpm", this.rpm);
  }
}
