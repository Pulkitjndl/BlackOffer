package com.assignment.blackoffer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialTextInputPicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.skyhope.materialtagview.TagView;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TrendingFragment trendingFragment;
    TagsFragment tagsFragment;
    ExploreFragment exploreFragment;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNav);

        mAuth= FirebaseAuth.getInstance();
        trendingFragment=new TrendingFragment();
        exploreFragment = new ExploreFragment();
        tagsFragment=new TagsFragment();
        setFragment(tagsFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id==R.id.Tags){
                    setFragment(tagsFragment);
                    return true;
                }
                else if(id==R.id.Trending){
                   setFragment(trendingFragment);
                    return true;
                }
                else if(id==R.id.Explore){
                    setFragment(exploreFragment);
                    return true;
                }
                else if(id==R.id.Saved){
                    Toast.makeText(MainActivity.this, "Saved Fragment is selected", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            updateUI();
        }

    }

    private void updateUI() {
        Intent homeIntent=new Intent(MainActivity.this,SelectLoginType.class);
        startActivity(homeIntent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav, menu);
        return true;
    }
}
