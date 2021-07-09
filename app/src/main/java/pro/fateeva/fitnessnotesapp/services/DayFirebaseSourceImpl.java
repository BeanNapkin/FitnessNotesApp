package pro.fateeva.fitnessnotesapp.services;

import android.util.Log;

import androidx.core.util.Consumer;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Date;
import java.util.Iterator;

import pro.fateeva.fitnessnotesapp.Day;
import pro.fateeva.fitnessnotesapp.DayDataMapping;
import pro.fateeva.fitnessnotesapp.DaySource;

public class DayFirebaseSourceImpl implements DaySource {

    public static final String DAY = "day";
    public static final String TAG = "[DaySourceFirebaseImpl]";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collection = db.collection(DAY);

    private Day day = new Day();

    DayFirebaseSourceImpl() {
    }

    @Override
    public void downloadDayFromServer(Date date, String accountId, Consumer<Day> onDownloaded) {
        collection
                .whereEqualTo(DayDataMapping.Fields.ACCOUNT_ID, accountId)
                .whereEqualTo(DayDataMapping.Fields.DATE, DayDataMapping.dateToString(date))
                .get()
                .addOnCompleteListener((task) -> {
                    if (task.isSuccessful()) {
                        day.getExerciseSetList().clear();

                        Iterator<QueryDocumentSnapshot> iterator = task.getResult().iterator();

                        if (iterator.hasNext()) {
                            QueryDocumentSnapshot doc = iterator.next();
                            doc.getId();
                            day = DayDataMapping.toDay(doc);
                        } else {
                            day = new Day();
                            day.setAccountId(accountId);
                        }

                        onDownloaded.accept(day);
                    } else {
                        Log.e(TAG, "onFailure", task.getException());
                    }
                });
    }

    @Override
    public void addOrUpdateDay(Consumer<Day> onSaved) {

        if (day.getAccountId() == null || "".equals(day.getAccountId())) {
            throw new IllegalArgumentException("Account ID is required");
        }

        if (day.getId() == null || "".equals(day.getId())) {
            collection.add(DayDataMapping.toDocument(day)).addOnSuccessListener(documentReference -> day.setId(documentReference.getId()))
                    .addOnFailureListener(e -> Log.e(null, "error saving " + day, e)).addOnCompleteListener(task -> onSaved.accept(day));
        } else {
            collection.document(day.getId()).set(DayDataMapping.toDocument(day))
                    .addOnFailureListener(e -> Log.e(null, "error updating " + day, e)).addOnCompleteListener(task -> onSaved.accept(day));
        }
    }

    @Override
    public Day getCurrentDay() {
        return day;
    }

}
