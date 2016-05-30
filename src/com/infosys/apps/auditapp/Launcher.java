package com.infosys.apps.auditapp;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Launcher {

	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public static void main(String[] args) {
		String jobFile = args[0];
		System.out.println("Starting job.");
		
		scheduler.scheduleAtFixedRate(() -> {
			try {
				System.out.println("Executing job for " + jobFile);
				new WebsiteCaptureTask().perform(jobFile);
			} catch (Exception e) {	//Comment : I am catching exception here so that next execution is not suppressed.
				System.err.println("Error occurred when executing " + jobFile + " at " + new Date());	//Comment : use logger to log to file since this will be a background job. 
				e.printStackTrace();
			}
			System.out.println("Job successfull for " + jobFile);
		}, 0, 24, TimeUnit.HOURS);

	}
}
