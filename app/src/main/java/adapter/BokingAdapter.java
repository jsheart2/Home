package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.home.R;

import java.util.ArrayList;

import database.DataBoking;

public class BokingAdapter extends RecyclerView.Adapter<BokingAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<DataBoking> dataBokings;

    public BokingAdapter (Context cont, ArrayList<DataBoking> data){
        context     = cont;
        dataBokings = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nama.setText(dataBokings.get(position).getNama());
        holder.jumlah.setText(dataBokings.get(position).getJumlah());
        holder.price.setText(dataBokings.get(position).getPrice());
        holder.getNama.setText(dataBokings.get(position).getGetNama());
        holder.tglboking.setText(dataBokings.get(position).getTglboking());
        holder.menit.setText(dataBokings.get(position).getMenit());
    }

    @Override
    public int getItemCount() {
        return dataBokings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama, jumlah, price, getNama, tglboking, menit;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nama       = itemView.findViewById(R.id.txt_nama);
            jumlah     = itemView.findViewById(R.id.txt_jumlah);
            price      = itemView.findViewById(R.id.txt_price);
            getNama    = itemView.findViewById(R.id.txt_getNama);
            tglboking  = itemView.findViewById(R.id.txt_tglboking);
            menit      = itemView.findViewById(R.id.txt_menit);
        }
    }
}
