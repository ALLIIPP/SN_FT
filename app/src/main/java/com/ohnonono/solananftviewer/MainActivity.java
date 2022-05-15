package com.ohnonono.solananftviewer;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.ohnonono.solananftviewer.data.network.returntypes.SNFTHomepage;
import com.ohnonono.solananftviewer.home.HomeFragment;
import com.ohnonono.solananftviewer.home.HomeViewModel;
import com.ohnonono.solananftviewer.misc.MiscFragment;
import com.ohnonono.solananftviewer.search.SearchFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String HOME_TAG = "nav_home";
    public static final String SEARCH_TAG = "nav_search";
    public static final String MISC_TAG = "nav_misc";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        BottomNavigationView bottomNavigationView = findViewById(R.id.activity_main_bnv_navbar);
        bottomNavigationView.setOnItemSelectedListener(navListener);

        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.translucent));

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    //    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
       /* Resources.Theme theme = super.getTheme();
        theme.applyStyle(R.style.Theme_Solananftviewer,true); */

        Intent intent = getIntent();

        if (intent.getParcelableExtra("homepage") != null) {
            HomeViewModel viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
            SNFTHomepage ehas = (SNFTHomepage) intent.getParcelableExtra("homepage");
            viewModel.setHomepage(ehas);
       }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activity_main_fl_frame, new HomeFragment(), HOME_TAG);
        ft.addToBackStack(HOME_TAG);
        ft.commit();

    }


    //TODO mess around w/ search adapter glide to make it faster

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size() - 1).getTag() == null || getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getFragments().size() - 1).getTag().equals("SEE_ALL")) {

            super.onBackPressed();
            //getSupportFragmentManager().popBackStack();

        } else if (getSupportFragmentManager().findFragmentByTag(SEARCH_TAG) != null && getSupportFragmentManager().findFragmentByTag(SEARCH_TAG).isVisible() || getSupportFragmentManager().findFragmentByTag(MISC_TAG) != null && getSupportFragmentManager().findFragmentByTag(MISC_TAG).isVisible()) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();

            for (Fragment frag : manager.getFragments()) {
                ft.hide(frag);

            }
            Log.i("JJJJJJJJJJJJJJJJJJJJJJJJ", "ASSASASASAs");

            if (manager.findFragmentByTag(HOME_TAG) == null) {
                ft.add(R.id.activity_main_fl_frame, new HomeFragment(), HOME_TAG);
                ft.addToBackStack(HOME_TAG);
                ft.commit();
            } else {

                ft.show(manager.findFragmentByTag(HOME_TAG));
                ft.commit();
            }

        } else if (getSupportFragmentManager().findFragmentByTag(HOME_TAG) != null && !getSupportFragmentManager().findFragmentByTag(HOME_TAG).isHidden()) {
            Log.i("HEEEEEEEEEEEEEEEEEEEEEEEEEEEEERRRRRRRRRRRRRREEEEEEE", " in finish !!");
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private final NavigationBarView.OnItemSelectedListener navListener =
            item -> {

                String fragment_tag;
                switch (item.getItemId()) {
                    case (R.id.nav_home):
                        fragment_tag = HOME_TAG;
                        break;
                    case (R.id.nav_search):
                        fragment_tag = SEARCH_TAG;
                        break;
                    case (R.id.nav_misc):
                        fragment_tag = MISC_TAG;
                        break;
                    default:
                        fragment_tag = HOME_TAG;
                        break;
                }

                //clear fragments


                Log.i("FRAGMENT TAG : ", "  : " + fragment_tag);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();

           //     manager.backst
           /*       while(manager.getBackStackEntryCount()>0){

                 if(manager.getBackStackEntryAt(manager.getBackStackEntryCount()-1).getName() == null){
                        manager.popBackStackImmediate();
                    }else if(manager.getBackStackEntryAt(manager.getBackStackEntryCount()-1).getName().equals(HOME_TAG) ||
                            manager.getBackStackEntryAt(manager.getBackStackEntryCount()-1).getName().equals(SEARCH_TAG) ||
                            manager.getBackStackEntryAt(manager.getBackStackEntryCount()-1).getName().equals(MISC_TAG)){
                        break;
                    }




                } */

               if(manager.findFragmentByTag("SEE_ALL") != null){
                   manager.popBackStackImmediate("SEE_ALL",FragmentManager.POP_BACK_STACK_INCLUSIVE);
               }



                if (manager.findFragmentByTag(fragment_tag) == null) {

                    Log.i("HEEEEEEEEEEEEEEEEEEEEEEEEEEEEERRRRRRRRRRRRRREEEEEEE", " in create new ");
                    Fragment selectedFragment;

                    if (item.getItemId() == R.id.nav_home) {
                        selectedFragment = new HomeFragment();
                    } else if (item.getItemId() == R.id.nav_search) {
                        selectedFragment = new SearchFragment();
                    } else {
                        selectedFragment = new MiscFragment();
                    }


                    ft.add(R.id.activity_main_fl_frame, selectedFragment, fragment_tag);
                    ft.addToBackStack(fragment_tag);
                    ft.commit();

                } else {
                    //TODO bring to frint

                   for (Fragment frag : manager.getFragments()) {

                       ft.hide(frag);

                    }


                    Log.i("HEEEEEEEEEEEEEEEEEEEEEEEEEEEEERRRRRRRRRRRRRREEEEEEE", " in backstack !!");

                    //    getSupportFragmentManager().popBackStackImmediate();
                    ft.show(manager.findFragmentByTag(fragment_tag));
                    //           if(getSupportFragmentManager().getBackStackEntryCount()<=4) {
                    //   getSupportFragmentManager().popBackStackImmediate();
                    //     ft.addToBackStack(fragment_tag);
                    ft.commit();


                }


                Log.i("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", "  : " + getSupportFragmentManager().getBackStackEntryCount());

                return true;

            };
}