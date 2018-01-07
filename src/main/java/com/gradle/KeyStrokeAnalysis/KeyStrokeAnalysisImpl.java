package com.gradle.KeyStrokeAnalysis;

import com.gradle.KeyStrokeAnalysis.KeyStrokeAnalysis;
import lombok.AllArgsConstructor;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.stream.Collectors;

@AllArgsConstructor
public class KeyStrokeAnalysisImpl implements KeyStrokeAnalysis {

    private  String fileName ; // logfile path
    Stack<JSONObject> jsonObjectStack;

    @Override
    public void addKeyStroke(String jsonString){

        JSONObject jsonObject = new JSONObject(jsonString);
        jsonObjectStack.push(jsonObject);
    }

    public void analyzeKeyStrokeStream() throws IOException{

        TextConstruction textConstruction = TextConstruction.builder()
                .deque(new LinkedList<>())
                .text(new StringBuilder())
                .actionList(new ArrayList<>())
                .fileName(fileName)
                .build();
        textConstruction.constructText();

        String text = textConstruction.text.toString().trim();
        String submittedText = fileReading("/Users/zshu/Desktop/log/essay.txt")
                .stream().collect(Collectors.joining("")).trim();
        if (text.equals(submittedText)){
            System.out.println("Text Matched,!!!"); // check if the submitted equals to the constructed ones
        }

        TimeDiff timeDiff = new TimeDiff( "yyyy-MM-dd'T'HH:mm:ss.SSSz");
        System.out.println("Total time: " + timeDiff.getTimeDiff(textConstruction.startTime, textConstruction.submitTime) + " milliseconds");

        ArrayList<Chunk> actionList = textConstruction.actionList; // generate groupedString
        KeyStrokeGrouping keyStrokeGrouping = KeyStrokeGrouping.builder().actionList(actionList).build(); // group individual keystrokes to grouped strings separate by whitespace/period
        keyStrokeGrouping.groupChunks();

//        writeFile("/Users/zshu/Desktop/log/combinedChunk.txt", actionList);
        writeFile("/Users/zshu/Desktop/log/combinedChunk.txt", keyStrokeGrouping.groupedChunksIII);


        TimeAnalysisImpl timeAnalysis = TimeAnalysisImpl.builder().groupedChunksI(keyStrokeGrouping.groupedChunksI)
                .beginTime(textConstruction.startTime)
                .submitTime(textConstruction.submitTime)
                .pauseBetweenSentences(new ArrayList<>())
                .pauseBetweenWords(new ArrayList<>())
                .timeOfDeletion(new ArrayList<>())
                .timeDiff(new TimeDiff("yyyy-MM-dd'T'HH:mm:ss.SSSz" ))
                .build();

        timeAnalysis.timeClassify();
        System.out.println("Average pause between sentences: " + timeAnalysis.betweenSentencesPause());
        System.out.println("Average pause between words: " + timeAnalysis.betweenWordsPause());
        System.out.println("Percentage of time of deletion over the total time: " + timeAnalysis.timeOfDeletion());

        EditsSummary editsSummary = EditsSummary.builder()
                .groupedChunksI(keyStrokeGrouping.groupedChunksI)
                .groupedChunksIII(keyStrokeGrouping.groupedChunksIII)
                .numberOfDeletedLetters(0)
                .numberOfInsertedLetters(0)
                .arrayOfWordsWithEdits(new ArrayList<>())
                .arrayOfWordsWithoutEdits(new ArrayList<>())
                .build();

        editsSummary.getSummary();
        System.out.println("Total Number of Deleted Letters: " + editsSummary.numberOfDeletedLetters);
        System.out.println("Total Number of Inserted Letters: " + editsSummary.numberOfInsertedLetters);

        System.out.println("Array of Words without edits: " + editsSummary.arrayOfWordsWithoutEdits.toString());
        System.out.println("Array of Words with edits: " + editsSummary.arrayOfWordsWithEdits.toString());

    }

    public ArrayList<String> fileReading( String fileName) throws IOException {
        ArrayList<String> list = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println("\""+line+"\",");
            list.add(line);
        }
        return list;
    }

    public void writeFile(String fileName, ArrayList<Chunk> combinedChunk ) throws IOException {
        FileWriter fileWriter  = new FileWriter( new File(fileName));

        Iterator<Chunk> iterator = combinedChunk.iterator();
        while(iterator.hasNext()){
            Chunk chunk = iterator.next();
            System.out.println(chunk.toString());
            fileWriter.write(chunk.toString()+"\n");
        }
        fileWriter.flush();
        fileWriter.close();

    }
}