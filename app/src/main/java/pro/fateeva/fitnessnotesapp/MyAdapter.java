package pro.fateeva.fitnessnotesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<ExerciseSet> exerciseSetList;
    private final Fragment fragment;


    public MyAdapter(List<ExerciseSet> exerciseSetList, Fragment fragment) {
        this.exerciseSetList = exerciseSetList;
        this.fragment = fragment;
    }

    public void setExerciseSetList(List<ExerciseSet> exerciseSetList) {
        this.exerciseSetList = exerciseSetList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final EditText exercise;
        private final EditText repetitions;
        private final EditText weight;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            exercise = itemView.findViewById(R.id.EditTextExercise);
            repetitions = itemView.findViewById(R.id.EditTextRepetitions);
            weight = itemView.findViewById(R.id.EditTextWeight);
        }

        void bind(final ExerciseSet exerciseSet) {
            exercise.setText(exerciseSet.getExercise());
            repetitions.setText(exerciseSet.getRepetitions());
            weight.setText(exerciseSet.getWeight());
        }
    }
}
