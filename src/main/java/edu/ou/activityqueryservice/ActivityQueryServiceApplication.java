package edu.ou.activityqueryservice;

import edu.ou.coreservice.annotation.BaseQueryAnnotation;
import org.springframework.boot.SpringApplication;

@BaseQueryAnnotation
public class ActivityQueryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivityQueryServiceApplication.class, args);
    }

}
