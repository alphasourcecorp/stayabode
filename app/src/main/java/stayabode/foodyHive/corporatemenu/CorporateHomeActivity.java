package stayabode.foodyHive.corporatemenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import stayabode.foodyHive.R;
import stayabode.foodyHive.commonactivities.SplashActivity;

public class CorporateHomeActivity extends AppCompatActivity {


Button foodyhive_id,foodyhive_corporate_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.home_for_consumer_and_corporate);

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

                Intent intent = new Intent(CorporateHomeActivity.this, CorporateSplashActivity.class);
                startActivity(intent);
            }
        });

    }
}
