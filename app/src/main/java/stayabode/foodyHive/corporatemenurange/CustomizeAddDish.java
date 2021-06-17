package stayabode.foodyHive.corporatemenurange;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import stayabode.foodyHive.R;

public class CustomizeAddDish extends AppCompatActivity {


LinearLayout breakfast_liner_id,lunch_liner_id,dinner_liner_id;
Button breakfast_add,lunch_add,dinner_add;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customize_add_dish);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        breakfast_add=findViewById(R.id.breakfast_add);
        lunch_add=findViewById(R.id.lunch_add);
        dinner_add=findViewById(R.id.dinner_add);

        breakfast_liner_id=findViewById(R.id.breakfast_liner_id);
        lunch_liner_id=findViewById(R.id.lunch_liner_id);
        dinner_liner_id=findViewById(R.id.dinner_liner_id);

        breakfast_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breakfast_liner_id.setVisibility(View.VISIBLE);
            }
        });

        lunch_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lunch_liner_id.setVisibility(View.VISIBLE);
            }
        });

        dinner_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dinner_liner_id.setVisibility(View.VISIBLE);
            }
        });

    }


}
