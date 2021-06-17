package stayabode.foodyHive.corporatemenurange;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import stayabode.foodyHive.R;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.utils.FulllistItemdisplay;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CorporateOrderSummery extends AppCompatActivity {


Button subscribe_id;
LinearLayout day_expand_parent_id,day_expand_child_id,time_expand_parent_id,time_expand_child_id;
boolean day_expand_parent_id_check=false,time_expand_parent_id_check=false;
ImageView day_image_animation,time_image_animation;

    ScrollView addresses_scroll ;
    ListView navLV;
    TextView sub_total_id,total_id;
    double full_totalvalue=0;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    TextView day_id,time_id;
    TextView today_id,tomorrow_id;
    //String day_default_selection="Tomorrow";
    LinearLayout color_changer_today,color_changer_tommorrow;
    LinearLayout color_changer_time_one,color_changer_time_two,color_changer_time_three,color_changer_time_four;
    LinearLayout color_changer_time_one_dinner,color_changer_time_two_dinner,color_changer_time_three_dinner,color_changer_time_four_dinner;
    TextView l_time_one,l_time_two,l_time_three,l_time_four;
    TextView d_time_one,d_time_two,d_time_three,d_time_four;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
      //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.corporate_order_summery);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        day_image_animation=findViewById(R.id.day_image_animation);
        time_image_animation=findViewById(R.id.time_image_animation);

        sub_total_id=findViewById(R.id.sub_total_id);
        total_id=findViewById(R.id.total_id);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        day_id = findViewById(R.id.day_id);
        time_id =  findViewById(R.id.time_id);

        today_id =  findViewById(R.id.today_id);
        tomorrow_id =  findViewById(R.id.tomorrow_id);
        color_changer_today =  findViewById(R.id.color_changer_today);
        color_changer_tommorrow =  findViewById(R.id.color_changer_tommorrow);

        day_id.setText(Globaluse.today_tomo);
        color_changer_tommorrow.setBackgroundColor(Color.parseColor("#e54750"));
        tomorrow_id.setTextColor(Color.parseColor("#ffffff"));
        color_changer_today.setBackgroundColor(Color.parseColor("#ffffff"));
        today_id.setTextColor(Color.parseColor("#000000"));


        color_changer_time_one =  findViewById(R.id.color_changer_time_one);
        color_changer_time_two =  findViewById(R.id.color_changer_time_two);
        color_changer_time_three =  findViewById(R.id.color_changer_time_three);
        color_changer_time_four =  findViewById(R.id.color_changer_time_four);
        color_changer_time_one_dinner =  findViewById(R.id.color_changer_time_one_dinner);
        color_changer_time_two_dinner =  findViewById(R.id.color_changer_time_two_dinner);
        color_changer_time_three_dinner =  findViewById(R.id.color_changer_time_three_dinner);
        color_changer_time_four_dinner =  findViewById(R.id.color_changer_time_four_dinner);

        l_time_one =  findViewById(R.id.l_time_one);
        l_time_two =  findViewById(R.id.l_time_two);
        l_time_three =  findViewById(R.id.l_time_three);
        l_time_four =  findViewById(R.id.l_time_four);

        d_time_one =  findViewById(R.id.d_time_one);
        d_time_two =  findViewById(R.id.d_time_two);
        d_time_three =  findViewById(R.id.d_time_three);
        d_time_four =  findViewById(R.id.d_time_four);


        color_changer_time_three.setBackgroundColor(Color.parseColor("#e54750"));
        l_time_three.setTextColor(Color.parseColor("#ffffff"));
        time_id.setText(Globaluse.deliver_time);

        color_changer_time_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_changer_time_one.setBackgroundColor(Color.parseColor("#e54750"));
                l_time_one.setTextColor(Color.parseColor("#ffffff"));
                Globaluse.deliver_time="12.00";
                time_id.setText(Globaluse.deliver_time);
                Globaluse.lunch_dinner="LUNCH";

                color_changer_time_two.setBackgroundColor(Color.parseColor("#ffffff")); l_time_two.setTextColor(Color.parseColor("#000000"));
                color_changer_time_three.setBackgroundColor(Color.parseColor("#ffffff")); l_time_three.setTextColor(Color.parseColor("#000000"));
                color_changer_time_four.setBackgroundColor(Color.parseColor("#ffffff")); l_time_four.setTextColor(Color.parseColor("#000000"));
                color_changer_time_one_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_one.setTextColor(Color.parseColor("#000000"));
                color_changer_time_two_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_two.setTextColor(Color.parseColor("#000000"));
                color_changer_time_three_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_three.setTextColor(Color.parseColor("#000000"));
                color_changer_time_four_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_four.setTextColor(Color.parseColor("#000000"));




            }
        });

        color_changer_time_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_changer_time_two.setBackgroundColor(Color.parseColor("#e54750"));
                l_time_two.setTextColor(Color.parseColor("#ffffff"));
                Globaluse.deliver_time="12.30";
                time_id.setText(Globaluse.deliver_time);
                Globaluse.lunch_dinner="LUNCH";


                color_changer_time_one.setBackgroundColor(Color.parseColor("#ffffff")); l_time_one.setTextColor(Color.parseColor("#000000"));
                color_changer_time_three.setBackgroundColor(Color.parseColor("#ffffff")); l_time_three.setTextColor(Color.parseColor("#000000"));
                color_changer_time_four.setBackgroundColor(Color.parseColor("#ffffff")); l_time_four.setTextColor(Color.parseColor("#000000"));
                color_changer_time_one_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_one.setTextColor(Color.parseColor("#000000"));
                color_changer_time_two_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_two.setTextColor(Color.parseColor("#000000"));
                color_changer_time_three_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_three.setTextColor(Color.parseColor("#000000"));
                color_changer_time_four_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_four.setTextColor(Color.parseColor("#000000"));



            }
        });


        color_changer_time_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_changer_time_three.setBackgroundColor(Color.parseColor("#e54750"));
                l_time_three.setTextColor(Color.parseColor("#ffffff"));
                Globaluse.deliver_time="1.00";
                time_id.setText(Globaluse.deliver_time);
                Globaluse.lunch_dinner="LUNCH";

                color_changer_time_one.setBackgroundColor(Color.parseColor("#ffffff")); l_time_one.setTextColor(Color.parseColor("#000000"));
                color_changer_time_two.setBackgroundColor(Color.parseColor("#ffffff")); l_time_two.setTextColor(Color.parseColor("#000000"));
                color_changer_time_four.setBackgroundColor(Color.parseColor("#ffffff")); l_time_four.setTextColor(Color.parseColor("#000000"));
                color_changer_time_one_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_one.setTextColor(Color.parseColor("#000000"));
                color_changer_time_two_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_two.setTextColor(Color.parseColor("#000000"));
                color_changer_time_three_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_three.setTextColor(Color.parseColor("#000000"));
                color_changer_time_four_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_four.setTextColor(Color.parseColor("#000000"));


            }
        });
        color_changer_time_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_changer_time_four.setBackgroundColor(Color.parseColor("#e54750"));
                l_time_four.setTextColor(Color.parseColor("#ffffff"));
                Globaluse.deliver_time="1.30";
                time_id.setText(Globaluse.deliver_time);
                Globaluse.lunch_dinner="LUNCH";


                color_changer_time_one.setBackgroundColor(Color.parseColor("#ffffff")); l_time_one.setTextColor(Color.parseColor("#000000"));
                color_changer_time_two.setBackgroundColor(Color.parseColor("#ffffff")); l_time_two.setTextColor(Color.parseColor("#000000"));
                color_changer_time_three.setBackgroundColor(Color.parseColor("#ffffff")); l_time_three.setTextColor(Color.parseColor("#000000"));
                color_changer_time_one_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_one.setTextColor(Color.parseColor("#000000"));
                color_changer_time_two_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_two.setTextColor(Color.parseColor("#000000"));
                color_changer_time_three_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_three.setTextColor(Color.parseColor("#000000"));
                color_changer_time_four_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_four.setTextColor(Color.parseColor("#000000"));



            }
        });



        color_changer_time_one_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_changer_time_one_dinner.setBackgroundColor(Color.parseColor("#e54750"));
                d_time_one.setTextColor(Color.parseColor("#ffffff"));
                Globaluse.deliver_time="8.00";
                time_id.setText(Globaluse.deliver_time);
                Globaluse.lunch_dinner="DINNER";


                color_changer_time_one.setBackgroundColor(Color.parseColor("#ffffff")); l_time_one.setTextColor(Color.parseColor("#000000"));
                color_changer_time_two.setBackgroundColor(Color.parseColor("#ffffff")); l_time_two.setTextColor(Color.parseColor("#000000"));
                color_changer_time_three.setBackgroundColor(Color.parseColor("#ffffff")); l_time_three.setTextColor(Color.parseColor("#000000"));
                color_changer_time_four.setBackgroundColor(Color.parseColor("#ffffff")); l_time_four.setTextColor(Color.parseColor("#000000"));
                color_changer_time_two_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_two.setTextColor(Color.parseColor("#000000"));
                color_changer_time_three_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_three.setTextColor(Color.parseColor("#000000"));
                color_changer_time_four_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_four.setTextColor(Color.parseColor("#000000"));


            }
        });


        color_changer_time_two_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_changer_time_two_dinner.setBackgroundColor(Color.parseColor("#e54750"));
                d_time_two.setTextColor(Color.parseColor("#ffffff"));
                Globaluse.deliver_time="8.30";
                time_id.setText(Globaluse.deliver_time);
                Globaluse.lunch_dinner="DINNER";


                color_changer_time_one.setBackgroundColor(Color.parseColor("#ffffff")); l_time_one.setTextColor(Color.parseColor("#000000"));
                color_changer_time_two.setBackgroundColor(Color.parseColor("#ffffff")); l_time_two.setTextColor(Color.parseColor("#000000"));
                color_changer_time_three.setBackgroundColor(Color.parseColor("#ffffff")); l_time_three.setTextColor(Color.parseColor("#000000"));
                color_changer_time_four.setBackgroundColor(Color.parseColor("#ffffff")); l_time_four.setTextColor(Color.parseColor("#000000"));
                color_changer_time_one_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_one.setTextColor(Color.parseColor("#000000"));
                color_changer_time_three_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_three.setTextColor(Color.parseColor("#000000"));
                color_changer_time_four_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_four.setTextColor(Color.parseColor("#000000"));


            }
        });

        color_changer_time_three_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_changer_time_three_dinner.setBackgroundColor(Color.parseColor("#e54750"));
                d_time_three.setTextColor(Color.parseColor("#ffffff"));
                Globaluse.deliver_time="9.00";
                time_id.setText(Globaluse.deliver_time);
                Globaluse.lunch_dinner="DINNER";


                color_changer_time_one.setBackgroundColor(Color.parseColor("#ffffff")); l_time_one.setTextColor(Color.parseColor("#000000"));
                color_changer_time_two.setBackgroundColor(Color.parseColor("#ffffff")); l_time_two.setTextColor(Color.parseColor("#000000"));
                color_changer_time_three.setBackgroundColor(Color.parseColor("#ffffff")); l_time_three.setTextColor(Color.parseColor("#000000"));
                color_changer_time_four.setBackgroundColor(Color.parseColor("#ffffff")); l_time_four.setTextColor(Color.parseColor("#000000"));
                color_changer_time_one_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_one.setTextColor(Color.parseColor("#000000"));
                color_changer_time_two_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_two.setTextColor(Color.parseColor("#000000"));
                color_changer_time_four_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_four.setTextColor(Color.parseColor("#000000"));


            }
        });

        color_changer_time_four_dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color_changer_time_four_dinner.setBackgroundColor(Color.parseColor("#e54750"));
                d_time_four.setTextColor(Color.parseColor("#ffffff"));
                Globaluse.deliver_time="9.30";
                time_id.setText(Globaluse.deliver_time);
                Globaluse.lunch_dinner="DINNER";


                color_changer_time_one.setBackgroundColor(Color.parseColor("#ffffff")); l_time_one.setTextColor(Color.parseColor("#000000"));
                color_changer_time_two.setBackgroundColor(Color.parseColor("#ffffff")); l_time_two.setTextColor(Color.parseColor("#000000"));
                color_changer_time_three.setBackgroundColor(Color.parseColor("#ffffff")); l_time_three.setTextColor(Color.parseColor("#000000"));
                color_changer_time_four.setBackgroundColor(Color.parseColor("#ffffff")); l_time_four.setTextColor(Color.parseColor("#000000"));
                color_changer_time_one_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_one.setTextColor(Color.parseColor("#000000"));
                color_changer_time_two_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_two.setTextColor(Color.parseColor("#000000"));
                color_changer_time_three_dinner.setBackgroundColor(Color.parseColor("#ffffff")); d_time_three.setTextColor(Color.parseColor("#000000"));


            }
        });



        today_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day_id.setText("Today");
                Globaluse.today_tomo="Today";
                color_changer_today.setBackgroundColor(Color.parseColor("#e54750"));
                today_id.setTextColor(Color.parseColor("#ffffff"));
                color_changer_tommorrow.setBackgroundColor(Color.parseColor("#ffffff"));
                tomorrow_id.setTextColor(Color.parseColor("#000000"));
            }
        });

        tomorrow_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day_id.setText("Tomorrow");
                Globaluse.today_tomo="Tomorrow";
                color_changer_tommorrow.setBackgroundColor(Color.parseColor("#e54750"));
                tomorrow_id.setTextColor(Color.parseColor("#ffffff"));
                color_changer_today.setBackgroundColor(Color.parseColor("#ffffff"));
                today_id.setTextColor(Color.parseColor("#000000"));
            }
        });



        subscribe_id=findViewById(R.id.subscribe_id);
        subscribe_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                //Toast.makeText(CorporateOrderSummery.this, radioButton.getText(), Toast.LENGTH_SHORT).show();

                Globaluse.only_Monthly_week=""+radioButton.getText();
                //Toast.makeText(CorporateOrderSummery.this, Globaluse.only_Monthly_week+" "+Globaluse.today_tomo+"    "+Globaluse.lunch_dinner+" "+Globaluse.deliver_time, Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(CorporateOrderSummery.this, CorporatebussinessInformation.class);
                startActivity(intent);
            }
        });


        addresses_scroll =findViewById(R.id.addresses_scroll);

        day_expand_parent_id=findViewById(R.id.day_expand_parent_id);
        day_expand_child_id=findViewById(R.id.day_expand_child_id);
        time_expand_parent_id=findViewById(R.id.time_expand_parent_id);
        time_expand_child_id=findViewById(R.id.time_expand_child_id);

        day_expand_parent_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!day_expand_parent_id_check)
                {

                    ObjectAnimator flip = ObjectAnimator.ofFloat(day_image_animation, "rotationY", 0f, 900f); // or rotationX
                    flip.setDuration(1200);
                    flip.start();

                    day_expand_parent_id_check=true;
                    day_expand_child_id.setVisibility(View.VISIBLE);
                    addresses_scroll.post(new Runnable() {
                        @Override
                        public void run() {
                            addresses_scroll.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                }
                else
                {

                    ObjectAnimator flip = ObjectAnimator.ofFloat(day_image_animation, "rotationY", 0f, 900f); // or rotationX
                    flip.setDuration(1200);
                    flip.start();

                    day_expand_parent_id_check=false;
                    day_expand_child_id.setVisibility(View.GONE);

                }

            }
        });

        time_expand_parent_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!time_expand_parent_id_check)
                {
                    ObjectAnimator flip = ObjectAnimator.ofFloat(time_image_animation, "rotationY", 0f, 900f); // or rotationX
                    flip.setDuration(1200);
                    flip.start();

                    time_expand_parent_id_check=true;
                    time_expand_child_id.setVisibility(View.VISIBLE);
                    addresses_scroll.post(new Runnable() {
                        @Override
                        public void run() {
                            addresses_scroll.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                }
                else
                {
                    ObjectAnimator flip = ObjectAnimator.ofFloat(time_image_animation, "rotationY", 0f, 900f); // or rotationX
                    flip.setDuration(1200);
                    flip.start();

                    time_expand_parent_id_check=false;
                    time_expand_child_id.setVisibility(View.GONE);

                }

            }
        });



            for (int k=0;k<Globaluse.corporatedishlistnew.size();k++)
            {

                try {
                    JSONObject jsonObject = new JSONObject(Globaluse.corporatedishlistnew.get(k));
                    double noofUnits= Integer.parseInt(jsonObject.getString("noOfUnits"));
                    double cost= Integer.parseInt(jsonObject.getString("cost"));
                    double totalvalue=(cost * noofUnits);
                    full_totalvalue=full_totalvalue+totalvalue;
                }catch (JSONException err){
                    Log.d("Error", err.toString());
                }

            }


        sub_total_id.setText(""+full_totalvalue);
        total_id.setText(""+full_totalvalue);

        navLV = findViewById(R.id.navLV);

        CustomAdapter adapter = new CustomAdapter(this,  Globaluse.corporatedishlistnew);
        navLV.setAdapter(adapter);
        FulllistItemdisplay.setListViewHeightBasedOnItems(navLV);


    }


    class CustomAdapter extends BaseAdapter {

        Context context;
        ArrayList<String> items;

        public CustomAdapter(Context context, ArrayList<String> items) {
            this.context = context;
            this.items = items;
        }


        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = LayoutInflater.from(context).inflate(R.layout.corporate_expand_dish_list_row_, null);

            ImageView deleterecord = v.findViewById(R.id.deleterecord);
            TextView dish_id = v.findViewById(R.id.dish_id);
            TextView dish_multipleprice = v.findViewById(R.id.dish_multipleprice);


            //expListView.setSelection(1);
            //notifyDataSetChanged();
            try {
                JSONObject jsonObject = new JSONObject(items.get(i));

                dish_id.setText(jsonObject.getString("noOfUnits")+"X "+jsonObject.getString("dishItemName"));

                double noofUnits= Integer.parseInt(jsonObject.getString("noOfUnits"));
                double cost= Integer.parseInt(jsonObject.getString("cost"));

                double totalvalue=(cost * noofUnits);

                dish_multipleprice.setText(jsonObject.getString("noOfUnits")+"X "+jsonObject.getString("dishItemName"));

                if(totalvalue==0.0)
                {
                    dish_multipleprice.setText("Rs. "+" TBD ");
                }else
                {
                    dish_multipleprice.setText("Rs. "+totalvalue);
                }



            }catch (JSONException err){
                Log.d("Error", err.toString());
            }


            deleterecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    JSONObject jsonObject_old = null;
                    try {
                        jsonObject_old = new JSONObject(items.get(i));
                        String dishItemName=jsonObject_old.getString("dishItemName");
                        String cost=jsonObject_old.getString("cost");


                        Globaluse.corporatedishlistnew=new  ArrayList<String>();
                        for(int c = 0; c< Globaluse._listDataHeader.size(); c++)
                        {
                            for (int t=0;t<Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).size();t++)
                            {
                                final String childText =String.valueOf(Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).get(t));
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(childText);

                                    if(jsonObject.getString("dishName").equals(dishItemName))
                                    {
                                        //if(jsonObject.getString("saleAmount").equals(dishItemName))
                                        //{
                                            try {
                                                jsonObject.put("buttondisplay", false);
                                                jsonObject.put("quantity", 0);
                                                updatejson(c,t,String.valueOf(jsonObject));

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        //}

                                    }




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }







                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                }
            });

            //text.setText(items.get(i));
            //event handling code can be done here
            return v;
        }
    }




    public void updatejson(int groupPosition, int childPosititon,String updatejsondat) {

        List<String> dishlistdata = new ArrayList<String>();
        for (int m = 0; m < Globaluse._listDataChild.get(Globaluse._listDataHeader.get(groupPosition)).size(); m++) {

            if(childPosititon==m)
            {
                dishlistdata.add(updatejsondat);
            }else
            {
                dishlistdata.add(String.valueOf(Globaluse._listDataChild.get(Globaluse._listDataHeader.get(groupPosition)).get(m)));
            }

        }
        Globaluse._listDataChild.put(Globaluse._listDataHeader.get(groupPosition),dishlistdata);

//        Globaluse._listDataHeader=new ArrayList<String>();
//        Globaluse._listDataChild=new HashMap<String, List<String>>();
        Globaluse._listDataHeader=new ArrayList<String>(Globaluse._listDataHeader);
        Globaluse._listDataChild=new HashMap<String, List<String>>(Globaluse._listDataChild);


        Globaluse.corporatedishlistnew=new  ArrayList<String>();
        for(int c = 0; c< Globaluse._listDataHeader.size(); c++)
        {
            for (int t=0;t<Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).size();t++)
            {
                final String childText =String.valueOf(Globaluse._listDataChild.get(Globaluse._listDataHeader.get(c)).get(t));
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(childText);
                    if(jsonObject.getBoolean("buttondisplay"))
                    {
                        JSONObject dishDetail = new JSONObject();
                        try {
                            dishDetail.put("dishItemName", jsonObject.getString("dishName"));
                            dishDetail.put("description", jsonObject.getString("dishDescription"));
                            dishDetail.put("cost", jsonObject.getString("saleAmount"));
                            dishDetail.put("noOfUnits", jsonObject.getString("quantity"));
                            dishDetail.put("taxValue", 0);
                            dishDetail.put("packingCharges", 0);
                            dishDetail.put("deliveryCharges", 0);

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Globaluse.corporatedishlistnew.add(String.valueOf(dishDetail));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }



        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);

    }




//    public void corporateshared()
//    {
//        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
//
//// The value will be default as empty string because for
//// the very first time when the app is opened, there is nothing to show
//        String addcart_share = sh.getString("addcart", "");
//        String companyname_share = sh.getString("companyname", "");
//        String name_share = sh.getString("name", "");
//        String email_share = sh.getString("email", "");
//        String phone_share = sh.getString("phone", "");
//        String fulladdress_share = sh.getString("fulladdress", "");
//        String city_share = sh.getString("city", "");
//        String postalcode_share = sh.getString("postalcode", "");
//
//        if(addcart_share.equals("yes"))
//        {
//            finish();
//            overridePendingTransition( 0, 0);
//            startActivity(getIntent());
//            overridePendingTransition( 0, 0);
//        }
//    }

    @Override
    public void onRestart() {
        super.onRestart();
        SharedPreferences sh = getSharedPreferences("corporateShare", MODE_APPEND);
        String addcart_share = sh.getString("addcart", "");
        if(addcart_share.equals("yes"))
        {
            finish();
            overridePendingTransition( 0, 0);
            startActivity(getIntent());
            overridePendingTransition( 0, 0);
        }
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
    }
}
