package com.project.food_hubs1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddMenuList extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private Button btnToggleDark;
    EditText item_name,item_price,item_desc;
    ImageView item_img;
    Button btnItem_enter;
    Uri imgUri;

    int SELECT_PICTURE = 200;

    FirebaseStorage storage;
    StorageReference storageReference;
    Bundle extras;


    DatabaseReference reff;
    String username,ubname,usermobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_list);
        extras = getIntent().getExtras();
        username=extras.getString("username");
        ubname=extras.getString("ubname");
        usermobile=extras.getString("usermobile");
        MaterialToolbar toolbar = findViewById(R.id.addmenu_toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
       // setUpToolbar();

        item_name=findViewById(R.id.item_name);
        item_price=findViewById(R.id.item_price);
        item_desc=findViewById(R.id.item_desc);
        item_img=findViewById(R.id.item_img);
        btnItem_enter=findViewById(R.id.btnItem_enter);

        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        Toast.makeText(this, extras.getString("usermobile"), Toast.LENGTH_SHORT).show();

        item_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        btnItem_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });
    }

    public void darkbutton(View view) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

    }

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        actionBarDrawerToggle.syncState();

    }

    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                imgUri = data.getData();
                if (null != imgUri) {
                    // update the preview image in the layout
                    item_img.setImageURI(imgUri);
                }
            }
        }
    }

    public void uploadData(){
        String filename=UUID.randomUUID().toString();
        if (imgUri != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + filename);

            // adding listeners on upload
            // or failure of image
            ref.putFile(imgUri)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            progressDialog.dismiss();
                                            Toast
                                                    .makeText(AddMenuList.this,
                                                            "Image Uploaded!!",
                                                            Toast.LENGTH_SHORT)
                                                    .show();
                                            is_products(uri.toString());
                                        }
                                    });


                                    // Image uploaded successfully
                                    // Dismiss dialog

                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(AddMenuList.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
        else{
            enter("null");
        }
    }

    public void is_products(String imgName){

        reff= FirebaseDatabase.getInstance().getReference().child("menus").child(usermobile).child(item_name.getText().toString());
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Toast.makeText(AddMenuList.this, "Product Already Exists", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),CaterRegistration.class);
                }else{
                    enter(imgName);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void enter(String item_image){
        reff= FirebaseDatabase.getInstance().getReference().child("menus").child(usermobile);
        String name=item_name.getText().toString().trim();
        String price=item_price.getText().toString().trim();
        String desc=item_desc.getText().toString().trim();

        Items items=new Items();


        items.setName(name);
        items.setImg(item_image);
        items.setDesc(desc);
        items.setPrice(price);

        reff.child(name).setValue(items);
        Toast.makeText(AddMenuList.this, "Items Inserted Successfully", Toast.LENGTH_SHORT).show();
        Intent intent10= new Intent(getApplicationContext(),AddMenuList.class);

        intent10.putExtra("usermobile",usermobile);
        intent10.putExtra("username",username);
        intent10.putExtra("userbname",ubname);
        startActivity(intent10);
    }
}