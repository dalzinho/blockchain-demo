package uk.co.mrdaly.blockchaindemo.model;

import lombok.Data;

@Data
public class ExecutorStats {

    private final int activeTasks;
    private final int waiting;

    public ExecutorStats(int activeTasks, int waiting) {
        this.activeTasks = activeTasks;
        this.waiting = waiting;
    }

    public boolean tasksComplete() {
        return activeTasks == 0 && waiting == 0;
    }
}
