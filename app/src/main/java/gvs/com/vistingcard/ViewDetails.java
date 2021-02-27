package gvs.com.vistingcard;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ViewDetails extends Fragment {
    ListView club_list;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;

    List<Visting_Card_Model> detailsList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_view_details, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        databaseReference= FirebaseDatabase.getInstance().getReference("uploded_Details");
        final String username=sharedPreferences.getString("username","");
        club_list=(ListView)view.findViewById(R.id.del_list);
        databaseReference.orderByChild("upby").equalTo(username).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                detailsList = new ArrayList<Visting_Card_Model>();
                if(dataSnapshot.exists()){
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        Visting_Card_Model clubdel = childSnapshot.getValue(Visting_Card_Model.class);
                        detailsList.add(clubdel);
                    }
                   CustomAdoptor customAdoptor = new CustomAdoptor();
                    club_list.setAdapter(customAdoptor);

                }else{
                    Toast.makeText(getContext(), " ADetails Does't Exit ", Toast.LENGTH_SHORT).show();

                }

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
            view = getActivity().getLayoutInflater().inflate(R.layout.details_list,null);
            TextView cname1=(TextView)view.findViewById(R.id.cname1);
            TextView chanme1=(TextView)view.findViewById(R.id.chanme1);
            TextView cweb1=(TextView)view.findViewById(R.id.cweb1);
            TextView cemail=(TextView)view.findViewById(R.id.cemail1);
            TextView cnt1=(TextView)view.findViewById(R.id.cnt1);
            TextView caddress1=(TextView)view.findViewById(R.id.caddress1);
            TextView positi=(TextView)view.findViewById(R.id.positi);

            Button button=(Button)view.findViewById(R.id.update_btn) ;
            cname1.setText(detailsList.get(i).getCname());
            chanme1.setText(detailsList.get(i).getCholdername());
            cweb1.setText(detailsList.get(i).getCwebaddress());
            cemail.setText(detailsList.get(i).getCemail());
            cnt1.setText(detailsList.get(i).getCphone());
            caddress1.setText(detailsList.get(i).getCaddress());
            positi.setText(detailsList.get(i).getCposition());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle=new Bundle();
                    bundle.putString("id",detailsList.get(i).getId());
                    bundle.putString("cname",detailsList.get(i).getCname());
                    bundle.putString("cemail",detailsList.get(i).getCemail());
                    bundle.putString("cphone",detailsList.get(i).getCphone());
                    bundle.putString("cwebaddress",detailsList.get(i).getCwebaddress());
                    bundle.putString("caddress",detailsList.get(i).getCaddress());
                    bundle.putString("choldername",detailsList.get(i).getCholdername());
                    bundle.putString("cposition",detailsList.get(i).getCposition());
                    bundle.putString("upby",detailsList.get(i).getUpby());
                    bundle.putString("backgroundimage",detailsList.get(i).getBackgroundimage());
                    bundle.putString("logoimage",detailsList.get(i).getLogoimage());
                    bundle.putString("cname_choldrename",detailsList.get(i).getCname_choldrename());
                    UpdateDetails details=new UpdateDetails();
                    details.setArguments(bundle);
                    FragmentManager fm=getFragmentManager();
                    fm.beginTransaction().replace(R.id.user_fragment_container,details).commit();





                }
            });
            return view;
        }
    }

}
