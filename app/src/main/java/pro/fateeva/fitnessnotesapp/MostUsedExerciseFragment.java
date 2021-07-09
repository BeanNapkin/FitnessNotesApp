package pro.fateeva.fitnessnotesapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pro.fateeva.fitnessnotesapp.services.ServiceLocator;

public class MostUsedExerciseFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public RecyclerView recyclerView;
    public ExerciseSetPicker exerciseSetPicker;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MostUsedExerciseFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static MostUsedExerciseFragment createFragment() {
        MostUsedExerciseFragment fragment = new MostUsedExerciseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Наиболее популярные");

        recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(getView().getContext());
        recyclerView.setLayoutManager(manager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        ArrayList<ExerciseSet> exerciseSetArrayList = new ArrayList<>();
        ExerciseSet exerciseSet = new ExerciseSet();
        exerciseSet.setExercise("Анжуманя");
        exerciseSet.setWeight("100");
        exerciseSet.setRepetitions("1");
        exerciseSetArrayList.add(0, exerciseSet);

        exerciseSetPicker = new ExerciseSetPicker(exerciseSetArrayList, this);

        recyclerView.setAdapter(exerciseSetPicker);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_most_used_exercise, container, false);
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
            case R.id.action_save:
                clickOnSave();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clickOnSave() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra(MainActivity.FRAGMENT_NAME, FragmentNames.TODAY_FRAGMENT);

        ArrayList<ExerciseSet> checkedExercises = exerciseSetPicker.getCheckedExercises();

        for (int i = 0; i < checkedExercises.size(); i++) {
            ServiceLocator.getDaySource().getCurrentDay().addExerciseSet(checkedExercises.get(i).getExercise(),
                    checkedExercises.get(i).getRepetitions(),
                    checkedExercises.get(i).getWeight());
        }
        ServiceLocator.getDaySource().addOrUpdateDay(day -> requireContext().startActivity(intent));
    }
}