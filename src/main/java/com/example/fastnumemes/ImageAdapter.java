package com.example.fastnumemes;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    int size;
    Context c;
    ArrayList<Images> Image;
    ArrayList<Images> tempImage;
ImageDatabase db;
    Bitmap bitmap ;
static int tLikes=0;
    String mSavedInfo;
    public ImageAdapter(Context c, ArrayList<Images> Image) {
        this.c = c;
        this.Image=Image;
        this.tempImage = Image;
    }

    @Override
    public int getCount() {
        return Image.size();
    }

    @Override
    public Object getItem(int position) {
        return Image.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater lf = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View singlerow = lf.inflate(R.layout.mylayout, null);
        final ImageView image = singlerow.findViewById(R.id.ImageView);

        image.setBackgroundResource(Image.get(position).imageid);
        final TextView text = singlerow.findViewById(R.id.textView);
        final ImageButton like = singlerow.findViewById(R.id.Like);
db=new ImageDatabase(c);
        final  ImageButton share=singlerow.findViewById(R.id.share);
share.setBackgroundResource(R.drawable.share);
        final RelativeLayout layout=singlerow.findViewById(R.id.Layout);


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==0) {
                    image.setImageResource(R.drawable.download1);

                }
                else if(position==1) {
                    image.setImageResource(R.drawable.download2);
                }
                else if(position==2) {
                    image.setImageResource(R.drawable.download3);
                }
                else if(position==3) {
                    image.setImageResource(R.drawable.download4);
            }
                else if(position==4) {
                    image.setImageResource(R.drawable.download5);
                }
                else if(position==5) {
                    image.setImageResource(R.drawable.download6);
                }
                else{
                    image.setImageResource(R.drawable.download7);
                }
                Drawable drawable=image.getDrawable();
                Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();

                try {
                    File file=null;
                    if(position==0) {
                       file = new File(c.getExternalCacheDir(), File.separator + "download1.jpg");
                    }
                    else if(position==1) {
                        file = new File(c.getExternalCacheDir(), File.separator + "download2.jpg");
                    }
                    else if(position==2) {
                        file = new File(c.getExternalCacheDir(), File.separator + "download3.jpg");
                    }
                    else if(position==3) {
                        file = new File(c.getExternalCacheDir(), File.separator + "download4.jpg");
                    }
                    else if(position==4) {
                        file = new File(c.getExternalCacheDir(), File.separator + "download5.jpg");
                    }
                    else if(position==5) {
                        file = new File(c.getExternalCacheDir(), File.separator + "download6.jpg");
                    }
                    else{
                        file = new File(c.getExternalCacheDir(), File.separator + "download7.jpg");
                    }
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true, false);
                    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri photoURI = FileProvider.getUriForFile(c, BuildConfig.APPLICATION_ID +".provider", file);

                    intent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setType("image/jpg");

                   c.startActivity(Intent.createChooser(intent, "Share image via"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        final ImageButton unlike=singlerow.findViewById(R.id.unlike);
        if(Image.get(position).btnid2==0){
            unlike.setBackgroundResource(R.drawable.unlikeem);

        }
        else{
            Image.get(position).btnid2=1;
            unlike.setBackgroundResource(R.drawable.unlikefill);

        }
        if (Image.get(position).btnid == 0) {
            like.setBackgroundResource(R.drawable.likeem);

        } else {
            Image.get(position).btnid=1;
            like.setBackgroundResource(R.drawable.likefill);
        }



     like.setOnLongClickListener(new View.OnLongClickListener() {
         @Override
         public boolean onLongClick(View v) {
             PopupMenu popupMenu=new PopupMenu(c,v);
             popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                 @Override
                 public boolean onMenuItemClick(MenuItem item) {

                     if(item.getItemId()==R.id.item1){
                         like.setBackgroundResource(R.drawable.likefill);
                     }
                     else{
                         like.setBackgroundResource(R.drawable.hahah);
                     }
                     return true;
                 }
             });
             popupMenu.inflate(R.menu.reactx);
             popupMenu.show();


             return true;
         }
     });

        unlike.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu=new PopupMenu(c,v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getItemId()==R.id.item1){
                            unlike.setBackgroundResource(R.drawable.unlikefill);
                        }
                        else{
                            unlike.setBackgroundResource(R.drawable.angry);
                        }
                        Image.get(position).btnid=1;
                        Image.get(position).btnid2=1;
                        return true;
                    }
                });
                popupMenu.inflate(R.menu.reactx2);
                popupMenu.show();


                return true;
            }
        });
        image.setOnLongClickListener(new View.OnLongClickListener(){
                     @Override
                    public boolean onLongClick(View view){
                         PopupMenu popupMenu=new PopupMenu(c,view);
                         popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                             @Override
                             public boolean onMenuItemClick(MenuItem item) {
                                 if(position==0) {
                                     getfile("https://scontent.fymy1-1.fna.fbcdn.net/v/t1.0-9/s720x720/92330651_2875288995884625_8946914690047410176_n.jpg?_nc_cat=109&_nc_sid=110474&_nc_ohc=uI2mF2QcISgAX8_jRaz&_nc_ht=scontent.fymy1-1.fna&_nc_tp=7&oh=7fb2c73061e1161eb8feb66ea5fb3c7d&oe=5EF6A92F");

                                 }
                                 else if(position==1){
                                     getfile("https://scontent.flhe5-1.fna.fbcdn.net/v/t1.0-9/49708603_2031841406896059_7224892061462298624_n.jpg?_nc_cat=107&_nc_sid=8bfeb9&_nc_eui2=AeFcagQq4NlhtCzTIgTySo8HkdNU6aMdkwSR01Tpox2TBM9szw6ztVqA0OjyWBsAzRz9D1dKmxN0CEUzL0xUtsxw&_nc_ohc=jRdq_z1Ho1sAX8DgnlH&_nc_ht=scontent.flhe5-1.fna&oh=318b0500cdbee6c264206ffda1f1cc68&oe=5EF4EB96");


                                 }
                                 else if(position==2){
                                     getfile("https://scontent.fymy1-1.fna.fbcdn.net/v/t1.0-9/s720x720/92037626_2864072380339620_342940945173446656_n.jpg?_nc_cat=102&_nc_sid=110474&_nc_ohc=eczSBYLMWIcAX8fW0iz&_nc_ht=scontent.fymy1-1.fna&_nc_tp=7&oh=d965596001abffde8fd0d014ad4b6efa&oe=5EF71A73");
                                 }
                                 else if(position==3){
                                     getfile("https://scontent.flhe5-1.fna.fbcdn.net/v/t1.0-9/43950586_1919938454753022_2458306879273566208_n.jpg?_nc_cat=104&_nc_sid=730e14&_nc_eui2=AeGKSjA0k1xqyNcWe5ydlHvdeSV4RRh9gKp5JXhFGH2Aqu9PxcICfaYBgqFwVLrtwJhzLATlNH9Q5sGLxtmePUbv&_nc_ohc=2FVTk3-u31gAX-5RW4e&_nc_ht=scontent.flhe5-1.fna&oh=ca7794deda89a93813df008313b64dab&oe=5EF6DF6E");


                                 }
                                 else if(position==4){
                                     getfile("https://scontent.fymy1-2.fna.fbcdn.net/v/t1.0-9/s720x720/86295389_2765751046838421_6748078149608669184_n.jpg?_nc_cat=105&_nc_sid=110474&_nc_ohc=Yo_Oi65fOaIAX8k7Rka&_nc_ht=scontent.fymy1-2.fna&_nc_tp=7&oh=cbfca94f58f9766bca2a3e3907d59286&oe=5EF6DD67");




                                 }
                                 else if(position==5){
                                     getfile("https://scontent.fymy1-2.fna.fbcdn.net/v/t1.0-9/s720x720/83906484_2744206942326165_4158482340663787520_n.jpg?_nc_cat=110&_nc_sid=110474&_nc_ohc=xXS-YcARotkAX81Asf0&_nc_ht=scontent.fymy1-2.fna&_nc_tp=7&oh=79a0e4766934188e0dc0019295b74d64&oe=5EF49B92");





                                 }
                                 else if(position==6){
                                     getfile("https://scontent.fymy1-1.fna.fbcdn.net/v/t1.0-9/s720x720/84897565_2746266075453585_4175862204999925760_o.jpg?_nc_cat=109&_nc_sid=110474&_nc_ohc=SiTLLHBA2sUAX8n1xa6&_nc_ht=scontent.fymy1-1.fna&_nc_tp=7&oh=69fe22c51dce5d7b1b30b48a2a8259c3&oe=5EF5669F");

                                 }





                                 return true;
                             }

                         });
                         popupMenu.inflate(R.menu.mymenu);
                         popupMenu.show();

                            return true;
            }

        }
        );
        text.setText(Image.get(position).name);
        like.setFocusable(true);
        unlike.setFocusable(true);
        unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Image.get(position).btnid2 == 0) {
                    Image.get(position).btnid2 = 1;

                    unlike.setBackgroundResource(R.drawable.unlikefill);

                    if(Image.get(position).btnid==1){
                        Image.get(position).btnid=0;
                        like.setBackgroundResource(R.drawable.likeem);

                    }


                }

                else {
                    Image.get(position).btnid2 = 0;

                    unlike.setBackgroundResource(R.drawable.unlikeem);

                }

                notifyDataSetChanged();
            }
        });
        like.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              FileOutputStream outputStream = null;
                if (Image.get(position).btnid == 0) {
                    Image.get(position).btnid = 1;
                    like.setBackgroundResource(R.drawable.likefill);

                    if(Image.get(position).btnid2==1){
                        Image.get(position).btnid2=0;
                        unlike.setBackgroundResource(R.drawable.unlikeem);

                    }
                }

                    else {
                    Image.get(position).btnid = 0;
                    like.setBackgroundResource(R.drawable.likeem);
                }

                notifyDataSetChanged();


            }
        });

   for(int i=0;i<Image.size();i++){
        db.updateStudent(Image.get(i));
    }
notifyDataSetChanged();
        return singlerow;
    }

    void getfile(final String urlpath){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                load(urlpath);

            }
        });

        thread.start();
    }
    static int f=0;
    void load(String urlPath){



        String line = "";
        try{

            URL url = new URL(urlPath);
            //URL url = new URL("http://10.0.2.2/android/pictures.xml");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
                    try {
                        InputStream stream =url.openStream();
                        File file=Environment.getExternalStorageDirectory();
                       OutputStream fileOutputStream=new FileOutputStream(new File(file,"img"+(f++)+".png"));
                        try {
                            byte[]buffer=new byte[10000];
                            int bytes=0;
                            while((bytes=stream.read(buffer,0,buffer.length))>=0){
                                fileOutputStream.write(buffer,0,bytes);
                                System.err.println("err");


                            }
                            Toast.makeText(c,"File downloaded to sdcard",Toast.LENGTH_LONG);
                            fileOutputStream.close();

                        }

                        catch (Exception ex){

                        }
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }




        } catch(Exception ex) {
            line = ex.getMessage();
            ex.printStackTrace();
        }

    }













   }





