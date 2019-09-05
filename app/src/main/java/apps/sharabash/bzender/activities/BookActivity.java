package apps.sharabash.bzender.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import apps.sharabash.bzender.R;
import apps.sharabash.bzender.Utills.Constant;

public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.changeLang(this, Constant.getLng(this));
        setContentView(R.layout.activity_book);
    }
}
