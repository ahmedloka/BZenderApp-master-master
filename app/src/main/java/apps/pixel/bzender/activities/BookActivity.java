package apps.pixel.bzender.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.Constant;

public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.changeLang(this, Constant.getLng(this));
        setContentView(R.layout.activity_book);
    }
}
