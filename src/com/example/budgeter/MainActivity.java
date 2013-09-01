package com.example.budgeter;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.budgeter.datasources.TransactionDataSource;
import com.example.budgeter.helpers.FormatHelper;
import com.example.budgeter.models.Transaction;

public class MainActivity extends ListActivity implements OnClickListener {

	private String TAG = "Budgeteer";
	private TransactionDataSource transactionDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		transactionDB = new TransactionDataSource(this);
		
		/*
		transactionDB.createRecord(125.06f,1,1,"Bought everyone lunch");
		transactionDB.createRecord(223.06f,1,2,"Got Paid");
		
		transactionDB.createRecord(232.023f,1,2,"Found a Bag of Money");
		*/
		//transactionDB.createRecord(1012.023f,1,1,"Funeral Costs");
		//Sets Values for texts view 
		
		setViewValues();
		
		
		
	}
	
	private void setViewValues()
	{
		TextView totalView = (TextView)findViewById(R.id.current_balance);
		//Set Total View to be current Total Balance
		float total = transactionDB.getTotalTransactions();
		
		if(total < 0)
		{
			totalView.setTextColor(Color.RED);
		}
		
		totalView.setText(FormatHelper.formatCurrency(total));
		
		//Get all Transactions from today
		List<Transaction> values = transactionDB.selectAllRecords(true);
		//Set listview to have the transactions from today
		ArrayAdapter<Transaction> adapter = new ArrayAdapter<Transaction>(this,android.R.layout.simple_list_item_1,values);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.new_transaction:
				Intent intent = new Intent(MainActivity.this,TransactionActivity.class);
				startActivity(intent);
				break;
			
		
		}
		
		
	}
	
	@Override
	public void onResume()
    {  // After a pause OR at startup
	    super.onResume();
	   this.setViewValues();
    }


}
