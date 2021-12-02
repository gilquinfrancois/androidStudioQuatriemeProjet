package com.example.quatriemeprojet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
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

    private ProgressBar pb_main_progressionS7;
    private Button bt_main_ConnexS7;
    private TextView tv_main_plc;
    private ReadTaskS7 readS7;
    private NetworkInfo network;
    private ConnectivityManager connexStatus;

    private LinearLayout ln_main_ecrireS7;
    private CheckBox ch_main_activerouv;
    private CheckBox ch_main_activerfer;
    private CheckBox ch_main_aru;
    private WriteTaskS7 writeS7;

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

        pb_main_progressionS7 = (ProgressBar) findViewById(R.id.pb_main_progressionS7);
        bt_main_ConnexS7 = (Button) findViewById(R.id.bt_main_ConnexS7);
        tv_main_plc = (TextView) findViewById(R.id.tv_main_plc);
        connexStatus = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        network = connexStatus.getActiveNetworkInfo();

        ln_main_ecrireS7 = (LinearLayout) findViewById(R.id.ln_main_ecrireS7);
        ch_main_activerouv = (CheckBox) findViewById(R.id.ch_main_activerouv);
        ch_main_activerfer = (CheckBox) findViewById(R.id.ch_main_activerfer);
        ch_main_aru = (CheckBox) findViewById(R.id.ch_main_aru);
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

            case R.id.bt_main_ConnexS7:
                if(network != null && network.isConnectedOrConnecting()) {
                    if(bt_main_ConnexS7.getText().equals("Connexion_S7")) {
                        Toast.makeText(this,network.getTypeName(),Toast.LENGTH_SHORT).show();
                        bt_main_ConnexS7.setText("Déconnexion_S7");
                        readS7 = new ReadTaskS7(v, bt_main_ConnexS7, pb_main_progressionS7, tv_main_plc);
                        readS7.Start("192.168.10.110","0","1");

                        ln_main_ecrireS7.setVisibility(View.VISIBLE);
                        try {
                            Thread.sleep(1000);
                        } catch(InterruptedException e) {
                            e.printStackTrace();
                        }
                        writeS7 = new WriteTaskS7();
                        writeS7.Start("192.168.10.110","0","1");
                    } else {
                        readS7.Stop();
                        bt_main_ConnexS7.setText("Connexion_S7");
                        Toast.makeText(getApplicationContext(),"Traitement interrompu par l'utilisateur !",Toast.LENGTH_LONG).show();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        writeS7.Stop();
                        ln_main_ecrireS7.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Toast.makeText(this, "Connexion réseau impossible !", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ch_main_activerouv:
                writeS7.setWriteBool(1, ch_main_activerouv.isChecked() ? 1 : 0);
                break;

            case R.id.ch_main_activerfer:
                writeS7.setWriteBool(2, ch_main_activerfer.isChecked() ? 1 : 0);
                break;

            case R.id.ch_main_aru:
                writeS7.setWriteBool(1, ch_main_aru.isChecked() ? 1 : 0);
                break;
        }
    }
}