package stayabode.foodyHive.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.List;

import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_CHEF;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_PREF;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_ADDRESS;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_AZURE_ACCESS_TOKEN;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_CARTS_LIST;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_CARTS_LIST_CART_ID;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_CARTS_LIST_POP_UP_BOOLEAN;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_CARTS_LIST_POP_UP_STRING;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_EMAIL;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_FIREBASE_TOKEN;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_FRESH_USER;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_HAS_SEEN_INTRO_SCREENS;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_ID;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_LATITUDE;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_LONGITUDE;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_NAME;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_PHONE_NUMBER;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_PROFILE;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_REFERRAL_CODE;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_REFERRY_CODE;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_ROLE;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_ROLE_ID;
import static stayabode.foodyHive.utils.PreferencesUtility.LOGGED_IN_USER_WORK_FLOW_ID;

public class SaveSharedPreference {

    public static  SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     */
    public static void setLoggedIn(Context context, boolean loggedIn,String role,String roleID,String id,String name,String email,String workFlowId,String profileImage,String loggedinUserReferral,String loggedinUserReferryCode,boolean loggedinUserFlag,String phoneNo_str) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.putString(LOGGED_IN_USER_ID,id);
        editor.putString(LOGGED_IN_USER_ROLE,role);
        editor.putString(LOGGED_IN_USER_ROLE_ID,roleID);
        editor.putString(LOGGED_IN_USER_NAME,name);
        editor.putString(LOGGED_IN_USER_EMAIL,email);
        editor.putString(LOGGED_IN_USER_WORK_FLOW_ID,workFlowId);
        editor.putString(LOGGED_IN_USER_PROFILE,profileImage);
        editor.putString(LOGGED_IN_USER_REFERRAL_CODE,loggedinUserReferral);
        editor.putString(LOGGED_IN_USER_REFERRY_CODE,loggedinUserReferryCode);
        editor.putBoolean(LOGGED_IN_USER_FRESH_USER,loggedinUserFlag);
        editor.putString(LOGGED_IN_USER_PHONE_NUMBER,phoneNo_str);
        editor.apply();
    }

    /**
     * Set the Address Status
     * @param context
     * @param address
     */
    public static void saveAddress(Context context,String address)
    {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(LOGGED_IN_USER_ADDRESS, address);
        editor.apply();
    }

    /**
     * Set the Cart is Already there or Not
     * @param context
     * @param isSelected
     */
    public static void alreadySavedPopUp(Context context,boolean isSelected,String selectedText)
    {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_USER_CARTS_LIST_POP_UP_BOOLEAN, isSelected);
        editor.putString(LOGGED_IN_USER_CARTS_LIST_POP_UP_STRING, selectedText);
        editor.apply();
    }

    /**
     * Sava the User Latitude and longitude
     * @param context
     * @param latitude
     * @param longitude
     */
    public static void saveLatLong(Context context,String latitude,String longitude)
    {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(LOGGED_IN_USER_LATITUDE, latitude);
        editor.putString(LOGGED_IN_USER_LONGITUDE, longitude);
        editor.apply();
    }

    /**
     * Save the Token
     * @param context
     * @param token
     */
    public static void saveToken(Context context,String token) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(LOGGED_IN_USER_FIREBASE_TOKEN,token);
        editor.apply();
    }

    /**
     * Save the Azure Bearer Token
     * @param context
     * @param accessToken
     */
    public static void SaveAzureAdToken(Context context,String accessToken)
    {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(LOGGED_IN_USER_AZURE_ACCESS_TOKEN,accessToken);
        editor.apply();
    }

    /**
     * Save the Cart Id from Cart
     * @param context
     * @param cartID
     */
    public static void saveCartID(Context context,String cartID)
    {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(LOGGED_IN_USER_CARTS_LIST_CART_ID,cartID);
        editor.apply();
    }




    /**
     * Check if user seen the intro screen or not
     * @param context
     */
    public static Boolean ifUserSeenIntroScreens(Context context)
    {
        return getPreferences(context).getBoolean(LOGGED_IN_USER_HAS_SEEN_INTRO_SCREENS, false);
    }



    /**
     * Get the pop up is already opened or not for user
     * @param context
     */
    public static boolean checKPopUpAlreadySelected(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_USER_CARTS_LIST_POP_UP_BOOLEAN, false);
    }

    /**
     * Get the pop up is already opened or not for user
     * @param context
     */
    public static String checKPopUpSelectedText(Context context) {
        return getPreferences(context).getString(LOGGED_IN_USER_CARTS_LIST_POP_UP_STRING, "");
    }

    /**
     * Get the role Info for user
     * @param context
     */
    public static String getLoggedInUserRole(Context context) {
        return getPreferences(context).getString(LOGGED_IN_USER_ROLE,"");
    }
    /**
     * Get the cart  Id for user
     * @param context
     */
    public static String getLoggedInUserCartID(Context context) {
        return getPreferences(context).getString(LOGGED_IN_USER_CARTS_LIST_CART_ID,"");
    }
    /**
     * Get the user name for user
     * @param context
     */
    public static String getLoggedInUserName(Context context) {
        return getPreferences(context).getString(LOGGED_IN_USER_NAME,"");
    }
    /**
     * Get the email Id for user
     * @param context
     */
    public static String getLoggedInUserEmail(Context context) {
        return getPreferences(context).getString(LOGGED_IN_USER_EMAIL,"");
    }
    /**
     * Get the user flow Id for user
     * @param context
     */
    public static String getLoggedInWorkFlowID(Context context) {
        return getPreferences(context).getString(LOGGED_IN_USER_WORK_FLOW_ID,"");
    }
    /**
     * Get the user Id for user
     * @param context
     */
    public static String getLoggedInUserID(Context context) {
        return getPreferences(context).getString(LOGGED_IN_USER_ID,"");
    }


    public static String getLoggedInUserPhone(Context context) {
        return getPreferences(context).getString(LOGGED_IN_USER_PHONE_NUMBER,"");
    }


    /**
     * Get the Address for user location
     * @param context
     */
    public static String getLoggedInUserAddress(Context context)
    {
        return getPreferences(context).getString(LOGGED_IN_USER_ADDRESS,"");
    }

    /**
     * Get the latitude for user location
     * @param context
     */
    public static String getLoggedInUserLatitude(Context context)
    {
        return getPreferences(context).getString(LOGGED_IN_USER_LATITUDE,"");
    }


    /**
     * Get the longitude for user location
     * @param context
     */
    public static String getLoggedInUserLongitude(Context context)
    {
        return getPreferences(context).getString(LOGGED_IN_USER_LONGITUDE,"");
    }



    /**
     * Get the Token Bearer Token which is already saved while logging in
     * @param context
     */
    public static String getAzureToken(Context context) {
        return getPreferences(context).getString(LOGGED_IN_USER_AZURE_ACCESS_TOKEN,"");
    }


    public static <Chef> void setList(List<Chef> list,Context context) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        set(json,context);
    }

    public static void set( String value,Context context) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(LOGGED_IN_USER_CARTS_LIST, value);
        editor.commit();
    }



    public static String getUserFilePath(Context context){
        return getPreferences(context).getString(LOGGED_IN_USER_PROFILE, "");
    }




    /**
     * Set the Address Status
     * @param context
     * @param logged_in_chef
     */
    public static void saveChef(Context context,String logged_in_chef)
    {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(LOGGED_IN_CHEF, logged_in_chef);
        editor.apply();
    }

    /**
     * Get the role Info for user
     * @param context
     */
    public static String getsaveChef(Context context) {
        return getPreferences(context).getString(LOGGED_IN_CHEF,"");
    }

}
