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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb_main_progressionTH = (ProgressBar) findViewById(R.id.pb_main_progressionTH);
        bt_main_startTH = (Button) findViewById(R.id.bt_main_startTH);
        tv_main_texte = (TextView) findViewById(R.id.tv_main_texte);
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
        }
    }
}