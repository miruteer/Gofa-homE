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
	 * Sets the html for the end of a method.
	 */
	public void setMethodEnd(String methodEnd) {
		this.methodEnd = methodEnd.toCharArray();
	}

	/**
	 * Gets the html for the start of a character.
	 */
	public String getCharacterStart() {
		return String.valueOf(characterStart);
	}

	/**
	 * Sets the html for the start of a character.
	 */
	public void setCharacterStart(String characterStart) {
		this.characterStart = characterStart.toCharArray();
	}

	/**
	 * Gets the html for the end of a character.
	 */
	public String getCharacterEnd() {
		return String.valueOf(characterEnd);
	}

	/**
	 * Sets the html for the end of a character.
	 */
	public void setCharacterEnd(String characterEnd) {
		this.characterEnd = characterEnd.toCharArray();
	}

	/**
	 * Gets the html for the start of a bracket.
	 */
	public String getBracketStart() {
		return String.valueOf(bracketStart);
	}

	/**
	 * Sets the html for the start of a bracket.
	 */
	public void setBracketStart(String bracketStart) {
		this.bracketStart = bracketStart.toCharArray();
	}

	/**
	 * Gets the html for the end of a bracket.
	 */
	public String getBracketEnd() {
		return String.valueOf(bracketEnd);
	}

	/**
	 * Sets the html for the end of a bracket.
	 */
	public void setBracketEnd(String bracketEnd) {
		this.bracketEnd = bracketEnd.toCharArray();
	}

	/**
	 * Gets the html for the start of a number
	 */
	public String getNumberStart() {
		return String.valueOf(numberStart);
	}

	/**
	 * Sets the html for the start of a number.
	 */
	public void setNumberStart(String numberStart) {
		this.numberStart = numberStart.toCharArray();
	}

	/**
	 * Gets the html for the end of a number.
	 */
	public String getNumberEnd() {
		return String.valueOf(numberEnd);
	}

	/**
	 * Sets the html for the end of a number.
	 */
	public void setNumberEnd(String numberEnd) {
		this.numberEnd = numberEnd.toCharArray();
	}

	/**
	 * See if method filtering is enabled.
	 */
	public boolean getFilterMethod() {
		return filterMethod;
	}

	/**
	 * Enables or disables method filtering.
	 */
	public void setFilterMethod(boolean filterMethod) {
		this.filterMethod = filterMethod;
	}

	/**
	 * See if number filtering is enabled.
	 */
	public boolean getFilterNumber() {
		return filterNumber;
	}

	/**
	 * Enables or disables number filtering.
	 */
	public void setFilterNumber(boolean filterNumber) {
		this.filterNumber = filterNumber;
	}

	/**
	 * Syntax highlights any java code in the input. In working with this method
	 * it is helpful to know how lexers work in general.
	 * <p>
	 * The overall strategy is as follows: The input is processed a character at
	 * a time, accompanied by a state update. When a valid Java token is
	 * detected, such as a keyword, a string, a comment block, etc, said token
	 * gets "wrapped" by htmlStart and htmlEnd tags. Not everything is
	 * implemented according to the Java Language Specifications. For example,
	 * the length of a valid number is left unchecked.
	 * <p>
	 * 
	 * @param input
	 *            string possibly containing Java code
	 * @return the output string containing html formatted Java code
	 */
	public final String javaCodeFilter(String line) {
		final int ENTRY = 0;
		final int INTERIM = 1;
		final int ACCEPT = 2;
		final int IGNORE_BEGIN = 3;
		final int INLINE_IGNORE = 4;
		final int MULTILINE_IGNORE = 5;
		final int MULTILINE_EXIT = 6;
		final int STRING_ENTRY = 7;
		final int CHARACTER_ENTRY = 8;
		final int NEWLINE_ENTRY = 9;
		final int NUMBER_BIN_INT_FLOAT_OCTAL = 10;
		final int NUMBER_HEX_BEGIN = 11;
		final int NUMBER_HEX_REST = 12;
		int state = ENTRY;
		char[] char_line;
		char curr_char;
		String token;
		StringBuilder buffer;

		if (line == null || line.equals("")) {
			return "";
		} else {
			char_line = line.toCharArray();
			buffer = new StringBuilder(char_line.length);
		}

		int i = 0;
		int head_idx = 0;
		int tail_idx = 0;

		while (i < char_line.length