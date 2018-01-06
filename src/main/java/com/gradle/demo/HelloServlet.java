package com.gradle.demo;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;

@WebServlet(name = "HelloServlet", urlPatterns = {"hello"}, loadOnStartup = 1)
public class HelloServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().print("Please describe your successful examples at work");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String textInput = request.getParameter("text_input");
        String responseText = "Your text has been submitted";
        if(textInput == null) responseText = "Please enter your text again!";

        try{
            FileWriter fileWriter = new FileWriter("/Users/zshu/Desktop/log/essay.txt");
            fileWriter.write(textInput);
            fileWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        request.setAttribute("res", responseText);
        request.getRequestDispatcher("response.jsp").forward(request, response);
    }
}
