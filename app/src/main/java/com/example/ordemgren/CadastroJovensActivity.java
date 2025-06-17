package com.example.ordemgren;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ordemgren.models.Jovem;
import com.example.ordemgren.models.Patrulha;
import java.util.ArrayList;
import java.util.List;

public class CadastroJovensActivity extends AppCompatActivity {
    private LinearLayout layoutJovens;
    private Button btnSalvar;
    private List<EditText> camposNomes;
    private List<Patrulha> patrulhas;
    private String nomePatrulha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_jovens);

        layoutJovens = findViewById(R.id.layoutJovens);
        btnSalvar = findViewById(R.id.btnSalvarPatrulha);
        camposNomes = new ArrayList<>();
        patrulhas = Utils.carregarPatrulhas(this);

        nomePatrulha = getIntent().getStringExtra("nomePatrulha");

        for (int i = 0; i < 8; i++) adicionarCampoJovem();

        btnSalvar.setOnClickListener(v -> salvarPatrulha());
    }

    private void adicionarCampoJovem() {
        EditText campo = new EditText(this);
        campo.setHint("Nome do Jovem");
        layoutJovens.addView(campo);
        camposNomes.add(campo);
    }

    private void salvarPatrulha() {
        Patrulha patrulha = new Patrulha(nomePatrulha);
        int i = 0;
        for (EditText campo : camposNomes) {
            String nome = campo.getText().toString();
            if (!nome.isEmpty()) {
                patrulha.adicionarJovem(new Jovem(nome));
            }
        }
        if (patrulha.getJovens().size() >= 4 && patrulha.getJovens().size() <= 8) {
            patrulhas.add(patrulha);
            Utils.salvarPatrulhas(this, patrulhas);
            for (Jovem jovem : patrulha.getJovens()) {
                if (i == 0) {
                    jovem.setMonitor(true);
                }
                else{
                    jovem.setMonitor(false);
                }
                i++;
            }
            finish();
        } else {
            Toast.makeText(this, "Cadastre entre 4 a 8 jovens", Toast.LENGTH_SHORT).show();
        }

    }
}