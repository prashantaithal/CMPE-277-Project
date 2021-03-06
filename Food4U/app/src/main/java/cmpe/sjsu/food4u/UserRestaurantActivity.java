package cmpe.sjsu.food4u;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class UserRestaurantActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button dessert;
    Button maincourse;
    Button appetizer;
    Button drink;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("orders");
    String message;

    private DatabaseReference dBMenu = database.getReference("MenuItems");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_restaurant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.addDrawerListener(drawerListener);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //custom logic start
        setupMenuCategoryUI();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_restaurant, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.cart) {
            moveTocart();
            }
        else if (id == R.id.orders) {
            Intent orderIntent=new Intent(UserRestaurantActivity.this,OrderStatus.class);
            startActivity(orderIntent);
        }
        else if (id == R.id.logout) {
            logout();
        } else if (id == R.id.orders) {

        }
        else if (id == R.id.orders) {
            moveToOrdersAcivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void moveToOrdersAcivity(){
        Intent intent = new Intent(getApplicationContext(),OrderActivity.class);
        startActivity(intent);
    }
    public void setUsername(){
        TextView usernameTv = findViewById(R.id.username);
        usernameTv.setText(LoginContext.currentUser.getDisplayName());
    }

    public void logout() {
        AuthUI.getInstance()
                .signOut(getApplicationContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        LoginContext.currentUser = null;
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                });
    }

    public void moveTocart(){
        startActivity(new Intent(getApplicationContext(), CartActivity.class));
    }
    public void moveToOrders() {

        Intent intent = new Intent(getApplicationContext(), CartActivity.class);
        startActivity(intent);
    }

    public void setupMenuCategoryUI() {
        dessert = (Button) findViewById(R.id.dessert);
        dessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserMenuItemActivity.class);
                intent.putExtra("filter", "dessert");
                startActivity(intent);
            }
        });

        maincourse = (Button) findViewById(R.id.maincourse);
        maincourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserMenuItemActivity.class);
                intent.putExtra("filter", "maincourse");
                startActivity(intent);
            }
        });
        appetizer = (Button) findViewById(R.id.appetizer);
        appetizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserMenuItemActivity.class);
                intent.putExtra("filter", "appetizer");
                startActivity(intent);
            }
        });
        drink = (Button) findViewById(R.id.drink);
        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserMenuItemActivity.class);
                intent.putExtra("filter", "drink");
                startActivity(intent);
            }
        });

    }

    DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
            setUsername();

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };
    @Override
    public void onStart() {
        super.onStart();

        Query lastQuery = ref.orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  String message = dataSnapshot.child("cartItemsList").child("0").child("item").child("name").getValue().toString();
                //   Iterable t = dataSnapshot.getChildren();
                for (DataSnapshot alert : dataSnapshot.getChildren()) {
                    message = (String) alert.child("cartItemsList").child("0").child("item").child("name").getValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Handle possible errors.
            }
        });
    }

}
