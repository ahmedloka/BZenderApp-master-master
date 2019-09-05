package apps.sharabash.bzender.dialog;

import android.content.Context;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class CustomCallenderDialog extends DatePickerDialog {

    public static DatePickerDialog getInstance(Context context) {


        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 2);


        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        return DatePickerDialog.newInstance((DatePickerDialog.OnDateSetListener) context, year, month, day);
    }


}

