package com.example.german.appfirebase;

/**
 * Created by German on 4/12/2016.
 */

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText txtFecha;
    public DateDialog(View view){
        txtFecha=(EditText)view;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendario = Calendar.getInstance();
        int year = calendario.get(Calendar.YEAR);
        int month = calendario.get(Calendar.MONTH);
        int day = calendario.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

        String fecha = day+"/"+(month+1)+"/"+year;
        txtFecha.setText(fecha);

    }
}
