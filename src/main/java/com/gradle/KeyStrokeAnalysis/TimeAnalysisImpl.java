package com.gradle.KeyStrokeAnalysis;

import lombok.Builder;
import lombok.NonNull;

import java.util.ArrayList;

@Builder
public class TimeAnalysisImpl implements TimeAnalysis {

    public @NonNull
    ArrayList<Chunk> groupedChunksI;
    public @NonNull String beginTime;
    public @NonNull String submitTime;


    private ArrayList<Long> pauseBetweenWords;
    private ArrayList<Long> pauseBetweenSentences ;
    private ArrayList<Long> timeOfDeletion;
    private TimeDiff timeDiff ;
    public void timeClassify(){




        for(int i=1; i<groupedChunksI.size()-1; i++){

            Chunk curChunk = groupedChunksI.get(i);
            Chunk nextChunk = groupedChunksI.get(i+1);
            Chunk preChunk = groupedChunksI.get(i-1);

            if ( curChunk.actionApplied.startsWith("Del") ) {
                timeOfDeletion.add(timeDiff.getTimeDiff(curChunk.startTime, curChunk.endTime));
            }

            if (curChunk.chunk.equals(" ")
                    && preChunk.endPos.equals(curChunk.startPos)
                    && curChunk.endPos.equals(nextChunk.startPos)
                    && nextChunk.actionApplied.startsWith("Insert")){
                // check if the whitespace is in the middle between pre and following chunks, and the whitespace followed by an insertion

                if(Punctuations.sentenceSepators.contains(preChunk.chunk)){
                    pauseBetweenSentences.add(timeDiff.getTimeDiff(curChunk.startTime, curChunk.endTime));
                }else {
                    pauseBetweenWords.add(timeDiff.getTimeDiff(curChunk.startTime, curChunk.endTime));
                }
            }
        }
    }

    @Override
    public Double betweenWordsPause(){
        return (double) pauseBetweenWords.stream().reduce(0L, (x, y) -> x + y) / pauseBetweenWords.size();
    }

    @Override
    public Double betweenSentencesPause(){
        return (double) pauseBetweenSentences.stream().reduce(0L, (x, y)-> x+y)/ pauseBetweenSentences.size();
    }

    @Override
    public Double timeOfDeletion(){
        return (double) timeOfDeletion.stream().reduce(0L, (x,y)-> x+y)/ timeDiff.getTimeDiff(beginTime, submitTime);
    }
}
