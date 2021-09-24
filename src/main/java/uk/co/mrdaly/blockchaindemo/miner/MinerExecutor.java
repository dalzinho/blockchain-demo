package uk.co.mrdaly.blockchaindemo.miner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uk.co.mrdaly.blockchaindemo.model.ExecutorStats;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MinerExecutor {

    private final BlockMinerCallableFactory blockMinerCallableFactory;
    private List<Future<Void>> taskQueue = new ArrayList<>();

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public MinerExecutor(BlockMinerCallableFactory blockMinerCallableFactory) {
        this.blockMinerCallableFactory = blockMinerCallableFactory;
    }

    public void submit(String data) {
        final MinerCallable minerCallable = blockMinerCallableFactory.get(data);
        final Future<Void> submit = executorService.submit(minerCallable);
        taskQueue.add(submit);
    }

    public ExecutorStats poll() {

        List<Future<Void>> updatedList = new ArrayList<>();

        for (Future<Void> future : taskQueue) {
            if (future.isDone()) {
                complete(future);
            } else {
                updatedList.add(future);
            }
        }

        taskQueue = updatedList;

        final int activeCount = ((ThreadPoolExecutor) executorService).getActiveCount();
        return new ExecutorStats(activeCount, taskQueue.size() - activeCount);
    }

    private void complete(Future<Void> future) {
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("some concurrency error", e);
            Thread.currentThread().interrupt();
        }
    }

}
