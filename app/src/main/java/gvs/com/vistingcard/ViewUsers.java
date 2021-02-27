package gvs.com.vistingcard;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ViewUsers extends Fragment {
    ListView club_list;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;

    List<User_Register_Model> detailsList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_view_users, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        databaseReference= FirebaseDatabase.getInstance().getReference("User_Details");
        final String username=sharedPreferences.getString("username","");

        club_list=(ListView)view.findViewById(R.id.user_list);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                detailsList = new ArrayList<User_Register_Model>();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    User_Register_Model clubdel = childSnapshot.getValue(User_Register_Model.class);
                    detailsList.add(clubdel);
                }
                CustomAdoptor customAdoptor = new CustomAdoptor();
                club_list.setAdapter(customAdoptor);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    class CustomAdoptor extends BaseAdapter {

        @Override
        public int getCount() {
            return detailsList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getActivity().getLayoutInflater().inflate(R.layout.users_list,null);
            TextView hnamee=(TextView)view.findViewById(R.id.uname);
            TextView hostype=(TextView)view.findViewById(R.id.uemail);
            TextView phone=(TextView)view.findViewById(R.id.phonee);

            hnamee.setText(detailsList.get(i).getUsername());
            hostype.setText(detailsList.get(i).getEmail());
            phone.setText(detailsList.get(i).getPhone());

            return view;
        }
    }
}
