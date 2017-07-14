package in.globalsoft.urncr;


import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeScreen extends Activity 
{
	private  Button btn_register,btn_login,btn_physician_n_fac,btn_hospitalRegistration,btn_search_doctor;
	private Button btn_painMgmtSpecialities,btn_findPhysian,btn_findFacility,btnChatNow , btnSavingCards;
	private AppPreferences appPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        btn_login = (Button) findViewById(R.id.login);
        btn_register = (Button) findViewById(R.id.register);
        //	btn_physician_n_fac = (Button) findViewById(R.id.facilities_n_physician);
        btn_hospitalRegistration = (Button) findViewById(R.id.register_as_doctor);
        btn_painMgmtSpecialities = (Button) findViewById(R.id.painManagemet_btn);
        btn_findPhysian = (Button) findViewById(R.id.find_physician);
        btn_findFacility = (Button) findViewById(R.id.find_facility);
        btn_search_doctor = (Button) findViewById(R.id.btnSearchDoctor);
        btnChatNow = (Button) findViewById(R.id.btnChatNow);
        btnSavingCards = (Button) findViewById(R.id.copay_saving_btn);

        btnSavingCards.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeScreen.this , ListCopaySavingCardsActivity.class);
                startActivity(i);
            }
        });

        btn_search_doctor.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomeScreen.this, SearchDoctor.class);
                startActivity(i);

            }
        });

        btnChatNow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
dialogForLoginRegister();
            }
        });

        appPref = new AppPreferences(this);

        btn_painMgmtSpecialities.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(HomeScreen.this, HospitalList.class);
                appPref.saveMapType(getString(R.string.map_name));
                startActivity(i);

            }
        });

        btn_findFacility.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeScreen.this, FacilityList.class);
                startActivity(i);

            }
        });
        btn_findPhysian.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeScreen.this, ListBusinessPersons.class);

                startActivity(i);

            }
        });


        btn_login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomeScreen.this, LoginScreen.class);
                startActivity(i);
            }
        });
        btn_register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomeScreen.this, RegisterScreen1.class);
                startActivity(i);
            }
        });

        btn_hospitalRegistration.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(HomeScreen.this, AddDoctor.class);
                startActivity(i);
            }
        });
    }

    private void dialogForLoginRegister()
    {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(HomeScreen.this);
        alt_bld.setMessage("To activate this link you have to register with app or you already registered then login with app").setCancelable(false)
                .setPositiveButton("Register", new DialogInterface.OnClickListener()
                {

                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent i = new Intent(HomeScreen.this,RegisterScreen1.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Login", new DialogInterface.OnClickListener()
                {

                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(HomeScreen.this, LoginScreen.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        dialog.cancel();
                    }
                });

        AlertDialog alert = alt_bld.create();
        alert.setTitle("\t" + getString(R.string.app_name));
        //		alert.setIcon(AlertDialog.BUTTON_NEGATIVE);
        alert.show();

    }




}
