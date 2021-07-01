package pro.fateeva.fitnessnotesapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExerciseMapper {

    public static class Fields {
        public static final String EXERCISE = "exercise";
        public static final String REPETITIONS = "repetitions";
        public static final String WEIGHT = "weight";
    }

    public static ExerciseSet toExerciseSet(Map<String, Object> doc) {
        String exercise = (String) doc.get(Fields.EXERCISE);
        String repetitions = (String) doc.get(Fields.REPETITIONS);
        String weight = (String) doc.get(Fields.WEIGHT);

        ExerciseSet exerciseSet = new ExerciseSet(exercise, repetitions, weight);

        return exerciseSet;
    }

    public static Map<String, Object> toDocument(ExerciseSet exerciseSet) {
        Map<String, Object> document = new HashMap<>();

        document.put(Fields.EXERCISE, exerciseSet.getExercise());
        document.put(Fields.REPETITIONS, exerciseSet.getRepetitions());
        document.put(Fields.WEIGHT, exerciseSet.getWeight());

        return document;
    }
}
