package stayabode.foodyHive.fragments.consumers;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.consumers.ConsumerMainActivity;
import stayabode.foodyHive.adapters.consumers.ConsumerTabAdapterInvoice;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.SaveSharedPreference;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ConsumerYouOrdersFragment extends Fragment {

    public static Typeface poppinsBold;
    public static Typeface poppinsMedium;
    public static Typeface poppinsLight;
    public static Typeface poppinsSemiBold;
    public static Typeface RobotoRegular;
    public static Typeface RobotoBold;
    public static Typeface robotoMedium;

    Spinner spinner;
    public static RecyclerView recyclerViewInfo;
    public static RecyclerView recyclerViewOrders;
    TabLayout tabLayout;
    public static ViewPager viewPager;


    public static String TAG = "ConsumerYourOrdersFragment";

    public static ConsumerYouOrdersFragment consumerYouOrdersFragment;

    LinearLayout startDateIcon,date_layout,endDateIcon;
    EditText startDateEditText,endDateEditText;
    DatePickerDialog picker;

    public  AutoCompleteTextView edit_search ;
    public String searchString="";
    public long lastFifteen=0;
    public long lastThirty=0;
    public long dateStart=0;
    public long dateEnd=0;
    ArrayList<String> arrayList=new ArrayList<>();
    ImageView clearid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_consumer_your_orders, container, false);
        consumerYouOrdersFragment = this;
        poppinsMedium = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Medium.ttf");
        poppinsBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Bold.ttf");
        poppinsLight = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Light.ttf");
        poppinsSemiBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-SemiBold.ttf");
        robotoMedium = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Medium.ttf");
        RobotoBold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
        RobotoRegular = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");

        spinner = rootView.findViewById(R.id.filterSpinner);
        tabLayout=rootView.findViewById(R.id.tabLayout);
        viewPager=rootView.findViewById(R.id.viewPager);


        ArrayList<String> spinnerList = new ArrayList<>();
        //spinnerList.clear();
        spinnerList.add("All");
        spinnerList.add("Last 15 Days");
        spinnerList.add("Last 30 Days");
        spinnerList.add("Custom");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, spinnerList);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        edit_search = (AutoCompleteTextView) rootView.findViewById(R.id.edit_search);



         arrayList = new ArrayList<>();
        arrayList.clear();
        arrayList.add("Open");
        arrayList.add("Closed");
        arrayList.add("Cancelled");
        prepareViewager(viewPager,arrayList);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);

        date_layout = rootView.findViewById(R.id.date_layout);
        //date_layout.setVisibility(View.GONE);
        startDateEditText = rootView.findViewById(R.id.startDateEditText);
        startDateIcon = rootView.findViewById(R.id.startDateIcon);
        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //  startDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                String dateFormat = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                try {
                                    Date date = format.parse(dateFormat);
                                    startDateEditText.setText(format.format(date));

                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date2 = sdf.parse(format.format(date));
                                    long millis = date2.getTime();
                                    Globaluse.dateStart=millis;
                                    Toast.makeText(getContext(),""+millis,Toast.LENGTH_SHORT).show();
                                    dateStart=millis;

                                    String search_txt = edit_search.getText().toString();
                                    if((search_txt.isEmpty()))
                                    {
                                        Globaluse.searchString="";
                                    }

                                    prepareViewager(viewPager,arrayList);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                //isValidateDate();
                            }
                        }, year, month, day);

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




       endDateEditText = rootView.findViewById(R.id.endDateEditText);
       endDateIcon = rootView.findViewById(R.id.endDateIcon);
        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //  startDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                String dateFormat = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                try {
                                    Date date = format.parse(dateFormat);
                                    endDateEditText.setText(format.format(date));

                                   // String myDate = "2014/10/29 18:10:45";
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    Date date2 = sdf.parse(format.format(date));
                                    long millis = date2.getTime();
                                    Globaluse.dateEnd=millis;
                                    Toast.makeText(getContext(),""+millis,Toast.LENGTH_SHORT).show();
                                    dateEnd=millis;

                                    String search_txt = edit_search.getText().toString();
                                    if((search_txt.isEmpty()))
                                    {
                                        Globaluse.searchString="";
                                    }

                                    prepareViewager(viewPager,arrayList);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                //isValidateDate();
                            }
                        }, year, month, day);

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


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int   pos = spinner.getSelectedItemPosition();
                if(pos==0)
                {
//                     searchString="";
//                     lastFifteen=0;
//                     lastThirty=0;
//                     dateStart=0;
//                     dateEnd=0;

                    endDateEditText.setText("");
                    startDateEditText.setText("");
                    edit_search.setText("");

                    Globaluse.searchString=" ";
                    Globaluse.lastFifteen=0;
                    Globaluse.lastThirty=0;
                    Globaluse.dateStart=0;
                    Globaluse.dateEnd=0;

                    //prepareViewager(viewPager,arrayList);
                }

                if(pos==1)
                {
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                  long fiftendaysTimeMillSeconds=  getCalculatedDate(date, "yyyy-MM-dd", -15);

                    //Toast.makeText(getContext(),""+fiftendaysTimeMillSeconds,Toast.LENGTH_SHORT).show();
                    lastFifteen=fiftendaysTimeMillSeconds;

//                    lastThirty=0;
//                    dateStart=0;
//                    dateEnd=0;

                    Globaluse.lastFifteen=fiftendaysTimeMillSeconds;
                    Globaluse.lastThirty=0;
                    Globaluse.dateStart=0;
                    Globaluse.dateEnd=0;
                    endDateEditText.setText("");
                    startDateEditText.setText("");
                   // prepareViewager(viewPager,arrayList);
                }
                if(pos==2)
                {
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    long ThiretydaysTimeMillSeconds=getCalculatedDate(date, "yyyy-MM-dd", -30);

                   // Toast.makeText(getContext(),""+ThiretydaysTimeMillSeconds,Toast.LENGTH_SHORT).show();
                    lastThirty=ThiretydaysTimeMillSeconds;

//                    lastFifteen=0;
//                    dateStart=0;
//                    dateEnd=0;

                    endDateEditText.setText("");
                    startDateEditText.setText("");
                    Globaluse.lastFifteen=0;
                    Globaluse.lastThirty=ThiretydaysTimeMillSeconds;
                    Globaluse.dateStart=0;
                    Globaluse.dateEnd=0;

                }



                if(pos==3)
                {
                    date_layout.setVisibility(View.VISIBLE);
                    //Toast.makeText(getContext(),"dxgdfg",Toast.LENGTH_SHORT).show();
                    Globaluse.lastFifteen=0;
                    Globaluse.lastThirty=0;
                    Globaluse.dateStart=0;
                    Globaluse.dateEnd=0;
                }else
                {
                    date_layout.setVisibility(View.GONE);
//                    Globaluse.lastFifteen=0;
//                    Globaluse.lastThirty=0;
//                    Globaluse.dateStart=0;
//                    Globaluse.dateEnd=0;
                   // Toast.makeText(getContext(),"dxgdfg",Toast.LENGTH_SHORT).show();
                }


                String search_txt = edit_search.getText().toString();
                if((search_txt.isEmpty()))
                {
                    Globaluse.searchString="";
                }

                prepareViewager(viewPager,arrayList);

               // Toast.makeText(getContext(),""+spinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
               // value = spinner.getSelectedItem().toString();

                // do stuff
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });



        // Get a reference to the AutoCompleteTextView in the layout

// Get the string array
        //String[] countries = getResources().getStringArray(R.array.countries_array);
// Create the adapter and set it to the AutoCompleteTextView
        getUserOrders();


        clearid=rootView.findViewById(R.id.clearid);

        clearid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Globaluse.searchString=" ";
                Globaluse.lastFifteen=0;
                Globaluse.lastThirty=0;
                Globaluse.dateStart=0;
                Globaluse.dateEnd=0;
                endDateEditText.setText("");
                startDateEditText.setText("");
                prepareViewager(viewPager,arrayList);

            }
        });

        return rootView;
    }


    /**
     add tabs to the viewpager
     **/
    private void prepareViewager(ViewPager viewPager, ArrayList<String> arrayList) {
//        ConsumerTabAdapter consumerTabAdapter=new ConsumerTabAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        ConsumerTabFragment consumerTabFragment=new ConsumerTabFragment();
//        for(int i=0;i<arrayList.size();i++){
//            Bundle bundle=new Bundle();
//            bundle.putString("title",arrayList.get(i));
//            consumerTabFragment.setArguments(bundle);
//            consumerTabAdapter.createFragment(consumerTabFragment,arrayList.get(i));
//            consumerTabFragment=new ConsumerTabFragment();
//        }
//        viewPager.setAdapter(consumerTabAdapter);

       // getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();


//        if((edit_search.getText().toString().isEmpty()))
//        {
//            Globaluse.searchString="";
//        }

//        String search_txt = edit_search.getText().toString();
//        if((search_txt.isEmpty()))
//        {
//            Globaluse.searchString="";
//        }

        ConsumerTabAdapterInvoice consumerTabAdapter=new ConsumerTabAdapterInvoice(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        ConsumerTabFragmentWithInvoice ConsumerTabFragmentwithithinvoice=new ConsumerTabFragmentWithInvoice();
        for(int i=0;i<arrayList.size();i++){
            Bundle bundle=new Bundle();
            bundle.putString("title",arrayList.get(i));
//            bundle.putString("searchString",searchString);
//            bundle.putString("lastFifteen", ""+"400");
//            bundle.putString("lastThirty",""+"100");
//            bundle.putString("dateStart",""+"200");
//            bundle.putString("dateEnd",""+"300");
            ConsumerTabFragmentwithithinvoice.setArguments(bundle);
            consumerTabAdapter.createFragment(ConsumerTabFragmentwithithinvoice,arrayList.get(i));
            ConsumerTabFragmentwithithinvoice=new ConsumerTabFragmentWithInvoice();



//            Globaluse.searchString=searchString;
//            Globaluse.lastFifteen=lastFifteen;
//            Globaluse.lastThirty=lastThirty;
//            Globaluse.dateStart=dateStart;
//            Globaluse.dateEnd=dateEnd;
        }
        viewPager.setAdapter(consumerTabAdapter);











    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }






    public void getUserOrders() {
        String url = APIBaseURL.getConsumersOrdersfullList + SaveSharedPreference.getLoggedInUserEmail(getActivity());
        //String url = APIBaseURL.getConsumersOrdersfullList + "satcatbat@gmail.com";
        //String url = APIBaseURL.getConsumersOrdersfullList + "jd.ramkumar@gmail.com";



        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v("userInfoResponse", response);
                List <String> stringList = new ArrayList<String>();
                try {

                    JSONObject  jsonObject = new JSONObject(response);
                    JSONArray dataArray = jsonObject.getJSONArray("data");




                    int adddataTo=0;
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject fullOrderData = dataArray.getJSONObject(i);

                        if(fullOrderData.optString("cartDetails")!=null && (fullOrderData.optString("cartDetails").length()>0)&& (!(fullOrderData.optString("cartDetails").equals("[]")))){
                            //Log.v("orderInvoiceNo", fullOrderData.optString("orderInvoiceNo"));
                            // Log.v("Number of order", " "+fullOrderData.optString("orderInvoiceNo"));



                            if(fullOrderData.optString("orderInvoiceNo") != null && !fullOrderData.optString("orderInvoiceNo") .isEmpty()&& !fullOrderData.optString("orderInvoiceNo").equals("null"))
                            {
                                // Log.v("Result_orderInvoiceNo", fullOrderData.optString("orderInvoiceNo"));
                                // Log.v("Result_paymentMethod", fullOrderData.optString("paymentMethod"));

                                JSONArray cartDetailsArray = fullOrderData.getJSONArray("cartDetails");
                                List<String> comingSoon = new ArrayList<String>();
                                JSONObject imagelist = new JSONObject();

                                String open_order="0";
                                String closed_order="0";
                                String cancelled_order="0";

                                for (int j = 0; j < cartDetailsArray.length(); j++) {
                                    JSONObject cartDetailsData = cartDetailsArray.getJSONObject(j);
                                    JSONObject listDetail = new JSONObject();

                                    stringList.add(cartDetailsData.optString("chefName"));


                                    try {
                                        listDetail.put("orderNo", cartDetailsData.optString("orderNo"));

                                    String consumerOrderStatus_str="";

                                        if (cartDetailsData.optInt("consumerOrderStatus") >= 0 && cartDetailsData.optInt("consumerOrderStatus") <= 2) {
                                            // foodItem.setStatus("Accepted");
                                            //open_order="1";
                                            consumerOrderStatus_str="Accepted";
                                        }
                                        else if (cartDetailsData.optInt("consumerOrderStatus") >= 3 && cartDetailsData.optInt("consumerOrderStatus") <= 6) {
                                            //foodItem.setStatus("Preparing");
                                            //open_order="1";
                                            consumerOrderStatus_str="Preparing";

                                        } else if (cartDetailsData.optInt("consumerOrderStatus") >= 7 && cartDetailsData.optInt("consumerOrderStatus") <= 11) {
                                            //foodItem.setStatus("On the way");

                                            //open_order="1";
                                            consumerOrderStatus_str="Preparing";

                                        } else if (cartDetailsData.optInt("consumerOrderStatus") == 100) {
                                            //foodItem.setStatus("Cancelled");
                                            //cancelled_order="1";

                                            consumerOrderStatus_str="Cancelled";
                                        } else if (cartDetailsData.optInt("consumerOrderStatus") >= 12 && cartDetailsData.optInt("consumerOrderStatus") <= 99) {
                                            // foodItem.setStatus("Delivered");
                                            //closed_order="1";
                                            consumerOrderStatus_str="Delivered";
                                        }


                                        listDetail.put("consumerOrderStatus", consumerOrderStatus_str);



                                        listDetail.put("orderCreatedDate", cartDetailsData.optString("orderCreatedDate"));
                                        //Allquantity+cartDetailsData.optInt("quantity");

                                        listDetail.put("chefName", cartDetailsData.optString("chefName"));
                                        listDetail.put("paymentMethod", fullOrderData.optString("paymentMethod"));
                                        listDetail.put("orderInvoiceNo", fullOrderData.optString("orderInvoiceNo"));
                                        if(cartDetailsData.optString("menuDetails")!=null && (cartDetailsData.optString("menuDetails").length()>0)&& (!(cartDetailsData.optString("menuDetails").equals("[]")))){
                                            ArrayList<String> img_source = new ArrayList<>();
                                            JSONArray jarray = new JSONArray();
                                            //JSONObject jsonObjectImag = new JSONObject();
                                            for (int c = 0; c < cartDetailsArray.length(); c++) {
                                                JSONObject cartDetailsData2 = cartDetailsArray.getJSONObject(c);
                                                JSONArray menuDetailsArray = cartDetailsData2.getJSONArray("menuDetails");
                                                int Allquantity=0;
                                                for (int m = 0; m < menuDetailsArray.length(); m++) {
                                                    JSONObject menuDetailsData = menuDetailsArray.getJSONObject(m);
                                                    JSONArray dishImageArray = menuDetailsData.getJSONArray("dishImage");
                                                    Allquantity=Allquantity+menuDetailsData.optInt("quantity");

                                                    stringList.add(menuDetailsData.optString("dishName"));


                                                    for (int d = 0; d < dishImageArray.length(); d++) {
                                                        img_source.add(String.valueOf(dishImageArray.get(d)));
                                                        jarray.put(dishImageArray.get(d));



                                                    }
                                                }
                                                listDetail.put("quantity", Allquantity);
                                            }
                                            listDetail.put("disImage", jarray);

                                        }

                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }

                                    //comingSoon.add(cartDetailsData.optString("chefName"));
                                    comingSoon.add(String.valueOf(listDetail));


                                        if (cartDetailsData.optInt("consumerOrderStatus") >= 0 && cartDetailsData.optInt("consumerOrderStatus") <= 2) {
                                           // foodItem.setStatus("Accepted");
                                            open_order="1";
                                        }
                                        else if (cartDetailsData.optInt("consumerOrderStatus") >= 3 && cartDetailsData.optInt("consumerOrderStatus") <= 6) {
                                            //foodItem.setStatus("Preparing");
                                            open_order="1";

                                        } else if (cartDetailsData.optInt("consumerOrderStatus") >= 7 && cartDetailsData.optInt("consumerOrderStatus") <= 11) {
                                            //foodItem.setStatus("On the way");

                                            open_order="1";

                                        } else if (cartDetailsData.optInt("consumerOrderStatus") == 100) {
                                            //foodItem.setStatus("Cancelled");
                                            cancelled_order="1";
                                        } else if (cartDetailsData.optInt("consumerOrderStatus") >= 12 && cartDetailsData.optInt("consumerOrderStatus") <= 99) {
                                           // foodItem.setStatus("Delivered");
                                            closed_order="1";
                                        }

//                                    if (cartDetailsData.optInt("consumerOrderStatus") >= 12 && cartDetailsData.optInt("consumerOrderStatus") <= 99) {
//                                        // foodItem.setStatus("Delivered");
//                                        closed_order="1";
//
//                                    }else if(cartDetailsData.optInt("consumerOrderStatus") == 100)
//                                    {
//                                        cancelled_order="1";
//                                    }else
//                                    {
//                                        open_order="1";
//                                    }


                                    Log.v("Result_chefName", cartDetailsData.optString("chefName"));
                                }



                                JSONObject headerDetail = new JSONObject();
                                try {
                                    headerDetail.put("orderInvoiceNo", fullOrderData.optString("orderInvoiceNo"));
                                    headerDetail.put("open", open_order);
                                    headerDetail.put("closed", closed_order);
                                    headerDetail.put("cancelled", cancelled_order);

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }




                                adddataTo++;
                            }
                        }

//                            if(fullOrderData.optString("orderInvoiceNo") != null && !fullOrderData.optString("orderInvoiceNo") .isEmpty()&& !fullOrderData.optString("orderInvoiceNo").equals("null"))
//                            {
//
//                                Log.v("orderInvoiceNo", fullOrderData.optString("orderInvoiceNo"));
//
//                            }
                    }


                    Set<String> set = new HashSet<>(stringList);
                    stringList.clear();
                    stringList.addAll(set);


                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stringList);
                    edit_search.setAdapter(adapter);
                    edit_search.setThreshold(1);
                    edit_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Toast.makeText(getActivity(), adapter.getItem(position).toString(), Toast.LENGTH_SHORT).show();

                            //searchString=adapter.getItem(position);
                            Globaluse.searchString=adapter.getItem(position);
                            prepareViewager(viewPager,arrayList);

                        }
                    });


//                    edit_search.setOnTouchListener(new View.OnTouchListener(){
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event){
//                            text.showDropDown();
//                            return false;
//                        }
//                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }






            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    //TODO
                    ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    android.app.AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            ConsumerMainActivity.logout();

                        }
                    });

                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();

                        }
                    });

                    buttonreset.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getActivity(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, getActivity());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "userInfoTaq");
    }





    public static long getCalculatedDate(String date, String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        long timeMilli2 = cal.getTimeInMillis();


        return timeMilli2;

    }



}
