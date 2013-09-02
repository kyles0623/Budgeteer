package com.example.budgeter;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
		transactionDB.deleteAllRows();
		
		
		//Testing Values
		transactionDB.createRecord(125.06f,1,1,"Bought everyone lunch");
		transactionDB.createRecord(223.06f,1,2,"Got Paid");
		transactionDB.createRecord(232.023f,1,2,"Found a Bag of Money");
		transactionDB.createRecord(1012.023f,1,1,"Funeral Costs");			
		

		
		//Sets Values for texts view 
		setViewValues();
		
		
		
	}
	@Override
	protected void onListItemClick(final ListView l, View v, final int position, long id) {
	 // TODO Auto-generated method stub
	 //super.onListItemClick(l, v, position, id);
		Resources r = getResources();
		final CharSequence[] values = r.getStringArray(R.array.options_dialog); 
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.options_dialog_title));
		
		builder.setItems(values, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,TransactionActivity.class);
				
				Transaction trans =  (Transaction)l.getAdapter().getItem(position);
				
				if(values[which].equals("Edit"))
				{
					
					intent.putExtra("transactionId", trans.getId());
					startActivity(intent);
				}
				else if(values[which].equals("Delete"))
				{
					confirmDeleteDialog(trans.getId());
					
					
				}
				
				
			}
		});
		
		builder.show();
		
		/*Intent intent = new Intent(this,TransactionActivity.class);
		
		Transaction trans =  (Transaction)l.getAdapter().getItem(position);
		intent.putExtra("transactionId", trans.getId());
		startActivity(intent);*/
	}

	private void confirmDeleteDialog(final int transId)
	{
		Resources r = getResources();
		
		final CharSequence[] vals = r.getStringArray(R.array.confirm_dialog); 
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Are you sure you want to delete this Transaction?");
		
		builder.setPositiveButton("Yes",
		        new DialogInterface.OnClickListener() {

		            // do something when the button is clicked
		            public void onClick(DialogInterface arg0, int arg1) {
		            	transactionDB.deleteRecord(transId);
		                Toast.makeText(MainActivity.this, "Your Transaction has been deleted.", Toast.LENGTH_SHORT).show();
		                setViewValues();
		            }
		        });
		builder.setNegativeButton("No", null);
		builder.show();
		
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
		else
		{
			totalView.setTextColor(Color.argb(255, 46, 164, 65));
		}
		
		totalView.setText(FormatHelper.formatCurrency(total));
		
		//Get all Transactions from today
		List<Transaction> values = transactionDB.selectAllRecords();
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
	    
	    //Need better implementation
	    this.setViewValues();
    }



}
