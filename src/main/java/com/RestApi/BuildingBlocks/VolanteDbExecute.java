package com.RestApi.BuildingBlocks;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VolanteDbExecute {
	
	public String selectAzureSQLPaymentAudit(Connection cnn,String event,String paymentId,String output) {
		String decode=null;
		try {
			Statement statement=cnn.createStatement();
			String sql="select"+output+"from paymentaudit where paymentID="+"'"+paymentId+"'"+"And EVENT="+"'"+event+"';";
			ResultSet resultset=statement.executeQuery(sql);
			while(resultset.next()) {
				decode=resultset.getString(1);
			}
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return decode;
	}
	
	public ResultSet selectAzureSQLBankInteractionData(Connection cnn, String event,String type, String paymentid,String output) {
		ResultSet resultset=null;
		try {
			Statement statement=cnn.createStatement();
			String sql="select"+output+"from bankinteractionadat where paymentid="+"'"+paymentid+"'"+"AND RELATIONSHIP="+"'"+type+"'"+"AND INVOCATIONPOINT="+"'"+event+"' ORDER BY BIDAMP ASC;";     
			resultset=statement.executeQuery(sql);
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return resultset;
	}

}
