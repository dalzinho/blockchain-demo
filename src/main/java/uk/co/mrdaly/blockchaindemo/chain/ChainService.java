package uk.co.mrdaly.blockchaindemo.chain;

import org.springframework.stereotype.Component;
import uk.co.mrdaly.blockchaindemo.model.Block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ChainService {

    private String lastHash = "";

    private final List<Block> chain = new ArrayList<>();

    public String getLastHash() {
        return lastHash;
    }

    public synchronized boolean addBlockAndConfirmAddition(Block block) {
        if (!block.getPreviousHash().equals(lastHash)) {
            return false;
        }
        chain.add(block);
        lastHash = block.getHash();
        return true;
    }

    public List<Block> getChain() {
        return Collections.unmodifiableList(chain);
    }
}
