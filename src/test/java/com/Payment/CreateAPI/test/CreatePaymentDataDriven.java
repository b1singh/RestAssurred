package com.Payment.CreateAPI.test;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.RestApi.BuildingBlocks.XLUtilities;

import io.restassured.RestAssured;

public class CreatePaymentDataDriven {
	
	@Test(dataProvider = "data", dataProviderClass = DataProvidersCreate.class)
	public void createPaymentWithParameters(String amount, String operator) {
		
	}
	
	
	
	

	private String postPayment(String body) {
		return RestAssured.given().relaxedHTTPSValidation()
			   .log().all()
			   .header("Content-Type","application/json")
			   .header("x-request-id","1234")
			   .header("x-xlient-id","1234")
			   .body(body)
			   .when()
			   .post("/payments")
			   .then().assertThat().statusCode(201)
			   .extract().response().asString();
	}
}
