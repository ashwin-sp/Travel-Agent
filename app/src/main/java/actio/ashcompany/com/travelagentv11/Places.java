package actio.ashcompany.com.travelagentv11;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;



public class Places extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_places, container, false);

        final Spinner spinner = rootView.findViewById(R.id.spinner);

        final Button fly = rootView.findViewById(R.id.fly);

        fly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selection = spinner.getSelectedItem().toString();
                System.out.println("item ==> "+ selection );
                if (selection.equals("Delhi")) {
                    Intent i = new Intent(getActivity(), Delhi.class);
                    getActivity().startActivity(i);
                }

                else if (selection.equals("Chennai")) {
                    Intent i = new Intent(getActivity(), Chennai.class);
                    getActivity().startActivity(i);
                }

                else if (selection.equals("Bangalore")) {
                    Intent i = new Intent(getActivity(), Bangalore.class);
                    getActivity().startActivity(i);
                }
                else
                {
                    fly.setVisibility(View.VISIBLE);
                }
            }
        });

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();
                if(!item.equals("Select a place")) {
                   // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                    fly.setVisibility(View.VISIBLE);
                }
                else
                {
                    fly.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Select a place");
        categories.add("Delhi");
        categories.add("Chennai");
        categories.add("Bangalore");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        return rootView;
    }

}

