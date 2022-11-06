/**
 * Copyright 2005 Jdon.com
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

package com.jdon.jivejdon.presentation.sitemap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.spi.component.sitemap.SitemapRepository;
import com.jdon.jivejdon.spi.component.sitemap.SitemapService;
import com.jdon.strutsutil.ModelListAction;

/**
 * CQRS
 * 
 * query directly from UserRepository, no need by userService
 * 
 * 
 * @author banq
 * 
 */
public class UrlListAction extends ModelListAction {
	private final static Logger logger = Logger.getLogger(UrlListAction.class);

	/**
	 * CQRS
	 * 
	 * query directly from UserRepository
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param start
	 *            int
	 * @param count
	 *            int
	 * @return PageIterator
	 * @todo Implement this com.jdon.strutsutil.ModelListAction method
	 */
	public PageIterator getPageIterator(HttpServletRequest request, int 