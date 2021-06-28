package pro.fateeva.fitnessnotesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Day day = new Day();
    private static String fragmentFromIntent = "";

    private static final String FRAGMENT_NAME = "name";
    public static final String ADD_NEW_EXERCISE = "new_exercise";
    private static final String addNewExerciseFragmentName = "add_new_exercise_fragment_name";

    TodayFragment todayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        day.setDate(new Date());
        day.addExerciseSet("Приседания", "10", "40");
        day.addExerciseSet("Отжимания", "10", "40");

        initToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showFragment(fragmentFromIntent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        ExerciseSet exerciseSetToAdd = (ExerciseSet) intent.getSerializableExtra(ADD_NEW_EXERCISE);
        if (exerciseSetToAdd != null) {
            addExerciseSetAndUpdateDay(exerciseSetToAdd);
        }

        fragmentFromIntent = (String) intent.getSerializableExtra(FRAGMENT_NAME);
        showFragment(fragmentFromIntent);
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private Fragment createTodayFragment() {
        todayFragment = TodayFragment.createFragment(day);
        return todayFragment;
    }

    private Fragment createAddNewExerciseFragment() {
        AddExerciseFragment addExerciseFragment = AddExerciseFragment.createFragment();
        return addExerciseFragment;
    }

    private void showFragment(String fragmentName) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragmentToShow = new Fragment();

        if (fragmentName.equals("")) {
            fragmentToShow = createTodayFragment();
        } else if (fragmentName.equals(addNewExerciseFragmentName)) {
            fragmentToShow = createAddNewExerciseFragment();
        }

        fragmentTransaction.replace(R.id.main, fragmentToShow, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void addExerciseSetAndUpdateDay(ExerciseSet exerciseSetToAdd) {
        day.addExerciseSet(exerciseSetToAdd.getExercise(), exerciseSetToAdd.getRepetitions(), exerciseSetToAdd.getWeight());
        todayFragment.changeExercises(day);
    }
}