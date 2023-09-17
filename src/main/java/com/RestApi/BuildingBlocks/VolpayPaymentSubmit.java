package com.RestApi.BuildingBlocks;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.fasterxml.jackson.databind.json.JsonMapper;

import io.restassured.internal.util.IOUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class VolpayPaymentSubmit {
	
	public String SubmitPaymentVolante(String path, String usecase) throws IOException {
		JSONObject data1=readVolanteFile(path);
		String sessionToken=getVolpaySessionToken("ENG2");
		data1=createUniquePayment(data1, usecase);
		Response response1=PaymentEndPoints.volanteLoginEngg2(sessionToken,data1.toString());
		response1.then().assertThat().statusCode(201).assertThat().log().all();
		
		JsonPath js=new JsonPath(response1.asString());
		String volpayStatus=js.getString("StsCd");
		String PaymentIDVolpay=js.getString("BKAssgndTxRef");
		return PaymentIDVolpay;
		
	}
	
	public JSONObject readVolanteFile(String path) throws IOException {
		InputStreamReader inputstreamreader=new InputStreamReader(getClass().getResourceAsStream(path));
		JSONTokener jt=new JSONTokener(org.apache.commons.io.IOUtils.toString(inputstreamreader));
		JSONObject data=new JSONObject(jt);
		return data;
	}
	public String getVolpaySessionToken(String env) throws IOException {
		JSONObject logindata;
		Response loginresponse=null;
		if(env.equals("ENG2")) {
			logindata=readVolanteFile("/volanteLoginEng2.json");
			loginresponse=PaymentEndPoints.volanteLoginEngg2(logindata.toString());
		}
		else {
			logindata=readVolanteFile("/volanteLogin.json");
			loginresponse=PaymentEndPoints.volanteSendPaymentAKS(logindata.toString(), env);
		}
		loginresponse.then().assertThat().statusCode(202).assertThat().log().all();
		JsonPath jp=new JsonPath(loginresponse.asString());
		String SessionToken=jp.getString("SessionToken");
		return SessionToken;
	}
	public String getCurrentdate() {
		LocalDate date=LocalDate.now();
		String formattedDateStr=DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.ENGLISH).format(date);
		return formattedDateStr;
	}
	public JSONObject createUniquePayment(JSONObject data, String usecase) {
		try {
			// PmtDtls is the root element under the Json payload
			JSONObject PmtDts=data.getJSONObject("PmtDtls");
			PmtDts.put("txid", RandomStringUtils.randomAlphabetic(16));
			String formatteddateStr=getCurrentdate();
			PmtDts.put("valDt", formatteddateStr);
			return data;
			
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

}
