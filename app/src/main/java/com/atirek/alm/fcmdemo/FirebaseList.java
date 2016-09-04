package com.atirek.alm.fcmdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.atirek.alm.fcmdemo.CustomAdapter.FirebaseAdapter;
import com.atirek.alm.fcmdemo.PojoClass.PersonRow;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Alm on 7/6/2016.
 */
public class FirebaseList extends AppCompatActivity implements Button.OnClickListener {

    ListView lv_firebase;
    FirebaseAdapter firebaseAdapter;
    ArrayList<PersonRow> arraylist = new ArrayList<>();
    Button btn_fetch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase_list);

        btn_fetch = (Button) findViewById(R.id.btn_fetch);
        lv_firebase = (ListView) findViewById(R.id.lv_firebase);

        btn_fetch.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        try {

            switch (id) {
                case R.id.btn_fetch:

                    Toast.makeText(FirebaseList.this, "Fetching Data", Toast.LENGTH_SHORT).show();

                    arraylist.clear();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference();
                    reference.child("Person").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Object jsonObject = dataSnapshot.getValue();


                            for (DataSnapshot personSnapshot : dataSnapshot.getChildren()) {
                                PersonRow personRow = personSnapshot.getValue(PersonRow.class);
                                arraylist.add(personRow);
                            }

                            firebaseAdapter = new FirebaseAdapter(FirebaseList.this, arraylist);
                            lv_firebase.setAdapter(firebaseAdapter);

                            Toast.makeText(FirebaseList.this, "Data Fetched", Toast.LENGTH_SHORT).show();

                            Log.d("ReceivedData>>>>", jsonObject.toString());

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("DatabaseError>>>>", databaseError.getMessage());
                        }
                    });

                    break;
            }

        } catch (Exception e) {
            Log.d("Exception>>>>", e.getMessage());
        }
    }
}
