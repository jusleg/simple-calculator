package com.simplemobiletools.calculator.helpers;

import android.os.AsyncTask;

import com.simpletools.calculator.commons.helpers.Calculator;

public class BackgroundCurrencyTaskBuilder {
    private String from = "CAD";
    private String to = "USD";
    private Calculator moneyActivity;
    private MoneyCalculatorImpl moneyCalculator;

    public BackgroundCurrencyTaskBuilder(Calculator moneyActivity, MoneyCalculatorImpl moneyCalculator) {
        this.moneyActivity = moneyActivity;
        this.moneyCalculator = moneyCalculator;
    }

    public BackgroundCurrencyTaskBuilder to(String to) {
        this.to = to;
        return this;
    }

    public BackgroundCurrencyTaskBuilder from(String from) {
        this.from = from;
        return this;
    }

    public AsyncTask<Void, Void, String> build() {
        return new GetCurrencyTask(from, to, moneyActivity, moneyCalculator);
    }
}