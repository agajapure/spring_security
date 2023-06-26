package com.example.utility;

public class RegexConstants {
	
	public static final String ALPHA_NUMERIC_SYMB = "^$|[0-9a-zA-Z- _()*:,./\\u00dc\\u00fc\\u00c4\\u00e4\\u00d6\\u00f6\\u00df]+";
	public static final String NUMERIC_ONLY = "[0-9]+";
	public static final String ALPHA_ONLY = "[a-zA-Z\\u00dc\\u00fc\\u00c4\\u00e4\\u00d6\\u00f6\\u00df]+";
	public static final String DATETIME = "^$|[0-9a-zA-Z- :/]+";

	private RegexConstants() {
	}

}
