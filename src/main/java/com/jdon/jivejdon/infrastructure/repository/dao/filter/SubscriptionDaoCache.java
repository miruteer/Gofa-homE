package com.jdon.jivejdon.infrastructure.repository.dao.filter;

import com.jdon.annotation.Component;
import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.jdon.jivejdon.util.Constants;
import com.jdon.jivejdon.domain.model.subscription.Subscription;
import com.jdon.jivejdon.infrastructure.repository.subscription.SubscriptionInitFactory;
import com.jdon.jivejdon.infrastructure.repository.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.infrastructure.repository.dao.sql.SubscriptionDaoSql;
import com.jdon.jivejdon.util.ContainerUtil;

@Component("subscriptionDaoCache")
@Introduce("modelCache")
public class SubscriptionDaoCache extends SubscriptionDaoSql {

	public SubscriptionDaoCache(JdbcTempSource jdbcTempSourc