package com.example.quatriemeprojet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pb_main_progressionTH;
    private Button bt_main_startTH;
    private TextView tv_main_texte;
    private ThreadTest monThread;

    private ProgressBar pb_main_progressionAS;
    private Button bt_main_startAS;

    private ProgressBar pb_main_progressionTrHa1;
    private ProgressBar pb_main_progressionTrHa2;
    private Button bt_main_startTrHa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb_main_progressionTH = (ProgressBar) findViewById(R.id.pb_main_progressionTH);
        bt_main_startTH = (Button) findViewById(R.id.bt_main_startTH);
        tv_main_texte = (TextView) findViewById(R.id.tv_main_texte);

        pb_main_progressionAS = (ProgressBar) findViewById(R.id.pb_main_progressionAS);
        bt_main_startAS = (Button) findViewById(R.id.bt_main_startAS);

        pb_main_progressionTrHa1 = (ProgressBar) findViewById(R.id.pb_main_progressionTrHa1);
        pb_main_progressionTrHa2 = (ProgressBar) findViewById(R.id.pb_main_progressionTrHa2);
        bt_main_startTrHa = (Button) findViewById(R.id.bt_main_startThHa);
    }

    public void onMainClickManager(View v) {
        switch(v.getId()) {
            case R.id.bt_main_txtchange:
                if(tv_main_texte.getText().equals("Hello World !")) {
                    tv_main_texte.setText("Hey Android !");
                } else {
                    tv_main_texte.setText("Hello World !");
                }
                break;
            case R.id.bt_main_startTH:
                if(bt_main_startTH.getText().equals("Thread GO")) {
                    monThread = new ThreadTest(pb_main_progressionTH);
                    monThread.Go();
                    bt_main_startTH.setText("Thread STOP");
                    Toast.makeText(this, "Activation du Thread !", Toast.LENGTH_SHORT).show();
                } else {
                    monThread.Stop();
                    bt_main_startTH.setText("Thread GO");
                    Toast.makeText(this,"Désactivation du Thread !",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.bt_main_startAS:
                AsyncroTask asyncroTask = new AsyncroTask(v, bt_main_startAS, pb_main_progressionAS);
                asyncroTask.execute("paramètre(s) de traitement");
                break;

            case R.id.bt_main_startThHa:
                BackgroundTask backgroundTask1 = new BackgroundTask(v, bt_main_startTrHa, pb_main_progressionTrHa1);
                backgroundTask1.start();
                BackgroundTask backgroundTask2 = new BackgroundTask(v, bt_main_startTrHa, pb_main_progressionTrHa2);
                backgroundTask2.start();
                break;
        }
    }
}