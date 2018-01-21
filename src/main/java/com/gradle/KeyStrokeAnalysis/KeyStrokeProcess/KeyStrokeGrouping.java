package com.gradle.KeyStrokeAnalysis.KeyStrokeProcess;

import com.gradle.KeyStrokeAnalysis.Config.Punctuations;
import com.gradle.KeyStrokeAnalysis.Model.Chunk;
import lombok.Builder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Collectors;

@Builder
public class KeyStrokeGrouping {
    public @NonNull
    ArrayList<Chunk> actionList;
    public ArrayList<Chunk> groupedChunksI;
    public ArrayList<Chunk> groupedChunksII;
    public ArrayList<Chunk> groupedChunksIII;

    public void groupChunks(){
        groupedChunksI = groupChunksI(
                new ArrayList<>(actionList.stream().map(x -> x.clone()).collect(Collectors.toList())));
        groupedChunksII = groupChunksII(
                new ArrayList<>(groupedChunksI.stream().map(x ->  x.clone() ).collect(Collectors.toList())));
        groupedChunksIII = groupChunksIII(
                new ArrayList<>(groupedChunksII.stream().map(x ->  x.clone() ).collect(Collectors.toList())));

    }

    private ArrayList<Chunk> groupChunksI(ArrayList<Chunk> actionList){ // separate chunks by whitespace, punctuations, eventType switch

        ArrayList<Chunk> combinedChunk = new ArrayList<>();
        Chunk preChunk = actionList.get(0);

        for(int i=1; i< actionList.size(); i++){
            Chunk chunk = actionList.get(i);

            if(chunk.actionApplied.equals("Paste")
                    || chunk.actionApplied.equals("Cut")
                    || chunk.actionApplied.equals("Replace")
                    || chunk.actionApplied.equals("Click")){

                combinedChunk.add(preChunk);

                preChunk=chunk;

            }else if (chunk.actionApplied.equals(preChunk.actionApplied)){

                if(chunk.chunk.equals(" ")
                        || preChunk.chunk.equals(" ")
                        || Punctuations.punctuations.contains(chunk.chunk)
                        || Punctuations.punctuations.contains(preChunk.chunk)){
                    // whitespace and punctuations of separating words
                    combinedChunk.add(preChunk);
                    preChunk = chunk;

                } else { // handle delete actions

                    preChunk.chunk += chunk.chunk;

                    if(chunk.actionApplied.equals("Insert"))
                        preChunk.endPos = chunk.endPos;
                    else
                        preChunk.startPos = chunk.startPos;

                    preChunk.endTime = chunk.endTime;
                }
            } else{
                combinedChunk.add(preChunk);
                preChunk = chunk;
            }

        }

        combinedChunk.add(preChunk); // add the last chunk;
        return combinedChunk;
    }


    private ArrayList<Chunk> groupChunksII(ArrayList<Chunk> groupedChunks){ // combine adjacent delete

        Deque<Chunk> combinedChunks = new LinkedList<>();
        int i=0;
        while(i<groupedChunks.size()){
            Chunk curChunk = groupedChunks.get(i);

            if(curChunk.actionApplied.equals("Click")
                    || curChunk.actionApplied.equals("Paste")
                    || curChunk.actionApplied.equals("Replace")
                    || curChunk.actionApplied.equals("Cut")){
                combinedChunks.addLast(curChunk);
                i+=1;
                continue;
            }

            if(curChunk.actionApplied.equals("Del")){ // detect consecutive deletion
                int j = i;
                while (j+1 < groupedChunks.size()){
                    Chunk futureChunk = groupedChunks.get(j+1);
                    if(futureChunk.actionApplied.equals("Del")
                            && futureChunk.endPos.equals(curChunk.startPos)){
                        curChunk.chunk += futureChunk.chunk;
                        curChunk.startPos = futureChunk.startPos;
                        curChunk.endTime = futureChunk.endTime;
                        j+=1;

                    } else{
                        break;
                    }
                }
                curChunk.chunk = "[" + curChunk.chunk + "]";
                i = j;
            }

            while(combinedChunks.size()>0
                    && combinedChunks.getLast().endPos>curChunk.startPos
                    && combinedChunks.getLast().endPos<= curChunk.endPos
                    && combinedChunks.getLast().actionApplied.equals("Insert")
                    && !Punctuations.punctuations.contains(curChunk.chunk)){
                // rolling back to combine the deletes and corresponding immediate inserts

                Chunk lastChunk = combinedChunks.pollLast();
                curChunk.chunk = lastChunk.chunk + curChunk.chunk;

                if(curChunk.startPos < lastChunk.startPos) {
                    curChunk.endPos = lastChunk.startPos;
                } else {
                    curChunk.endPos = curChunk.startPos;
                    curChunk.startPos = lastChunk.startPos;
                }  // calculating the meaningful chunk size after combining deleted and previous insertion

                curChunk.startTime = lastChunk.startTime;
                curChunk.actionApplied = lastChunk.actionApplied + "+"+ curChunk.actionApplied;
            }
            combinedChunks.addLast(curChunk);
            i+=1;
        }
        return new ArrayList<>(combinedChunks);
    }

    public ArrayList<Chunk> groupChunksIII(ArrayList<Chunk> groupedChunksII){
        // combine adjacent chunks with position overlap between consecutive
        // whitespace and/or punctuations or other separators.

        Deque<Chunk> combinedChunks = new LinkedList<>();

        int i=0;
        while(i<groupedChunksII.size()){

            Chunk curChunk = groupedChunksII.get(i);
            if(curChunk.chunk.equals(" ")
                    || Punctuations.punctuations.contains(curChunk.chunk)
                    || curChunk.actionApplied.equals("Click")
                    || curChunk.actionApplied.equals("Cut")
                    || curChunk.actionApplied.equals("Paste")
                    || curChunk.actionApplied.equals("Replace")){
                combinedChunks.addLast(curChunk);
                i+=1;
                continue;
            }
            int j = i;
            while(j+1<groupedChunksII.size()){
                Chunk nextChunk = groupedChunksII.get(j+1);
                if (! (nextChunk.chunk.equals(" ") || Punctuations.punctuations.contains(nextChunk.chunk))
                        && curChunk.endPos == nextChunk.startPos
                        && (nextChunk.actionApplied.equals("Insert") || nextChunk.actionApplied.equals("Del"))
                        && nextChunk.startPos!=nextChunk.endPos){
                    curChunk.chunk += nextChunk.chunk;
                    curChunk.startPos = Math.min(curChunk.startPos, nextChunk.startPos);
                    curChunk.endPos = Math.max(curChunk.endPos, nextChunk.endPos);
                    curChunk.endTime = nextChunk.endTime;
                    curChunk.actionApplied += "+"+ nextChunk.actionApplied;
                    j+=1;
                } else{
                    break;
                }
                i=j; // jump the i index and so skip the scanned chunks in this while-loop
            }
            combinedChunks.addLast(curChunk);
            i+=1;
        }
        return new ArrayList<>(combinedChunks);
    }

//    public ArrayList<Chunk> getGroupedChunksIV(){
//        // flagging jumps
//    }



}

