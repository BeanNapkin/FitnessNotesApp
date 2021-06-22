package pro.fateeva.fitnessnotesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Day day = new Day();

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
        createTodayFragment();
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void createTodayFragment() {
        TodayFragment todayFragment = TodayFragment.createFragment(day);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main, todayFragment, TodayFragment.TAG);
        fragmentTransaction.commit();
    }
}