package stayabode.foodyHive.fragments.chefs;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.chefs.ChefsMainActivity;
import stayabode.foodyHive.adapters.chefs.MenuCategoryAdapter;
import stayabode.foodyHive.adapters.chefs.MenuDishesAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.DishTags;
import stayabode.foodyHive.models.MenuCategory;
import stayabode.foodyHive.models.MenuCuisine;
import stayabode.foodyHive.models.MenuDishType;
import stayabode.foodyHive.models.WeekDays;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.FilePath;
import stayabode.foodyHive.utils.SaveSharedPreference;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheffragmentManager;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheftoolbar;

public class AddChefMenuFragment extends Fragment {

    public static RecyclerView recyclerView;
    public static List<Object> objectList = new ArrayList<>();
    public static AddChefMenuFragment addChefMenuFragment;
    public static Typeface fontBold;
    public static Typeface fontRegular;
    public static Typeface raleWayFontBold;
    public static Typeface ralewayFontRegular;
    public static LinearLayout topViews;
    public static View viewFirst;
    public static View viewSecond;
    public static View viewThird;
    public static View viewFourth;
    public static TextView header;

    File fileuploaded = null;
    File filetoUploadinServer = null;
    File mFolder = null;
    private Bitmap bitmap;
    String strMyImagePath = null;


    // First Visible Code Items Starts

    TextView dishDetailHeader;
    TextView weightSubText;
    TextView preparationSubText;
    TextView portionsHeader;
    TextView forSubText;
    TextView portionPriceSubText;
    TextView uploadFoodPicHeader;
    EditText dishName;
    EditText shortDescription;
    EditText Weight;
    EditText quantityText;
    EditText price;
    EditText preparationTime;
    EditText portionText;
    EditText portionpriceET;
    EditText uploadText;
    ImageView upload;
    ImageView imageView;
    EditText discountPercent;
    Button nextBtn;
    NestedScrollView rootLayout;
    TextView nameCountText;
    TextView descriptionCountText;


    // First Visible Code Items Ends


    // Second Visible Code Items Starts

    TextView categoryHeader;
    TextView dishHeader;
    TextView cuisineHeader;
    TextView daysAvailableHeader;
    Button nextBtntwo;
    RecyclerView daysRecyclerView;
    RecyclerView cuisineRecyclerView;
    RecyclerView dishRecyclerView;
    RecyclerView categoryRecyclerView;
    TextView startTimeText;
    TextView endTimeText;
    CheckBox promotedCheckbox;

    String endTime = "";
    String startTime = "";
    // Second Visible Code Items Ends


    // Third Visible Code Items Starts

    EditText ingredients;
    EditText dishdescription;
    EditText receipeupload;
    TextView ingredientsHeader;
    TextView ingredientsMinLenghtText;
    TextView minLengthDishDescription;
    TextView uploadReceipeHeader;
    Button nextBtnthree;
    ImageView attachPdf;
    RecyclerView tagsRecyclerView;

    // Third Visible Code Items Ends

    // Fourth Visible Code Items Starts

    EditText calories;
    EditText protein;
    EditText carbs;
    EditText fat;
    EditText fibre;
    EditText sugar;
    EditText allerginInfo;
    TextView minLengthAllerginInfoText;
    Button addBtn;
    ImageView arrowBack;
    TextView proteinInfoText;

    // Fourth Visible Code Items Ends

    RelativeLayout firstLayout;
    RelativeLayout secondLayout;
    RelativeLayout thirdLayout;
    RelativeLayout fourthLayout;

    private String format = "";
    Date startTimeDateFormat;
    Date endTimeDateFormat;
    boolean ispriceValid = false;
    boolean isWeightValid = false;
    boolean isPreperationTimeValid = false;
    boolean isDiscountValid = false;
    boolean isQuantityValid = false;
    boolean isPortionValid = false;
    boolean isPortionPriceValid = false;


    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_PDF_REQUEST = 2;
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    private String filePath = "";
    private String fileImageURLFromServer = "";
    private String pdffilePath;
    private Uri pdffileData;


    public static List<MenuCategory> selectedMenuCategoriesforAdd = new ArrayList<>();
    public static List<MenuDishType> selectedMenuDishTypeforAdd = new ArrayList<>();
    public static List<MenuCuisine> selectedMenuCuisineforAdd = new ArrayList<>();
    public static List<WeekDays> selectedWeekDaysforAdd = new ArrayList<>();


    List<Object> dishCategoryListsObject = new ArrayList<>();
    List<Object> dishTypesListsObject = new ArrayList<>();
    List<Object> dishCuisinesListsObject = new ArrayList<>();
    List<Object> daysListObject = new ArrayList<>();
    List<Object> tagsListObject = new ArrayList<>();

    List<MenuCategory> dishCategoryListsArray = new ArrayList<>();
    List<MenuDishType> dishTypesListsArray = new ArrayList<>();
    List<MenuCuisine> dishCuisinesListsArray = new ArrayList<>();
    List<WeekDays> daysListArray = new ArrayList<>();
    List<DishTags> tagsListArray = new ArrayList<>();


    Object getValuesforDishTypes;
    Object getValuesforCuisinessTypes;
    Object getValuesforCategpryTypes;
    Object getValuesforAvailableDaysTypes;


    String availabilityStartTimeToServer = "";
    String availabilityEndTimeToServer = "";

    JSONArray recipeFilePathArray = new JSONArray();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_menu, container, false);
        addChefMenuFragment = this;
        selectedMenuCategoriesforAdd = new ArrayList<>();
        selectedMenuDishTypeforAdd = new ArrayList<>();
        selectedMenuCuisineforAdd = new ArrayList<>();
        selectedWeekDaysforAdd = new ArrayList<>();
        fontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
        fontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        raleWayFontBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Bold.ttf");
        ralewayFontRegular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Raleway-Regular.ttf");
        ChefsMainActivity.searchLayout.setVisibility(View.GONE);
        ChefsMainActivity.chefnavigation.setVisibility(View.GONE);
        ChefsMainActivity.mainBottomLayout.setVisibility(View.GONE);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        viewFirst = rootView.findViewById(R.id.viewFirst);
        viewSecond = rootView.findViewById(R.id.viewSecond);
        viewThird = rootView.findViewById(R.id.viewThird);
        viewFourth = rootView.findViewById(R.id.viewFourth);
        header = rootView.findViewById(R.id.header);
        header.setTypeface(fontBold);
        topViews = rootView.findViewById(R.id.topViews);
        arrowBack = rootView.findViewById(R.id.arrowBack);
        arrowBack.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cheftoolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        cheftoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        firstLayout = rootView.findViewById(R.id.firstLayout);
        secondLayout = rootView.findViewById(R.id.secondLayout);
        thirdLayout = rootView.findViewById(R.id.thirdLayout);
        fourthLayout = rootView.findViewById(R.id.fourthLayout);
        firstLayout.setVisibility(View.VISIBLE);
        secondLayout.setVisibility(View.GONE);
        thirdLayout.setVisibility(View.GONE);
        fourthLayout.setVisibility(View.GONE);


        // First Visible Code Items Starts
        dishDetailHeader = rootView.findViewById(R.id.dishDetailHeader);
        dishName = rootView.findViewById(R.id.dishName);
        shortDescription = rootView.findViewById(R.id.shortDescription);
        Weight = rootView.findViewById(R.id.Weight);
        weightSubText = rootView.findViewById(R.id.weightSubText);
        quantityText = rootView.findViewById(R.id.quantityText);
        price = rootView.findViewById(R.id.price);
        preparationTime = rootView.findViewById(R.id.preparationTime);
        preparationSubText = rootView.findViewById(R.id.preparationSubText);
        portionsHeader = rootView.findViewById(R.id.portionsHeader);
        portionText = rootView.findViewById(R.id.portionText);
        forSubText = rootView.findViewById(R.id.forSubText);
        portionpriceET = rootView.findViewById(R.id.portionpriceET);
        portionPriceSubText = rootView.findViewById(R.id.portionPriceSubText);
        uploadFoodPicHeader = rootView.findViewById(R.id.uploadFoodPicHeader);
        nextBtn = rootView.findViewById(R.id.nextBtn);
        discountPercent = rootView.findViewById(R.id.discountPercent);
        upload = rootView.findViewById(R.id.upload);
        imageView = rootView.findViewById(R.id.imageView);
        uploadText = rootView.findViewById(R.id.uploadText);
        rootLayout = rootView.findViewById(R.id.rootLayout);
        descriptionCountText = rootView.findViewById(R.id.descriptionCountText);
        nameCountText = rootView.findViewById(R.id.nameCountText);

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!price.getText().toString().equals("")) {
                    if (charSequence.charAt(0) != '.') {
                        String[] splitter = Double.valueOf(price.getText().toString()).toString().split("\\.");
                        splitter[0].length();   // Before Decimal Count
                        int decimalLength = splitter[1].length();
                        // After Decimal Count
                        if (price.getText().toString().contains(".")) {
                            if (decimalLength < 3) {
                                ispriceValid = true;
                            } else {
                                Toast.makeText(getContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                                ispriceValid = false;
                            }
                        } else ispriceValid = true;
                    } else ispriceValid = false;
                } else ispriceValid = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onTextChanged(charSequence, i, i1, i2);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!Weight.getText().toString().equals("")) {
                    if (charSequence.charAt(0) != '.') {
                        String[] splitter = Double.valueOf(Weight.getText().toString()).toString().split("\\.");
                        splitter[0].length();   // Before Decimal Count
                        int decimalLength = splitter[1].length();
                        // After Decimal Count
                        if (Weight.getText().toString().contains(".")) {
                            if (decimalLength < 4) {
                                isWeightValid = true;
                            } else {
                                Toast.makeText(getContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                                isWeightValid = false;
                            }
                        } else isWeightValid = true;
                    } else isWeightValid = false;
                } else isWeightValid = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        preparationTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    int prepTime = Integer.valueOf(preparationTime.getText().toString().trim());
                    if (prepTime > 0) {
                        isPreperationTimeValid = true;
                    } else {
                        Toast.makeText(getContext(), "Preperation time should be greater than Zero", Toast.LENGTH_SHORT).show();
                        isPreperationTimeValid = false;
                    }
                } else isPreperationTimeValid = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        quantityText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    int qty = Integer.valueOf(charSequence.toString());
                    if (qty > 0) {
                        isQuantityValid = true;
                    } else {
                        isQuantityValid = false;
                        Toast.makeText(getContext(), "Quantity should be greater than Zero", Toast.LENGTH_SHORT).show();
                    }
                } else isQuantityValid = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        portionText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    int qty = Integer.valueOf(charSequence.toString());
                    if (qty > 0) {
                        isPortionValid = true;
                    } else {
                        isPortionValid = false;
                        Toast.makeText(getContext(), "Quantity should be greater than Zero", Toast.LENGTH_SHORT).show();
                    }
                } else isPortionValid = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        portionpriceET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    if (charSequence.charAt(0) != '.') {
                        String[] splitter = Double.valueOf(charSequence.toString()).toString().split("\\.");
                        splitter[0].length();   // Before Decimal Count
                        int decimalLength = splitter[1].length();
                        // After Decimal Count

                        if (charSequence.toString().contains(".")) {
                            if (decimalLength < 3) {
                                isPortionPriceValid = true;
                            } else {
                                Toast.makeText(getContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                                isPortionPriceValid = false;
                            }
                        } else isPortionPriceValid = true;
                    } else isPortionPriceValid = false;
                } else isPortionPriceValid = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        discountPercent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {

                    float discount = Float.valueOf(charSequence.toString().replaceAll("%", "").trim());
                    if (/*discount > 0 &&*/ discount <= 100) {
                        isDiscountValid = true;
                    } else {
                        isDiscountValid = false;
                        Toast.makeText(getContext(), "Invalid format", Toast.LENGTH_SHORT).show();
                    }
                } else isDiscountValid = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                if ((ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);
                    }
                } else {

                    showFileChooser();
                }
            }
        });

        dishName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nameCountText.setText((charSequence.length()) + "/100");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        shortDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                descriptionCountText.setText((charSequence.length()) + "/100");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        dishDetailHeader.setTypeface(fontBold);
        dishName.setTypeface(fontRegular);
        shortDescription.setTypeface(fontRegular);
        weightSubText.setTypeface(fontRegular);
        Weight.setTypeface(fontRegular);
        portionPriceSubText.setTypeface(fontRegular);
        quantityText.setTypeface(fontRegular);
        price.setTypeface(fontRegular);
        portionpriceET.setTypeface(fontRegular);
        portionText.setTypeface(fontRegular);
        portionsHeader.setTypeface(fontBold);
        uploadFoodPicHeader.setTypeface(fontBold);
        //upload.setTypeface(fontRegular);
        forSubText.setTypeface(fontRegular);
        nextBtn.setTypeface(fontRegular);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (dishName.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Please Add Dish Name", Toast.LENGTH_SHORT).show();
                } else if (shortDescription.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Please Add short Description", Toast.LENGTH_SHORT).show();
                } else if (Weight.getText().toString().trim().equals("") || !isWeightValid) {
                    Toast.makeText(getContext(), "Please Add Weight in valid format", Toast.LENGTH_SHORT).show();
                } else if (quantityText.getText().toString().trim().equals("") || !isQuantityValid) {
                    Toast.makeText(getContext(), "Please Add valid Quantity", Toast.LENGTH_SHORT).show();
                } else if (preparationTime.getText().toString().trim().equals("") || !isPreperationTimeValid) {
                    Toast.makeText(getContext(), "Please Add preparation Time", Toast.LENGTH_SHORT).show();
                } else if (portionText.getText().toString().trim().equals("") || !isPortionValid) {
                    Toast.makeText(getContext(), "Please Add valid portion", Toast.LENGTH_SHORT).show();
                } else if (portionpriceET.getText().toString().trim().equals("") || !isPortionPriceValid) {
                    Toast.makeText(getContext(), "Please Add valid Portion Price", Toast.LENGTH_SHORT).show();
                } /*else if (filePath == null) {
                    Toast.makeText(getContext(), "Please Add Dish Image", Toast.LENGTH_SHORT).show();
                }*/ else if (price.getText().toString().trim().equals("") || !ispriceValid) {
                    Toast.makeText(getContext(), "Please Add Price in valid format", Toast.LENGTH_SHORT).show();
                } else if (!discountPercent.getText().toString().equals("") && !isDiscountValid) {
//                    if(!isDiscountValid)
//                    {
                    Toast.makeText(getContext(), "Please Add valid discount%", Toast.LENGTH_SHORT).show();
//                    }
//                    else
//                    {
//                        Log.d("Test","discount");
//                    }
                } else {
                    if (getArguments().getString("From").equals("Edit")) {
                        viewFirst.setAlpha(10);
                        viewSecond.setAlpha(10);

                        firstLayout.setVisibility(View.GONE);
                        secondLayout.setVisibility(View.VISIBLE);
                        header.requestFocus();
                        rootLayout.fullScroll(View.FOCUS_UP);
                        arrowBack.setVisibility(View.VISIBLE);
                        thirdLayout.setVisibility(View.GONE);
                        fourthLayout.setVisibility(View.GONE);
                    } else {
                        if (filetoUploadinServer == null) {
                            Toast.makeText(getContext(), "Please Add Dish Image", Toast.LENGTH_SHORT).show();
                        } else {
                            viewFirst.setAlpha(10);
                            viewSecond.setAlpha(10);
                            firstLayout.setVisibility(View.GONE);
                            secondLayout.setVisibility(View.VISIBLE);
                            header.requestFocus();
                            rootLayout.fullScroll(View.FOCUS_UP);
                            arrowBack.setVisibility(View.VISIBLE);
                            thirdLayout.setVisibility(View.GONE);
                            fourthLayout.setVisibility(View.GONE);
                        }
                    }
                }

            }
        });
        // First Visible Code Items Ends


        categoryHeader = rootView.findViewById(R.id.categoryHeader);
        dishHeader = rootView.findViewById(R.id.dishHeader);
        cuisineHeader = rootView.findViewById(R.id.cuisineHeader);
        daysAvailableHeader = rootView.findViewById(R.id.daysAvailableHeader);
        nextBtntwo = rootView.findViewById(R.id.nextBtntwo);
        daysRecyclerView = rootView.findViewById(R.id.daysRecyclerView);
        tagsRecyclerView = rootView.findViewById(R.id.tagsRecyclerView);
        cuisineRecyclerView = rootView.findViewById(R.id.cuisineRecyclerView);
        dishRecyclerView = rootView.findViewById(R.id.dishRecyclerView);
        categoryRecyclerView = rootView.findViewById(R.id.categoryRecyclerView);
        endTimeText = rootView.findViewById(R.id.endTimeText);
        startTimeText = rootView.findViewById(R.id.startTimeText);
        promotedCheckbox = rootView.findViewById(R.id.promotedCheckbox);


        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        daysRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        dishRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        cuisineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        tagsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));


        categoryHeader.setTypeface(fontBold);
        dishHeader.setTypeface(fontBold);
        cuisineHeader.setTypeface(fontBold);
        daysAvailableHeader.setTypeface(fontBold);


        categoryHeader.setTypeface(fontBold);
        dishHeader.setTypeface(fontBold);
        cuisineHeader.setTypeface(fontBold);
        daysAvailableHeader.setTypeface(fontBold);

        startTimeText.setFocusable(false);
        startTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        showTime(selectedHour, selectedMinute, true);


                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        endTimeText.setFocusable(false);
        endTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        showTime(selectedHour, selectedMinute, false);


                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        nextBtntwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startTime == "" || endTime == "") {
                    Toast.makeText(getContext(), "Please add Start and End time", Toast.LENGTH_SHORT).show();
                } else if (!validTime()) {
                    Toast.makeText(getContext(), "Please select 'End time' greater than 'Start time'", Toast.LENGTH_LONG).show();
                } else if (selectedMenuCategoriesforAdd.size() < 1) {
                    Toast.makeText(getContext(), "Please select category", Toast.LENGTH_LONG).show();
                } else if (selectedMenuDishTypeforAdd.size() < 1) {
                    Toast.makeText(getContext(), "Please select dish type", Toast.LENGTH_LONG).show();
                } else if (selectedMenuCuisineforAdd.size() < 1) {
                    Toast.makeText(getContext(), "Please select cuisine", Toast.LENGTH_LONG).show();
                } else if (selectedWeekDaysforAdd.size() < 1) {
                    Toast.makeText(getContext(), "Please select week day", Toast.LENGTH_LONG).show();
                } else {
                    viewFirst.setAlpha(10);
                    viewSecond.setAlpha(10);
                    viewThird.setAlpha(10);

                    firstLayout.setVisibility(View.GONE);
                    secondLayout.setVisibility(View.GONE);
                    thirdLayout.setVisibility(View.VISIBLE);
                    arrowBack.setVisibility(View.VISIBLE);
                    fourthLayout.setVisibility(View.GONE);
                }
            }
        });


        ingredientsHeader = rootView.findViewById(R.id.ingredientsHeader);
        ingredientsMinLenghtText = rootView.findViewById(R.id.ingredientsMinLenghtText);
        ingredients = rootView.findViewById(R.id.ingredients);
        dishdescription = rootView.findViewById(R.id.dishdescription);
        minLengthDishDescription = rootView.findViewById(R.id.minLengthDishDescription);
        uploadReceipeHeader = rootView.findViewById(R.id.uploadReceipeHeader);
        receipeupload = rootView.findViewById(R.id.receipeupload);
        nextBtnthree = rootView.findViewById(R.id.nextBtnthree);
        attachPdf = rootView.findViewById(R.id.attachPdf);

        ingredientsHeader.setTypeface(fontBold);
        ingredients.setTypeface(fontRegular);
        dishdescription.setTypeface(fontRegular);
        uploadReceipeHeader.setTypeface(fontBold);
        receipeupload.setTypeface(fontRegular);
        minLengthDishDescription.setTypeface(fontRegular);
        ingredientsMinLenghtText.setTypeface(fontRegular);
        nextBtn.setTypeface(fontRegular);

        ingredients.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ingredientsMinLenghtText.setText(charSequence.length() + "/100");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        dishdescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                minLengthDishDescription.setText(charSequence.length() + "/300");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        nextBtnthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ingredients.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Please add Ingredients", Toast.LENGTH_SHORT).show();
                } else if (dishdescription.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Please add  Description", Toast.LENGTH_SHORT).show();
                } else {
                    viewFirst.setAlpha(10);
                    viewSecond.setAlpha(10);
                    viewThird.setAlpha(10);
                    viewFourth.setAlpha(10);

                    firstLayout.setVisibility(View.GONE);
                    secondLayout.setVisibility(View.GONE);
                    thirdLayout.setVisibility(View.GONE);
                    fourthLayout.setVisibility(View.VISIBLE);
                    arrowBack.setVisibility(View.VISIBLE);
                }


            }
        });

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (secondLayout.getVisibility() == View.VISIBLE) {
                    arrowBack.setVisibility(View.GONE);
                    firstLayout.setVisibility(View.VISIBLE);
                    viewFirst.setAlpha(10);
                    viewSecond.setAlpha((float) 0.3);
                    viewThird.setAlpha((float) 0.3);
                    viewFourth.setAlpha((float) 0.3);
                    secondLayout.setVisibility(View.GONE);
                    thirdLayout.setVisibility(View.GONE);
                    fourthLayout.setVisibility(View.GONE);
                } else if (thirdLayout.getVisibility() == View.VISIBLE) {
                    arrowBack.setVisibility(View.VISIBLE);
                    viewFirst.setAlpha(10);
                    viewSecond.setAlpha(10);
                    viewThird.setAlpha((float) 0.3);
                    viewFourth.setAlpha((float) 0.3);
                    firstLayout.setVisibility(View.GONE);
                    secondLayout.setVisibility(View.VISIBLE);
                    thirdLayout.setVisibility(View.GONE);
                    fourthLayout.setVisibility(View.GONE);
                } else if (fourthLayout.getVisibility() == View.VISIBLE) {
                    arrowBack.setVisibility(View.VISIBLE);
                    viewFirst.setAlpha(10);
                    viewSecond.setAlpha(10);
                    viewThird.setAlpha(10);
                    viewFourth.setAlpha((float) 0.3);
                    firstLayout.setVisibility(View.GONE);
                    secondLayout.setVisibility(View.GONE);
                    thirdLayout.setVisibility(View.VISIBLE);
                    fourthLayout.setVisibility(View.GONE);
                }
            }
        });

        attachPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPDFFileFileChooser();
            }
        });


        calories = rootView.findViewById(R.id.calories);
        protein = rootView.findViewById(R.id.protein);
        fat = rootView.findViewById(R.id.fat);
        carbs = rootView.findViewById(R.id.carbs);
        fibre = rootView.findViewById(R.id.fibre);
        sugar = rootView.findViewById(R.id.sugar);
        allerginInfo = rootView.findViewById(R.id.allerginInfo);
        minLengthAllerginInfoText = rootView.findViewById(R.id.minLengthAllerginInfoText);
        addBtn = rootView.findViewById(R.id.addBtn);
        proteinInfoText = rootView.findViewById(R.id.proteinInfoText);

        calories.setTypeface(fontRegular);
        protein.setTypeface(fontRegular);
        fat.setTypeface(fontRegular);
        carbs.setTypeface(fontRegular);
        fibre.setTypeface(fontRegular);
        sugar.setTypeface(fontRegular);
        allerginInfo.setTypeface(fontRegular);
        minLengthAllerginInfoText.setTypeface(fontRegular);
        addBtn.setTypeface(fontRegular);

        protein.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                chechTotalNutritionValue();
                if (charSequence.length() > 0) {
                    if (Float.parseFloat(charSequence.toString()) > 100) {
                        Toast.makeText(getContext(), "Enter protein value below 100", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        fibre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                chechTotalNutritionValue();
                if (charSequence.length() > 0) {
                    if (Float.parseFloat(charSequence.toString()) > 100) {
                        Toast.makeText(getContext(), "Enter fibre value below 100", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        fat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                chechTotalNutritionValue();
                if (charSequence.length() > 0) {
                    if (Float.parseFloat(charSequence.toString()) > 100) {
                        Toast.makeText(getContext(), "Enter fat value below 100", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        sugar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                chechTotalNutritionValue();
                if (charSequence.length() > 0) {
                    if (Float.parseFloat(charSequence.toString()) > 100) {
                        Toast.makeText(getContext(), "Enter sugar value below 100", Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        carbs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                chechTotalNutritionValue();
                if (charSequence.length() > 0) {
                    if (Float.parseFloat(charSequence.toString()) > 100) {
                        Toast.makeText(getContext(), "Enter carbs value below 100", Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        allerginInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                minLengthAllerginInfoText.setText(charSequence.length() + "/200");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        if (getArguments().getString("From").equals("Edit")) {
            getDishOfValues();
            addBtn.setText("Update");

        } else {
            addBtn.setText("Add");
            getDishOfValues();
        }


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (calories.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please Add Calories", Toast.LENGTH_SHORT).show();
                } else if (protein.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please Add Protein", Toast.LENGTH_SHORT).show();
                } else if (fat.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please Add fat", Toast.LENGTH_SHORT).show();
                } else if (carbs.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please Add Carbs", Toast.LENGTH_SHORT).show();
                } else if (fibre.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please Add fibre", Toast.LENGTH_SHORT).show();
                } else if (sugar.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please Add Sugar", Toast.LENGTH_SHORT).show();
                } else if (!chechTotalNutritionValue()) {
                    proteinInfoText.setVisibility(View.VISIBLE);
                } else {
                    proteinInfoText.setVisibility(View.GONE);

                    if (getArguments().getString("From").equals("Edit")) {
                        if (pdffileData != null && filetoUploadinServer != null) {

                            editChefsMenuItem(filePath);
                        } else if (pdffileData == null && filetoUploadinServer != null) {

                            editChefsMenuItemWithoutPDF(filePath);
                        } else if (pdffileData != null && filetoUploadinServer == null) {

                            editChefsMenuItemWithoutImage(filePath);
                        } else {

                            editChefsMenuItemWithoutPDFAndImage(filePath);

                        }

                    } else {
                        if (pdffileData != null) {
                            addChefsMenuItem(filePath);
                        } else {
                            addChefMenuItemWihtoutPdf(filePath);
                        }
                    }
                }

            }
        });

        /**
         Requesting storage permission
         **/
        requestStoragePermission();


        return rootView;
    }

    /**
     * Add Dish Items Without PDF Selecting
     **/
    private void addChefMenuItemWihtoutPdf(String filePath) {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading item details, Please wait..");
        progressDialog.show();
        JSONObject inputObject = new JSONObject();

        try {


            try {
                inputObject.put("name", dishName.getText().toString());
                inputObject.put("eachDayQuanity", quantityText.getText().toString());
                inputObject.put("weight", Weight.getText().toString());
                //inputObject.put("eachDayQuanity", "1");
                inputObject.put("portionPrice", portionpriceET.getText().toString());
                inputObject.put("forPortions", portionText.getText().toString());
                JSONArray dishImagePathArray = new JSONArray();
                inputObject.put("dishImagePath", dishImagePathArray);
                inputObject.put("shortDescription", shortDescription.getText().toString());

                JSONArray availableOnDaysArray = new JSONArray();

                for (int i = 0; i < selectedWeekDaysforAdd.size(); i++) {
                    availableOnDaysArray.put(selectedWeekDaysforAdd.get(i).getIdForCheck());
                }
                inputObject.put("availableOnDays", availableOnDaysArray);


                JSONArray dishCategoryArray = new JSONArray();
                JSONArray dishCuisineArray = new JSONArray();
                JSONArray typeOfDishArray = new JSONArray();

                for (int i = 0; i < selectedMenuCategoriesforAdd.size(); i++) {
                    dishCategoryArray.put(selectedMenuCategoriesforAdd.get(i).getIdForCheck());
                }


                for (int i = 0; i < selectedMenuCuisineforAdd.size(); i++) {
                    dishCuisineArray.put(selectedMenuCuisineforAdd.get(i).getIdForCheck());
                }

                for (int i = 0; i < selectedMenuDishTypeforAdd.size(); i++) {
                    typeOfDishArray.put(selectedMenuDishTypeforAdd.get(i).getIdForCheck());
                }


                inputObject.put("dishCategory", dishCategoryArray);
                inputObject.put("dishCuisine", dishCuisineArray);
                inputObject.put("typeOfDish", typeOfDishArray);
                if (!discountPercent.getText().toString().equals("")) {
                    inputObject.put("discountPercent", Integer.parseInt(discountPercent.getText().toString()));
                } else {
                    inputObject.put("discountPercent", 0);
                }

                inputObject.put("subscriptionOfferPercent", 0);
                inputObject.put("bulkOrderOfferPercent", 0);
                inputObject.put("ingredients", ingredients.getText().toString());
                JSONArray accoladesArray = new JSONArray();
                JSONObject accoladesObject = new JSONObject();
                accoladesObject.put("title", "");
                accoladesObject.put("accoladeDescription", "");
                accoladesArray.put(accoladesObject);

                inputObject.put("accolades", accoladesArray);

                inputObject.put("description", dishdescription.getText().toString());
                JSONArray tagsArray = new JSONArray();
                tagsArray.put(1);
                inputObject.put("tags", tagsArray);

                JSONArray recipeFilePathArray = new JSONArray();
                inputObject.put("recipeFilePath", recipeFilePathArray);

                if (promotedCheckbox.isChecked()) {
                    inputObject.put("isPromotedOrExclusive", true);
                } else {
                    inputObject.put("isPromotedOrExclusive", false);
                }
                inputObject.put("autoAcceptOrder", true);
                JSONObject nutritionObject = new JSONObject();
                nutritionObject.put("energyCalories", calories.getText().toString());
                nutritionObject.put("carbohydrates", carbs.getText().toString());
                nutritionObject.put("sugar", sugar.getText().toString());
                nutritionObject.put("fibre", fibre.getText().toString());
                nutritionObject.put("protein", protein.getText().toString());
                nutritionObject.put("fat", fat.getText().toString());
                nutritionObject.put("allergenInformation", allerginInfo.getText().toString());
                inputObject.put("nutrition", nutritionObject);
                Date availabilityStartDate = null;
                Date availabilityEndDate = null;
                try {
                    availabilityStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm a").parse(availabilityStartTimeToServer);

                } catch (ParseException e) {
                    availabilityStartDate = new SimpleDateFormat("yyyy-MM-dd HH:m a").parse(availabilityStartTimeToServer);

                    e.printStackTrace();
                }

                try {
                    availabilityEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm a").parse(availabilityEndTimeToServer);
                } catch (Exception e) {
                    availabilityEndDate = new SimpleDateFormat("yyyy-MM-dd HH:m a").parse(availabilityEndTimeToServer);
                    e.printStackTrace();
                }
                try {
                    SimpleDateFormat finalUTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//                    finalUTCFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String startDate = finalUTCFormat.format(availabilityStartDate);
                    String renewalDate = finalUTCFormat.format(availabilityEndDate);
                    inputObject.put("availabilityStartTime", startDate);
                    inputObject.put("availabilityEndTime", renewalDate);
                } catch (Exception e) {
                    inputObject.put("availabilityStartTime", "2020-08-12T07:22:23.392Z");
                    inputObject.put("availabilityEndTime", "2020-08-12T13:21:26.993Z");
                    e.printStackTrace();
                }

                inputObject.put("price", price.getText().toString());
                inputObject.put("preparationTime", preparationTime.getText().toString());
                inputObject.put("chefId", SaveSharedPreference.getLoggedInWorkFlowID(getContext()));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            pdffilePath = FilePath.getLocalPath(getContext(), pdffileData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("inputObject", inputObject.toString());

        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(getContext(), uploadId, APIBaseURL.addChefsMenuItems)
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .addHeader("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(getContext()))
                    .setMethod("POST")
                    .addParameter("request", inputObject.toString())
                    .addFileToUpload(filetoUploadinServer.getAbsolutePath(), "dishImagePath")
                    .setNotificationConfig(new UploadNotificationConfig().setRingToneEnabled(false))
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {
                            progressDialog.show();
                        }


                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "lease try againP", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();
                        }


                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Item Added Successfully", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();
                            onBackPressed();
                        }


                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();
                        }
                    })
                    .setMaxRetries(2)
                    .startUpload();
        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Requesting permission
     **/
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(addChefMenuFragment.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(addChefMenuFragment.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(addChefMenuFragment.getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    /**
     * This method will be called when the user will tap on allow or deny
     **/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(addChefMenuFragment.getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                // Toast.makeText(addChefMenuFragment.getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * Custom method Fr Nutrition Validation
     **/
    private boolean chechTotalNutritionValue() {
        try {
            int proteinVal = Integer.parseInt(protein.getText().toString());
            int fatVal = Integer.parseInt(fat.getText().toString());
            int carbsVal = Integer.parseInt(carbs.getText().toString());
            int sugarVal = Integer.parseInt(sugar.getText().toString());
            int fibreVal = Integer.parseInt(fibre.getText().toString());


            if (proteinVal + fatVal + carbsVal + sugarVal + fibreVal != 100) {
                proteinInfoText.setVisibility(View.VISIBLE);
                return false;
            } else {
                proteinInfoText.setVisibility(View.GONE);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Time Picker with selected time to show in edit text fields
     **/
    public void showTime(int hour, int min, Boolean checkStartorEndTime) {
        Calendar datetime = Calendar.getInstance();
        datetime.set(Calendar.HOUR_OF_DAY, hour);
        datetime.set(Calendar.MINUTE, min);

        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
            format = "AM";
        if (datetime.get(Calendar.AM_PM) == Calendar.PM)
            format = "PM";


        String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime.get(Calendar.HOUR) + "";


        if (checkStartorEndTime) {

            int minutes = datetime.get(Calendar.MINUTE);
            String minsToShow = "";
            if (minutes < 10) {
                minsToShow = "0" + minutes;
            } else {
                minsToShow = String.valueOf(minutes);
            }

            String hoursToShowinTwoDigits = "";
            if (Integer.parseInt(strHrsToShow) < 10) {
                hoursToShowinTwoDigits = "0" + Integer.parseInt(strHrsToShow);
            } else {
                hoursToShowinTwoDigits = strHrsToShow;

            }
            startTimeText.setText(hoursToShowinTwoDigits + ":" + minsToShow + " " + format);

            startTime = String.valueOf(new StringBuilder().append(hour).append(":").append(min)
                    .append(" ").append(format));
            Log.v("startTime", startTime);

            validTime();

            //create Date object
            Date date = new Date();


            SimpleDateFormat sdf = new SimpleDateFormat("MM");
            SimpleDateFormat sdfForDay = new SimpleDateFormat("dd");
            SimpleDateFormat sdfForYear = new SimpleDateFormat("yyyy");
            String currentmonth = sdf.format(date);
            String currentDate = sdfForDay.format(date);
            String currentYear = sdfForYear.format(date);


            availabilityStartTimeToServer = currentYear + "-" + currentmonth + "-" + currentDate + " " + startTime;
            Log.v("availabilityStartTimeToServer", availabilityStartTimeToServer);


        } else {

            int minutes = datetime.get(Calendar.MINUTE);
            String minsToShow = "";
            if (minutes < 10) {
                minsToShow = "0" + minutes;
            } else {
                minsToShow = String.valueOf(minutes);
            }

            String hoursToShowinTwoDigits = "";
            if (Integer.parseInt(strHrsToShow) < 10) {
                hoursToShowinTwoDigits = "0" + Integer.parseInt(strHrsToShow);
            } else {
                hoursToShowinTwoDigits = strHrsToShow;

            }

            endTimeText.setText(hoursToShowinTwoDigits + ":" + minsToShow + " " + format);

            endTime = String.valueOf(new StringBuilder().append(hour).append(":").append(min)
                    .append(" ").append(format));


            validTime();

            //create Date object
            Date date = new Date();


            SimpleDateFormat sdf = new SimpleDateFormat("MM");
            SimpleDateFormat sdfForDay = new SimpleDateFormat("dd");
            SimpleDateFormat sdfForYear = new SimpleDateFormat("yyyy");
            String currentmonth = sdf.format(date);
            String currentDate = sdfForDay.format(date);
            String currentYear = sdfForYear.format(date);

            availabilityEndTimeToServer = currentYear + "-" + currentmonth + "-" + currentDate + " " + endTime;


        }


    }

    /**
     * Custom Method To Validate Start time is less than end time
     **/
    private boolean validTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        try {
            Date d1 = sdf.parse(startTime);
            Date d2 = sdf.parse(endTime);
            long elapsed = d2.getTime() - d1.getTime();
            Log.v("elapsedTime", elapsed + "");
            if (elapsed > 0) {
                Toast.makeText(getContext(), "Time Selection Successfull", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(getContext(), "Please select 'End time' greater than 'Start time'", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Show File Choosed from Gallery to Pick an Image
     **/
    private void showFileChooser() {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //output = new File(dir, System.currentTimeMillis() / 1000 + "profile" + ".jpeg");
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_IMAGE_REQUEST);
    }

    /**
     * Show File Chooser from Internal Storage to Pick an PDF File
     **/
    private void showPDFFileFileChooser() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    /**
     * To Get the file path of selected gallery image
     **/
    private String getFilePath(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToFirst();
        String res = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        cursor.close();
        return res;
    }


    /**
     * To get List of Categories like breakfast,cuisine types(GET)
     **/

    public void getDishOfValues() {
        String url = APIBaseURL.searchOptions;

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray daysArray = new JSONArray();
                    JSONArray dishCategoriesArray = new JSONArray();
                    JSONArray dishCuisinesArray = new JSONArray();
                    JSONArray dishTypesArray = new JSONArray();
                    JSONArray dishTagsArray = new JSONArray();

                    if (jsonObject.has("dishTypes")) {
                        dishTypesArray = jsonObject.getJSONArray("dishTypes");
                    }

                    if (jsonObject.has("days")) {
                        daysArray = jsonObject.getJSONArray("days");
                    }

                    if (jsonObject.has("dishCategories")) {
                        dishCategoriesArray = jsonObject.getJSONArray("dishCategories");
                    }

                    if (jsonObject.has("dishCuisines")) {
                        dishCuisinesArray = jsonObject.getJSONArray("dishCuisines");
                    }

                    if (jsonObject.has("dishTags")) {
                        dishTagsArray = jsonObject.getJSONArray("dishTags");
                    }

                    dishCategoryListsArray = new ArrayList<>();
                    for (int i = 0; i < dishCategoriesArray.length(); i++) {
                        JSONObject dishCategoriesObject = dishCategoriesArray.getJSONObject(i);

                        MenuCategory menuCategory = new MenuCategory();
                        menuCategory.setName(dishCategoriesObject.optString("label"));
                        menuCategory.setId(dishCategoriesObject.optString("value"));
                        menuCategory.setIdForCheck(dishCategoriesObject.optInt("value"));
                        menuCategory.setSelected(false);
                        dishCategoryListsObject.add(menuCategory);
                        dishCategoryListsArray.add(menuCategory);
                    }

                    dishTypesListsArray = new ArrayList<>();
                    for (int i = 0; i < dishTypesArray.length(); i++) {
                        JSONObject dishTypesObject = dishTypesArray.getJSONObject(i);
                        MenuDishType menuDishType = new MenuDishType();
                        menuDishType.setName(dishTypesObject.optString("label"));
                        menuDishType.setId(dishTypesObject.optString("value"));
                        menuDishType.setIdForCheck(dishTypesObject.optInt("value"));
                        menuDishType.setSelected(false);
                        dishTypesListsObject.add(menuDishType);
                        dishTypesListsArray.add(menuDishType);

                    }

                    dishCuisinesListsArray = new ArrayList<>();
                    for (int i = 0; i < dishCuisinesArray.length(); i++) {
                        JSONObject dishCuisinesObject = dishCuisinesArray.getJSONObject(i);
                        MenuCuisine menuCuisine = new MenuCuisine();
                        menuCuisine.setName(dishCuisinesObject.optString("label"));
                        menuCuisine.setId(dishCuisinesObject.optString("value"));
                        menuCuisine.setIdForCheck(dishCuisinesObject.optInt("value"));
                        menuCuisine.setSelected(false);
                        dishCuisinesListsObject.add(menuCuisine);
                        dishCuisinesListsArray.add(menuCuisine);

                    }

                    daysListArray = new ArrayList<>();
                    for (int i = 0; i < daysArray.length(); i++) {
                        JSONObject daysObject = daysArray.getJSONObject(i);
                        WeekDays weekDays = new WeekDays();
                        weekDays.setWeekDayName(daysObject.optString("label"));
                        weekDays.setId(daysObject.optString("value"));
                        weekDays.setIdForCheck(daysObject.optInt("value"));
                        weekDays.setSelected(false);
                        daysListObject.add(weekDays);
                        daysListArray.add(weekDays);

                    }


                    tagsListArray = new ArrayList<>();
                    for (int i = 0; i < dishTagsArray.length(); i++) {
                        JSONObject dishTagsObject = dishTagsArray.getJSONObject(i);
                        DishTags dishTags = new DishTags();
                        dishTags.setName(dishTagsObject.optString("label"));
                        dishTags.setId(dishTagsObject.optString("value"));
                        dishTags.setIdForCheck(dishTagsObject.optInt("value"));
                        dishTags.setSelected(false);
                        tagsListObject.add(dishTags);
                        tagsListArray.add(dishTags);

                    }

                    if (getArguments().getString("From").equals("Edit")) {

                        getEditItemDetails();

                        tagsRecyclerView.setAdapter(new MenuCategoryAdapter(getContext(), tagsListObject, fontBold, fontRegular, "Add"));
                    } else {
                        categoryRecyclerView.setAdapter(new MenuCategoryAdapter(getContext(), dishCategoryListsObject, fontBold, fontRegular, "Add"));


                        dishRecyclerView.setAdapter(new MenuDishesAdapter(getContext(), dishTypesListsObject, fontBold, fontRegular, "Add"));


                        cuisineRecyclerView.setAdapter(new MenuCategoryAdapter(getContext(), dishCuisinesListsObject, fontBold, fontRegular, "Add"));

                        daysRecyclerView.setAdapter(new MenuCategoryAdapter(getContext(), daysListObject, fontBold, fontRegular, "Add"));

                        tagsRecyclerView.setAdapter(new MenuCategoryAdapter(getContext(), tagsListObject, fontBold, fontRegular, "Add"));
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
                    ViewGroup viewGroup = ((Activity) getContext()).findViewById(android.R.id.content);

                    //then we will inflate the custom alert dialog xml that we created
                    View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.access_token_expired_dialog, viewGroup, false);

                    Button buttonreset = dialogView.findViewById(R.id.buttonreset);
                    Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                    ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);

                    //Now we need an AlertDialog.Builder object
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());

                    //setting the view of the builder to our custom view that we already inflated
                    builder.setView(dialogView);

                    //finally creating the alert dialog and displaying it
                    android.app.AlertDialog alertDialog = builder.create();


                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                            ChefsMainActivity.logout();

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
                    Toast.makeText(getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                }
            }
        }, getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "get_dish_taqs");
    }

    /**
     * Custom Method to go back to previous fragment
     **/
    public void onBackPressed() {
        FragmentManager fm = cheffragmentManager;
        fm.popBackStack();
    }

    /**
     * Get Dish Item Detail by Dish Id
     **/
    public void getEditItemDetails() {

        String url = APIBaseURL.getDishById + getArguments().getString("ID");
        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("EditItemResponse", response);

                try {
                    imageView.setVisibility(View.VISIBLE);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    try {
                        Glide.with(getContext()).load(dataObject.getJSONArray("dishImagePath").optString(0)).placeholder(R.drawable.foodi_logo_left_image).into(imageView);
                        Log.d("FunctionCall", "FunctionCall");
                        fileImageURLFromServer = dataObject.getJSONArray("dishImagePath").optString(0);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    recipeFilePathArray = new JSONArray();
                    if (dataObject.has("recipeFilePath")) {
                        recipeFilePathArray = dataObject.getJSONArray("recipeFilePath");
                    }
                    if (recipeFilePathArray.length() != 0) {

                        URL url = null;
                        try {
                            url = new URL(recipeFilePathArray.get(0).toString());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        Uri uri = Uri.parse(recipeFilePathArray.get(0).toString());

                        receipeupload.setText(recipeFilePathArray.get(0).toString());

                        pdffilePath = recipeFilePathArray.get(0).toString();

                    }


                    dishName.setText(dataObject.optString("name"));

                    price.setText(String.format("%.2f", Double.valueOf(dataObject.optString("price"))));
                    preparationTime.setText(dataObject.optString("preparationTime"));
                    Weight.setText(dataObject.optString("weight"));

                    portionpriceET.setText(String.format("%.2f", Double.valueOf(dataObject.optString("portionPrice"))));

                    portionText.setText(dataObject.optString("forPortions"));
                    shortDescription.setText(dataObject.optString("shortDescription"));
                    dishdescription.setText(dataObject.optString("description"));
                    ingredients.setText(dataObject.optString("ingredients"));
                    JSONObject nutritionObject = dataObject.getJSONObject("nutrition");
                    discountPercent.setText(dataObject.optString("discountPercent"));
                    quantityText.setText(dataObject.optString("eachDayQuanity"));
                    protein.setText(nutritionObject.optString("protein"));
                    fat.setText(nutritionObject.optString("fat"));
                    carbs.setText(nutritionObject.optString("carbohydrates"));
                    fibre.setText(nutritionObject.optString("fibre"));
                    sugar.setText(nutritionObject.optString("sugar"));
                    calories.setText(nutritionObject.optString("energyCalories"));
                    allerginInfo.setText(nutritionObject.optString("allergenInformation"));

                    if (dataObject.optBoolean("isPromotedOrExclusive")) {
                        promotedCheckbox.setChecked(true);
                    } else {
                        promotedCheckbox.setChecked(false);
                    }

                    JSONArray dishCategoryArray = new JSONArray();

                    JSONArray dishCuisineArray = new JSONArray();

                    JSONArray typeOfDishArray = new JSONArray();

                    JSONArray availableOnDaysArray = new JSONArray();


                    if (dataObject.has("availableOnDays")) {
                        availableOnDaysArray = dataObject.getJSONArray("availableOnDays");
                    }

                    if (dataObject.has("dishCategory")) {
                        dishCategoryArray = dataObject.getJSONArray("dishCategory");
                    }


                    if (dataObject.has("dishCuisine")) {
                        dishCuisineArray = dataObject.getJSONArray("dishCuisine");
                    }

                    if (dataObject.has("typeOfDish")) {
                        typeOfDishArray = dataObject.getJSONArray("typeOfDish");
                    }


                    if (typeOfDishArray.length() != 0) {

                        ArrayList<String> stringList = new ArrayList<>();

                        for (int i = 0; i < typeOfDishArray.length(); i++) {

                            getValuesforDishTypes = typeOfDishArray.get(i);


                            for (int j = 0; j < dishTypesListsArray.size(); j++) {
                                if (getValuesforDishTypes.equals(dishTypesListsArray.get(j).getIdForCheck())) {
                                    String finalResultNamesForDishTypes = dishTypesListsArray.get(j).getName();
                                    stringList.add(finalResultNamesForDishTypes);

                                }


                            }


                        }
                        List<Object> dishTypesLists = new ArrayList<>();

                        for (int j = 0; j < dishTypesListsArray.size(); j++) {
                            MenuDishType category5 = new MenuDishType();
                            category5.setIdForCheck(dishTypesListsArray.get(j).getIdForCheck());
                            category5.setId(dishTypesListsArray.get(j).getId());
                            category5.setName(dishTypesListsArray.get(j).getName());

                            if (stringList.contains(dishTypesListsArray.get(j).getName())) {
                                category5.setSelected(true);
                            } else {
                                category5.setSelected(false);
                            }

                            selectedMenuDishTypeforAdd.add(category5);
                            dishTypesLists.add(category5);

                        }


                        dishRecyclerView.setAdapter(new MenuDishesAdapter(getContext(), dishTypesLists, fontBold, fontRegular, "Add"));


                    } else {
                        dishRecyclerView.setAdapter(new MenuDishesAdapter(getContext(), dishTypesListsObject, fontBold, fontRegular, "Add"));
                    }


                    if (dishCuisineArray.length() != 0) {

                        ArrayList<String> stringList = new ArrayList<>();

                        for (int i = 0; i < dishCuisineArray.length(); i++) {

                            getValuesforCuisinessTypes = dishCuisineArray.get(i);

                            for (int j = 0; j < dishCuisinesListsArray.size(); j++) {
                                if (getValuesforCuisinessTypes.equals(dishCuisinesListsArray.get(j).getIdForCheck())) {
                                    String finalResultNamesForDishTypes = dishCuisinesListsArray.get(j).getName();
                                    stringList.add(finalResultNamesForDishTypes);

                                }


                            }


                        }
                        List<Object> dishTypesLists = new ArrayList<>();

                        for (int j = 0; j < dishCuisinesListsArray.size(); j++) {
                            MenuCuisine category5 = new MenuCuisine();
                            category5.setIdForCheck(dishCuisinesListsArray.get(j).getIdForCheck());
                            category5.setId(dishCuisinesListsArray.get(j).getId());
                            category5.setName(dishCuisinesListsArray.get(j).getName());

                            if (stringList.contains(dishCuisinesListsArray.get(j).getName())) {
                                category5.setSelected(true);
                            } else {
                                category5.setSelected(false);
                            }

                            selectedMenuCuisineforAdd.add(category5);
                            dishTypesLists.add(category5);

                        }


                        cuisineRecyclerView.setAdapter(new MenuCategoryAdapter(getContext(), dishTypesLists, fontBold, fontRegular, "Add"));

                    } else {
                        cuisineRecyclerView.setAdapter(new MenuCategoryAdapter(getContext(), dishCuisinesListsObject, fontBold, fontRegular, "Add"));
                    }

                    if (dishCategoryArray.length() != 0) {

                        ArrayList<String> stringList = new ArrayList<>();

                        for (int i = 0; i < dishCategoryArray.length(); i++) {

                            getValuesforCategpryTypes = dishCategoryArray.get(i);

                            for (int j = 0; j < dishCategoryListsArray.size(); j++) {
                                if (getValuesforCategpryTypes.equals(dishCategoryListsArray.get(j).getIdForCheck())) {
                                    String finalResultNamesForDishTypes = dishCategoryListsArray.get(j).getName();
                                    stringList.add(finalResultNamesForDishTypes);

                                }


                            }


                        }
                        List<Object> dishTypesLists = new ArrayList<>();

                        for (int j = 0; j < dishCategoryListsArray.size(); j++) {
                            MenuCategory category5 = new MenuCategory();
                            category5.setIdForCheck(dishCategoryListsArray.get(j).getIdForCheck());
                            category5.setId(dishCategoryListsArray.get(j).getId());
                            category5.setName(dishCategoryListsArray.get(j).getName());

                            if (stringList.contains(dishCategoryListsArray.get(j).getName())) {
                                category5.setSelected(true);
                            } else {
                                category5.setSelected(false);
                            }

                            selectedMenuCategoriesforAdd.add(category5);
                            dishTypesLists.add(category5);

                        }


                        categoryRecyclerView.setAdapter(new MenuCategoryAdapter(getContext(), dishTypesLists, fontBold, fontRegular, "Add"));

                    } else {
                        categoryRecyclerView.setAdapter(new MenuCategoryAdapter(getContext(), dishCategoryListsObject, fontBold, fontRegular, "Add"));
                    }

                    if (availableOnDaysArray.length() != 0) {

                        ArrayList<String> stringList = new ArrayList<>();

                        for (int i = 0; i < availableOnDaysArray.length(); i++) {

                            getValuesforAvailableDaysTypes = availableOnDaysArray.get(i);

                            for (int j = 0; j < daysListArray.size(); j++) {
                                if (getValuesforAvailableDaysTypes.equals(daysListArray.get(j).getIdForCheck())) {
                                    String finalResultNamesForDishTypes = daysListArray.get(j).getWeekDayName();
                                    stringList.add(finalResultNamesForDishTypes);

                                }

                            }


                        }
                        List<Object> dishTypesLists = new ArrayList<>();

                        for (int j = 0; j < daysListArray.size(); j++) {
                            WeekDays category5 = new WeekDays();
                            category5.setIdForCheck(daysListArray.get(j).getIdForCheck());
                            category5.setId(daysListArray.get(j).getId());
                            category5.setWeekDayName(daysListArray.get(j).getWeekDayName());

                            if (stringList.contains(daysListArray.get(j).getWeekDayName())) {
                                category5.setSelected(true);
                            } else {
                                category5.setSelected(false);
                            }

                            selectedWeekDaysforAdd.add(category5);
                            dishTypesLists.add(category5);

                        }


                        daysRecyclerView.setAdapter(new MenuCategoryAdapter(getContext(), dishTypesLists, fontBold, fontRegular, "Add"));

                    } else {
                        daysRecyclerView.setAdapter(new MenuCategoryAdapter(getContext(), daysListObject, fontBold, fontRegular, "Add"));
                    }

                    Date newDate = null;
                    Date newDate1 = null;
                    try {
                        SimpleDateFormat finalUTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//                        finalUTCFormat.setTimeZone(TimeZone.getTimeZone("IST"));


                        newDate = finalUTCFormat.parse(dataObject.optString("availabilityStartTime"));

                    } catch (ParseException e) {

                        try {
                            SimpleDateFormat finalUTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//                            finalUTCFormat.setTimeZone(TimeZone.getTimeZone("IST"));


                            newDate = finalUTCFormat.parse(dataObject.optString("availabilityStartTime"));
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }

                        e.printStackTrace();
                    }

                    try {
                        SimpleDateFormat finalUTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//                        finalUTCFormat.setTimeZone(TimeZone.getTimeZone("IST"));
                        newDate1 = finalUTCFormat.parse(dataObject.optString("availabilityEndTime"));
                    } catch (Exception e) {
                        try {
                            SimpleDateFormat finalUTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//                            finalUTCFormat.setTimeZone(TimeZone.getTimeZone("IST"));

                            newDate1 = finalUTCFormat.parse(dataObject.optString("availabilityEndTime"));
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    try {
                        SimpleDateFormat finalUTCFormat = new SimpleDateFormat("hh:mm a");


//                        finalUTCFormat.setTimeZone(TimeZone.getTimeZone("IST"));
                        String startDate = finalUTCFormat.format(newDate);
                        String renewalDate = finalUTCFormat.format(newDate1);

                        SimpleDateFormat sdf = new SimpleDateFormat("MM");
                        SimpleDateFormat sdfForDay = new SimpleDateFormat("dd");
                        SimpleDateFormat sdfForYear = new SimpleDateFormat("yyyy");
                        String startMonth = sdf.format(newDate);
                        String startDateNo = sdfForDay.format(newDate);
                        String startYear = sdfForYear.format(newDate);

                        String endMonth = sdf.format(newDate1);
                        String endDateNo = sdfForDay.format(newDate1);
                        String endYear = sdfForYear.format(newDate1);


                        availabilityStartTimeToServer = startYear + "-" + startMonth + "-" + startDateNo + " " + startDate;
                        startTime = startDate;

                        availabilityEndTimeToServer = endYear + "-" + endMonth + "-" + endDateNo + " " + renewalDate;
                        endTime = renewalDate;

                        startTimeText.setText(startDate);
                        endTimeText.setText(renewalDate);
                    } catch (Exception e) {
                        startTimeText.setText("");
                        endTimeText.setText("");
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "menu_detail_taq");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                int imageWidth = bitmap.getWidth();
                int imageHeight = bitmap.getHeight();
                try {
                    fileuploaded = new File(FilePath.getPath(getContext(), imageUri));
                } catch (Exception e) {
                    imageView.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Please choose a different image", Toast.LENGTH_SHORT).show();
                }
                /*if (imageHeight > 250 && imageWidth > 250) {
                    Log.v("imageok", imageHeight + "//" + imageWidth + " : resolution Ok");
                    if (fileuploaded.length() < (5 * 1024 * 1024)) {*/
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                imageView.setVisibility(View.VISIBLE);

                // BitmapFactory options to downsize the image
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                o.inSampleSize = 6;
                // factor of downsizing the image

                FileInputStream inputStream = null;

                inputStream = new FileInputStream(fileuploaded);
                //Bitmap selectedBitmap = null;
                BitmapFactory.decodeStream(inputStream, null, o);
                inputStream.close();

                // The new size we want to scale to
                final int REQUIRED_SIZE = 75;

                // Find the correct scale value. It should be the power of 2.
                int scale = 1;
                while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                        o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                    scale *= 2;
                }

                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize = scale;
                inputStream = new FileInputStream(fileuploaded);

                Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
                selectedBitmap = Bitmap.createScaledBitmap(selectedBitmap, 250, 250, true);
                imageView.setImageBitmap(selectedBitmap);
                inputStream.close();

                // here i override the original image file
                fileuploaded.createNewFile();
                if (ContextCompat.checkSelfPermission(
                        getContext(), WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED) {
                    String extr = Environment.getExternalStorageDirectory().toString();
                    mFolder = new File(extr + "/FoodyHive");
                    if (!mFolder.exists()) {
                        mFolder.mkdir();
                    }
                    File f = new File(mFolder.getAbsolutePath(), System.currentTimeMillis() + "menu.png");
                    Log.v("extrPath", mFolder.getAbsolutePath());
                    strMyImagePath = f.getAbsolutePath();

                    FileOutputStream outputStream = new FileOutputStream(f);

                    selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);


                    filetoUploadinServer = f;


                }
                    /*}else{
                        Toast.makeText(getContext(), "Image size must be less than 5 MB", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Image resolution must be atleast 250 X 250", Toast.LENGTH_LONG).show();
                }*/
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Please choose a different image", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            pdffileData = data.getData();


            receipeupload.setText(FilePath.getFileName(getContext(), pdffileData));
            Toast.makeText(getContext(), "Pdf file  selected successfully", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(
                    getContext(), "Null or Empty",
                    Toast.LENGTH_LONG).show();
        }
    }


    /**
     * FOr Both PDF and Imageis Selced
     **/
    private void editChefsMenuItem(String filePath) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading item details, Please wait..");
        progressDialog.show();
        JSONObject inputObject = new JSONObject();

        try {


            try {
                inputObject.put("name", dishName.getText().toString());
                inputObject.put("weight", Weight.getText().toString());
                inputObject.put("eachDayQuanity", quantityText.getText().toString());
                inputObject.put("preparationTime", preparationTime.getText().toString());
                inputObject.put("portionPrice", portionpriceET.getText().toString());
                inputObject.put("forPortions", portionText.getText().toString());
                JSONArray dishImagePathArray = new JSONArray();

                inputObject.put("dishImagePath", dishImagePathArray);
                inputObject.put("shortDescription", shortDescription.getText().toString());

                JSONArray availableOnDaysArray = new JSONArray();

                for (int i = 0; i < selectedWeekDaysforAdd.size(); i++) {
                    if (selectedWeekDaysforAdd.get(i).getSelected()) {
                        availableOnDaysArray.put(selectedWeekDaysforAdd.get(i).getIdForCheck());
                    }

                }
                inputObject.put("availableOnDays", availableOnDaysArray);
                inputObject.put("price", price.getText().toString());
                inputObject.put("preparationTime", preparationTime.getText().toString());
                Date availabilityStartDate = null;
                Date availabilityEndDate = null;
                try {
                    availabilityStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm a").parse(availabilityStartTimeToServer);

                } catch (ParseException e) {

                    availabilityStartDate = new SimpleDateFormat("yyyy-MM-dd HH:m a").parse(availabilityStartTimeToServer);

                    e.printStackTrace();
                }

                try {
                    availabilityEndDate = new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(availabilityEndTimeToServer);
                } catch (Exception e) {
                    availabilityEndDate = new SimpleDateFormat("yyyy-MM-dd hh:m a").parse(availabilityEndTimeToServer);
                    e.printStackTrace();
                }
                try {
                    SimpleDateFormat finalUTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    finalUTCFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String startDate = finalUTCFormat.format(availabilityStartDate);
                    String renewalDate = finalUTCFormat.format(availabilityEndDate);
                    inputObject.put("availabilityStartTime", startDate);
                    inputObject.put("availabilityEndTime", renewalDate);
                } catch (Exception e) {
                    inputObject.put("availabilityStartTime", "2020-08-12T07:22:23.392Z");
                    inputObject.put("availabilityEndTime", "2020-08-12T13:21:26.993Z");
                    e.printStackTrace();
                }


                JSONArray dishCategoryArray = new JSONArray();
                JSONArray dishCuisineArray = new JSONArray();
                JSONArray typeOfDishArray = new JSONArray();

                for (int i = 0; i < selectedMenuCategoriesforAdd.size(); i++) {
                    if (selectedMenuCategoriesforAdd.get(i).getSelected()) {
                        dishCategoryArray.put(selectedMenuCategoriesforAdd.get(i).getIdForCheck());
                    }

                }


                for (int i = 0; i < selectedMenuCuisineforAdd.size(); i++) {
                    if (selectedMenuCuisineforAdd.get(i).getSelected()) {
                        dishCuisineArray.put(selectedMenuCuisineforAdd.get(i).getIdForCheck());
                    }

                }

                for (int i = 0; i < selectedMenuDishTypeforAdd.size(); i++) {
                    if (selectedMenuDishTypeforAdd.get(i).getSelected()) {
                        typeOfDishArray.put(selectedMenuDishTypeforAdd.get(i).getIdForCheck());
                    }

                }


                inputObject.put("dishCategory", dishCategoryArray);
                inputObject.put("dishCuisine", dishCuisineArray);
                inputObject.put("typeOfDish", typeOfDishArray);
                if (!discountPercent.getText().toString().equals("")) {
                    inputObject.put("discountPercent", Integer.parseInt(discountPercent.getText().toString()));
                } else {
                    inputObject.put("discountPercent", 0);
                }


                inputObject.put("subscriptionOfferPercent", 0);
                inputObject.put("bulkOrderOfferPercent", 0);
                inputObject.put("ingredients", ingredients.getText().toString());
                if (promotedCheckbox.isChecked()) {
                    inputObject.put("isPromotedOrExclusive", true);
                } else {
                    inputObject.put("isPromotedOrExclusive", false);
                }

                JSONArray accoladesArray = new JSONArray();
                JSONObject accoladesObject = new JSONObject();
                accoladesObject.put("title", "");
                accoladesObject.put("accoladeDescription", "");
                accoladesArray.put(accoladesObject);

                inputObject.put("accolades", accoladesArray);

                inputObject.put("description", dishdescription.getText().toString());
                JSONArray tagsArray = new JSONArray();
                tagsArray.put(1);
                inputObject.put("tags", tagsArray);

                JSONArray recipeFilePathArray = new JSONArray();
                inputObject.put("recipeFilePath", recipeFilePathArray);

               // inputObject.put("isPromotedOrExclusive", false);
                inputObject.put("autoAcceptOrder", true);
                JSONObject nutritionObject = new JSONObject();
                nutritionObject.put("energyCalories", calories.getText().toString());
                nutritionObject.put("carbohydrates", carbs.getText().toString());
                nutritionObject.put("sugar", sugar.getText().toString());
                nutritionObject.put("fibre", fibre.getText().toString());
                nutritionObject.put("protein", protein.getText().toString());
                nutritionObject.put("fat", fat.getText().toString());
                nutritionObject.put("allergenInformation", allerginInfo.getText().toString());
                inputObject.put("nutrition", nutritionObject);
                inputObject.put("chefId", SaveSharedPreference.getLoggedInWorkFlowID(getContext()));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (pdffileData != null)
                pdffilePath = FilePath.getLocalPath(getContext(), pdffileData);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.v("inputObject",inputObject.toString());

        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(getContext(), uploadId, APIBaseURL.updateChefMenuItem)
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .addHeader("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(getContext()))
                    .setMethod("POST")
                    .addParameter("request", inputObject.toString())
                    .addParameter("id", getArguments().getString("ID"))
                    .addFileToUpload(filetoUploadinServer.getAbsolutePath(), "dishImagePath")
                    .addFileToUpload(pdffilePath, "recipeFilePath")
                    .setNotificationConfig(new UploadNotificationConfig().setRingToneEnabled(false))
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {
                            progressDialog.show();
                        }


                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            progressDialog.dismiss();


                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();
                        }


                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Item Updated Successfully", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();
                            onBackPressed();
                        }


                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();
                        }
                    })
                    .setMaxRetries(2)
                    .startUpload();
        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * If selecting Only Image tis
     **/
    private void editChefsMenuItemWithoutPDF(String filePath) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading item details, Please wait..");
        progressDialog.show();
        JSONObject inputObject = new JSONObject();

        try {


            try {
                inputObject.put("name", dishName.getText().toString());
                inputObject.put("weight", Weight.getText().toString());
                inputObject.put("eachDayQuanity", quantityText.getText().toString());
                inputObject.put("preparationTime", preparationTime.getText().toString());

                inputObject.put("portionPrice", portionpriceET.getText().toString());
                inputObject.put("forPortions", portionText.getText().toString());
                JSONArray dishImagePathArray = new JSONArray();

                inputObject.put("dishImagePath", dishImagePathArray);
                inputObject.put("shortDescription", shortDescription.getText().toString());

                JSONArray availableOnDaysArray = new JSONArray();

                for (int i = 0; i < selectedWeekDaysforAdd.size(); i++) {
                    if (selectedWeekDaysforAdd.get(i).getSelected()) {
                        availableOnDaysArray.put(selectedWeekDaysforAdd.get(i).getIdForCheck());
                    }

                }
                inputObject.put("availableOnDays", availableOnDaysArray);
                inputObject.put("price", price.getText().toString());
                inputObject.put("preparationTime", preparationTime.getText().toString());
                Date availabilityStartDate = null;
                Date availabilityEndDate = null;
                try {
                    availabilityStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm a").parse(availabilityStartTimeToServer);

                } catch (ParseException e) {

                    availabilityStartDate = new SimpleDateFormat("yyyy-MM-dd HH:m a").parse(availabilityStartTimeToServer);

                    e.printStackTrace();
                }

                try {
                    availabilityEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm a").parse(availabilityEndTimeToServer);
                } catch (Exception e) {
                    availabilityEndDate = new SimpleDateFormat("yyyy-MM-dd HH:m a").parse(availabilityEndTimeToServer);
                    e.printStackTrace();
                }
                try {
                    SimpleDateFormat finalUTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    finalUTCFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String startDate = finalUTCFormat.format(availabilityStartDate);
                    String renewalDate = finalUTCFormat.format(availabilityEndDate);
                    inputObject.put("availabilityStartTime", startDate);
                    inputObject.put("availabilityEndTime", renewalDate);
                } catch (Exception e) {
                    inputObject.put("availabilityStartTime", "2020-08-12T07:22:23.392Z");
                    inputObject.put("availabilityEndTime", "2020-08-12T13:21:26.993Z");
                    e.printStackTrace();
                }


                JSONArray dishCategoryArray = new JSONArray();
                JSONArray dishCuisineArray = new JSONArray();
                JSONArray typeOfDishArray = new JSONArray();

                for (int i = 0; i < selectedMenuCategoriesforAdd.size(); i++) {
                    if (selectedMenuCategoriesforAdd.get(i).getSelected()) {
                        dishCategoryArray.put(selectedMenuCategoriesforAdd.get(i).getIdForCheck());
                    }

                }


                for (int i = 0; i < selectedMenuCuisineforAdd.size(); i++) {
                    if (selectedMenuCuisineforAdd.get(i).getSelected()) {
                        dishCuisineArray.put(selectedMenuCuisineforAdd.get(i).getIdForCheck());
                    }

                }

                for (int i = 0; i < selectedMenuDishTypeforAdd.size(); i++) {
                    if (selectedMenuDishTypeforAdd.get(i).getSelected()) {
                        typeOfDishArray.put(selectedMenuDishTypeforAdd.get(i).getIdForCheck());
                    }

                }


                inputObject.put("dishCategory", dishCategoryArray);
                inputObject.put("dishCuisine", dishCuisineArray);
                inputObject.put("typeOfDish", typeOfDishArray);
                if (!discountPercent.getText().toString().equals("")) {
                    inputObject.put("discountPercent", Integer.parseInt(discountPercent.getText().toString()));
                } else {
                    inputObject.put("discountPercent", 0);
                }

                inputObject.put("subscriptionOfferPercent", 0);
                inputObject.put("bulkOrderOfferPercent", 0);
                inputObject.put("ingredients", ingredients.getText().toString());
                if (promotedCheckbox.isChecked()) {
                    inputObject.put("isPromotedOrExclusive", true);
                } else {
                    inputObject.put("isPromotedOrExclusive", false);
                }

                JSONArray accoladesArray = new JSONArray();
                JSONObject accoladesObject = new JSONObject();
                accoladesObject.put("title", "");
                accoladesObject.put("accoladeDescription", "");
                accoladesArray.put(accoladesObject);

                inputObject.put("accolades", accoladesArray);

                inputObject.put("description", dishdescription.getText().toString());
                JSONArray tagsArray = new JSONArray();
                tagsArray.put(1);
                inputObject.put("tags", tagsArray);

                JSONArray recipeFilePathArray = new JSONArray();
                try {
                    if (!pdffilePath.equals("null") || !pdffilePath.equals(null)) {
                        recipeFilePathArray.put(pdffilePath);
                    }
                }
                catch (Exception e)
                {
                    //recipeFilePathArray.put(pdffilePath);
                }


                inputObject.put("recipeFilePath", recipeFilePathArray);

              //  inputObject.put("isPromotedOrExclusive", false);
                inputObject.put("autoAcceptOrder", true);
                JSONObject nutritionObject = new JSONObject();
                nutritionObject.put("energyCalories", calories.getText().toString());
                nutritionObject.put("carbohydrates", carbs.getText().toString());
                nutritionObject.put("sugar", sugar.getText().toString());
                nutritionObject.put("fibre", fibre.getText().toString());
                nutritionObject.put("protein", protein.getText().toString());
                nutritionObject.put("fat", fat.getText().toString());
                nutritionObject.put("allergenInformation", allerginInfo.getText().toString());
                inputObject.put("nutrition", nutritionObject);
                inputObject.put("chefId", SaveSharedPreference.getLoggedInWorkFlowID(getContext()));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (pdffileData != null)
                pdffilePath = FilePath.getLocalPath(getContext(), pdffileData);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.v("inputObjectwithoutpdf",inputObject.toString());

        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(getContext(), uploadId, APIBaseURL.updateChefMenuItem)
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .addHeader("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(getContext()))
                    .setMethod("POST")
                    .addParameter("request", inputObject.toString())
                    .addParameter("id", getArguments().getString("ID"))
                    .addFileToUpload(filetoUploadinServer.getAbsolutePath(), "dishImagePath")
//                    .addFileToUpload(pdffilePath, "recipeFilePath")
                    .setNotificationConfig(new UploadNotificationConfig().setRingToneEnabled(false))
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {

                            progressDialog.show();
                        }


                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            progressDialog.dismiss();


                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();

                        }


                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            progressDialog.dismiss();

                            Toast.makeText(context, "Item Updated Successfully", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();

                            onBackPressed();
                        }


                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {

                            progressDialog.dismiss();
                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();

                        }
                    })
                    .setMaxRetries(2)
                    .startUpload();
        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    /**
     * If both Image and PDF is Not selected then this methid will be called
     **/
    private void editChefsMenuItemWithoutPDFAndImage(String filePath) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading item details, Please wait..");
        progressDialog.show();
        JSONObject inputObject = new JSONObject();

        try {


            try {
                inputObject.put("name", dishName.getText().toString());
                inputObject.put("weight", Weight.getText().toString());
                inputObject.put("eachDayQuanity", quantityText.getText().toString());
                inputObject.put("preparationTime", preparationTime.getText().toString());

                inputObject.put("portionPrice", portionpriceET.getText().toString());
                inputObject.put("forPortions", portionText.getText().toString());
                JSONArray dishImagePathArray = new JSONArray();
                dishImagePathArray.put(fileImageURLFromServer);
                inputObject.put("dishImagePath", dishImagePathArray);
                inputObject.put("shortDescription", shortDescription.getText().toString());

                JSONArray availableOnDaysArray = new JSONArray();

                for (int i = 0; i < selectedWeekDaysforAdd.size(); i++) {
                    if (selectedWeekDaysforAdd.get(i).getSelected()) {
                        availableOnDaysArray.put(selectedWeekDaysforAdd.get(i).getIdForCheck());
                    }

                }
                inputObject.put("availableOnDays", availableOnDaysArray);
                inputObject.put("price", price.getText().toString());
                inputObject.put("preparationTime", preparationTime.getText().toString());
                Date availabilityStartDate = null;
                Date availabilityEndDate = null;
                try {
                    availabilityStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm a").parse(availabilityStartTimeToServer);

                } catch (ParseException e) {

                    availabilityStartDate = new SimpleDateFormat("yyyy-MM-dd HH:m a").parse(availabilityStartTimeToServer);

                    e.printStackTrace();
                }

                try {
                    availabilityEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm a").parse(availabilityEndTimeToServer);
                } catch (Exception e) {
                    availabilityEndDate = new SimpleDateFormat("yyyy-MM-dd HH:m a").parse(availabilityEndTimeToServer);
                    e.printStackTrace();
                }
                try {
                    SimpleDateFormat finalUTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//                    finalUTCFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String startDate = finalUTCFormat.format(availabilityStartDate);
                    String renewalDate = finalUTCFormat.format(availabilityEndDate);
                    inputObject.put("availabilityStartTime", startDate);
                    inputObject.put("availabilityEndTime", renewalDate);
                } catch (Exception e) {
                    inputObject.put("availabilityStartTime", "2020-08-12T07:22:23.392Z");
                    inputObject.put("availabilityEndTime", "2020-08-12T13:21:26.993Z");
                    e.printStackTrace();
                }


                JSONArray dishCategoryArray = new JSONArray();
                JSONArray dishCuisineArray = new JSONArray();
                JSONArray typeOfDishArray = new JSONArray();

                for (int i = 0; i < selectedMenuCategoriesforAdd.size(); i++) {
                    if (selectedMenuCategoriesforAdd.get(i).getSelected()) {
                        dishCategoryArray.put(selectedMenuCategoriesforAdd.get(i).getIdForCheck());
                    }

                }


                for (int i = 0; i < selectedMenuCuisineforAdd.size(); i++) {
                    if (selectedMenuCuisineforAdd.get(i).getSelected()) {
                        dishCuisineArray.put(selectedMenuCuisineforAdd.get(i).getIdForCheck());
                    }

                }

                for (int i = 0; i < selectedMenuDishTypeforAdd.size(); i++) {
                    if (selectedMenuDishTypeforAdd.get(i).getSelected()) {
                        typeOfDishArray.put(selectedMenuDishTypeforAdd.get(i).getIdForCheck());
                    }
//
                }


                inputObject.put("dishCategory", dishCategoryArray);
                inputObject.put("dishCuisine", dishCuisineArray);
                inputObject.put("typeOfDish", typeOfDishArray);
                if (!discountPercent.getText().toString().equals("")) {
                    inputObject.put("discountPercent", Integer.parseInt(discountPercent.getText().toString()));
                } else {
                    inputObject.put("discountPercent", 0);
                }
                inputObject.put("subscriptionOfferPercent", 0);
                inputObject.put("bulkOrderOfferPercent", 0);
                inputObject.put("ingredients", ingredients.getText().toString());
                if (promotedCheckbox.isChecked()) {
                    inputObject.put("isPromotedOrExclusive", true);
                } else {
                    inputObject.put("isPromotedOrExclusive", false);
                }

                JSONArray accoladesArray = new JSONArray();
                JSONObject accoladesObject = new JSONObject();
                accoladesObject.put("title", "");
                accoladesObject.put("accoladeDescription", "");
                accoladesArray.put(accoladesObject);

                inputObject.put("accolades", accoladesArray);

                inputObject.put("description", dishdescription.getText().toString());
                JSONArray tagsArray = new JSONArray();
                tagsArray.put(1);
                inputObject.put("tags", tagsArray);

                JSONArray recipeFilePathArray = new JSONArray();
                try {
                    if (!pdffilePath.equals("null") || !pdffilePath.equals(null)) {
                        recipeFilePathArray.put(pdffilePath);
                    }
                }
                catch (Exception e)
                {
                    //recipeFilePathArray.put(pdffilePath);
                }


                inputObject.put("recipeFilePath", recipeFilePathArray);

//                inputObject.put("isPromotedOrExclusive", false);
                inputObject.put("autoAcceptOrder", true);
                JSONObject nutritionObject = new JSONObject();
                nutritionObject.put("energyCalories", Integer.parseInt(calories.getText().toString()));
                nutritionObject.put("carbohydrates", Integer.parseInt(carbs.getText().toString()));
                nutritionObject.put("sugar", Integer.parseInt(sugar.getText().toString()));
                nutritionObject.put("fibre", Integer.parseInt(fibre.getText().toString()));
                nutritionObject.put("protein", Integer.parseInt(protein.getText().toString()));
                nutritionObject.put("fat", Integer.parseInt(fat.getText().toString()));
                nutritionObject.put("allergenInformation", allerginInfo.getText().toString());
                inputObject.put("nutrition", nutritionObject);
                inputObject.put("chefId", SaveSharedPreference.getLoggedInWorkFlowID(getContext()));


                Log.d("InputObjectForEditsssss", inputObject + " ");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (pdffileData != null)
                pdffilePath = FilePath.getLocalPath(getContext(), pdffileData);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.v("inputObjectwithoutpdfandimage", inputObject.toString());
        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(getContext(), uploadId, APIBaseURL.updateChefMenuItem)
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .addHeader("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(getContext()))
                    .setMethod("POST")
                    .addParameter("request", inputObject.toString())
                    .addParameter("id", getArguments().getString("ID"))

                    .setNotificationConfig(new UploadNotificationConfig().setRingToneEnabled(false))
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {

                            progressDialog.show();
                        }


                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            progressDialog.dismiss();

                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();

                        }


                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            progressDialog.dismiss();

                            Toast.makeText(context, "Item Updated Successfully", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();

                            onBackPressed();
                        }


                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {

                            progressDialog.dismiss();
                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();

                        }
                    })
                    .setMaxRetries(2)
                    .startUpload();
        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    /**
     * If selecting Only PDF this method will be called
     **/
    private void editChefsMenuItemWithoutImage(String filePath) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading item details, Please wait..");
        progressDialog.show();
        JSONObject inputObject = new JSONObject();

        try {


            try {
                inputObject.put("name", dishName.getText().toString());
                inputObject.put("weight", Weight.getText().toString());
                inputObject.put("eachDayQuanity", quantityText.getText().toString());
                inputObject.put("preparationTime", preparationTime.getText().toString());

                inputObject.put("portionPrice", portionpriceET.getText().toString());
                inputObject.put("forPortions", portionText.getText().toString());
                JSONArray dishImagePathArray = new JSONArray();
                dishImagePathArray.put(fileImageURLFromServer);
                inputObject.put("dishImagePath", dishImagePathArray);
                inputObject.put("shortDescription", shortDescription.getText().toString());

                JSONArray availableOnDaysArray = new JSONArray();

                for (int i = 0; i < selectedWeekDaysforAdd.size(); i++) {
                    if (selectedWeekDaysforAdd.get(i).getSelected()) {
                        availableOnDaysArray.put(selectedWeekDaysforAdd.get(i).getIdForCheck());
                    }

                }
                inputObject.put("availableOnDays", availableOnDaysArray);
                inputObject.put("price", price.getText().toString());
                inputObject.put("preparationTime", preparationTime.getText().toString());
                Date availabilityStartDate = null;
                Date availabilityEndDate = null;
                try {
                    availabilityStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm a").parse(availabilityStartTimeToServer);

                } catch (ParseException e) {
                    availabilityStartDate = new SimpleDateFormat("yyyy-MM-dd HH:m a").parse(availabilityStartTimeToServer);

                    e.printStackTrace();
                }

                try {
                    availabilityEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm a").parse(availabilityEndTimeToServer);
                } catch (Exception e) {
                    availabilityEndDate = new SimpleDateFormat("yyyy-MM-dd HH:m a").parse(availabilityEndTimeToServer);
                    e.printStackTrace();
                }
                try {
                    SimpleDateFormat finalUTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    finalUTCFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String startDate = finalUTCFormat.format(availabilityStartDate);
                    String renewalDate = finalUTCFormat.format(availabilityEndDate);
                    inputObject.put("availabilityStartTime", startDate);
                    inputObject.put("availabilityEndTime", renewalDate);
                } catch (Exception e) {
                    inputObject.put("availabilityStartTime", "2020-08-12T07:22:23.392Z");
                    inputObject.put("availabilityEndTime", "2020-08-12T13:21:26.993Z");
                    e.printStackTrace();
                }


                JSONArray dishCategoryArray = new JSONArray();
                JSONArray dishCuisineArray = new JSONArray();
                JSONArray typeOfDishArray = new JSONArray();

                for (int i = 0; i < selectedMenuCategoriesforAdd.size(); i++) {
                    if (selectedMenuCategoriesforAdd.get(i).getSelected()) {
                        dishCategoryArray.put(selectedMenuCategoriesforAdd.get(i).getIdForCheck());
                    }

                }


                for (int i = 0; i < selectedMenuCuisineforAdd.size(); i++) {
                    if (selectedMenuCuisineforAdd.get(i).getSelected()) {
                        dishCuisineArray.put(selectedMenuCuisineforAdd.get(i).getIdForCheck());
                    }

                }
                for (int i = 0; i < selectedMenuDishTypeforAdd.size(); i++) {
                    if (selectedMenuDishTypeforAdd.get(i).getSelected()) {
                        typeOfDishArray.put(selectedMenuDishTypeforAdd.get(i).getIdForCheck());
                    }

                }


                inputObject.put("dishCategory", dishCategoryArray);
                inputObject.put("dishCuisine", dishCuisineArray);
                inputObject.put("typeOfDish", typeOfDishArray);
                if (!discountPercent.getText().toString().equals("")) {
                    inputObject.put("discountPercent", Integer.parseInt(discountPercent.getText().toString()));
                } else {
                    inputObject.put("discountPercent", 0);
                }

                inputObject.put("subscriptionOfferPercent", 0);
                inputObject.put("bulkOrderOfferPercent", 0);
                inputObject.put("ingredients", ingredients.getText().toString());
                if (promotedCheckbox.isChecked()) {
                    inputObject.put("isPromotedOrExclusive", true);
                } else {
                    inputObject.put("isPromotedOrExclusive", false);
                }

                JSONArray accoladesArray = new JSONArray();
                JSONObject accoladesObject = new JSONObject();
                accoladesObject.put("title", "");
                accoladesObject.put("accoladeDescription", "");
                accoladesArray.put(accoladesObject);

                inputObject.put("accolades", accoladesArray);

                inputObject.put("description", dishdescription.getText().toString());
                JSONArray tagsArray = new JSONArray();
                tagsArray.put(1);
                inputObject.put("tags", tagsArray);

                JSONArray recipeFilePathArray = new JSONArray();

                inputObject.put("recipeFilePath", recipeFilePathArray);

               // inputObject.put("isPromotedOrExclusive", false);
                inputObject.put("autoAcceptOrder", true);
                JSONObject nutritionObject = new JSONObject();
                nutritionObject.put("energyCalories", calories.getText().toString());
                nutritionObject.put("carbohydrates", carbs.getText().toString());
                nutritionObject.put("sugar", sugar.getText().toString());
                nutritionObject.put("fibre", fibre.getText().toString());
                nutritionObject.put("protein", protein.getText().toString());
                nutritionObject.put("fat", fat.getText().toString());
                nutritionObject.put("allergenInformation", allerginInfo.getText().toString());
                inputObject.put("nutrition", nutritionObject);
                inputObject.put("chefId", SaveSharedPreference.getLoggedInWorkFlowID(getContext()));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (pdffileData != null)
                pdffilePath = FilePath.getLocalPath(getContext(), pdffileData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("inputObjectwithoutimage",inputObject.toString());


        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(getContext(), uploadId, APIBaseURL.updateChefMenuItem)
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .addHeader("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(getContext()))
                    .setMethod("POST")
                    .addParameter("request", inputObject.toString())
                    .addParameter("id", getArguments().getString("ID"))

                    .addFileToUpload(pdffilePath, "recipeFilePath")
                    .setNotificationConfig(new UploadNotificationConfig().setRingToneEnabled(false))
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {

                            progressDialog.show();
                        }


                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            progressDialog.dismiss();


                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();

                        }


                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            progressDialog.dismiss();

                            Toast.makeText(context, "Item Updated Successfully", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();

                            onBackPressed();
                        }


                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {

                            progressDialog.dismiss();
                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();

                        }
                    })
                    .setMaxRetries(2)
                    .startUpload();
        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * Add Menu Dish with both image and pdf if selected
     **/
    private void addChefsMenuItem(String filePath) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Uploading item details, Please wait..");
        progressDialog.show();
        JSONObject inputObject = new JSONObject();

        try {


            try {
                inputObject.put("name", dishName.getText().toString());
                inputObject.put("eachDayQuanity", quantityText.getText().toString());
                inputObject.put("weight", Weight.getText().toString());
                inputObject.put("portionPrice", portionpriceET.getText().toString());
                inputObject.put("forPortions", portionText.getText().toString());
                JSONArray dishImagePathArray = new JSONArray();
                inputObject.put("dishImagePath", dishImagePathArray);
                inputObject.put("shortDescription", shortDescription.getText().toString());

                JSONArray availableOnDaysArray = new JSONArray();

                for (int i = 0; i < selectedWeekDaysforAdd.size(); i++) {
                    availableOnDaysArray.put(selectedWeekDaysforAdd.get(i).getIdForCheck());
                }
                inputObject.put("availableOnDays", availableOnDaysArray);


                JSONArray dishCategoryArray = new JSONArray();
                JSONArray dishCuisineArray = new JSONArray();
                JSONArray typeOfDishArray = new JSONArray();

                for (int i = 0; i < selectedMenuCategoriesforAdd.size(); i++) {
                    dishCategoryArray.put(selectedMenuCategoriesforAdd.get(i).getIdForCheck());
                }


                for (int i = 0; i < selectedMenuCuisineforAdd.size(); i++) {
                    dishCuisineArray.put(selectedMenuCuisineforAdd.get(i).getIdForCheck());
                }

                for (int i = 0; i < selectedMenuDishTypeforAdd.size(); i++) {
                    typeOfDishArray.put(selectedMenuDishTypeforAdd.get(i).getIdForCheck());
                }


                inputObject.put("dishCategory", dishCategoryArray);
                inputObject.put("dishCuisine", dishCuisineArray);
                inputObject.put("typeOfDish", typeOfDishArray);
                if (!discountPercent.getText().toString().equals("")) {
                    inputObject.put("discountPercent", Integer.parseInt(discountPercent.getText().toString()));
                } else {
                    inputObject.put("discountPercent", 0);
                }

                inputObject.put("subscriptionOfferPercent", 0);
                inputObject.put("bulkOrderOfferPercent", 0);
                inputObject.put("ingredients", ingredients.getText().toString());
                JSONArray accoladesArray = new JSONArray();
                JSONObject accoladesObject = new JSONObject();
                accoladesObject.put("title", "");
                accoladesObject.put("accoladeDescription", "");
                accoladesArray.put(accoladesObject);

                inputObject.put("accolades", accoladesArray);

                inputObject.put("description", dishdescription.getText().toString());
                JSONArray tagsArray = new JSONArray();
                tagsArray.put(1);
                inputObject.put("tags", tagsArray);

                JSONArray recipeFilePathArray = new JSONArray();
                inputObject.put("recipeFilePath", recipeFilePathArray);

                if (promotedCheckbox.isChecked()) {
                    inputObject.put("isPromotedOrExclusive", true);
                } else {
                    inputObject.put("isPromotedOrExclusive", false);
                }
                inputObject.put("autoAcceptOrder", true);
                JSONObject nutritionObject = new JSONObject();
                nutritionObject.put("energyCalories", calories.getText().toString());
                nutritionObject.put("carbohydrates", carbs.getText().toString());
                nutritionObject.put("sugar", sugar.getText().toString());
                nutritionObject.put("fibre", fibre.getText().toString());
                nutritionObject.put("protein", protein.getText().toString());
                nutritionObject.put("fat", fat.getText().toString());
                nutritionObject.put("allergenInformation", allerginInfo.getText().toString());
                inputObject.put("nutrition", nutritionObject);
                Date availabilityStartDate = null;
                Date availabilityEndDate = null;
                try {
                    availabilityStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm a").parse(availabilityStartTimeToServer);

                } catch (ParseException e) {

                    availabilityStartDate = new SimpleDateFormat("yyyy-MM-dd HH:m a").parse(availabilityStartTimeToServer);

                    e.printStackTrace();
                }

                try {
                    availabilityEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm a").parse(availabilityEndTimeToServer);
                } catch (Exception e) {
                    availabilityEndDate = new SimpleDateFormat("yyyy-MM-dd HH:m a").parse(availabilityEndTimeToServer);
                    e.printStackTrace();
                }
                try {
                    SimpleDateFormat finalUTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//                    finalUTCFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String startDate = finalUTCFormat.format(availabilityStartDate);
                    String renewalDate = finalUTCFormat.format(availabilityEndDate);
                    inputObject.put("availabilityStartTime", startDate);
                    inputObject.put("availabilityEndTime", renewalDate);
                } catch (Exception e) {
                    inputObject.put("availabilityStartTime", "2020-08-12T07:22:23.392Z");
                    inputObject.put("availabilityEndTime", "2020-08-12T13:21:26.993Z");
                    e.printStackTrace();
                }

                inputObject.put("price", price.getText().toString());
                inputObject.put("preparationTime", preparationTime.getText().toString());
                inputObject.put("chefId", SaveSharedPreference.getLoggedInWorkFlowID(getContext()));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            pdffilePath = FilePath.getLocalPath(getContext(), pdffileData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("inputObject", inputObject.toString());
        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(getContext(), uploadId, APIBaseURL.addChefsMenuItems)
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .addHeader("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(getContext()))
                    .setMethod("POST")
                    .addParameter("request", inputObject.toString())
                    .addFileToUpload(filetoUploadinServer.getAbsolutePath(), "dishImagePath")
                    .addFileToUpload(pdffilePath, "recipeFilePath")
                    .setNotificationConfig(new UploadNotificationConfig().setRingToneEnabled(false))
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {

                            progressDialog.show();
                        }


                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            progressDialog.dismiss();

                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();

                        }


                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Item Added Successfully", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();

                            onBackPressed();
                        }


                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {

                            progressDialog.dismiss();
                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                            deleteTempImageFolder();

                        }
                    })
                    .setMaxRetries(2)
                    .startUpload();
        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    /**
     * delete temp folder of image after compression
     **/
    public void deleteTempImageFolder() {
        try {
            if (mFolder.exists() && mFolder.isDirectory()) {
                File path = mFolder.getAbsoluteFile();
                String[] entries = path.list();
                for (String s : entries) {
                    File currentFile = new File(path.getPath(), s);
                    currentFile.delete();
                }
                mFolder.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
