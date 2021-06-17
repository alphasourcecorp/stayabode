package stayabode.foodyHive.corporatemenurange;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import stayabode.foodyHive.R;
import stayabode.foodyHive.adapters.consumers.CorporateExpandableListAdapterRange;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.models.CorporateDish;
import stayabode.foodyHive.models.CorporateMenuModel;
import stayabode.foodyHive.models.RequestBtoBItem;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.SqlDateTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CorporateMyRequestList extends AppCompatActivity {


   
    ImageView back_id;

    ListView navLV;
    ImageView close_navigation_drawer;
    TextView menu_drawer_open_id;
    private static final String TAG = CorporateMyRequestList.class.getName();

    CorporateExpandableListAdapterRange listAdapter;
    ExpandableListView expListView;
//    List<String> listDataHeader;
//    HashMap<String, List<String>> listDataChild;

    List<CorporateMenuModel> items = new ArrayList<>();
    List<CorporateDish> CorporateDish_list = new ArrayList<>();
    List<CorporateDish.MenuDetail> MenuDetail_list=new ArrayList<>();

    Button view_basket_id;
    TextView whatsapp_id;
    public static CorporateMyRequestList corporateMenuWithNavigatioin;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String jsonurl = "https://foodyhivestorage.blob.core.windows.net/assets/data/subscription-requests.json";


    private ProgressDialog pDialog;
    TextView logout_id;
    ImageView menu_id_menu;

    ViewPager viewPager;
    TabLayout tabLayoutViewAll;
    Spinner spinner;
    ImageView addclient;
    int pageindex=0;
    String status="All";
    String edit_search_str="";
    LinearLayout startDateIcon,date_layout,endDateIcon;
    EditText startDateEditText,endDateEditText;
    DatePickerDialog picker;
    long startTime=0;


    private RecyclerView schedule_list;
    ArrayList<RequestBtoBItem> near_data= new ArrayList<>();
    CorporateMyRequestList.NearAdapter adapter;

    int firstVisibleItem, visibleItemCount, totalItemCount,count=0;
    protected int m_PreviousTotalCount;
    RecyclerViewPositionHelper mRecyclerViewHelper;

    public AutoCompleteTextView edit_search ;
    String rstartDate=null;
    String rendDate=null;
    ImageView clearid;
    List <String> stringList = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.corporatemyrequestlist);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        corporateMenuWithNavigatioin=this;

        near_data= new ArrayList<>();
        stringList.add("");
        GetAllRequestId();

        edit_search = (AutoCompleteTextView) findViewById(R.id.edit_search);


        adapter = new CorporateMyRequestList.NearAdapter(CorporateMyRequestList.this, near_data);

        back_id=findViewById(R.id.back_id);

        back_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
       // GetCompanySignUp();

        spinner = findViewById(R.id.filterSpinner);
        ArrayList<String> spinnerList = new ArrayList<>();
        //spinnerList.clear();
        spinnerList.add("All");
        spinnerList.add("Requested");
        spinnerList.add("Accepted");
        spinnerList.add("Rejected");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CorporateMyRequestList.this, R.layout.support_simple_spinner_dropdown_item, spinnerList);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int   pos = spinner.getSelectedItemPosition();
                if(pos==0)
                {
                    Globaluse.spinnerselection="All";
                    status="All";
                    pageindex=0;
                    near_data= new ArrayList<>();
                    count=0;
                    GetRequestList();
                    // adapter.notifyDataSetChanged();
                }

                if(pos==1)
                {
                    Globaluse.spinnerselection="Requested";
                    status="Request";
                    pageindex=0;
                    near_data= new ArrayList<>();
                    count=0;
                    GetRequestList();
                    //adapter.notifyDataSetChanged();
                }

                if(pos==2)
                {
                    Globaluse.spinnerselection="Accepted";
                    status="Approved";
                    pageindex=0;
                    near_data= new ArrayList<>();
                    count=0;
                    GetRequestList();
                    adapter.notifyDataSetChanged();
                }

                if(pos==3)
                {
                    Globaluse.spinnerselection="Rejected";
                    status="Rejected";
                    pageindex=0;
                    near_data= new ArrayList<>();
                    count=0;
                    GetRequestList();
                    adapter.notifyDataSetChanged();
                }


                //  prepareViewager(viewPager,arrayList);

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        clearid= findViewById(R.id.clearid);
        clearid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //spinner.setSelection(0);
                status="All";
                pageindex=0;
                edit_search.setText("");
                edit_search_str="";
                rstartDate=null;
                rendDate=null;
                startDateEditText.setText("");
                endDateEditText.setText("");

                near_data= new ArrayList<>();
                count=0;
                GetRequestList();
            }
        });


        schedule_list = (RecyclerView) findViewById(R.id.schedule_list);

        schedule_list.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mRecyclerViewHelper.getItemCount();
                firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();

                if (totalItemCount == 0 || adapter == null)
                    return;
                if (m_PreviousTotalCount == totalItemCount)
                {
                    return;
                }
                else
                {
                    boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
                    if (loadMore)
                    {
                        m_PreviousTotalCount = totalItemCount;
                        GetRequestList();
                    }
                }
            }
        });




        startDateEditText = findViewById(R.id.startDateEditText);
        startDateIcon = findViewById(R.id.startDateIcon);
        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CorporateMyRequestList.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //  startDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                String dateFormat = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date = format.parse(dateFormat);
                                    startDateEditText.setText(format.format(date));
                                    rstartDate=format2.format(date);
                                    //format2

//                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                                    Date date2 = sdf.parse(format.format(date));
//                                    long millis = date2.getTime();
//                                    Globaluse.dateStart=millis;
//                                    Toast.makeText(CorporateMenuWithNavigatioinAdmin.this,""+millis,Toast.LENGTH_SHORT).show();
//                                    //dateStart=millis;
//
//                                    String search_txt = edit_search.getText().toString();
//                                    if((search_txt.isEmpty()))
//                                    {
//                                        Globaluse.searchString="";
//                                    }
//

                                    Calendar calendar = Calendar.getInstance();
                                    calendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                                    startTime = calendar.getTimeInMillis();


                                    //prepareViewager(viewPager,arrayList);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                //isValidateDate();
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                //picker.getDatePicker().setMinDate(System.currentTimeMillis());
                // cldr.add(Calendar.YEAR, 1);
                //picker.getDatePicker().setMaxDate(cldr.getTimeInMillis());
                picker.show();




            }
        });

        startDateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDateEditText.performClick();
            }
        });




        endDateEditText =findViewById(R.id.endDateEditText);
        endDateIcon = findViewById(R.id.endDateIcon);
        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(CorporateMyRequestList.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {






                                //  startDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                String dateFormat = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    Date date = format.parse(dateFormat);
                                    endDateEditText.setText(format.format(date));
                                    rendDate=format2.format(date);
                                    // String myDate = "2014/10/29 18:10:45";
//                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                                    Date date2 = sdf.parse(format.format(date));
//                                    long millis = date2.getTime();
//                                    Globaluse.dateEnd=millis;
//                                    Toast.makeText(CorporateMenuWithNavigatioinAdmin.this,""+millis,Toast.LENGTH_SHORT).show();
//                                    //dateEnd=millis;

//                                    String search_txt = edit_search.getText().toString();
//                                    if((search_txt.isEmpty()))
//                                    {
//                                        Globaluse.searchString="";
//                                    }

                                    // prepareViewager(viewPager,arrayList);



                                    pageindex=0;
                                    near_data= new ArrayList<>();
                                    count=0;
                                    GetRequestList();


                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                //isValidateDate();
                            }
                        }, year, month, day);

                picker.getDatePicker().setMinDate(startTime);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);

                //picker.getDatePicker().setMinDate(System.currentTimeMillis());
                // cldr.add(Calendar.YEAR, 1);
                //picker.getDatePicker().setMaxDate(cldr.getTimeInMillis());
                picker.show();




            }
        });

        endDateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endDateEditText.performClick();
            }
        });








    }






    public void  GetCompanySignUp()
    {
        SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
        String companyId = sh.getString("companyId", "");
        String URL = APIBaseURL.BASEURLLINK_B2B_company_list;
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("userInfoResponse", response);
               // List <String> stringList = new ArrayList<String>();
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    if(jsonObject.getString("isSuccess").equals("true"))
                    {
                        JSONArray datajsonarray = jsonObject.getJSONArray("data");

                        for(int k=0;k<datajsonarray.length();k++)
                        {
                            stringList.add("");
                            stringList.add(datajsonarray.getJSONObject(k).getString("companyName"));
                            stringList.add(datajsonarray.getJSONObject(k).getString("emailId"));
                            stringList.add(datajsonarray.getJSONObject(k).getString("phoneNumber"));
                            stringList.add(datajsonarray.getJSONObject(k).getString("subscriberName"));
                        }

                        Set<String> set = new HashSet<>(stringList);
                        stringList.clear();
                        stringList.addAll(set);

                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(CorporateMyRequestList.this, android.R.layout.simple_list_item_1, stringList);
                        edit_search.setAdapter(adapter2);
                        edit_search.setThreshold(1);
                        edit_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                Toast.makeText(CorporateMyRequestList.this, adapter2.getItem(position).toString(), Toast.LENGTH_SHORT).show();

//                                //searchString=adapter.getItem(position);
//                                Globaluse.searchString=adapter.getItem(position);
//                                prepareViewager(viewPager,arrayList);

                                edit_search_str=adapter2.getItem(position).toString();
                                pageindex=0;
                                near_data= new ArrayList<>();
                                count=0;
                                adapter.notifyDataSetChanged();
                                GetRequestList();


                            }
                        });
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO

                } else if (error instanceof NetworkError) {
                    Toast.makeText(CorporateMyRequestList.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, CorporateMyRequestList.this);
//        {    //this is the part, that adds the header to the request
//            @Override
//            public Map<String, String> getHeaders() {
//                SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
//                String token = sh.getString("token", "");
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Authorization", "Bearer " + token);
//                params.put("content-type", "application/json");
//                return params;
//            }
//        };

        ApplicationController.getInstance().addToRequestQueue(stringRequest, "companyList");
    }





    public void  GetRequestList() {
        SharedPreferences sharedPreferences = getSharedPreferences("corporateLogin",MODE_PRIVATE);
        String companyId = sharedPreferences.getString("companyId", "");

        pDialog = ProgressDialog.show(CorporateMyRequestList.this, "", "Please wait...", true,false);
        JSONObject inputObject = new JSONObject();
        String URL = APIBaseURL.BASEURLLINK_B2B_Subscription_list;
        try {


//            inputObject = new JSONObject();
//            inputObject.put("companyId", companyId);
//            inputObject.put("status", status);
//            inputObject.put("search", edit_search_str);
//            inputObject.put("startDate", "2020-04-11T10:21:54.996Z");
//            inputObject.put("endDate", "2020-04-11T10:21:54.996Z");
//            inputObject.put("pagesize", 20);
//            inputObject.put("pageindex", pageindex);

            inputObject = new JSONObject();
            inputObject.put("companyId", companyId);
            inputObject.put("status", status);
            inputObject.put("search", edit_search_str);
            inputObject.put("startDate", rstartDate);
            inputObject.put("endDate", rendDate);
            inputObject.put("pagesize", 50);
            inputObject.put("pageindex", pageindex);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, inputObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();





                SqlDateTypeAdapter sqlAdapter = new SqlDateTypeAdapter();
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(java.sql.Date.class, sqlAdapter)
                        .setDateFormat("yyyy-MM-dd")
                        .create();

                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    if (jsonObject.getString("isSuccess").equals("true")) {
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        JSONArray requestsList = dataObject.getJSONArray("requestsList");

                        int forrequestsList=0;//var

                        for(int i=0;i<requestsList.length();i++)
                        {
                            RequestBtoBItem foodItem = new RequestBtoBItem();
                            Log.v("BreakFastURL", String.valueOf(requestsList.get(i)));
                            foodItem = gson.fromJson(String.valueOf(requestsList.get(i)), RequestBtoBItem .class);
                            Log.v("BreakFastURL222", String.valueOf(foodItem));
                            if(foodItem.getStatus().equalsIgnoreCase(status))//varr
                            {
                                near_data.add(foodItem);
                                forrequestsList++;
                            }
                            else {
                                if(status.equalsIgnoreCase("All"))
                                {
                                    near_data.add(foodItem);
                                    forrequestsList++;
                                }
                            }

                        }


                        if (count == 0) //vara
                        {
                            adapter = new CorporateMyRequestList.NearAdapter(CorporateMyRequestList.this, near_data);
                            schedule_list.setAdapter(adapter);
                            schedule_list.setLayoutManager(new LinearLayoutManager(CorporateMyRequestList.this));
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                        if (forrequestsList == 0) {
                            count = 0;
                        } else {
                            count += forrequestsList;
                        }
                        pageindex++;


                    }



                } catch (JSONException e) {

                    e.printStackTrace();
                }


                pDialog.dismiss();




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
            }
        }) {    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
                String token = sh.getString("token", "");
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
               // params.put("content-type", "application/json");
                return params;
            }
        };
        ApplicationController.getInstance().addToRequestQueue(jsonObjectRequest);



    }







    public class NearAdapter extends RecyclerView.Adapter<CorporateMyRequestList.NearAdapter.ViewHolder> {
        private ArrayList<RequestBtoBItem> data;
        private Context context;
        public NearAdapter(Context context,ArrayList<RequestBtoBItem> data) {
            this.data = data;
            this.context = context;
        }

        @Override
        public CorporateMyRequestList.NearAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.corporate_user_request_list_item, viewGroup, false);
            return new CorporateMyRequestList.NearAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CorporateMyRequestList.NearAdapter.ViewHolder viewHolder, final int i) {
//            viewHolder.company_id.setText(data.get(i).getCompanyName());
//            viewHolder.email_id.setText(data.get(i).getEmailId());
//            viewHolder.mobile_id.setText(data.get(i).getPhoneNumber());
            viewHolder.request_id.setText(data.get(i).getRequestId());

            if(data.get(i).getStatus().equalsIgnoreCase("Request"))
            {
                viewHolder.view_detail_id.setBackgroundResource(R.drawable.corporate_menu_bg_yellow);
            }else if(data.get(i).getStatus().equalsIgnoreCase("Approved"))
            {
                viewHolder.view_detail_id.setBackgroundResource(R.drawable.corporate_menu_bg_green);
            }
            else if(data.get(i).getStatus().equalsIgnoreCase("Rejected"))
            {
                viewHolder.view_detail_id.setBackgroundResource(R.drawable.corporate_menu_bg_red);
            }else
            {
                viewHolder.view_detail_id.setBackgroundResource(R.drawable.corporate_menu_bg_yellow);
            }


            viewHolder.view_detail_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(CorporateMyRequestList.this, CorporateOrderDetailUser.class);
                    intent.putExtra("requestId", data.get(i).getRequestId());
                    intent.putExtra("companyId", data.get(i).getCompanyId());
                    startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            //            private TextView name,distince,review,category,address;
//            private ImageView img;
//            private RatingBar rat;
//            CardView vi_click;
            Button view_detail_id;
            //TextView company_id,email_id,mobile_id,request_id;
            TextView request_id;

            public ViewHolder(View view) {
                super(view);
                view_detail_id = (Button) view.findViewById(R.id.view_detail_id);
//                company_id = (TextView) view.findViewById(R.id.company_id);
//                email_id = (TextView) view.findViewById(R.id.email_id);
//                mobile_id = (TextView) view.findViewById(R.id.mobile_id);
                request_id = (TextView) view.findViewById(R.id.request_id);


            }
        }
    }




    public void  GetAllRequestId()
    {
        SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
        String companyId = sh.getString("companyId", "");
        String URL = APIBaseURL.BASEURLLINK_B2B_AddRequestId+companyId;
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("userInfoResponse", response);
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    if (jsonObject.getString("isSuccess").equals("true")) {
                        //JSONObject dataObject = jsonObject.getJSONObject("data");
                        JSONArray requestsList = jsonObject.getJSONArray("data");

                        for (int i = 0; i < requestsList.length(); i++) {
                            stringList.add(requestsList.getJSONObject(i).getString("requestId"));
                        }


                        Set<String> set = new HashSet<>(stringList);
                        stringList.clear();
                        stringList.addAll(set);

                        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(CorporateMyRequestList.this, android.R.layout.simple_list_item_1, stringList);
                        edit_search.setAdapter(adapter2);
                        edit_search.setThreshold(1);
                        edit_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id) {
                                Toast.makeText(CorporateMyRequestList.this, adapter2.getItem(position).toString(), Toast.LENGTH_SHORT).show();

//                                //searchString=adapter.getItem(position);
//                                Globaluse.searchString=adapter.getItem(position);
//                                prepareViewager(viewPager,arrayList);

                                edit_search_str=adapter2.getItem(position).toString();
                                pageindex=0;
                                near_data= new ArrayList<>();
                                count=0;
                                adapter.notifyDataSetChanged();
                                GetRequestList();


                            }
                        });
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO

                } else if (error instanceof NetworkError) {
                    Toast.makeText(CorporateMyRequestList.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, CorporateMyRequestList.this){    //this is the part, that adds the header to the request
            @Override
            public Map<String, String> getHeaders() {
                SharedPreferences sh = getSharedPreferences("corporateLogin", MODE_APPEND);
                String token = sh.getString("token", "");
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);
               // params.put("content-type", "application/json");
                return params;
            }};
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "requestList");
    }


}
