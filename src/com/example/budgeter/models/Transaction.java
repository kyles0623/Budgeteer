package com.example.budgeter.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.budgeter.helpers.FormatHelper;

public class Transaction extends Model {

	Category category;
	private int id,
		category_id,
		transaction_type;
	private String description;
	private float amount;
	private String created;
	public Transaction()
	{
		this.id = -1;
	}
	public Transaction(int id, float amount, Category category, int transaction_type,String description,String created)
	{
		this.id = id;
		this.amount = amount;
		this.category = category;
		this.transaction_type = transaction_type;
		this.description = description;
		this.created = created;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getDescription()
	{
		return this.description;
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
	public Category getCategory()
	{
		return this.category;
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
	public String toString()
	{
		String price = FormatHelper.formatCurrency(this.amount);
		String formatted = "";
		if(this.transaction_type == 1)
		{
			price = "- "+price;
		}
		try
		{
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.created);
			formatted = new SimpleDateFormat("MMM dd, yyyy").format(date);
		}
		catch(Exception e)
		{
			
		}
		
		
		
		return price+" : "+this.category.getName()+" : "+formatted;
	}
	
}
