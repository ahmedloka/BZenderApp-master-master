package apps.sharabash.bzender.activities.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.Locale;

import apps.sharabash.bzender.R;
import apps.sharabash.bzender.Utills.Constant;
import apps.sharabash.bzender.activities.forget_password.ForgetPassword;
import apps.sharabash.bzender.activities.signUp.SignUp;

public class Login extends AppCompatActivity implements loginInterface {

    private static final String TAG = "LOGIN";

    private TextView userName;
    private TextView password;

    private LoginPresenter loginPresenter;
    private ScrollView mImgBg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constant.changeLang(this, Constant.getLng(this));
        setContentView(R.layout.activity_login);

        mImgBg = findViewById(R.id.img_bg);
        mImgBg.setOnTouchListener((v, event) -> {
            Constant.hideKeyboardFrom(Login.this , v);
            return true;
        });


        SharedPreferences mSharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        String language = mSharedPreferences.getString(Constant.language, Locale.getDefault().getDisplayLanguage());


        TextView sign_up = findViewById(R.id.mBtnSignUp);
        TextView forget_password = findViewById(R.id.forget_password);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        FrameLayout register = findViewById(R.id.register);

        loginPresenter = new LoginPresenter(this, this);


        sign_up.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignUp.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.pull_in_right);
            finish();
        });
        register.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignUp.class);
            startActivity(intent);
            Animatoo.animateSlideRight(this);
            //finish();
        });
        login.setOnClickListener(v -> {


            loginPresenter.validation(userName.getText().toString(), password.getText().toString());
            /*Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.pull_in_right);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();*/
        });
        forget_password.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
            startActivity(intent);
            Animatoo.animateSlideRight(this);
        });


        AppCompatImageView mImgOr = findViewById(R.id.img_or);
        if (language.equals("ar")) {
            mImgOr.setImageResource(R.drawable.or_ar);
        } else {
            mImgOr.setImageResource(R.drawable.or);
        }

    }

}
