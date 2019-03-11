package com.kudriashov.pgss.task2.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/agent")
public class UserAgentServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userAgent = req.getHeader("user-agent");
        String clientBrowser = "Not known!";
        if (userAgent != null) clientBrowser = userAgent;
        req.setAttribute("client.browser", clientBrowser);
        req.getRequestDispatcher("/agent.jsp").forward(req, resp);
    }

}
