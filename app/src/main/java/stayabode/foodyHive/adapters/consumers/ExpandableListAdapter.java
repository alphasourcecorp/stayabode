package stayabode.foodyHive.adapters.consumers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.consumers.ConusumerOrderDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    //RecyclerView.LayoutManager RecyclerViewLayoutManager;
    //LinearLayoutManager HorizontalLayout;
    // adapter class object
    //AdapterImage adapter;


    RecyclerView recyclerView;
    ArrayList<String> Number;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout ;
    View ChildView ;
    int RecyclerViewItemPosition ;


    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expand_list_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);

        recyclerView = (RecyclerView)convertView.findViewById(R.id.recyclerview);
        RecyclerViewLayoutManager = new LinearLayoutManager(_context.getApplicationContext());
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(Number);


//        RecyclerViewLayoutManager = new LinearLayoutManager(_context.getApplicationContext());
        LinearLayout layout = (LinearLayout)convertView.findViewById(R.id.imageLayout);



        TextView orderNumber = (TextView) convertView.findViewById(R.id.orderNumber);
        TextView orderStatus = (TextView) convertView.findViewById(R.id.orderStatus);
        TextView orderDate = (TextView) convertView.findViewById(R.id.orderDate);
        TextView orderQty = (TextView) convertView.findViewById(R.id.orderQty);
        TextView paymentBy = (TextView) convertView.findViewById(R.id.paymentBy);


        TextView chefId = (TextView) convertView.findViewById(R.id.chefId);
        TextView viewmore_id = (TextView) convertView.findViewById(R.id.viewmore_id);



        try {
            JSONObject listjsonObject = new JSONObject(childText);
            chefId.setText(listjsonObject.getString("chefName"));
            orderNumber.setText(listjsonObject.getString("orderNo"));
            orderStatus.setText(listjsonObject.getString("consumerOrderStatus"));
            orderDate.setText(listjsonObject.getString("orderCreatedDate"));
            orderQty.setText(listjsonObject.getString("quantity"));
            paymentBy.setText(listjsonObject.getString("paymentMethod"));


            //Set LayoutManager on Recycler View
//            recyclerview.setLayoutManager(RecyclerViewLayoutManager);

            // Adding items to RecyclerView.
            // AddItemsToRecyclerViewArrayList();

            // calling constructor of adapter
            // with source list as a parameter
           // String replace = listjsonObject.getString("disImage").replace("[","");


           // List<String> listdata = new ArrayList<>();


//            JSONObject listjsonObjectImage = new JSONObject(listjsonObject.getString("disImage"));
//            for(int i=0;i<listjsonObjectImage.length();i++)
//            {
//                listdata.add(listjsonObjectImage.getString("disImage"));
//            }

//            JSONObject jsnobject = new JSONObject(listjsonObject.getString("disImage"));
//            Object object=null;
//            JSONArray arrayObj=null;
//            object=jsnobject;
//            arrayObj=(JSONArray) object;
//            JSONArray jArray = arrayObj;
//            if (jArray != null) {
//                for (int i=0;i<jArray.length();i++){
//                    listdata.add(jArray.get(i));
//                }
//            }


            //ArrayList<String> listdata = new ArrayList<String>();
//            JSONArray jArray = (JSONArray)jsonObject;
//            if (jArray != null) {
//                for (int i=0;i<jArray.length();i++){
//                    listdata.add(jArray.getString(i));
//                }
//            }

// Recycler View object

          //  RecyclerViewLayoutManager = new LinearLayoutManager(_context);
            // Set LayoutManager on Recycler View
         //   recyclerview.setLayoutManager(RecyclerViewLayoutManager);

            // Adding items to RecyclerView.
            //AddItemsToRecyclerViewArrayList();

            JSONArray jsonArray = listjsonObject.getJSONArray("disImage");
            List<String> listdata = new ArrayList<String>();
            for (int i=0; i<jsonArray.length(); i++) {
                listdata.add(jsonArray.getString(i));
            }





//            for(int i=0;i<listdata.size();i++)
//            {
//                ImageView image = new ImageView(_context);
//                image.setLayoutParams(new android.view.ViewGroup.LayoutParams(80,60));
//                image.setMaxHeight(20);
//                image.setMaxWidth(20);
//                Glide.with(_context).load(listdata.get(i)).into(image);
//                // Adds the view to the layout
//                layout.addView(image);
//
//
//                //Glide.with(_context).load(image);
//            }
            //List<String> list = new ArrayList<String>();



            // Set Horizontal Layout Manager
            // for Recycler view
//            HorizontalLayout = new LinearLayoutManager(_context.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
//            recyclerview.setLayoutManager(HorizontalLayout);
//            adapter = new AdapterImage(listdata);

            RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(listdata);

            HorizontalLayout = new LinearLayoutManager(_context, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(HorizontalLayout);

            recyclerView.setAdapter(RecyclerViewHorizontalAdapter);



            viewmore_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        Toast.makeText(_context,"Invoice Id : "+listjsonObject.getString("orderInvoiceNo"),Toast.LENGTH_LONG).show();



                        String value=listjsonObject.getString("orderInvoiceNo");
                        Intent i = new Intent(_context, ConusumerOrderDetail.class);
                        i.putExtra("orderInvoiceNo",value);
                        _context.startActivity(i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }



        if(isLastChild)
        {
            viewmore_id.setVisibility(View.VISIBLE);
        }else
        {
            viewmore_id.setVisibility(View.GONE);
        }
        if(childPosition==0)
        {
            recyclerView.setVisibility(View.VISIBLE);
        }else
        {
            recyclerView.setVisibility(View.GONE);
        }







        txtListChild.setText(childText);
        //chefId.setText(childText);


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expand_list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        TextView close_id = (TextView) convertView.findViewById(R.id.close_id);
        TextView compleate_id = (TextView) convertView.findViewById(R.id.compleate_id);
        TextView open_id = (TextView) convertView.findViewById(R.id.open_id);
        ImageView ivGroupIndicator = (ImageView) convertView.findViewById(R.id.ivGroupIndicator);
       // lblListHeader.setTypeface(null, Typeface.BOLD);

        try {
            JSONObject jsonObject = new JSONObject(headerTitle);
            //jsonObject.getString("orderInvoiceNo");
            lblListHeader.setText(jsonObject.getString("orderInvoiceNo"));
            if(jsonObject.getString("open").equals("1"))
            {
                open_id.setVisibility(View.VISIBLE);

            }else
            {
                open_id.setVisibility(View.GONE);
            }
            if(jsonObject.getString("closed").equals("1"))
            {


                compleate_id.setVisibility(View.VISIBLE);
            }else
            {
                compleate_id.setVisibility(View.GONE);
            }
            if(jsonObject.getString("cancelled").equals("1"))
            {


                close_id.setVisibility(View.VISIBLE);
            }else
            {

                close_id.setVisibility(View.GONE);
            }

        }catch (JSONException err){
            Log.d("Error", err.toString());
        }


        lblListHeader.setSelected(isExpanded);
        if (isExpanded) {
            ivGroupIndicator.setImageResource(R.drawable.arrowdown);
        } else {
            ivGroupIndicator.setImageResource(R.drawable.arrowup);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }





    // The adapter class which
// extends RecyclerView Adapter
    public class AdapterImage extends RecyclerView.Adapter<AdapterImage.MyView> {

        // List with String type
        private List<String> list;

        // View Holder class which
        // extends RecyclerView.ViewHolder
        public class MyView
                extends RecyclerView.ViewHolder {

            // Text View
           // TextView textView;
            de.hdodenhof.circleimageview.CircleImageView imageViewc;

            // parameterised constructor for View Holder class
            // which takes the view as a parameter
            public MyView(View view)
            {
                super(view);

                // initialise TextView with id
                //textView = (TextView)view.findViewById(R.id.textview);
                imageViewc = (de.hdodenhof.circleimageview.CircleImageView)view.findViewById(R.id.profile_image);
            }
        }

        // Constructor for adapter class
        // which takes a list of String type
        public AdapterImage(List<String> horizontalList)
        {
            this.list = horizontalList;
        }

        // Override onCreateViewHolder which deals
        // with the inflation of the card layout
        // as an item for the RecyclerView.
        @Override
        public MyView onCreateViewHolder(ViewGroup parent, int viewType)
        {

            // Inflate item.xml using LayoutInflator
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expand_list_img_item, parent, false);

            // return itemView
            return new MyView(itemView);
        }

        // Override onBindViewHolder which deals
        // with the setting of different data
        // and methods related to clicks on
        // particular items of the RecyclerView.
        @Override
        public void onBindViewHolder(final MyView holder, final int position)
        {

            // Set the text of each item of
            // Recycler view with the list items
            //holder.textView.setText(list.get(position));
            //holder.imageView.setText(list.get(position));

            Glide.with(_context).load(list.get(position)).into(holder.imageViewc);

        }

        // Override getItemCount which Returns
        // the length of the RecyclerView.
        @Override
        public int getItemCount()
        {
            return list.size();
        }
    }
}