package com.atirek.alm.fcmdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.atirek.alm.fcmdemo.PojoClass.PersonRow;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDatabaseAccess extends AppCompatActivity implements Button.OnClickListener {

    private EditText editTextName;
    private EditText editTextAddress;
    private TextView textViewPersons;
    private Button buttonSave;
    private Button buttonRetrieve;
    ImageButton imgBtn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonRetrieve = (Button) findViewById(R.id.buttonRetrieve);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        textViewPersons = (TextView) findViewById(R.id.textViewPersons);
        imgBtn_next = (ImageButton) findViewById(R.id.imgBtn_nextPage);

        Intent FirebaseMessagingService = new Intent(this, com.atirek.alm.fcmdemo.MessagingService.FirebaseMessagingService.class);
        startService(FirebaseMessagingService);

        Intent FirebaseInstanceIDListenerService = new Intent(this, com.atirek.alm.fcmdemo.MessagingService.FirebaseInstanceIDListenerService.class);
        startService(FirebaseInstanceIDListenerService);

        buttonSave.setOnClickListener(this);
        buttonRetrieve.setOnClickListener(this);

        imgBtn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jump = new Intent(FirebaseDatabaseAccess.this, FirebaseList.class);
                startActivity(jump);
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        try {

            //Creating firebase object
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference();


            switch (id) {
                case R.id.buttonSave:

                    if (editTextName.getText().toString().isEmpty() && editTextAddress.getText().toString().isEmpty()) {
                        Toast.makeText(FirebaseDatabaseAccess.this, "All The Above Fields are Required...", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //Getting values to store
                    String name = editTextName.getText().toString().trim();
                    String address = editTextAddress.getText().toString().trim();

                    //Creating object
                    PersonRow personRow = new PersonRow(name, address);

/*
                //Adding values
                personRow.setName(name);
                personRow.setAddress(address);
*/

                    //Storing values to firebase
                    reference.child("Person").child(editTextName.getText().toString()).setValue(personRow);

                    Toast.makeText(FirebaseDatabaseAccess.this, "Data Stored", Toast.LENGTH_SHORT).show();

                    editTextName.setText("");
                    editTextAddress.setText("");

                    break;

                case R.id.buttonRetrieve:

                    if (editTextName.getText().toString().isEmpty()) {
                        Toast.makeText(FirebaseDatabaseAccess.this, "Name Field Should Not Be Empty...", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    reference.child("Person").child(editTextName.getText().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            try {
                                PersonRow personRow = dataSnapshot.getValue(PersonRow.class);
                                //Adding it to a string
                                String string = "Name: " + personRow.getName() + "\nAddress: " + personRow.getAddress() + "\n\n";

                                Toast.makeText(FirebaseDatabaseAccess.this, "Data Retrieved", Toast.LENGTH_SHORT).show();
                                //Displaying it on textview
                                textViewPersons.setText(string);

                            } catch (Exception e) {
                                Log.d("Exception", e.getMessage());
                                Toast.makeText(FirebaseDatabaseAccess.this, "Sorry! Name Doesn't Exists...", Toast.LENGTH_SHORT).show();
                            }

                            editTextName.setText("");
                            editTextAddress.setText("");

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("DatabaseError>>>>", databaseError.getMessage());
                        }
                    });

                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            Log.d("Exception>>>>", e.getMessage());
        }

    }

}
