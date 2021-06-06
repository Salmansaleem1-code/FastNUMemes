package com.example.fastnumemes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    ImageView photo;
    public static final int PICK_IMAGE = 1;
GalleryDatabase dbg;
String name;
    ImageDatabase db;
    String imgDecodableString;
    final int GALLERY_REQUEST_CODE=1;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();


                    dbg=new GalleryDatabase(this);

                    dbg.InsertImage(new Profile(name,imgDecodableString));


                    // Set the Image in ImageView after decoding the String
                    photo.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));



                break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
            }
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
         name=getIntent().getStringExtra("username");

         TextView username=findViewById(R.id.picPer);

         username.setText(name);


       db=new ImageDatabase(this);


        ArrayList<Images> reg;
        reg= (ArrayList<Images>) db.FetchImg();

        TextView likes=findViewById(R.id.tLike);
        TextView unlikes=findViewById(R.id.tUnlike);
        int size=reg.size();


        if(size!=0) {
            int v1=0,v2=0;
            for(int i=0;i<reg.size();i++){
                v1=v1+reg.get(i).btnid;
                v2=v2+reg.get(i).btnid2;

            }
            String s= String.valueOf(v1);
            String s2= String.valueOf(v2);
            likes.setText(s);
            unlikes.setText(s2);


        }
        else{

            likes.setText("0");
            unlikes.setText("0");

        }
        dbg=new GalleryDatabase(this);
        ArrayList<Profile>profiles ;
        profiles= (ArrayList<Profile>) dbg.Fetchprofile();


        if(profiles.size()!=0) {
          /*  Bitmap bitmap;
File imagepath =new File(Environment.getExternalStorageDirectory()+"/img0.png");
            FileOutputStream fileOutputStream;
            try{
                fileOutputStream=new FileOutputStream(imagepath);
                photo.setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/img0.png"));
            }
            catch (Exception ex){}

           // photo.setImageBitmap(BitmapFactory.decodeFile(profiles.get(0).pic));

        }
*/
        }


photo= findViewById(R.id.profile);

photo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }
});

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu3,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Home:
                newactivity();
                return true;
            case R.id.Logout:
                newactivity2();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void newactivity2(){
        Login_database db=new Login_database(this);

        List<Register> reg=db.FetchUsers();

        if(reg.size()!=0){
            for(int i=0;i<reg.size();i++) {
                if (reg.get(i).value == 1) {
                    db.updateDb(new Register(reg.get(i).username,reg.get(i).password,0));



                }
            }
        }


        Intent i = new Intent(ProfileActivity.this, MainActivity.class);

        startActivity(i);

    }
    public void newactivity(){

        Intent i = new Intent(ProfileActivity.this, Main2Activity.class);

        startActivity(i);

    }
}
