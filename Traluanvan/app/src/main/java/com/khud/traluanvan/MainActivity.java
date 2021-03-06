package com.khud.traluanvan;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    FragmentManager fragmentManager;
    Fragment selectedFragment = null;
    String tag = "Search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.bottom_navigation);
        //Copy database from asset folder to data/database/...
        Traluanvandb data=new Traluanvandb(MainActivity.this);
        //Get luanvan from server
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container,new SearchFragment(),"Search").commit();
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_search:
                        if (fragmentManager.findFragmentByTag("Info") == null){
                                selectedFragment = new SearchFragment();
                                tag = "Search";
                                Loadfragment(selectedFragment, tag);
                        }
                         else {
                                tag = "Last";
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_container, fragmentManager
                                                .findFragmentByTag("Info"))
                                        .addToBackStack(tag)
                                        .commit();
                            }
                        return true;
                    case R.id.nav_login:
                        if (fragmentManager.findFragmentByTag("Info_User") == null){
                            selectedFragment = new LoginFragment();
                            tag = "Login";
                            Loadfragment(selectedFragment, tag);
                        }
                        else {
                            tag = "Last";
                            fragmentManager.beginTransaction()
                                    .replace(R.id.frame_container, fragmentManager
                                            .findFragmentByTag("Info_User"))
                                    .addToBackStack(tag)
                                    .commit();
                        }
                        return true;
                    case R.id.nav_menu:
                        selectedFragment = new MenuFragment();
                        tag = "Menu";
                        Loadfragment(selectedFragment, tag);
                        return true;
                }
                return false;
            }
        });
    }
    // load fragment
    public void Loadfragment(Fragment fragment, String name) {
        final int count = fragmentManager.getBackStackEntryCount();
        tag = name;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment backstack_fragment = fragmentManager.findFragmentByTag(name);
        //Check fragment tag name
//        Toast.makeText(MainActivity.this,currentfragment.getClass().getName(),Toast.LENGTH_SHORT).show();
        //Check fragment in backstack
        if (backstack_fragment != null) {
            try {
                transaction.replace(R.id.frame_container, backstack_fragment);
                tag = "Last";
                transaction.addToBackStack(tag);
                transaction.commit();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        //Create new fragment
        else {
            transaction.replace(R.id.frame_container, fragment, name);
            transaction.setReorderingAllowed(true);
            transaction.addToBackStack(name);
            fragmentManager.executePendingTransactions();
            transaction.commit();

        }
    }
    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0 && Updatenavigation(fragmentManager.findFragmentById(R.id.frame_container)) != 0) {
            if (fragmentManager.findFragmentByTag("Info") != null) {
                fragmentManager.executePendingTransactions();
                fragmentManager.popBackStack("Info", 1);
            }
            navigationView.getMenu().getItem(0).setChecked(true);
            //Ger fragment home in backstack(wait to fix bug backstack)
            Fragment backstack_fragment = fragmentManager.findFragmentByTag("Search");
            //Check fragment in backstack
            if (backstack_fragment != null) {
                try {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frame_container, backstack_fragment);
                    tag = "Last";
                    transaction.addToBackStack(tag);
                    transaction.commit();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            finish();
        }
    }
    private int Updatenavigation(Fragment fragment) {
        //Set item=randomnum
        int item = 999;
        if (fragment != null) {
            String fragClassName = fragment.getClass().getName();
            //Check class name fragment
//        Toast.makeText(MainActivity.this,fragClassName,Toast.LENGTH_SHORT).show();
            if (fragClassName.equals(SearchFragment.class.getName())) {
                item = 0;
                //set selected item position, etc
            } else if (fragClassName.equals(DateFragment.class.getName())) {
                item = 1;
                //set selected item position, etc
            } else if (fragClassName.equals(MenuFragment.class.getName())) {
                item = 2;
                //set selected item position, etc
            }
        }
        return item;
    }


}
