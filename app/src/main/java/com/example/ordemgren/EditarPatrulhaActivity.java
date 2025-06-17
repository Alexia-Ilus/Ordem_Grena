package com.example.ordemgren;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Typeface;
import com.example.ordemgren.models.Jovem;
import com.example.ordemgren.models.Patrulha;
import java.util.ArrayList;
import java.util.List;

public class EditarPatrulhaActivity extends AppCompatActivity {
    private Spinner spinnerPatrulha;
    private LinearLayout layoutJovens;
    private List<Patrulha> patrulhas;
    private Patrulha patrulhaSelecionada;
    private Button adicionarJovem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_patrulha);

        spinnerPatrulha = findViewById(R.id.spinnerPatrulhaEditar);
        layoutJovens = findViewById(R.id.LayoutJovens);
        patrulhas = Utils.carregarPatrulhas(this);
        adicionarJovem = findViewById(R.id.adicionarJovem);

        String[] nomesPatrulhas = new String[patrulhas.size()];
        for (int i = 0; i < patrulhas.size(); i++) nomesPatrulhas[i] = patrulhas.get(i).getNome();
        spinnerPatrulha.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nomesPatrulhas));

        findViewById(R.id.btnSelecionarEditar).setOnClickListener(v -> carregarJovens());
        findViewById(R.id.adicionarJovem).setOnClickListener(v -> adicionarJovem());
        findViewById(R.id.btnExcluirPatrulha).setOnClickListener(v -> excluirPatrulha());
    }

    void carregarJovens() {
        adicionarJovem.setVisibility(View.VISIBLE);
        layoutJovens.removeAllViews();

        patrulhaSelecionada = patrulhas.get(spinnerPatrulha.getSelectedItemPosition());

        List<Jovem> listaOrdenada = new ArrayList<>();

        for (Jovem jovem : patrulhaSelecionada.getJovens()) {
            if (jovem.getMonitoria()) {
                listaOrdenada.add(jovem);
            }
        }

        for (Jovem jovem : patrulhaSelecionada.getJovens()) {
            if (!jovem.getMonitoria()) {
                listaOrdenada.add(jovem);
            }
        }

        for (Jovem jovem : listaOrdenada) {
            TextView nomeJovem = new TextView(this);
            nomeJovem.setText(jovem.getNome());
            nomeJovem.setTextSize(18);

            if (jovem.getMonitoria()) {
                nomeJovem.setTypeface(null, Typeface.BOLD);
            }

            nomeJovem.setOnClickListener(v -> {
                AddDialogFragment dialog = new AddDialogFragment(jovem, patrulhas, patrulhaSelecionada, EditarPatrulhaActivity.this);
                dialog.show(getSupportFragmentManager(), "EditarJovem");
            });

            layoutJovens.addView(nomeJovem);
        }
    }

    private void adicionarJovem() {
        AddJovemDialogFragment dialog = new AddJovemDialogFragment(patrulhaSelecionada, patrulhas, EditarPatrulhaActivity.this);
        dialog.show(getSupportFragmentManager(), "AddNovoJovem");
    }

    private void excluirPatrulha() {
        patrulhaSelecionada = patrulhas.get(spinnerPatrulha.getSelectedItemPosition());
        patrulhas.remove(patrulhaSelecionada);
        Utils.salvarPatrulhas(this, patrulhas);
        finish();
    }
}