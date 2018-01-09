package com.gradle.Servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "TextEnterKeyStrokeLoggingServlet", urlPatterns = {"Hello"}, loadOnStartup = 1)
public class TextEnterKeyStrokeLoggingServlet extends HttpServlet {
    private String uuid=""; // this is the uuid for the text and its keystrokes

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().print("Please describe your successful examples at work");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

        try{ // receive the text when clicking submission button
            String textInput = request.getParameter("text_input");
            String responseText = "Your text has been submitted";

            if(textInput == null) responseText = "Please enter your text again!";

            try{
                String filePath = "/Users/zhanshu//Desktop/log/" + uuid+".txt";
                System.out.println(filePath);
                FileWriter fileWriter = new FileWriter(new File(filePath), true);
                fileWriter.write(textInput);
                fileWriter.close();
            } catch (IOException e){
                e.printStackTrace();
            }

            request.setAttribute("res", responseText);
            request.getRequestDispatcher("response.jsp").forward(request, response);

        } catch (NullPointerException e){ // receive keystroke logs
            String logs = request.getParameter("log");
            if(uuid.equals(""))
                uuid = request.getParameter("uuid");
            System.out.println(uuid + " " + logs.toString()); // unique ID from the session
        }


    }
}

