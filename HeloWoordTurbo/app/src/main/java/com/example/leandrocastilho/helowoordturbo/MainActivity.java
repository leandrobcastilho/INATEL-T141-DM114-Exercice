package com.example.leandrocastilho.helowoordturbo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.leandrocastilho.helowoordturbo.fragments.GCMFragment;
import com.example.leandrocastilho.helowoordturbo.fragments.InterestProductInfoFragment;
import com.example.leandrocastilho.helowoordturbo.fragments.InterestProductListFragment;
import com.example.leandrocastilho.helowoordturbo.fragments.ListaPedidosFragment;
import com.example.leandrocastilho.helowoordturbo.fragments.ListaPedidosFragmentMenu;
import com.example.leandrocastilho.helowoordturbo.fragments.ListaPedidosSofisticadoFragment;
import com.example.leandrocastilho.helowoordturbo.fragments.OrdersFragment;
import com.example.leandrocastilho.helowoordturbo.fragments.SettingsFragment;
import com.example.leandrocastilho.helowoordturbo.fragments.Tela1Fragment;
import com.example.leandrocastilho.helowoordturbo.fragments.Tela2Fragment;
import com.example.leandrocastilho.helowoordturbo.fragments.Tela3Fragment;
import com.example.leandrocastilho.helowoordturbo.models.InterestProduct;
import com.example.leandrocastilho.helowoordturbo.models.OrderInfo;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private OrderInfo orderInfo;
    private InterestProduct interestProduct;

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.hasExtra("orderInfo")) {
            loadOrderNotification(intent);
        } else if (intent.hasExtra("productOfInterest")) {
            loadInterestProductNotification(intent);
        }
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = this.getIntent();
        if (intent.hasExtra("orderInfo")) {
            loadOrderNotification(intent);
        } else if (intent.hasExtra("productOfInterest")) {
            loadInterestProductNotification(intent);
        } else if (savedInstanceState == null) {
            displayFragment(R.id.nav_tela1);
        }

        createNotificationChannel();
    }

    private void loadOrderNotification(Intent intent) {
        this.orderInfo = (OrderInfo) intent.getSerializableExtra("orderInfo");
        if (this.orderInfo != null) {
            displayFragment(R.id.nav_register);
        }
    }

    private void loadInterestProductNotification(Intent intent) {
        this.interestProduct = (InterestProduct) intent.getSerializableExtra("productOfInterest");
        if (this.interestProduct != null) {
            Class fragmentClass;
            Fragment fragment = null;
            fragmentClass = InterestProductInfoFragment.class;
            try {
                Bundle bundle = new Bundle();
                bundle.putSerializable("productOfInterest", this.interestProduct);
                this.interestProduct = null;
                fragment = (Fragment) fragmentClass.newInstance();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, fragment, InterestProductInfoFragment.class.getCanonicalName());
                transaction.addToBackStack(Tela1Fragment.class.getCanonicalName());
                transaction.commit();
            } catch (Exception e) {
                try {
                    Toast.makeText(this, "Erro ao tentar abrir detalhes do Produto de interesse", Toast.LENGTH_SHORT).show();
                } catch (Exception e1) {
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int entryCount = getFragmentManager().getBackStackEntryCount();
        if ( entryCount == 1) {
            displayFragment(R.id.nav_tela1);
        } else {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displayFragment(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayFragment(int fragmentId) {
        Class fragmentClass;
        Fragment fragment = null;

        int backStackEntryCount;
        backStackEntryCount = getFragmentManager().getBackStackEntryCount();
        for (int j = 0; j < backStackEntryCount; j++) {
            getFragmentManager().popBackStack();
        }

        try {
            switch (fragmentId) {
                case R.id.nav_tela1:
                    fragmentClass = Tela1Fragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;

                case R.id.nav_tela2:
                    fragmentClass = Tela2Fragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;

                case R.id.nav_tela3:
                    fragmentClass = Tela3Fragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;

                case R.id.nav_listaPedidos:
                    fragmentClass = ListaPedidosFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;

                case R.id.nav_listaPedidosSofisticado:
                    fragmentClass = ListaPedidosSofisticadoFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;

                case R.id.nav_listaPedidosMenu:
                    fragmentClass = ListaPedidosFragmentMenu.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;

                case R.id.nav_orders:
                    fragmentClass = OrdersFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;

                case R.id.nav_interst_products:
                    fragmentClass = InterestProductListFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;

                case R.id.nav_settings:
                    fragmentClass = SettingsFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;

                case R.id.nav_register:
                    fragmentClass = GCMFragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    if (this.orderInfo != null) {
                        Bundle args = new Bundle();
                        args.putSerializable("orderInfo", this.orderInfo);
                        fragment.setArguments(args);
                        this.orderInfo = null;
                    }
                    break;

                default:
                    fragmentClass = Tela1Fragment.class;
                    fragment = (Fragment) fragmentClass.newInstance();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            if( fragmentId != R.id.nav_tela1)
                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(Tela1Fragment.class.getCanonicalName()).commit();
            else
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            NotificationChannel channel1 = new NotificationChannel("1", "SiecolaVendas", importance);
            channel1.setDescription("Orders");
            notificationManager.createNotificationChannel(channel1);
            NotificationChannel channel2 = new NotificationChannel("2", "SiecolaMessage", importance);
            channel2.setDescription("Interest Products");
            notificationManager.createNotificationChannel(channel2);
        }
    }

}
