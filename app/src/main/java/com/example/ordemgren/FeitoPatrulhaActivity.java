package com.example.ordemgren;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ordemgren.models.Jovem;
import com.example.ordemgren.models.Patrulha;
import java.util.List;

public class FeitoPatrulhaActivity extends AppCompatActivity {
    private Spinner spinnerTipo, spinnerFeito, spinnerPatrulha, spinnerJovem;
    private List<Patrulha> patrulhas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feito_patrulha);

        spinnerTipo = findViewById(R.id.spinnerTipo);
        spinnerFeito = findViewById(R.id.spinnerFeito);
        spinnerPatrulha = findViewById(R.id.spinnerPatrulhaFeito);
        spinnerJovem = findViewById(R.id.spinnerJovemFeito);

        patrulhas = Utils.carregarPatrulhas(this);
        String[] tipos = {"Patrulha", "Jovem"};
        spinnerTipo.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipos));

        String[] nomesPatrulhas = new String[patrulhas.size()];
        for (int i = 0; i < patrulhas.size(); i++) nomesPatrulhas[i] = patrulhas.get(i).getNome();
        spinnerPatrulha.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nomesPatrulhas));

        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                atualizarSpinners();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerPatrulha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                atualizarSpinners();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        findViewById(R.id.btnSalvarFeito).setOnClickListener(v -> salvarFeito());
    }

    private void atualizarSpinners() {
        String tipo = spinnerTipo.getSelectedItem().toString();
        if (tipo.equals("Patrulha")) {
            spinnerJovem.setVisibility(View.GONE);
            String[] feitos = {"Ganhou o jogo", "Primeira a formar"};
            spinnerFeito.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, feitos));
        } else {
            spinnerJovem.setVisibility(View.VISIBLE);
            String[] feitos = {"Esteve presente", "Se atrasou"};
            spinnerFeito.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, feitos));

            int pos = spinnerPatrulha.getSelectedItemPosition();
            if (pos >= 0) {
                List<Jovem> jovens = patrulhas.get(pos).getJovens();
                String[] nomesJovens = new String[jovens.size()];
                for (int i = 0; i < jovens.size(); i++) nomesJovens[i] = jovens.get(i).getNome();
                spinnerJovem.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nomesJovens));
            }
        }
    }

    private void salvarFeito() {
        String tipo = spinnerTipo.getSelectedItem().toString();
        String feito = spinnerFeito.getSelectedItem().toString();

        if (tipo.equals("Patrulha")) {
            // Pontuação por patrulha
            int pos = spinnerPatrulha.getSelectedItemPosition();
            Patrulha patrulha = patrulhas.get(pos);

            if (feito.equals("Ganhou o jogo")) {
                patrulha.adicionarPontosPatrulha(10);
            } else if (feito.equals("Primeira a formar")) {
                patrulha.adicionarPontosPatrulha(5);
            }

        } else if (tipo.equals("Jovem")) {
            // Pontuação por jovem
            int pos = spinnerPatrulha.getSelectedItemPosition();
            int posPatrulha = spinnerPatrulha.getSelectedItemPosition();
            Patrulha patrulha = patrulhas.get(posPatrulha);

            int posJovem = spinnerJovem.getSelectedItemPosition();
            Jovem jovem = patrulha.getJovens().get(posJovem);

            if (feito.equals("Esteve presente")) {
                jovem.adicionarPontos(2);
                patrulha.adicionarPontosPatrulha(2);
            } else if (feito.equals("Se atrasou")) {
                jovem.adicionarPontos(-1);
                patrulha.adicionarPontosPatrulha(-1);
            }
        }

        Utils.salvarPatrulhas(this, patrulhas);
        Toast.makeText(this, "Feito registrado!", Toast.LENGTH_SHORT).show();
        finish();
    }

}