package it.sevenbits.seabattle.core.util.timer.tasks.session;

import java.sql.Date;
import java.sql.Timestamp;

public class TasksScheduler {
    public static Long getExecutionTime(SeaTask seaTask) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() + seaTask.getDelayBeforeExecution());
        return timestamp.getTime();
    }
}
