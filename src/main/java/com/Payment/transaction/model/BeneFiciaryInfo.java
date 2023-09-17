package com.Payment.transaction.model;

@lombok.AllArgsConstructor
@lombok.RequiredArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder

public class BeneFiciaryInfo {

	private String bankName;
	private PostalAddress bankAddress;
	private String accountNumber;
	private String name;
	private PostalAddress address;
	
	public BeneFiciaryInfo bankName(String bankName) {
		this.bankName=bankName;
		return this;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName=bankName;
	}
	
	public BeneFiciaryInfo bankAddress(PostalAddress bankAddress) {
		this.bankAddress=bankAddress;
		return this;
	}
	
	public PostalAddress getBankAddress() {
		return bankAddress;
	}
	public void setBankName(PostalAddress bankAddress) {
		this.bankAddress=bankAddress;
	}
	
	public BeneFiciaryInfo accountNumber(String accountNumber) {
		this.accountNumber=accountNumber;
		return this;
	}
	
	public String getaccountNumber() {
		return accountNumber;
	}
	public void setaccountNumber(String accountNumber) {
		this.accountNumber=accountNumber;
	}
	
	
	public BeneFiciaryInfo name(String name) {
		this.name=name;
		return this;
	}
	
	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name=name;
	}

	
	public BeneFiciaryInfo address(PostalAddress address) {
		this.address=address;
		return this;
	}
	
	public PostalAddress getAddress() {
		return address;
	}
	public void setAddress(PostalAddress address) {
		this.address=address;
	}
	
	


}
