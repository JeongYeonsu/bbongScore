package com.hfad.scorelist;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

public class RecordHelper {
    Context mContext;

    SharedPreferences preferences;

    EditText total_record_view;
    EditText house_money_view;
    EditText house_betting_money_view;

    EditText father_1_view;
    EditText father_2_view;
    EditText father_3_view;
    EditText father_4_view;
    EditText father_top_rate_view;
    EditText father_win_rate_view;

    EditText mother_1_view;
    EditText mother_2_view;
    EditText mother_3_view;
    EditText mother_4_view;
    EditText mother_top_rate_view;
    EditText mother_win_rate_view;

    EditText sun_1_view;
    EditText sun_2_view;
    EditText sun_3_view;
    EditText sun_4_view;
    EditText sun_top_rate_view;
    EditText sun_win_rate_view;

    EditText ys_1_view;
    EditText ys_2_view;
    EditText ys_3_view;
    EditText ys_4_view;
    EditText ys_top_rate_view;
    EditText ys_win_rate_view;

    int totalGame;

    int houseMoney;

    int houseBettingMoney;

    PlayerRecord fatherRecord;

    PlayerRecord motherRecord;

    PlayerRecord sunRecord;

    PlayerRecord ysRecord;

    public RecordHelper(Context context) {
        mContext = context;
        preferences = context.getSharedPreferences("bb_record", Context.MODE_PRIVATE);

        total_record_view = (EditText) ((Activity)mContext).findViewById(R.id.total_record);
        house_money_view = (EditText) ((Activity)mContext).findViewById(R.id.house_money);
        house_betting_money_view = (EditText) ((Activity)mContext).findViewById(R.id.house_money_betting);

        father_1_view = (EditText) ((Activity)mContext).findViewById(R.id.rank_1_player_0);
        father_2_view = (EditText) ((Activity)mContext).findViewById(R.id.rank_2_player_0);
        father_3_view = (EditText) ((Activity)mContext).findViewById(R.id.rank_3_player_0);
        father_4_view = (EditText) ((Activity)mContext).findViewById(R.id.rank_4_player_0);
        father_top_rate_view = (EditText) ((Activity)mContext).findViewById(R.id.top_rate_player_0);
        father_win_rate_view = (EditText) ((Activity)mContext).findViewById(R.id.win_rate_player_0);

        mother_1_view = (EditText) ((Activity)mContext).findViewById(R.id.rank_1_player_1);
        mother_2_view = (EditText) ((Activity)mContext).findViewById(R.id.rank_2_player_1);
        mother_3_view = (EditText) ((Activity)mContext).findViewById(R.id.rank_3_player_1);
        mother_4_view = (EditText) ((Activity)mContext).findViewById(R.id.rank_4_player_1);
        mother_top_rate_view = (EditText) ((Activity)mContext).findViewById(R.id.top_rate_player_1);
        mother_win_rate_view = (EditText) ((Activity)mContext).findViewById(R.id.win_rate_player_1);

        sun_1_view = (EditText) ((Activity)mContext).findViewById(R.id.rank_1_player_2);
        sun_2_view = (EditText) ((Activity)mContext).findViewById(R.id.rank_2_player_2);
        sun_3_view = (EditText) ((Activity)mContext).findViewById(R.id.rank_3_player_2);
        sun_4_view = (EditText) ((Activity)mContext).findViewById(R.id.rank_4_player_2);
        sun_top_rate_view = (EditText) ((Activity)mContext).findViewById(R.id.top_rate_player_2);
        sun_win_rate_view = (EditText) ((Activity)mContext).findViewById(R.id.win_rate_player_2);

        ys_1_view = (EditText) ((Activity)mContext).findViewById(R.id.rank_1_player_3);
        ys_2_view = (EditText) ((Activity)mContext).findViewById(R.id.rank_2_player_3);
        ys_3_view = (EditText) ((Activity)mContext).findViewById(R.id.rank_3_player_3);
        ys_4_view = (EditText) ((Activity)mContext).findViewById(R.id.rank_4_player_3);
        ys_top_rate_view = (EditText) ((Activity)mContext).findViewById(R.id.top_rate_player_3);
        ys_win_rate_view = (EditText) ((Activity)mContext).findViewById(R.id.win_rate_player_3);

    }

    public void init() {
        getAllPreference();
        showRecordData();

    }

    private void getAllPreference() {
        totalGame = Integer.parseInt(preferences.getString("total_game", "0"));
        houseMoney = Integer.parseInt(preferences.getString("house_total_money", "0"));
        houseBettingMoney = Integer.parseInt(preferences.getString("house_betting_money", "0"));

        fatherRecord = new PlayerRecord(preferences.getString("father1", "0"),
                preferences.getString("father2", "0"),
                preferences.getString("father3", "0"),
                preferences.getString("father4", "0"),
                preferences.getString("fatherPresident", "0"),
                totalGame);

        motherRecord = new PlayerRecord(preferences.getString("mother1", "0"),
                preferences.getString("mother2", "0"),
                preferences.getString("mother3", "0"),
                preferences.getString("mother4", "0"),
                preferences.getString("motherPresident", "0"),
                totalGame);

        sunRecord = new PlayerRecord(preferences.getString("sun1", "0"),
                preferences.getString("sun2", "0"),
                preferences.getString("sun3", "0"),
                preferences.getString("sun4", "0"),
                preferences.getString("sunPresident", "0"),
                totalGame);

        ysRecord = new PlayerRecord(preferences.getString("ys1", "0"),
                preferences.getString("ys2", "0"),
                preferences.getString("ys3", "0"),
                preferences.getString("ys4", "0"),
                preferences.getString("ysPresident", "0"),
                totalGame);
    }

    public void showRecordData() {
        total_record_view.setText(String.valueOf(totalGame));
        house_money_view.setText(String.valueOf(houseMoney));

        StringBuilder builder = new StringBuilder();

        father_1_view.setText(fatherRecord.getFirstCount());
        father_2_view.setText(fatherRecord.getSecondCount());
        father_3_view.setText(fatherRecord.getThirdCount());
        father_4_view.setText(fatherRecord.getForthCount());
        builder.append(fatherRecord.getWinRate());
        builder.append("%  (");
        builder.append(fatherRecord.getTopRate());
        builder.append("%) , ");
        builder.append(fatherRecord.getPresidentCount());
        builder.append(" 대통령");
        father_win_rate_view.setText(builder.toString());
//        father_top_rate_view.setText(fatherRecord.getTopRate());
//        father_win_rate_view.setText(fatherRecord.getWinRate());

        mother_1_view.setText(motherRecord.getFirstCount());
        mother_2_view.setText(motherRecord.getSecondCount());
        mother_3_view.setText(motherRecord.getThirdCount());
        mother_4_view.setText(motherRecord.getForthCount());
        //mother_top_rate_view.setText(motherRecord.getTopRate());
        //mother_win_rate_view.setText(motherRecord.getWinRate());
        builder.setLength(0);
        builder.append(motherRecord.getWinRate());
        builder.append("%  (");
        builder.append(motherRecord.getTopRate());
        builder.append("%), ");
        builder.append(motherRecord.getPresidentCount());
        builder.append(" 대통령");
        mother_win_rate_view.setText(builder.toString());

        sun_1_view.setText(sunRecord.getFirstCount());
        sun_2_view.setText(sunRecord.getSecondCount());
        sun_3_view.setText(sunRecord.getThirdCount());
        sun_4_view.setText(sunRecord.getForthCount());
        //sun_top_rate_view.setText(sunRecord.getTopRate());
        //sun_win_rate_view.setText(sunRecord.getWinRate());
        builder.setLength(0);
        builder.append(sunRecord.getWinRate());
        builder.append("%  (");
        builder.append(sunRecord.getTopRate());
        builder.append("%), ");
        builder.append(sunRecord.getPresidentCount());
        builder.append(" 대통령");
        sun_win_rate_view.setText(builder.toString());

        ys_1_view.setText(ysRecord.getFirstCount());
        ys_2_view.setText(ysRecord.getSecondCount());
        ys_3_view.setText(ysRecord.getThirdCount());
        ys_4_view.setText(ysRecord.getForthCount());
        //ys_top_rate_view.setText(ysRecord.getTopRate());
       //ys_win_rate_view.setText(ysRecord.getWinRate());
        builder.setLength(0);
        builder.append(ysRecord.getWinRate());
        builder.append("%  (");
        builder.append(ysRecord.getTopRate());
        builder.append("%), ");
        builder.append(ysRecord.getPresidentCount());
        builder.append(" 대통령");
        ys_win_rate_view.setText(builder.toString());

    }

    public void getCurrentViewDataToRecordData() {
        totalGame = Integer.parseInt(total_record_view.getText().toString());
        houseMoney = Integer.parseInt(house_money_view.getText().toString());
        houseBettingMoney = Integer.parseInt(house_betting_money_view.getText().toString());

        fatherRecord.updateRecord(
                Integer.parseInt(father_1_view.getText().toString()),
                Integer.parseInt(father_2_view.getText().toString()),
                Integer.parseInt(father_3_view.getText().toString()),
                Integer.parseInt(father_4_view.getText().toString()), totalGame );

        motherRecord.updateRecord(
                Integer.parseInt(mother_1_view.getText().toString()),
                Integer.parseInt(mother_2_view.getText().toString()),
                Integer.parseInt(mother_3_view.getText().toString()),
                Integer.parseInt(mother_4_view.getText().toString()), totalGame );

        sunRecord.updateRecord(
                Integer.parseInt(sun_1_view.getText().toString()),
                Integer.parseInt(sun_2_view.getText().toString()),
                Integer.parseInt(sun_3_view.getText().toString()),
                Integer.parseInt(sun_4_view.getText().toString()), totalGame );

        ysRecord.updateRecord(
                Integer.parseInt(ys_1_view.getText().toString()),
                Integer.parseInt(ys_2_view.getText().toString()),
                Integer.parseInt(ys_3_view.getText().toString()),
                Integer.parseInt(ys_4_view.getText().toString()), totalGame );
    }

    public void addHouseMoney() {
        houseMoney += houseBettingMoney;
    }

    public void addRecord(int fRank, int mRank, int sRank, int yRank) {
        totalGame++;
        updateTotalRecord();
        updateFatherRecord(fRank);
        updateMotherRecord(mRank);
        updateSunRecord(sRank);
        updateYsRecord(yRank);

        showRecordData();
        savePreference();
    }

    public void addPresidentRecord(int fCnt, int mCnt, int sCnt, int yCnt) {
        fatherRecord.increasePresidentCount(fCnt);
        motherRecord.increasePresidentCount(mCnt);
        sunRecord.increasePresidentCount(sCnt);
        ysRecord.increasePresidentCount(yCnt);
    }

    private void updateTotalRecord() {
        fatherRecord.updateTotalCount(totalGame);
        motherRecord.updateTotalCount(totalGame);
        sunRecord.updateTotalCount(totalGame);
        ysRecord.updateTotalCount(totalGame);
    }

    private void updateFatherRecord(int fRank) {
        if (fRank == 1) {
            fatherRecord.increaseFirstCount();
        } else if (fRank == 2){
            fatherRecord.increaseSecondCount();
        } else if (fRank == 3) {
            fatherRecord.increaseThirdCount();
        } else {
            fatherRecord.increaseForthCount();
        }
    }

    private void updateMotherRecord(int mRank) {
        if (mRank == 1) {
            motherRecord.increaseFirstCount();
        } else if (mRank == 2){
            motherRecord.increaseSecondCount();
        } else if (mRank == 3) {
            motherRecord.increaseThirdCount();
        } else {
            motherRecord.increaseForthCount();
        }
    }

    private void updateSunRecord(int sRank) {
        if (sRank == 1) {
            sunRecord.increaseFirstCount();
        } else if (sRank == 2){
            sunRecord.increaseSecondCount();
        } else if (sRank == 3) {
            sunRecord.increaseThirdCount();
        } else {
            sunRecord.increaseForthCount();
        }
    }

    private void updateYsRecord(int yRank) {
        if (yRank == 1) {
            ysRecord.increaseFirstCount();
        } else if (yRank == 2){
            ysRecord.increaseSecondCount();
        } else if (yRank == 3) {
            ysRecord.increaseThirdCount();
        } else {
            ysRecord.increaseForthCount();
        }
    }

    public void savePreference() {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("total_game", total_record_view.getText().toString());

        editor.putString("father1", father_1_view.getText().toString());
        editor.putString("father2", father_2_view.getText().toString());
        editor.putString("father3", father_3_view.getText().toString());
        editor.putString("father4", father_4_view.getText().toString());
        editor.putString("fatherPresident", fatherRecord.getPresidentCount());

        editor.putString("mother1", mother_1_view.getText().toString());
        editor.putString("mother2", mother_2_view.getText().toString());
        editor.putString("mother3", mother_3_view.getText().toString());
        editor.putString("mother4", mother_4_view.getText().toString());
        editor.putString("motherPresident", motherRecord.getPresidentCount());

        editor.putString("sun1", sun_1_view.getText().toString());
        editor.putString("sun2", sun_2_view.getText().toString());
        editor.putString("sun3", sun_3_view.getText().toString());
        editor.putString("sun4", sun_4_view.getText().toString());
        editor.putString("sunPresident", sunRecord.getPresidentCount());

        editor.putString("ys1", ys_1_view.getText().toString());
        editor.putString("ys2", ys_2_view.getText().toString());
        editor.putString("ys3", ys_3_view.getText().toString());
        editor.putString("ys4", ys_4_view.getText().toString());
        editor.putString("ysPresident", ysRecord.getPresidentCount());

        editor.putString("house_total_money", house_money_view.getText().toString());
        editor.putString("house_betting_money", house_betting_money_view.getText().toString());

        editor.commit();
    }

    public void resetRecordData() {
        total_record_view.setText("0");

        father_1_view.setText("0");
        father_2_view.setText("0");
        father_3_view.setText("0");
        father_4_view.setText("0");
        father_win_rate_view.setText("");

        mother_1_view.setText("0");
        mother_2_view.setText("0");
        mother_3_view.setText("0");
        mother_4_view.setText("0");
        mother_win_rate_view.setText("");

        sun_1_view.setText("0");
        sun_2_view.setText("0");
        sun_3_view.setText("0");
        sun_4_view.setText("0");
        sun_win_rate_view.setText("");

        ys_1_view.setText("0");
        ys_2_view.setText("0");
        ys_3_view.setText("0");
        ys_4_view.setText("0");
        ys_win_rate_view.setText("");

        totalGame = 0;

        fatherRecord.resetData();
        motherRecord.resetData();
        sunRecord.resetData();
        ysRecord.resetData();
    }

}
