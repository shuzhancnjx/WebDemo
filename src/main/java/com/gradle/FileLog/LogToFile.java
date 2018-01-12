package com.gradle.FileLog;

import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@AllArgsConstructor
public class LogToFile {

    private String filePath; // end with forward slash

    public void logText(String dataToLog, String uuid) throws IOException{ // factor this out to the logging package
        File file = new File(filePath + "text/" + uuid + ".txt");
        fileWrite(file, dataToLog);
    }

    public void logKeyStroke(String dataToLog, String uuid) throws IOException{
        File file = new File(filePath + "keystroke/" + uuid + ".txt");
        fileWrite(file, dataToLog);
    }

    private void fileWrite(File file, String dataToLog) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        String dataLogLineBreak = dataToLog + "\n";
        fileOutputStream.write(dataLogLineBreak.getBytes());
        fileOutputStream.flush();

    }

}
