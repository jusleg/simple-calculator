package com.simplemobiletools.calculator.helpers;

import android.os.AsyncTask;

import com.simplemobiletools.calculator.activities.CryptoActivity;
import com.simpletools.calculator.commons.helpers.Calculator;

public class BackgroundCryptoTaskBuilder {
    private String from = "BTC";
    private String to = "ETH";
    private CryptoActivity cryptoActivty;
    private CryptoCalculatorImpl cryptoCalculator;

    public BackgroundCryptoTaskBuilder(CryptoActivity cryptoActivty, CryptoCalculatorImpl cryptoCalculator) {
        this.cryptoActivty = cryptoActivty;
        this.cryptoCalculator = cryptoCalculator;
    }

    public BackgroundCryptoTaskBuilder to(String to) {
        this.to = to;
        return this;
    }

    public BackgroundCryptoTaskBuilder from(String from) {
        this.from = from;
        return this;
    }

    public AsyncTask<Void, Void, String> build() {
        return new GetCryptoTask(from, to, cryptoActivty, cryptoCalculator);
    }
}