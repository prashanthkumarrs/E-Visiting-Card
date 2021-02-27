package gvs.com.vistingcard;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class UserRegister extends AppCompatActivity {
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private static final int REQUEST_READ_PHONE_STATE = 1;
    private final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private final String SENT = "SMS_SENT";
    private final String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }
    public void reg(View view){
        final String cpname=((EditText)findViewById(R.id.cname1)).getText().toString();
        final String cemail=((EditText)findViewById(R.id.cemail1)).getText().toString();
        final String cpphone=((EditText)findViewById(R.id.cphone1)).getText().toString();
        final String cppass=((EditText)findViewById(R.id.cpass1)).getText().toString();
        final String cpusername=((EditText)findViewById(R.id.cusername1)).getText().toString();
        final String cusername_pass=cpusername+"_"+cppass;
        if (cpusername.length() >= 4 && cemail.length() >= 4 && cpphone.length() == 10 && cppass.length() >= 6 && cpname.length() >= 4 && cppass.length() <= 10) {
            databaseReference = FirebaseDatabase.getInstance().getReference("User_Details");

            databaseReference.orderByChild("username_phone").equalTo(cpusername+"_"+cpphone).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Toast.makeText(UserRegister.this, " Details Already Exist", Toast.LENGTH_SHORT).show();

                    } else {
                        Random r = new Random();
                        int x = r.nextInt(123452);
                        int permissionCheck = ContextCompat.checkSelfPermission(UserRegister.this, android.Manifest.permission.READ_PHONE_STATE);
                        if (ContextCompat.checkSelfPermission(UserRegister.this, android.Manifest.permission.SEND_SMS)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) UserRegister.this, new String[]{android.Manifest.permission.SEND_SMS},
                                    MY_PERMISSIONS_REQUEST_SEND_SMS);
                        } else if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions((Activity) UserRegister.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
                            SmsManager sms = SmsManager.getDefault();

                            Log.d("hellow", "sravani2");
                            String msg = "Your OTP Is" + " : " + x;
                            sms.sendTextMessage(cpphone, null, msg, sentPI, deliveredPI);
                        } else {
                            SmsManager sms = SmsManager.getDefault();

                            Log.d("hellow", "sravani2");
                            String msg = "Your OTP Is" + " : " + x;
                            sms.sendTextMessage(cpphone, null, msg, sentPI, deliveredPI);


                        }
                        final String username_otp = cpusername+"_"+String.valueOf(x);

                        String id = databaseReference.push().getKey();
                        User_Register_Model fdel = new User_Register_Model(id, cpname, cemail, cpphone, cppass, cpusername,String.valueOf(x),username_otp,cusername_pass,cpusername+"_"+cpphone);
                        databaseReference.child(id).setValue(fdel);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("cname", cpusername);
                        editor.commit();
                        Toast.makeText(UserRegister.this, "Added Sucess", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UserRegister.this, Otp.class);
                        startActivity(intent);
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else{
            Toast.makeText(UserRegister.this, " Please Fill The All Values above 4 Characters and password length mininum 6 character and maxinum 10 character", Toast.LENGTH_SHORT).show();

        }

    }
}
