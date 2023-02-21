package com.example.pokedex;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class Cadastrar extends AppCompatActivity {
    private Spinner tipoSpinner, habilidadeSpinner1, habilidadeSpinner2, habilidadeSpinner3;
    private ArrayAdapter<CharSequence> habilidadeAdapter, tipoAdapter;
    private String selectecTipo, selectedHabilidade1, selectedHabilidade2, selectedHabilidade3;

    int SELECT_FOTO = 1;
    int SELECT_CAM = 2;


    ImageView IVPreviewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);


        tipoSpinner = findViewById(R.id.spinnerTipo);
        habilidadeSpinner1 = findViewById(R.id.spinnerHabilidade1);
        habilidadeSpinner2 = findViewById(R.id.spinnerHabilidade2);
        habilidadeSpinner3 = findViewById(R.id.spinnerHabilidade3);
        Button submitButton = findViewById(R.id.button_submit);
        IVPreviewImage = findViewById(R.id.imageViewCam);


        //Cria ArrayAdapter usando um string array e um spinnerlayout
        tipoAdapter = ArrayAdapter.createFromResource(this, R.array.tipo_poke, R.layout.spinner_layout);
        habilidadeAdapter = ArrayAdapter.createFromResource(this, R.array.habilidade_poke, R.layout.spinner_layout);

        //Seleciona o layout
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        habilidadeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Seta o adapter ao spinner para popular o spinner
        tipoSpinner.setAdapter(tipoAdapter);
        habilidadeSpinner1.setAdapter(habilidadeAdapter);
        habilidadeSpinner2.setAdapter(habilidadeAdapter);
        habilidadeSpinner3.setAdapter(habilidadeAdapter);

        tipoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectecTipo = tipoSpinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        habilidadeSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHabilidade1 = habilidadeSpinner1.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        habilidadeSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHabilidade2 = habilidadeSpinner2.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        tipoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHabilidade3 = habilidadeSpinner3.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    public void abrirCamera(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Cadastrar.this);
        builder.setTitle("Imagem");
        builder.setMessage("Escolha a origem da Imagem");
        builder.setCancelable(true);

        builder.setNegativeButton("Galeria", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                abrirGaleria();
            }
        });

        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                abrirCamera();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void abrirGaleria() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_FOTO);
    }
    public void abrirCamera() {
        File file = new File(Environment.getExternalStorageDirectory() + "/arquivo.jpg");
        Uri outputFileUri = Uri.fromFile(file);
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, SELECT_CAM);
    }

   public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       System.gc();

        if (requestCode == SELECT_FOTO) {

            if (resultCode == RESULT_OK) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    IVPreviewImage.setImageURI(selectedImageUri);
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Nenhuma imagem selecionada", Toast.LENGTH_SHORT);
                }
            }

        } if (requestCode == SELECT_CAM) {
                if (resultCode == RESULT_OK) {
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 3;
                        Bitmap imageBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/arquivo.jpg", options);

                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        boolean validaCompressao = imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                        byte[] fotoBinario = outputStream.toByteArray();

                        String encodedImage = Base64.encodeToString(fotoBinario, Base64.DEFAULT);

                        IVPreviewImage.setImageBitmap(imageBitmap); // ImageButton, seto a foto como imagem do botão

                        boolean isImageTaken = true;
                    } catch (Exception e) {
                        Toast.makeText(this, "Foto não tirada",Toast.LENGTH_LONG).show();e.printStackTrace();
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Nenhuma foto tirada 1", Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(this, "Nenhuma foto tirada 1", Toast.LENGTH_SHORT);
                }
            }
        }
    }