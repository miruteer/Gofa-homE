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
package com.jdon.jivejdon.util;

import java.awt.Color;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.logging.log4j.*;

import com.jdon.jivejdon.domain.model.message.output.beanutil.FilterBeanInfo;
  

/**
 * A utility class that provides methods that are useful for dealing with
 * Java Beans.
 */
public class BeanUtils {
    private final static Logger logger = LogManager.getLogger(BeanUtils.class);

    /**
     * Sets the properties of a Java Bean based on the String name/value pairs in
     * the specifieed Map. Because this method has to know how to convert a
     * String value into the correct type for the bean, only a few bean property
     * types are supported. They are: String, boolean, int, long, float, double,
     * Color, and Class.<p>
     *
     * If key/value pairs exist in the Map that don't correspond to properties
     * of the bean, they will be ignored.
     *
     * @param bean the JavaBean to set properties on.
     * @param properties String name/value pairs of the properties to set.
     */
    public static void setProperties(Object bean, Map properties) {
        try {
            // Loop through all the property names in the Map
            for (Iterator iter = properties.keySet().iterator(); iter.hasNext();) {
                String propName = (String) iter.next();
                try {
                    // Create a property descriptor for the named property. If
                    // the bean doesn't have the named property, an
                    // Introspection will be thrown.
                    PropertyDescriptor descriptor = new PropertyDescriptor(propName, bean.getClass());
                    // Load the class type of the property.
                    Class propertyType = descriptor.getPropertyType();
                    // Get the value of the property by converting it from a
                    // String to the correct object type.
                    Object value = decode(propertyType, (String) properties.get(propName));
                    // Set the value of the bean.
                    descriptor.getWriteMeth