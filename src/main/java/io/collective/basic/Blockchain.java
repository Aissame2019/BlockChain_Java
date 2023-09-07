package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Blockchain {

    List<Block> blocks = new LinkedList<>();
    public boolean isEmpty() {
        return blocks.isEmpty();
    }

    public void add(Block block) {
        blocks.add(block);
    }

    public int size() {
        return blocks.size();
    }

    public boolean isValid() throws NoSuchAlgorithmException {
        var previousHash = "0";
        for (Block block : blocks) {
            if (!Objects.equals(block.getPreviousHash(), previousHash)) {
                return false;
            }
            previousHash = block.getHash();
            if (!isMined(block)){
                return false;
            }
            if (!Objects.equals(block.calculatedHash(), block.getHash())) {
                return false;
            }
        }

        return true;
    }

    /// Supporting functions that you'll need.

    public static Block mine(Block block) throws NoSuchAlgorithmException {
        Block mined = new Block(block.getPreviousHash(), block.getTimestamp(), block.getNonce());

        while (!isMined(mined)) {
            mined = new Block(mined.getPreviousHash(), mined.getTimestamp(), mined.getNonce() + 1);
        }
        return mined;
    }

    public static boolean isMined(Block minedBlock) {
        return minedBlock.getHash().startsWith("00");
    }
}