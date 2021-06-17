package stayabode.foodyHive.commonactivities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.consumers.ConsumerMainActivity;
import stayabode.foodyHive.activities.consumers.IntroScreenActivity;
import stayabode.foodyHive.utils.SaveSharedPreference;

public class SplashActivity extends AppCompatActivity {

    TextView splashHeader;
    Typeface fontBold;
    Typeface fontRegular;
    Typeface poppinsFont;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        fontBold = Typeface.createFromAsset(getAssets(), "fonts/Nunito-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getAssets(), "fonts/Nunito-Regular.ttf");
        poppinsFont = Typeface.createFromAsset(getAssets(), "fonts/Poppins-SemiBold.ttf");


        splashHeader = findViewById(R.id.splashHeader);
        splashHeader.setTypeface(fontBold);
        SaveSharedPreference.alreadySavedPopUp(SplashActivity.this,false,"");
        //startIntroScreen();
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
//                 This method will be executed once the timer is over
//                Intent i = new Intent(SplashActivity.this, ChefsMainActivity.class);
//                i.putExtra("Role","Admin");
//                startActivity(i);
//                finish();
                if(SaveSharedPreference.getLoggedInUserAddress(SplashActivity.this).equals(""))
                {
                    Intent intent = new Intent(SplashActivity.this, IntroScreenActivity.class);
                    intent.putExtra("Role","Admin");
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(SplashActivity.this, ConsumerMainActivity.class);
                    intent.putExtra("Role","Admin");
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);

    }
}
