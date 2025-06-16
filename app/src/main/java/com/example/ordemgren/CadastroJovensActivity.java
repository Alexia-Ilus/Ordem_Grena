package com.example.ordemgren;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ordemgren.models.Jovem;
import com.example.ordemgren.models.Patrulha;
import java.util.ArrayList;
import java.util.List;

/**
 * Tela onde o usuário adiciona de 4 a 8 jovens na patrulha.
 * Depois salva a patrulha com os jovens no armazenamento.
 */
public class CadastroJovensActivity extends AppCompatActivity {

    private LinearLayout layoutJovens;
    private List<EditText> camposJovens = new ArrayList<>();
    private String nomePatrulha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_jovens);

        nomePatrulha = getIntent().getStringExtra("nomePatrulha");
        layoutJovens = findViewById(R.id.layoutJovens);

        // Cria campos para até 8 jovens
        for (int i = 0; i < 8; i++) {
            EditText campo = new EditText(this);
            campo.setHint("Nome do Jovem " + (i + 1));
            layoutJovens.addView(campo);
            camposJovens.add(campo);
        }

        findViewById(R.id.btnSalvarPatrulha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarPatrulha();
            }
        });
    }

    private void salvarPatrulha() {
        Patrulha patrulha = new Patrulha(nomePatrulha);
        int preenchidos = 0;

        for (EditText campo : camposJovens) {
            String nome = campo.getText().toString();
            if (!nome.isEmpty()) {
                patrulha.adicionarJovem(new Jovem(nome));
                preenchidos++;
            }
        }

        if (preenchidos < 4) {
            Toast.makeText(this, "Adicione pelo menos 4 jovens", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Patrulha> patrulhas = Utils.carregarPatrulhas(this);
        patrulhas.add(patrulha);
        Utils.salvarPatrulhas(this, patrulhas);

        Toast.makeText(this, "Patrulha salva!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
