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
package com.jdon.jivejdon.util;

import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.util.StringUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 *
 */
public class FiltersUtils {
	private static final char[] LT_ENCODE = "&lt;".toCharArray();
	private static final char[] GT_ENCODE = "&gt;".toCharArray();

	public static String getPropertyHTML(Function<MessageVO, MessageVO> filter, PropertyDescriptor
			descriptor) {
		// HTML of the customizer for this property
		StringBuilder html = new StringBuilder(50);
		// Get the name of the property (this becomes the name of the form
		// element)
		String propName = descriptor.getName();
		// Get the current value of the property
		Object propValue = null;
		try {
			Method methodc = descriptor.getReadMethod();
			// Decode the property value using the property type and
			// encoded String value.
			Object[] args = null;
			propValue = methodc.invoke(filter, args);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Get the classname of this property
		String className = descriptor.getPropertyType().getName();

		// HTML form elements for number values (rendered as small textfields)
		if ("int".equals(className) || "double".equals(className) || "long".equals(class