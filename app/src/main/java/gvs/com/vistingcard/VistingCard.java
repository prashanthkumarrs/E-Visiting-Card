package gvs.com.vistingcard;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class VistingCard extends Fragment {
Button save_button, background,logo,name,designation,land,phone,email,web1,web2,address,cname,buttonleft,buttonright,buttonbottom,buttontop;
TextView textView_name,textView_desgnation,textView_land,textView_phone,textView_email,textView_web1,textView_web2,textView_address,textView_cname;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef,databaseReference,databaseReference2;
    private ImageView imageView;
    SharedPreferences sharedPreferences;
    int flag=0;
    String username;
    private Uri imgUri;
    public static final String MyPREFERENCES = "MyPrefs";
    RelativeLayout vistingCard;
    int count=0,count1=0;
    public static final String FB_STORAGE_PATH = "image/";
    public static final String FB_DATABASE_PATH = "image";
    public static final int REQUEST_CODE = 1234;
    int marginleft_name,margintop_name,marginleft_descgnation,margintop_descgnation,marginleft_land,margintop_land,marginleft_phone,margintop_phone,marginleft_web1,margintop_web1,
    marginleft_web2,margintop_web2,marginleft_address,marginleft_email,margintop_address,marginleft_cname,marginleft_image,margintop_cname,marginleft_image1,marginleft_email1,margintop_email,logoleft,logotop;
   String backgroundImages1,logoimage;
   RelativeLayout vistingcardLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_visting_card, container, false);
        background=(Button)view.findViewById(R.id.button_background);
        logo=(Button)view.findViewById(R.id.button_log);
        name=(Button)view.findViewById(R.id.button_name);
        designation=(Button)view.findViewById(R.id.button_designation);
        land=(Button)view.findViewById(R.id.button_landline);
        phone=(Button)view.findViewById(R.id.button_phone);
        email=(Button)view.findViewById(R.id.button_email);
        web1=(Button)view.findViewById(R.id.button_web);
        web2=(Button)view.findViewById(R.id.button_web2);
        address=(Button)view.findViewById(R.id.button_address);
        cname=(Button)view.findViewById(R.id.button_companyname);
        buttonleft=(Button)view.findViewById(R.id.button_left);
        buttonright=(Button)view.findViewById(R.id.button_right);
        buttontop=(Button)view.findViewById(R.id.button_Top);
        buttonbottom=(Button)view.findViewById(R.id.button_bottom);
        save_button=(Button)view.findViewById(R.id.button_save);
        textView_name=(TextView)view.findViewById(R.id.textView_name1);
        textView_desgnation=(TextView)view.findViewById(R.id.textView_designation);
        textView_land=(TextView)view.findViewById(R.id.textView_land);
        textView_phone=(TextView)view.findViewById(R.id.textview_phone);
        textView_email=(TextView)view.findViewById(R.id.textview_email1);
        textView_web1=(TextView)view.findViewById(R.id.textView_web1);
        textView_web2=(TextView)view.findViewById(R.id.textView_web2);
        textView_address=(TextView)view.findViewById(R.id.textView_address);
        textView_cname=(TextView)view.findViewById(R.id.textView_cname);
        imageView=(ImageView)view.findViewById(R.id.imageButton_logo);
        vistingCard=(RelativeLayout)view.findViewById(R.id.vistingcard);

       textView_name.setVisibility(View.INVISIBLE);
        textView_desgnation.setVisibility(View.INVISIBLE);
        textView_land.setVisibility(View.INVISIBLE);
        textView_phone.setVisibility(View.INVISIBLE);
        textView_email.setVisibility(View.INVISIBLE);
        textView_web1.setVisibility(View.INVISIBLE);
        textView_web2.setVisibility(View.INVISIBLE);
        textView_address.setVisibility(View.INVISIBLE);
        textView_cname.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Background_Image_Details");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Logo_Image_Details");
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Template_Details");

        logo.setOnClickListener(new View.OnClickListener() {
       @Override
        public void onClick(View v) {
           flag=10;
        btnBrowse_Click1(view);
        }
        });
        background.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBrowse_Click(view);

            }
        });

        buttonleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_name.getLayoutParams();

                    lp.setMargins(lp.leftMargin+10, lp.topMargin, lp.rightMargin, lp.bottomMargin);
                     marginleft_name=lp.leftMargin;
                    textView_name.setLayoutParams(lp);
                }else if (flag==2){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_desgnation.getLayoutParams();
                    int marginleft=lp.leftMargin+10;

                    lp.setMargins(marginleft, lp.topMargin, lp.rightMargin, lp.bottomMargin);
                    marginleft_descgnation=lp.leftMargin;
                    textView_desgnation.setLayoutParams(lp);
                }else if(flag==3){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_land.getLayoutParams();
                    int marginleft=lp.leftMargin+10;

                    lp.setMargins(marginleft, lp.topMargin, lp.rightMargin, lp.bottomMargin);
                    marginleft_land=lp.leftMargin;
                    textView_land.setLayoutParams(lp);
                }else if(flag==4){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_phone.getLayoutParams();
                    int marginleft=lp.leftMargin+10;

                    lp.setMargins(marginleft, lp.topMargin, lp.rightMargin, lp.bottomMargin);
                    marginleft_phone=lp.leftMargin;
                    textView_phone.setLayoutParams(lp);
                }else if(flag==5){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_email.getLayoutParams();
                    int marginleft=lp.leftMargin+10;

                    lp.setMargins(marginleft, lp.topMargin, lp.rightMargin, lp.bottomMargin);
                            marginleft_email=lp.leftMargin;
                    textView_email.setLayoutParams(lp);
                }else if(flag==6){
                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_web1.getLayoutParams();
                    int marginleft=lp.leftMargin+10;

                    lp.setMargins(marginleft, lp.topMargin, lp.rightMargin, lp.bottomMargin);
marginleft_web1=lp.leftMargin;
                    textView_web1.setLayoutParams(lp);
                }else if(flag==7){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_web2.getLayoutParams();
                    int marginleft=lp.leftMargin+10;

                    lp.setMargins(marginleft, lp.topMargin, lp.rightMargin, lp.bottomMargin);
                        marginleft_web2=lp.leftMargin;
                    textView_web2.setLayoutParams(lp);
                }else if(flag==8){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_address.getLayoutParams();
                    int marginleft=lp.leftMargin+10;

                    lp.setMargins(marginleft, lp.topMargin, lp.rightMargin, lp.bottomMargin);
                        marginleft_address=lp.leftMargin;
                    textView_address.setLayoutParams(lp);
                }else if(flag==9){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_cname.getLayoutParams();
                    int marginleft=lp.leftMargin+10;

                    lp.setMargins(marginleft, lp.topMargin, lp.rightMargin, lp.bottomMargin);
                    marginleft_cname=lp.leftMargin;
                    textView_cname.setLayoutParams(lp);
                }else if(flag==10){
                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                   marginleft_image=lp.leftMargin+10;

                    lp.setMargins(marginleft_image, lp.topMargin, lp.rightMargin, lp.bottomMargin);
                    logoleft=lp.leftMargin;

                    imageView.setLayoutParams(lp);
                }

            }
        });
        buttonright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==1){
                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_name.getLayoutParams();
                    lp.setMargins(lp.leftMargin-10, lp.topMargin, lp.rightMargin, lp.bottomMargin);

                    textView_name.setLayoutParams(lp);
                }else if(flag==2){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_desgnation.getLayoutParams();
                    lp.setMargins(lp.leftMargin-10, lp.topMargin, lp.rightMargin, lp.bottomMargin);

                    textView_desgnation.setLayoutParams(lp);
                }else if(flag==3){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_land.getLayoutParams();
                    lp.setMargins(lp.leftMargin-10, lp.topMargin, lp.rightMargin, lp.bottomMargin);

                    textView_land.setLayoutParams(lp);
                }else if(flag==4){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_phone.getLayoutParams();
                    lp.setMargins(lp.leftMargin-10, lp.topMargin, lp.rightMargin, lp.bottomMargin);

                    textView_phone.setLayoutParams(lp);
                }else if(flag==5){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_email.getLayoutParams();
                    lp.setMargins(lp.leftMargin-10, lp.topMargin, lp.rightMargin, lp.bottomMargin);

                    textView_email.setLayoutParams(lp);
                }else if(flag==6){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_web1.getLayoutParams();
                    lp.setMargins(lp.leftMargin-10, lp.topMargin, lp.rightMargin, lp.bottomMargin);

                    textView_web1.setLayoutParams(lp);
                }else if(flag==7){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_web2.getLayoutParams();
                    lp.setMargins(lp.leftMargin-10, lp.topMargin, lp.rightMargin, lp.bottomMargin);

                    textView_web2.setLayoutParams(lp);
                }else if(flag==8){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_address.getLayoutParams();
                    lp.setMargins(lp.leftMargin-10, lp.topMargin, lp.rightMargin, lp.bottomMargin);

                    textView_address.setLayoutParams(lp);
                }else if(flag==9){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_cname.getLayoutParams();
                    lp.setMargins(lp.leftMargin-10, lp.topMargin, lp.rightMargin, lp.bottomMargin);

                    textView_cname.setLayoutParams(lp);
                }else if(flag==10){
                RelativeLayout.LayoutParams lp =
                        (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                lp.setMargins(lp.leftMargin-10, lp.topMargin, lp.rightMargin, lp.bottomMargin);

                imageView.setLayoutParams(lp);
            }}
        });
        buttonbottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) textView_name.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin-10, lp.rightMargin, lp.bottomMargin);
                    textView_name.setLayoutParams(lp);
                }else if(flag==2){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_desgnation.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin-10, lp.rightMargin, lp.bottomMargin);

                    textView_desgnation.setLayoutParams(lp);
                }else if(flag==3){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_land.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin-10, lp.rightMargin, lp.bottomMargin);

                    textView_land.setLayoutParams(lp);
                }else if(flag==4){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_phone.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin-10, lp.rightMargin, lp.bottomMargin);

                    textView_phone.setLayoutParams(lp);
                }else if(flag==5){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_email.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin-10, lp.rightMargin, lp.bottomMargin);

                    textView_email.setLayoutParams(lp);
                }else if(flag==6){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_web1.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin-10, lp.rightMargin, lp.bottomMargin);

                    textView_web1.setLayoutParams(lp);
                }else if(flag==7){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_web2.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin-10, lp.rightMargin, lp.bottomMargin);

                    textView_web2.setLayoutParams(lp);
                }else if(flag==8){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_address.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin-10, lp.rightMargin, lp.bottomMargin);

                    textView_address.setLayoutParams(lp);
                }else if(flag==9){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_cname.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin-10, lp.rightMargin, lp.bottomMargin);
                    textView_cname.setLayoutParams(lp);
                }else if(flag==10){
                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin-10, lp.rightMargin, lp.bottomMargin);
                    imageView.setLayoutParams(lp);
                }

            }
        });
        buttontop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_name.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin+10, lp.rightMargin, lp.bottomMargin);
                    textView_name.setLayoutParams(lp);
                    margintop_name=lp.topMargin;

                }else if(flag == 2){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_desgnation.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin+10, lp.rightMargin, lp.bottomMargin);
                    margintop_descgnation=lp.topMargin;
                    textView_desgnation.setLayoutParams(lp);
                }else if(flag==3){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_land.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin+10, lp.rightMargin, lp.bottomMargin);
                    margintop_land=lp.topMargin;
                    textView_land.setLayoutParams(lp);
                }else if(flag==4){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_phone.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin+10, lp.rightMargin, lp.bottomMargin);
                    margintop_phone=lp.topMargin;
                    textView_phone.setLayoutParams(lp);
                }else if(flag==5){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_email.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin+10, lp.rightMargin, lp.bottomMargin);
                    margintop_email=lp.topMargin;
                    textView_email.setLayoutParams(lp);
                }else if(flag==6){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_web1.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin+10, lp.rightMargin, lp.bottomMargin);
                    margintop_web1=lp.topMargin;
                    textView_web1.setLayoutParams(lp);
                }else if(flag==7){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_web2.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin+10, lp.rightMargin, lp.bottomMargin);
                    margintop_web2=lp.topMargin;
                    textView_web2.setLayoutParams(lp);
                }else if(flag==8){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_address.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin+10, lp.rightMargin, lp.bottomMargin);
                    margintop_address=lp.topMargin;
                    textView_address.setLayoutParams(lp);
                }else if(flag==9){

                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) textView_cname.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin+10, lp.rightMargin, lp.bottomMargin);
                    textView_cname.setLayoutParams(lp);
                    margintop_cname=lp.topMargin;
                }else if(flag==10){
                    RelativeLayout.LayoutParams lp =
                            (RelativeLayout.LayoutParams) imageView.getLayoutParams();
                    lp.setMargins(lp.leftMargin, lp.topMargin+10, lp.rightMargin, lp.bottomMargin);
                    imageView.setLayoutParams(lp);
                    marginleft_image1=lp.topMargin;
                    logotop=lp.topMargin;
                }

            }
        });
        name.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        textView_name.setText("aa");
                                        textView_name.setVisibility(View.VISIBLE);
                                        flag = 1;
                                    }
                                });

                designation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        textView_desgnation .setVisibility(View.VISIBLE);
                        flag = 2;
                    }
                });
        land.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_land.setVisibility(View.VISIBLE);
                flag=3;
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  textView_phone.setVisibility(View.VISIBLE);
                                                  flag=4;
                                              }
                                          }
        );

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_email.setVisibility(View.VISIBLE);

                flag=5;
            }
        });
web1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        textView_web1.setVisibility(View.VISIBLE);

        flag=6;
    }
});
web2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        textView_web2.setVisibility(View.VISIBLE);

        flag=7;
    }
});
address.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        textView_address.setVisibility(View.VISIBLE);

        flag=8;
    }
});
cname.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        textView_cname.setVisibility(View.VISIBLE);

        flag=9;
    }
});

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id=databaseReference2.push().getKey();
                Card card=new Card();
                card.setName(textView_name.getText().toString());
                card.setNameLeft(String.valueOf(marginleft_name));
                card.setNameTop(String.valueOf(margintop_name));
                card.setDescgnation(textView_desgnation.getText().toString());
                card.setDescgnationLeft(String.valueOf(marginleft_descgnation));
                card.setDescgnationTop(String.valueOf(margintop_descgnation));
                card.setLanalinePhone(textView_land.getText().toString());
                card.setLandlineLeft(String.valueOf(marginleft_land));
                card.setLandlineTop(String.valueOf(margintop_land));
                card.setPhone(textView_phone.getText().toString());
                card.setPhoneLeft(String.valueOf(marginleft_phone));
                card.setPhoneTop(String.valueOf(margintop_phone));
                card.setEmail1(textView_email.getText().toString());
                card.setEmailLeft(String.valueOf(marginleft_email));
                card.setEmailTop(String.valueOf(margintop_email));
                card.setWeb1(textView_web1.getText().toString());
                card.setWeb1Left(String.valueOf(marginleft_web1));
                card.setWeb1Top(String.valueOf(margintop_web1));
                card.setWeb2(textView_web2.getText().toString());
                card.setWeb2Left(String.valueOf(marginleft_web2));
                card.setWeb2Top(String.valueOf(margintop_web2));
                card.setAddress1(textView_address.getText().toString());
                card.setAddressLeft(String.valueOf(marginleft_address));
                card.setAddressTop(String.valueOf(margintop_address));
                card.setCname(textView_cname.getText().toString());
                card.setCnameLeft(String.valueOf(marginleft_cname));
                card.setCnameTop(String.valueOf(margintop_cname));
                card.setBackgroindImage(backgroundImages1);
                card.setLogoimages(logoimage);
                card.setLogoleft(String.valueOf(logoleft));
                card.setLogoTop(String.valueOf(logotop));
                card.setLayoutHeight(String.valueOf(vistingCard.getHeight()));
                card.setLayoutWidth(String.valueOf(vistingCard.getWidth()));
                databaseReference2.child(id).setValue(card);
                FragmentManager fm=getFragmentManager();
                fm.beginTransaction().replace(R.id.admin_fragment_container,new Ahome()).commit();


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

         View view = null;
         if(requestCode == REQUEST_CODE && resultCode == getActivity().RESULT_OK
                 && data != null && data.getData() != null )  {
             imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imgUri);
                if(count==1){
                    btnUpload_Click(view);

                }
                if(count1==2){
                    btnUpload_Click1(view);

                }

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
                    String id=mDatabaseRef.push().getKey();
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String curdate = df.format(c);
                    backgroundImages1=taskSnapshot.getDownloadUrl().toString();
                    BackgroundImages backgroundImages=new BackgroundImages(id,taskSnapshot.getDownloadUrl().toString());
                    mDatabaseRef.child(id).setValue(backgroundImages);

                    Glide.with(VistingCard.this)
                            .load(backgroundImages1)
                            .into(new CustomTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    vistingCard.setBackground(resource);

                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {

                                }
                            });

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
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot1) {


                    //Dimiss dialog when success
                    dialog.dismiss();
                    //Display success toast msg
                    Toast.makeText(getContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
                    String id=databaseReference.push().getKey();
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String curdate = df.format(c);
                    logoimage=taskSnapshot1.getDownloadUrl().toString();
                    LogoImages backgroundImages=new LogoImages(id,taskSnapshot1.getDownloadUrl().toString());
                    databaseReference.child(id).setValue(backgroundImages);
                    Toast.makeText(getContext(), "Image uploaded to Database", Toast.LENGTH_SHORT).show();
                    imageView.setVisibility(View.VISIBLE);
                    Picasso.get().load(logoimage).into(imageView);

                    //Glide.with(getContext()).load(taskSnapshot1.getDownloadUrl().toString()).into(imageView);


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
