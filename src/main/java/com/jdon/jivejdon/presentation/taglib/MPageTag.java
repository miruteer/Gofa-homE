
/**
 * Copyright 2003-2006 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jdon.jivejdon.presentation.taglib;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.LinkTag;

import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.strutsutil.ModelListForm;
import com.jdon.util.Debug;

/**
 * 类似Html:link
 * 
 * <p>
 * Copyright: Jdon.com Copyright (c) 2003
 * </p>
 * <p>
 * </p>
 * 
 * @author banq
 * @version 1.0
 */
public class MPageTag extends LinkTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9079910663630228290L;

	private final static String module = MPageTag.class.getName();

	public final static String URLNAME = "URL";
	public final static String COUNT = "COUNT";
	public final static String START = "START";
	public final static String ALLCOUNT = "ALLCOUNT";
	public final static String NEXTPAGE = "NEXTPAGE";
	public final static String DISP = "DISP";

	private String actionFormName;

	// --------------------------------------------------------- Public Methods

	/**
	 * Render the beginning of the hyperlink.
	 * 
	 * @exception JspException
	 *                if a JSP exception has occurred
	 */
	public int doStartTag() throws JspException {

		ModelListForm form = null;
		try {
			form = (ModelListForm) FormBeanUtil.lookupActionForm((HttpServletRequest) pageContext.getRequest(), actionFormName);
			if (form == null)
				throw new Exception();
		} catch (Exception e) {
			Debug.logError("[JdonFramework]not found actionFormName value: " + actionFormName, module);
			throw new JspException(" not found " + actionFormName);
		}

		int start = form.getStart();
		int allCount = form.getAllCount();
		int count = form.getCount();

		String nextPage = "";
		if ((allCount > (start + count)))
			nextPage = NEXTPAGE;

		pageContext.setAttribute(URLNAME, calUrl());
		pageContext.setAttribute(START, Integer.toString(start));
		pageContext.setAttribute(COUNT, Integer.toString(count));
		pageContext.setAttribute(ALLCOUNT, Integer.toString(allCount));
		pageContext.setAttribute(NEXTPAGE, nextPage);

		int currentPage = 1;
		if (count > 0) {
			currentPage = (start / count) + 1;
		}

		// 当前只有一页，没有下一页
		if ((currentPage == 1) && (nextPage.length() == 0)) {
			pageContext.setAttribute(DISP, "off"); // 不显示其它标识
		} else
			pageContext.setAttribute(DISP, "on");

		// Evaluate the body of this tag
		return (EVAL_BODY_INCLUDE);

	}

	public String calUrl() {
		StringBuilder url = new StringBuilder();
		try {
			// Generate the URL to be encoded
			Map params = TagUtils.getInstance().computeParameters(pageContext, paramId, paramName, paramProperty, paramScope, name, property, scope,
					transaction);

			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

			url.append(request.getContextPath());
			url.append(page);

			if (params == null || params.isEmpty())
				return url.toString();
			Iterator keys = params.keySet().iterator();
			while (keys.hasNext()) {
				String key = (String) keys.next();
				Object value = params.get(key);
				url.append('/');
				url.append((String) value);
			}
		} catch (JspException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url.toString();

	}

	/**
	 * Render the end of the hyperlink.
	 * 
	 * @exception JspException
	 *                if a JSP exception has occurred
	 */
	public int doEndTag() throws JspException {

		return (EVAL_PAGE);

	}

	/**
	 * Release any acquired resources.
	 */
	public void release() {

		super.release();

	}

	public String getActionFormName() {
		return actionFormName;
	}

	public void setActionFormName(String actionFormName) {
		this.actionFormName = actionFormName;
	}

}