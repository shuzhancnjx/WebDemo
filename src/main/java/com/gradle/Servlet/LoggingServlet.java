package com.gradle.Servlet;

import com.gradle.KeyStrokeAnalysis.KeyStrokeAnalysisImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;


@WebServlet(name="LoggingServlet", urlPatterns = {"logging"})
public class LoggingServlet extends HttpServlet {

    KeyStrokeAnalysisImpl keyStrokeAnalysis = new KeyStrokeAnalysisImpl("/Users/zhanshu/Desktop/log/log.txt", new Stack<>());
    protected void doPost(HttpServletRequest request, HttpServletResponse response){

        String[] logs = request.getParameterValues("log");
        System.out.println(logs[0]);

        try{
            logJson(logs[0]);
        } catch (IOException e){
            e.printStackTrace();
        }

        keyStrokeAnalysis.addKeyStroke(logs[0]);
    }

    public void logJson(String dataLog) throws IOException {
        File file = new File("/Users/zhanshu/Desktop/log/log.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        String dataLogLineBreak = dataLog + "\n";
        fileOutputStream.write(dataLogLineBreak.getBytes());
        fileOutputStream.flush();
    }
    
}
