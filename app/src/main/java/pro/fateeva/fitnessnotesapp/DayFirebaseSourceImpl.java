package pro.fateeva.fitnessnotesapp;

import android.util.Log;

import androidx.core.util.Consumer;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Date;
import java.util.Iterator;

public class DayFirebaseSourceImpl implements DaySource {

    public static final String DAY = "day";
    public static final String TAG = "[DaySourceFirebaseImpl]";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collection = db.collection(DAY);

    final private Day day = new Day();

    @Override
    public void downloadDayFromServer(Date date, Consumer<Day> onDownloaded) {
        collection
                .whereEqualTo(DayDataMapping.Fields.DATE, DayDataMapping.dateToString(date))
                .get()
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        day.getExerciseSetList().clear();

                        Day day;

                        Iterator<QueryDocumentSnapshot> iterator = task.getResult().iterator();

                        if (iterator.hasNext()) {
                            day = DayDataMapping.toDay(iterator.next().getData());
                        } else {
                            day = new Day();
                        }

                        onDownloaded.accept(day);
                    } else {
                        Log.e(TAG, "onFailure", task.getException());
                    }
                });
    }

    @Override
    public void addOrUpdateDay(Day day) {

        if (day.getId() == null || "".equals(day.getId())) {
            collection.add(DayDataMapping.toDocument(day)).addOnSuccessListener(documentReference -> day.setId(documentReference.getId()))
                    .addOnFailureListener(e -> Log.e(null, "error saving " + day, e));
        } else {
            collection.document(day.getId()).set(DayDataMapping.toDocument(day))
                    .addOnFailureListener(e -> Log.e(null, "error updating " + day, e));
        }
    }

    @Override
    public void deleteDay(String date) {

    }

    @Override
    public Day getDay(String date) {
        return null;
    }

}
