package net.rodofire.easierworldcreator.world.chunk;

import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;

public interface ChunkRegionUtil {
    Chunk getNullableChunk(int chunkX, int chunkZ, ChunkStatus leastStatus, boolean create);
}
