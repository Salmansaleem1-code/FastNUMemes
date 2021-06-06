package com.example.fastnumemes;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    ListView myListView;
    String[] Name={"Meme1","Meme2","Meme3","Meme4","Meme5","Meme6","Meme7"};
    ArrayList<Images> images;
    int imageid[]={R.drawable.download1,R.drawable.download2,R.drawable.download3,R.drawable.download4,R.drawable.download5,R.drawable.download6,R.drawable.download7};
    private static final String TAG = "MainActivity";

    private AdView mAdView;
    ImageDatabase db;
    int btnid[]={R.drawable.likeem,R.drawable.likeem};
    Images image;
    ImageAdapter imagesAdap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        myListView = (ListView) findViewById(R.id.listView);
        images=new ArrayList<>();
        db=new ImageDatabase(this);
        ArrayList<Images> reg;
        reg= (ArrayList<Images>) db.FetchImg();


        if(reg.size()!=0){
            System.err.println("arey");
          images=reg;

        }
       else {

            for (int i = 0; i < Name.length; i++) {
                image = new Images(imageid[i], Name[i], 0, 0);

                images.add(image);
                db.InsertImage(image);
            }
        }



        imagesAdap=new ImageAdapter(this,images);
     //   myListView.setDividerHeight(10);
        myListView.setAdapter(imagesAdap);

       if(savedInstanceState!=null){
            images=(ArrayList<Images>) savedInstanceState.getSerializable("image");
            imagesAdap= new ImageAdapter(this, images);
            myListView.setAdapter(imagesAdap);

        }}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu2,menu);
        return true;
    }
    @Override
    public void onSaveInstanceState(Bundle mybundle){
        super.onSaveInstanceState(mybundle);
        for(int i=0;i<Name.length;i++)
            mybundle.putSerializable("image",images);
    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item2:
                newactivity();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    void newactivity(){
        Intent i = new Intent(Main2Activity.this, ProfileActivity.class);

        startActivity(i);
    }


    }
