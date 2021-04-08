
package com.example.ltctapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public List<MeterData> meterDataList,totalDataList;
    public static boolean adminAcc=false;
    List<Integer> meterIndices;
    RecyclerView meterListView;
    MeterAdapter meterAdapter;
    ProgressBar loadingBar;
    FloatingActionButton addUserFAB;
    TextView remarkText;
    ActionBarDrawerToggle mToggle;
    DrawerLayout drawerLayout;
    TextView helloText,adminText,accessText;
    DrawerLayout mDrawerLayout;


    public static String division = "Guindy";
    String searchQuery = "";

    User user;
    DatabaseReference ref;

    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
        getSupportActionBar().setTitle(division);
        totalDataList = new ArrayList<MeterData>();
        meterDataList = new ArrayList<MeterData>();
        meterIndices = new ArrayList<Integer>();
        meterListView = findViewById(R.id.MeterRecyclerView);
        loadingBar = findViewById(R.id.loadingBar);
        meterAdapter = new MeterAdapter(meterDataList, meterIndices);
        remarkText = findViewById(R.id.remarkText);
        meterListView.setAdapter(meterAdapter);
        meterListView.setLayoutManager(new LinearLayoutManager(this));


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("/"+division);

        loadingBar.setVisibility(View.VISIBLE);


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                switch (direction){
                    case ItemTouchHelper.LEFT:
                        final MeterAdapter.CustomViewHolder customViewHolder = (MeterAdapter.CustomViewHolder) viewHolder;
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        if (user.adminMode) {
                            builder.setMessage("Are you sure you want to delete " + customViewHolder.meterName.getText() + "(Cannot be recovered)").setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            int offsetIndex = Integer.parseInt(customViewHolder.globalSerialNo) - 1;
                                            for (int i = offsetIndex + 1; i < totalDataList.size(); i++) {
                                                totalDataList.get(i).Sno = i + "";
                                            }

                                            totalDataList.remove(offsetIndex);
                                            for(int i = 0;i<totalDataList.size();i++){
                                                if(totalDataList.get(i).SealNumber==null){
                                                    totalDataList.get(i).SealNumber = "";
                                                }
                                            }

                                            ref.setValue(totalDataList);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            meterAdapter.notifyDataSetChanged();


                                            dialog.cancel();
                                        }
                                    });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }else{
                            printToast("You are not allowed Admin Access");

                            meterAdapter.notifyDataSetChanged();

                        }

                        break;
                    case ItemTouchHelper.RIGHT:
                        meterAdapter.notifyDataSetChanged();

                        break;
                }
            }
        };
        ItemTouchHelper deleteSwipe = new ItemTouchHelper(simpleCallback);
        deleteSwipe.attachToRecyclerView(meterListView);

        addUserFAB = findViewById(R.id.addUserFAB);
        addUserFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean adminMode = user==null?false:user.adminMode;
                if(adminMode) {

                    Intent intent = new Intent(getApplicationContext(), AddMeterActivity.class);
                    intent.putExtra("COUNT", meterDataList.size());
                    intent.putExtra("editmode", true);
                    startActivity(intent);
                }else{
                    printToast("You are not allowed Admin Access");
                }
            }
        });

        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(1).getSubMenu().getItem(0).setEnabled(!division.equalsIgnoreCase( "Porur"));
        navigationView.getMenu().getItem(1).getSubMenu().getItem(0).setChecked(division.equalsIgnoreCase( "Porur"));
        navigationView.getMenu().getItem(1).getSubMenu().getItem(1).setEnabled(!division.equalsIgnoreCase( "Guindy"));
        navigationView.getMenu().getItem(1).getSubMenu().getItem(1).setChecked(division.equalsIgnoreCase( "Guindy"));
        navigationView.getMenu().getItem(1).getSubMenu().getItem(2).setEnabled(!division.equalsIgnoreCase("KK Nagar"));
        navigationView.getMenu().getItem(1).getSubMenu().getItem(2).setChecked(division.equalsIgnoreCase( "KK Nagar"));




        drawerLayout=findViewById(R.id.drawer);
        mToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }catch (Exception e){}
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(mToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToggle.syncState();



        helloText = navigationView.getHeaderView(0).findViewById(R.id.helloText);
        adminText = navigationView.getHeaderView(0).findViewById(R.id.adminText);
        accessText = navigationView.getHeaderView(0).findViewById(R.id.accessText);
        helloText.setText("Hello "+ FirebaseAuth.getInstance().getCurrentUser().getDisplayName()+"!");


        adminText.setText("");
        accessText.setText("");
        DatabaseReference userReference = database.getReference("/Users");

        userReference.child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                user = snapshot.getValue(User.class);
                adminAcc = user.adminMode;


                try {
                    adminText.setText(user.adminMode?"Administrator":"");
                    accessText.setText(user.readAccess?"Allowed Access":"Blocked Access");
                    accessText.setTextColor(user.readAccess?getResources().getColor(R.color.Green):getResources().getColor(R.color.Red));
                    for(int i=0;i<navigationView.getMenu().size();i++){

                        if(navigationView.getMenu().getItem(i).getItemId()==R.id.userList){

                            navigationView.getMenu().getItem(i).setEnabled(user.adminMode );
                            navigationView.getMenu().getItem(i).setCheckable(user.adminMode );

                        }


                    }
                    if(user.readAccess) {
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                meterDataList.clear();
                                totalDataList.clear();
                                loadingBar.setVisibility(View.INVISIBLE);
                                for(DataSnapshot productSnapshot : dataSnapshot.getChildren()){
                                    MeterData product = productSnapshot.getValue(MeterData.class);
                                    meterIndices.add(totalDataList.size());
                                    totalDataList.add(product);

                                }

                                search();
                                meterAdapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }else{
                        loadingBar.setVisibility(View.INVISIBLE);
                        meterDataList.clear();
                        totalDataList.clear();
                        meterAdapter.notifyDataSetChanged();
                        printToast("You aren't allowed access yet, Please contact administrator");
                    }
                }catch(Exception e){
                    printToast(e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                printToast(error.getMessage());
                loadingBar.setVisibility(View.INVISIBLE);
            }
        });


    }
    public void printToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mf = getMenuInflater();
        mf.inflate(R.menu.search_view,menu);

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if(menuItem.getItemId() == R.id.ManageSearch){

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SearchFiltersFrag searchFiltersFrag = new SearchFiltersFrag();
            fragmentTransaction.setCustomAnimations(R.anim.slide_down,R.anim.slide_up);

            fragmentTransaction.replace(R.id.SearchFilterFrag,searchFiltersFrag,"SEARCHFILTERFRAG").commit();
            addUserFAB.setVisibility(View.INVISIBLE);
            getSupportActionBar().hide();


        }
        if(menuItem.getItemId() == R.id.ClearSearch){
            SearchFiltersFrag.nameSearch = "";
            SearchFiltersFrag.AccNoSearch = "";
            SearchFiltersFrag.CTMakeSearch = "";
            SearchFiltersFrag.MeterNoSearch = "";
            SearchFiltersFrag.MeterMakeSearch = "";
            SearchFiltersFrag.SubDivSearch = "";
            SearchFiltersFrag.SectionSearch = "";
            SearchFiltersFrag.CTRatioSearch = "";
            SearchFiltersFrag.ClassSearch = "";
            SearchFiltersFrag.TariffSearch = "";
            SearchFiltersFrag.SealNoSearch = "";
            SearchFiltersFrag.LoadSearch = "";
            SearchFiltersFrag.MFSearch = "";
            SearchFiltersFrag.PoNoSearch = "";
            SearchFiltersFrag.ServiceDateSearch = "";
            search();
        }



        if(menuItem.getItemId()!=R.id.Porur && menuItem.getItemId()!=R.id.Guindy && menuItem.getItemId()!=R.id.KKNagar) {

        }
        else if(menuItem.getItemId()==R.id.Porur || menuItem.getItemId()==R.id.Guindy || menuItem.getItemId()==R.id.KKNagar){
            for (int i = 0; i <  navigationView.getMenu().getItem(1).getSubMenu().size(); i++) {
                navigationView.getMenu().getItem(1).getSubMenu().getItem(i).setChecked(false);
                navigationView.getMenu().getItem(1).getSubMenu().getItem(i).setEnabled(true);
            }
            division = menuItem.getTitle().toString();

            finish();
            startActivity(getIntent());



        }



        if(menuItem.getItemId()!= R.id.userList && menuItem.getItemId()!=R.id.ManageSearch && menuItem.getItemId()!=R.id.ClearSearch) {
            menuItem.setChecked(true);
            menuItem.setEnabled(false);
        }


        if(menuItem.getItemId() == R.id.LogOut){
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(MainActivity.this,LoginActivity.class);

            startActivity(i);
            finish();
            printToast("Logged Out");
        }
        if(menuItem.getItemId() == R.id.userList){

            Intent i = new Intent(MainActivity.this, UserListActivity.class);
            startActivity(i);

        }


        mDrawerLayout.closeDrawers();
        return false;
    }



    public void search() {
        meterDataList.clear();
        meterIndices.clear();
        boolean nameMatch, AccNoMatch, CTMakeMatch, MeterNoMatch, MeterMakeMatch, SubDivMatch, SectionMatch, CTRatioMatch, ClassMatch, TariffMatch, SealNoMatch, LoadMatch, MFMatch, PoNoMatch, ServiceDateMatch;
        nameMatch = AccNoMatch = CTMakeMatch = MeterNoMatch = MeterMakeMatch = SubDivMatch = SectionMatch = CTRatioMatch = ClassMatch = TariffMatch = SealNoMatch = LoadMatch = MFMatch = PoNoMatch = ServiceDateMatch = true;
            for (int i = 0; i < totalDataList.size(); i++) {
             try {

                 nameMatch = totalDataList.get(i).ConsumerName.toLowerCase().trim().contains(SearchFiltersFrag.nameSearch.toLowerCase());
                 AccNoMatch = totalDataList.get(i).AccNo.toLowerCase().trim().contains(SearchFiltersFrag.AccNoSearch.toLowerCase());
                 CTMakeMatch = totalDataList.get(i).CTMake.toLowerCase().trim().contains(SearchFiltersFrag.CTMakeSearch.toLowerCase());
                 MeterNoMatch = totalDataList.get(i).MeterNo.toLowerCase().trim().contains(SearchFiltersFrag.MeterNoSearch.toLowerCase());
                 MeterMakeMatch = totalDataList.get(i).MeterMake.toLowerCase().trim().contains(SearchFiltersFrag.MeterMakeSearch.toLowerCase());
                 SubDivMatch = totalDataList.get(i).Division.toLowerCase().trim().contains(SearchFiltersFrag.SubDivSearch.toLowerCase());
                 SectionMatch = totalDataList.get(i).Section.toLowerCase().trim().contains(SearchFiltersFrag.SectionSearch.toLowerCase());
                 CTRatioMatch = totalDataList.get(i).CTCoilRatio.toLowerCase().trim().contains(SearchFiltersFrag.CTRatioSearch.toLowerCase());
                 ClassMatch = totalDataList.get(i).__Class.toLowerCase().trim().contains(SearchFiltersFrag.ClassSearch.toLowerCase());
                 TariffMatch = totalDataList.get(i).Tariff.toLowerCase().trim().contains(SearchFiltersFrag.TariffSearch.toLowerCase());
                 SealNoMatch = totalDataList.get(i).SealNumber.toLowerCase().trim().contains(SearchFiltersFrag.SealNoSearch.toLowerCase());
                 LoadMatch = totalDataList.get(i).Load.toLowerCase().trim().contains(SearchFiltersFrag.LoadSearch.toLowerCase());
                 MFMatch = totalDataList.get(i).MF.toLowerCase().trim().contains(SearchFiltersFrag.MFSearch.toLowerCase());
                 PoNoMatch = totalDataList.get(i).PoNo.toLowerCase().trim().contains(SearchFiltersFrag.PoNoSearch.toLowerCase());
                 ServiceDateMatch = totalDataList.get(i).ServiceDate.toLowerCase().trim().contains(SearchFiltersFrag.ServiceDateSearch.toLowerCase());
             }catch (NullPointerException n){}
                try {
                    if (nameMatch && AccNoMatch && CTMakeMatch && MeterNoMatch && MeterMakeMatch && SubDivMatch && SectionMatch && CTRatioMatch && ClassMatch && TariffMatch && SealNoMatch && LoadMatch && MFMatch && PoNoMatch && ServiceDateMatch) {
                        meterDataList.add(totalDataList.get(i));
                        meterIndices.add(i);
                    }
                } catch (NullPointerException n) {

                }
            }
            meterAdapter.notifyDataSetChanged();
        getSupportActionBar().setTitle(division+"("+meterAdapter.getItemCount()+"/"+totalDataList.size()+")");

            if (meterAdapter.getItemCount() == 0) {

                remarkText.setText("No Relevant Results To Display");
            } else {
                remarkText.setText("");
            }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){ // use android.R.id
            if(!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.openDrawer(GravityCompat.START);
            }else{
                drawerLayout.closeDrawer(GravityCompat.START);
            }

        }
        if(item.getItemId() == R.id.searchView){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SearchFiltersFrag searchFiltersFrag = new SearchFiltersFrag();
            fragmentTransaction.setCustomAnimations(R.anim.slide_down,R.anim.slide_up);

            fragmentTransaction.replace(R.id.SearchFilterFrag,searchFiltersFrag,"SEARCHFILTERFRAG").commit();
            addUserFAB.setVisibility(View.INVISIBLE);
            getSupportActionBar().hide();

        }
        if(item.getItemId() == R.id.pageStartBut){
            meterListView.scrollToPosition(0);
        }
        if(item.getItemId() == R.id.pageEndBut){
            meterListView.scrollToPosition(meterAdapter.getItemCount()-1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        /*SearchFiltersFrag.nameSearch = "";
        SearchFiltersFrag.AccNoSearch = "";
        SearchFiltersFrag.CTMakeSearch = "";
        SearchFiltersFrag.MeterNoSearch = "";
        SearchFiltersFrag.MeterMakeSearch = "";
        SearchFiltersFrag.SubDivSearch = "";
        SearchFiltersFrag.SectionSearch = "";
        SearchFiltersFrag.CTRatioSearch = "";
        SearchFiltersFrag.ClassSearch = "";
        SearchFiltersFrag.TariffSearch = "";
        SearchFiltersFrag.SealNoSearch = "";
        SearchFiltersFrag.LoadSearch = "";
        SearchFiltersFrag.MFSearch = "";
        SearchFiltersFrag.PoNoSearch = "";
        SearchFiltersFrag.ServiceDateSearch = "";*/
        search();
         closeSearchFrag();
    }
    public void closeSearchFrag(){
        Fragment myFragment = getSupportFragmentManager().findFragmentByTag("SEARCHFILTERFRAG");
        if(myFragment==null) {
            super.onBackPressed();

        }
        else{
            getSupportActionBar().show();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_down,R.anim.slide_up);
            fragmentTransaction.remove(myFragment).commit();
            addUserFAB.setVisibility(View.VISIBLE);




        }
    }
}