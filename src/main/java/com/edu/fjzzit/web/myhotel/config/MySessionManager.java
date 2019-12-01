package com.edu.fjzzit.web.myhotel.config;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

public class MySessionManager extends DefaultWebSessionManager {

//    /**
//     * 重写getSessionId，获取Session会话
//     * @param request
//     * @param response
//     * @return
//     */
//    @Override
//    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
//        String sessionId= WebUtils.toHttp(request).getHeader("token");
//
//        if (sessionId==null){
//            super.getSessionId(request,response);
//        }else{
//            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,"token");
//            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
//            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
//            request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED,false);
//        }
//        return sessionId;
//    }
}
