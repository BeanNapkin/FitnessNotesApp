package pro.fateeva.fitnessnotesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import java.util.Date;

import pro.fateeva.fitnessnotesapp.services.DayFirebaseSourceImpl;
import pro.fateeva.fitnessnotesapp.services.ServiceLocator;

public class MainActivity extends AppCompatActivity {

    public static final String FRAGMENT_NAME = "name";

    DayFragment todayFragment;

    private DaySource daySource = ServiceLocator.getDaySource();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        showFragment(FragmentNames.SIGN_IN_FRAGMENT);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        FragmentNames fragmentFromIntent = (FragmentNames) intent.getSerializableExtra(FRAGMENT_NAME);
        showFragment(fragmentFromIntent);
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private Fragment createDayFragment() {
        todayFragment = DayFragment.createFragment(new Date());
        return todayFragment;
    }

    private Fragment createAddNewExerciseFragment() {
        AddExerciseFragment addExerciseFragment = AddExerciseFragment.createFragment();
        return addExerciseFragment;
    }

    private Fragment createMostUsedExerciseFragment() {
        MostUsedExerciseFragment mostUsedExerciseFragment = MostUsedExerciseFragment.createFragment();
        return mostUsedExerciseFragment;
    }

    private void showFragment(FragmentNames fragmentName) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragmentToShow = null;

        switch (fragmentName) {
            case TODAY_FRAGMENT:
                fragmentToShow = createDayFragment();
                break;
            case MOST_USED_EXERCISES_FRAGMENT:
                fragmentToShow = createMostUsedExerciseFragment();
                break;
            case SIGN_IN_FRAGMENT:
                fragmentToShow = SignInFragment.createFragment();
                break;
        }

        fragmentTransaction.replace(R.id.main, fragmentToShow, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}