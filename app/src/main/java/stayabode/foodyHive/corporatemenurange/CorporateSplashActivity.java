package stayabode.foodyHive.corporatemenurange;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import stayabode.foodyHive.R;
import stayabode.foodyHive.constants.Globaluse;

public class CorporateSplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_corporate_splash);


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                Globaluse.frommenu=" ";
                Intent intent = new Intent(CorporateSplashActivity.this, CorporateLoginActivity.class);
                startActivity(intent);
                finish();




            }
        }, 3000);

    }
}
