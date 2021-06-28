package pro.fateeva.fitnessnotesapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

public class TodayFragment extends Fragment {

    public RecyclerView recyclerView;
    private MyAdapter myAdapter;

    private static final String ARG_EXERCISESETLIST = "param1";

    private static final String FRAGMENT_NAME = "name";
    private static final String addNewExerciseFragmentName = "add_new_exercise_fragment_name";

    public TodayFragment() {
        // Required empty public constructor
    }

    public static TodayFragment createFragment(Day day) {
        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EXERCISESETLIST, new SerializableExerciseSetList(day.getExerciseSetList()));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_today, container, false);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_today, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_addNewExercise:
                clickOnAddNewExercise();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        final SerializableExerciseSetList exerciseSetList = (SerializableExerciseSetList) getArguments().getSerializable(ARG_EXERCISESETLIST);

        recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(getView().getContext());
        recyclerView.setLayoutManager(manager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        myAdapter = new MyAdapter(exerciseSetList.getExerciseSetList(), this);

        recyclerView.setAdapter(myAdapter);
    }

    private void clickOnAddNewExercise() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra(FRAGMENT_NAME, addNewExerciseFragmentName);
        getContext().startActivity(intent);
    }

    public void changeExercises(Day day) {
        myAdapter.setExerciseSetList(day.getExerciseSetList());
        myAdapter.notifyDataSetChanged();
    }
}