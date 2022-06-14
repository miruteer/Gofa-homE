package com.jdon.jivejdon.domain.model.subscription.notifysubscribed;

import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.shortmessage.ShortMessage;
import com.jdon.jivejdon.domain.model.subscription.Subscription;
import com.jdon.jivejdon.domain.model.subscription.messsage.ThreadNotifyMessage;
import com.jdon.util.StringUtil;

public class ThreadNotifySubscribed implements NotifySubscribed {

	protected final ForumThread forumThread;

	private ThreadNotifyMessage threadNotifyMessage;

	public ThreadNotifySubscribed(Foru