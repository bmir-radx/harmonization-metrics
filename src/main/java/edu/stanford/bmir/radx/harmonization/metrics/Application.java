package edu.stanford.bmir.radx.harmonization.metrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import picocli.CommandLine;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        System.setProperty("spring.main.banner-mode", "off");
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        CalculateMetricsCommand metricsCommand = applicationContext.getBean(CalculateMetricsCommand.class);
        int exitCode = new CommandLine(metricsCommand).execute(args);
        System.exit(exitCode);
    }
}