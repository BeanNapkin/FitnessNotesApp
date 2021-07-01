package pro.fateeva.fitnessnotesapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayDataMapping {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static class Fields {
        public static final String DATE = "date";
        public static final String ID = "id";
        public static final String EXERCISE_SET_LIST = "exercises";
    }

    public static String dateToString(Date date){
        return dateFormat.format(date);
    }

    public static Day toDay(Map<String, Object> doc) {

        List<ExerciseSet> exerciseSetList = new ArrayList<>();

        List<Map<String, Object>> exercises = (List<Map<String, Object>>) doc.get(Fields.EXERCISE_SET_LIST);

        for (Map<String, Object> exercise : exercises) {
            exerciseSetList.add(ExerciseMapper.toExerciseSet(exercise));
        }

        Day day = new Day();

        String dateString = (String) doc.get(Fields.DATE);
        try {
            day.setDate(dateFormat.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        day.setId((String) doc.get(Fields.ID));
        day.setExerciseSetList(exerciseSetList);

        return day;
    }

    public static Map<String, Object> toDocument(Day day) {
        Map<String, Object> document = new HashMap<>();

        document.put(Fields.DATE, dateFormat.format(day.getDate()));

        ArrayList<Map<String, Object>> mappedExercises = new ArrayList<>();

        for (ExerciseSet exerciseSet : day.getExerciseSetList()) {
            mappedExercises.add(ExerciseMapper.toDocument(exerciseSet));
        }

        document.put(Fields.EXERCISE_SET_LIST, mappedExercises);

        return document;
    }
}
