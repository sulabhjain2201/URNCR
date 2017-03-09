package in.globalsoft.urncr;

import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;
import in.globalsoft.util.ParseInfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static in.globalsoft.urncr.HospitalsMap1.hospitalListBeans;

public class HospitalList extends Activity
{
	ListView listHospital;
	TextView tv_title;
	AppPreferences appPref;
	private  String data;
	private int specialityId;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_hospital_list);
		appPref = new AppPreferences(this);
		tv_title = (TextView)findViewById(R.id.title_text);
		specialityId = getIntent().getIntExtra("speciality_id",0);
		tv_title.setText(appPref.getMapType().toUpperCase());
		listHospital = (ListView)findViewById(R.id.list_hospitals);
		hospitalListBeans = null;
		new SpecialDoctorTask().execute();
		//listHospital.setAdapter(new AdapterSearchDoctorsList(this, hospitalListBeans.getHospital_list()));
		
	listHospital.setOnItemClickListener(new OnItemClickListener()
	{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) 
		{
			
			if(hospitalListBeans.getHospital_list().get(arg2).getOpen_status().equals(""))
			{
				Toast.makeText(HospitalList.this, "Hospital is not registered", Toast.LENGTH_LONG).show();
			}
			else
			{
			Intent i = new Intent(HospitalList.this,HospitalDescription.class);
			i.putExtra("position", arg2);
			startActivity(i);
			}
		
			
		}
		
	});

	}

	private class SpecialDoctorTask extends AsyncTask<Void, Void, Void> {


		ProgressDialog pd;

		@Override
		protected void onPreExecute()
		{
			pd = ProgressDialog.show(HospitalList.this, null, "Loading...");
			pd.show();
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		// Invoked by execute() method of this object
		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				String specialDoctorUrl = Cons.url_special_doctors_by_speciality+specialityId;
				data = Cons.http_connection(specialDoctorUrl);
				System.out.println("data::"+data);
				if(data!=null)
				{
					ParseInfo parseInfo = new ParseInfo();
					hospitalListBeans = parseInfo.parseHospitalInfo(data);
				}
				//				PlaceJSONParser placeJsonParser = new PlaceJSONParser();
				//
				//				JSONObject jObject = new JSONObject(data);
				//
				//				/** Getting the parsed data as a List construct */
				//				places = placeJsonParser.parse(jObject);


			}

			catch (Exception e)
			{
				Log.d("Background Task", e.toString());
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result)
		{
			if(pd.isShowing())
			{
				pd.dismiss();
			}
			Message myMessage = new Message();
			myMessage.obj = "hospital_info_task";
			myHandler.sendMessage(myMessage);
			// Clears all the existing markers


			super.onPostExecute(result);

		}

	}

	private Handler myHandler = new Handler()
	{

		public void handleMessage(Message msg)
		{


			if (msg.obj.toString().equalsIgnoreCase("hospital_info_task"))
			{
				if (!isFinishing())
				{

					if((hospitalListBeans == null) || data == null)

					{
						System.out.println("net available::"+Cons.isNetAvail);
						System.out.println("list::"+hospitalListBeans);
						System.out.println("data::"+data);

						Cons.isNetAvail = 0;
						Toast.makeText(HospitalList.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();

					}

					else if(hospitalListBeans.getCode()==200)
					{


						if(hospitalListBeans.getHospital_list().size() == 0)
						{
							Toast.makeText(HospitalList.this, "No near by hospital found.", Toast.LENGTH_LONG).show();
						}
						else
						{
							listHospital.setAdapter(new AdapterSearchDoctorsList(HospitalList.this, hospitalListBeans.getHospital_list()));
						}
					}
					else
					{

						Toast.makeText(HospitalList.this, "Api not working or connection is slow.Please try after some time.", Toast.LENGTH_LONG).show();
					}



				}
			}


		}
	};



}
