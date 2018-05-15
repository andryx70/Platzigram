package com.example.andry.platzigram.post.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andry.platzigram.R;
import com.example.andry.platzigram.adapter.PictureAdapterRecyclerView;
import com.example.andry.platzigram.model.Picture;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final int REQUEST_CAMERA = 1;
    private FloatingActionButton fabCamera;
    private String photoPathTemp = "";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        showToolbar(getResources().getString(R.string.tab_home), false, view);
        RecyclerView pictureRecycler = (RecyclerView) view.findViewById(R.id.pictureRecycler);

        fabCamera = (FloatingActionButton) view.findViewById(R.id.fab);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pictureRecycler.setLayoutManager(linearLayoutManager);
        PictureAdapterRecyclerView pictureAdapterRecyclerView = new PictureAdapterRecyclerView(buidPicture(), R.layout.cardview_picture, getActivity());
        pictureRecycler.setAdapter(pictureAdapterRecyclerView);

        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        return view;
    }

    private void takePicture() {
        Intent intentTakePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentTakePicture.resolveActivity(getActivity().getPackageManager()) != null) {

            File phothoFile = null;

            try {
                phothoFile = createImageFile();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (phothoFile != null){
                Uri photoUri = FileProvider.getUriForFile(getActivity(), "com.example.andry.platzigram", phothoFile);
                intentTakePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intentTakePicture, REQUEST_CAMERA);
            }


        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date());
        String imageFileName = "JPEG" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = File.createTempFile(imageFileName, "jpg", storageDir);

        photoPathTemp = "FILE: " + photo.getAbsolutePath();

        return photo;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA && requestCode == getActivity().RESULT_OK){
            Log.d("HomeFragment", "CAMERA OK!!");
            Intent i = new Intent(getActivity(),NewPostActivity.class);
            i.putExtra("PHOTO_PATH_TEMP", photoPathTemp);
            startActivity(i);
        }
    }

    public ArrayList<Picture> buidPicture(){
        ArrayList<Picture> pictures = new ArrayList<>();
        pictures.add(new Picture("http://www.novalandtours.com/images/guide/guilin.jpg", "Andry Valdez", "4 dias", "10 Me Gusta"));
        pictures.add(new Picture("http://www.enjoyart.com/library/landscapes/tuscanlandscapes/large/Tuscan-Bridge--by-Art-Fronckowiak-.jpg", "Genesis Gomez", "5 dias", "3 Me Gusta"));
        pictures.add(new Picture("http://www.educationquizzes.com/library/KS3-Geography/river-1-1.jpg", "Oscar Garcia", "6 dias", "6 Me Gusta"));
        return pictures;
    }

    public void showToolbar(String tittle, boolean upButton, View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

}
