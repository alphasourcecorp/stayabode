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

public class CorporateLoginActivity extends AppCompatActivity {


Button login_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.corporate_order_login);

        login_id=findViewById(R.id.login_id);
        login_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CorporateLoginActivity.this, CorporateMenuWithNavigatioin.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
