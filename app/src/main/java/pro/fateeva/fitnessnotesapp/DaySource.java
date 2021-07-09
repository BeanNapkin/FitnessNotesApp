package pro.fateeva.fitnessnotesapp;

import androidx.core.util.Consumer;

import java.io.Serializable;
import java.util.Date;

public interface DaySource extends Serializable {

    void downloadDayFromServer(Date date, String accountId, Consumer<Day> onDownloaded);

    void addOrUpdateDay(Consumer<Day> onSaved);

    Day getCurrentDay();
}
