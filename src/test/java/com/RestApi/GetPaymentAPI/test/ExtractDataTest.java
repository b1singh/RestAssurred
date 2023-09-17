package com.RestApi.GetPaymentAPI.test;

import org.testng.annotations.Test;

import com.RestApi.BuildingBlocks.PaymentEndPoints;

import io.restassured.response.Response;

public class ExtractDataTest {
	
	@Test
	public void GetBookingDetails()
	{
		Response response=PaymentEndPoints.getdetails();
		String title=response.jsonPath().get("title");
		
	}

}
