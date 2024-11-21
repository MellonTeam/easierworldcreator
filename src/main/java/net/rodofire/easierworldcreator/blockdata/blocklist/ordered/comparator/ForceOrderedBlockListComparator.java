package net.rodofire.easierworldcreator.blockdata.blocklist.ordered.comparator;

import com.google.common.collect.BiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Class to manage an ordered list with force parameters.
 * This class uses a BiMap that connects each BlockState to a pair of Boolean and Set.
 */
@SuppressWarnings("unused")
public class ForceOrderedBlockListComparator extends DefaultOrderedBlockListComparator {
    private BiMap<Short, Pair<Boolean, Set<Block>>> forceBlocks;

    public ForceOrderedBlockListComparator(BlockState states, List<BlockPos> posList) {
        super(states, posList);
    }

    public ForceOrderedBlockListComparator(BlockState states, List<BlockPos> posList, boolean force, Set<Block> blocksToForce) {
        super(states, posList);
        Pair<Boolean, Set<Block>> forceData = new Pair<>(force, blocksToForce);
        this.forceBlocks.put((short) 0, forceData);
    }

    public ForceOrderedBlockListComparator(Map<BlockState, List<BlockPos>> info) {
        super(info);
    }

    /**
     * gives you the Set of every block that can be forced
     *
     * @return the set of the blocks that can be forced
     */

    public Set<Block> getBlocksToForce(BlockState state) {
        return forceBlocks.get(getStateIndex(state)).getRight();
    }

    /**
     * sets all the blocks that can be forced in the case force == false
     *
     * @param blocksToForce the set of blocks that can be forced
     */
    public void setBlocksToForce(BlockState state, Set<Block> blocksToForce) {
        this.forceBlocks.get(getStateIndex(state)).setRight(blocksToForce);
    }

    /**
     * add a block to the set
     *
     * @param block the block added
     */
    public void addBlocksToForce(BlockState state, Block block) {
        short index = getStateIndex(state);
        Set<Block> blockToForce = this.forceBlocks.get(index).getRight();
        blockToForce.add(block);
        this.forceBlocks.get(index).setRight(blockToForce);
    }

    /**
     * add a set of blocks to the set
     *
     * @param blocksToForce the set that will be added
     */
    public void addBlocksToForce(BlockState state, Set<Block> blocksToForce) {
        short index = getStateIndex(state);
        Set<Block> blockToForce = this.forceBlocks.get(index).getRight();
        blockToForce.addAll(blocksToForce);
        this.forceBlocks.get(index).setRight(blockToForce);
    }

    /**
     * remove a block to the set
     *
     * @param block the block removed
     */
    public void removeBlocksToForce(BlockState state, Block block) {
        short index = getStateIndex(state);
        Set<Block> blockToForce = this.forceBlocks.get(index).getRight();
        blockToForce.remove(block);
    }

    /**
     * remove a set of blocks to the set
     *
     * @param blocksToForce the set that will be removed
     */
    public void removeBlocksToForce(BlockState state, Set<Block> blocksToForce) {
        short index = getStateIndex(state);
        Set<Block> blockToForce = this.forceBlocks.get(index).getRight();
        blockToForce.removeAll(blocksToForce);
    }

    /**
     * get if any block can be replaced by any BlockState of this BlockShapeManager
     *
     * @return the boolean related to it
     */
    public boolean isForce(BlockState state) {
        return this.forceBlocks.get(getStateIndex(state)).getLeft();
    }

    /**
     * sets if any block can be replaced by any BlockState of this BlockShapeManager
     *
     * @param force the boolean used
     */
    public void setForce(BlockState state, boolean force) {
        this.forceBlocks.get(getStateIndex(state)).setLeft(force);
    }
}