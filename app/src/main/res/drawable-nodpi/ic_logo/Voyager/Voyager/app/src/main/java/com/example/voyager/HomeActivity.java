package com.example.voyager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    TextView show;
    EditText message;
    Button send;
    String fn;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    int flag=0;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        show = findViewById(R.id.show);
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserMessage(message.getText().toString().trim());
                String s = message.getText().toString().trim();
                message.setText("");
                s=s.toLowerCase();
                String[] n = s.split(" ");


                for(int i=0;i<n.length;i++) {
                    if (n[i].equals("andheri") || n[i].equals("bandra") || n[i].equals("borivali") || n[i].equals("chembur") || n[i].equals("churchgate") || n[i].equals("dadar") || n[i].equals("ghatkopar") || n[i].equals("juhu") || n[i].equals("kurla") || n[i].equals("matunga") || n[i].equals("mulund") || n[i].equals("powai") || n[i].equals("sion") || n[i].equals("thane") || n[i].equals("vashi")) {
                        flag = 1;
                        String nn = n[i];
                        String nf = String.valueOf(nn.charAt(0));
                        nf = nf.toUpperCase();
                        fn = nf + nn.substring(1);
                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot i : dataSnapshot.getChildren()) {
                                    Log.i("valueee", String.valueOf(i));
                                    if (i.getKey().equals(fn)) {
                                        addLayer("Top 5 Restaurants in the given area are:");
                                        Log.i("valueee", String.valueOf(i));
                                        for (DataSnapshot j : i.getChildren()) {
                                            addLayer("Name: " + j.getKey() + "\nAddress: " + j.getValue());

                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    }

                    else if(n[i].equals("fine") || n[i].equals("good") || n[i].equals("well")){
                        addLayer("Great !! \nTell me how can I help you ?");
                        flag=1;
                    }


                    else {
                        flag = 0;
                    }

                }

                if(s.equals("how are you") || s.equals("how about you") || s.equals("what about you")){
                    addLayer("I am fine\nTell me how can I help you ?");
                    flag=2;
                }

                if(flag==0)
                {
                    addLayer("Not able to find location. Please try another location");
                }
            }
        });
    }

    public void addUserMessage(String val)
    {
        LinearLayout msgLayout;
        msgLayout = findViewById(R.id.msgLayout);
        TextView message = new TextView(this);
        Typeface face = ResourcesCompat.getFont(getApplicationContext(),R.font.aladin);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(152,30,0,0);
        layoutParams.gravity = Gravity.RIGHT;
        message.setLayoutParams(layoutParams);
        message.setTextSize(20);
        message.setTypeface(face);
        message.setGravity(Gravity.RIGHT);
        message.setBackgroundResource(R.drawable.incoming);
        message.setPadding(90,60,90,60);
        message.setTextColor(Color.parseColor("#ffffff"));
        message.setText(val);
        msgLayout.addView(message);
    }

    public void addLayer(String val)
    {
        LinearLayout msgLayout;
        msgLayout = findViewById(R.id.msgLayout);
        TextView message = new TextView(this);
        Typeface face = ResourcesCompat.getFont(getApplicationContext(),R.font.aladin);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,30,152,0);
        message.setLayoutParams(layoutParams);
        message.setTypeface(face);
        message.setTextSize(18);
        message.setBackgroundResource(R.drawable.outgoing);
        message.setPadding(90,60,90,60);
        message.setTextColor(Color.parseColor("#ffffff"));
        message.setText(val);
        msgLayout.addView(message);
    }

}
