
package com.jdon.jivejdon.presentation.action;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.domain.model.Forum;
import com.jdon.jivejdon.domain.model.ForumThread;
import com.jdon.jivejdon.domain.model.query.HoThreadCriteria;
import com.jdon.jivejdon.api.query.ForumMessageQueryService;
import com.jdon.jivejdon.api.ForumService;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.strutsutil.ModelListForm;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class MaxThreadListAction extends ModelListAction {
    private final static String module = ThreadListAction.class.getName();
    private ForumMessageQueryService forumMessageQueryService;


    public ForumMessageQueryService getForumMessageQueryService() {
        if (forumMessageQueryService == null)
            forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", this.servlet.getServletContext());
        return forumMessageQueryService;
    }

    public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {

        HoThreadCriteria queryCriteria = new HoThreadCriteria();

        String dateRange = "1";
        if (request.getParameter("dateRange") != null)
            dateRange = request.getParameter("dateRange");
        queryCriteria.setDateRange(dateRange);
        Debug.logVerbose("ThreadHotAction dateRange=" + dateRange + " count=" + count, module);

        String messageReplyCountWindowS = request.getParameter("messageReplyCountWindow");
        int messageReplyCountWindow = 10; // if reply num is greate than 10,
        // it
        // is hot thread
        if (!UtilValidate.isEmpty(messageReplyCountWindowS)) {
            messageReplyCountWindow = Integer.parseInt(messageReplyCountWindowS);
        }
        queryCriteria.setMessageReplyCountWindow(messageReplyCountWindow);

        return getForumMessageQueryService().getHotThreads(queryCriteria, start, count);
    }

    public Object findModelIFByKey(HttpServletRequest request, Object key) {
        ForumThread thread = null;
        try {
            thread = getForumMessageQueryService().getThread((Long) key);
        } catch (Exception e) {
            Debug.logError("getThread error:" + e, module);
        }
        return thread;
    }

    public void customizeListForm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, ModelListForm modelListForm)
            throws Exception {
        ForumService forumService = (ForumService) WebAppUtil.getService
                ("forumService", this.servlet.getServletContext());
        String forumId = request.getParameter("forum");
        if (forumId == null)
            forumId = request.getParameter("forumId");

        Forum forum = null;
        if ((forumId == null) || !UtilValidate.isInteger(forumId) || forumId.length()>10) {
            forum = new Forum();
            forum.setName("????????????");
        } else {
            forum = forumService.getForum(new Long(forumId));
        }
        if (forum == null)
            throw new Exception("forum is null forumid=" + forumId);
        modelListForm.setOneModel(forum);
    }

}