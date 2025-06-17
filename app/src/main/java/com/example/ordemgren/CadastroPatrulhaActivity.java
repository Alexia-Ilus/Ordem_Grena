package com.example.ordemgren;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ordemgren.models.Patrulha;
import java.util.List;

public class CadastroPatrulhaActivity extends AppCompatActivity {
    private EditText editNomePatrulha;
    private Button btnProximo;
    private List<Patrulha> patrulhas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_patrulha);

        editNomePatrulha = findViewById(R.id.editNomePatrulha);
        btnProximo = findViewById(R.id.btnProximoJovens);
        patrulhas = Utils.carregarPatrulhas(this);

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomePatrulha = editNomePatrulha.getText().toString();
                if (!nomePatrulha.isEmpty()) {
                    Intent intent = new Intent(CadastroPatrulhaActivity.this, CadastroJovensActivity.class);
                    intent.putExtra("nomePatrulha", nomePatrulha);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CadastroPatrulhaActivity.this, "Informe o nome da patrulha", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}