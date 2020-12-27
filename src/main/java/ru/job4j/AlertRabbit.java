package ru.job4j;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    public static void main(String[] args) {
        try {
            Properties log = new Properties();
            log.load(AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties"));
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDetail job = JobBuilder.newJob(Rabbit.class).build();
            SimpleScheduleBuilder times = SimpleScheduleBuilder.
                    simpleSchedule().
                    withIntervalInSeconds(Integer.parseInt(log.getProperty("rabbit.interval"))).
                    repeatForever();
            Trigger trigger = TriggerBuilder.
                    newTrigger().
                    startNow().
                    withSchedule(times).build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException | IOException e) {
            e.printStackTrace();
        }
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println("Rabbit runs here...");
        }
    }
}
