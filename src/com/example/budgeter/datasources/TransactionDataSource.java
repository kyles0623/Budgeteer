package com.example.budgeter.datasources;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.budgeter.models.Transaction;
public class TransactionDataSource extends AbstractDataSource {


	private final String TABLE = "transactions";
	private CategoryDataSource categoryDB;
	private final String TRANS_ID = "id",
			TRANS_AMOUNT = "amount",
			TRANS_CAT_ID = "category_id",
			TRANS_TYPE = "transaction_type",
			TRANS_DESC = "desc",
			TRANS_CREATED = "created";
	
	public TransactionDataSource(Context context)
	{
		super(context);
		categoryDB = new CategoryDataSource(context);
	}
	public long createRecord(Transaction transaction)
	{
		ContentValues values = new ContentValues();  
		
		values.put(TRANS_AMOUNT,transaction.getAmount());
		values.put(TRANS_CAT_ID,transaction.getCategoryId());
		values.put(TRANS_TYPE,transaction.getTransactionType());
		values.put(TRANS_DESC,transaction.getDescription());
		if(transaction.getId() != -1)
		{
			String where = "id=?";
			String[] args = {transaction.getId()+""};
			return database.update(TABLE, values,where,args); 
		}
		else
		{
			return database.insert(TABLE, null, values);
		}
		 
	}
	
	public long createRecord(float amount, int category_id, int transaction_type, String description) {
		
		ContentValues values = new ContentValues();  
		
		values.put(TRANS_AMOUNT,amount);
		values.put(TRANS_CAT_ID,category_id);
		values.put(TRANS_TYPE,transaction_type);
		values.put(TRANS_DESC,description);
		return database.insert(TABLE, null, values);  
	}
	public Transaction findById(int id)
	{
		String[] cols = {TRANS_ID,TRANS_AMOUNT,TRANS_CAT_ID,TRANS_TYPE,TRANS_DESC,TRANS_CREATED};
		String where = "id=?";
		String[] where_args = {id+""};
		Cursor cursor = database.query(false,TABLE,cols,where,where_args,null,null,TRANS_CREATED+" desc",null);
		cursor.moveToFirst();
		
		int index_id = cursor.getColumnIndex(TRANS_ID);
		int index_amount = cursor.getColumnIndex(TRANS_AMOUNT);
		int index_cat_id = cursor.getColumnIndex(TRANS_CAT_ID);
		int index_trans_type = cursor.getColumnIndex(TRANS_TYPE);
		int index_desc = cursor.getColumnIndex(TRANS_DESC);
		int index_created = cursor.getColumnIndex(TRANS_CREATED);
		
		
		return new Transaction(cursor.getInt(index_id),
				cursor.getFloat(index_amount),
				categoryDB.findById(cursor.getInt(index_cat_id)),
				cursor.getInt(index_trans_type),
				cursor.getString(index_desc),
				cursor.getString(index_created)
				);
	}
	public ArrayList<Transaction> selectAllRecords() {
		String[] cols = {TRANS_ID,TRANS_AMOUNT,TRANS_CAT_ID,TRANS_TYPE,TRANS_DESC,TRANS_CREATED};
		
		// TODO Auto-generated method stub
		Cursor cursor = database.query(false,TABLE,cols,null,null,null,null,TRANS_CREATED+" desc",null);
		
		cursor.moveToFirst();
		
		int index_id = cursor.getColumnIndex(TRANS_ID);
		int index_amount = cursor.getColumnIndex(TRANS_AMOUNT);
		int index_cat_id = cursor.getColumnIndex(TRANS_CAT_ID);
		int index_trans_type = cursor.getColumnIndex(TRANS_TYPE);
		int index_desc = cursor.getColumnIndex(TRANS_DESC);
		int index_created = cursor.getColumnIndex(TRANS_CREATED);
		
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		
		while(!cursor.isAfterLast())
		{
			transactions.add(new Transaction(cursor.getInt(index_id),
					cursor.getFloat(index_amount),
					categoryDB.findById(cursor.getInt(index_cat_id)),
					cursor.getInt(index_trans_type),
					cursor.getString(index_desc),
					cursor.getString(index_created)
					)
			);
			cursor.moveToNext();
		}
		
		return transactions;
		
	}
	public void deleteRecord(int id)
	{
		String where = " id = "+id;
		
		database.delete(TABLE, where, null);
	}
	
	public void deleteAllRows()
	{
		database.delete(TABLE,null,null);
	}
	
	
	public float getTotalTransactions()
	{
		String[] cols = {TRANS_AMOUNT,TRANS_TYPE};
		Cursor cursor = database.query(false,TABLE,cols,null,null,null,null,null,null);
		
		cursor.moveToFirst();
		
		int index_amount = cursor.getColumnIndex(TRANS_AMOUNT);
		int index_trans_type = cursor.getColumnIndex(TRANS_TYPE);
		float total = 0;
		
		while(!cursor.isAfterLast())
		{
			float cur_amount = cursor.getFloat(index_amount);
			int trans_type = cursor.getInt(index_trans_type);
			if(trans_type == 1)
				total -= cur_amount;
			if(trans_type == 2)
				total += cur_amount;
			
			cursor.moveToNext();
		}
		
		return total;
		
	}
}
