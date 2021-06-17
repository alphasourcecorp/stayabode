package stayabode.foodyHive.corporatemenurange;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import stayabode.foodyHive.R;

import java.util.ArrayList;

public class Inventory extends AppCompatActivity {


LinearLayout breakfast_liner_id,lunch_liner_id,dinner_liner_id;
Button breakfast_add,lunch_add,dinner_add;
    Spinner kitchenSpinner,inventorySpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }








        kitchenSpinner = findViewById(R.id.kitchenSpinner);
        ArrayList<String> spinnerList = new ArrayList<>();
        spinnerList.add("Select Kitchen");
        spinnerList.add("Hsr");
        spinnerList.add("Indira Nagar");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Inventory.this, R.layout.support_simple_spinner_dropdown_item, spinnerList);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        kitchenSpinner.setAdapter(arrayAdapter);
        kitchenSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int   pos = kitchenSpinner.getSelectedItemPosition();
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        inventorySpinner = findViewById(R.id.inventorySpinner);
        ArrayList<String> inventoryspinnerList = new ArrayList<>();
        inventoryspinnerList.add("Select Inventory");
        inventoryspinnerList.add("Inventory Add");
        inventoryspinnerList.add("Inventory Add Bulk");
        inventoryspinnerList.add("Inventory Expense");
        inventoryspinnerList.add("Inventory Expense Bulk");
        inventoryspinnerList.add("Inventory Report");

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(Inventory.this, R.layout.support_simple_spinner_dropdown_item, inventoryspinnerList);
        arrayAdapter2.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        inventorySpinner.setAdapter(arrayAdapter2);
        inventorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int   pos = inventorySpinner.getSelectedItemPosition();
            }
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

    }


}
