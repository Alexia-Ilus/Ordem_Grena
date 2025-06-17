package com.example.ordemgren;

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

public class AddJovemDialogFragment extends DialogFragment {

    private EditText editNomeNovoJovem;
    private Button btnSalvar;

    private Button btnCancelar;
    private Patrulha patrulhaSelecionada;
    private List<Patrulha> patrulhas;
    private EditarPatrulhaActivity activity;

    public AddJovemDialogFragment(Patrulha patrulhaSelecionada, List<Patrulha> patrulhas, EditarPatrulhaActivity activity) {
        this.patrulhaSelecionada = patrulhaSelecionada;
        this.patrulhas = patrulhas;
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diaolg_add_jovem, container, false);

        editNomeNovoJovem = view.findViewById(R.id.editNomeNovoJovem);
        btnSalvar = view.findViewById(R.id.btnSalvarNovoJovem);
        btnCancelar = view.findViewById(R.id.Cancelar);

        btnSalvar.setOnClickListener(v -> {
            String nomeNovo = editNomeNovoJovem.getText().toString();
            if (!nomeNovo.isEmpty()) {
                patrulhaSelecionada.adicionarJovem(new Jovem(nomeNovo));
                Utils.salvarPatrulhas(getContext(), patrulhas);
                activity.carregarJovens();
                Toast.makeText(getContext(), "Jovem adicionado!", Toast.LENGTH_SHORT).show();
                dismiss();
            } else {
                Toast.makeText(getContext(), "Digite um nome", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancelar.setOnClickListener(v -> dismiss());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) getDialog().setCanceledOnTouchOutside(false);
    }
}
