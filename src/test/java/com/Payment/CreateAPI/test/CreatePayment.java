package com.Payment.CreateAPI.test;

import java.io.File;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.Payment.transaction.model.ComprensivePaymentInfo;
import com.Payment.transaction.model.PaymentInstruction;
import com.RestApi.BuildingBlocks.PaymentEndPoints;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreatePayment {
	ObjectMapper objectmapper;
	PaymentInstruction paymentInstruction;
	ComprensivePaymentInfo expectedComprehensivePayments;

	@BeforeClass
	@JsonIgnoreProperties(ignoreUnknown = true)
	public void datasetup() throws IOException
	{
		objectmapper=JsonMapper.builder().addModule(new JavaTimeModule())
				     .enable(SerializationFeature.INDENT_OUTPUT).build();
		
		//objectmapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		paymentInstruction=objectmapper.readValue(new File("target/TestData/Payload/CreateToken.json"), PaymentInstruction.class);
	}
	
	@Test
	public void CreatePayment_test() throws JsonProcessingException
	{
		Response response=PaymentEndPoints.CreateBookingRequest(objectmapper.writeValueAsString(paymentInstruction));
		response.then().assertThat().statusCode(200).log().all();
		
		System.out.println(response.asString());
		JsonPath jp=new JsonPath(response.asString());
		Assert.assertEquals(jp.getString("token"), jp.getString("token"));
		
		
		
		
	
	}   
	
}
