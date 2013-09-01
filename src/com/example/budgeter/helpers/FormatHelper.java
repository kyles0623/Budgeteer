package com.example.budgeter.helpers;

import java.text.NumberFormat;

public final class FormatHelper {

	private static NumberFormat formatter;
	public static String formatCurrency(float amount)
	{
		if(formatter == null)
			formatter = NumberFormat.getCurrencyInstance();
		
		return formatter.format(amount);
		
	}
}
