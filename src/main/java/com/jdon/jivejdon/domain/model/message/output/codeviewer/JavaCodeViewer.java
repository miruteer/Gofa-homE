/*
 * Copyright 2003-2006 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.domain.model.message.output.codeviewer;

import java.util.HashMap;

/**
 * Never used
 * 
 * A class that syntax highlights Java code into html.
 * <p>
 * A CodeViewer object is used to syntax highlight Java code. To make use of
 * this class, first set up how tokens should be highlighted by invoking the
 * getXXX and setXXX methods, then pass in "chunks" of relevent input to be
 * filtered. Typically this will mean the section(s) of a form input that has
 * been tagged with [code][/code] tags.
 */
public class JavaCodeViewer {
	private static HashMap reservedWords = new HashMap(150); // >= Java2 only
																// (also, not
																// thread-safe)
	private char[] backgroundColor = "#ffffff".toCharArray();
	private char[] commentStart = "<font color=\"darkgreen\">".toCharArray();
	private char[] commentEnd = "</font>".toCharArray();
	private char[] stringStart = "<font color=\"red\">".toCharArray();
	private char[] stringEnd = "</font>".toCharArray();
	private char[] reservedWordStart = "<font color=\"navy\"><b>".toCharArray();
	private char[] reservedWordEnd = "</b></font>".toCharArray();
	private char[] methodStart = "<font color=\"brown\">".toCharArray();
	private char[] methodEnd = "</font>".toCharArray();
	private char[] characterStart = "<font color=\"navy\">".toCharArray();
	private char[] characterEnd = "</font>".toCharArray();
	private char[] bracketStart = "<font color=\"navy\">".toCharArray();
	private char[] bracketEnd = "</font>".toCharArray();
	private char[] numberStart = "<font color=\"red\">".toCharArray();
	private char[] numberEnd = "</font>".toCharArray();
	private char[] nbsp = "&nbsp;".toCharArray();
	private char stringEscape = '\\';
	private char characterEscape = '\\';
	private boolean filterMethod = false;
	private boolean filterNumber = false;

	/**
	 * Load all keywords at class loading time.
	 */
	static {
		loadKeywords();
	}

	/**
	 * Gets the html for the start of a comment block.
	 */
	public String getCommentStart() {
		return String.valueOf(commentStart);
	}

	/**
	 * Sets the html for the start of a comment block.
	 */
	public void setCommentStart(String commentStart) {
		this.commentStart = commentStart.toCharArray();
	}

	/**
	 * Gets the html for the end of a comment block.
	 */
	public String getCommentEnd() {
		return String.valueOf(commentEnd);
	}

	/**
	 * Sets the html for the end of a comment block.
	 */
	public void setCommentEnd(String commentEnd) {
		this.commentEnd = commentEnd.toCharArray();
	}

	/**
	 * Gets the html for the start of a String.
	 */
	public String getStringStart() {
		return String.valueOf(stringStart);
	}

	/**
	 * Sets the html for the start of a String.
	 */
	public void setStringStart(String stringStart) {
		this.stringStart = stringStart.toCharArray();
	}

	/**
	 * Gets the html for the end of a String.
	 */
	public String getStringEnd() {
		return String.valueOf(stringEnd);
	}

	/**
	 * Sets the html for the end of a String.
	 */
	public void setStringEnd(String stringEnd) {
		this.stringEnd = stringEnd.toCharArray();
	}

	/**
	 * Gets the html for the start of a reserved word.
	 */
	public String getReservedWordStart() {
		return String.valueOf(reservedWordStart);
	}

	/**
	 * Sets the html for the start of a reserved word.
	 */
	public void setReservedWordStart(String reservedWordStart) {
		this.reservedWordStart = reservedWordStart.toCharArray();
	}

	/**
	 * Gets the html for the end of a reserved word.
	 */
	public String getReservedWordEnd() {
		return String.valueOf(reservedWordEnd);
	}

	/**
	 * Sets the html for the end of a reserved word.
	 */
	public void setReservedWordEnd(String reservedWordEnd) {
		this.reservedWordEnd = reservedWordEnd.toCharArray();
	}

	/**
	 * Gets the html for the start of a method.
	 */
	public String getMethodStart() {
		return String.valueOf(methodStart);
	}

	/**
	 * Sets the html for the start of a method.
	 */
	public void setMethodStart(String methodStart) {
		this.methodStart = methodStart.toCharArray();
	}

	/**
	 * Gets the html for the end of a method.
	 */
	public String getMethodEnd() {
		return String.valueOf(methodEnd);
	}

	/**
	 * Sets the