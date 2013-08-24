package com.example.budgeter.models;

public class Transaction extends Model {

	
	private int id,
		category_id,
		transaction_type;
	private float amount;
	
	public Transaction()
	{
		
	}
	public Transaction(int id, float amount, int category_id, int transaction_type)
	{
		this.id = id;
		this.amount = amount;
		this.category_id = category_id;
		this.transaction_type = transaction_type;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void setCategoryId(int id)
	{
		this.category_id = id;
	}
	public int getCategoryId()
	{
		return this.category_id;
	}
	
	public void setTransactionType(int id)
	{
		this.transaction_type = id;
	}
	public int getTransactionType()
	{
		return this.transaction_type;
	}
	
	public void setAmount(float amount)
	{
		this.amount = amount;
	}
	public float getAmount()
	{
		return this.amount;
	}
	
}
