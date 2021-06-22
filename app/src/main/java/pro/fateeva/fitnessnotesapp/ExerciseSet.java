package pro.fateeva.fitnessnotesapp;

public class ExerciseSet {
    private String exercise;
    private String repetitions;
    private String weight;

    public ExerciseSet(String exercise, String repetitions, String weight) {
        this.exercise = exercise;
        this.repetitions = repetitions;
        this.weight = weight;
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
