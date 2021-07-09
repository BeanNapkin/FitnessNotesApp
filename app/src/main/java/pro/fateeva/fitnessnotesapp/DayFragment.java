package pro.fateeva.fitnessnotesapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pro.fateeva.fitnessnotesapp.services.DayFirebaseSourceImpl;
import pro.fateeva.fitnessnotesapp.services.ServiceLocator;

public class DayFragment extends Fragment {

    private static final String ARG_DATE = "date";

    public RecyclerView recyclerView;
    private MyAdapter myAdapter;

    private boolean isProgressBarShown;

    private DaySource dayFirebaseSource = ServiceLocator.getDaySource();

    private Calendar date = Calendar.getInstance();

    private String chosenDayFromCalendar;

    private String accountId;

    public DayFragment() {
        // Required empty public constructor
    }

    public static DayFragment createFragment(Date date) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

            case R.id.action_chooseDay:
                clickOnChooseDay();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Сегодня");

        final Date date = (Date) getArguments().getSerializable(ARG_DATE);
        accountId = ServiceLocator.getAccountSource().getAccountId();

        recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(getView().getContext());
        recyclerView.setLayoutManager(manager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        myAdapter = new MyAdapter(new ArrayList<>(), this);

        showDay(date, accountId);

        recyclerView.setAdapter(myAdapter);
    }

    private void showDay(Date date, String accountId) {
        showProgressBar(true);
        dayFirebaseSource.downloadDayFromServer(date, accountId, day -> {
            changeExercises(day);
            showProgressBar(false);
        });
    }

    private void clickOnAddNewExercise() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.putExtra(MainActivity.FRAGMENT_NAME, FragmentNames.MOST_USED_EXERCISES_FRAGMENT);
        requireContext().startActivity(intent);
    }

    private void clickOnChooseDay() {
        new DatePickerDialog(requireContext(), d,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate();
        }
    };

    private void setInitialDate() {
        chosenDayFromCalendar = DateUtils.formatDateTime(getActivity(),
                date.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(chosenDayFromCalendar);

        showDay(date.getTime(), accountId);
    }

    public void changeExercises(Day day) {
        myAdapter.setExerciseSetList(day.getExerciseSetList());
    }

    public void showProgressBar(boolean isShowing) {
        isProgressBarShown = isShowing;

        if (getView() != null) {
            updateProgressBar();
        }
    }

    private void updateProgressBar() {
        ProgressBar progressBar = getView().findViewById(R.id.progressBar);

        if (isProgressBarShown) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}