package in.globalsoft.urncr;


import in.globalsoft.fragment.HomeFragment;
import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.special.ResideMenu.ResideMenu;


public class HomeScreen extends FragmentActivity
{
	private  Button btn_register,btn_login,btn_physician_n_fac,btn_hospitalRegistration,btn_search_doctor;
	private Button btn_painMgmtSpecialities,btn_findPhysian,btn_findFacility,btnChatNow , btnSavingCards;
	private AppPreferences appPref;
    private ResideMenu resideMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_with_menu);

        init();

        if( savedInstanceState == null )
             addFragment();

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });



//        btn_login = (Button) findViewById(R.id.login);
//        btn_register = (Button) findViewById(R.id.register);
//        //	btn_physician_n_fac = (Button) findViewById(R.id.facilities_n_physician);
//        btn_hospitalRegistration = (Button) findViewById(R.id.register_as_doctor);
//        btn_painMgmtSpecialities = (Button) findViewById(R.id.painManagemet_btn);
//        btn_findPhysian = (Button) findViewById(R.id.find_physician);
//        btn_findFacility = (Button) findViewById(R.id.find_facility);
//        btn_search_doctor = (Button) findViewById(R.id.btnSearchDoctor);
//        btnChatNow = (Button) findViewById(R.id.btnChatNow);
//        btnSavingCards = (Button) findViewById(R.id.copay_saving_btn);
//
//        btnSavingCards.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(HomeScreen.this , ListCopaySavingCardsActivity.class);
//                startActivity(i);
//            }
//        });
//
//        btn_search_doctor.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(HomeScreen.this, SearchDoctor.class);
//                startActivity(i);
//
//            }
//        });
//
//        btnChatNow.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//dialogForLoginRegister();
//            }
//        });
//
//        appPref = new AppPreferences(this);
//
//        btn_painMgmtSpecialities.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                Intent i = new Intent(HomeScreen.this, HospitalList.class);
//                appPref.saveMapType(getString(R.string.map_name));
//                startActivity(i);
//
//            }
//        });
//
//        btn_findFacility.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(HomeScreen.this, FacilityList.class);
//                startActivity(i);
//
//            }
//        });
//        btn_findPhysian.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(HomeScreen.this, ListBusinessPersons.class);
//
//                startActivity(i);
//
//            }
//        });
//
//
//        btn_login.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(HomeScreen.this, LoginScreen.class);
//                startActivity(i);
//            }
//        });
//        btn_register.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(HomeScreen.this, RegisterScreen1.class);
//                startActivity(i);
//            }
//        });
//
//        btn_hospitalRegistration.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(HomeScreen.this, AddDoctor.class);
//                startActivity(i);
//            }
//        });


        //addLeftDrawer();
    }

    private void addFragment() {
        resideMenu.clearIgnoredViewList();
        HomeFragment fragment=new HomeFragment();
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fm.beginTransaction();
        fragmentTransaction.add(R.id.main_fragment,fragment,"HomeFragment");
         fragmentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void init() {

        resideMenu = new ResideMenu(HomeScreen.this, R.layout.menu_left,-1);
        resideMenu.setUse3D(true);
        resideMenu.setBackground(R.color.white);
        resideMenu.attachToActivity(this);
        resideMenu.setScaleValue(0.5f);
        resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        resideMenu.getLeftMenuView().findViewById(R.id.btnProviderRegistration).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeScreen.this, AddDoctor.class);
                startActivity(i);
            }
        });

        resideMenu.getLeftMenuView().findViewById(R.id.btnPatientRegistration).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeScreen.this, RegisterScreen1.class);
                startActivity(i);
            }
        });

        resideMenu.getLeftMenuView().findViewById(R.id.btnLogin).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeScreen.this, LoginScreen.class);
               startActivity(i);
            }
        });

        resideMenu.getLeftMenuView().findViewById(R.id.btnFacilities).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeScreen.this, FacilityList.class);
                startActivity(i);
            }
        });

        ((TextView) resideMenu.getLeftMenuView().findViewById(R.id.advertisement)).setText("Consult a Doctor online - Online diagnosis and Treatment \n" +
                "\n" +
                "1-833-987-4368 \n" +
                "\n" +
                "1-833-9-URGENT");
       // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);


    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu(){
        return resideMenu;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    private void addLeftDrawer() {

        resideMenu = new ResideMenu(this);
        //resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);


        // create menu items;
        /*String titles[] = { "Home", "Profile", "Calendar", "Settings" };
        int icon[] = { R.drawable.green_marker, R.drawable.green_marker, R.drawable.green_marker, R.drawable.green_marker };

        for (int i = 0; i < titles.length; i++){
            ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            resideMenu.addMenuItem(item,  ResideMenu.DIRECTION_LEFT); // or  ResideMenu.DIRECTION_RIGHT

            findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
                }
            });
//            findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
//                }
//            });
        }*/
    }





}
