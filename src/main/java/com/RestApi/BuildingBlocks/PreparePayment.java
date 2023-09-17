package com.RestApi.BuildingBlocks;

import java.io.IOException;

import org.json.JSONObject;

public class PreparePayment {
	VolpayPaymentSubmit volpaysubmit=new VolpayPaymentSubmit();
	public JSONObject[] volpaylogin() throws IOException{
		JSONObject logindata=volpaysubmit.readVolanteFile("/volanteLoginEng2.json");
		JSONObject paymentdata=volpaysubmit.readVolanteFile("/type10.json");
		JSONObject pmtData=volpaysubmit.createUniquePayment(paymentdata, "");
		return new JSONObject[] {logindata,pmtData};
	}

}
