package com.example.municipalityapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.municipalityapp.EmploymentRateData;
import com.example.municipalityapp.EmploymentRateDataRetriever;
import com.example.municipalityapp.MunicipalityData;
import com.example.municipalityapp.MunicipalityDataRetriever;
import com.example.municipalityapp.PopulationChangeData;
import com.example.municipalityapp.PopulationChangeDataRetriever;
import com.example.municipalityapp.R;
import com.example.municipalityapp.SelfSufficiencyData;
import com.example.municipalityapp.SelfSufficiencyDataRetriever;
import com.example.municipalityapp.WeatherData;
import com.example.municipalityapp.WeatherDataRetriever;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InfoFragment extends Fragment {

    private EditText editTextLocation;
    private TextView showMunicipality;
    private TextView txtPopulationData;
    private TextView txtPopChange;
    private TextView txtJobData;
    private TextView txtEmpRate;
    private TextView txtWeather;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        editTextLocation = view.findViewById(R.id.inputMunicipality);
        txtPopulationData = view.findViewById(R.id.populationData);
        showMunicipality = view.findViewById(R.id.printMunicipality);
        txtPopChange = view.findViewById(R.id.popChange);
        txtJobData = view.findViewById(R.id.jobData);
        txtEmpRate = view.findViewById(R.id.employmentRate);
        txtWeather = view.findViewById(R.id.weatherData);



        Button getDataBtn = view.findViewById(R.id.getDataButton);
        getDataBtn.setOnClickListener(v -> {
            String location = editTextLocation.getText().toString();

            if (location.isEmpty()) {
                Toast.makeText(getContext(), "Syötä kunnan nimi pliis", Toast.LENGTH_SHORT).show();
                return;
            }

            MunicipalityDataRetriever mr = new MunicipalityDataRetriever();
            PopulationChangeDataRetriever pcdr = new PopulationChangeDataRetriever();
            SelfSufficiencyDataRetriever ssdr = new SelfSufficiencyDataRetriever();
            EmploymentRateDataRetriever erdr = new EmploymentRateDataRetriever();
            WeatherDataRetriever wdr = new WeatherDataRetriever();

            Context context = getContext();

            ExecutorService service = Executors.newSingleThreadExecutor();
            service.execute(() -> {
                ArrayList<MunicipalityData> populationData = mr.getData(context, location);
                ArrayList<PopulationChangeData> changeData = pcdr.getPopulationChangeData(context, location);
                ArrayList<SelfSufficiencyData> selfSufficiencyData = ssdr.getSelfSufficiencyData(context, location);
                ArrayList<EmploymentRateData> employmentRateData = erdr.getEmploymentRateData(context, location);
                WeatherData weatherData = wdr.getCurrentWeather(location);

                if (populationData == null || populationData.isEmpty()) {
                    return;
                }
                if (changeData == null || changeData.isEmpty()) {
                    return;
                }
                if (selfSufficiencyData == null || selfSufficiencyData.isEmpty()) {
                    return;
                }
                if (employmentRateData == null || employmentRateData.isEmpty()) {
                    return;
                }

                requireActivity().runOnUiThread(() -> {
                    StringBuilder a = new StringBuilder();
                    StringBuilder b = new StringBuilder();
                    StringBuilder c = new StringBuilder();
                    StringBuilder d = new StringBuilder();

                    showMunicipality.setText(location);

                    for (MunicipalityData data : populationData) {
                        a.append("Väkiluku vuonna ").append(data.getYear()).append(": ").append(data.getPopulation()).append("\n");
                    }
                    txtPopulationData.setText(a.toString());

                    for (PopulationChangeData data1 : changeData) {
                        b.append("Väestonmuutos vuonna ").append(data1.getYear()).append(": ").append(data1.getPopulationChange()).append("\n");
                    }
                    txtPopChange.setText(b.toString());

                    for (SelfSufficiencyData data2 : selfSufficiencyData) {
                        c.append("Työpaikkaomavaraisuus: ").append(data2.getSelfSufficiency()).append("\n");
                    }
                    txtJobData.setText(c.toString());

                    for (EmploymentRateData data3 : employmentRateData) {
                        d.append("Työllisyysprosentti: ").append(data3.getEmploymentRate()).append("\n");
                    }
                    txtEmpRate.setText(d.toString());

                    txtWeather.setText(
                            String.format("%s\nSää: %s (%s)\nLämpötila: %s C\nTuulennopeus: %s m/s\n",
                                    weatherData.getName(), weatherData.getMain(), weatherData.getDescription(),
                                    weatherData.getTemperature(), weatherData.getWindSpeed())
                    );


                });
                Log.d("This", "got data");
                editTextLocation.setText("");
                // to make sure data retrieving has been done, prints to log, then clears input field
            });
        });
        return view;
    }
}