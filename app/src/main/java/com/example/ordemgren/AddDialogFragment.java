package com.example.ordemgren;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.ordemgren.models.Jovem;
import com.example.ordemgren.models.Patrulha;
import java.util.List;

public class AddDialogFragment extends DialogFragment{

    private EditarPatrulhaActivity activity;
    private EditText editNome;
    private CheckBox checkMonitor, checkPatrulheiro;
    private Button btnSalvar, btnExcluir;
    private ImageView btnFechar;

    private Jovem jovem;
    private List<Patrulha> patrulhas;
    private Patrulha patrulhaSelecionada;

    public AddDialogFragment(Jovem jovem, List<Patrulha> patrulhas, Patrulha patrulhaSelecionada, EditarPatrulhaActivity activity) {
        this.jovem = jovem;
        this.patrulhas = patrulhas;
        this.patrulhaSelecionada = patrulhaSelecionada;
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diaolg_edit_jovem, container, false);

        editNome = view.findViewById(R.id.editNomeJovem);
        checkMonitor = view.findViewById(R.id.checkMonitor);
        checkPatrulheiro = view.findViewById(R.id.checkPatrulheiro);
        btnSalvar = view.findViewById(R.id.Salvar);
        btnExcluir = view.findViewById(R.id.Excluir);
        btnFechar = view.findViewById(R.id.Fechar);

        editNome.setText(jovem.getNome());
        checkMonitor.setChecked(jovem.getMonitoria());
        checkPatrulheiro.setChecked(!jovem.getMonitoria());

        checkMonitor.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) checkPatrulheiro.setChecked(false);
        });

        checkPatrulheiro.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) checkMonitor.setChecked(false);
        });

        btnSalvar.setOnClickListener(v -> {
            String novoNome = editNome.getText().toString();
            boolean novoMonitor = checkMonitor.isChecked();

            if (!novoNome.isEmpty()) {
                jovem.setNome(novoNome);
            }

            if (novoMonitor) {
                for (Jovem j : patrulhaSelecionada.getJovens()) {
                    j.setMonitor(false);
                }
            }

            jovem.setMonitor(novoMonitor);

            Utils.salvarPatrulhas(getContext(), patrulhas);
            activity.carregarJovens();
            Toast.makeText(getContext(), "Alterações salvas!", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        btnExcluir.setOnClickListener(v -> {
            patrulhaSelecionada.getJovens().remove(jovem);
            Utils.salvarPatrulhas(getContext(), patrulhas);
            activity.carregarJovens();
            Toast.makeText(getContext(), "Jovem excluído!", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        btnFechar.setOnClickListener(v -> dismiss());

        return view;
    }
}
