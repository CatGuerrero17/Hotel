package com.example.aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    VideoView w_fondo;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;
    EditText pass,em;
    Button btnLogin;
    String url ="http://192.168.0.7/PHP/PostgreSQL/read.php";
    int confirm=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pass=(EditText)findViewById(R.id.password);
        em=(EditText)findViewById(R.id.email);
        btnLogin=findViewById(R.id.btnLogin);



        w_fondo = findViewById(R.id.w_fondo);
        Uri uri = Uri.parse("android.resource://"+ getPackageName()+ "/"+ R.raw.video_login);

        w_fondo.setVideoURI(uri);

        w_fondo.start();

        w_fondo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
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


    public boolean validar(){

        String ps=pass.getText().toString();
        String cr=em.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            String succes = jsonObject.getString("succes");
                            JSONArray jsonArray = jsonObject.getJSONArray("datos");

                            if(succes.equals("1")){

                                for(int i=0;i<jsonArray.length();i++){

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    if(em.getText().toString().equals(object.getString("usuario"))){
                                        if(pass.getText().toString().equals(object.getString("contraseña"))){
                                            Toast.makeText(MainActivity.this,"Se encuentra en la bd",Toast.LENGTH_LONG).show();
                                            confirm=1;
                                            break;
                                        }else{
                                            confirm=0;
                                        }
                                    }else{
                                        confirm=0;
                                    }
                                }
                                if (confirm==0){
                                    Toast.makeText(MainActivity.this,"NO se encuentra en la bd",Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

        boolean aux=true;
        if (ps.isEmpty()) {
            pass.setError("Campo vacio");
            aux=false;
        }
        if (cr.isEmpty()){
            em.setError("Campo vacio");
            aux=false;
        }
        return aux;
    }

    public void Log_in (View view){
        validar();
    }
    @Override
    protected void onPause(){
        super.onPause();

        mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        w_fondo.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();

        w_fondo.start();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    public void Recordar(View view){
        Intent recordar = new Intent(this, Recordar_con.class);
        startActivity(recordar);
    }
    public void Registrar(View view){
        Intent resgistrar = new Intent(this, Registrar.class);
        startActivity(resgistrar);
    }


}