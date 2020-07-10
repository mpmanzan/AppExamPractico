package mpmanzan.appexampractico.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import mpmanzan.appexampractico.R;

public class ListAdapter extends ArrayAdapter<ModeloLista> {

    private List<ModeloLista> mList;
    private Context mcontext;
    int resLayout;

    public ListAdapter(@NonNull Context context, int resource, @NonNull List<ModeloLista> objects) {
        super(context, resource, objects);
        this.mList = objects;
        mcontext = context;
        resLayout = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View mview = convertView;

        if (mview == null) {
            mview = LayoutInflater.from(mcontext).inflate(resLayout, null);
        }
        ModeloLista modeloLista = mList.get(position);
        ImageView imageView = mview.findViewById(R.id.ImgView);
        imageView.setImageResource(modeloLista.getImagen());

        TextView textProducto = mview.findViewById(R.id.txtProducto);
        textProducto.setText(modeloLista.getProducto());

        TextView textPrecio= mview.findViewById(R.id.txtPrecio);
        textPrecio.setText(String.valueOf(modeloLista.getPrecio()));

        return mview;
    }
}
