package pro.fateeva.fitnessnotesapp;

import java.io.Serializable;

public class ExerciseSet implements Serializable {
    private String exercise;
    private String repetitions;
    private String weight;

    public ExerciseSet(String exercise, String repetitions, String weight) {
        this.exercise = exercise;
        this.repetitions = repetitions;
        this.weight = weight;
    }

    public ExerciseSet() {
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(String repetitions) {
        this.repetitions = repetitions;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
