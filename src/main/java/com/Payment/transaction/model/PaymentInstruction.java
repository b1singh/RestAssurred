package com.Payment.transaction.model;

import java.time.LocalDate;

public class PaymentInstruction {
	
	private String username;
	private String password;
	private LocalDate requestedExecutionDate;
	private String endtoendIdentification;
	private String instructionId;
	public PaymentInstruction username(String username)
	{
		this.username=username;
		return this;
	}
	public void setusername(String username)
	{
		this.username=username;
	}
	public String getusername()
	{
		return username;
	}
	public PaymentInstruction password(String password)
	{
		this.password=password;
		return this;
	}
	public void setPassword(String password)
	{
		this.password=password;
	}
	
	public String getPassword()
	{
		return password;
	}
	public void setRequestedExecutionDate(LocalDate requestedExecutionDate) {
		this.requestedExecutionDate=requestedExecutionDate;
	}
	public LocalDate getRequestedExecutionDate() {
		return requestedExecutionDate;
	}
	public void setEndToEndIdentification(String endtoendIdentification) {
		this.endtoendIdentification=endtoendIdentification;
	}
	public String getEndToEndIdentification() {
		return endtoendIdentification;
	}
	public void setInstructionId(String instructionId) {
		this.instructionId=instructionId;
	}
	public String getInstructionId() {
		return instructionId;
	}
	
	

}
