package com.example.administrator.restoapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.restoapp.R;
import com.example.administrator.restoapp.models.SignupLoginModel;
import com.example.administrator.restoapp.utils.RestoApi;
import com.example.administrator.restoapp.utils.SharedPrefUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreen extends AppCompatActivity {

    private static final int RC_SIGN_IN = 456;
    @BindView(R.id.tv_fb_btn)
    TextView tvFbBtn;
    @BindView(R.id.bt_googleLogin)
    TextView btGoogleLogin;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;

    private RestoApi webService;
    private CallbackManager fbCallbackManager;
    private static final String TAG = "SplashScreen";
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);


        configureGoogleSignIn();

//        generateDebugKey();


        btGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();

            }
        });

        tvFbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbCallbackManager = CallbackManager.Factory.create();
                LoginManager loginManager = LoginManager.getInstance();
                loginManager.logInWithReadPermissions(SplashScreen.this, Arrays.asList("email"));
                loginManager.registerCallback(fbCallbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                Log.d(TAG, "facebook callback onSuccess: " + loginResult.getAccessToken());
                                GraphRequest graphRequest = GraphRequest.newMeRequest(
                                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(JSONObject json, GraphResponse response) {
                                                if (response.getError() != null) {
                                                    // handle error
                                                    Log.d(TAG, "GraphRequest onCompleted: Error");
                                                } else {
                                                    Log.d(TAG, "GraphRequest onCompleted: Success");
                                                    try {
                                                        AccessToken accessToken = AccessToken.getCurrentAccessToken();
                                                        // TODO: 25/10/16 send below info to continueWithFB WS
                                                        String fbToken = accessToken.getToken();
                                                        String fbId = json.getString("id");
                                                        String imageUrl = "https://graph.facebook.com/" + json
                                                                .getString("id") + "/picture?type=large";
                                                        String firstName = json.getString("first_name");
                                                        String lastName = json.getString("last_name");
                                                        String fullname = firstName + " " + lastName;
                                                        String email = json.getString("email");
                                                        loginSignUp(fullname, fbId, email, fbToken, imageUrl);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }

                                        });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id, first_name, last_name, email");
                                graphRequest.setParameters(parameters);
                                graphRequest.executeAsync();
                            }

                            @Override
                            public void onCancel() {
                                Log.d(TAG, "facebook callback onCancel ");
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Log.d(TAG, "facebook callback onError: " + exception);
                            }
                        });


            }
        });


    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            signOutGoogle();
            result = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
//            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

            if (result != null) {


                String gId = result.getId();
                String gToken = result.getIdToken();
                String personName = result.getDisplayName();
//                String personGivenName = result.getGivenName();
//                String personFamilyName = result.getFamilyName();
                String personEmail = result.getEmail();
                String personId = result.getId();
                String personPhoto = null;
                try {

                    personPhoto = result.getPhotoUrl().toString();

                } catch (Exception e) {

                    personPhoto = "";

                }
                googleSignIn(personName, gId, personEmail, personPhoto);


            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }

    private void googleSignIn(String fullname, String gId, String personEmail, String personPhoto) {

        Call<SignupLoginModel> googleLoginCall = getWebService().loginWithGoogle(fullname, "a@bs.com", gId, "0000", personPhoto);

        googleLoginCall.enqueue(new Callback<SignupLoginModel>() {
            @Override
            public void onResponse(Call<SignupLoginModel> call, Response<SignupLoginModel> response) {


                SignupLoginModel model = response.body();


                String token = model.token;
                String profile_pic = model.pic;
                SharedPrefUtils.put(SplashScreen.this,"token",token);
                startActivity(new Intent(SplashScreen.this,HomeActivity.class));
                finish();
                Log.d(TAG, "onResponse: successsss --- > " + response.body());


            }

            @Override
            public void onFailure(Call<SignupLoginModel> call, Throwable t) {


                t.printStackTrace();

                Log.d(TAG, "onResponse: errorrrrrr --- > ");

            }
        });


    }

    private void signOutGoogle() {

        mGoogleSignInClient.signOut();

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void configureGoogleSignIn() {


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    private void loginSignUp(String fullname, String fbId, String email, String fbToken, String profile_pic) {

        Call<SignupLoginModel> signupLogin = getWebService().loginWithFacebook(fullname, email, fbId, fbToken, profile_pic);

        signupLogin.enqueue(new Callback<SignupLoginModel>() {
            @Override
            public void onResponse(Call<SignupLoginModel> call, Response<SignupLoginModel> response) {

                SignupLoginModel model = response.body();


                String token = model.token;
                String profile_pic = model.pic;
                SharedPrefUtils.put(SplashScreen.this,"token",token);

                SharedPrefUtils.put(SplashScreen.this,"token",token);
                startActivity(new Intent(SplashScreen.this,HomeActivity.class));
                finish();

                
                Log.d(TAG, "onResponse: successsss --- > " + response.body());

            }

            @Override
            public void onFailure(Call<SignupLoginModel> call, Throwable t) {

                Log.d(TAG, "onResponse: errorrrrrr --- > ");


            }
        });


    }

//    private void generateDebugKey() {
//
//        PackageInfo info;
//        try {
//            info = getPackageManager().getPackageInfo("com.example.administrator.restoapp", PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md;
//                md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String something = new String(Base64.encode(md.digest(), 0));
//                //String something = new String(Base64.encodeBytes(md.digest()));
//                Log.e("hash key", something);
//            }
//        } catch (PackageManager.NameNotFoundException e1) {
//            Log.e("name not found", e1.toString());
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("no such an algorithm", e.toString());
//        } catch (Exception e) {
//            Log.e("exception", e.toString());
//        }
//
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

        if (fbCallbackManager != null) {
            fbCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private RestoApi getWebService() {
        if (webService == null) {
            webService = new Retrofit.Builder().baseUrl(RestoApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory
                            .create())
                    .build()
                    .create(RestoApi.class);
        }
        return webService;
    }
}
