package com.gradle.demo;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class KeyStrokeAnalysisImpl implements KeyStrokeAnalysis{

     Stack<JSONObject> jsonObjectStack = new Stack<>();

     @Override
     public void addKeyStroke(String jsonString){

         JSONObject jsonObject = new JSONObject(jsonString);
         jsonObjectStack.push(jsonObject);
         analyzeKeyStrokeStream();
    }

    @Override
    public void analyzeKeyStrokeStream() {




    }
}
