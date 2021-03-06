package com.example.budgeter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.budgeter.datasources.CategoryDataSource;
import com.example.budgeter.datasources.TransactionDataSource;
import com.example.budgeter.models.Category;
import com.example.budgeter.models.Transaction;

public class TransactionActivity extends Activity implements OnItemSelectedListener, OnClickListener{
	
	private Transaction transaction;
	private Category category;
	private CategoryDataSource categoryDB;
	private TransactionDataSource transactionDB;
	private AutoCompleteTextView categoryView;
	private Spinner transType;
	private EditText amountView;
	private Button addTransaction;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transaction);
		
		//Initial required database connections and the views. 
		categoryDB = new CategoryDataSource(this);
		transactionDB = new TransactionDataSource(this);
		
		amountView = (EditText)findViewById(R.id.transaction_amount);
		transType = (Spinner)findViewById(R.id.transaction_type);
		addTransaction = (Button)findViewById(R.id.add_transaction);
		
		//Used to store all the transactions information
		transaction = new Transaction();
		
		
		setCategoryView();
		setTransactionTypeView();
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if(extras != null)
		{
			
			//Set Values if we are editing
			if(extras.containsKey("transactionId"))
			{
				int id = extras.getInt("transactionId");
				this.transaction = transactionDB.findById(id);
				this.category = this.transaction.getCategory();
				amountView.setText(this.transaction.getAmount()+"");
				categoryView.setText(this.category.getName());
				
				//Note: not the best way to do this
				transType.setSelection(this.transaction.getTransactionType()-1);
				addTransaction.setText(getString(R.string.update_transaction_btn));
				
			}
		}
	}
	//Initial the Transaction Type Spinner
	private void setTransactionTypeView()
	{
		Spinner spinner = (Spinner) findViewById(R.id.transaction_type);
		ArrayAdapter<CharSequence> adapter =  
				ArrayAdapter.createFromResource(
						this,
						R.array.transaction_type,
						android.R.layout.simple_spinner_dropdown_item
						);
		spinner.setAdapter(adapter);
		
		spinner.setOnItemSelectedListener(this);
	}
	
	//Initialize Category View AutoComplete
	private void setCategoryView()
	{
		categoryView = (AutoCompleteTextView ) findViewById(R.id.categories_textView);
		
		List<Category> values = categoryDB.selectAllRecords();
		ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(this,android.R.layout.simple_dropdown_item_1line,values);
		//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categoryView.setAdapter(adapter);
	}
	
	
	public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) {
		
		//
		String trans_type = parent.getItemAtPosition(pos).toString();
		//Toast.makeText(TransactionActivity.this, trans_type,Toast.LENGTH_SHORT).show();
		
		if(trans_type.equals("Expense"))
			this.transaction.setTransactionType(1);
		else
			this.transaction.setTransactionType(2);
		
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }
	
	//Required but not implemented
	public void onNothingSelected(AdapterView<?> parent) {
        
    }
	
	
	private boolean setCategory()
	{
		String name = categoryView.getText().toString();
		if(name.isEmpty())
		{
			Toast.makeText(this, "A category must be supplied.", Toast.LENGTH_LONG).show();
			return false;
		}
			
		this.category = categoryDB.createIfNotExists(name);
		this.transaction.setCategoryId(this.category.getId());
		return true;
	}
	private boolean setTransaction()
	{
		String amount = amountView.getText().toString();
		float trans_amount=0.0f;
		
		try
		{
			trans_amount = Float.parseFloat(amount);
			
		}
		catch(Exception e)
		{
			Toast.makeText(this, "The amount must be greater than 0.", Toast.LENGTH_LONG).show();
			return false;
		}
		this.transaction.setAmount(trans_amount);
		
		return true;
		
	}
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.add_transaction:
				if(setTransaction() && setCategory())
				{
					//Also updates record. I should change the name of this.
					//IF id is not set in the Transaction model, then add it 
					//if it is set then update that record
					transactionDB.createRecord(this.transaction);
					
					if(this.transaction.getId() == -1)
					{
						Toast.makeText(this, "A Transaction has been added to the database.", Toast.LENGTH_SHORT).show();
					}
					else
					{
						Toast.makeText(this, "A Transaction has been updated in the database.", Toast.LENGTH_SHORT).show();
					}
					finish();
				}
				break;
		
		}
	}
	
}
