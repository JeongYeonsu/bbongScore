package com.hfad.scorelist;

public class PlayerRecord {
    int firstCount;
    int secondCount;
    int thirdCount;
    int forthCount;
    int presidentCount;
    int total;
    double winRate;
    double topRate;

    public PlayerRecord(String c1, String c2, String c3, String c4, String pCount, int mTotal) {
        firstCount = Integer.valueOf(c1);
        secondCount = Integer.valueOf(c2);
        thirdCount = Integer.valueOf(c3);
        forthCount = Integer.valueOf(c4);
        presidentCount = Integer.valueOf(pCount);
        total = mTotal;
        calculatorRate();
    }

    public void updateRecord(int c1, int c2, int c3, int c4, int mTotal) {
        firstCount = c1;
        secondCount = c2;
        thirdCount = c3;
        forthCount = c4;
        total = mTotal;
        calculatorRate();
    }

    private void calculatorRate() {
        if (total == 0) {
            winRate = 0;
            topRate = 0;
        } else {
            winRate = (double)((firstCount + secondCount) * 1000 / total) / 10;
            topRate = (double)(firstCount * 1000 / total) / 10;
        }
    }

    public void updateTotalCount(int count) {
        total = count;
    }

    public void increaseFirstCount() {
        firstCount++;
        calculatorRate();
    }

    public void increaseSecondCount() {
        secondCount++;
        calculatorRate();
    }

    public void increaseThirdCount() {
        thirdCount++;
        calculatorRate();
    }

    public void increaseForthCount() {
        forthCount++;
        calculatorRate();
    }

    public void increasePresidentCount(int c) {
        presidentCount += c;
    }

    public String getFirstCount() {
        return Integer.toString(firstCount);
    }

    public String getSecondCount() {
        return Integer.toString(secondCount);
    }

    public String getThirdCount() {
        return Integer.toString(thirdCount);
    }

    public String getForthCount() {
        return Integer.toString(forthCount);
    }

    public String getPresidentCount() {
        return Integer.toString(presidentCount);
    }

    public String getWinRate() {
        return Double.toString(winRate);
    }

    public String getTopRate() {
        return Double.toString(topRate);
    }

    public void resetData() {
        firstCount = 0;
        secondCount = 0;
        thirdCount = 0;
        forthCount = 0;
        total = 0;
        winRate = 0;
        topRate = 0;
        presidentCount = 0;
    }
}
