package com.hfad.scorelist;

import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

public class MoneyHelper {
    int fMoney;
    int mMoney;
    int sMoney;
    int yMoney;
    int prize1;
    int prize2;
    int prize3;
    int prize4;
    Context mContext;

    public MoneyHelper(Context context) {
        mContext = context;
        fMoney = 0;
        mMoney = 0;
        sMoney = 0;
        yMoney = 0;
        prize1 = 5000;
        prize2 = 2000;
        prize3 = 3000;
        prize4 = 5000;

        ((EditText) ((Activity)mContext).findViewById(R.id.prize1)).setText(String.valueOf(prize1));
        ((EditText) ((Activity)mContext).findViewById(R.id.prize2)).setText(String.valueOf(prize2));
        ((EditText) ((Activity)mContext).findViewById(R.id.prize3)).setText(String.valueOf(prize3));
        ((EditText) ((Activity)mContext).findViewById(R.id.prize4)).setText(String.valueOf(prize4));
        ((EditText) ((Activity)mContext).findViewById(R.id.fTodayMoney)).setText("0");
        ((EditText) ((Activity)mContext).findViewById(R.id.mTodayMoney)).setText("0");
        ((EditText) ((Activity)mContext).findViewById(R.id.sTodayMoney)).setText("0");
        ((EditText) ((Activity)mContext).findViewById(R.id.yTodayMoney)).setText("0");
    }

    public void setBettingMoney() {
        EditText editText = ((EditText) ((Activity)mContext).findViewById(R.id.prize1));
        prize1 = Integer.parseInt(editText.getText().toString());

        editText = ((EditText) ((Activity)mContext).findViewById(R.id.prize2));
        prize2 = Integer.parseInt(editText.getText().toString());

        editText = ((EditText) ((Activity)mContext).findViewById(R.id.prize3));
        prize3 = Integer.parseInt(editText.getText().toString());

        editText = ((EditText) ((Activity)mContext).findViewById(R.id.prize4));
        prize4 = Integer.parseInt(editText.getText().toString());
    }

    public void updateMemberTodayMoney(int fr, int mr, int sr, int yr) {
        getTodayMoneyViewToData();
        updateFMoney(fr);
        updateMMoney(mr);
        updateSMoney(sr);
        updateYMoney(yr);
        showTodayMoney();
    }

    private void getTodayMoneyViewToData() {
        EditText editText = ((EditText) ((Activity)mContext).findViewById(R.id.fTodayMoney));
        fMoney = Integer.parseInt(editText.getText().toString());

        editText = ((EditText) ((Activity)mContext).findViewById(R.id.mTodayMoney));
        mMoney = Integer.parseInt(editText.getText().toString());

        editText = ((EditText) ((Activity)mContext).findViewById(R.id.sTodayMoney));
        sMoney = Integer.parseInt(editText.getText().toString());

        editText = ((EditText) ((Activity)mContext).findViewById(R.id.yTodayMoney));
        yMoney = Integer.parseInt(editText.getText().toString());
    }

    private void updateFMoney(int rank){
        if (rank == 1) {
            fMoney += prize1;
        } else if (rank == 2) {
            fMoney += prize2;
        } else if (rank == 3) {
            fMoney -= prize3;
        } else if (rank == 4) {
            fMoney -= prize4;
        }
    }

    private void updateMMoney(int rank){
        if (rank == 1) {
            mMoney += prize1;
        } else if (rank == 2) {
            mMoney += prize2;
        } else if (rank == 3) {
            mMoney -= prize3;
        } else if (rank == 4) {
            mMoney -= prize4;
        }
    }

    private void updateSMoney(int rank){
        if (rank == 1) {
            sMoney += prize1;
        } else if (rank == 2) {
            sMoney += prize2;
        } else if (rank == 3) {
            sMoney -= prize3;
        } else if (rank == 4) {
            sMoney -= prize4;
        }
    }

    private void updateYMoney(int rank){
        if (rank == 1) {
            yMoney += prize1;
        } else if (rank == 2) {
            yMoney += prize2;
        } else if (rank == 3) {
            yMoney -= prize3;
        } else if (rank == 4) {
            yMoney -= prize4;
        }
    }

    public void showTodayMoney() {
        ((EditText) ((Activity)mContext).findViewById(R.id.fTodayMoney)).setText(String.valueOf(fMoney));
        ((EditText) ((Activity)mContext).findViewById(R.id.mTodayMoney)).setText(String.valueOf(mMoney));
        ((EditText) ((Activity)mContext).findViewById(R.id.sTodayMoney)).setText(String.valueOf(sMoney));
        ((EditText) ((Activity)mContext).findViewById(R.id.yTodayMoney)).setText(String.valueOf(yMoney));
    }


}
