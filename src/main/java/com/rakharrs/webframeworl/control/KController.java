package com.rakharrs.webframeworl.control;

import com.rakharrs.webframeworl.process.MethodRetriever;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//@WebServlet(name = "front", value = "/test/*")
public class FrontServlet extends HttpServlet {
    private HttpServletRequest request;
    private HttpServletResponse resp;
    private MethodRetriever retriever = new MethodRetriever(this);
    public String retrieveUrlKey(){
        HttpServletMapping mapping = request.getHttpServletMapping();
        return mapping.getMatchValue();
    }

    public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setReq(req); setResp(resp);
        String methodKey = this.retrieveUrlKey();
        Method m = getRetriever().getControllerMethods().get(methodKey);
        try {
            m.invoke(this);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setReq(HttpServletRequest req) {
        this.request = req;
    }

    public HttpServletResponse getResp() {
        return resp;
    }

    public void setResp(HttpServletResponse resp) {
        this.resp = resp;
    }

    public MethodRetriever getRetriever() {
        return retriever;
    }

    public void setRetriever(MethodRetriever retriever) {
        this.retriever = retriever;
    }
}
