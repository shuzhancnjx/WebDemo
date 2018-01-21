package com.gradle.KeyStrokeAnalysis.KeyStrokeProcess;

import com.gradle.KeyStrokeAnalysis.Model.Chunk;
import lombok.Builder;
import lombok.NonNull;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;

@Builder
public class TextConstruction {

// create chunks and construct the final text based on the raw keystroke logs
// remove unnecessary keys at first
// construct the text based on the logs;
// create chunks for afterward analysis, including startPos, endPos, startTime, endTime, and actionApplied
// startPos is inclusive and endPos is exclusive; endPos is greater or equal to startPos;
// create eventType, including click, paste, cut, replace, del, insert

    private @NonNull
    String fileName;
    public @NonNull StringBuilder text ;
    public @NonNull
    ArrayList<Chunk> actionList;
    public @NonNull
    Deque<JSONObject> deque;
    public String startTime;
    public String submitTime;

    public void constructText() throws IOException {

        removeUnnecessaryKeys(fileName);
        Iterator<JSONObject> iterator = deque.iterator();
        JSONObject preJson = deque.peekFirst();

        while (iterator.hasNext()){

            JSONObject temp= iterator.next();
            String eventType = temp.getString("eventType");
            int startPos = temp.getInt("startPosition");
            int endPos = temp.getInt("endPosition");

            if(eventType.equals("keydown")){
                String pressedKey= temp.getString("pressedKey");

                if(pressedKey.equals("Backspace")){
                    if(startPos==endPos) { // a single character delete
                        actionList.add(
                                Chunk.builder().chunk(text.substring(startPos-1, endPos))
                                        .startPos(startPos-1)
                                        .endPos(endPos)
                                        .actionApplied("Del")
                                        .startTime(preJson.getString("Timestamp"))
                                        .endTime(temp.getString("Timestamp"))
                                        .build());
                        text.delete(startPos-1, endPos);
                    }
                    else { // handle a big chunk delete
                        actionList.add(
                                Chunk.builder().chunk(text.substring(startPos, endPos+1))
                                        .startPos(startPos)
                                        .endPos(endPos+1)
                                        .actionApplied("Del")
                                        .startTime(preJson.getString("Timestamp"))
                                        .endTime(temp.getString("Timestamp"))
                                        .build());
                        text.delete(startPos, endPos+1);
                    }
                } else {  // handle insert
                    if (startPos!=endPos) { // replacement: insert upon a selected chunk of characters
                        actionList.add(
                                Chunk.builder().chunk(text.substring(startPos, endPos))
                                        .startPos(startPos)
                                        .endPos(endPos)
                                        .actionApplied("Replace")
                                        .startTime(preJson.getString("Timestamp"))
                                        .endTime(temp.getString("Timestamp"))
                                        .build());
                        text.delete(startPos, endPos);
                    }

                    if(pressedKey.length()==1) { // handle a single key insert
                        text.insert(startPos, pressedKey);

                        actionList.add(
                                Chunk.builder().chunk(text.substring(startPos, endPos+1))
                                        .startPos(startPos)
                                        .endPos(endPos+1)
                                        .actionApplied("Insert")
                                        .startTime(preJson.getString("Timestamp"))
                                        .endTime(temp.getString("Timestamp"))
                                        .build());
                    } else if(pressedKey.equals("Enter")) {// handle enter key
                        text.insert(startPos, "\n");
                        actionList.add(
                                Chunk.builder().chunk(text.substring(startPos, endPos+1))
                                        .startPos(startPos)
                                        .endPos(endPos+1)
                                        .actionApplied("Insert")
                                        .startTime(preJson.getString("Timestamp"))
                                        .endTime(temp.getString("Timestamp"))
                                        .build());
                    }  // expand other special characters

                }
            } else if(eventType.equals("paste")){
                if (startPos!=endPos) {
                    actionList.add(
                            Chunk.builder().chunk(text.substring(startPos, endPos))
                                    .startPos(startPos)
                                    .endPos(endPos)
                                    .actionApplied("Replace")
                                    .startTime(preJson.getString("Timestamp"))
                                    .endTime(temp.getString("Timestamp"))
                                    .build());
                    text.delete(startPos, endPos);
                }

                String toBePasted = temp.getString("textPasted");
                text.insert(startPos, toBePasted );
                actionList.add(
                        Chunk.builder().chunk(toBePasted)
                                .startPos(startPos)
                                .endPos(startPos+ toBePasted.length())
                                .actionApplied("Paste")
                                .startTime(preJson.getString("Timestamp"))
                                .endTime(temp.getString("Timestamp"))
                                .build()
                );

            } else if(eventType.equals("click")){
                while (startPos>=text.length()){
                    text.append(" ");
                }  // make sure the string length is long enough to store the new inserted letters
                actionList.add(
                        Chunk.builder().chunk("")
                                .startPos(startPos)
                                .endPos(startPos)
                                .actionApplied("Click")
                                .startTime(preJson.getString("Timestamp"))
                                .endTime(temp.getString("Timestamp"))
                                .build()
                );
            } else if(eventType.equals("cut")){
                actionList.add(
                        Chunk.builder().chunk(text.substring(startPos, endPos))
                                .startPos(startPos)
                                .endPos(endPos)
                                .actionApplied("Cut")
                                .startTime(preJson.getString("Timestamp"))
                                .endTime(temp.getString("Timestamp"))
                                .build()
                );
                text.delete(startPos, endPos);
            }
            preJson = temp; // save as last Json file.
        }

    }

    public void removeUnnecessaryKeys(String fileName) throws IOException {

        ArrayList<String> list = rawLogFileReading(fileName); // reading from log files.

        JSONObject jsonObjectFirst = new JSONObject(list.get(0));
        if (!jsonObjectFirst.getString("eventType").equals("BeginEssay")) {
            deque.addFirst(jsonObjectFirst);
        } // if the first Json is not BeginEssay, we will add it into deque for analysis, otherwise just record its time

        startTime = jsonObjectFirst.getString("Timestamp");

        for(int i=1; i<list.size()-1; i++){

            JSONObject curJsonObject = new JSONObject(list.get(i));
            String eventType = curJsonObject.getString("eventType") ;

            switch(eventType){
                case "keyup":
                    continue;
                case "cut":
                    JSONObject lastOne = deque.pollLast();
                    JSONObject lastTwo = deque.pollLast();
                    if(lastOne.getString("pressedKey").equals("x") && lastTwo.getString("pressedKey").equals("Meta"))
                        deque.addLast(curJsonObject);
                    continue;
                case "paste":
                    JSONObject lastObject = deque.pollLast();
                    JSONObject lastObject1  = deque.pollLast();
                    if(lastObject.getString("pressedKey").equals("v") && lastObject1.getString("pressedKey").equals("Meta"))
                        deque.addLast(curJsonObject);
                    continue;
            }

            if(curJsonObject.has("pressedKey") &&
                    !curJsonObject.getString("pressedKey").equals("Shift"))
                deque.addLast(curJsonObject);
        }

        JSONObject jsonObjectLast = new JSONObject(list.get(list.size()-1));
        if (!jsonObjectLast.getString("eventType").equals("SubmitEssay")){
            deque.addLast(jsonObjectLast);
        } // if the lastJson is not "SubmitEssay" we will add it into deque for analysis, otherwise we will just record its time

        submitTime = jsonObjectLast.getString("Timestamp");
    }

    private ArrayList<String> rawLogFileReading( String fileName) throws IOException {
        ArrayList<String> list = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            list.add(line);
        }
        return list;
    }


}
