package com.example.cameraaa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = (ImageView) findViewById(R.id.imagem);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA}, 0);

        }

    }

    public void botaoGaleria(View view){

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 2);


    }

    public void camera(View view){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 1);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == RESULT_OK){

            Uri imagemSelecionada = data.getData();

            String[] caminhoArquivo = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(imagemSelecionada, caminhoArquivo, null, null, null);
            c.moveToFirst();
            int coluna = c.getColumnIndex(caminhoArquivo[0]);
            String caminhoFoto = c.getString(coluna);
            c.close();
            Bitmap resultado = (BitmapFactory.decodeFile(caminhoFoto));

            iv.setImageBitmap(resultado);


        }

        if(requestCode == 1 && resultCode == RESULT_OK){
            Bundle dado = data.getExtras();
            Bitmap imagem = (Bitmap) dado.get("data");
            iv.setImageBitmap(imagem);
        }


    }
}