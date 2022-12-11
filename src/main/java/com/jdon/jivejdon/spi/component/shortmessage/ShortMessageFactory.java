/*
 * Copyright (c) 2008 Ge Xinying
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jdon.jivejdon.spi.component.shortmessage;

import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import com.jdon.annotation.Component;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.shortmessage.ShortMessage;
import com.jdon.jivejdon.domain.model.shortmessage.ShortMessageState;
import com.jdon.jivejdon.domain.event.ATUserNotifiedEvent;
import com.jdon.jivejdon.infrast