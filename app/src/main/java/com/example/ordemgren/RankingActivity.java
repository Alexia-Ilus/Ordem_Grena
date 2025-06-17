package com.example.ordemgren;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.ordemgren.models.Patrulha;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankingActivity extends AppCompatActivity {

    private ListView listRanking;
    private TextView txtSemPatrulhas;
    private List<Patrulha> patrulhas;
    private LinearLayout menuLateral;
    private ImageView iconeMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        listRanking = findViewById(R.id.listRanking);
        txtSemPatrulhas = findViewById(R.id.txtSemPatrulhas);
        menuLateral = findViewById(R.id.menuLateral);
        iconeMenu = findViewById(R.id.iconeMenu);

        carregarRanking();

        iconeMenu.setOnClickListener(v -> menuLateral.setVisibility(View.VISIBLE));

        findViewById(R.id.btnFecharMenu).setOnClickListener(v -> menuLateral.setVisibility(View.GONE));

        findViewById(R.id.btnAbrirCadastro).setOnClickListener(v -> {
            startActivity(new Intent(this, CadastroPatrulhaActivity.class));
            menuLateral.setVisibility(View.GONE);
        });

        findViewById(R.id.btnAbrirEditar).setOnClickListener(v -> {
            startActivity(new Intent(this, EditarPatrulhaActivity.class));
            menuLateral.setVisibility(View.GONE);
        });

        findViewById(R.id.btnAbrirFeito).setOnClickListener(v -> {
            startActivity(new Intent(this, FeitoPatrulhaActivity.class));
            menuLateral.setVisibility(View.GONE);
        });
    }

    private void carregarRanking() {
        patrulhas = Utils.carregarPatrulhas(this);

        if (patrulhas.isEmpty()) {
            txtSemPatrulhas.setVisibility(View.VISIBLE);
            listRanking.setVisibility(View.GONE);
        } else {
            txtSemPatrulhas.setVisibility(View.GONE);
            listRanking.setVisibility(View.VISIBLE);

            Collections.sort(patrulhas, (p1, p2) -> Integer.compare(p2.getPontosPatrulha(), p1.getPontosPatrulha()));

            String[] nomes = new String[patrulhas.size()];
            for (int i = 0; i < patrulhas.size(); i++) {
                nomes[i] = (i + 1) + "º - " + patrulhas.get(i).getNome() + " - " + patrulhas.get(i).getPontosPatrulha() + " pts";
            }

            listRanking.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nomes));

            listRanking.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent(this, DetalhePatrulhaActivity.class);
                intent.putExtra("posicaoPatrulha", position);
                startActivity(intent);
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarRanking();
    }
}
