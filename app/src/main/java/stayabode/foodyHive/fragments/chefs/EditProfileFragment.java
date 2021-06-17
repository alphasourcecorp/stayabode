package stayabode.foodyHive.fragments.chefs;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import stayabode.foodyHive.R;
import stayabode.foodyHive.activities.chefs.ChefsMainActivity;
import stayabode.foodyHive.adapters.chefs.AccoladesAdapter;
import stayabode.foodyHive.adapters.chefs.MenuCategoryAdapter;
import stayabode.foodyHive.constants.APIBaseURL;
import stayabode.foodyHive.models.ChefRewards;
import stayabode.foodyHive.models.WeekDays;
import stayabode.foodyHive.utils.ApplicationController;
import stayabode.foodyHive.utils.CustomVolleyRequest;
import stayabode.foodyHive.utils.FilePath;
import stayabode.foodyHive.utils.SaveSharedPreference;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheffragmentManager;
import static stayabode.foodyHive.activities.chefs.ChefsMainActivity.cheftoolbar;

public class EditProfileFragment extends Fragment implements
        AdapterView.OnItemSelectedListener {

    LinearLayout personalHeaderLayout;
    LinearLayout professionalHeaderLayout;
    LinearLayout dashBoardSearch;
    RelativeLayout personalRootLayout;
    RelativeLayout professionalRootLayout;

    EditText nameEditText;
    EditText locationEditText;
    EditText dobEditText;
    EditText bioEditText;
    EditText addressoneEditText;
    EditText addresstwoEditText;
    EditText addressthreeEditText;
    EditText pinCodeEditText;
    EditText stateEditText;
    EditText countryEditText;
    EditText phoneEditText;
    EditText emailEditText;
    EditText gstEditText;
    EditText panEditText;
    EditText fssaiEditText;
    EditText workingTimeEditText;
    EditText timeOfDayEditText;
    EditText startDateEditText;
    EditText renewalDateEditText;
    EditText accoladesdescriptionEditText;
    EditText accoladesitleEditText;
    ImageView profileImageView;
    LinearLayout spinnerDropDownlayout;

    String franchiseeID;
    String franchiseeLocation;
    String franchiseName;
    String franchiseLogo;
    String professionalAddress1;
    String professionalAddress2;
    String dateOfBirth;
    String aboutYou;
    String lat;
    String lng;
    String city;
    String bankName;
    String branch;
    String accountNumber;
    String ifscCode;
    String kitchenName;
    String cloudKitchen;
    String uploadLogo;
    String isActive;
    Boolean isHomeKitchen;
    Boolean isCloudKitchen;
    Boolean isSelfPickUp;
    String selectedGenderType = "";
    String fileImageURLFromServer = "";
    JSONArray contactArray = new JSONArray();
    JSONArray contractsArray = new JSONArray();
    JSONArray certificatesArray = new JSONArray();
    JSONObject locationObjectFromAPI = new JSONObject();


    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_PERMISSIONS = 100;

    Button Update;
    private String filePath;
    DatePickerDialog picker;
    Spinner searchSpinner;
    String[] genderTypes = {"Male", "Female"};
    ArrayAdapter aa;
    RecyclerView daysRecyclerView;
    public static Typeface fontBold;
    public static Typeface fontRegular;
    public static Typeface raleWayFontBold;
    public static Typeface ralewayFontRegular;


    List<Object> daysListObject = new ArrayList<>();


    List<WeekDays> daysListArray = new ArrayList<>();

    LinearLayout startDateIcon;
    LinearLayout endDateIcon;
    LinearLayout dobIcon;
    RecyclerView accoladesrecyclerView;
    Button addBtn;
    List<ChefRewards> chefRewardsList = new ArrayList<>();

    File fileuploaded = null;
    File filetoUploadinServer = null;
    File mFolder = null;
    File output = null;
    private Bitmap bitmap;
    String strMyImagePath = null;


    public static List<WeekDays> selectedWeekDaysforAddInEditProfile = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_chef_profile, container, false);
        cheftoolbar.setNavigationIcon(R.drawable.foodi_logo_left_image);
        ChefsMainActivity.searchLayout.setVisibility(View.GONE);
        ChefsMainActivity.chefnavigation.setVisibility(View.GONE);
        ChefsMainActivity.mainBottomLayout.setVisibility(View.GONE);
        cheftoolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        cheftoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        personalHeaderLayout = rootView.findViewById(R.id.personalHeaderLayout);
        professionalHeaderLayout = rootView.findViewById(R.id.professionalHeaderLayout);
        personalRootLayout = rootView.findViewById(R.id.personalRootLayout);
        professionalRootLayout = rootView.findViewById(R.id.professionalRootLayout);
        profileImageView = rootView.findViewById(R.id.profileImageView);
        nameEditText = rootView.findViewById(R.id.nameEditText);
        locationEditText = rootView.findViewById(R.id.locationEditText);
        dobEditText = rootView.findViewById(R.id.dobEditText);
        dobIcon = rootView.findViewById(R.id.dobIcon);
        bioEditText = rootView.findViewById(R.id.bioEditText);
        addressoneEditText = rootView.findViewById(R.id.addressoneEditText);
        addresstwoEditText = rootView.findViewById(R.id.addresstwoEditText);
        addressthreeEditText = rootView.findViewById(R.id.addressthreeEditText);
        pinCodeEditText = rootView.findViewById(R.id.pinCodeEditText);
        stateEditText = rootView.findViewById(R.id.stateEditText);
        countryEditText = rootView.findViewById(R.id.countryEditText);
        phoneEditText = rootView.findViewById(R.id.phoneEditText);
        emailEditText = rootView.findViewById(R.id.emailEditText);
        gstEditText = rootView.findViewById(R.id.gstEditText);
        panEditText = rootView.findViewById(R.id.panEditText);
        workingTimeEditText = rootView.findViewById(R.id.workingTimeEditText);
        timeOfDayEditText = rootView.findViewById(R.id.timeOfDayEditText);
        startDateEditText = rootView.findViewById(R.id.startDateEditText);
        startDateIcon = rootView.findViewById(R.id.startDateIcon);
        renewalDateEditText = rootView.findViewById(R.id.renewalDateEditText);
        endDateIcon = rootView.findViewById(R.id.endDateIcon);
        fssaiEditText = rootView.findViewById(R.id.fssaiEditText);
        Update = rootView.findViewById(R.id.Update);
        searchSpinner = rootView.findViewById(R.id.searchSpinner);
        daysRecyclerView = rootView.findViewById(R.id.daysRecyclerView);
        spinnerDropDownlayout = rootView.findViewById(R.id.spinnerDropDownlayout);
        dashBoardSearch = rootView.findViewById(R.id.dashBoardSearch);
        accoladesdescriptionEditText = rootView.findViewById(R.id.accoladesdescriptionEditText);
        accoladesitleEditText = rootView.findViewById(R.id.accoladesitleEditText);
        accoladesrecyclerView = rootView.findViewById(R.id.accoladesrecyclerView);
        accoladesrecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addBtn = rootView.findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (accoladesdescriptionEditText.getText().toString().equals("") && accoladesdescriptionEditText.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Required Title and Description", Toast.LENGTH_SHORT).show();
                } else {
                    if (chefRewardsList.size() <= 4) {
                        ChefRewards chefRewards = new ChefRewards();
                        chefRewards.setName(accoladesitleEditText.getText().toString());
                        chefRewards.setDescription(accoladesdescriptionEditText.getText().toString());
                        chefRewardsList.add(chefRewards);

                        accoladesrecyclerView.setAdapter(new AccoladesAdapter(getContext(), chefRewardsList));

                        accoladesitleEditText.getText().clear();
                        accoladesdescriptionEditText.getText().clear();
                    } else {
                        Toast.makeText(getContext(), "Accolades upto max:5 !!.", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
        daysRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        searchSpinner.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, genderTypes);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        searchSpinner.setAdapter(aa);

        spinnerDropDownlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchSpinner.performClick();
            }
        });

        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                cldr.add(Calendar.YEAR, -20);
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dobEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(cldr.getTimeInMillis());
                picker.show();

            }
        });


        dobIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                cldr.add(Calendar.YEAR, -20);
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dobEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(cldr.getTimeInMillis());
                picker.show();
            }
        });

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
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                isValidateDate();
                            }
                        }, year, month, day);

                picker.getDatePicker().setMinDate(System.currentTimeMillis());
                cldr.add(Calendar.YEAR, 1);
                picker.getDatePicker().setMaxDate(cldr.getTimeInMillis());
                picker.show();
            }
        });

        startDateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDateEditText.performClick();
            }
        });


        renewalDateEditText.setOnClickListener(new View.OnClickListener() {
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
                                // renewalDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                String dateFormat = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                try {
                                    Date date = format.parse(dateFormat);
                                    renewalDateEditText.setText(format.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                isValidateDate();
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis());
                cldr.add(Calendar.YEAR, 1);
                picker.getDatePicker().setMaxDate(cldr.getTimeInMillis());
                picker.show();
            }
        });

        endDateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renewalDateEditText.performClick();
            }
        });


        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }

                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                if ((ContextCompat.checkSelfPermission(getContext(),
                        WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getContext(),
                        READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);
                    }
                } else {

                    showFileChooser();
                }

            }
        });

        personalHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (personalRootLayout.getVisibility() == View.VISIBLE) {
                    personalRootLayout.setVisibility(View.GONE);
                } else {
                    personalRootLayout.setVisibility(View.VISIBLE);


                }
            }
        });

        professionalHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (professionalRootLayout.getVisibility() == View.VISIBLE) {
                    professionalRootLayout.setVisibility(View.GONE);
                } else {
                    gstEditText.requestFocus();
                    professionalRootLayout.setVisibility(View.VISIBLE);


                }
            }
        });


        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameEditText.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (locationEditText.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Location cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (dobEditText.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Date of Birth cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (bioEditText.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Bio cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (addressoneEditText.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Address line 1 cannot be empty", Toast.LENGTH_SHORT).show();
                }

                else if (pinCodeEditText.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Pincode cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (stateEditText.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "State cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (countryEditText.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Country cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (phoneEditText.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Mobile Number cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (emailEditText.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
                }

                else if (fssaiEditText.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "FSSAI cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (workingTimeEditText.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Working-time cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (timeOfDayEditText.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Time of the day cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (!isValidateDate()) {
                    Toast.makeText(getContext(), "Please check start and Renewal date", Toast.LENGTH_SHORT).show();
                } else {
                    if (filetoUploadinServer != null) {
                        uploadImage(filePath);
                    } else uploadWithoutImage();
                }
            }
        });


        getDishOfValues();

        return rootView;
    }

    private boolean isValidateDate() {
        String startDate = startDateEditText.getText().toString().trim();
        String renewalDate = renewalDateEditText.getText().toString().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date strDate = null;
        Date renwDate = null;
        try {
            strDate = sdf.parse(startDate);
            renwDate = sdf.parse(renewalDate);
            if (renwDate.after(strDate)) {
                return true;
            } else {
                Toast.makeText(getContext(), "Renewal date must be greater than start date", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get logged in Chef Profile weekdays working days  from this API(GET)
     **/

    public void getDishOfValues() {
        String url = APIBaseURL.searchOptions;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray daysArray = new JSONArray();
                    JSONArray dishCategoriesArray = new JSONArray();
                    JSONArray dishCuisinesArray = new JSONArray();
                    JSONArray dishTypesArray = new JSONArray();

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



                    getChefProfileFromAPI();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "get_dish_taqs");
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
                fileuploaded = new File(FilePath.getPath(getContext(), imageUri));
                /*if (imageHeight > 250 && imageWidth > 250) {
                    Log.v("imageok", imageHeight + "//" + imageWidth + " : resolution Ok");
                    if (fileuploaded.length() < (5 * 1024 * 1024)) {*/
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                       /* try {
                            profileImageView.setImageBitmap(compressedBitmap);

                        } catch (Exception e) {
                            Toast.makeText(getContext(), "Please choose a different image", Toast.LENGTH_SHORT).show();
                        }*/


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
                        selectedBitmap=Bitmap.createScaledBitmap(selectedBitmap,250,250,true);
                        profileImageView.setImageBitmap(selectedBitmap);
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
                            File f = new File(mFolder.getAbsolutePath(), System.currentTimeMillis() + "profile.png");

                            strMyImagePath = f.getAbsolutePath();

                            FileOutputStream outputStream = new FileOutputStream(f);

                            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);


                            filetoUploadinServer = f;


                        }
                    /*} else {
                        Toast.makeText(getContext(), "Image size must be less than 5 MB", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Image resolution must be atleast 250 X 250", Toast.LENGTH_LONG).show();
                }*/

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Please choose a different image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Custom method to go back press to previous fragment
     **/
    public void onBackPressed() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getActivity().getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(getContext());
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        FragmentManager fm = cheffragmentManager;
        fm.popBackStack();
    }

    /**
     * Get logged in Chef Profile Details from this API(GET)
     **/

    public void getChefProfileFromAPI() {
        String url = APIBaseURL.chefProfile + SaveSharedPreference.getLoggedInWorkFlowID(getContext());

        CustomVolleyRequest stringRequest = new CustomVolleyRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray dataArray = new JSONArray();
                    if (jsonObject.has("data")) {
                        dataArray = jsonObject.getJSONArray("data");
                    }
                    JSONObject dataObject = dataArray.getJSONObject(0);

                    try {

                        Glide.with(getContext()).load(dataObject.optString("uploadLogo")).placeholder(R.drawable.customer_user_profile_left_menu).into(profileImageView);

                        Log.d("FunctionCall", "FunctionCall");
                        fileImageURLFromServer = dataObject.optString("uploadLogo");


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    nameEditText.setText(dataObject.optString("name"));
                    JSONObject locationObject = new JSONObject();
                    if (dataObject.has("location")) {
                        locationObject = dataObject.getJSONObject("location");
                    }
                    locationObjectFromAPI = locationObject;
                    locationEditText.setText(locationObject.optString("name"));
                    int spinnerPosition = aa.getPosition(dataObject.optString("gender"));
                    searchSpinner.setSelection(spinnerPosition);
                    selectedGenderType = dataObject.optString("gender");
                    JSONArray accoladesArray = new JSONArray();
                    if (dataObject.has("accolades")) {
                        accoladesArray = dataObject.getJSONArray("accolades");
                    }
                    chefRewardsList.clear();
                    for (int i = 0; i < accoladesArray.length(); i++) {
                        JSONObject accoladesObject = accoladesArray.getJSONObject(i);
                        ChefRewards chefRewards = new ChefRewards();
                        chefRewards.setName(accoladesObject.optString("title"));
                        chefRewards.setDescription(accoladesObject.optString("accoladeDescription"));
                        chefRewardsList.add(chefRewards);

                        accoladesrecyclerView.setAdapter(new AccoladesAdapter(getContext(), chefRewardsList));
                    }
                    bioEditText.setText(dataObject.optString("aboutYou"));
                    addressoneEditText.setText(dataObject.optString("addressLine1"));
                    addresstwoEditText.setText(dataObject.optString("addressLine2"));
                    pinCodeEditText.setText(dataObject.optString("pinCode"));
                    countryEditText.setText(dataObject.optString("country"));
                    stateEditText.setText(dataObject.optString("state"));
                    gstEditText.setText(dataObject.optString("gstNumber"));
                    panEditText.setText(dataObject.optString("panNumber"));
                    fssaiEditText.setText(dataObject.optString("fssaiNumber"));
                    workingTimeEditText.setText(dataObject.optString("workingHours"));
                    timeOfDayEditText.setText(dataObject.optString("timeOfTheDay"));
                    professionalAddress1 = dataObject.optString("professionalAddress1");
                    professionalAddress2 = dataObject.optString("professionalAddress2");
                    kitchenName = dataObject.optString("kitchenName");
                    cloudKitchen = dataObject.optString("cloudKitchen");
                    uploadLogo = dataObject.optString("uploadLogo");
                    isSelfPickUp = dataObject.optBoolean("isSelfPickUp");
                    isHomeKitchen = dataObject.optBoolean("isHomeKitchen");
                    isCloudKitchen = dataObject.optBoolean("isCloudKitchen");
                    contactArray = dataObject.getJSONArray("contact");
                    contractsArray = dataObject.getJSONArray("contracts");
                    certificatesArray = dataObject.getJSONArray("certificates");
                    bankName = dataObject.optString("bankName");
                    branch = dataObject.optString("branch");
                    accountNumber = dataObject.optString("accountNumber");
                    ifscCode = dataObject.optString("ifscCode");
                    isActive = dataObject.optString("isActive");
                    lat = dataObject.optString("lat");
                    lng = dataObject.optString("lng");
                    city = dataObject.optString("city");
                    aboutYou = dataObject.optString("aboutYou");

                    JSONArray contactNumbers = new JSONArray();
                    if (dataObject.has("contact")) {
                        contactNumbers = dataObject.getJSONArray("contact");
                    }
                    if (contactNumbers.length() != 0) {
                        phoneEditText.setText(contactNumbers.getString(0));
                    }


                    JSONArray daysOfTheWeekArray = new JSONArray();
                    if (dataObject.has("daysOfTheWeek")) {
                        daysOfTheWeekArray = dataObject.getJSONArray("daysOfTheWeek");
                    }

                    if (daysOfTheWeekArray.length() != 0) {
                        ArrayList<String> stringList = new ArrayList<>();

                        for (int i = 0; i < daysOfTheWeekArray.length(); i++) {

                            Object getValuesforAvailableDaysTypes = daysOfTheWeekArray.get(i);

                            for (int j = 0; j < daysListArray.size(); j++) {
                                if (getValuesforAvailableDaysTypes.equals(daysListArray.get(j).getIdForCheck())) {
                                    String finalResultNamesForDishTypes = daysListArray.get(j).getWeekDayName();
                                    stringList.add(finalResultNamesForDishTypes);
                                    Log.d("GetSelected", daysListArray.get(j).getWeekDayName() + " Selected");
                                    Log.d("getValuesCheck", "True " + "  ");
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

                            selectedWeekDaysforAddInEditProfile.add(category5);
                            dishTypesLists.add(category5);

                        }


                        daysRecyclerView.setAdapter(new MenuCategoryAdapter(getContext(), dishTypesLists, fontBold, fontRegular, "EditChef"));

                    } else {
                        daysRecyclerView.setAdapter(new MenuCategoryAdapter(getContext(), daysListObject, fontBold, fontRegular, "EditChef"));
                    }

                    JSONObject franchiseObject = new JSONObject();
                    if (dataObject.has("franchise")) {
                        franchiseObject = dataObject.getJSONObject("franchise");
                    }

                    franchiseeID = franchiseObject.optString("franchiseId");
                    franchiseName = franchiseObject.optString("franchiseName");
                    franchiseeLocation = franchiseObject.optString("franchiseLocation");
                    franchiseLogo = franchiseObject.optString("franchiseLogo");


                    dateOfBirth = dataObject.optString("dateOfBirth");
                    try {
                        emailEditText.setText(dataObject.getJSONArray("email").get(0).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Date newDate = null;
                    Date newDate1 = null;
                    Date newDate2 = null;
                    try {
                        newDate1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dataObject.optString("startDate"));
                        newDate2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dataObject.optString("renewalDate"));
                        if (dataObject.optString("dateOfBirth").equals("null") || dataObject.optString("dateOfBirth").equals(null)) {

                        } else {
                            newDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dataObject.optString("dateOfBirth"));
                        }

                    } catch (ParseException e) {
                        newDate1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dataObject.optString("startDate"));
                        newDate2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dataObject.optString("renewalDate"));
                        if (dataObject.optString("dateOfBirth").equals("null") || dataObject.optString("dateOfBirth").equals(null)) {

                        } else {
                            newDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dataObject.optString("dateOfBirth"));
                        }

                        e.printStackTrace();
                    }

                    try {
                        String formattedDate1 = new SimpleDateFormat("dd/MM/yyyy").format(newDate1);
                        String formattedDate2 = new SimpleDateFormat("dd/MM/yyyy").format(newDate2);

                        startDateEditText.setText(formattedDate1);
                        renewalDateEditText.setText(formattedDate2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(newDate);
                        dobEditText.setText(formattedDate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, getContext());
        ApplicationController.getInstance().addToRequestQueue(stringRequest, "chef_profile_page");
    }


    /**
     * To Upload the Profile Details with Image if gets changed by Chef using upload Info third party library(PUT )
     **/
    private void uploadImage(String filePath) {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Updating your deatils, Please wait..");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        JSONObject inputObject = new JSONObject();

        try {
            JSONArray emailArray = new JSONArray();
            emailArray.put(emailEditText.getText().toString());
            try {

                inputObject.put("name", nameEditText.getText().toString());
                JSONObject locationObject = new JSONObject();

                locationObject.put("id", locationObjectFromAPI.optString("id"));
                locationObject.put("name", locationEditText.getText().toString());
                locationObject.put("position", locationObjectFromAPI.getJSONObject("position"));
                inputObject.put("location", locationObject);

                inputObject.put("lat", lat);
                inputObject.put("lng", lng);
                inputObject.put("aboutYou", bioEditText.getText().toString());
                inputObject.put("addressLine1", addressoneEditText.getText().toString());
                inputObject.put("addressLine2", addresstwoEditText.getText().toString());
                inputObject.put("pinCode", pinCodeEditText.getText().toString());
                inputObject.put("city", city);
                inputObject.put("state", stateEditText.getText().toString());
                inputObject.put("country", countryEditText.getText().toString());

                inputObject.put("contact", contactArray);
                inputObject.put("email", emailArray);
                inputObject.put("uploadLogo", uploadLogo);
                inputObject.put("gstNumber", gstEditText.getText().toString());
                inputObject.put("panNumber", panEditText.getText().toString());
                inputObject.put("isActive", isActive);
                inputObject.put("bankName", bankName);
                inputObject.put("branch", branch);
                inputObject.put("accountNumber", accountNumber);

                JSONArray accoladesArray = new JSONArray();
                for (int i = 0; i < chefRewardsList.size(); i++) {
                    JSONObject accoladesObject = new JSONObject();
                    accoladesObject.put("title", chefRewardsList.get(i).getName());
                    accoladesObject.put("accoladeDescription", chefRewardsList.get(i).getDescription());
                    accoladesArray.put(accoladesObject);
                }
                inputObject.put("accolades", accoladesArray);
                inputObject.put("ifscCode", ifscCode);

                inputObject.put("certificates", certificatesArray);
                inputObject.put("fssaiNumber", fssaiEditText.getText().toString());
                JSONObject franchiseObject = new JSONObject();
                franchiseObject.put("franchiseId", franchiseeID);
                franchiseObject.put("franchiseName", franchiseName);
                franchiseObject.put("franchiseLocation", franchiseeLocation);
                franchiseObject.put("franchiseLogo", franchiseLogo);
                inputObject.put("franchise", franchiseObject);
                inputObject.put("professionalAddress1", professionalAddress1);
                inputObject.put("professionalAddress2", professionalAddress2);
                inputObject.put("contracts", contractsArray);
                inputObject.put("kitchenName", kitchenName);
                JSONArray cusineArray = new JSONArray();
                cusineArray.put(1);
                inputObject.put("cusine", cusineArray);
                JSONArray labelArray = new JSONArray();
                labelArray.put(1);
                inputObject.put("label", labelArray);
                inputObject.put("workingHours", workingTimeEditText.getText().toString());
                inputObject.put("daysOfTheWeek", "");
                inputObject.put("timeOfTheDay", timeOfDayEditText.getText().toString());
                inputObject.put("isSelfPickUp", isSelfPickUp);
                inputObject.put("gender", selectedGenderType);
                Date newDate1 = null;
                Date newDate2 = null;
                try {
                    newDate1 = new SimpleDateFormat("dd/MM/yyyy").parse(renewalDateEditText.getText().toString());
                    newDate2 = new SimpleDateFormat("dd/MM/yyyy").parse(startDateEditText.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String formattedDate1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(newDate1);
                String formattedDate2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(newDate2);

                inputObject.put("renewalDate", formattedDate1);
                inputObject.put("startDate", formattedDate2);
                Date newDate = null;
                try {
                    newDate = new SimpleDateFormat("dd/MM/yyyy").parse(dobEditText.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String formattedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(newDate);

                inputObject.put("dateOfBirth", formattedDate);
                inputObject.put("isCloudKitchen", isCloudKitchen);
                inputObject.put("isHomeKitchen", isHomeKitchen);
                JSONArray availableOnDaysArray = new JSONArray();

                for (int i = 0; i < selectedWeekDaysforAddInEditProfile.size(); i++) {
                    if (selectedWeekDaysforAddInEditProfile.get(i).getSelected()) {
                        availableOnDaysArray.put(selectedWeekDaysforAddInEditProfile.get(i).getIdForCheck());
                    }

                }
                inputObject.put("daysOfTheWeek", availableOnDaysArray);

                if (cloudKitchen == null || cloudKitchen.equals("null")) {
                    inputObject.put("cloudKitchen", "");
                } else {
                    inputObject.put("cloudKitchen", cloudKitchen);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(getContext(), uploadId, APIBaseURL.updateChefProfile)
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .addHeader("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(getContext()))
                    .setMethod("PUT")
                    .addParameter("request", inputObject.toString())
                    .addParameter("id", SaveSharedPreference.getLoggedInWorkFlowID(getContext()))

                    .addFileToUpload(filetoUploadinServer.getAbsolutePath(), "UploadLogo")
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {

                            progressDialog.show();
                        }


                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            progressDialog.dismiss();

                            deleteTempImageFolder();
                            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
                        }


                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            progressDialog.dismiss();

                            Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
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
                    .setMaxRetries(5)
                    .startUpload();
        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    /**
     * To Upload the Profile Details without Image get changed by Chef using upload Info third party library(PUT )
     **/
    private void uploadWithoutImage() {

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Updating your deatils, Please wait..");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        JSONObject inputObject = new JSONObject();

        try {
            JSONArray emailArray = new JSONArray();
            emailArray.put(emailEditText.getText().toString());
            try {

                inputObject.put("name", nameEditText.getText().toString());
                JSONObject locationObject = new JSONObject();

                locationObject.put("id", locationObjectFromAPI.optString("id"));
                locationObject.put("name", locationEditText.getText().toString());
                locationObject.put("position", locationObjectFromAPI.getJSONObject("position"));
                inputObject.put("location", locationObject);

                inputObject.put("lat", lat);
                inputObject.put("lng", lng);
                inputObject.put("aboutYou", bioEditText.getText().toString());
                inputObject.put("addressLine1", addressoneEditText.getText().toString());
                inputObject.put("addressLine2", addresstwoEditText.getText().toString());
                inputObject.put("pinCode", pinCodeEditText.getText().toString());
                inputObject.put("city", city);
                inputObject.put("state", stateEditText.getText().toString());
                inputObject.put("country", countryEditText.getText().toString());

                inputObject.put("contact", contactArray);
                inputObject.put("email", emailArray);
                inputObject.put("uploadLogo", fileImageURLFromServer);
                inputObject.put("gstNumber", gstEditText.getText().toString());
                inputObject.put("panNumber", panEditText.getText().toString());
                inputObject.put("isActive", isActive);
                inputObject.put("bankName", bankName);
                inputObject.put("branch", branch);
                inputObject.put("accountNumber", accountNumber);

                JSONArray accoladesArray = new JSONArray();
                for (int i = 0; i < chefRewardsList.size(); i++) {
                    JSONObject accoladesObject = new JSONObject();
                    accoladesObject.put("title", chefRewardsList.get(i).getName());
                    accoladesObject.put("accoladeDescription", chefRewardsList.get(i).getDescription());
                    accoladesArray.put(accoladesObject);
                }
                inputObject.put("accolades", accoladesArray);
                inputObject.put("ifscCode", ifscCode);

                inputObject.put("certificates", certificatesArray);
                inputObject.put("fssaiNumber", fssaiEditText.getText().toString());
                JSONObject franchiseObject = new JSONObject();
                franchiseObject.put("franchiseId", franchiseeID);
                franchiseObject.put("franchiseName", franchiseName);
                franchiseObject.put("franchiseLocation", franchiseeLocation);
                franchiseObject.put("franchiseLogo", franchiseLogo);
                inputObject.put("franchise", franchiseObject);
                inputObject.put("professionalAddress1", professionalAddress1);
                inputObject.put("professionalAddress2", professionalAddress2);
                inputObject.put("contracts", contractsArray);
                inputObject.put("kitchenName", kitchenName);
                JSONArray cusineArray = new JSONArray();
                cusineArray.put(1);
                inputObject.put("cusine", cusineArray);
                JSONArray labelArray = new JSONArray();
                labelArray.put(1);
                inputObject.put("label", labelArray);
                inputObject.put("workingHours", workingTimeEditText.getText().toString());
                inputObject.put("daysOfTheWeek", "");
                inputObject.put("timeOfTheDay", timeOfDayEditText.getText().toString());
                inputObject.put("isSelfPickUp", isSelfPickUp);
                inputObject.put("gender", selectedGenderType);
                Date newDate1 = null;
                Date newDate2 = null;
                try {
                    newDate1 = new SimpleDateFormat("dd/MM/yyyy").parse(renewalDateEditText.getText().toString());
                    newDate2 = new SimpleDateFormat("dd/MM/yyyy").parse(startDateEditText.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String formattedDate1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(newDate1);
                String formattedDate2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(newDate2);

                inputObject.put("renewalDate", formattedDate1);
                inputObject.put("startDate", formattedDate2);
                Date newDate = null;
                try {
                    newDate = new SimpleDateFormat("dd/MM/yyyy").parse(dobEditText.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String formattedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(newDate);

                inputObject.put("dateOfBirth", formattedDate);
                inputObject.put("isCloudKitchen", isCloudKitchen);
                inputObject.put("isHomeKitchen", isHomeKitchen);
                JSONArray availableOnDaysArray = new JSONArray();

                for (int i = 0; i < selectedWeekDaysforAddInEditProfile.size(); i++) {
                    if (selectedWeekDaysforAddInEditProfile.get(i).getSelected()) {
                        availableOnDaysArray.put(selectedWeekDaysforAddInEditProfile.get(i).getIdForCheck());
                    }

                }
                inputObject.put("daysOfTheWeek", availableOnDaysArray);

                if (cloudKitchen == null || cloudKitchen.equals("null")) {
                    inputObject.put("cloudKitchen", "");
                } else {
                    inputObject.put("cloudKitchen", cloudKitchen);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String uploadId = UUID.randomUUID().toString();
            new MultipartUploadRequest(getContext(), uploadId, APIBaseURL.updateChefProfile)
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .addHeader("Authorization", "Bearer " + SaveSharedPreference.getAzureToken(getContext()))
                    .setMethod("PUT")
                    .addParameter("request", inputObject.toString())
                    .addParameter("id", SaveSharedPreference.getLoggedInWorkFlowID(getContext()))

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

                            Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
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
                    .setMaxRetries(5)
                    .startUpload();
        } catch (Exception e) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    /**
     * Show Gallery Intent Custom Method
     **/
    private void showFileChooser() {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        output = new File(dir, System.currentTimeMillis() / 1000 + "profile" + ".jpeg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_IMAGE_REQUEST);
    }

    /**
     * Get gallery selected image file path
     **/
    private String getFilePath(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToFirst();
        String res = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        cursor.close();
        return res;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedGenderType = genderTypes[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
