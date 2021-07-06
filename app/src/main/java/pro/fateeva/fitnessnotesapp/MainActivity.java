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

    public static final String FRAGMENT_NAME = "name";
    public static final String ADD_NEW_EXERCISE = "new_exercise";
    private static final String TODAY_FRAGMENT = "today_fragment";
    private static final String addNewExerciseFragmentName = "add_new_exercise_fragment_name";

    DayFragment todayFragment;

    private DaySource daySource = new DayFirebaseSourceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showFragment(FragmentNames.SIGN_IN_FRAGMENT, null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        ExerciseSet exerciseSetToAdd = (ExerciseSet) intent.getSerializableExtra(ADD_NEW_EXERCISE);

        if (exerciseSetToAdd != null) {
            addExerciseSetAndUpdateDay(exerciseSetToAdd);
        }

        FragmentNames fragmentFromIntent = (FragmentNames) intent.getSerializableExtra(FRAGMENT_NAME);
        showFragment(fragmentFromIntent, intent);
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private Fragment createDayFragment(String accountId) {
        todayFragment = DayFragment.createFragment(new Date(), accountId);
        return todayFragment;
    }

    private Fragment createAddNewExerciseFragment() {
        AddExerciseFragment addExerciseFragment = AddExerciseFragment.createFragment();
        return addExerciseFragment;
    }

    private void showFragment(FragmentNames fragmentName, Intent intent) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragmentToShow = null;

        switch (fragmentName){
            case TODAY_FRAGMENT:
                String accountId = intent.getStringExtra(SignInFragment.ACCOUNT_ID);
                fragmentToShow = createDayFragment(accountId);
                break;
            case ADD_NEW_EXERCISE_FRAGMENT:
                fragmentToShow = createAddNewExerciseFragment();
                break;
            case SIGN_IN_FRAGMENT:
                fragmentToShow = SignInFragment.createFragment();
                break;
        }

        fragmentTransaction.replace(R.id.main, fragmentToShow, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void addExerciseSetAndUpdateDay(ExerciseSet exerciseSetToAdd) {
        day.addExerciseSet(exerciseSetToAdd.getExercise(), exerciseSetToAdd.getRepetitions(), exerciseSetToAdd.getWeight());
        daySource.addOrUpdateDay(day);
        todayFragment.changeExercises(day);
    }
}