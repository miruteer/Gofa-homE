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
package com.jdon.jivejdon.domain.model.message.output.html;

import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.function.Function;

/**
 * A ForumMessageFilter that highlights quoted messages based upon the level of
 * indentation - very similar to how groups.google.com highlights quotes.
 * 
 * @author Bruce Ritchie
 */
public class QuoteFilter implements Function<MessageVO, MessageVO> {
	private final static String module = QuoteFilter.class.getName();

	private ArrayList quoteColors;
	private char quoteChar;
	private boolean reformatTextEnabled;

	public QuoteFilter() {
		quoteColors = new ArrayList();

		quoteColors.add("#660066");
		quoteColors.add("#007777");
		quoteColors.add("#3377ff");
		quoteColors.add("#669966");
		quoteColors.add("#660000");

		quoteChar = '>';
		reformatTextEnabled = false;
	}

	/**
	 * Clones a new filter that will have the same properties and that will wrap
	 * around the specified message.
	 *
	 */
	public MessageVO apply(MessageVO messageVO) {
		return messageVO.builder().subject(messageVO.getSubject()).body(parseQuotes
				(messageVO.getBody())).build();
	}


	// OTHER METHODS//

	public String getQuoteColors() {
		StringBuilder sb = new StringBuilder();

		for (int x = 0; x < quoteColors.size(); x++) {
			sb.append(quoteColors.get(x).toString());
			if (x != quoteColors.size() - 1) {
				sb.append(",");
			}
		}

		return sb.toString().intern();
	}

	public void setQuoteColors(String prefix) {
		if (prefix != null) {
			quoteColors = new ArrayList();
			StringTokenizer st = new StringTokenizer(prefix, ",");

			while (st.hasMoreTokens()) {
				quoteColors.add(st.nextToken());
			}
		}
	}

	public boolean isReformatTextEnabled() {
		return reformatTextEnabled;
	}

	public void setReformatTextEnabled(boolean enabled) {
		reformatTextEnabled = enabled;
	}

	public String getQuoteChar() {
		return new Character(quoteChar).toString();
	}

	public void setQuoteChar(String c) {
		if (c != null) {
			quoteChar = c.trim().charAt(0);
		}
	}

	/**
	 * This method takes a string which may contain quotes lines and quotes the
	 * lines accordingly. It does this by adding the prefix line postfix
	 * according to the appropriate quote level. An arbitrary level of quotes
	 * are available to be set, and once those are used up it restarts from the
	 * beginning of the list
	 * 
	 * @param input
	 *            the text to be converted.
	 * @returns the input string with the quoted lines handled accordingly.
	 */
	private String parseQuotes(String input) {
		// Check if the string is null or zero length -- if so, return
		// what was sent in.
		if (input == null || input.length() == 0) {
			return input;
		}

		StringBuilder sb = new StringBuilder(input.length() + 512);
		BufferedReader br = new BufferedReader(new StringReader(input), 1024);
		ArrayList lines = new ArrayList();
		String line;
		char chars[];

		try {
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
			br.close();
		} catch (IOException e) { /* ignore */
		}

		// correct text formatting if requested
		if (reformatTextEnabled) {
			reformatText(lines);
		}

		for (int c = 0; c < lines.size(); c++) {
			line = (String) lines.get(c);
			chars = StringUtil.replace(line, "&gt;", ">").toCharArray();

			if (isQuoted(chars)) {
				int count = countQuotes(chars) - 1;
				sb.append("<font color=\"");
				sb.append(quoteColors.get(count % quoteColors.size()).toString());
				sb.append("\">");
				sb.append(line);

				// for all following lines that have the same quote level
				// just append them to the buffer, we'll append the quotePostfix
				// at the end. This saves on needless html being generated and
				// reduces the size of the final html page. (Every little bit
				// counts)
				for (int g = c + 1; g < lines.size(); g++) {
					line = (String) lines.get(g);
					chars = StringUtil.replace(line, "&gt;", ">").toCharArray();

					if (isQuoted(chars) && (countQuotes(chars) - 1 == count)) {
						sb.append("\n").append(line);
						c = g;
					} else {
						break;
					}
				}
				sb.append("</font>").append("\n");
			} else {
				sb.append(line).append("\n");
			}
		}

		return sb.toString().intern();
	}

	/**
	 * This method takes a list of strings (one per line) and through some
	 * heuristics attempts to correct for badly wrapped lines.
	 * <p>
	 * 
	 * This is allows for:
	 * <p>
	 * 
	 * <ul>
	 * <li>Making the text easier to read</li>
	 * <li>Restoring the text format as close as possible to the original
	 * formatting of the text prior to being reformatted by a MUA such as
	 * Microsoft Outlook. MUA's often wrap lines of length > 76 characters
	 * regardless of whether they are quote