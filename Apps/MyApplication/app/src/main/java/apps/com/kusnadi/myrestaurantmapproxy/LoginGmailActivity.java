package apps.com.kusnadi.myrestaurantmapproxy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

/**
 * Created by Kusnadi on 11/5/2016.
 */

public class LoginGmailActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private SignInButton btnLogin;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleApiClient googleApiClient;
    private static final int REQUEST_CODE_LOGIN = 10;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_gmail_layout);

        session = new Session(this);
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .addApi(Plus.API).build();
        btnLogin = (SignInButton) findViewById(R.id.signin_button);
        btnLogin.setSize(SignInButton.SIZE_ICON_ONLY);
        btnLogin.setScopes(googleSignInOptions.getScopeArray());

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent signin = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signin, REQUEST_CODE_LOGIN);
            }
        });
        if(session.loggedin()){
            startActivity(new Intent(LoginGmailActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOGIN) {
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount account = googleSignInResult.getSignInAccount();

            try {
                Intent sendData = new Intent(LoginGmailActivity.this, MainActivity.class);
                String name, email, dpUrl = "";
                name = account.getDisplayName();
                email = account.getEmail();

                dpUrl = account.getPhotoUrl().toString();
                sendData.putExtra("p_name", name);
                sendData.putExtra("p_email", email);
                sendData.putExtra("p_url", dpUrl);

                session.setLoggedin(true);
                LoginGmailActivity.this.finish();
                startActivity(sendData);
            }
            catch (Exception e) {
                Toast.makeText(LoginGmailActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(LoginGmailActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
