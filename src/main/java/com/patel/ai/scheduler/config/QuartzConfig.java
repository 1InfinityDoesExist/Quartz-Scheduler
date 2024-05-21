package com.patel.ai.scheduler.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class QuartzConfig {

  private final ApplicationContext applicationContext;

  @Bean
  public AutowiringSpringBeanJobFactory getAutowiringSpringBeanJobFactory() {
    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
    jobFactory.setApplicationContext(applicationContext);
    return jobFactory;
  }

  @Bean
  @Primary
  public Scheduler getScheduler() throws SchedulerException {
    SchedulerFactory schedFactory = new StdSchedulerFactory();
    Scheduler scheduler = schedFactory.getScheduler();
    scheduler.setJobFactory(getAutowiringSpringBeanJobFactory());
    scheduler.start();
    return scheduler;
  }
}
