package stayabode.foodyHive.corporatemenurange;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import stayabode.foodyHive.R;

public class ColiveSplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_corporate_splash);


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {


                SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
                String isAlreadyLogin = sh.getString("isAlreadyLogin", "");
                String isAdmin = sh.getString("isAdmin", "");
                if (isAlreadyLogin.equalsIgnoreCase("yes")) {

                    if(isAdmin.equals("true"))
                    {
                        Intent intent = new Intent(ColiveSplashActivity.this, CorporateMenuWithNavigatioinAdmin.class);
                        startActivity(intent);
                        finishAffinity();
                    }else
                    {
                        Intent intent = new Intent(ColiveSplashActivity.this, CorporateMenuWithNavigatioin.class);
                        startActivity(intent);
                        finishAffinity();
                    }




                } else
                {
                    Intent intent = new Intent(ColiveSplashActivity.this, ColiveAndStayabodeHomeActivity.class);
                    startActivity(intent);

                }


            }
        }, 3000);

    }
}
