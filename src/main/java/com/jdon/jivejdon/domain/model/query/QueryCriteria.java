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
package com.jdon.jivejdon.domain.model.query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.jdon.controller.cache.StringKey;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.util.UtilValidate;

/**
 * Query Object P of EAA http://www.martinfowler.com/eaaCatalog/queryObject.html
 * 
 * @author banq(http://www.jdon.com)
 * 
 */
public class QueryCriteria implements StringKey {
	private static final String DATE_SPLIT = "-";

	protected java.util.Date fromDate;
	protected java.util.Date toDate;

	private String forumId;

	private int messageReplyCountWindow;

	protected ResultSort resultSort;

	public QueryCriteria() {

		try {
			this.fromDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2001-01-01 00:00:00");
			this.toDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2100-01-01 00:00:00");

		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.resultSort = new ResultSort();
	}

	public java.util.Date getFromDate() {
		return fromDate;
	}

	/**
	 * 
	 * @return yyyy-mm-dd
	 */
	public String getFromDateString() {
		return ToolsUtil.toDateString(fromDate, DATE_SPLIT);
	}

	/**
	 * 
	 * @return a long Millis String with no hour/min/ss/mills
	 */
	public String getFromDateNoMillisString() {
		return ToolsUtil.dateToNoMillis(fromDate);
	}

	public void setFromDate(String fromYear, String fromMonth, String fromDay) {
		Calendar cal = Calendar.getInstance();
		if (UtilValidate.isEmpty(fromYear))
			fromYear = Integer.toString(cal.get(Calendar.YEAR));
		if (UtilValidate.isEmpty(fromMonth)) {
			int month = cal.get(Calendar.MONTH) + 1;
			if (month < 10) {
				fromMonth = "0" + mo