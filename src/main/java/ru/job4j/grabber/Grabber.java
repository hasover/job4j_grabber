package ru.job4j.grabber;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.job4j.html.SqlRuParse;
import java.io.IOException;
import java.util.Properties;

public class Grabber implements Grab {
    private final Properties cfg = new Properties();

    public Store store() {
        return new PsqlStore(cfg);
    }

    public Scheduler scheduler() {
        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return scheduler;
    }

    @Override
    public void init(Parse parse, Store store, Scheduler scheduler) throws SchedulerException {
        JobDataMap data = new JobDataMap();
        data.put("parse", parse);
        data.put("store", store);

        JobDetail jobDetail = JobBuilder.newJob(GrabJob.class).usingJobData(data).build();
        SimpleScheduleBuilder times = SimpleScheduleBuilder.
                simpleSchedule().
                withIntervalInSeconds(Integer.parseInt(cfg.getProperty("time"))).
                repeatForever();
        Trigger trigger = TriggerBuilder.newTrigger().startNow().withSchedule(times).build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public static class GrabJob implements Job {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) {
            JobDataMap data = jobExecutionContext.getJobDetail().getJobDataMap();
            Parse sqlParser = (Parse) data.get("parse");
            Store sqlStore = (Store) data.get("store");
            for (Post post : sqlParser.list("https://www.sql.ru/forum/job-offers")) {
                sqlStore.save(post);
            }
        }
    }

    public void cfg() {
        try {
            cfg.load(Grabber.class.getClassLoader().getResourceAsStream("app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SchedulerException {
        Grabber grabber = new Grabber();
        grabber.cfg();
        Scheduler scheduler = grabber.scheduler();
        Store store = grabber.store();
        grabber.init(new SqlRuParse(), store, scheduler);
    }
}
