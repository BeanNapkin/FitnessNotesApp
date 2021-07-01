package pro.fateeva.fitnessnotesapp;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Day {

    private Date date = new Date();
    private String id;
    private List<ExerciseSet> exerciseSetList = new ArrayList<>();

    public Date getDate() {
        return date;
    }

    public void addExerciseSet(String exercise, String repetitions, String weight){
        ExerciseSet exerciseSet = new ExerciseSet(exercise, repetitions, weight);
        exerciseSetList.add(exerciseSet);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ExerciseSet> getExerciseSetList() {
        return exerciseSetList;
    }

    public void setExerciseSetList(List<ExerciseSet> exerciseSetList) {
        this.exerciseSetList = exerciseSetList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return "Day " + date.toString();
    }
}
