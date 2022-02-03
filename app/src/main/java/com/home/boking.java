package com.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import database.DbBoking;

public class boking extends AppCompatActivity {

    private FirebaseDatabase rootnode;
    private DatabaseReference reference;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat;

    private TextView txtJumlah, txtHarga, txtGetNama, txttgl_boking, txt_menit;
    private EditText edtNama;
    private CheckBox cbx_studio, cbx_recording, cbx_stik;
    private int jumlah, pertambahan, total=0, harga=0, studio, recording, stik;
    private String nama, statusStudio = "tidak", statusStik= "tidak", statusRecording= "tidak";
    private boolean iscbx_studio, isCbx_recording, isCbx_Stik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boking);

        txtJumlah     = findViewById(R.id.txt_jumlah);
        txtHarga      = findViewById(R.id.txt_price);
        edtNama       = findViewById(R.id.txt_nama);
        txtGetNama    = findViewById(R.id.txt_getNama);
        txttgl_boking = findViewById(R.id.txt_tglboking);
        txt_menit     = findViewById(R.id.txt_menit);
        cbx_studio    = findViewById(R.id.cbx_studio);
        cbx_recording = findViewById(R.id.cbx_recording);
        cbx_stik      = findViewById(R.id.cbx_stik);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        txttgl_boking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                txttgl_boking.setText(dateFormat.format(newDate.getTime()));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void Studio(){
        if (cbx_studio.isChecked()){
            iscbx_studio=true;
            statusStudio="Studio";
            studio=50000;
        }else{
            iscbx_studio=false;
            statusStudio="";
            studio=0;
        }
        if (cbx_stik.isChecked()){
            isCbx_Stik=true;
            statusStik="Sewa Stik Drum";
            stik=10000;
        }else{
            isCbx_Stik=false;
            statusStik="";
            stik=0;
        }
        if (cbx_recording.isChecked()){
            isCbx_recording=true;
            statusRecording="Recording";
            recording=100000;
        }else{
            isCbx_recording=false;
            statusRecording="";
            recording=0;
        }
    }

    public void order(View view) {
        display(harga);
        simpanData();
    }

    private void simpanData() {

        rootnode  = FirebaseDatabase.getInstance("https://home-7e2a4.firebaseio.com");
        reference = rootnode.getReference("Boking User");

        Toast.makeText(boking.this, "Data Sudah Berhasil Di input !! Terimakasih", Toast.LENGTH_LONG).show();

        // values //

        String Nama    = edtNama.getText().toString();
        String Jumlah  = txtJumlah.getText().toString();
        String Harga   = txtHarga.getText().toString();
        String GetName = txtGetNama.getText().toString();
        String Tanggal = txttgl_boking.getText().toString();
        String Menit   = txt_menit.getText().toString();

        DbBoking dbBoking = new DbBoking(Nama, Jumlah, Harga, GetName, Tanggal, Menit);

        reference.child(Nama).setValue(dbBoking);

    }
    public void display(int harga) {

        Studio();
        total = pertambahan * harga;
        if (iscbx_studio){
            total += (pertambahan * studio);
        }
        if (isCbx_recording) {
            total += (pertambahan * recording);
        }
        if (isCbx_Stik) {
            total += (pertambahan * stik);
        }
        Log.i("harga :", " " +total);
        nama = edtNama.getText().toString();
        txtGetNama.setText(statusStudio +","+ statusStik +"," + statusRecording );
        txtHarga.setText("Harga : Rp." +total );
    }

    //button boking perjam
    public void tambah(View view) {
        pertambahan = pertambahan + 1;
        txtJumlah.setText("" + pertambahan);
    }

    public void kurang(View view) {
        pertambahan = pertambahan - 1 ;
        txtJumlah.setText("" + pertambahan);
    }

    public void cancel(View view) {
        startActivity(new Intent(getApplicationContext(), Home.class));
    }

    public void hapus(View view) {
        txtGetNama.setText(" ");
        txtHarga.setText(" ");
        Toast.makeText(getApplicationContext(), "Data Berhasil Dihapus", Toast.LENGTH_LONG).show();
    }

    //button jam main
    public void btnmin(View view) {
        jumlah = jumlah - 1;
        txt_menit.setText("" + jumlah);
    }

    public void btnplus(View view) {
        jumlah = jumlah + 1;
        txt_menit.setText("" + jumlah);
    }


}
