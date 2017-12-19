package in.globalsoft.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.special.ResideMenu.ResideMenu;

import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.urncr.HomeScreen;
import in.globalsoft.urncr.HospitalList;
import in.globalsoft.urncr.ListBusinessPersons;
import in.globalsoft.urncr.ListCopaySavingCardsActivity;
import in.globalsoft.urncr.LoginScreen;
import in.globalsoft.urncr.R;
import in.globalsoft.urncr.RegisterScreen1;
import in.globalsoft.urncr.SearchDoctor;

/**
 * Created by root on 18/12/17.
 */

public class HomeFragment extends Fragment {

  private ResideMenu resideMenu;
  private View parentView;
    private Button btn_painMgmtSpecialities,btn_findPhysian,btn_search_doctor,btnChatNow , btnSavingCards;
    AppPreferences appPref ;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView=inflater.inflate(R.layout.activity_home_screen,container,false);

        btn_painMgmtSpecialities = (Button) parentView.findViewById(R.id.painManagemet_btn);
        btn_findPhysian = (Button) parentView.findViewById(R.id.find_physician);

        btn_search_doctor = (Button)parentView.findViewById(R.id.btnSearchDoctor);
        btnChatNow = (Button) parentView.findViewById(R.id.btnChatNow);
        btnSavingCards = (Button) parentView.findViewById(R.id.copay_saving_btn);



        ((TextView)parentView.findViewById(R.id.btnAdvertisement2)).setText("Urgent Care\n$49/month per family\n(Cough, Cold , Flu , Sore throat , Ear pain, Eye and Skin issues , UTI , Refills)\n\n1-8333-987-4368\n\n1-833-9-URGENT");

        HomeScreen parentActivity = (HomeScreen) getActivity();
        resideMenu = parentActivity.getResideMenu();


        btnSavingCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity() , ListCopaySavingCardsActivity.class);
                startActivity(i);
            }
        });

        btn_search_doctor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), SearchDoctor.class);
                startActivity(i);

            }
        });

        appPref = new AppPreferences(getActivity());

        btnChatNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dialogForLoginRegister();
            }
        });

        btn_findPhysian.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ListBusinessPersons.class);

                startActivity(i);

            }
        });


        btn_painMgmtSpecialities.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(getActivity(), HospitalList.class);
                appPref.saveMapType(getString(R.string.map_name));
                startActivity(i);

            }
        });


        return parentView;
    }



    private void dialogForLoginRegister()
    {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(getActivity());
        alt_bld.setMessage("To activate this link you have to register with app or you already registered then login with app").setCancelable(false)
                .setPositiveButton("Register", new DialogInterface.OnClickListener()
                {

                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent i = new Intent(getActivity(),RegisterScreen1.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Login", new DialogInterface.OnClickListener()
                {

                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(getActivity(), LoginScreen.class);
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
