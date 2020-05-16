package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button ButtonLeft;
    private Button ButtonMiddle;
    private Button ButtonRight;
    private TextView ScoreText;
    private List<Button> buttonList = new ArrayList<Button>();
    private int score;

    private static final String TAG = "Whack-A-Mole";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Whack-A-Mole!"); // Set Title Of Activity

        ButtonLeft = (Button) findViewById(R.id.ButtonLeft);
        ButtonMiddle = (Button) findViewById(R.id.ButtonMiddle);
        ButtonRight = (Button) findViewById(R.id.ButtonRight);
        ScoreText = findViewById(R.id.Score);
        score = 0;

        buttonList.add(ButtonLeft);
        buttonList.add(ButtonMiddle);
        buttonList.add(ButtonRight);

        ButtonLeft.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.v(TAG, "Left Button Clicked!");
                if (hitCorrectButton(ButtonLeft)){
                    Log.v(TAG, "Hit, score added!");
                    score += 1;
                }
                else{
                    Log.v(TAG, "Missed, score deducted!");
                    score -= 1;
                }
                ScoreText.setText(score + "");
                doCheck(ButtonLeft);
                setNewMole(buttonList);
            }
        });

        ButtonMiddle.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.v(TAG, "Middle Button Clicked!");
                if (hitCorrectButton(ButtonMiddle)){
                    Log.v(TAG, "Hit, score added!");
                    score += 1;
                }
                else{
                    Log.v(TAG, "Missed, score deducted!");
                    score -= 1;
                }
                ScoreText.setText(score + "");
                doCheck(ButtonMiddle);
                setNewMole(buttonList);
            }
        });

        ButtonRight.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.v(TAG, "Right Button Clicked!");
                if (hitCorrectButton(ButtonRight)){
                    Log.v(TAG, "Hit, score added!");
                    score += 1;
                }
                else{
                    Log.v(TAG, "Missed, score deducted!");
                    score -= 1;
                }
                ScoreText.setText(score + "");
                doCheck(ButtonRight);
                setNewMole(buttonList);
            }
        });

        Log.v(TAG, "Finished Pre-Initialisation!");
    }

    @Override
    protected void onStart(){
        super.onStart();
        setNewMole(buttonList);
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton){
        if (checkButton.getText().equals("*") && score % 10 == 0){
            nextLevelQuery();
        }
    }

    private void nextLevelQuery(){
        final AlertDialog.Builder advancedVersionBuilder = new AlertDialog.Builder(this);
        advancedVersionBuilder.setTitle("Advanced Version");
        advancedVersionBuilder.setMessage("Would you like to enter the advanced version?");
        advancedVersionBuilder.setCancelable(false);
        advancedVersionBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Log.v(TAG, "User accepts!");
                nextLevel();
            }
        });
        advancedVersionBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.v(TAG, "User decline!");
            }
        });
        final AlertDialog advancedVersionAlert = advancedVersionBuilder.create();
        advancedVersionAlert.show();
        Log.v(TAG, "Advance option given to user!");
    }

    private void nextLevel(){
        Intent toAdvanced = new Intent(MainActivity.this, AdvancedWhackAMoleActivity.class);
        toAdvanced.putExtra("Score", score);
        startActivity(toAdvanced);
        finish();
    }

    public static boolean hitCorrectButton(Button button){
        if (button.getText().equals("*")){
            return true;
        }

        else if (button.getText().equals("O")){
            return false;
        }

        return false;
    }

    public static void setNewMole(List<Button> buttonList){
        Random r = new Random();
        int molePlace = r.nextInt(buttonList.size());
        for (Button button : buttonList){
            button.setText("O");
        }
        buttonList.get(molePlace).setText("*");
    }
}
