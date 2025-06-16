package com.example.ordemgren;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ordemgren.models.Patrulha;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Exibe o ranking geral das patrulhas.
 * Ao clicar em uma patrulha, abre a tela de detalhes.
 */
public class RankingActivity extends AppCompatActivity {

    private ListView listRanking;
    private List<Patrulha> patrulhas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        listRanking = findViewById(R.id.listRanking);
        patrulhas = Utils.carregarPatrulhas(this);

        Collections.sort(patrulhas, new Comparator<Patrulha>() {
            @Override
            public int compare(Patrulha p1, Patrulha p2) {
                return Integer.compare(p2.getPontuacaoTotal(), p1.getPontuacaoTotal());
            }
        });

        String[] nomes = new String[patrulhas.size()];
        for (int i = 0; i < patrulhas.size(); i++) {
            nomes[i] = (i + 1) + "º - " + patrulhas.get(i).getNome() + " - " + patrulhas.get(i).getPontuacaoTotal() + " pts";
        }

        listRanking.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nomes));

        listRanking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RankingActivity.this, DetalhePatrulhaActivity.class);
                intent.putExtra("posicaoPatrulha", position);
                startActivity(intent);
            }
        });
    }
}
