package pro.fateeva.fitnessnotesapp;

import androidx.core.util.Consumer;

import java.io.Serializable;
import java.util.Date;

public interface DaySource extends Serializable {

    void downloadDayFromServer(Date date, Consumer<Day> onDownloaded);

    void addOrUpdateDay(Day day);

    void deleteDay(String date);

    Day getDay(String date);

}
