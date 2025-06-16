package com.example.ordemgren;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.ordemgren.models.Jovem;
import com.example.ordemgren.models.Patrulha;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária para salvar e carregar patrulhas e jovens
 * usando SharedPreferences com serialização JSON.
 */
public class Utils {

    private static final String PREFS_NAME = "OrdemGrenaPrefs";
    private static final String KEY_PATRULHAS = "patrulhas";

    /**
     * Salva a lista completa de patrulhas no SharedPreferences.
     */
    public static void salvarPatrulhas(Context context, List<Patrulha> patrulhas) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        JSONArray jsonArray = new JSONArray();

        try {
            for (Patrulha patrulha : patrulhas) {
                JSONObject patrulhaJson = new JSONObject();
                patrulhaJson.put("nome", patrulha.getNome());
                patrulhaJson.put("pontosPatrulha", patrulha.getPontosPatrulha());

                JSONArray jovensJson = new JSONArray();
                for (Jovem jovem : patrulha.getJovens()) {
                    JSONObject jovemJson = new JSONObject();
                    jovemJson.put("nome", jovem.getNome());
                    jovemJson.put("pontos", jovem.getPontos());
                    jovensJson.put(jovemJson);
                }

                patrulhaJson.put("jovens", jovensJson);
                jsonArray.put(patrulhaJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        editor.putString(KEY_PATRULHAS, jsonArray.toString());
        editor.apply();
    }

    /**
     * Recupera a lista de patrulhas salvas no SharedPreferences.
     */
    public static List<Patrulha> carregarPatrulhas(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_PATRULHAS, "[]");

        List<Patrulha> patrulhas = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject patrulhaJson = jsonArray.getJSONObject(i);
                Patrulha patrulha = new Patrulha(patrulhaJson.getString("nome"));
                patrulha.adicionarPontosPatrulha(patrulhaJson.getInt("pontosPatrulha"));

                JSONArray jovensJson = patrulhaJson.getJSONArray("jovens");
                for (int j = 0; j < jovensJson.length(); j++) {
                    JSONObject jovemJson = jovensJson.getJSONObject(j);
                    Jovem jovem = new Jovem(jovemJson.getString("nome"));
                    jovem.adicionarPontos(jovemJson.getInt("pontos"));
                    patrulha.adicionarJovem(jovem);
                }

                patrulhas.add(patrulha);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return patrulhas;
    }
}
