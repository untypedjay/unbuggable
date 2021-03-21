package io.untypedjay.unbuggable.util;

@SuppressWarnings("Duplicates")
public class PrintUtil {
	
	public static void printTitle(String text, int length, char ch) {	
		StringBuilder sb = new StringBuilder(length);
		int n = (length - (text.length() + 2))/2;
		for (int i=0; i<n; i++) sb.append(ch);
		sb.append(" " + text + " ");
		for (int i=0; i<length - (n + text.length() + 2); i++) sb.append(ch);
		
		System.out.println(sb);
	}

	public static void printTitle(String text, int length) {	
	  printTitle(text, length, '=');
	}
	
	public static void printSeparator(int length, char ch) {
		StringBuilder sb = new StringBuilder(length);
		for (int i=0; i<length; i++) sb.append(ch);
		System.out.println(sb);
	}
	
	public static void printSeparator(int length) {	
		printSeparator(length, '=');
	}

	public static void println() {
		System.out.println();
	}

}
