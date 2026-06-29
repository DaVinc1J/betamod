package davincij.betamod.block.entity;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.block.entity.BlockEntity;

public class electrical_block_entity extends BlockEntity {
  private float amps;

  public float get_amps() {
    return amps;
  }

  public void set_amps(float amps) {
    this.amps = amps;
  }

  @Override
  public void readNbt(NbtCompound nbt) {
    super.readNbt(nbt);
    this.amps = nbt.getFloat("amps");
  }

  @Override
  public void writeNbt(NbtCompound nbt) {
    super.writeNbt(nbt);
    nbt.putFloat("amps", this.amps);
  }
}
