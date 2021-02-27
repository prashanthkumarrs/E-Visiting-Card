package gvs.com.vistingcard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Otp extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }
    public void otp(View view){
        final String otp=((EditText)findViewById(R.id.otp)).getText().toString();
        final String cusername=sharedPreferences.getString("cname","");

        final String cusername_pass=cusername+"_"+otp;
        databaseReference= FirebaseDatabase.getInstance().getReference("User_Details");

        databaseReference.orderByChild("username_otp").equalTo(cusername_pass).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Toast.makeText(Otp.this, " Verify Sucess", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(Otp.this,userLog.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(Otp.this, " Invalid Otp", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(Otp.this,Otp.class);
                    startActivity(intent);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
