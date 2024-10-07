package com.example.municipalityapp;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class PopulationChangeDataRetriever {

    public ArrayList<PopulationChangeData> getPopulationChangeData(Context context, String municipality) {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode areas = null;

        try {
            areas = objectMapper.readTree(new URL("https://statfin.stat.fi/PxWeb/api/v1/en/StatFin/synt/statfin_synt_pxt_12dy.px"));
        } catch (IOException e) {
            Log.e("PopulationChangeDataR", "Error while retrieving data", e);
        }
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

        assert areas != null;
        for (JsonNode node : areas.get("variables").get(1).get("values")) {
        values.add(node.asText());
        }
        for (JsonNode node : areas.get("variables").get(1).get("valueTexts")) {
            keys.add(node.asText());
        }
        HashMap<String, String> municipalityCodes= new HashMap<>();

        for (int i = 0; i < keys.size(); i++) {
            municipalityCodes.put(keys.get(i), values.get(i));
        }
        String code;
        code = municipalityCodes.get(municipality);

        try {
            URL url = new URL("https://pxdata.stat.fi:443/PxWeb/api/v1/fi/StatFin/synt/statfin_synt_pxt_12dy.px");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            JsonNode jsonInputString = objectMapper.readTree(context.getResources().openRawResource(R.raw.popchange_query));
            ((ObjectNode) jsonInputString.get("query").get(0).get("selection")).putArray("values").add(code);

            byte[] input = objectMapper.writeValueAsBytes(jsonInputString);
            OutputStream outputStream = con.getOutputStream();
            outputStream.write(input, 0, input.length);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line.trim());
            }
            Log.d("PopChangeDataRetriever", "API response: " + response);

            JsonNode populationChangeData = objectMapper.readTree(response.toString());

            ArrayList<String> years = new ArrayList<>();
            ArrayList<String> change = new ArrayList<>();

            for (JsonNode node : populationChangeData.get("dimension").get("Vuosi").get("category").get("label")) {
                years.add(node.asText());
            }
            for (JsonNode node : populationChangeData.get("value")) {
                change.add(node.asText());
            }

            ArrayList<PopulationChangeData> popChangeData = new ArrayList<>();

            if (!years.isEmpty() && !change.isEmpty()) {
                int latest = years.size() -1;
                popChangeData.add(new PopulationChangeData(
                        Integer.parseInt(years.get(latest)),
                        Integer.parseInt(change.get(latest))
                ));
            }
            return popChangeData;
        } catch (IOException e) {
            Log.e("PopChangeDataR", "error");
        }
        return null;
    }
}
