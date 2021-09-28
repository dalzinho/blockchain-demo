package uk.co.mrdaly.blockchaindemo.miner;

import lombok.extern.slf4j.Slf4j;
import uk.co.mrdaly.blockchaindemo.chain.ChainService;
import uk.co.mrdaly.blockchaindemo.hash.HashService;
import uk.co.mrdaly.blockchaindemo.model.Block;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;

@Slf4j
public class MinerCallable implements Callable<Void> {

    private final ChainService chainService;
    private final HashService hashService;
    private final int prefixSize;
    private final String data;

    public MinerCallable(ChainService chainService, HashService hashService, int prefixSize, String data) {
        this.chainService = chainService;
        this.hashService = hashService;
        this.prefixSize = prefixSize;
        this.data = data;
    }

    @Override
    public Void call() {
        LocalDateTime start = LocalDateTime.now();
        log.info("I've been triggered to mine for {}!", data);
        String previousHash = chainService.getLastHash();

        String prefixString = new String(new char[prefixSize]).replace('\0', '0');

        String hash = "";
        int nonce = 0;
        while (hash.isEmpty() || !hash.substring(0, prefixSize).equals(prefixString)) {
            if (!previousHash.equals(chainService.getLastHash())) {
                log.info("Looks like the last hash in the chain has changed! Resetting hash and nonce for {} after {}",
                        data, Duration.between(start, LocalDateTime.now()));
                previousHash = chainService.getLastHash();
                nonce = 0;
            }
            hash = hashService.calculateHash(previousHash, start, nonce, data);
            nonce++;
        }

        log.info("found hash {} for {}", hash, data);
        Block block = new Block(previousHash, data, start, hash, nonce);
        boolean blockAddedToChain = chainService.addBlockAndConfirmAddition(block);

        if (!blockAddedToChain) {
            call();
        }

        final Duration duration = Duration.between(start, LocalDateTime.now());
        log.info("{} entered into blockchain. Hashing took {} seconds", data, duration.getSeconds());
        return null;
    }
}
