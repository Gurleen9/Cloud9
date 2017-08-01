package com.codingblocks.gurleen.cloud9.Activities;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.codingblocks.gurleen.cloud9.Adapters.Adapter;
import com.codingblocks.gurleen.cloud9.Adapters.Adapter2;
import com.codingblocks.gurleen.cloud9.DataModels.ImageFolderData;
import com.codingblocks.gurleen.cloud9.DataModels.MainRecyclerViewData;
import com.codingblocks.gurleen.cloud9.DataModels.UserData;
import com.codingblocks.gurleen.cloud9.MainFragement;
import com.codingblocks.gurleen.cloud9.R;
import com.codingblocks.gurleen.cloud9.Upload;
import com.getbase.floatingactionbutton.AddFloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import com.firebase.ui.auth.AuthUI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Main2Activity extends AppCompatActivity {
    ArrayList<ImageFolderData> arrayList1 = new ArrayList<>();
    ArrayList<MainRecyclerViewData> arrayList = new ArrayList<>();
    private StorageReference cStorageRef;
    private StorageReference mStorageRef;
    AddFloatingActionButton chooserPhotos;
    AddFloatingActionButton chooserCamera;
    RecyclerView rvImageFrag ;
    AddFloatingActionButton chooserDisk;
    private String folderName;
    private String name;
    private String email;
    private Uri photoUrl;
    private UserData userData;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private ArrayList<Upload> UploadArrayList;

    private File localFile;
    private static final int PICK_IMAGE_REQUEST = 56;
    private static final int PICKFILE_REQUEST_CODE = 32;
    private static final int CAMERA_REQUEST = 99;


    private Uri downloadUrl1;

    private Bitmap bitmap;
    TextView downloadU;
    FirebaseUser user;
    RecyclerView rv;
    Adapter adapter;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        Bitmap restwo = BitmapFactory.decodeResource(getResources(), R.drawable.restwo);
        Bitmap resone = BitmapFactory.decodeResource(getResources(), R.drawable.resone);
        Bitmap resthree = BitmapFactory.decodeResource(getResources(), R.drawable.resthree);
        Bitmap resfour = BitmapFactory.decodeResource(getResources(), R.drawable.resfour);


        rv = (RecyclerView) findViewById(R.id.rv);


        chooserPhotos = (AddFloatingActionButton) findViewById(R.id.ChooseButton);
        chooserPhotos.setImageResource(R.drawable.uploadicon);


        chooserCamera = (AddFloatingActionButton) findViewById(R.id.ChooseButton1);
        chooserCamera.setImageResource(R.drawable.uploadicon);
        chooserDisk = (AddFloatingActionButton) findViewById(R.id.ChooseButton2);
        chooserDisk.setImageResource(R.drawable.uploadicon);
        UploadArrayList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        cStorageRef = FirebaseStorage.getInstance().getReference();
        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
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

        chooserPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("MainActivity", "Chooser Photos Got clciked");
                ImageChooser();


            }
        });
        chooserDisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MainActivity", "Chooser Disk Got clicked");
                DiskChooser();
            }
        });

        chooserCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MainActivity", "From camera got opened");
                OpenCamera();
            }
        });


        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {

            Intent signInIntent = AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false).setAvailableProviders(
                    Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(), new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build())
            ).setIsSmartLockEnabled(false).build();

            startActivityForResult(signInIntent, 111);

        } else {
            user = mAuth.getCurrentUser();
            name = user.getDisplayName();
            email = user.getEmail();
            photoUrl = user.getPhotoUrl();


        }
UploadArrayList.clear();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot  todoSnap = dataSnapshot.child("users").child(user.getUid()).child("image");
                Iterable<DataSnapshot> snapshotIterable = todoSnap.getChildren();


                for(DataSnapshot ds:snapshotIterable){

                   Upload upload  = ds.getValue(Upload.class);
                    Log.e("Photo", " "+ upload.getName() + " "+ upload.getUrl());

                           UploadArrayList.add(upload);


                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


            mAuthListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.e("TAG", "User still logged in");
                } else {
                    Log.e("TAG", "Signed Out");


                }
            }
        };


        arrayList.add(new MainRecyclerViewData(restwo, "Photos", "21"));
        arrayList.add(new MainRecyclerViewData(resone, "Videos", "21"));
        arrayList.add(new MainRecyclerViewData(resfour, "Files", "18"));
        arrayList.add(new MainRecyclerViewData(resthree, "Offline", "56"));


        FragmentManager fm = getSupportFragmentManager();

        MainFragement mainFragement = new MainFragement();
        mainFragement = mainFragement.newInstance();
        fm.beginTransaction().add(R.id.mainFrameLayout, mainFragement).commit();



    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    protected void onStop() {
        super.onStop();

        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }


    private void DiskChooser() {
        Intent DiskChooser = new Intent(Intent.ACTION_GET_CONTENT);
        DiskChooser.setType("file/*");
        DiskChooser.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(DiskChooser, "Select files to backup"), PICKFILE_REQUEST_CODE);


    }

    private void ImageChooser() {
        Intent imageChooser = new Intent();

        imageChooser.setType("image/* video/*");

        //For choosing both images and/or videos
        imageChooser.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); //This should allow multiple selection
        imageChooser.putExtra(Intent.EXTRA_LOCAL_ONLY, true);


        imageChooser.setAction(Intent.ACTION_GET_CONTENT); //Image chooser lets you set title for the dialog that shows options to display content//
        startActivityForResult(Intent.createChooser(imageChooser, "Select files to Upload"), PICK_IMAGE_REQUEST);
//PICK_IMAGE_REQUEST - this tells the system what return-code to use when the invoked Activity completes, so that we can respond correctly.
    }


    private void OpenCamera() {

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 111 && resultCode == RESULT_OK && data != null) {

            user = mAuth.getCurrentUser();
            Log.e("TAG", "Got current user on activity result " + user);

            name = user.getDisplayName();
            email = user.getEmail();
            photoUrl = user.getPhotoUrl();
            Log.e("Retrieved Data", name + email + photoUrl);
            userData = new UserData(name, email, photoUrl);
            // firebaseDatabase = FirebaseDatabase.getInstance();
            // databaseReference =firebaseDatabase.getReference();
            // databaseReference.child("users").child(user.getUid()).setValue(userData);


        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {

            ClipData clip = data.getClipData();
            MyAsyncTask asyncTask = new MyAsyncTask();
            asyncTask.execute(clip);


        }


    }


    class MyAsyncTask extends AsyncTask<ClipData, Integer, String> {


        @Override
        protected String doInBackground(ClipData... params) {


            ClipData clip = params[0];
            for (int i = 0; i < clip.getItemCount(); i++) {
                ClipData.Item item = clip.getItemAt(i);

                Uri uri = item.getUri();
                String path = uri.getPath();
                File f = new File("" + uri);
                final String fileName = f.getName();

                String mimeType = getFileExtension(uri);
                Log.e("FUNCTION TEST", "Mimetype" + mimeType);

                Log.e("OnActivityResult", "Uri of selected images" + uri.toString());

                Log.e("OnActivityResult", "Path of selected image is" + path);
                Log.e("OnActivityResult", "Name  of selected image is" + fileName);
                Log.e("OnActivityResult", "Mime type  of selected image is" + mimeType);

                if (mimeType.contains("jpg") || mimeType.contains("png")) {
                    folderName = "image";
                }


                mStorageRef = cStorageRef.child("users").child(user.getUid()).child(folderName).child(fileName);


                mStorageRef.putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
                                // com.codingblocks.gurleen.cloud9.ImagesUri imagesUriObject = new ImagesUri(taskSnapshot.getDownloadUrl());
                                // Log.e("CONVERSION", imagesUriObject + " after conversion" + imagesUriObject.getImageUri().toString());


                                //  ImagesuriArrayList.add(imagesUriObject);
                                // Log.e("Tag", " " + ImagesuriArrayList.get(0).getImageUri().toString());
                                // DatabaseReference dbrefNew = databaseReference.child("users").child(user.getUid()).child(folderName);
                                // MyAsyncTask2 asyncTask2 = new MyAsyncTask2(dbrefNew);
                                // asyncTask2.execute(ImagesuriArrayList);
                                Upload upload = new Upload(fileName, taskSnapshot.getDownloadUrl().toString());
                                UploadArrayList.add(upload);

                                DatabaseReference dbrefNew = databaseReference.child("users").child(user.getUid()).child(folderName);
                                dbrefNew.setValue(UploadArrayList);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                // ...
                            }
                        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {


                            //Do something here

                        }
                    }
                });


            }
            return " ";
        }


        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);


        }


    }


    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void MainFragementMethod(View v)
    {

        RecyclerView rv = (RecyclerView)v.findViewById(R.id.rv);
        Adapter2 adapter = new Adapter2(arrayList,UploadArrayList,Main2Activity.this,this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);




    }
    public void FragementImagesMethod(View v)
    {

        rvImageFrag = (RecyclerView)v.findViewById(R.id.rvimagefolder);
      adapter = new Adapter(UploadArrayList,Main2Activity.this,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvImageFrag.setLayoutManager(layoutManager);
        rvImageFrag.setAdapter(adapter);



    }
    public void UpdateRecyclerView()

    {

        adapter.notifyDataSetChanged();



    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }
}