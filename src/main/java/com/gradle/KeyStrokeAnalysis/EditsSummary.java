package com.gradle.KeyStrokeAnalysis;

import lombok.Builder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Iterator;

@Builder
public class EditsSummary {

    private @NonNull
    ArrayList<Chunk> groupedChunksI;
    private @NonNull ArrayList<Chunk> groupedChunksIII;

    public  @NonNull Integer numberOfInsertedLetters;
    public  @NonNull Integer numberOfDeletedLetters;
    public  @NonNull ArrayList<String> arrayOfWordsWithEdits;
    public  @NonNull ArrayList<String> arrayOfWordsWithoutEdits;


    public void getSummary(){
        summarizeEditsOfGroupedChunksI();
        summarizedEditsOfGroupedChunksIII();
    }

    private void summarizeEditsOfGroupedChunksI(){
        Iterator<Chunk> chunkIterator = groupedChunksI.iterator();
        while(chunkIterator.hasNext()){

            Chunk curChunk = chunkIterator.next();
            Integer lengthOfCurChunk = curChunk.endPos - curChunk.startPos;
            if(curChunk.actionApplied.equals("Insert")){
                numberOfInsertedLetters+= lengthOfCurChunk ;
            } else if(curChunk.actionApplied.equals("Del")){
                numberOfDeletedLetters+= lengthOfCurChunk;
            }
        }

    }

    private void summarizedEditsOfGroupedChunksIII(){

        Iterator<Chunk> chunkIterator = groupedChunksIII.iterator();
        while(chunkIterator.hasNext()){

            Chunk curChunk = chunkIterator.next();
            if(!curChunk.startPos.equals(curChunk.endPos)
                    && !curChunk.chunk.equals(" ")
                    && !Punctuations.punctuations.contains(curChunk.chunk)){
                if(curChunk.actionApplied.equals("Insert")){
                    arrayOfWordsWithoutEdits.add(curChunk.chunk);
                }else if(curChunk.actionApplied.contains("+")){
                    arrayOfWordsWithEdits.add(curChunk.chunk);
                }
            }

        }
    }


}
