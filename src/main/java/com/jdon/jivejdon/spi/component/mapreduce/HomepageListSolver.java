package com.jdon.jivejdon.spi.component.mapreduce;

import com.jdon.annotation.Component;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.query.ResultSort;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpec;
import com.jdon.jivejdon.domain.model.query.specification.ThreadListSpecForMod;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Home page approve threads
 */
@Component("homepageListSolver")
public class HomepageListSolver {

	private final ThreadApprovedNewList threadAppr