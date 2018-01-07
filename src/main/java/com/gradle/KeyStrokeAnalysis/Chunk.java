package com.gradle.KeyStrokeAnalysis;


import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

@Builder
@ToString
public class Chunk implements Cloneable {

    public @NonNull String chunk;
    public @NonNull Integer startPos;
    public @NonNull Integer endPos; // not included;
    public @NonNull String actionApplied;
    public @NonNull String startTime;
    public @NonNull String endTime;

    @Override
    public Chunk clone() {
        return Chunk.builder()
                .chunk(this.chunk)
                .startPos(this.startPos)
                .endPos(this.endPos)
                .actionApplied(this.actionApplied)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .build();
    }
}

