package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdvancedWhackAMoleActivity extends AppCompatActivity {

    private Button topLeftButton, topMiddleButton, topRightButton, middleLeftButton, middleMiddleButton, middleRightButton, bottomLeftButton, bottomMiddleButton, bottomRightButton;
    private TextView scoreText, countdownText;

    private List<Button> buttonList = new ArrayList<Button>();

    private static final String TAG = "Whack-A-Mole";

    private int score;

    private CountDownTimer timer, moleTimer;

    private void readyTimer(){

        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
                Toast.makeText(getApplicationContext(), "Get Ready in " + millisUntilFinished / 1000 + " seconds", Toast.LENGTH_SHORT).show();
                countdownText.setText("Game starting in " + (int) Math.floor(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                countdownText.setVisibility(View.GONE);
                scoreText.setVisibility(View.VISIBLE);
                for (Button button : buttonList){
                    button.setVisibility(View.VISIBLE);
                    button.setClickable(true);
                }
                Log.v(TAG, "Ready CountDown Complete!");
                Toast.makeText(getApplicationContext(), "GO", Toast.LENGTH_SHORT).show();
                placeMoleTimer();
            }
        };
        timer.start();
    }

    private void placeMoleTimer(){
        moleTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Log.v(TAG, "New Mole Location!");
                MainActivity.setNewMole(buttonList);
                moleTimer.start();
            }
        };
        moleTimer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_whack_a_mole);
        this.setTitle("Whack-A-Mole!"); // Set Title Of Activity

        Intent toAdvanced = getIntent();
        score = toAdvanced.getIntExtra("Score", 0);

        topLeftButton = (Button) findViewById(R.id.topLeftButton);
        topMiddleButton = (Button) findViewById(R.id.topMiddleButton);
        topRightButton = (Button) findViewById(R.id.topRightButton);
        middleLeftButton = (Button) findViewById(R.id.middleLeftButton);
        middleMiddleButton = (Button) findViewById(R.id.middleMiddleButton);
        middleRightButton = (Button) findViewById(R.id.middleRightButton);
        bottomLeftButton = (Button) findViewById(R.id.bottomLeftButton);
        bottomMiddleButton = (Button) findViewById(R.id.bottomMiddleButton);
        bottomRightButton = (Button) findViewById(R.id.bottomRightButton);

        buttonList.add(topLeftButton);
        buttonList.add(topMiddleButton);
        buttonList.add(topRightButton);
        buttonList.add(middleLeftButton);
        buttonList.add(middleMiddleButton);
        buttonList.add(middleRightButton);
        buttonList.add(bottomLeftButton);
        buttonList.add(bottomMiddleButton);
        buttonList.add(bottomRightButton);

        scoreText = (TextView) findViewById(R.id.advancedScore);
        scoreText.setVisibility(View.INVISIBLE);
        scoreText.setText(score + "");

        countdownText = (TextView) findViewById(R.id.countdownText);

        for (Button button : buttonList){
            button.setVisibility(View.INVISIBLE);
            button.setClickable(false);
            final Button buttonInner = button;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MainActivity.hitCorrectButton(buttonInner)){
                        Log.v(TAG, "Hit, score added!");
                        score += 1;
                    }
                    else{
                        Log.v(TAG, "Missed, score deducted!");
                        score -= 1;
                    }
                    scoreText.setText(score + "");
                    MainActivity.setNewMole(buttonList);
                }
            });
        }

        readyTimer();
    }

    @Override
    protected void onStart(){
        super.onStart();
        MainActivity.setNewMole(buttonList);
    }





}

