package in.globalsoft.urncr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import in.globalsoft.beans.BeanSpecaility;
import in.globalsoft.beans.BeansHospitalInfo;
import in.globalsoft.beans.BeansHospitalList;
import in.globalsoft.beans.BeansListSpecialities;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;
import in.globalsoft.util.ParseInfo;

public class SearchDoctor extends Activity{
	
	private TextView tv_speciality;
	private RelativeLayout layout_speciality ,rlCountry , rlState , rlCity;
	private EditText et_name, et_city;

	private Button search_doctor;

	private String str_speciality;
	
	String url , data;
	private AlertDialog dialog_speciality;
	public static BeansHospitalList hospitalListBeans;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_search_doctor);
		
		tv_speciality = (TextView) findViewById(R.id.doctor_speciality_text);
		layout_speciality = (RelativeLayout) findViewById(R.id.doctor_speciality_layout);

		rlCountry = (RelativeLayout) findViewById(R.id.country_layout);
		rlState = (RelativeLayout) findViewById(R.id.state_layout);
		rlCity = (RelativeLayout) findViewById(R.id.city_layout);

		et_name=(EditText)findViewById(R.id.et_search_name);
		//et_city=(EditText)findViewById(R.id.et_search_city);

		rlCountry.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});
		
		search_doctor= (Button)findViewById(R.id.search_doctor_btn);
		
		
		search_doctor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!et_name.getText().toString().equals("") && !et_city.getText().toString().equals("")&& !tv_speciality.getText().toString().equals("Specialty"))
				{
					url= Cons.url_search_doctor+ et_name.getText().toString()+" " + 
				tv_speciality.getText().toString() + " in " + et_city.getText().toString();
				}
				else if(!et_name.getText().toString().equals("") && !et_city.getText().toString().equals(""))
				{
					url= Cons.url_search_doctor+ et_name.getText().toString()+
							" in " + et_city.getText().toString();
				}
				else if(!et_name.getText().toString().equals("") && !tv_speciality.getText().toString().equals("Specialty") )
				{
					url= Cons.url_search_doctor+ et_name.getText().toString()+" " 
							 + tv_speciality.getText().toString();
				}
				else if(!et_city.getText().toString().equals("")&& !tv_speciality.getText().toString().equals("Specialty"))
				{
					url= Cons.url_search_doctor+ tv_speciality.getText().toString() + " in "
							+ et_city.getText().toString();
				}
				else
					Toast.makeText(SearchDoctor.this, "Atleast two fields are required to search a doctor.", Toast.LENGTH_LONG).show();
				if(url != null)
				new SearchDoctorTask().execute();
				
				
			}
		});
		
		layout_speciality.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v) 
			{
				
				
					dialogSpeciality();
					dialog_speciality.show();
				

			}
		});
		
		
	}

	public void dialogSpeciality()
	{
		BeansListSpecialities beansListSpecialities = new Gson().fromJson(new AppPreferences(SearchDoctor.this).getListSpecialities(), BeansListSpecialities.class);
		final List<BeanSpecaility> specialities = beansListSpecialities.getSpecialities();
		final List<String> listSpeciality = new ArrayList<String>();
		for(BeanSpecaility specaility : specialities){
			listSpeciality.add(specaility.getName());
		}


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, listSpeciality);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Doctor Specialty");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				if (item == 0) {


					dialog.cancel();
				}
				else if (item == 1) {


					dialog.cancel();

				}
				else if (item == 2) {


					dialog.cancel();

				}
				else if (item == 3) {


					dialog.cancel();

				}

				tv_speciality.setText(listSpeciality.get(item));
				str_speciality = String.valueOf(specialities.get(item).getId());
				str_speciality = String.valueOf(specialities.get(item).getId());
/*				if(Cons.isNetworkAvailable(AddDoctor.this))
				{
					new GetDoctorAddressesTask(AddDoctor.this).execute();
				}
				else
					Cons.showDialog(AddDoctor.this, "Carrxon", "Internet connection is not available.", "OK");
*/
			}
		});

		dialog_speciality = builder.create();
	}



	private class SearchDoctorTask extends AsyncTask<Void, Void, Void> {

			
			ProgressDialog pd;

			@Override
			protected void onPreExecute() 
			{
				pd = ProgressDialog.show(SearchDoctor.this, null, "Loading...");
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
					data = Cons.http_connection(url);
					
					if(data!=null)
					{
						ParseInfo parseInfo = new ParseInfo();
						hospitalListBeans = parseInfo.parseHospitalInfo(data);
					}
					
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
				myMessage.obj = "search_doctor_task";
				myHandler.sendMessage(myMessage);
				// Clears all the existing markers


				super.onPostExecute(result);

			}

		}
			
			private Handler myHandler = new Handler() 
			{

				public void handleMessage(Message msg)
				{


					if (msg.obj.toString().equalsIgnoreCase("search_doctor_task"))
					{
						if (!isFinishing()) 
						{

							if((hospitalListBeans == null)||Cons.isNetAvail==1 || data == null)

							{

								Cons.isNetAvail = 0;
								Toast.makeText(SearchDoctor.this, "Connection is slow or some error in apis.", Toast.LENGTH_LONG).show();
							
							}

							else if(hospitalListBeans.getCode()==200)
							{

								
								if(hospitalListBeans.getHospital_list().size() == 0)
								{
									Toast.makeText(SearchDoctor.this, "No near by hospital found.", Toast.LENGTH_LONG).show();
								}
								else
								{
									HospitalsMap1.hospitalListBeans = hospitalListBeans;
									List<BeansHospitalInfo> list_doctor= hospitalListBeans.getHospital_list();
									
									if(list_doctor.size()>0)
									{
										Intent i = new Intent(SearchDoctor.this, ListDoctors.class);
										//i.putExtra("doctor_list", list_doctor);
										
										startActivity(i);
									}
									
								}
							}
							else 
							{

								Toast.makeText(SearchDoctor.this, "Api not working or connection is slow.Please try after some time.", Toast.LENGTH_LONG).show();
							}



						}
					}


				}
			};

}
