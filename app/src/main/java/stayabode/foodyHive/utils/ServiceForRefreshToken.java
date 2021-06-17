package stayabode.foodyHive.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.core.app.NotificationCompat;

import stayabode.foodyHive.R;
import stayabode.foodyHive.authentication.AuthStateManager;
import stayabode.foodyHive.authentication.Configuration;

import net.openid.appauth.AppAuthConfiguration;
import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.ClientAuthentication;
import net.openid.appauth.TokenRequest;
import net.openid.appauth.TokenResponse;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class ServiceForRefreshToken extends Service {

    private AuthorizationService mAuthService;
    private AuthStateManager mStateManager;
    private final AtomicReference<JSONObject> mUserInfoJson = new AtomicReference<>();
    private ExecutorService mExecutor;
    private Configuration mConfiguration;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startForeground(62318, builtNotification());
        AuthState state = mStateManager.getCurrent();


        if(state.getAccessToken() == null)
        {

        }
        else
        {
            SaveSharedPreference.SaveAzureAdToken(getApplicationContext(), state.getAccessToken());
        }

        if (state.getAccessToken() == null) {

        } else {
            Long expiresAt = state.getAccessTokenExpirationTime();
            if (expiresAt == null) {

            } else if (expiresAt < System.currentTimeMillis()) {

            } else {
                String template = getResources().getString(R.string.access_token_expires_at);

            }
            refreshAccessToken();
        }

        new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                if(millisUntilFinished<8000&&millisUntilFinished>5000){
//                    if(!isSecond){

                        AuthState state = mStateManager.getCurrent();


                        if(state.getAccessToken() == null)
                        {

                        }
                        else
                        {
                            SaveSharedPreference.SaveAzureAdToken(getApplicationContext(), state.getAccessToken());
                        }

                        if (state.getAccessToken() == null) {

                        } else {
                            Long expiresAt = state.getAccessTokenExpirationTime();
                            if (expiresAt == null) {

                            } else if (expiresAt < System.currentTimeMillis()) {

                            } else {
                                String template = getResources().getString(R.string.access_token_expires_at);

                            }
                            refreshAccessToken();
                        }


//                    }
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        mStateManager = AuthStateManager.getInstance(this);
        mExecutor = Executors.newSingleThreadExecutor();
        mConfiguration = Configuration.getInstance(this);

        Configuration config = Configuration.getInstance(this);
        if (config.hasConfigurationChanged()) {
            Toast.makeText(
                    this,
                    "Configuration change detected",
                    Toast.LENGTH_SHORT)
                    .show();
            //signOut();
            return;
        }

        mAuthService = new AuthorizationService(
                this,
                new AppAuthConfiguration.Builder()
                        .setConnectionBuilder(config.getConnectionBuilder())
                        .build());

        super.onCreate();
        startForeground(62318, builtNotification());

        AuthState state = mStateManager.getCurrent();


        if(state.getAccessToken() == null)
        {

        }
        else
        {
            SaveSharedPreference.SaveAzureAdToken(getApplicationContext(), state.getAccessToken());
        }

        if (state.getAccessToken() == null) {

        } else {
            Long expiresAt = state.getAccessTokenExpirationTime();
            if (expiresAt == null) {

            } else if (expiresAt < System.currentTimeMillis()) {

            } else {
                String template = getResources().getString(R.string.access_token_expires_at);

            }
            refreshAccessToken();
        }

    }

    public Notification builtNotification()
    {

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;

        NotificationCompat.Builder builder = null;
        Uri soundUri = Uri.parse("android.resource://" +
                getApplicationContext().getPackageName());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel =
                    new NotificationChannel("ID", "Name", importance);
            // Creating an Audio Attribute
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();

            notificationChannel.setSound(soundUri,audioAttributes);
            notificationManager.createNotificationChannel(notificationChannel);
            builder = new NotificationCompat.Builder(this, notificationChannel.getId());
        } else {
            builder = new NotificationCompat.Builder(this);
        }

        builder.setDefaults(Notification.DEFAULT_LIGHTS);
        builder.setSound(soundUri);


        String message = "Forever running service";
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(false)
                .setPriority(Notification.PRIORITY_MAX)
                .setSound(soundUri)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setColor(Color.parseColor("#0f9595"))
                .setContentTitle(getString(R.string.app_name))
                .setContentText(message);

        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, launchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        return notification;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ForegroundServiceLauncher.getInstance().startService(this);
    }


    @MainThread
    private void exchangeAuthorizationCode(AuthorizationResponse authorizationResponse) {

        performTokenRequest(
                authorizationResponse.createTokenExchangeRequest(),
                this::handleCodeExchangeResponse);
    }

    @MainThread
    private void performTokenRequest(
            TokenRequest request,
            AuthorizationService.TokenResponseCallback callback) {
        ClientAuthentication clientAuthentication;
        try {
            clientAuthentication = mStateManager.getCurrent().getClientAuthentication();
        } catch (ClientAuthentication.UnsupportedAuthenticationMethod ex) {
            Log.d("TAG", "Token request cannot be made, client authentication for the token "
                    + "endpoint could not be constructed (%s)", ex);

            return;
        }

        mAuthService.performTokenRequest(
                request,
                clientAuthentication,
                callback);
    }

    @WorkerThread
    private void handleAccessTokenResponse(
            @Nullable TokenResponse tokenResponse,
            @Nullable AuthorizationException authException) {
        mStateManager.updateAfterTokenResponse(tokenResponse, authException);

    }

    @WorkerThread
    private void handleCodeExchangeResponse(
            @Nullable TokenResponse tokenResponse,
            @Nullable AuthorizationException authException) {

        mStateManager.updateAfterTokenResponse(tokenResponse, authException);
        if (!mStateManager.getCurrent().isAuthorized()) {
            final String message = "Authorization Code exchange failed"
                    + ((authException != null) ? authException.error : "");


        } else {

        }
    }

    @MainThread
    private void refreshAccessToken() {

        performTokenRequest(
                mStateManager.getCurrent().createTokenRefreshRequest(),
                this::handleAccessTokenResponse);
    }


}
