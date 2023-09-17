package com.Payment.CreateAPI.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.Payment.transaction.model.PaymentInstruction;
import com.RestApi.BuildingBlocks.PaymentEndPoints;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestTheUtilities {

	ObjectMapper objectmapper;
	PaymentInstruction paymentInstruction;
	PaymentEndPoints payment;

	@BeforeClass
	
	public void datasetup() throws IOException
	{
		objectmapper=JsonMapper.builder().addModule(new JavaTimeModule())
				     .enable(SerializationFeature.INDENT_OUTPUT).build();
		
		//objectmapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		paymentInstruction=objectmapper.readValue(new File("target/TestData/Payload/CreateToken.json"), PaymentInstruction.class);
		
	}
	
	@Test
	public void fetchTheRequestData() throws FileNotFoundException
	{
		System.out.println(payment.fetchDataFromRequest("items.OrderBlocks.ProductCode"));
	}
	
}
