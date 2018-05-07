package in.medit.codemed.chatmet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 539;

    private FirebaseListAdapter<ChatMessages> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

            Toast.makeText(this,
                    "Welcome " + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName(),
                    Toast.LENGTH_LONG)
                    .show();

            displayChatMessages();
    }


    private void displayChatMessages() {

        ListView listOfMessages = findViewById(R.id.lv_msg);

        adapter = new FirebaseListAdapter<ChatMessages>(this, ChatMessages.class,
                R.layout.list_item, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, ChatMessages model, int position) {
                // Get references to the views of message.xml
                TextView messageText =  v.findViewById(R.id.msg_view);
                CircleImageView messageUser =  v.findViewById(R.id.profile_image);
                TextView messageTime =  v.findViewById(R.id.msg_post_time);

                // Set their text
                messageText.setText(model.getMessageText());
                Picasso.get().load(model.getMessageUser()).into(messageUser);

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM (HH:mm)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this,
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG)
                        .show();
                displayChatMessages();
            } else {
                Toast.makeText(this,
                        "Please try again later.",
                        Toast.LENGTH_LONG)
                        .show();

                finish();
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sign_out:
                AuthUI.getInstance().signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(MainActivity.this,
                                        "You have been signed out.",
                                        Toast.LENGTH_LONG)
                                        .show();

                                // Close activity
                                finish();
                            }
                        });
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendMsg(View view) {
        EditText input = findViewById(R.id.type_msg);

        FirebaseDatabase.getInstance()
                .getReference()
                .push()
                .setValue(new ChatMessages(input.getText().toString(),
                        FirebaseAuth.getInstance()
                                .getCurrentUser()
                                .getPhotoUrl().toString())
                );

        input.setText("");
    }
}

