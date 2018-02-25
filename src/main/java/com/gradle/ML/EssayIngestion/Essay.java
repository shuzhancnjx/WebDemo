package com.gradle.ML.EssayIngestion;

import com.sun.istack.internal.Nullable;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class Essay {

    private final @NonNull long essayId;
    private final @NonNull int essaySet;
    private final @NonNull String essayBody;
    private final @Nullable int rater1Domain1;
    private final @Nullable int rater2Domain1;
    private final @Nullable int rater3Domain1;
    private final @Nullable int domain1Score;
    private final @Nullable int rater1Domain2;
    private final @Nullable int rater2Domain2;
    private final @Nullable int domain2Score;
    private final @Nullable int rater1Trait1;
    private final @Nullable int rater1Trait2;
    private final @Nullable int rater1Trait3;
    private final @Nullable int rater1Trait4;
    private final @Nullable int rater1Trait5;
    private final @Nullable int rater1Trait6;
    private final @Nullable int rater2Trait1;
    private final @Nullable int rater2Trait2;
    private final @Nullable int rater2Trait3;
    private final @Nullable int rater2Trait4;
    private final @Nullable int rater2Trait5;
    private final @Nullable int rater2Trait6;
    private final @Nullable int rater3Trait1;
    private final @Nullable int rater3Trait2;
    private final @Nullable int rater3Trait3;
    private final @Nullable int rater3Trait4;
    private final @Nullable int rater3Trait5;
    private final @Nullable int rater3Trait6;

}