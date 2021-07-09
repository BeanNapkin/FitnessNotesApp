package pro.fateeva.fitnessnotesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExerciseSetPicker extends RecyclerView.Adapter<ExerciseSetPicker.ExerciseSetPickerViewHolder> {

    private List<ExerciseSet> exerciseSetList;
    private final Fragment fragment;

    private ArrayList<ExerciseSet> checkedExercises = new ArrayList<>();


    public ExerciseSetPicker(List<ExerciseSet> exerciseSetList, Fragment fragment) {
        this.exerciseSetList = exerciseSetList;
        this.fragment = fragment;
    }

    public ArrayList<ExerciseSet> getCheckedExercises() {
        return checkedExercises;
    }

    public void setExerciseSetList(List<ExerciseSet> exerciseSetList) {
        this.exerciseSetList = exerciseSetList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExerciseSetPickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox_set_item, parent, false);
        return new ExerciseSetPickerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseSetPickerViewHolder holder, int position) {
        holder.bind(exerciseSetList.get(position));
    }

    @Override
    public int getItemCount() {
        if (exerciseSetList == null) {
            return 0;
        } else {
            return exerciseSetList.size();
        }
    }

    public class ExerciseSetPickerViewHolder extends RecyclerView.ViewHolder {

        private final EditText exercise;
        private final EditText repetitions;
        private final EditText weight;
        private final CheckBox checkBox;

        public ExerciseSetPickerViewHolder(@NonNull View itemView) {
            super(itemView);
            exercise = itemView.findViewById(R.id.EditTextExercise);
            repetitions = itemView.findViewById(R.id.EditTextRepetitions);
            weight = itemView.findViewById(R.id.EditTextWeight);
            checkBox = itemView.findViewById(R.id.checkBox);
        }

        void bind(final ExerciseSet exerciseSet) {
            exercise.setText(exerciseSet.getExercise());
            repetitions.setText(exerciseSet.getRepetitions());
            weight.setText(exerciseSet.getWeight());

            checkBox.setOnClickListener(v -> {
                clickOnCheckBox(exerciseSet);
            });

            checkBox.setChecked(checkedExercises.contains(exerciseSet));
        }
    }

    private void clickOnCheckBox(ExerciseSet exerciseSet) {
        if (checkedExercises.contains(exerciseSet)) {
            checkedExercises.remove(exerciseSet);
        } else {
            checkedExercises.add(exerciseSet);
        }
    }
}
