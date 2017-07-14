package in.globalsoft.util;

import in.globalsoft.urncr.DisplayDoctorInfo;
import in.globalsoft.urncr.DoctorOfficeHomeActivity;
import in.globalsoft.urncr.HomeScreen;
import in.globalsoft.urncr.HomeScreenWithLogin;
import in.globalsoft.preferences.AppPreferences;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

public class Handlers extends Handler
{
	Context con;
	Activity act;
	AppPreferences appPref;
	public Handlers(Context con)
	{
		this.con = con;
		act = (Activity) con;
		appPref = new AppPreferences(con);
	}
	public void handleMessage(Message msg) {

		if (msg.obj.toString().equalsIgnoreCase("waiting_task"))
		{
			if(appPref.getLoginState()==0)
			{
			Intent i = new Intent(con,HomeScreen.class);
			con.startActivity(i);
			}
			else if(appPref.getLogintype() == 0)
			{
				Intent i = new Intent(con,HomeScreenWithLogin.class);
				con.startActivity(i);
			}
            else if(appPref.getLogintype() == 1)
            {
                Intent i = new Intent(con,DisplayDoctorInfo.class);
                con.startActivity(i);
            }
            else if(appPref.getLogintype() == 2)
			{
				Intent i = new Intent(con,DoctorOfficeHomeActivity.class);
				con.startActivity(i);
			}
			act.finish();
		}
	}
}
