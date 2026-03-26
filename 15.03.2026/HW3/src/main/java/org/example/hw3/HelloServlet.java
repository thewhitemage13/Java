package org.example.hw3;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/number")
public class HelloServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String numberParam = request.getParameter("number");

        if (numberParam == null || numberParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "параметр number відсутній");
            return;
        }

        int number = 0;

        try {
            number = Integer.parseInt(numberParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "неправильний числовий формат");
            return;
        }

        int result = number + 1;

        response.setContentType("text/plain");
        response.getWriter().write("Ось уже й результати: " + result);
    }
}
