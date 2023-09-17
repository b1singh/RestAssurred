package com.RestApi.BuildingBlocks;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;



public class PaymentFlow {
	ObjectMapper objectmapper;
	
	public PaymentFlow(){
		objectmapper=JsonMapper.builder().addModule(new JavaTimeModule())
			     .enable(SerializationFeature.INDENT_OUTPUT).build();
	}
	
	public static  DocumentContext loadPaymentAsJsonPath(String id) throws IOException {
		String randomid=RandomStringUtils.randomAlphabetic(16);
		return JsonPath.parse(new File("src/main/resources/payment-"+id+".json"))
				  .set("$.EndToEndIdentification", randomid)
				  .set("$.InstructionIdentification", randomid)
				  .set("$.InstructedAmount.Amount", RandomStringUtils.randomNumeric(7))
				  .set("$.RequestedExecutionDate", LocalDate.now().format(DateTimeFormatter.ISO_DATE));
	}

}
