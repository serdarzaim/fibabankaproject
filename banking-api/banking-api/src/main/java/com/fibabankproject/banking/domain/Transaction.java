package com.fibabankproject.banking.domain;

public class Transaction {

		private Integer transactionId;
	    private Integer senderAccountId;
	    private Integer senderUserId;
	    private Integer recipientAccountId;
	    private Integer recipientUserId;
	    private Double amount;
	    private String note;
	    private Long transactionDate;
	    
	public Transaction(Integer transactionId, Integer senderAccountId, Integer senderUserId, Integer recipientAccountId,
				Integer recipientUserId, Double amount, String note, Long transactionDate) {
			this.transactionId = transactionId;
			this.senderAccountId = senderAccountId;
			this.senderUserId = senderUserId;
			this.recipientAccountId = recipientAccountId;
			this.recipientUserId = recipientUserId;
			this.amount = amount;
			this.note = note;
			this.transactionDate = transactionDate;
		}	    
	    
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public Integer getSenderAccountId() {
		return senderAccountId;
	}
	public void setSenderAccountId(Integer senderAccountId) {
		this.senderAccountId = senderAccountId;
	}
	public Integer getSenderUserId() {
		return senderUserId;
	}
	public void setSenderUserId(Integer senderUserId) {
		this.senderUserId = senderUserId;
	}
	public Integer getRecipientAccountId() {
		return recipientAccountId;
	}
	public void setRecipientAccountId(Integer recipientAccountId) {
		this.recipientAccountId = recipientAccountId;
	}
	public Integer getRecipientUserId() {
		return recipientUserId;
	}
	public void setRecipientUserId(Integer recipientUserId) {
		this.recipientUserId = recipientUserId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Long getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Long transactionDate) {
		this.transactionDate = transactionDate;
	}
}
