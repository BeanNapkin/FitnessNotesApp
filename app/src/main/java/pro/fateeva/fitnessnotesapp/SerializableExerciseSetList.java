package pro.fateeva.fitnessnotesapp;

import java.io.Serializable;
import java.util.List;

public class SerializableExerciseSetList implements Serializable {

    private final List<ExerciseSet> exerciseSetList;

    public SerializableExerciseSetList(List<ExerciseSet> exerciseSetList) {
        this.exerciseSetList = exerciseSetList;
    }

    public List<ExerciseSet> getExerciseSetList() {
        return exerciseSetList;
    }
}
