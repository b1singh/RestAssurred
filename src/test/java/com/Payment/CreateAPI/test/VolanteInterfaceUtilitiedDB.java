package com.Payment.CreateAPI.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.Assert;

import com.RestApi.BuildingBlocks.VolanteDbExecute;
import com.RestApi.BuildingBlocks.volanteDbConnection;

public class VolanteInterfaceUtilitiedDB {
	String resultstring;
	ResultSet resultset;
	
   VolanteDbExecute dbexecute=new VolanteDbExecute();
   public void fedOutboundData(String paymentid) throws SQLException {
	   Connection conn=volanteDbConnection.createDbConnection();
	   resultstring=dbexecute.selectAzureSQLPaymentAudit(conn, "PAYMENT DUPLICATE CHECK", paymentid, "DESCRIPTION");
	   Assert.assertEquals(resultstring, "");
	   resultset=dbexecute.selectAzureSQLBankInteractionData(conn, "CUSTOMERACCOUNTLOOKUP", "REQUEST", paymentid, "OBJECT");
	   Assert.assertEquals(resultset, "empty result set fro book transfer customerlookup first request");
	   resultset.next();
	   resultstring=new String(resultset.getBytes(1));
	   Assert.assertEquals(resultstring, "Success","Account lookup request failed");
	   resultset.next();
	   
   }
}
