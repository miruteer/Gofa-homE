/*
 * Copyright 2003-2005 the original author or authors.
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
 * A class that syntax highlights Java code by turning it into html.
 * 
 * @version 1.0 October 1999
 * @author Bill Lynch, Matt Tucker, CoolServlets.com
 * 
 *         <p>
 *         A <code>CodeViewer</code> object is created and then keeps state as
 *         lines are passed in. Each line passed in as java text, is returned as
 *         syntax highlighted html text.
 * 
 *         <p>
 *         Users of the class can set how the java code will be highlighted with
 *         setter methods.
 * 
 *         <p>
 *         Only valid java lines should be passed in since the object maintains
 *         state and may not handle illegal code gracefully.
 * 
 *         <p>
 *         The actual system is implemented as a series of filters that deal
 *         with specific portions of the java code. The filters are as follows:
 * 
 *         <pre>
 *  htmlFilter
 *     |__
 *        multiLineCommentFilter
 *           |___
 *                inlineCommentFilter
 *                   |___
 *                        stringFilter
 *                           |__
 *                               keywordFilter
 * </pre>
 */
public class CodeViewer {

	private static HashMap reservedWords = new HashMap(); // >= Java2 only
															// (also, not
															// thread-safe)
	// private static Hashtable reservedWords = new Hashtable(); // < Java2
	// (thread-safe)
	private boolean inMultiLineComment = false;
	private String commentStart = "</font><font  color=\"#0000aa\"><i>";
	private String commentEnd = "</font></i><font  color=black>";
	private String stringStart = "</font><font  color=\"#00bb00\">";
	private String stringEnd = "</font><font  color=black>";
	private String reservedWordStart = "<b>";
	private String reservedWordEnd = "</b>";

	static {
		loadHash();
	}

	public CodeViewer() {
	}

	public void setCommentStart(String commentStart) {
		this.commentStart = commentStart;
	}

	public void setCommentEnd(String commentEnd) {
		this.commentEnd = commentEnd;
	}

	public void setStringStart(String stringStart) {
		this.stringStart = stringStart;
	}

	public void setStringEnd(String stringEnd) {
		this.stringEnd = stringEnd;
	}

	public void setReservedWordStart(String reservedWordStart) {
		this.reservedWordStart = reservedWordStart;
	}

	public void setReservedWordEnd(String reservedWordEnd) {
		this.reservedWordEnd = reservedWordEnd;
	}

	public String getCommentStart() {
		return commentStart;
	}

	public String getCommentEnd() {
		return commentEnd;
	}

	public String getStringStart() {
		return stringStart;
	}

	public String getStringEnd() {
		return stringEnd;
	}

	public String getReservedWordStart() {
		return reservedWordStart;
	}

	public String getReservedWordEnd() {
		return reservedWordEnd;
	}

	/**
	 * Passes off each line to the first filter.
	 * 
	 * @param line
	 *            The line of Java code to be highlighted.
	 */
	public String syntaxHighlight(String line) {
		return htmlFilter(line);
	}

	/*
	 * Filter html tags into more benign text.
	 */
	private String htmlFilter(String line) {
		if (line == null || line.equals("")) {
			return "";
		}

		// replace ampersands with HTML escape sequence for ampersand;
		line = replace(line, "&", "&#