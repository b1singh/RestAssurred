package com.Payment.CreateAPI.test;



import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.Payment.transaction.model.ComprensivePaymentInfo;
import com.Payment.transaction.model.PaymentInstruction;
import com.RestApi.BuildingBlocks.PaymentEndPoints;
import com.RestApi.BuildingBlocks.PaymentFlow;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;

import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchPaymentAPI {
	ObjectMapper objectmapper;
	PaymentInstruction paymentInstruction;
	PaymentEndPoints paymentEndPoints;
	ComprensivePaymentInfo expectedComprensiveInfo;
	String jsonstring;
	
	@BeforeClass
	@JsonIgnoreProperties(ignoreUnknown = true)
	public void datasetup() throws IOException
	{
		jsonstring=PaymentFlow.loadPaymentAsJsonPath("submitPaymentBase").jsonString();
		objectmapper=JsonMapper.builder().addModule(new JavaTimeModule())
				     .enable(SerializationFeature.INDENT_OUTPUT).build();
		
		//objectmapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		paymentInstruction=objectmapper.readValue(new File("target/TestData/Payload/CreateToken.json"), PaymentInstruction.class);
		paymentInstruction.setRequestedExecutionDate(LocalDate.parse(LocalDate.now().plusDays(1).toString()));
		expectedComprensiveInfo=objectmapper.readValue("expected file path", ComprensivePaymentInfo.class);
		
	}
	
	@Test(description = "SerachPaymentRequestAPI")
	public void SerachPaymentValidationFromCreatePaymentAPI() throws JsonProcessingException {
		String str=RandomStringUtils.randomAlphabetic(16);
		paymentInstruction.setEndToEndIdentification(str);
		paymentInstruction.setInstructionId(str);
		
		Response response=paymentEndPoints.CreatePaymentReq(objectmapper.writeValueAsString(paymentInstruction));
		response.then().assertThat().statusCode(201).log().all();
		io.restassured.path.json.JsonPath js=new io.restassured.path.json.JsonPath(response.asString());
		String status=js.getString("paymentStatus");
		String creationDateTime=js.getString("creationDateTime");
		String endtoendIdentification=js.getString("endtoendIdentification");
		List<ComprensivePaymentInfo>listofpayments=paymentEndPoints.searchPayment(creationDateTime, "accessToken");
		ComprensivePaymentInfo matchingpayment=new ComprensivePaymentInfo();
		matchingpayment.setEndToEndIdentification(endtoendIdentification);
		matchingpayment=listofpayments.stream().filter((c)->c.getEndToEndIdentification().equals(endtoendIdentification)).findFirst().get();
		Assert.assertEquals(paymentInstruction.getEndToEndIdentification(), matchingpayment.getEndToEndIdentification());
		
	}
	
	
	@Test(description = "searchpaymentAPI")
	public void searchPaymentValidation(ITestContext context)
	{
		List<ComprensivePaymentInfo> listofPayment=paymentEndPoints.searchPayment("", "");
		ComprensivePaymentInfo matchingpayment=new ComprensivePaymentInfo();
		matchingpayment=listofPayment.stream().filter((c) -> c.gettokenId().equals("asdf@1123")).findFirst().get();
		// lets i validate the two json with asserthat and skip the date at time validatiom
	    assertThat(expectedComprensiveInfo).isNotNull().isEqualToIgnoringGivenFields(matchingpayment, "date");
	    // another way do the validation
	    Assert.assertEquals(matchingpayment.gettokenId(), paymentInstruction.getPassword());
	}
	
	// few methods from serach payment API.
	@Test
	public void sucessful_payment_search_searchAPI(String jsonstring) {
		Response response=paymentEndPoints.CreatePaymentReq(jsonstring);
		response.then().assertThat().statusCode(200).log().all();
		io.restassured.path.json.JsonPath js=new io.restassured.path.json.JsonPath(response.asString());
		String endtoendid=js.getString("ENDTOENDID");
		Response response1=paymentEndPoints.searchPaymentResponse();
		response1.then().assertThat().statusCode(200).log().all();
		
		
	}
	// values validated with data provider
	
	@Test(dataProvider = "headerValueNull", description = "headerValueNull")
	public void headerValueNull(String testSuffix,String xClientId,String xrequestId) {
		Response response=paymentEndPoints.CreatePaymentReq(jsonstring);
		response.then().assertThat().statusCode(200).assertThat().header("x-request-id", "1234");
		
		io.restassured.path.json.JsonPath js=new io.restassured.path.json.JsonPath(response.asString());
		String endToEndIdentofication=js.getString("endToEndIdentofication");
		
		Response searchresponse=paymentEndPoints.getPaymentResponseWithHeaderParam(endToEndIdentofication, xrequestId, xClientId);
	
		searchresponse.then().assertThat().statusCode(200).assertThat().header("x-request-id", "1234");
		
	}
	@Test(dataProvider = "headerValueEmpty", description = "headerValueEmpty")
	public void headerValueEmpty(String testSuffix,String xClientId,String xrequestId) {
		Response response=paymentEndPoints.CreatePaymentReq(jsonstring);
		response.then().assertThat().statusCode(200).assertThat().header("x-request-id", "1234");
		
		io.restassured.path.json.JsonPath js=new io.restassured.path.json.JsonPath(response.asString());
		String endToEndIdentofication=js.getString("endToEndIdentofication");
		
		Response searchresponse=paymentEndPoints.getPaymentResponseWithHeaderParam(endToEndIdentofication, xrequestId, xClientId);
	
		searchresponse.then().assertThat().statusCode(200).assertThat().header("x-request-id", xrequestId);
		
	}
	@Test(dataProvider = "emptyInvalidNullEndtoEndID")
	public void emptyInvalidNullEndtoEndId(String testSuffix,String jsonPathBoundaryEleemnt,String newValue) {
		Response response=paymentEndPoints.CreatePaymentReq(jsonstring);
		response.then().assertThat().statusCode(200).log().all();
		
		Response searchresponse=paymentEndPoints.getPaymentResponse(newValue);
		searchresponse.then().assertThat().statusCode(200).log().all();
		
	}
	@Test(dataProvider = "invalidEndToEndId",description ="invalidEndToEndId" )
	public void invalidEndToEndId(String testSuffix,String jsonPathBoundaryEleemnt,String newValue) {
		Response response=paymentEndPoints.CreatePaymentReq(jsonstring);
		response.then().assertThat().statusCode(200).assertThat().header("x-request-id", "fbedd").log();
		
		Response searchresponse=paymentEndPoints.getPaymentResponse(newValue);
		searchresponse.then().assertThat().statusCode(200).assertThat().header("x-request-id", "fbedd").log();
		
	}
	@Test(dataProvider = "clientidInvalid",description ="clientidInvalid" )
	public void clientidInvalid(String testSuffix,String xClientId,String xrequestId) {
		Response response=paymentEndPoints.CreatePaymentReq(jsonstring);
		response.then().assertThat().statusCode(200).assertThat().header("x-request-id", "fbedd").log();
		
		io.restassured.path.json.JsonPath js=new io.restassured.path.json.JsonPath(response.asString());
		String endToEndIdentofication=js.getString("endToEndIdentofication");
		
		Response searchresponse=paymentEndPoints.getPaymentResponseWithHeaderParam(endToEndIdentofication, xrequestId, xClientId);
		searchresponse.then().assertThat().statusCode(200).assertThat().header("x-request-id", xrequestId).log();
		
	}
	@DataProvider(name = "emptyInvalidNullEndtoEndID")
	public Object[][] emptyInvalidNullEndtoEndID(){
		return new Object[][] {
			{"EndToEndIdentification is empty","$.EndToEndIdentification "," "},
			{"EndToEndIdentification is null","$.EndToEndIdentification ",null},
			{"EndToEndIdentification is less than 16 digit","$.EndToEndIdentification","12345"}
		};
	}
	@DataProvider(name = "clientIdInvalid")
	public Object[][] clientInvalid(){
		return new Object[][] {
			{"x-client-id is invalid","4321","1234"}
		};
	}
	@DataProvider(name = "headerValueNull")
	public Object[][] headerValueEmpty(){
		return new Object[][] {
			{"x-client-id is empty","","1234"}
		};
	}
	@DataProvider(name = "headerValueEmpty")
	public Object[][] headerValueNull(){
		return new Object[][] {
			{"x-client-id is null",null,"1234"},
			{"x-request-id is null","1234",null}
		};
	}
	@DataProvider(name = "invalidEndToEndId")
	public Object[][] invalidEndToEndId(){
		return new Object[][] {
			{"EndToEndIdentification is invalid","$.EndToEndIdentification","11111222233"},
		};
	}
	

}
