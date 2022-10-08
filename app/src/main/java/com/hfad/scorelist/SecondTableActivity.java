package com.hfad.scorelist;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SecondTableActivity extends AppCompatActivity {

    String emptyToken = "      ";
    String stringToken = "   /   ";
    String lineChangeToken = "\n";

    TextView day_text_view;
    TextView empty_text_view;
    TextView round_text_view;
    Button minus_make_button;
    Button total_score_button;
    Button round_1_button;
    Button round_2_button;
    Button round_3_button;
    Button round_4_button;
    Button round_5_button;
    Button round_6_button;
    Button round_7_button;
    Button round_8_button;
    Button round_9_button;
    Button round_10_button;

    Button add_line_history_button;
    Button add_history_button;
    Button people_record_update_button;
    Button reset_score_button;
    Button reset_record_button;
    Button betting_button;

    ArrayList<Boolean> isLineCalculated = new ArrayList<>(10);

    InputMethodManager imm;

    EditText mHistoryText;

    RecordHelper mRecordHelper;

    MoneyHelper mMoneyHelper;

    ArrayList<Integer> finalRank = new ArrayList<>();

    ArrayList<Integer> presidentCount = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_table_layout);
        getSupportActionBar().hide();

        mRecordHelper = new RecordHelper(this);

        mMoneyHelper = new MoneyHelper(this);

        getPermission();

        getView();

        getUpdateTime();

        loadHistory();

        mRecordHelper.init();

        etcInit();
    }

    private void etcInit() {
        setRoundViewText(0);
    }

    private void getPermission() {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
    }

    private void getView() {
        mHistoryText = findViewById(R.id.history);
        mHistoryText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (view.getId() == R.id.history) {
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            view.getParent().requestDisallowInterceptTouchEvent(true);
                            break;
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });


        betting_button = findViewById(R.id.betting);
        betting_button.setOnClickListener(view -> {
            mMoneyHelper.setBettingMoney();
        });

        reset_record_button = findViewById(R.id.record_reset_button);
        reset_record_button.setOnClickListener(view -> {
            resetRecordDialog();
        });

        people_record_update_button = findViewById(R.id.record_update_button);
        people_record_update_button.setOnClickListener(view -> {
            mRecordHelper.getCurrentViewDataToRecordData();
            //rate 계산 결과 업데이트 위해
            mRecordHelper.showRecordData();
            mRecordHelper.savePreference();
        });

        add_history_button = findViewById(R.id.add_history);
        add_history_button.setOnClickListener(view -> {
            if (isGameEnd()) {
                showDialogAddHistory();
            }
        });

        add_line_history_button = findViewById(R.id.add_line_to_history_button);
        add_line_history_button.setOnClickListener(view -> {
            getUpdateTime();
            addLineAndWriteHistoryFileToDevice();
            mRecordHelper.savePreference();
        });

        reset_score_button = findViewById(R.id.reset_button);
        reset_score_button.setOnClickListener(view -> {
            resetScore();
        });

        empty_text_view = findViewById(R.id.empty_text);

        round_text_view = findViewById(R.id.round_view);

        day_text_view = findViewById(R.id.day_text);

        minus_make_button = findViewById(R.id.minus_score);
        minus_make_button.setOnClickListener(view -> {
            getMinusScore();
        });

        total_score_button = findViewById(R.id.get_score_button);
        total_score_button.setOnClickListener(view -> {
            getTotalScore();
        });

        round_1_button = findViewById(R.id.round_1);
        round_1_button.setOnClickListener(view -> {
            onClickLineCalculate(view);
        });
        round_2_button = findViewById(R.id.round_2);
        round_2_button.setOnClickListener(view -> {
            onClickLineCalculate(view);
        });
        round_3_button = findViewById(R.id.round_3);
        round_3_button.setOnClickListener(view -> {
            onClickLineCalculate(view);
        });
        round_4_button = findViewById(R.id.round_4);
        round_4_button.setOnClickListener(view -> {
            onClickLineCalculate(view);
        });
        round_5_button = findViewById(R.id.round_5);
        round_5_button.setOnClickListener(view -> {
            onClickLineCalculate(view);
        });
        round_6_button = findViewById(R.id.round_6);
        round_6_button.setOnClickListener(view -> {
            onClickLineCalculate(view);
        });
        round_7_button = findViewById(R.id.round_7);
        round_7_button.setOnClickListener(view -> {
            onClickLineCalculate(view);
        });
        round_8_button = findViewById(R.id.round_8);
        round_8_button.setOnClickListener(view -> {
            onClickLineCalculate(view);
        });
        round_9_button = findViewById(R.id.round_9);
        round_9_button.setOnClickListener(view -> {
            onClickLineCalculate(view);
        });
        round_10_button = findViewById(R.id.round_10);
        round_10_button.setOnClickListener(view -> {
            onClickLineCalculate(view);
        });

        for (int i = 0; i < 10; i++) {
            isLineCalculated.add(false);
        }

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    }

    private boolean isGameEnd() {

        int cnt = 0;

        for (int i = 0; i < 40; i++) {
            int pid = i % 4;
            EditText editText = (EditText) findViewById(getID(i));
            if ("".equals(editText.getText().toString())) {
                continue;
            }
            cnt++;
        }

        return true;
        //return (cnt == 40);
    }

    private void updateFinalRankWithHouseMoney() {
        if (presidentCount.size() == 4) {
            mRecordHelper.addPresidentRecord(presidentCount.get(0), presidentCount.get(1), presidentCount.get(2), presidentCount.get(3));
        } else {
            Toast.makeText(getApplicationContext(), "presidentCount update Failed!", Toast.LENGTH_SHORT).show();
        }

        if (finalRank.size() == 4) {
            mRecordHelper.addHouseMoney();
            mRecordHelper.addRecord(finalRank.get(0), finalRank.get(1), finalRank.get(2), finalRank.get(3));
            mMoneyHelper.updateMemberTodayMoney(finalRank.get(0), finalRank.get(1), finalRank.get(2), finalRank.get(3));
        } else {
            Toast.makeText(getApplicationContext(), "updateFinalRankWithHouseMoney Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getMinusScore() {
        //Log.i("TEST333", "getMinusScore");
        View focusView = getCurrentFocus();
        if (focusView instanceof EditText) {
            try {
                int score = Integer.valueOf(((EditText) focusView).getText().toString());
                //Log.i("TEST333", "Score : " + score);
                score *= -1;
                ((EditText) focusView).setText(String.valueOf(score));
            } catch (Exception e) {
                Log.i("TEST333", "getMinusScore Exception!");
                Toast.makeText(getApplicationContext(), "SCORE에다 커서를 위치하세요", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "SCORE 입력 후 누르세요", Toast.LENGTH_SHORT).show();
        }
    }

    private void getTotalScore() {
        getUpdateTime();
        calculateTotalScore();
        calculatePresidentCount();
    }

    private void getUpdateTime() {
        /*
        long mNow = System.currentTimeMillis();
        Date date = new Date(mNow);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        String curTime = sdf.format(date);
        day_text_view.setText(curTime);
         */
        DayTimeHelper calendar = new DayTimeHelper();
        day_text_view.setText(calendar.getDateDayTime());
    }

    private int getID(int i) {
        return getResources().getIdentifier("val" + i, "id", getPackageName());
    }

    private int getPlayerScoreID(int i) {
        return getResources().getIdentifier("player_score_" + i, "id", getPackageName());
    }

    private int getRankID(int i) {
        return getResources().getIdentifier("rank_" + i, "id", getPackageName());
    }

    private void calculateTotalScore() {
        try {
            ArrayList<Integer> playScore = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                playScore.add(0);
            }

            for (int i = 0; i < 40; i++) {
                int pid = i % 4;
                EditText editText = (EditText) findViewById(getID(i));
                if ("".equals(editText.getText().toString())) {
                    continue;
                }
                int score = Integer.parseInt(editText.getText().toString());
                int pScore = playScore.get(pid);
                pScore += score;
                playScore.set(pid, pScore);
            }

            for (int i = 0; i < 4; i++) {
                String sc = playScore.get(i).toString();
                TextView playerScore = (TextView) findViewById(getPlayerScoreID(i));
                playerScore.setText(sc);
            }

            getRank(playScore);
        } catch (Exception E) {
            Toast.makeText(getApplicationContext(), E.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void calculatePresidentCount() {
        try {
            presidentCount.clear();
            for (int i = 0; i < 4; i++) {
                presidentCount.add(0);
            }

            for (int i = 0; i < 40; i++) {
                int pid = i % 4;
                EditText editText = (EditText) findViewById(getID(i));
                if ("".equals(editText.getText().toString())) {
                    continue;
                }
                int score = Integer.parseInt(editText.getText().toString());
                if (score < -100) {
                    int pCount = presidentCount.get(pid);
                    pCount += 1;
                    presidentCount.set(pid, pCount);
                }
            }

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                String sc = presidentCount.get(i).toString();
                builder.append(i);
                builder.append(" - ");
                builder.append(Integer.parseInt(sc));
                builder.append(", ");
            }
            Log.i("TEST333", builder.toString());

        } catch (Exception E) {
            Toast.makeText(getApplicationContext(), E.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickLineCalculate(View view) {
        int line = Integer.valueOf(((TextView)view).getText().toString());
        line--;

        //Log.i("TEST333", "->line : " + line);
        try {
            if (isLineCalculated.get(line)) {
                for (int i = 0; i < 4; i++) {
                    int id = line * 4 + i;
                    EditText editText = (EditText) findViewById(getID(id));
                    editText.setBackgroundResource(R.drawable.second_table_item_normal);
                }
                isLineCalculated.set(line, false);
            } else {
                int minEditTextId = 0;
                int minVal = 10000;
                for (int i = 0; i < 4; i++) {
                    int id = line * 4 + i;
                    EditText editText = (EditText) findViewById(getID(id));
                    if ("".equals(editText.getText().toString())) {
                        continue;
                    }
                    int score = Integer.valueOf(editText.getText().toString());
                    if (score > 10000) {
                        score -= 10000;
                        score *= -1;
                        editText.setText(String.valueOf(score));
                    }
                    if (score < minVal) {
                        minVal = score;
                        minEditTextId = id;
                    }
                }
                EditText minEditText = (EditText) findViewById(getID(minEditTextId));

                minEditText.setBackgroundColor(getApplicationContext().getColor(R.color.winnerColor));

                //Bomb or President Color
                for (int i = 0; i < 4; i++) {
                    int id = line * 4 + i;
                    EditText editText = (EditText) findViewById(getID(id));
                    if ("".equals(editText.getText().toString())) {
                        continue;
                    }
                    int score = Integer.valueOf(editText.getText().toString());
                    if (score > 10000) {
                        score -= 10000;
                        score *= -1;
                        editText.setText(String.valueOf(score));
                    }
                    if (score >= 52) {
                        editText.setBackgroundResource(R.drawable.second_table_record_bomb);
                    }
                    if (score < 0 && score >= -100) {
                        editText.setBackgroundResource(R.drawable.second_table_record_brave_win);
                    }
                    if (score < -100) {
                        editText.setBackgroundResource(R.drawable.second_table_record_president);
                    }
                }

                isLineCalculated.set(line, true);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception!", Toast.LENGTH_SHORT).show();
        }

        setRoundViewText(line + 1);
        empty_text_view.requestFocus();
        imm.hideSoftInputFromWindow(empty_text_view.getWindowToken(), 0);
    }

    private void setRoundViewText(int line) {
        if (line == 9) {
            round_text_view.setText("마지막 판!!");
            round_text_view.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        } else if (line == 10) {
            round_text_view.setText("결과 집계 중...");
            round_text_view.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
        } else {
            StringBuilder builder = new StringBuilder();
            int remain = 10 - line;
            builder.append("이번 게임 종료까지 ");
            builder.append(remain);
            builder.append(" 판 남았습니다.");
            round_text_view.setText(builder.toString());
            round_text_view.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.gray));
        }
        //round_text_view.setTypeface(null, Typeface.BOLD);
    }

    private void resetRecordDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("History 초기화 하기");
        alertDialogBuilder.setMessage("초기화 할까요?")
                .setCancelable(false)
                .setPositiveButton(
                        "History 초기화 하기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mRecordHelper.resetRecordData();
                                mHistoryText.setText("");
                                writeHistoryFileToDevice();

                            }
                        }
                )
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        alertDialogBuilder.create().show();
    }

    private void resetScore() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("점수 초기화 하기");
        alertDialogBuilder.setMessage("초기화 할까요?")
                .setCancelable(false)
                .setPositiveButton(
                        "점수 초기화 하기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                doResetScore();
                            }
                        }
                )
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        alertDialogBuilder.create().show();
    }

    private void doResetScore() {
        finalRank.clear();

        for (int i = 0; i < 40; i++) {
            //int pid = i % 4;
            EditText editText = (EditText) findViewById(getID(i));
            editText.setText("");
            editText.setBackgroundResource(R.drawable.second_table_item_normal);
        }

        for (int i = 0; i < 4; i++) {
            TextView playerScore = (TextView) findViewById(getPlayerScoreID(i));
            playerScore.setText("");

            TextView rankResult = (TextView) findViewById(getRankID(i));
            rankResult.setText("");
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("게임 종료하기");
        alertDialogBuilder.setMessage("종료할까요 ?")
                .setCancelable(false)
                .setPositiveButton(
                        "종료하기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SecondTableActivity.super.onBackPressed();
                            }
                        }
                )
                .setNegativeButton("게임으로 돌아가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        alertDialogBuilder.create().show();
    }

    private void updatePresidentCount(ArrayList<Integer> pCount) {
        presidentCount.clear();
        for (int i = 0; i < 4; i++) {
            presidentCount.add(pCount.get(i));
        }
    }

    private void getRank(ArrayList<Integer> playScore) {
        finalRank.clear();

        ArrayList<Integer> rank = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            rank.add(0);
            finalRank.add(0);
        }

        for (int i = 0; i < playScore.size(); i++) {
            rank.set(i, 1);
            for (int j = 0; j < playScore.size(); j++) {
                if (playScore.get(i) > playScore.get(j)) {
                    int ranking = rank.get(i);
                    ranking++;
                    rank.set(i, ranking);
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            TextView rankResult = (TextView) findViewById(getRankID(i));
            rankResult.setText(rank.get(i).toString() + " 등");
            finalRank.set(i, rank.get(i));
        }
    }

    private void loadHistory() {
        String fileTitle = "bb_history.txt";
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileTitle);
        String result = "";
        FileInputStream fis = null;
        StringBuilder builder = new StringBuilder();

        try {
            if (!file.exists()) {
                return;
            }

            fis = new FileInputStream(file);
            char[] buf = new char[4096];
            InputStreamReader reader = new InputStreamReader(fis, "UTF-8");

            while (true) {
                int len = reader.read(buf);
                if (len < 0) {
                    break;
                }
                builder.append(buf, 0, len);
            }

            fis.close();
            fis = null;
        } catch (Exception e) {
            Log.i("TEST333", e.toString());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    Log.i("TEST333", e.toString());
                }
            }
        }

        mHistoryText.setText(builder.toString());
    }

    private void writeHistoryFileToDevice() {
        String fileTitle = "bb_history.txt";
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileTitle);
        Log.i("TEST333", "-->" + getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS));
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            String str = mHistoryText.getText().toString();

            FileWriter writer = new FileWriter(file, false);
            writer.write(str);
            writer.close();
        } catch (IOException e) {
            Log.i("TEST333", e.toString());
        }
    }

    private void addLineAndWriteHistoryFileToDevice() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("- - - - - - - - - - - - - - - - - - - - - - - -\n");
            String originHistory = mHistoryText.getText().toString();
            sb.append(originHistory);
            mHistoryText.setText(sb.toString());
        } catch (Exception e) {
            Log.i("TEST333", e.toString());
        }

        writeHistoryFileToDevice();
    }

    private void addHistory() {
        getUpdateTime();

        StringBuilder sb = new StringBuilder();
        try {
            sb.append(((TextView)findViewById(R.id.day_text)).getText());
            sb.append(emptyToken);
            sb.append(((TextView)findViewById(R.id.rank_0)).getText());
            sb.append(stringToken);
            sb.append(((TextView)findViewById(R.id.rank_1)).getText());
            sb.append(stringToken);
            sb.append(((TextView)findViewById(R.id.rank_2)).getText());
            sb.append(stringToken);
            sb.append(((TextView)findViewById(R.id.rank_3)).getText());
            sb.append(lineChangeToken);

            String originHistory = mHistoryText.getText().toString();
            sb.append(originHistory);

            mHistoryText.setText(sb.toString());
        } catch (Exception e) {
            Log.i("TEST333", e.toString());
        }
    }

    private void doingAddHistoryProcess() {
        addHistory();
        writeHistoryFileToDevice();
        updateFinalRankWithHouseMoney();
    }

    private void showDialogAddHistory() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("게임 결과 업데이트");
        alertDialogBuilder.setMessage("rank저장 및 금액 업데이트")
                .setCancelable(false)
                .setPositiveButton(
                        "YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                doingAddHistoryProcess();
                            }
                        }
                )
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        alertDialogBuilder.create().show();
    }
}
