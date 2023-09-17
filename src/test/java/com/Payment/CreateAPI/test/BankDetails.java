package com.Payment.CreateAPI.test;

import java.lang.invoke.SerializedLambda;

import org.junit.BeforeClass;
import org.testng.annotations.Test;

import com.RestApi.BuildingBlocks.PaymentEndPoints;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.restassured.response.Response;

public class BankDetails {
	
	ObjectMapper objectMapper;
	PaymentEndPoints paymentendpoints=new PaymentEndPoints();
	private static final String REQUEST_ID="x-request-id";
	private static final String REQUEST_ID_VALUE="1234";
	
	@BeforeClass
	public void dataSetUp() {
		objectMapper=JsonMapper.builder()
				     .addModule(new JavaTimeModule())
				     .enable(SerializationFeature.INDENT_OUTPUT)
				     .build();
	}
	
	@Test
	public void getBankDetails() {
		Response response=paymentendpoints.getBankDetailsReferenceDataResponse("REQUEST_ID_VALUE", "ABA", "011234");
		response.then().assertThat().statusCode(200).assertThat().header(REQUEST_ID, REQUEST_ID_VALUE).assertThat().log();
	}

	
	
}
