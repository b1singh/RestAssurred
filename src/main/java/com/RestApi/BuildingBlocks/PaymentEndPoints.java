package com.RestApi.BuildingBlocks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.Payment.transaction.model.ComprensivePaymentInfo;
import com.Payment.transaction.model.PaymentInstruction;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import groovy.time.BaseDuration.From;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class PaymentEndPoints {
	
	JsonPath js;
	ObjectMapper objectMapper;
	
	public PaymentEndPoints() {
		// TODO Auto-generated constructor stub
		objectMapper=JsonMapper.builder()
				     .addModule(new JavaTimeModule())
				     .enable(SerializationFeature.INDENT_OUTPUT)
				     .build();
	}
	
	public static Response volanteSendPaymentAKS(String seassionid,String paymentreq) {
		Response resposne=RestAssured.given().relaxedHTTPSValidation()
				          .contentType("application/json")
				          .header("Authorization","SessionTojen:"+seassionid)
				          .accept("application/json")
				          .body(paymentreq).log().all()
				          .when().post(Routes.createBookingTokenURL);
				         
				return resposne;
	}
	public static Response volanteLoginEngg2(String sessiontoken, String paymentreq) {
		Response resposne=RestAssured.given().relaxedHTTPSValidation()
				          .contentType("application/json")
				          .header("Authorization","SessionTojen:"+sessiontoken)
				          .accept("application/json")
				          .body(paymentreq).log().all()
				          .when().post(Routes.createBookingTokenURL);
				         
				return resposne;
	}
	
	public Response CreatePaymentReq(String payload) {
		RequestSpecification spec=RestAssured.given().relaxedHTTPSValidation()
				                  .auth().oauth2("accesstoken")
				                  .queryParam("approvalrequired", "true")
				                  .header("content-Type","application/json")
				                  .accept("application/json")
				                  .header("x-request-id","")
				                  .header("x-xlient-id","1234").body(payload).log().all();
			Response response=spec.when().post(Routes.createBookingTokenURL);
		return response;
				                  
	}
	public Response CreatePaymentReq(PaymentInstruction payload) {
		RequestSpecification spec=RestAssured.given().relaxedHTTPSValidation()
				                  .auth().oauth2("accesstoken")
				                  .queryParam("approvalrequired", "true")
				                  .header("content-Type","application/json")
				                  .accept("application/json")
				                  .header("x-request-id","")
				                  .header("x-xlient-id","1234").body(payload).log().all();
			Response response=spec.when().post(Routes.createBookingTokenURL);
		return response;
				                  
	}
	public Response getPaymentResponse(String EndtoEndIdentification) {
		RequestSpecification spec=RestAssured.given().relaxedHTTPSValidation()
				                  .auth().oauth2("authorizationToken")
				                  .header("content-type","application/json")
				                  .accept("application/json")
				                  .header("x-request-id","id")
				                  .header("x-client-id","1234")
				                  .body("string").log().all();
		Response response=spec.when().get(Routes.getBookingDetailsUrl+EndtoEndIdentification+"/Outbound");
		return response;
	}
	public Response getPaymentResponseWithHeaderParam(String EndtoEndIdentification, String xRequestId,String xClientId) {
		RequestSpecification spec=RestAssured.given().relaxedHTTPSValidation()
				                  .auth().oauth2("authorizationToken")
				                  .header("content-type","application/json")
				                  .accept("application/json")
				                  .header("x-request-id",xRequestId)
				                  .header("x-client-id",xClientId)
				                  .body("string").log().all();
		Response response=spec.when().get(Routes.getBookingDetailsUrl+EndtoEndIdentification+"/Outbound");
		return response;
	}
	// when we want to fetch the data in the list abd want to validate by pojo class then how i can.
		public List<ComprensivePaymentInfo> searchPayment(String fromdateTime, String accesstoken){
			return List.of(RestAssured.given().relaxedHTTPSValidation().pathParam("key", "search").queryParam("fromDateTime", fromdateTime)
					.auth().oauth2(accesstoken)
					.queryParam("direction", "Outbound")
					.queryParam("maxRecord", "100")
					.header("Content-Type","application/json")
					.accept("application/json")
					.header("x-request-id","")
					.header("x-client-id","1234")
					.when().get("SearchEndPointUrl")
					.then()
					.assertThat().log().all().statusCode(200)
					.extract().response().as(ComprensivePaymentInfo.class)
					);
		}
    public Response searchSinglePayment(String FromdateTime, String direction,String maxrecord,String requestId, String clientid) {
    	  Response response= RestAssured.given().relaxedHTTPSValidation().pathParam("key", "search").queryParam("fromDateTime", FromdateTime)
    			          .auth().oauth2("accessToken")
    			          .queryParam("dicrection", direction)
    			          .queryParam("maxRecord", maxrecord)
    			          .header("Content-Type","application/json")
    			          .accept("application/json")
    			          .header("x-request-id","1234")
    			          .header("x-client-id","1234")
    			          .when().get(Routes.getBookingDetailsUrl)
    			          .then()
    			          .assertThat().log().all().statusCode(200)
    			          .extract().response();
    	return response;
    	
    			          
    }
    
    public Response searchPaymentResponse() {
  	  Response response= RestAssured.given().relaxedHTTPSValidation().pathParam("key", "search").queryParam("fromDateTime", "2023-10-09")
  			          .auth().oauth2("accessToken")
  			          .queryParam("dicrection", "OUTBOUND")
  			          .queryParam("maxRecord", "10")
  			          .header("Content-Type","application/json")
  			          .accept("application/json")
  			          .header("x-request-id","1234")
  			          .header("x-client-id","1234")
  			          .when().get(Routes.getBookingDetailsUrl);
  	return response;
  	
  			          
  }
    public Response getPaymentRetrival() {
    	RequestSpecification spec=RestAssured.given().relaxedHTTPSValidation()
                .auth().oauth2("authorizationToken")
                .header("content-type","application/json")
                .accept("application/json")
                .header("x-request-id","id")
                .header("x-client-id","1234")
                .body("string").log().all();
Response response=spec.when().get(Routes.getBookingDetailsUrl);
return response;
    }
    public List<ComprensivePaymentInfo> searchPayment1(String fromdateTime,String accesstoken){
		Response response=  RestAssured.given().relaxedHTTPSValidation().pathParam("key", "search").queryParam("fromDateTime", fromdateTime)
				.auth().oauth2(accesstoken)
				.queryParam("direction", "Outbound")
				.queryParam("maxRecord", "100")
				.header("Content-Type","application/json")
				.accept("application/json")
				.header("x-request-id","")
				.header("x-client-id","1234")
				.when().get("SearchEndPointUrl")
				.then()
				.assertThat().log().all().statusCode(200)
				.extract().response();
		return List.of(response.as(ComprensivePaymentInfo.class));
		
	}
	public static org.json.JSONObject ReadDataFromRequest() throws FileNotFoundException
	{
		File file=new File(System.getProperty("user.dir")+"\\target\\TestData\\Payload\\Test.json");
		FileInputStream fis=new FileInputStream(file);
		InputStreamReader inputstream=new InputStreamReader(fis);
		JSONTokener jt=new JSONTokener(inputstream);
		org.json.JSONObject data=new org.json.JSONObject(jt);
		return data;
	}
	public static String fetchDataFromRequest(String value) throws FileNotFoundException
	{
		String valdt=null;
		JSONObject paymentdata=ReadDataFromRequest();
		String payment_Data=paymentdata.toString();
		JsonPath jp6=new JsonPath(payment_Data);
		valdt=jp6.getString(value);
		
		return value;
		
	}
	public JSONObject createUpdatedrequestWithDynamicValue(JSONObject data)
	{
		JSONObject items=data.getJSONObject("items");
		items.put("orderID", "1234");
		items.put("orderInvoiceNo", "abc@123");
		return items;
	}
	
       
	public static void convertStringIntoJSON(String str)
	{
		str="{'userId':'bhupender','Password':'hello'}";
		JsonPath json=new JsonPath(str);
		System.out.println(json.getString("password"));
		// o/p is hello
	}
	
	public static Response getBookingDetails()
	{
		Response response=RestAssured.given().header("Content-Type", "application/json").accept("application/json")
				          .log().all().when().get(Routes.getBookingDetailsUrl);
		return response;
	}
	public static Response getdetails() {
		Response response=RestAssured.given().when().get("url").then().assertThat().statusCode(200).log().all().extract().response();
		return response;
	}
	
	public static Response CreateBookingRequest(String payload)
	{
		Response response=RestAssured.given().relaxedHTTPSValidation()
				          .queryParam("approvalRequired", "true")
				          .header("Content-Type","application/json")
				          .accept("application/json")
				          .header("username","admin")
				          .header("password","password123")
				          .body(payload).log().all()
				          .when().post(Routes.createBookingTokenURL);
		return response;
	}
	public Response getBankDetailsReferenceDataResponse(String requestId,String identifierType,String identifiervalue) {
		
		RequestSpecification spec=RestAssured.given().relaxedHTTPSValidation()
				                  .auth().oauth2("accessToken")
				                  .header("Content-Type","application/json")
				                  .accept("application/json")
				                  .header("x-request-id",requestId)
				                  .header("identificationType",identifierType)
				                  .header("identifiervalue",identifiervalue)
				                  .body("string").log().all();
		Response response=spec.when().get(Routes.getBookingDetailsUrl);
		return response;
	}
	
	
}
