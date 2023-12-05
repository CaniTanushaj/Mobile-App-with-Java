package ba.fsre.trecaaplikacija;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ba.fsre.trecaaplikacija.model.User;

public class ProfileActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase db;
    FirebaseUser loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

          this.auth=FirebaseAuth.getInstance();
        this.db=FirebaseDatabase.getInstance();


        this.loggedUser=this.auth.getCurrentUser();

        DatabaseReference userDatabaseRef = this.db.getReference("users");

        EditText firstnameTxt = findViewById(R.id.nameTxt);
        EditText lastnameTxt = findViewById(R.id.surnameTxt);
        EditText phoneTxt = findViewById(R.id.brojTxt);
        EditText addressTxt = findViewById(R.id.adresaTxt);
        EditText placeTxt = findViewById(R.id.placeTxt);
        EditText countryTxt  = findViewById(R.id.countryTxt);

        Button saveBtn = findViewById(R.id.buttonSubmit);

        userDatabaseRef.child("users").child(this.loggedUser.getUid()).get().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    try {
                        User currentUser = task.getResult().getValue(User.class);
                        firstnameTxt.setText(currentUser.firstname);
                        lastnameTxt.setText(currentUser.lastname);
                        addressTxt.setText(currentUser.phone);
                        phoneTxt.setText(currentUser.address);
                        placeTxt.setText(currentUser.place);
                        countryTxt.setText(currentUser.country);
                    }catch (NullPointerException ex) {
                        Log.e("No data", ex.getMessage());
                    }
                }
            });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User currentUser = new User(
                        firstnameTxt.getText().toString(),
                        lastnameTxt.getText().toString(),
                        addressTxt.getText().toString(),
                        phoneTxt.getText().toString(),
                        placeTxt.getText().toString(),
                        countryTxt.getText().toString()
                );
                userDatabaseRef.child("users").child(loggedUser.getUid()).setValue(currentUser);
                Toast.makeText(getApplicationContext(),"Uneseni podatci",Toast.LENGTH_LONG).show();
            }
        });
    }
}