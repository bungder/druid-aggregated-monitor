package io.github.bungder.druid.monitor.servlet;

import com.alibaba.druid.support.http.ResourceServlet;
import io.github.bungder.druid.monitor.config.DruidConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 聚合的视图
 * @author 谭仕昌
 * @date 2017-08-30 14:19
 */
public abstract class AggregatedResourceServlet extends ResourceServlet {


    public AggregatedResourceServlet(String resourcePath) {
        super(resourcePath);
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String requestURI = request.getRequestURI();

        response.setCharacterEncoding("utf-8");

        if (contextPath == null) { // root context
            contextPath = "";
        }
        String uri = contextPath + servletPath;
        String path = requestURI.substring(contextPath.length() + servletPath.length());

        if (!isPermittedRequest(request)) {
            path = "/nopermit.html";
            returnResourceFile(path, uri, response);
            return;
        }

        if ("/submitLogin".equals(path)) {
            String usernameParam = request.getParameter(PARAM_NAME_USERNAME);
            String passwordParam = request.getParameter(PARAM_NAME_PASSWORD);
            if (username.equals(usernameParam) && password.equals(passwordParam)) {
                request.getSession().setAttribute(SESSION_USER_KEY, username);
                response.getWriter().print("success");
            } else {
                response.getWriter().print("error");
            }
            return;
        }

        if (isRequireAuth() //
                && !ContainsUser(request)//
                && !("/login.html".equals(path) //
                || path.startsWith("/css")//
                || path.startsWith("/js") //
                || path.startsWith("/img"))) {
            if (contextPath.equals("") || contextPath.equals("/")) {
                response.sendRedirect("/" + DruidConfig.VIEW_PATH + "/login.html");
            } else {
                if ("".equals(path)) {
                    response.sendRedirect(DruidConfig.VIEW_PATH + "/login.html");
                } else {
                    response.sendRedirect("login.html");
                }
            }
            return;
        }

        if ("".equals(path)) {
            if (contextPath.equals("") || contextPath.equals("/")) {
                response.sendRedirect("/instances");
            } else {
                response.sendRedirect("instances");
            }
            return;
        }

        if ("/".equals(path) || "/index.html".equals(path)) {
            response.sendRedirect("instances");
            return;
        }
        if("/instances".equals(path) || "instances".equals(path)){
            response.getWriter().print(listInstances());
            return;
        }

        if (path.contains(".json")) {
            String fullUrl = path;
            if (request.getQueryString() != null && request.getQueryString().length() > 0) {
                fullUrl += "?" + request.getQueryString();
            }
            response.getWriter().print(process(fullUrl));
            return;
        }

        // find file in resources path
        returnResourceFile(path, uri, response);
    }

    /**
     * 列出所有实例
     * @return
     */
    public abstract String listInstances() throws IOException;
}
