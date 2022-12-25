/*
 * Copyright 2003-2009 the original author or authors.
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
package com.jdon.jivejdon.spi.component.subscription;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.domain.model.account.Account;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumMessage;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.property.ThreadTag;
import com.jdon.jivejdon.domain.model.subscription.event.AccountSubscribedNotifyEvent;
import com.jdon.jivejdon.domain.model.subscription.event.ForumSubscribedNotifyEvent;
import com.jdon.jivejdon.domain.model.subscription.event.SubscribedNotifyEvent;
import com.jdon.jivejdon.domain.model.subscription.event.TagSubscribedNotifyEvent;
import com.jdon.jivejdon.domain.model.subscription.event.ThreadSubscribedNotifyEvent;
import com.jdon.jivejdon.domain.model.subscription.messsage.AccountNotifyMessage;
import com.jdon.jivejdon.domain.model.subscription.messsage.ForumNotifyMessage;
import com.jdon.jivejdon.domain.model.subscription.messsage.TagNotifyMessage;
import com.jdon.jivejdon.domain.model.subscription.messsage.ThreadNotifyMessage;
import com.jdon.jivejdon.domain.model.subscription.notifysubscribed.AccountNotifySubscribed;
import com.jdon.jivejdon.domain.model.subscription.notifysubscribed.ForumNotifySubscribed;
import com.jdon.jivejdon.domain.model.subscription.notifysubscribed.NotifySubscribed;
import com.jdon.jivejdon.domain.model.subscription.notifysubscribed.TagNotifySubscribed;
import com.jdon.jivejdon.domain.model.subscription.notifysubscribed.ThreadNotifySubscribed;
import com.jdon.jivejdon.infrastructure.repository.acccount.AccountFactory;
import com.jdon.jivejdon.infrastructure.repository.ForumFactory;
import com.jdon.jivejdon.infrastructure.repository.property.TagRepository;

import java.util.