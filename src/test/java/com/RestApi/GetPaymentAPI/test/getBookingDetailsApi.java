package com.RestApi.GetPaymentAPI.test;

import org.testng.annotations.Test;

import com.RestApi.BuildingBlocks.PaymentEndPoints;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class getBookingDetailsApi {

	@Test
	public void GetBookingDetails()
	{
		Response response=PaymentEndPoints.getBookingDetails();
		response.then().assertThat().statusCode(200).log().all();
		
		System.out.println(response.asString());
		
		JsonPath js =new JsonPath(response.asString());
		String bookingid= js.getString("bookingid");
		System.out.println(bookingid);
		
		String empName=js.get("company.employee.id");
		
	}
}
