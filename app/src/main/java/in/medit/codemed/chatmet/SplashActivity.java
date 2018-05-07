package in.medit.codemed.chatmet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;


public class SplashActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 539;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Checking if any user is available after 500ms
                FirebaseAuth auth = FirebaseAuth.getInstance();
                if (auth.getCurrentUser() != null) {
                    // already signed in
                    moveToHome();
                    finish();

                } else {
                    // not signed in
                    notSignedIn();
                }
            }
        }, 500);
    }

    private void moveToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void notSignedIn() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.drawable.logo)
                        .setTheme(R.style.MyTheme)
                        .setProviders(
                                AuthUI.GOOGLE_PROVIDER
                        )
                        .build(),
                RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                moveToHome();
            } else {
//                // Sign in failed
//                if (response == null) {
//                    // User pressed back button
//                    showToast("Sign in cancelled");
//                    return;
//                }
//
//                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
//                    showToast("No internet");
//                    return;
//                }

                showToast("Unknown Error");
            }
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
