package gvs.com.vistingcard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userLog extends AppCompatActivity {
TextView textView;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        textView=(TextView)findViewById(R.id.ureg);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(userLog.this,UserRegister.class);
                startActivity(intent);

            }
        });
    }

    public void cpLog(View view){
        final String email=((EditText)findViewById(R.id.username)).getText().toString();
        final String password=((EditText)findViewById(R.id.upass)).getText().toString();
        if (email.length() <= 1 || password.length() <= 1 ) {
            Toast.makeText(userLog.this, "All Fields Should be More then 3 Characters", Toast.LENGTH_SHORT).show();

        } else {
            final String email_pass=email+"_"+password;
            final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("User_Details");
            ref.orderByChild("username_password").equalTo(email_pass).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists())
                    {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String key = childSnapshot.getKey();
                            String name = childSnapshot.child("name").getValue().toString();
                            String email = childSnapshot.child("email").getValue().toString();
                            String username = childSnapshot.child("username").getValue().toString();
                            String phone = childSnapshot.child("phone").getValue().toString();

                            Log.d("name is", key);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name",name);
                            editor.putString("email",email);
                            editor.putString("username",username);
                            editor.putString("key",key);
                            editor.putString("phone",phone);

                            editor.commit();
                            Log.d("keyis", key);
                            Intent intent = new Intent(userLog.this,UserHome.class);
                            startActivity(intent);
                        }


                    }else{
                        Toast.makeText(userLog.this, "Failed To Login", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }
}
