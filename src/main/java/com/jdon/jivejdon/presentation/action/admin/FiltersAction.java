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
                descriptors[i] = descriptor;
                if (!isInstalledFilter(filterManager, availablefilters[i])) {
                    unInstalledDescriptors.add(descriptor);
                }
            }
            filtersForm.setAvailableDescriptors(descriptors);
            filtersForm.setUnInstalledDescriptors(unInstalledDescriptors);

			Function<MessageVO, MessageVO>[] filters = filterManager.getFilters();
            descriptors = new BeanDescriptor[filters.length];
            for (int i = 0; i < filters.length; i++) {
                BeanDescriptor descriptor = (Introspector.getBeanInfo(filters[i].getClass())).getBeanDescriptor();
                descriptors[i] = descriptor;
                PropertyDescriptor[] propertydescriptors = BeanUtils.getPropertyDescriptors(filters[i].getClass());
                filtersForm.setPropertyDescriptors(descriptor, propertydescriptors);
            }
            filtersForm.setDescriptors(descriptors);
            filtersForm.setFilters(filters);
            filtersForm.setFilterCount(filterManager.getFilterCount());

        } catch (IntrospectionException e) {
            logger.error("IntrospectionException error:" + e);
            e.printStackTrace();
        } catch (Exception e) {
            logger.error(" Exception error:" + e);
            e.printStackTrace();
        }
    }

	private boolean isInstalledFilter(RenderingFilterManager filterManager, Function<MessageVO,
			MessageVO> filter) {
        try {
            int filterCount = filterManager.getFilterCount();
            if (filter == null) {
                return false;
            }
            if (filterCount < 1) {
                return false;
            }
            String filterClassname = filter.getClass().getName();
            for (int i = 0; i < filterCount; i++) {
				Function<MessageVO, MessageVO> installedFilter = filterManager.getFilter(i);
                if (filterClassname.equals(installedFilter.getClass().getName())) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * /admin/filters/filtersAction.shtml?method=changePos
     */
    public ActionForward changePos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug(" enter changePos..");
        FiltersForm filtersForm = (FiltersForm) form;
        logger.debug(" filterIndex =" + filtersForm.getFilterIndex());
        if (filtersForm.getFilterIndex() < 0) {
            logger.error("filterIndex < 0");
        }

        RenderingFilterManager filterManager = getFilterManager(request);
        // Get the filter at the specified filter position
		Function<MessageVO, MessageVO> filter = filterManager.getFilter(filtersForm.getFilterIndex
				());
        // Remove it
        filterManager.removeFilter(filtersForm.getFilterIndex());
        // Re-add it based on the "direction" we're doing
        if (filtersForm.isUp()) {
            filterManager.addFilter(filter, filtersForm.getFilterIndex() - 1);
        }
        if (filtersForm.isDown()) {
            filterManager.addFilter(filter, filtersForm.getFilterIndex() + 1);
        }
        saveFilter(request);
        initForm(filtersForm, request);
        return mapping.findForward("forward");
    }

    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FiltersForm filtersForm = (FiltersForm) form;
        initForm(filtersForm, request);
        return mapping.findForward("forward");

    }

    public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug(" enter remove..");
        FiltersForm filtersForm = (FiltersForm) form;
        logger.debug(" pos =" + filtersForm.getPos());
   