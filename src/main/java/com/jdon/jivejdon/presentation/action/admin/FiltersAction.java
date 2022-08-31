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
package com.jdon.jivejdon.presentation.action.admin;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.domain.model.message.MessageVO;
import com.jdon.jivejdon.domain.model.message.output.RenderingFilterManager;
import com.jdon.jivejdon.presentation.form.FiltersForm;
import com.jdon.jivejdon.api.ForumMessageService;
import com.jdon.jivejdon.api.ForumService;
import com.jdon.jivejdon.util.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 *
 */
public class FiltersAction extends DispatchAction {
    private final static Logger logger = LogManager.getLogger(FiltersAction.class);

    public ActionForward display(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FiltersForm filtersForm = (FiltersForm) form;
        initForm(filtersForm, request);
        return mapping.findForward("forward");
    }

    private void initForm(FiltersForm filtersForm, HttpServletRequest request) {

        try {
            RenderingFilterManager filterManager = getFilterManager(request);
			Function<MessageVO, MessageVO>[] availablefilters = filterManager
					.getAvailableFilters();

            BeanDescriptor[] descriptors = new BeanDescriptor[availablefilters.length];
            List unInstalledDescriptors = new ArrayList(availablefilters.length);
            for (int i = 0; i < availablefilters.length; i++) {
                BeanDescriptor descriptor = (Introspector.getBeanInfo(availablefilters[i].getClass())).getBeanDescriptor();
                descriptors