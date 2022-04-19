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

		while (i < char_line.length) {
			curr_char = char_line[i];
			switch (state) {
			case ENTRY:
				if (Character.isJavaIdentifierPart(curr_char)) { // keyword, id,
																	// number
																	// literal
					head_idx = i;
					if (Character.isDigit(curr_char)) {
						if (curr_char == '0') {
							state = NUMBER_HEX_BEGIN;
						} else {
							state = NUMBER_BIN_INT_FLOAT_OCTAL;
						}
					} else {
						state = INTERIM;
					}
				} else if (curr_char == '+' || curr_char == '-') { // number
																	// literals
					if (i < char_line.length - 1) {
						if (!Character.isDigit(char_line[i + 1])) { // +0x43 <--
																	// this
																	// cannot be
																	// hex
							buffer.append(curr_char);
							state = ENTRY;
						} else {
							head_idx = i;
							state = NUMBER_BIN_INT_FLOAT_OCTAL;
						}
					} else {
						buffer.append(curr_char);
						state = NUMBER_BIN_INT_FLOAT_OCTAL;
					}
				} else if (curr_char == '/') { // comment
					head_idx = i;
					state = IGNORE_BEGIN;
				} else if (curr_char == '\"') { // string
					head_idx = i;
					state = STRING_ENTRY;
				} else if (curr_char == '\'') { // character
					head_idx = i;
					state = CHARACTER_ENTRY;
				} else if (curr_char == '{' || curr_char == '}') { // scope
																	// bracket
					buffer.append(bracketStart);
					buffer.append(curr_char);
					buffer.append(bracketEnd);
					state = ACCEPT;
				} else if (curr_char == '\n') { // in case of multiple newlines
					buffer.append(curr_char);
					state = NEWLINE_ENTRY;
				} else { // space, =, >, <, \t, white_space, etc
					buffer.append(curr_char);
					state = ENTRY;
				}
				break;
			case NUMBER_BIN_INT_FLOAT_OCTAL:
				if (Character.isJavaIdentifierPart(curr_char)) {
					// 1f 1xf 67.6 1. -1 +2 0l
					if (Character.isDigit(curr_char)) {
						// 87 10 12 00-0L
						state = NUMBER_BIN_INT_FLOAT_OCTAL;
					} else if (curr_char == 'l' || curr_char == 'L') { // cheating...
																		// +l +L
						tail_idx = i;
						if (filterNumber) {
							buffer.append(numberStart);
						}
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						if (filterNumber) {
							buffer.append(numberEnd);
						}
						state = ACCEPT;
					} else {
						// -E +f 5e 1x 34234x 7979/7897 79+897 890-7989
						if (char_line[i - 1] == '-' || char_line[i - 1] == '+') {
							// -E +f -l -0l
							tail_idx = i;
							buffer.append(char_line, head_idx, (tail_idx - head_idx));
							state = ACCEPT;
						} else { // must be numbers infront
							// 5e 1x 34234x 2342static 243} 243;
							tail_idx = i;
							if (filterNumber) {
								buffer.append(numberStart);
							}
							buffer.append(char_line, head_idx, (tail_idx - head_idx));
							if (filterNumber) {
								buffer.append(numberEnd);
							}
							state = ACCEPT;
							i--;
						}
					}
				} else {
					// 12324\ 23423\n 23432. 2432\r 2423\t 2421{ 423: 2342~ 242&
					// 6868- 768+
					tail_idx = i;
					if (filterNumber) {
						buffer.append(numberStart);
					}
					buffer.append(char_line, head_idx, (tail_idx - head_idx));
					if (filterNumber) {
						buffer.append(numberEnd);
					}
					state = ACCEPT;
					i--;
				}
				break;
			case NUMBER_HEX_BEGIN:
				if (Character.isJavaIdentifierPart(curr_char)) {
					// 0X 0B 05 0i 0x 00x3 0x0x0 0x00x 0x0005fg
					if (curr_char == 'x') { // this accounts for proper case
						// 0xn 0xf 0xl
						state = NUMBER_HEX_REST;
					} else if (Character.isDigit(curr_char)) {
						// 0000.0 05 078799
						state = NUMBER_BIN_INT_FLOAT_OCTAL;
					} else { // upper case letters, lower case minus 'x'
						// 0X 0b 0static 0case
						tail_idx = i;
						if (filterNumber) {
							buffer.append(numberStart);
						}
						buffer.append(char_line, head_idx, (tail_idx - head_idx));
						if (filterNumber) {
							buffer.append(numberEnd);
						}
						