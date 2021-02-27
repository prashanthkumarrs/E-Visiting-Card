package gvs.com.vistingcard;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class UpdateDetails extends Fragment {
String cname,cemail,cphone,id,cwebaddress,caddress,choldername,cposition,upby,backgroundimage,logoimage,cname_choldrename;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;
    int count=0,count1=0;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private ImageView imageView;
    int flag=0;
    String username;
    private Uri imgUri;
    Button add_btn,logo_btn,background_btn;
    String  backgroundImages1,logoImages;
    public static final String FB_STORAGE_PATH = "image/";
    public static final String FB_DATABASE_PATH = "image";
    public static final int REQUEST_CODE = 1234;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_update_details, container, false);
        Bundle bundle=this.getArguments();
        cname=bundle.getString("cname");
        id=bundle.getString("id");
        cemail=bundle.getString("cemail");
        cphone=bundle.getString("cphone");
        cwebaddress=bundle.getString("cwebaddress");
        caddress=bundle.getString("caddress");
        choldername=bundle.getString("choldername");
        cposition=bundle.getString("cposition");
        upby=bundle.getString("upby");
        backgroundimage=bundle.getString("backgroundimage");
        logoimage=bundle.getString("logoimage");
        cname_choldrename=bundle.getString("cname_choldrename");
        databaseReference= FirebaseDatabase.getInstance().getReference("uploded_Details");
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final EditText companyname = ((EditText)view.findViewById(R.id.companyname1));
        final EditText chname = ((EditText)view.findViewById(R.id.chname1));
        final EditText position = ((EditText)view.findViewById(R.id.position1));
        final EditText phone = ((EditText)view.findViewById(R.id.phone1));
        final EditText webaddress = ((EditText)view.findViewById(R.id.webaddress1));
        final EditText cemail1 = ((EditText)view.findViewById(R.id.cemail1));
        final EditText caddress1 = ((EditText)view.findViewById(R.id.caddress1));
        mStorageRef = FirebaseStorage.getInstance().getReference();

        add_btn=(Button)view.findViewById(R.id.btn_sccc1);
        logo_btn=(Button)view.findViewById(R.id.btn_logo1);

        logo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBrowse_Click(v);
            }
        });
      companyname.setText(cname);
        chname.setText(choldername);
        position.setText(cposition);
        phone.setText(cphone);
        webaddress.setText(cwebaddress);
        cemail1.setText(cemail);
        caddress1.setText(caddress);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String companyname1=companyname.getText().toString();
                final String chname1=chname.getText().toString();
                final String position1=position.getText().toString();
                final String phone1=phone.getText().toString();
                final String webaddress1=webaddress.getText().toString();
                final String cemail11=cemail1.getText().toString();
                final String caddress11=caddress1.getText().toString();
                final String cusername=sharedPreferences.getString("username","");


                    final String hname_address=companyname1+"_"+chname1;



                                Visting_Card_Model fdel = new Visting_Card_Model(id, companyname1, cemail11, phone1,webaddress1, caddress11,chname1,position1,hname_address,cusername,logoImages);
                                databaseReference.child(id).setValue(fdel);

                                Toast.makeText(getContext(), " Update Sucess ", Toast.LENGTH_SHORT).show();
                                FragmentManager fm=getFragmentManager();
                                fm.beginTransaction().replace(R.id.user_fragment_container,new ViewDetails()).commit();






            }
        });


        return view;
    }
    public void btnBrowse_Click(View v) {
        count=1;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE);

    } public void btnBrowse_Click1(View v) {
        count1=2;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE);

    }
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getContext(),"welcome1",Toast.LENGTH_LONG).show();

        View view = null;
        if(requestCode == REQUEST_CODE && resultCode == getActivity().RESULT_OK
                && data != null && data.getData() != null )  {
            imgUri = data.getData();
            Toast.makeText(getContext(),"welcome2",Toast.LENGTH_LONG).show();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imgUri);

                    btnUpload_Click1(view);



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getImageExt(Uri uri) {

        ContentResolver contentResolver =getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    @SuppressWarnings("VisibleForTests")
    public void btnUpload_Click(View v) {
        if (imgUri != null) {
            Toast.makeText(getContext(),"welcome11",Toast.LENGTH_LONG).show();
            final ProgressDialog dialog = new ProgressDialog(getContext());
            dialog.setTitle("Uploading image");
            dialog.show();

            //Get the storage reference
            StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imgUri));

            //Add file to reference

            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Dimiss dialog when success
                    dialog.dismiss();
                    //Display success toast msg
                    Toast.makeText(getContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String curdate = df.format(c);
                    backgroundImages1=taskSnapshot.getDownloadUrl().toString();



                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            //Dimiss dialog when error
                            dialog.dismiss();
                            //Display err toast msg
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            //Show upload progress

                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else {
            Toast.makeText(getContext(), "Please select image", Toast.LENGTH_SHORT).show();
        }
    }
    public void btnUpload_Click1(View v) {
        if (imgUri != null) {
            Toast.makeText(getContext(),"welcome",Toast.LENGTH_LONG).show();
            final ProgressDialog dialog = new ProgressDialog(getContext());
            dialog.setTitle("Uploading image");
            dialog.show();

            //Get the storage reference
            StorageReference ref = mStorageRef.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(imgUri));

            //Add file to reference

            ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                    //Dimiss dialog when success
                    dialog.dismiss();
                    //Display success toast msg
                    Toast.makeText(getContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
                    String id=databaseReference.push().getKey();
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String curdate = df.format(c);
                    logoImages=taskSnapshot.getDownloadUrl().toString();
                    Toast.makeText(getContext(),"url is"+logoimage,Toast.LENGTH_LONG).show();



                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            //Dimiss dialog when error
                            dialog.dismiss();
                            //Display err toast msg
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            //Show upload progress

                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            dialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else {
            Toast.makeText(getContext(), "Please select image", Toast.LENGTH_SHORT).show();
        }
    }
}
