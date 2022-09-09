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
package com.jdon.jivejdon.presentation.action.query;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.query.HoThreadCriteria;
import com.jdon.jivejdon.domain.model.query.QueryCriteria;
import com.jdon.jivejdon.presentation.form.QueryForm;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.Debug;

import javax.servlet.http.HttpServletRequest;

/**
 * ThreadQueryAction is call from Form by /message/threadViewQuery.shtml
 * ThreadQueryAction important is in query date format. need queryForm but
 * ThreadHotAction donot need queryForm. int ThreadQueryAction. queryForm is not
 * created by it, queryForm is created by before action : QueryViewAction.
 * ThreadHotAction is simple than ThreadQueryAction.
 * 
 * HOT1: date inpute is date range such as before 1 days, 2 days, 365 days HOT2:
 * date inpute is from yyyy-mm-dd to yyyy-mm-dd
 * 
 * @author banq(http://www.jdon.com)
 * 
 */
public c