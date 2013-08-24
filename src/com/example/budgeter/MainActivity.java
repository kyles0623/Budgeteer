package com.example.budgeter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.budgeter.datasources.TransactionDataSource;
import com.example.budgeter.models.Transaction;

public class MainActivity extends Activity implements OnClickListener {

	private String TAG = "Budgeteer";
	private TransactionDataSource transactionDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		transactionDB = new TransactionDataSource(this);
		setViewValues();
	}
	
	private void setViewValues()
	{

		
		TextView totalView = (TextView)findViewById(R.id.current_balance);
		
		totalView.setText("$"+transactionDB.getTotalTransactions());
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


}
