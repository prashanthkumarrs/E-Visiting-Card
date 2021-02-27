package gvs.com.vistingcard;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
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


public class UploadDetails extends Fragment {
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
        View view= inflater.inflate(R.layout.fragment_upload_details, container, false);
        databaseReference= FirebaseDatabase.getInstance().getReference("uploded_Details");
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final EditText companyname = ((EditText)view.findViewById(R.id.companyname));
        final EditText chname = ((EditText)view.findViewById(R.id.chname));
        final EditText position = ((EditText)view.findViewById(R.id.position));
        final EditText phone = ((EditText)view.findViewById(R.id.phone));
        final EditText webaddress = ((EditText)view.findViewById(R.id.webaddress));
        final EditText cemail = ((EditText)view.findViewById(R.id.cemail));
        final EditText caddress = ((EditText)view.findViewById(R.id.caddress));
        mStorageRef = FirebaseStorage.getInstance().getReference();
        add_btn=(Button)view.findViewById(R.id.btn_sccc);
        logo_btn=(Button)view.findViewById(R.id.btn_logo);
        logo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBrowse_Click(v);
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String companyname1=companyname.getText().toString();
                final String chname1=chname.getText().toString();
                final String position1=position.getText().toString();
                final String phone1=phone.getText().toString();
                final String webaddress1=webaddress.getText().toString();
                final String cemail1=cemail.getText().toString();
                final String caddress1=caddress.getText().toString();
                final String cusername=sharedPreferences.getString("username","");

                if (TextUtils.isEmpty(companyname1) && TextUtils.isEmpty(chname1) && TextUtils.isEmpty(position1) && TextUtils.isEmpty(phone1) && TextUtils.isEmpty(webaddress1) && TextUtils.isEmpty(cemail1) && TextUtils.isEmpty(caddress1) ) {
                    Toast.makeText(getContext(), " Please fill all the feilds", Toast.LENGTH_SHORT).show();

                }else{
                    final String hname_address=companyname1+"_"+chname1;

                    databaseReference.orderByChild("cname_choldrename").equalTo(hname_address).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Toast.makeText(getContext(), " Details Already Exist", Toast.LENGTH_SHORT).show();

                            }else{
                                String id = databaseReference.push().getKey();
                                Visting_Card_Model fdel = new Visting_Card_Model(id, companyname1, cemail1, phone1,webaddress1, caddress1,chname1,position1,hname_address,cusername,logoImages);
                                databaseReference.child(id).setValue(fdel);
                                Toast.makeText(getContext(), " Added Sucess ", Toast.LENGTH_SHORT).show();
                                FragmentManager fm=getFragmentManager();
                                fm.beginTransaction().replace(R.id.user_fragment_container,new ViewDetails()).commit();


                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }



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
