package uk.co.mrdaly.blockchaindemo.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import uk.co.mrdaly.blockchaindemo.chain.ChainService;
import uk.co.mrdaly.blockchaindemo.hash.HashService;
import uk.co.mrdaly.blockchaindemo.miner.BlockMiner;
import uk.co.mrdaly.blockchaindemo.miner.BlockMinerCallableFactory;
import uk.co.mrdaly.blockchaindemo.miner.MinerExecutor;
import uk.co.mrdaly.blockchaindemo.model.Block;
import uk.co.mrdaly.blockchaindemo.validator.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {
        ChainService.class,
        HashService.class,
        BlockMiner.class,
        BlockMinerCallableFactory.class,
        MinerExecutor.class,
        Validator.class
})
public class IntegrationTest {

    @Autowired
    BlockMiner blockMiner;

    @Autowired
    ChainService chainService;

    @Autowired
    Validator validator;

    @Test
    public void test() throws InterruptedException {
        final List<String> inputs = Arrays.asList(
                "pollok football club",
                "chips",
                "rocket league",
                "java",
                "spain",
                "bbq",
                "sparks"
        );
        for (String input : inputs) {
            blockMiner.mineForData(input);
        }

        while (!blockMiner.pollForDone()) {
            Thread.sleep(20000);
        }

        final List<Block> chain = chainService.getChain();
        System.out.println(chain);

        assertFalse(chain.isEmpty());

        final Optional<Block> firstInvalidBlock = validator.getFirstInvalidBlock(chain);
        assertNull(firstInvalidBlock.orElse(null));

        assertEquals(inputs.size(), chain.size());
    }
}
