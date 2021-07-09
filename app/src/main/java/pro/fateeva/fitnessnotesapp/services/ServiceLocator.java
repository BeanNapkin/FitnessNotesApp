package pro.fateeva.fitnessnotesapp.services;

import pro.fateeva.fitnessnotesapp.AccountSource;
import pro.fateeva.fitnessnotesapp.DaySource;

public class ServiceLocator {

    private static DaySource daySource;
    private static AccountSource accountSource;

    public static DaySource getDaySource(){
        if (daySource == null){
            daySource = new DayFirebaseSourceImpl();
        }
        return daySource;
    }

    public static AccountSource getAccountSource(){
        if (accountSource == null){
            accountSource = new AccountSourceImpl();
        }
        return accountSource;
    }
}
