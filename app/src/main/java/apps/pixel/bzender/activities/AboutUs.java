package apps.pixel.bzender.activities;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import apps.pixel.bzender.R;
import apps.pixel.bzender.Utills.Constant;

public class AboutUs extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Animatoo.animateInAndOut(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.changeLang(this, Constant.getLng(this));
        setContentView(R.layout.activity_about_us);

        AppCompatImageView mImgBack = findViewById(R.id.imageNavigationIcon);
        mImgBack.setOnClickListener(v -> {
            NavUtils.navigateUpFromSameTask(this);
            Animatoo.animateInAndOut(this);
        });
    }
}
