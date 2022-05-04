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

		quoteColors.add("