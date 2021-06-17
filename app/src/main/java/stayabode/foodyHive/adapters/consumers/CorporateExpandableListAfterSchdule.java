package stayabode.foodyHive.adapters.consumers;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.constants.Globaluse;
import stayabode.foodyHive.corporatemenurange.CorporateDatePickerExample;
import com.google.android.flexbox.FlexboxLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CorporateExpandableListAfterSchdule extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    //RecyclerView.LayoutManager RecyclerViewLayoutManager;
    //LinearLayoutManager HorizontalLayout;
    // adapter class object
    //AdapterImage adapter;

    ExpandableListView expListView;
    RecyclerView recyclerView;
    ArrayList<String> Number;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout ;
    View ChildView ;
    int RecyclerViewItemPosition ;




    public CorporateExpandableListAfterSchdule(Context context, ExpandableListView ex, List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.expListView = ex;
    }






    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.corporate_expand_list_schedule_header_row, null);
        }
      //  TextView lblListHeader = (TextView) convertView.findViewById(R.id.cat_id);
        String headerTitle = (String) getGroup(groupPosition);
        try {
            JSONObject jsonObject = new JSONObject(headerTitle);
           // lblListHeader.setText(jsonObject.getString("categoryTitle")+" "+jsonObject.getString("categorySubTitle"));
        }catch (JSONException err){
            Log.d("Error", err.toString());
        }




        //expListView.expandGroup(groupPosition);//expand all child

        return convertView;
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }





    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }



    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }


    public void updatejson(int groupPosition, int childPosititon,String updatejsondat) {
        List<String> dishlistdata = new ArrayList<String>();
        for (int m = 0; m < this._listDataChild.get(this._listDataHeader.get(groupPosition)).size(); m++) {

            if(childPosititon==m)
            {
                dishlistdata.add(updatejsondat);
            }else
            {
                dishlistdata.add(String.valueOf(this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(m)));
            }

        }
        _listDataChild.put(this._listDataHeader.get(groupPosition),dishlistdata);

        Globaluse._listDataHeader=new ArrayList<String>();
        Globaluse._listDataChild=new HashMap<String, List<String>>();
        Globaluse._listDataHeader=new ArrayList<String>(this._listDataHeader);
        Globaluse._listDataChild=new HashMap<String, List<String>>(this._listDataChild);

//        JSONObject jsonObject = null;
//        try {
//            jsonObject = new JSONObject(updatejsondat);
//
//                JSONObject dishDetail = new JSONObject();
//                try {
//                    dishDetail.put("dishItemName", jsonObject.getString("dishName"));
//                    dishDetail.put("description", jsonObject.getString("dishDescription"));
//                    dishDetail.put("cost", jsonObject.getString("saleAmount"));
//                    dishDetail.put("noOfUnits", jsonObject.getString("quantity"));
//                    dishDetail.put("taxValue", 0);
//                    dishDetail.put("packingCharges", 0);
//                    dishDetail.put("deliveryCharges", 0);
//
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                Globaluse.corporatedishlistnew.add(String.valueOf(dishDetail));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }







        notifyDataSetChanged();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        try {

            JSONObject jsonObject = new JSONObject(childText);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.range_corporate_expand_list_row_new_schedule, null);
        }
        LinearLayout fulllayout_id = (LinearLayout) convertView.findViewById(R.id.fulllayout_id);
        TextView title_id = (TextView) convertView.findViewById(R.id.title_id);
        LinearLayout add_linear_id = (LinearLayout) convertView.findViewById(R.id.add_linear_id);
        LinearLayout inc_dec_id = (LinearLayout) convertView.findViewById(R.id.inc_dec_id);
        Button addbutton_id = (Button) convertView.findViewById(R.id.addbutton_id);
        ImageView dish_img = (ImageView) convertView.findViewById(R.id.dish_img);
        TextView des_id = (TextView) convertView.findViewById(R.id.des_id);
        TextView price_id = (TextView) convertView.findViewById(R.id.price_id);
        TextView sub_price_id = (TextView) convertView.findViewById(R.id.sub_price_id);
        EditText quantity_id = (EditText) convertView.findViewById(R.id.quantity_id);
        ImageView minus_id = (ImageView) convertView.findViewById(R.id.minus_id);
        ImageView plus_id = (ImageView) convertView.findViewById(R.id.plus_id);
        LinearLayout listclick_id = (LinearLayout) convertView.findViewById(R.id.listclick_id);
        ImageView custom_date_id = (ImageView) convertView.findViewById(R.id.custom_date_id);
        CheckBox checkStatus_id = (CheckBox) convertView.findViewById(R.id.checkStatus_id);









            Glide.with(_context).load(jsonObject.getString("dishImage")).into(dish_img);

            title_id.setText(jsonObject.getString("dishName"));
            des_id.setText(jsonObject.getString("dishDescription"));

            if(jsonObject.getString("saleAmount").equals("0"))
            {
                price_id.setText("Rs. "+" TBD ");
            }else
            {
                price_id.setText("Rs. "+jsonObject.getString("saleAmount")+"/-");
            }



            sub_price_id.setText(jsonObject.getString("saleKgMg"));
            quantity_id.setText(jsonObject.getString("quantity"));
            if(jsonObject.getBoolean("buttondisplay"))
            {
                inc_dec_id.setVisibility(View.VISIBLE);
                add_linear_id.setVisibility(View.GONE);
               // fulllayout_id.setVisibility(View.VISIBLE);

            }
            else {

                inc_dec_id.setVisibility(View.GONE);
                add_linear_id.setVisibility(View.VISIBLE);
               // fulllayout_id.setVisibility(View.GONE);

            }




            checkStatus_id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {

                    try {
//                        boolean checkStatus=false;
//                        if (isChecked){
//                            if(jsonObject.getBoolean("checkStatus"))
//                            {
//                                jsonObject.put("checkStatus", true);
//                                updatejson(groupPosition,childPosition,String.valueOf(jsonObject));
//                            }else
//                            {
//                                jsonObject.put("checkStatus", false);
//                                updatejson(groupPosition,childPosition,String.valueOf(jsonObject));
//                            }
//                        }else
//                        {
//
//                            if(jsonObject.getBoolean("checkStatus"))
//                            {
//                                jsonObject.put("checkStatus", true);
//                                updatejson(groupPosition,childPosition,String.valueOf(jsonObject));
//                            }else
//                            {
//                                jsonObject.put("checkStatus", false);
//                                updatejson(groupPosition,childPosition,String.valueOf(jsonObject));
//                            }
//
//                        }



                        if(isChecked)
                        {
                            //jsonObject.put("checkStatus", true);
                            jsonObject.put("checkStatus_schedule", true);

                        }else
                        {
                            //jsonObject.put("checkStatus", false);
                            jsonObject.put("checkStatus_schedule", false);

                        }

                        updatejson(groupPosition,childPosition,String.valueOf(jsonObject));

//                        if(jsonObject.getBoolean("checkStatus"))
//                        {
//                            //checkStatus_id.setChecked(true);
//                            jsonObject.put("checkStatus", false);
//                            updatejson(groupPosition,childPosition,String.valueOf(jsonObject));
//                        }
//                        else {
//                            jsonObject.put("checkStatus", true);
//                            updatejson(groupPosition,childPosition,String.valueOf(jsonObject));
//                        }

//                        if(isChecked)
//                        {
//                            checkStatus_id.setChecked(true);
//                            jsonObject.put("checkStatus", false);
//                            updatejson(groupPosition,childPosition,String.valueOf(jsonObject));
//                        }
//                        else {
//                            checkStatus_id.setChecked(false);
//                            jsonObject.put("checkStatus", true);
//                            updatejson(groupPosition,childPosition,String.valueOf(jsonObject));
//                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


            if(jsonObject.getBoolean("checkStatus_schedule"))
            {
                checkStatus_id.setChecked(true);
            }
            else {
                checkStatus_id.setChecked(false);
            }





            minus_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int qut= Integer.parseInt(quantity_id.getText().toString())- 1;
                    quantity_id.setText(""+qut);
                    try {
                        if(qut==0)
                        {
                            jsonObject.put("buttondisplay", false);
                            jsonObject.put("checkStatus", false);
                        }



                        jsonObject.put("quantity", qut);
                        updatejson(groupPosition,childPosition,String.valueOf(jsonObject));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });

            plus_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int qut= Integer.parseInt(quantity_id.getText().toString())+ 1;
                    quantity_id.setText(""+qut);
                    try {
                        jsonObject.put("quantity", qut);

                        updatejson(groupPosition,childPosition,String.valueOf(jsonObject));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });





            addbutton_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        jsonObject.put("buttondisplay", true);
                        jsonObject.put("quantity", 1);
                        jsonObject.put("checkStatus", true);
                        updatejson(groupPosition,childPosition,String.valueOf(jsonObject));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    inc_dec_id.setVisibility(View.VISIBLE);
                    add_linear_id.setVisibility(View.GONE);

                }
            });



            listclick_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final Dialog dialog = new Dialog(_context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.corporate_list_detail);

                    ImageView close_id = (ImageView) dialog.findViewById(R.id.close_id);
                    TextView title_id = (TextView) dialog.findViewById(R.id.title_id);
                    TextView sub_title_id = (TextView) dialog.findViewById(R.id.sub_title_id);
                    ImageView dish_img_id = (ImageView) dialog.findViewById(R.id.dish_img_id);
                    TextView dish_des = (TextView) dialog.findViewById(R.id.dish_des);
                    TextView dynamic_txt = (TextView) dialog.findViewById(R.id.dynamic_txt);
                    LinearLayout dynamiclayout_id = (LinearLayout) dialog.findViewById(R.id.dynamiclayout_id);


                    TextView calories_id = (TextView) dialog.findViewById(R.id.calories_id);
                    TextView protein_id = (TextView) dialog.findViewById(R.id.protein_id);
                    TextView fat_id = (TextView) dialog.findViewById(R.id.fat_id);
                    TextView carbs_id = (TextView) dialog.findViewById(R.id.carbs_id);
                    TextView fiber_id = (TextView) dialog.findViewById(R.id.fiber_id);
                    TextView sugar_id = (TextView) dialog.findViewById(R.id.sugar_id);





                    try {


                        calories_id.setText("CALORIES :"+jsonObject.getString("calories"));
                        protein_id.setText("PROTEIN : "+jsonObject.getString("protein")+" %");
                        fat_id.setText("FAT : "+jsonObject.getString("fat")+" %");
                        carbs_id.setText("CARBS : "+jsonObject.getString("carbs")+" %");
                        fiber_id.setText("FIBER : "+jsonObject.getString("fiber")+" %");
                        sugar_id.setText("SUGAR : "+jsonObject.getString("sugar")+" %");


                        title_id.setText(jsonObject.getString("dishName")+" "+jsonObject.getString("dishSubName"));
                        Glide.with(_context).load(jsonObject.getString("dishImage")).into(dish_img_id);
                        dish_des.setText(jsonObject.getString("dishDescription"));

                        String str = jsonObject.getString("suitableFor");
                        String[] temp = str.replaceAll("[\\[\\]]", "").split(",");
                        FlexboxLayout flexboxLayout;
                        flexboxLayout = (FlexboxLayout)dialog.findViewById(R.id.flexboxlayout2);
                        int prevTextViewId = R.id.dynamic_txt;
                        for(int k=0; k<temp.length; k++)
                        {
                            View vi = LayoutInflater.from(_context).inflate(R.layout.nutrion_item, null);
                            TextView dynamic_txt_new = vi.findViewById(R.id.dynamic_txt_new);
                            dynamic_txt_new.setText(String.valueOf(temp[k]));
                            int curTextViewId = prevTextViewId + 1;
                            final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT);
                            params.addRule(RelativeLayout.BELOW, prevTextViewId);
                            vi.setLayoutParams(params);
                            flexboxLayout.addView(vi, params);
                            prevTextViewId = curTextViewId;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                    close_id.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();




                }
            });



            custom_date_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View ve) {

//                    final Dialog dialog = new Dialog(_context);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//                    if (dialog != null)
//                    {
//                        int width = ViewGroup.LayoutParams.MATCH_PARENT;
//                        int height = ViewGroup.LayoutParams.MATCH_PARENT;
//                        dialog.getWindow().setLayout(width, height);
//                    }
//                  //  dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//                    dialog.setCancelable(false);
//                    dialog.setContentView(R.layout.corporate_date_picker_test);
//
//
//
//                    ImageView dialogButton = (ImageView) dialog.findViewById(R.id.close_id);
//                    dialogButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
//
//                    dialog.show();



                    // the content
                    final RelativeLayout root = new RelativeLayout(_context);
                    root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                    // creating the fullscreen dialog
                    final Dialog dialog = new Dialog(_context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(root);
                    dialog.setContentView(R.layout.corporate_date_picker_test);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                    Button suubmit_id=(Button) dialog.findViewById(R.id.suubmit_id);
                    ImageView dialogButton = (ImageView) dialog.findViewById(R.id.close_id);
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    suubmit_id.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(_context, CorporateDatePickerExample.class);
                            _context.startActivity(intent);
                        }
                    });





                }
            });



//            quantity_id.addTextChangedListener(new TextWatcher() {
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                    if(s.length() != 0)
//                    {
//
//                        try {
//                            int qut= Integer.parseInt(quantity_id.getText().toString());
//
//                            jsonObject.put("quantity", 200);
//                            updatejson(groupPosition,childPosition,String.valueOf(jsonObject));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                @Override
//                public void beforeTextChanged(CharSequence s, int start,
//                                              int count, int after) {
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start,
//                                          int before, int count) {
//
//
//                }
//            });



        }catch (JSONException err){
            Log.d("Error", err.toString());
        }









        //title_id.setText(childText);
        //chefId.setText(childText);


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }














    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }




}