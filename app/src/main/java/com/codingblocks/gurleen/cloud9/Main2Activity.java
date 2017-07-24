package com.codingblocks.gurleen.cloud9;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import com.firebase.ui.auth.AuthUI;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main2Activity extends AppCompatActivity {

    private StorageReference mStorageRef;
    Button chooserB;
    Button uploadB;
    private String name;
    private String email;
    private Uri photoUrl;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private  StorageReference riversRef;
    private File localFile;
    private static final int PICK_IMAGE_REQUEST = 56;
    private  Uri file;
    private  int count;
    private   Uri downloadUrl1;
    ImageView imageView;
    private Bitmap bitmap;
    TextView downloadU;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().show();*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);


        uploadB=(Button)findViewById(R.id.UploadButton);
        chooserB =(Button)findViewById(R.id.ChooseButton);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        final FrameLayout frameLayout = (FrameLayout)findViewById(R.id.frame_layout);
        frameLayout.getBackground().setAlpha(0);
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.fab_menu);
        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                frameLayout.getBackground().setAlpha(240);
                frameLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        fabMenu.collapse();
                        return true;
                    }
                });
            }

            @Override
            public void onMenuCollapsed() {
                frameLayout.getBackground().setAlpha(0);
                frameLayout.setOnTouchListener(null);
            }
        });


        mAuth  = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()==null) {

            Intent signInIntent = AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false).setAvailableProviders(
                    Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),new  AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build())
            ).setIsSmartLockEnabled(false).build();

            startActivityForResult(signInIntent, 111);

        }
        else
        {
            user = mAuth.getCurrentUser();
            name = user.getDisplayName();
            email = user.getEmail();
            photoUrl = user.getPhotoUrl();


        }




        mAuthListener = new FirebaseAuth.AuthStateListener(){


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if(user!=null)
                {
                    Log.e("TAG","User still logged in");
                }
                else
                {
                    Log.e("TAG","Signed Out");


                }
            }
        };




        chooserB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ImageChooser();


            }    });
        uploadB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uploader();

            }
        });

        /*downloadB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




try{
    localFile = File.createTempFile("images", "jpg"); }
catch (IOException ex)
{
    ex.printStackTrace();
}
                .getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                // ...


                                Toast.makeText(getBaseContext(),"File downloaded !",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle failed download
                        // ...
                    }
                });

            }
        });



    }
*/










    }



    public void Uploader()
    {


          /*  final ProgressDialog progressDialog = new ProgressDialog(getBaseContext());
            progressDialog.show();*/
        riversRef
                = mStorageRef.child("images/ something1.jpeg");
        count++;

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        @SuppressWarnings("VisibleForTests")  Uri      downloadUrl = taskSnapshot.getDownloadUrl();
                        Log.e("CONVERSION", downloadUrl + " after conversion" + downloadUrl.toString());




                        //  progressDialog.dismiss();
                        Toast.makeText(getBaseContext(),downloadUrl.toString()+"",Toast.LENGTH_SHORT);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });

    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    protected void onStop() {
        super.onStop();

        if(mAuthListener!=null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

    private void ImageChooser()
    {
        Intent imageChooser = new Intent();
        imageChooser.setType("image/*");
        //imageChooser.setType("*/*");
        // String[] mimetypes = {"image/*", "video/*","audio/*"};
        //imageChooser.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        // startActivityForResult(imageChooser, PICK_IMAGE_REQUEST);


        imageChooser.setAction(Intent.ACTION_GET_CONTENT); //Image chooser lets you set title for the dialog that shows options to display content//
        startActivityForResult(Intent.createChooser(imageChooser,"Select files to Upload"),PICK_IMAGE_REQUEST);
//PICK_IMAGE_REQUEST - this tells the system what return-code to use when the invoked Activity completes, so that we can respond correctly.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data != null )
        {
            file = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);

        }


        if(requestCode==111 && resultCode==RESULT_OK && data!=null)
        {

            user = mAuth.getCurrentUser();
            Log.e("TAG","Got current user on activity result " + user);

            name = user.getDisplayName();
            email = user.getEmail();
            photoUrl = user.getPhotoUrl();

            UserData userData = new UserData(name,email,photoUrl);
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference =firebaseDatabase.getReference();
            databaseReference.child("users").child(user.getUid()).setValue(userData);


        }


    }
}
