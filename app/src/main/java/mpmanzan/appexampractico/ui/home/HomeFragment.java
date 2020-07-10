package mpmanzan.appexampractico.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import mpmanzan.appexampractico.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    EditText txtConsulta;
    Button btnBuscar;
    ListView mListView;
    private List<ModeloLista> mListaProductos = new ArrayList<>();
    ListAdapter mListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //hook
        txtConsulta = (EditText) root.findViewById(R.id.txtbuscar);
        btnBuscar = (Button) root.findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(OnclickBuscar);
        mListView = (ListView) root.findViewById(R.id.ListVProductos);



//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        return root;
    }

    private Button.OnClickListener OnclickBuscar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Consulta
            classClientHttp clclienteHttp = new classClientHttp(getActivity().getApplicationContext());
            if (!TextUtils.isEmpty(txtConsulta.getText().toString().trim())) {
                String[] sJson = clclienteHttp.HttpBuscarProducto(txtConsulta.getText().toString().trim(), 1, 5);

                //Leer Json
//        try {
//            JSONObject jsonObject = new JSONObject(sJson);
//            iIDClienteCredere = Integer.parseInt(jsonObject.getString("iIdClienteCredere"));
//            sCURP = jsonObject.getString("sCurp");
//            sNSS = jsonObject.getString("sNSoSeguridadSocial");
//            sNombre = jsonObject.getString("sNombre");
//        } catch (JSONException Ex) {
//            Toast.makeText(this, Ex.getMessage(), Toast.LENGTH_LONG);
//        }
//        /*iIDClienteCredere = 4318298;
//        sCURP = "MOMA740705HMSRRL06";
//        sNSS = "15917524637";
//        sNombre = "ALFONSO MORALES MORA";*/
//
//        SharedPreferences shPreferencias = getSharedPreferences("DatosCte", Context.MODE_PRIVATE);
//        SharedPreferences.Editor ShEdit = shPreferencias.edit();
//        if (iIDClienteCredere > 0) {
//            ShEdit.putString("CURP", sCURP);
//            ShEdit.putString("NSS", sNSS);
//            ShEdit.putString("IdClienteCredere", String.valueOf(iIDClienteCredere));
//            ShEdit.putString("NombreCte", sNombre);
//            ShEdit.commit();
//            Resp = true;
//        } else {
//            Resp = false;
//        }
                mListaProductos.add(new ModeloLista("Computadora Hp", 3900.99, R.mipmap.ic_launcher));
                mListaProductos.add(new ModeloLista("Laptop Asus", 49000.50, R.mipmap.ic_launcher_round));
                mListaProductos.add(new ModeloLista("Computadora Acer", 19899.00, R.mipmap.ic_launcher));
                mListAdapter = new ListAdapter(getActivity().getApplicationContext(), R.layout.item_productos, mListaProductos);
                mListView.setAdapter(mListAdapter);

            }
        }
    };

}