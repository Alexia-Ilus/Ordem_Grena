package com.example.ordemgren;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Tela para cadastrar o nome da patrulha.
 * Após salvar o nome, vai para o cadastro dos jovens.
 */
public class CadastroPatrulhaActivity extends AppCompatActivity {

    private EditText editNomePatrulha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_patrulha);

        editNomePatrulha = findViewById(R.id.editNomePatrulha);

        findViewById(R.id.btnProximoJovens).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editNomePatrulha.getText().toString();
                if (nome.isEmpty()) {
                    Toast.makeText(CadastroPatrulhaActivity.this, "Digite o nome da patrulha", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CadastroPatrulhaActivity.this, CadastroJovensActivity.class);
                    intent.putExtra("nomePatrulha", nome);
                    startActivity(intent);
                }
            }
        });
    }
}
