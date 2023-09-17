package com.RestApi.BuildingBlocks;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;
import org.json.JSONTokener;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReadJsonAndPostToResAssurredinBodyformat {
	
	public String submitPaymentToVolante(String filename)  {
		String str=null;
		try {
		File file=new File("src/chso/resources/volpanteloginEngg2.json");
		FileInputStream fis=new FileInputStream(file);
		//BufferedReader buff=new BufferedReader(null);
		InputStreamReader inputstream=new InputStreamReader(fis);
		File file1=new File("src/chso/resources/colpay-submitpayment.json");
		FileInputStream fis1=new FileInputStream(file1);
		//BufferedReader buff=new BufferedReader(null);
		InputStreamReader pmtinputstream=new InputStreamReader(fis1);
		
		JSONTokener jt=new JSONTokener(inputstream);
		JSONTokener jt1=new JSONTokener(pmtinputstream);
		JSONObject data=new JSONObject(jt);
		JSONObject data1=new JSONObject(jt1);
		
		Response response=PaymentEndPoints.volanteLoginEngg2(null, data.toString());
		response.then().assertThat().statusCode(201).log().all();
		
		JSONObject pmtDtls=data1.getJSONObject("pmntDtls");
		pmtDtls.put("Txid", "1234");
		
		JSONObject DbtrAgt=data1.getJSONObject("DbtrAgt");
		DbtrAgt.put("mmid", "1234");
		
		
		Response response1=PaymentEndPoints.volanteSendPaymentAKS(filename, data1.toString());
		response.then().assertThat().statusCode(201).log().all();
		
		JsonPath js=new JsonPath(response1.asString());
		String paymentidvolpay=js.getString("BkAssgndRef");
		str=paymentidvolpay;
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return str;
	}

}
