package com.example.ordemgren;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ordemgren.models.Jovem;
import com.example.ordemgren.models.Patrulha;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class DetalhePatrulhaActivity extends AppCompatActivity {

    private TextView txtDetalhes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_patrulha);

        txtDetalhes = findViewById(R.id.txtDetalhes);

        int posicao = getIntent().getIntExtra("posicaoPatrulha", 0);
        List<Patrulha> patrulhas = Utils.carregarPatrulhas(this);
        Patrulha patrulha = patrulhas.get(posicao);

        StringBuilder detalhes = new StringBuilder();
        detalhes.append("Pontuação Total: ").append(patrulha.getPontosPatrulha()).append("\n\n");

        List<Jovem> jovens = patrulha.getJovens();

        detalhes.append("Top 3 Jovens com Mais Pontos:\n");
        Collections.sort(jovens, (a, b) -> Integer.compare(b.getPontos(), a.getPontos()));
        for (int i = 0; i < Math.min(3, jovens.size()); i++) {
            detalhes.append(jovens.get(i).getNome()).append(": ").append(jovens.get(i).getPontos()).append(" pts\n");
        }

        detalhes.append("\nTop 3 Jovens com Mais Pontos Negativos:\n");
        Collections.sort(jovens, Comparator.comparingInt(Jovem::getPontos));
        for (int i = 0; i < Math.min(3, jovens.size()); i++) {
            if (jovens.get(i).getPontos() < 0) {
                detalhes.append(jovens.get(i).getNome()).append(": ").append(jovens.get(i).getPontos()).append(" pts\n");
            }
        }

        txtDetalhes.setText(detalhes.toString());
    }
}
