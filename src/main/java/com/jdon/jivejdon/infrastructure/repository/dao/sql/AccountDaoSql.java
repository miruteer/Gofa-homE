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
package com.jdon.jivejdon.infrastructure.repository.dao.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.account.PasswordassitVO;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountRepository;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountInitFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.AccountDao;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.model.query.PageIteratorSolver;

/**
 * @author <a href="mailto:banqJdon<AT>jdon.com">banq</a