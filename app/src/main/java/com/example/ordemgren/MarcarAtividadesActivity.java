package com.example.ordemgren;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ordemgren.models.Jovem;
import com.example.ordemgren.models.Patrulha;
import java.util.List;

/**
 * Tela para marcar as atividades de um sábado:
 * - Presença individual, uniforme e atrasos
 * - Pontuação por patrulha (ex: posição em atividades)
 */
public class MarcarAtividadesActivity extends AppCompatActivity {

    private Spinner spinnerPatrulha;
    private LinearLayout layoutJovens;
    private List<Patrulha> patrulhas;
    private Patrulha patrulhaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcar_atividades);

        spinnerPatrulha = findViewById(R.id.spinnerPatrulha);
        layoutJovens = findViewById(R.id.layoutJovens);

        patrulhas = Utils.carregarPatrulhas(this);
        String[] nomesPatrulhas = new String[patrulhas.size()];
        for (int i = 0; i < patrulhas.size(); i++) {
            nomesPatrulhas[i] = patrulhas.get(i).getNome();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nomesPatrulhas);
        spinnerPatrulha.setAdapter(adapter);

        findViewById(R.id.btnSelecionarPatrulha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = spinnerPatrulha.getSelectedItemPosition();
                patrulhaSelecionada = patrulhas.get(pos);
                carregarJovens();
            }
        });

        findViewById(R.id.btnSalvarPontuacao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarPontuacao();
            }
        });
    }

    private void carregarJovens() {
        layoutJovens.removeAllViews();
        for (Jovem jovem : patrulhaSelecionada.getJovens()) {
            CheckBox cb = new CheckBox(this);
            cb.setText(jovem.getNome() + " - Presente e com uniforme?");
            layoutJovens.addView(cb);
        }
    }

    private void salvarPontuacao() {
        for (int i = 0; i < layoutJovens.getChildCount(); i++) {
            CheckBox cb = (CheckBox) layoutJovens.getChildAt(i);
            Jovem jovem = patrulhaSelecionada.getJovens().get(i);
            if (cb.isChecked()) {
                jovem.adicionarPontos(2); // Presença e uniforme = +2
            } else {
                jovem.adicionarPontos(-2); // Ausência ou sem uniforme = -2
            }
        }

        // Exemplo: adicionar 5 pontos por ter ficado em 1º lugar
        patrulhaSelecionada.adicionarPontosPatrulha(5);

        Utils.salvarPatrulhas(this, patrulhas);
        Toast.makeText(this, "Pontuação salva!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
