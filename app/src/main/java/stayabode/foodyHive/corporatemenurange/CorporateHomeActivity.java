package stayabode.foodyHive.corporatemenurange;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import stayabode.foodyHive.R;
import stayabode.foodyHive.commonactivities.SplashActivity;
import stayabode.foodyHive.constants.Globaluse;

public class CorporateHomeActivity extends AppCompatActivity {


Button foodyhive_id,foodyhive_corporate_id;
    private boolean exit = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.home_for_consumer_and_corporate);
        Globaluse.fromscheduleEdit=" ";
        foodyhive_id=findViewById(R.id.foodyhive_id);
        foodyhive_corporate_id=findViewById(R.id.foodyhive_corporate_id);

        foodyhive_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Intent intent = new Intent(CorporateHomeActivity.this, SplashActivity.class);
                startActivity(intent);


            }
        });

        foodyhive_corporate_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
                String isAlreadyLogin = sh.getString("isAlreadyLogin", "");
                String isAdmin = sh.getString("isAdmin", "");
                if (isAlreadyLogin.equalsIgnoreCase("yes")) {

                    if(isAdmin.equals("true"))
                    {
                        Intent intent = new Intent(CorporateHomeActivity.this, CorporateMenuWithNavigatioinAdmin.class);
                        startActivity(intent);
                        finishAffinity();
                    }else
                    {
                        Intent intent = new Intent(CorporateHomeActivity.this, CorporateMenuWithNavigatioin.class);
                        startActivity(intent);
                        finishAffinity();
                    }




                } else
                {
                    Intent intent = new Intent(CorporateHomeActivity.this, CorporateSplashActivity.class);
                    startActivity(intent);

                }



            }
        });

    }



    public void onBackPressed() {
        if (exit)
            CorporateHomeActivity.this.finish();
        else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
}
