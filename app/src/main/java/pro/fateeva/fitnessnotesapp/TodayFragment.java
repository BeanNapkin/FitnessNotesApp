package pro.fateeva.fitnessnotesapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
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
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodayFragment extends Fragment {

    public static final String TAG = "TodayFragment";
    public RecyclerView recyclerView;
    private MyAdapter myAdapter;

    private static final String ARG_EXERCISESETLIST = "param1";

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
}