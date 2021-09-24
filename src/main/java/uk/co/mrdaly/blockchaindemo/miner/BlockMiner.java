package uk.co.mrdaly.blockchaindemo.miner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uk.co.mrdaly.blockchaindemo.model.ExecutorStats;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BlockMiner {

    private final MinerExecutor minerExecutor;


    public BlockMiner(MinerExecutor minerExecutor) {
        this.minerExecutor = minerExecutor;
    }

    public void mineForData(String data) {
        minerExecutor.submit(data);
    }

    public boolean pollForDone() {
        final ExecutorStats stats = minerExecutor.poll();
        log.info("Executor service has {} jobs running and {} waiting.", stats.getActiveTasks(), stats.getWaiting());
        return stats.tasksComplete();
    }

}
