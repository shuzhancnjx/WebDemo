package com.gradle.Servlet;

import com.gradle.KeyStrokeAnalysis.KeyStrokeAnalysisImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;

@Deprecated
@WebServlet(name="LoggingServlet", urlPatterns = {"logging"})
 public class LoggingServlet extends HttpServlet {

    KeyStrokeAnalysisImpl keyStrokeAnalysis = KeyStrokeAnalysisImpl.builder()
            .fileName("/Users/zhanshu/Desktop/log/log.txt")
            .jsonObjectStack(new Stack<>())
            .build();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String[] logs = request.getParameterValues("log");
        String[] uuid = request.getParameterValues("uuid");
        System.out.println(uuid[0]); // unique ID from the session

        try{ // factor this out to the Logging package
            logJson(logs[0]);
        } catch (IOException e){
            e.printStackTrace();
        }

        keyStrokeAnalysis.addKeyStroke(logs[0]);

    }

    public void logJson(String dataLog) throws IOException { // factor this out to the logging package
        File file = new File("/Users/zhanshu/Desktop/log/log.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        String dataLogLineBreak = dataLog + "\n";
        fileOutputStream.write(dataLogLineBreak.getBytes());
        fileOutputStream.flush();
    }
    
}
