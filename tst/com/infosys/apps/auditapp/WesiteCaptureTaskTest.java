package com.infosys.apps.auditapp;

import java.util.Arrays;

import org.junit.Test;

public class WesiteCaptureTaskTest {

	@Test
	public void testWebsiteCaptureByUrls() throws Exception {
		new WebsiteCaptureTask().perform(Arrays.asList("http://www.google.com" , "http://www.amazon.com"));
	}
}
