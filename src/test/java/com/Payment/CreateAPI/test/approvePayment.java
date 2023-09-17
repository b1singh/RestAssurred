package com.Payment.CreateAPI.test;

import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class approvePayment {
	
	@Test
	public void validateApporvalRequest(ITestContext context) {
		ISuite suite=context.getSuite();

		System.out.println("suite is"+suite);
	}

}
