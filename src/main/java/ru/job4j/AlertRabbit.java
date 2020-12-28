package ru.job4j;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.sql.*;
import java.util.Properties;

public class AlertRabbit {

    public static void main(String[] args) throws Exception {
        Properties log = new Properties();
        log.load(AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties"));
        Class.forName(log.getProperty("jdbc.driver"));
        try (Connection connection = DriverManager.getConnection(
                log.getProperty("jdbc.url"),
                log.getProperty("jdbc.username"),
                log.getProperty("jdbc.password"))) {

            try (Statement statement = connection.createStatement()) {
                String sql = "create table if not exists rabbit("
                        + "id serial primary key,"
                        + "created date)";
                statement.execute(sql);
            }

            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JobDataMap data = new JobDataMap();
            data.put("connection", connection);

            SimpleScheduleBuilder times = SimpleScheduleBuilder.
                    simpleSchedule().
                    withIntervalInSeconds(Integer.parseInt(log.getProperty("rabbit.interval"))).
                    repeatForever();

            JobDetail job = JobBuilder.newJob(Rabbit.class).usingJobData(data).build();

            Trigger trigger = TriggerBuilder.newTrigger().startNow().withSchedule(times).build();

            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        }
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            Connection connection = (Connection) jobExecutionContext.
                                                 getJobDetail().
                                                 getJobDataMap().
                                                 get("connection");
            try (Statement statement = connection.createStatement()) {
                String sql = "insert into rabbit(created) VALUES (CURRENT_TIMESTAMP)";
                statement.execute(sql);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
