package stayabode.foodyHive.corporatemenurange;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import stayabode.applandeo.materialcalendarview.CalendarView;
import stayabode.applandeo.materialcalendarview.EventDay;

import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;

import stayabode.foodyHive.adapters.consumers.CorporateExpandableListAfterSchdule;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.utils.FulllistItemdisplay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CorporateAfterSchedule extends AppCompatActivity {




    static CalendarView calendarView;
    static List<EventDay> events = new ArrayList<>();
     int incre = 0;
     LinearLayout startdate_button_id,enddate_button_id;
     static TextView start_date_id;
    static TextView end_date_id;
    static Calendar min = Calendar.getInstance();
    static Calendar max = Calendar.getInstance();
    static int start_day=0;
    static int start_month=0;
    static int  start_year=0;
    static int end_day=0;
    static int end_month=0;
    static int  end_year=0;
  //  int End_date_MILLISECOND_calender=0;
   // int click_date_MILLISECOND_calender=0;

    static ArrayList<HashMap<String, String>> arraylist;
    Button suubmit_id;

    ListView navLV;
    TextView cartTotalCountText;
    ExpandableListView expListView;

    CorporateExpandableListAfterSchdule listAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.corporate_after_schedule);

        navLV = findViewById(R.id.navLV);
        navLV.setSmoothScrollbarEnabled(true);

//        // get the listview
//        expListView = (ExpandableListView) findViewById(R.id.lvExp);
//        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v,
//                                        int groupPosition, long id) {
//
//                return false;
//            }
//        });
//
//        // Listview Group expanded listener
//        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//
//
//            }
//        });
//
//        // Listview Group collasped listener
//        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//
//            }
//        });
//
//        // Listview on child click listener
//        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                // TODO Auto-generated method stub
//
//
//                return false;
//            }
//        });
//
//
////        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
////        {
////            public boolean onGroupClick(ExpandableListView arg0, View itemView, int itemPosition, long itemId)
////            {
////                expListView.expandGroup(itemPosition);
////                return true;
////            }
////        });
//
//        listAdapter = new CorporateExpandableListAfterSchdule(this,expListView, Globaluse._listDataHeader, Globaluse._listDataChild);
//        expListView.setAdapter(listAdapter);


        CustomAdapter2 adapter = new CustomAdapter2(CorporateAfterSchedule.this,  Globaluse.schedule_data_array);
        navLV.setAdapter(adapter);
        FulllistItemdisplay.setListViewHeightBasedOnItems(navLV);
    }



    class CustomAdapter2 extends BaseAdapter {

        Context context;
        ArrayList<ArrayList<HashMap<String, String>>> items;

        public CustomAdapter2(Context context, ArrayList<ArrayList<HashMap<String, String>>> items) {
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
            View v = LayoutInflater.from(context).inflate(R.layout.corporate_schedule_item_new, null);

            TextView s_date_id = v.findViewById(R.id.s_date_id);
            TextView s_month_id = v.findViewById(R.id.s_month_id);
            TextView s_year_id = v.findViewById(R.id.s_year_id);
            TextView breakfast_id = v.findViewById(R.id.breakfast_id);
            TextView lunch_id = v.findViewById(R.id.lunch_id);
            TextView dinner_id = v.findViewById(R.id.dinner_id);
            ImageView edit_id = v.findViewById(R.id.edit_id);
            ImageView delete_id = v.findViewById(R.id.delete_id);
            TextView e_date_id = v.findViewById(R.id.e_date_id);
            TextView e_month_id = v.findViewById(R.id.e_month_id);
            TextView e_year_id = v.findViewById(R.id.e_year_id);
            RecyclerView dish_recycler_view = v.findViewById(R.id.dish_recycler_view);
            RecyclerView excludes_recycler_view = v.findViewById(R.id.excludes_recycler_view);
            LinearLayout exclude_linear_id = v.findViewById(R.id.exclude_linear_id);
            TextView total_id = v.findViewById(R.id.total_id);

            // ArrayList<Arraylist> horizontalList;
             CustomAdapterRecycleDish horizontalAdapter;

//            TextView dish_id = v.findViewById(R.id.dish_id);
//            ImageView img_id = v.findViewById(R.id.img_id);
            ArrayList<String> listdata = new ArrayList<String>();
            ArrayList<HashMap<String, String>> schedule_data = new ArrayList<HashMap<String, String>>();
//            try {
               // JSONObject jsonObject = new JSONObject(String.valueOf(items.get(i)));
            double dishtotal=0.0;
            Globaluse.corporatedishlistnewEdit=new ArrayList<String>();
                for(int dishitem=0;dishitem<(items.get(i)).size();dishitem++)
                {
                       HashMap<String, String> itr = new HashMap<String, String>();
                         itr=items.get(i).get(dishitem);
                        schedule_data.add(itr);


//                    HashMap<String, String> map = new HashMap<String, String>();
//                    map.put("id", "FH"+k);
//                    map.put("dishItemName", jsonObject.getString("dishItemName"));
//                    map.put("description", jsonObject.getString("description"));
//                    map.put("cost", jsonObject.getString("cost"));
//                    map.put("noOfUnits", jsonObject.getString("noOfUnits"));
//                    map.put("dishImage", jsonObject.getString("dishImage"));
//                    map.put("taxValue", jsonObject.getString("taxValue"));
//                    map.put("packingCharges", jsonObject.getString("packingCharges"));
//                    map.put("deliveryCharges", jsonObject.getString("deliveryCharges"));
//                    map.put("buttondisplay", jsonObject.getString("buttondisplay"));
//                    map.put("quantity", jsonObject.getString("quantity"));
//                    map.put("checkStatus", String.valueOf(jsonObject.getBoolean("checkStatus")));
//                    map.put("checkStatus_schedule", String.valueOf(jsonObject.getBoolean("checkStatus_schedule")));
//                    map.put("startDate", Sdate);
//                    map.put("endDate", Edate);
//                    map.put("deliveryType", deliveryType);//1-lunch,2-dinner,3-both
//                    map.put("breakfast",""+breakfast_chk_id.isChecked());
//                    map.put("lunch", ""+lunch_chk_id.isChecked());
//                    map.put("dinner",""+dinner_chk_id.isChecked());
//                    map.put("noNeedDeliver", String.valueOf(no_need_deliver_that_day));
//



                    JSONObject dishDetail = new JSONObject();
                    try {
                        dishDetail.put("dishItemName", items.get(i).get(dishitem).get("dishItemName"));
                        dishDetail.put("description", items.get(i).get(dishitem).get("description"));
                        dishDetail.put("cost", items.get(i).get(dishitem).get("cost"));
                        dishDetail.put("noOfUnits", items.get(i).get(dishitem).get("noOfUnits"));
                        dishDetail.put("taxValue", items.get(i).get(dishitem).get("taxValue"));
                        dishDetail.put("packingCharges", items.get(i).get(dishitem).get("packingCharges"));
                        dishDetail.put("deliveryCharges", items.get(i).get(dishitem).get("deliveryCharges"));
                        dishDetail.put("buttondisplay", items.get(i).get(dishitem).get("buttondisplay"));
                        dishDetail.put("quantity", items.get(i).get(dishitem).get("quantity"));
                        dishDetail.put("checkStatus", items.get(i).get(dishitem).get("checkStatus"));
                        dishDetail.put("checkStatus_schedule", items.get(i).get(dishitem).get("checkStatus_schedule"));
                        dishDetail.put("dishImage", items.get(i).get(dishitem).get("dishImage"));

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Globaluse.corporatedishlistnewEdit.add(String.valueOf(dishDetail));



                    double noOfUnits= Double.parseDouble(items.get(i).get(dishitem).get("noOfUnits"));
                    double cost= Double.parseDouble(items.get(i).get(dishitem).get("cost"));
                    double totalval=((noOfUnits)*(cost));
                    dishtotal+=totalval;

                        if(dishitem==0)
                        {

                            String string = (items.get(i).get(dishitem).get("startDate"));
                            String[] parts = string.split("/");
                            String part1 = parts[0]; // 004
                            String part2 = parts[1];
                            String part3 = parts[2];
                            String input_date=string;
                            SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
                            Date dt1= null;
                            try {
                                dt1 = format1.parse(input_date);
                                DateFormat format2=new SimpleDateFormat("MMM");
                                String finalDay=format2.format(dt1);
                                s_month_id.setText(finalDay);
                                s_date_id.setText(part1);
                                s_year_id.setText(part3);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            String string1 = (items.get(i).get(dishitem).get("endDate"));
                            String[] parts1 = string1.split("/");
                            String part11 = parts1[0]; // 004
                            String part21 = parts1[1];
                            String part31= parts1[2];
                            String input_date1=string1;
                            SimpleDateFormat format11=new SimpleDateFormat("dd/MM/yyyy");
                            Date dt11= null;
                            try {
                                dt11 = format11.parse(input_date1);
                                DateFormat format2=new SimpleDateFormat("MMM");
                                String finalDay=format2.format(dt11);
                                e_month_id.setText(finalDay);
                                e_date_id.setText(part11);
                                e_year_id.setText(part31);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            if(items.get(i).get(dishitem).get("breakfast").equals("true"))
                            {
                                breakfast_id.setVisibility(View.VISIBLE);
                            }
                            if(items.get(i).get(dishitem).get("lunch").equals("true"))
                            {
                                lunch_id.setVisibility(View.VISIBLE);
                            }
                            if(items.get(i).get(dishitem).get("dinner").equals("true"))
                            {
                                dinner_id.setVisibility(View.VISIBLE);
                            }


                            JSONArray jArray = null;
                            try {
                                jArray = new JSONArray(items.get(i).get(dishitem).get("noNeedDeliver"));
                                if (jArray != null) {
                                    for (int l=0;l<jArray.length();l++){
                                        listdata.add(jArray.getString(l));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                           // listdata=items.get(i).get(dishitem).get("noNeedDeliver");
                            // JSONArray jArray = (JSONArray)jsonObject;
//                            if (items.get(i).get(dishitem).get("dinner") != null) {

//                                for (int k=0;k<items.get(i).get(dishitem).get("noNeedDeliver").length();k++){
////                                    try {
////
////                                     //   listdata.add(no_need_deliver_that_day.getString(k));
////
////                                    } catch (JSONException e) {
////                                        e.printStackTrace();
////                                    }
//                                }



                           // }

                        }


                }

            total_id.setText(""+dishtotal);

                if(listdata.isEmpty())
                {
                    exclude_linear_id.setVisibility(View.GONE);
                }else
                {
                    exclude_linear_id.setVisibility(View.VISIBLE);
                }


            CustomAdapterRecycle horizontalAdapter2;
            horizontalAdapter2=new CustomAdapterRecycle(listdata);
            LinearLayoutManager horizontalLayoutManagaer2 = new LinearLayoutManager(CorporateAfterSchedule.this, LinearLayoutManager.HORIZONTAL, false);
            excludes_recycler_view.setLayoutManager(horizontalLayoutManagaer2);
            excludes_recycler_view.setAdapter(horizontalAdapter2);

            excludes_recycler_view.setNestedScrollingEnabled(false);
           // FulllistItemdisplay.setListViewHeightBasedOnItems(excludes_recycler_view);

            horizontalAdapter=new CustomAdapterRecycleDish(schedule_data);
            LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(CorporateAfterSchedule.this, LinearLayoutManager.VERTICAL, false);
            dish_recycler_view.setLayoutManager(horizontalLayoutManagaer);
            dish_recycler_view.setAdapter(horizontalAdapter);
            dish_recycler_view.setNestedScrollingEnabled(false);

//                Glide.with(CorporateAfterSchedule.this).load(items.get(i).get("dishImage")).into(img_id);
//                dish_multipleprice.setText("Rs. "+jsonObject.getString("cost"));
//                dish_id.setText(items.get(i).get("noOfUnits")+" X "+jsonObject.getString("dishItemName"));

//            }catch (JSONException err){
//                Log.d("Error", err.toString());
//            }



            delete_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Globaluse.schedule_data_array
                    Globaluse.schedule_data_array.remove(items.get(i));
                    notifyDataSetChanged();
//                    CustomAdapter2 adapter = new CustomAdapter2(CorporateAfterSchedule.this,  Globaluse.schedule_data_array);
//                    navLV.setAdapter(adapter);
//                    FulllistItemdisplay.setListViewHeightBasedOnItems(navLV);

                }
            });



            edit_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    Globaluse.corporatedishlistnewEdit=new ArrayList<String>();
                    for(int dishitem=0;dishitem<(items.get(i)).size();dishitem++) {


//                    HashMap<String, String> map = new HashMap<String, String>();
//                    map.put("id", "FH"+k);
//                    map.put("dishItemName", jsonObject.getString("dishItemName"));
//                    map.put("description", jsonObject.getString("description"));
//                    map.put("cost", jsonObject.getString("cost"));
//                    map.put("noOfUnits", jsonObject.getString("noOfUnits"));
//                    map.put("dishImage", jsonObject.getString("dishImage"));
//                    map.put("taxValue", jsonObject.getString("taxValue"));
//                    map.put("packingCharges", jsonObject.getString("packingCharges"));
//                    map.put("deliveryCharges", jsonObject.getString("deliveryCharges"));
//                    map.put("buttondisplay", jsonObject.getString("buttondisplay"));
//                    map.put("quantity", jsonObject.getString("quantity"));
//                    map.put("checkStatus", String.valueOf(jsonObject.getBoolean("checkStatus")));
//                    map.put("checkStatus_schedule", String.valueOf(jsonObject.getBoolean("checkStatus_schedule")));
//                    map.put("startDate", Sdate);
//                    map.put("endDate", Edate);
//                    map.put("deliveryType", deliveryType);//1-lunch,2-dinner,3-both
//                    map.put("breakfast",""+breakfast_chk_id.isChecked());
//                    map.put("lunch", ""+lunch_chk_id.isChecked());
//                    map.put("dinner",""+dinner_chk_id.isChecked());
//                    map.put("noNeedDeliver", String.valueOf(no_need_deliver_that_day));
//


                        JSONObject dishDetail = new JSONObject();
                        try {
                            dishDetail.put("dishItemName", items.get(i).get(dishitem).get("dishItemName"));
                            dishDetail.put("description", items.get(i).get(dishitem).get("description"));
                            dishDetail.put("cost", items.get(i).get(dishitem).get("cost"));
                            dishDetail.put("noOfUnits", items.get(i).get(dishitem).get("noOfUnits"));
                            dishDetail.put("taxValue", items.get(i).get(dishitem).get("taxValue"));
                            dishDetail.put("packingCharges", items.get(i).get(dishitem).get("packingCharges"));
                            dishDetail.put("deliveryCharges", items.get(i).get(dishitem).get("deliveryCharges"));
                            dishDetail.put("buttondisplay", items.get(i).get(dishitem).get("buttondisplay"));
                            dishDetail.put("quantity", items.get(i).get(dishitem).get("quantity"));
                            dishDetail.put("checkStatus", items.get(i).get(dishitem).get("checkStatus"));
                            dishDetail.put("checkStatus_schedule", items.get(i).get(dishitem).get("checkStatus_schedule"));
                            dishDetail.put("dishImage", items.get(i).get(dishitem).get("dishImage"));

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Globaluse.corporatedishlistnewEdit.add(String.valueOf(dishDetail));
                    }



                    Intent intent = new Intent(CorporateAfterSchedule.this, CorporateScheduleEdit.class);
                    startActivity(intent);

                }
            });



            return v;
        }
    }






    public class CustomAdapterRecycleDish extends RecyclerView.Adapter<CustomAdapterRecycleDish.MyViewHolder> {

        private ArrayList<HashMap<String, String>> dataSet;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView dish_id,dish_price,dish_multipleprice;
            ImageView img_id;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.dish_id = (TextView) itemView.findViewById(R.id.dish_id);
                this.dish_price = (TextView) itemView.findViewById(R.id.dish_price);
                this.dish_multipleprice = (TextView) itemView.findViewById(R.id.dish_multipleprice);
                //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
                this.img_id = (ImageView) itemView.findViewById(R.id.img_id);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                    }
                });
            }
        }

        public CustomAdapterRecycleDish(ArrayList<HashMap<String, String>> data) {
            this.dataSet = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.after_schedule_my_dish_item, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

            TextView dish_id = holder.dish_id;
            TextView dish_price = holder.dish_price;
            TextView dish_multipleprice = holder.dish_multipleprice;
            ImageView img_id = holder.img_id;

            dish_id.setText(dataSet.get(listPosition).get("noOfUnits")+" X "+dataSet.get(listPosition).get("dishItemName"));
            dish_price.setText("Rs. "+dataSet.get(listPosition).get("cost")+" /-");
            double noOfUnits= Double.parseDouble(dataSet.get(listPosition).get("noOfUnits"));
            double cost= Double.parseDouble(dataSet.get(listPosition).get("cost"));
            double totalval=((noOfUnits)*(cost));

            dish_multipleprice.setText(""+totalval);

            Glide.with(CorporateAfterSchedule.this).load(dataSet.get(listPosition).get("dishImage")).into(img_id);

            //textViewVersion.setText(dataSet.get(listPosition).getVersion());
            //imageView.setImageResource(dataSet.get(listPosition).getImage());
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    }




    class CustomAdapterRecycle extends RecyclerView.Adapter<CustomAdapterRecycle.MyViewHolder> {

        private ArrayList<String> dataSet;

        public  class MyViewHolder extends RecyclerView.ViewHolder {

            TextView month_id,day_id,year_id;
            public MyViewHolder(View itemView) {
                super(itemView);
                this.month_id = (TextView) itemView.findViewById(R.id.month_id);
                this.day_id = (TextView) itemView.findViewById(R.id.day_id);
                this.year_id = (TextView) itemView.findViewById(R.id.year_id);
                //this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
//                this.imageViewIcon = (ImageView) itemView.findViewById(R.id.image);
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        if (getPosition()==0)
//                        {
//                            Toast.makeText(v.getContext(), " On CLick one", Toast.LENGTH_SHORT).show();
//
//                        } if (getPosition()==1)
//                    {
//                        Toast.makeText(v.getContext(), " On CLick Two", Toast.LENGTH_SHORT).show();
//
//                    } if (getPosition()==2)
//                    {
//                        Toast.makeText(v.getContext(), " On CLick Three", Toast.LENGTH_SHORT).show();
//
//                    } if (getPosition()==3)
//                    {
//                        Toast.makeText(v.getContext(), " On CLick Fore", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    }
//                });
            }
        }

        public CustomAdapterRecycle(ArrayList<String> data) {
            this.dataSet = data;
        }

        @Override
        public CustomAdapterRecycle.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                      int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.after_schedule_my_item, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

            CustomAdapterRecycle.MyViewHolder myViewHolder = new CustomAdapterRecycle.MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final CustomAdapterRecycle.MyViewHolder holder, final int listPosition) {

            TextView month_id = holder.month_id;
            TextView day_id = holder.day_id;
            TextView year_id = holder.year_id;
            String string = (dataSet.get(listPosition));
            String[] parts = string.split("/");
            String part1 = parts[0]; // 004
            String part2 = parts[1];
            String part3 = parts[2];
            String input_date=string;
            SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
            Date dt1= null;
            try {
                dt1 = format1.parse(input_date);
                DateFormat format2=new SimpleDateFormat("MMM");
                String finalDay=format2.format(dt1);
                month_id.setText(finalDay);
                day_id.setText(part1);
                year_id.setText(part3);
            } catch (ParseException e) {
                e.printStackTrace();
            }




        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }}



}
