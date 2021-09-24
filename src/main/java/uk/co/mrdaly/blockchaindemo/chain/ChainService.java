package uk.co.mrdaly.blockchaindemo.chain;

import org.springframework.stereotype.Component;
import uk.co.mrdaly.blockchaindemo.model.Block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ChainService {

    private final List<Block> chain = new ArrayList<>();

    public String getLastHash() {
        return chain.isEmpty() ? "" : chain.get(chain.size() - 1).getHash();
    }

    public void addBlock(Block block) {
        chain.add(block);
    }

    public List<Block> getChain() {
        return Collections.unmodifiableList(chain);
    }
}
