package com.example.aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

public class Registrar extends AppCompatActivity {

    VideoView w_fondo3;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;
    EditText id,nom,pais,n_acom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        id= (EditText)findViewById(R.id.id_registro);
        nom=(EditText)findViewById(R.id.nom_registro);
        pais=(EditText)findViewById(R.id.pais_registro);
        n_acom=(EditText)findViewById(R.id.n_acom_registro);
        w_fondo3 = findViewById(R.id.w_fondo3);
        Uri uri = Uri.parse("android.resource://"+ getPackageName()+ "/"+ R.raw.video_login);

        w_fondo3.setVideoURI(uri);

        w_fondo3.start();

        w_fondo3.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;

                mMediaPlayer.setLooping(true);

                if(mCurrentVideoPosition !=0){
                    mMediaPlayer.seekTo(mCurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });
    }

    public boolean validar_registro(){
        boolean aux=true;
        String i=id.getText().toString();
        String n=nom.getText().toString();
        String p=pais.getText().toString();
        String na=n_acom.getText().toString();

        if (i.isEmpty()) {
            id.setError("Campo vacio");
            aux=false;
        }
        if (n.isEmpty()){
            nom.setError("Campo vacio");
            aux=false;
        }
        if (p.isEmpty()){
            pais.setError("Campo vacio");
            aux=false;
        }
        if (na.isEmpty()){
            n_acom.setError("Campo vacio");
            aux=false;
        }
        return aux;
    }

    public void resgistrar (View view){
        if (validar_registro()){
            Toast.makeText(this, "Datos ingresados", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();

        mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        w_fondo3.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();

        w_fondo3.start();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        mMediaPlayer.release();
        mMediaPlayer = null;
    }
}