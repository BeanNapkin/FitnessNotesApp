package pro.fateeva.fitnessnotesapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class AddExerciseFragment extends Fragment {

    public static final String ADD_NEW_EXERCISE = "new_exercise";
    private static final String FRAGMENT_NAME = "name";


    EditText exercise;
    EditText repetitions;
    EditText weight;

    public AddExerciseFragment() {
        // Required empty public constructor
    }

    public static AddExerciseFragment createFragment() {
        AddExerciseFragment fragment = new AddExerciseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_exercise, container, false);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onStart() {
        exercise = getView().findViewById(R.id.EditTextExercise);
        repetitions = getView().findViewById(R.id.EditTextRepetitions);
        weight = getView().findViewById(R.id.EditTextWeight);
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_saveNewExercise:
                clickOnSaveNewExercise();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clickOnSaveNewExercise(){
        ExerciseSet exerciseSet = new ExerciseSet();

        exerciseSet.setExercise((exercise).getText().toString());
        exerciseSet.setRepetitions((repetitions).getText().toString());
        exerciseSet.setWeight((weight).getText().toString());

        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra(FRAGMENT_NAME, FragmentNames.TODAY_FRAGMENT);
        intent.putExtra(ADD_NEW_EXERCISE, exerciseSet);
        requireContext().startActivity(intent);
    };
}