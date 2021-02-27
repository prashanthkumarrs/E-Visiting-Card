package gvs.com.vistingcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void adminlog(View view){
        Intent intent=new Intent(MainActivity.this,adminlog.class);
        startActivity(intent);

    }
    public void user(View view){
        Intent intent=new Intent(MainActivity.this,userLog.class);
        startActivity(intent);

    }

}
