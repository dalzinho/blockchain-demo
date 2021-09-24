package uk.co.mrdaly.blockchaindemo.miner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.co.mrdaly.blockchaindemo.chain.ChainService;
import uk.co.mrdaly.blockchaindemo.hash.HashService;
import uk.co.mrdaly.blockchaindemo.model.Block;

import java.time.LocalDateTime;

@Component
@Slf4j
public class BlockMiner {

    @Value("${prefix.size}:5")
    private int prefixSize;

    private final HashService hashService;
    private final ChainService chainService;

    public BlockMiner(HashService hashService, ChainService chainService) {
        this.hashService = hashService;
        this.chainService = chainService;
    }

    public void mineForData(String data) {
        String previousHash = chainService.getLastHash();
        LocalDateTime localDateTime = LocalDateTime.now();

        String prefixString = new String(new char[prefixSize]).replace('\0', '0');

        String hash = "";
        int nonce = 0;
        while(!hash.substring(0, prefixSize).equals(prefixString)) {
            hash = hashService.calculateHash(previousHash, localDateTime, nonce, data);
            log.info("Most recent hash of {} is {}", data, hash);
            nonce++;
        }
        log.info("found hash {} for {}", hash, data);
        Block block = new Block(previousHash, data, localDateTime, hash, nonce);
        chainService.addBlock(block);

    }
}
