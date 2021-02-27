package gvs.com.vistingcard;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.LiveFolders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gvs.com.vistingcard.helper.FileUtil;
import gvs.com.vistingcard.helper.ScreenshotUtil;
import ly.img.android.ui.activities.CameraPreviewActivity;
import ly.img.android.ui.activities.CameraPreviewIntent;
import ly.img.android.ui.activities.PhotoEditorIntent;
import ly.img.android.ui.utilities.PermissionRequest;


public class ViewTemplates extends Fragment {
    ListView templatelist;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    String username;
    List<Card> detailsList;
    Card clubdel;
    Button savaButton;
    Visting_Card_Model vistingCardModel;
    RelativeLayout relativeLayout;
    private Button btnSS, btnshare, btnPL;
    private Bitmap bitmap;
    private ImageView iv;
    private String sharePath="no";
    private static final String FOLDER = "ImgLy";
    public static int CAMERA_PREVIEW_RESULT = 1;
    private RelativeLayout parentView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_view_templates, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        databaseReference= FirebaseDatabase.getInstance().getReference("Template_Details");
        databaseReference2= FirebaseDatabase.getInstance().getReference("uploded_Details");
        username=sharedPreferences.getString("username","");
        templatelist=(ListView)view.findViewById(R.id.template_list);
        iv = view.findViewById(R.id.iv);

        btnSS = view.findViewById(R.id.btnSS);

        relativeLayout=(RelativeLayout)view.findViewById(R.id.screenlayout);


        //templatelist.setBackgroundResource(R.drawable.g);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                detailsList = new ArrayList<Card>();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    clubdel = childSnapshot.getValue(Card.class);
                    detailsList.add(clubdel);
                }


                CustomAdoptor customAdoptor = new CustomAdoptor();
                templatelist.setAdapter(customAdoptor);
                //templatelist.setBackgroundResource(R.drawable.background);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*savaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout content = view.findViewById(R.id.layout);
                content.setDrawingCacheEnabled(true);
                Bitmap bitmap = content.getDrawingCache();
                File file = new File(getContext().getFilesDir(), "tempaltes"+" "+".png");
                try {
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileOutputStream ostream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
                    ostream.close();
                    content.invalidate();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    content.setDrawingCacheEnabled(false);
                }
            }
        });*/


        /*btnSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bitmap = ScreenshotUtil.getInstance().takeScreenshotForView(relativeLayout); // Take ScreenshotUtil for any view
                iv.setImageBitmap(bitmap);
                Date now = new Date();
                android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

                String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpeg";

                File imageFile = new File(mPath);

                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(imageFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                int quality = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //setting screenshot in imageview
                String filePath = imageFile.getPath();

                Bitmap ssbitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());


            }
        });*/
        btnSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeSS(templatelist);
            }
        });



        Log.d("click here","inside listview1");
        templatelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Log.d("click here","inside listview2");
                Toast.makeText(getContext(),"welcome",Toast.LENGTH_LONG).show();
                Date now = new Date();
                android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

                try {
                    // image naming and path  to include sd card  appending name you choose for file
                    String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpeg";

                    // create bitmap screen capture
                    v.setDrawingCacheEnabled(true);
                    Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());

                    v.setDrawingCacheEnabled(false);

                    File imageFile = new File(mPath);

                    FileOutputStream outputStream = new FileOutputStream(imageFile);
                    int quality = 100;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                    outputStream.flush();
                    outputStream.close();

                    //setting screenshot in imageview
                    String filePath = imageFile.getPath();

                    Bitmap ssbitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    iv.setImageBitmap(ssbitmap);
                    //sharePath = filePath;

                } catch (Throwable e) {
                    // Several error may come out with file handling or DOM
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
    private void takeScreenshot1() {
        Toast.makeText(getContext(),"hii1",Toast.LENGTH_LONG).show();

        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        try {
            Toast.makeText(getContext(),"hii2",Toast.LENGTH_LONG).show();


            Toast.makeText(getContext(),"hii4",Toast.LENGTH_LONG).show();
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // Do the file write
                String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpeg";

                // create bitmap screen capture
                View v1 = getActivity().getWindow().getDecorView().getRootView();
                v1.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                v1.setDrawingCacheEnabled(false);

                File imageFile = new File(mPath);

                FileOutputStream outputStream = new FileOutputStream(imageFile);
                int quality = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                outputStream.flush();
                outputStream.close();

                //setting screenshot in imageview
                String filePath = imageFile.getPath();

                Bitmap ssbitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                iv.setImageBitmap(ssbitmap);
                sharePath = filePath;
            } else {
                // Request permission from the user
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

            }


        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }
    private void takeSS(View v){
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                // image naming and path  to include sd card  appending name you choose for file
                String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpeg";

                // create bitmap screen capture
                v.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
                v.setDrawingCacheEnabled(false);

                File imageFile = new File(mPath);

                FileOutputStream outputStream = new FileOutputStream(imageFile);
                int quality = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                outputStream.flush();
                outputStream.close();

                //setting screenshot in imageview
                String filePath = imageFile.getPath();

                Bitmap ssbitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                iv.setImageBitmap(ssbitmap);
                Toast.makeText(getContext(),"Check your Mobile Storage",Toast.LENGTH_LONG).show();

                //sharePath = filePath;
            } else {
                // Request permission from the user
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

            }

        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }
    private void takeScreenshot() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            Date now = new Date();
            android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

            try {
                // image naming and path  to include sd card  appending name you choose for file
                String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpeg";

                // create bitmap screen capture
                View v1 = getActivity(). getWindow().getDecorView().getRootView();
                v1.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                Log.d("bitmap is", String.valueOf(bitmap));
                v1.setDrawingCacheEnabled(false);

                File imageFile = new File(mPath);

                FileOutputStream outputStream = new FileOutputStream(imageFile);
                int quality = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                outputStream.flush();
                outputStream.close();

                //setting screenshot in imageview
                String filePath = imageFile.getPath();


                Bitmap ssbitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

                iv.setImageBitmap(ssbitmap);
                Matrix mat = new Matrix();
                //mat.postScale(900, 1000);

                //  mat.postRotate(360);
                // Bitmap bMapRotate = Bitmap.createBitmap(ssbitmap, 0, 0,
                //      ssbitmap.getWidth(), ssbitmap.getHeight(), mat, true);
                // iv.setImageBitmap(bMapRotate);
                Uri uri = Uri.fromFile(imageFile);

                sharePath = filePath;

            } catch (Throwable e) {
                // Several error may come out with file handling or DOM
                e.printStackTrace();
            }
        }else {
            // Request permission from the user
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }
    }
    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width , height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

    private void share(String sharePath){
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpeg";

        File photo = new File(mPath, sharePath);

        Log.d("ffff",sharePath);
        File file = new File(sharePath);
        Uri outputFileUri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID+".fileprovider", photo);
        //Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent .setType("image/*");
        intent .putExtra(Intent.EXTRA_STREAM, outputFileUri);
        startActivity(intent );

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
            view = getActivity().getLayoutInflater().inflate(R.layout.templatelist,null);
            //view.setBackgroundResource(R.drawable.background);
            view.setLayoutParams(new LinearLayout.LayoutParams(1080,636));
            final RelativeLayout background=(RelativeLayout) view.findViewById(R.id.imagess);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.imagess);
            final TextView companyName=(TextView)view.findViewById(R.id.companyName);
            final TextView md=(TextView)view.findViewById(R.id.md);
            final TextView descgnation=(TextView)view.findViewById(R.id.descgnation);
            final TextView Cphone=(TextView)view.findViewById(R.id.Cphone);
            final TextView Caddress=(TextView)view.findViewById(R.id.Caddress);
            final TextView webAddress=(TextView)view.findViewById(R.id.webAddress);
            final TextView cEmail=(TextView)view.findViewById(R.id.cEmail);
            final ImageView logoimage=(ImageView)view.findViewById(R.id.image_logo);

            final View finalView = view;
            databaseReference2.orderByChild("upby").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        vistingCardModel=childSnapshot.getValue(Visting_Card_Model.class);
                        companyName.setText(vistingCardModel.getCname());
                        md.setText(vistingCardModel.getCholdername());
                        descgnation.setText(vistingCardModel.getCposition());
                        Cphone.setText(vistingCardModel.getCphone());
                        Caddress.setText(vistingCardModel.getCaddress());
                        webAddress.setText(vistingCardModel.getCwebaddress());
                        cEmail.setText(vistingCardModel.getCemail());

                        Glide.with(ViewTemplates.this)
                                .load(detailsList.get(i).getBackgroindImage())
                                .into(new CustomTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        finalView.setBackgroundDrawable(resource);

                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {

                                    }
                                });

                        Glide.with(getContext()).load(vistingCardModel.getLogoimage()).into(logoimage);



                        RelativeLayout.LayoutParams lp =
                                (RelativeLayout.LayoutParams) companyName.getLayoutParams();
                        lp.setMargins(Integer.parseInt(detailsList.get(i).getCnameLeft()), Integer.parseInt(detailsList.get(i).getCnameTop()), lp.rightMargin, lp.bottomMargin);
                        companyName.setLayoutParams(lp);

                        RelativeLayout.LayoutParams lp1 =
                                (RelativeLayout.LayoutParams) md.getLayoutParams();
                        lp1.setMargins(Integer.parseInt(detailsList.get(i).getNameLeft()), Integer.parseInt(detailsList.get(i).getNameTop()), lp1.rightMargin, lp1.bottomMargin);
                        md.setLayoutParams(lp1);

                        RelativeLayout.LayoutParams lp2 =
                                (RelativeLayout.LayoutParams) descgnation.getLayoutParams();
                        lp2.setMargins(Integer.parseInt(detailsList.get(i).getDescgnationLeft()), Integer.parseInt(detailsList.get(i).getDescgnationTop()), lp2.rightMargin, lp2.bottomMargin);
                        descgnation.setLayoutParams(lp2);



                        RelativeLayout.LayoutParams lp3 =
                                (RelativeLayout.LayoutParams) Cphone.getLayoutParams();
                        lp3.setMargins(Integer.parseInt(detailsList.get(i).getPhoneLeft()), Integer.parseInt(detailsList.get(i).getPhoneTop()), lp3.rightMargin, lp3.bottomMargin);
                        Cphone.setLayoutParams(lp3);



                        RelativeLayout.LayoutParams lp4 =
                                (RelativeLayout.LayoutParams) Caddress.getLayoutParams();
                        lp4.setMargins(Integer.parseInt(detailsList.get(i).getAddressLeft()), Integer.parseInt(detailsList.get(i).getAddressTop()), lp4.rightMargin, lp4.bottomMargin);
                        Caddress.setLayoutParams(lp4);


                        RelativeLayout.LayoutParams lp5 =
                                (RelativeLayout.LayoutParams) webAddress.getLayoutParams();
                        lp5.setMargins(Integer.parseInt(detailsList.get(i).getWeb1Left()), Integer.parseInt(detailsList.get(i).getWeb1Top()), lp5.rightMargin, lp5.bottomMargin);
                        webAddress.setLayoutParams(lp5);

                        RelativeLayout.LayoutParams lp6 =
                                (RelativeLayout.LayoutParams) cEmail.getLayoutParams();
                        lp6.setMargins(Integer.parseInt(detailsList.get(i).getEmailLeft()), Integer.parseInt(detailsList.get(i).getEmailTop()), lp6.rightMargin, lp6.bottomMargin);
                        cEmail.setLayoutParams(lp6);
                        RelativeLayout.LayoutParams lp7 =
                                (RelativeLayout.LayoutParams) logoimage.getLayoutParams();
                        lp7.setMargins(Integer.parseInt(detailsList.get(i).getLogoleft()), Integer.parseInt(detailsList.get(i).getLogoTop()), lp7.rightMargin, lp7.bottomMargin);
                        logoimage.setLayoutParams(lp7);

                        //Gets the layout params that will allow you to resize the layout


                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });







            return view;
        }
    }

}
