package pro.fateeva.fitnessnotesapp.services;

import pro.fateeva.fitnessnotesapp.AccountSource;

public class AccountSourceImpl implements AccountSource {

    private String accountId;

    @Override
    public String getAccountId() {
        return accountId;
    }

    @Override
    public void setAccountID(String accountId) {
        this.accountId = accountId;
    }
}
