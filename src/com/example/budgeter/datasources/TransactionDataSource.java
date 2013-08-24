package com.example.budgeter.datasources;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.budgeter.models.Transaction;
public class TransactionDataSource extends AbstractDataSource {


	private String TABLE = "transactions";
	
	private String TRANS_ID = "id",
			TRANS_AMOUNT = "amount",
			TRANS_CAT_ID = "category_id",
			TRANS_TYPE = "transaction_type";
	
	public TransactionDataSource(Context context)
	{
		super(context);
	}

	
	public long createRecord(float amount, int category_id, int transaction_type) {
		
		ContentValues values = new ContentValues();  
		
		values.put(TRANS_AMOUNT,amount);
		values.put(TRANS_CAT_ID,category_id);
		values.put(TRANS_TYPE,transaction_type);
		
		return database.insert(TABLE, null, values);  
	}
	
	public long createRecord(Transaction transaction)
	{
		return 0;
	}

	
	public ArrayList<Transaction> selectAllRecords() {
		String[] cols = {TRANS_ID,TRANS_AMOUNT,TRANS_CAT_ID,TRANS_TYPE};
		// TODO Auto-generated method stub
		Cursor cursor = database.query(true,TABLE,cols,null,null,null,null,null,null);
		
		cursor.moveToFirst();
		
		int index_id = cursor.getColumnIndex(TRANS_ID);
		int index_amount = cursor.getColumnIndex(TRANS_AMOUNT);
		int index_cat_id = cursor.getColumnIndex(TRANS_CAT_ID);
		int index_trans_type = cursor.getColumnIndex(TRANS_TYPE);
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		while(!cursor.isAfterLast())
		{
			transactions.add(new Transaction(cursor.getInt(index_id),
					cursor.getFloat(index_amount),
					cursor.getInt(index_cat_id),
					cursor.getInt(index_trans_type)));
			cursor.moveToNext();
		}
		
		return transactions;
		
	}
	
	public float getTotalTransactions()
	{
		String[] cols = {TRANS_AMOUNT};
		Cursor cursor = database.query(true,TABLE,cols,null,null,null,null,null,null);
		cursor.moveToFirst();
		int index_amount = cursor.getColumnIndex(TRANS_AMOUNT);
		float total = 0;
		while(!cursor.isAfterLast())
		{
			total += cursor.getFloat(index_amount);
			cursor.moveToNext();
		}
		
		return total;
		
	}
}
