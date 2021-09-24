package uk.co.mrdaly.blockchaindemo.miner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.co.mrdaly.blockchaindemo.hash.HashService;
import uk.co.mrdaly.blockchaindemo.model.Block;

@Component
@Slf4j
public class BlockMiner {

    @Value("${prefix.size}:5")
    private int prefixSize;

    private HashService hashService;

    public BlockMiner(HashService hashService) {
        this.hashService = hashService;
    }

    //    public String mineBlock(int prefix) {
//        String prefixString = new String(new char[prefix]).replace('\0', '0');
//        while (!hash.substring(0, prefix).equals(prefix)) {
//            nonce++;
//            hash = calculateHash();
//        }
//        return hash;
//    }

    public Block mineForData(String data) {

    }
}
