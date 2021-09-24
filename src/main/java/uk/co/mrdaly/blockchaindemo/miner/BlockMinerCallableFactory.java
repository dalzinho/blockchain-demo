package uk.co.mrdaly.blockchaindemo.miner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.co.mrdaly.blockchaindemo.chain.ChainService;
import uk.co.mrdaly.blockchaindemo.hash.HashService;

@Component
public class BlockMinerCallableFactory {

    @Value("${prefix.size:5}")
    private int prefixSize;

    private final HashService hashService;
    private final ChainService chainService;

    public BlockMinerCallableFactory(HashService hashService, ChainService chainService) {
        this.hashService = hashService;
        this.chainService = chainService;
    }

    public MinerCallable get(String data) {
        return new MinerCallable(chainService, hashService, prefixSize, data);
    }
}
