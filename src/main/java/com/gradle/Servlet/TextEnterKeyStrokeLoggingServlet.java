package com.gradle.Servlet;


import com.gradle.FileLog.LogToFile;
import com.gradle.ML.EssayStatistics.Statistics;
import com.gradle.ML.OpenNLP.WordSentenceParser;
import com.gradle.ML.WordCorrect.WordCorrect;
import com.gradle.MySql.SqlOperations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings("unchecked")
@WebServlet(name = "TextEnterKeyStrokeLoggingServlet", urlPatterns = {"Hello"}, loadOnStartup = 1)
public class TextEnterKeyStrokeLoggingServlet extends HttpServlet {
    private String uuid="";           // this is the uuid for the text and its keystrokes
    final String filePathToLog = "/Users/zhanshu/Desktop/log/";

    LogToFile logToFile = new LogToFile(filePathToLog);
    HttpRequestParse httpRequestParse = new HttpRequestParse();
    SqlOperations sqlOperations = new SqlOperations();

    Statistics statistics = new Statistics(new WordSentenceParser(), new WordCorrect());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().print("Please describe your successful examples at work");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

        Map<String, String> requestMap = httpRequestParse.parseRequest(request);

        if(requestMap.containsKey("text_input")){
            String textInput = requestMap.get("text_input");
            String responseText = "Your text has been submitted";

            if(textInput == null) {
                responseText = "Please enter your text again!";
            } else{
                logToFile.logText(textInput, uuid);
                sqlOperations.writeToLogTable(uuid, textInput, "Essay");
                System.out.println(
                "characters " + statistics.getNumberOfCharacters(textInput) + "\n" +
                "words " + statistics.getNumberOfWords(textInput) + "\n" +
                "sentence " + statistics.getNumberOfSentence(textInput) + "\n" +
                "wrong words " + statistics.getNumberOfWrongWords(textInput).toString());
            }



            request.setAttribute("res", responseText);
            request.getRequestDispatcher("response.jsp").forward(request, response);

        } else{
            String logs = requestMap.get("log");
            uuid = requestMap.get("uuid");
            System.out.println(uuid + " " + logs.toString()); // unique ID from the session

            logToFile.logKeyStroke(logs, uuid);
            sqlOperations.writeToLogTable(uuid, logs, "KeyStrokeLog");
        }
    }

}

