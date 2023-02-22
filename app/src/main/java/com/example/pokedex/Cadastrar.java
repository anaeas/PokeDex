package com.example.pokedex;

import static android.content.ContentValues.TAG;

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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Cadastrar extends AppCompatActivity {
    private EditText nomeEdt;
    private Spinner tipoSpinner, habilidadeSpinner1, habilidadeSpinner2, habilidadeSpinner3;
    private ArrayAdapter<CharSequence> habilidadeAdapter, tipoAdapter;
    private String nome,selectecTipo, selectedHabilidade1, selectedHabilidade2, selectedHabilidade3, encodedImage;

    int SELECT_FOTO = 1;
    int SELECT_CAM = 2;


    ImageView IVPreviewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        encodedImage = "false";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        nomeEdt = findViewById(R.id.editTextNome);
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

        habilidadeSpinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                final InputStream imageStream;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG,50,baos);
                    byte[] b = baos.toByteArray();
                    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if (null != selectedImageUri) {
                    IVPreviewImage.setImageURI(selectedImageUri);
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Nenhuma imagem selecionada", Toast.LENGTH_SHORT);
                }
            }

        }
        if (requestCode == SELECT_CAM) {
                if (resultCode == RESULT_OK) {
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 3;
                        Bitmap imageBitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/arquivo.jpg", options);

                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        boolean validaCompressao = imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                        byte[] fotoBinario = outputStream.toByteArray();

                        encodedImage = Base64.encodeToString(fotoBinario, Base64.DEFAULT);

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

        public void submit(View view)
        {
            nome = nomeEdt.getText().toString();
            if (nome.isEmpty()) {
                Toast.makeText(Cadastrar.this, "Por favor informe o nome do pokemon", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectecTipo.equals("Selecione um tipo")) {
                Toast.makeText(Cadastrar.this, "Por favor selecione um tipo", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedHabilidade1.equals("Selecione uma habilidade") && selectedHabilidade2.equals("Selecione uma habilidade") && selectedHabilidade3.equals("Selecione uma habilidade")) {
                Toast.makeText(Cadastrar.this, "Por favor selecione pelo menos uma habilidade", Toast.LENGTH_SHORT).show();
                return;
            }
            if (encodedImage.equals("false")) {
                Toast.makeText(Cadastrar.this, "Por forneça uma imagem", Toast.LENGTH_SHORT).show();
                return;
            }
            cadastrarPokemon();
        }

        private void cadastrarPokemon() {
            String url = "http://192.168.15.5/pokedexApi/public/api/registroPokemon";

            RequestQueue queue = Volley.newRequestQueue(Cadastrar.this);

            StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG, "response :" +response);
                    if (Integer.parseInt(response) == 1) {
                        Toast.makeText(getApplicationContext(), "Pokemon cadastrado!.", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(getIntent());
                    } else {
                        Toast.makeText(getApplicationContext(), "Erro: nome duplicado!.", Toast.LENGTH_LONG).show();
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Erro de conexão com a api.", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("nome", nome);
                    params.put("tipo", selectecTipo);
                    params.put("habilidade_1", selectedHabilidade1);
                    params.put("habilidade_2", selectedHabilidade2);
                    params.put("habilidade_3", selectedHabilidade3);
                    params.put("image", encodedImage);

                    return params;
                }
            };
            queue.add(request);
        }
    }