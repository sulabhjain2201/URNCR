package in.globalsoft.urncr;


import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.AdapterFacilityList;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListBusinessPersons extends Activity
{
	AppPreferences appPref;
	List<String> listBusinessPersons;
	ListView list_busineess;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_business_persons);
		appPref = new AppPreferences(this);
		listBusinessPersons = new ArrayList<String>();
		list_busineess = (ListView) findViewById(R.id.list_business);
		listOfDoctors();
		
		list_busineess.setAdapter(new AdapterFacilityList(this,listBusinessPersons));
		
		list_busineess.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) 
			{
				
				appPref.saveMapType(listBusinessPersons.get(arg2));
				Intent i = new Intent(ListBusinessPersons.this,HospitalList.class);
				appPref.saveMapType(listBusinessPersons.get(arg2));
				i.putExtra("speciality_id",arg2+1);

				startActivity(i);
				
			}
			
		});
	}
	
	public void listOfDoctors()
	{
		final String doctors_array[]= {"Urgent Care Centers","Acupuncturists", "Allergists", "Audiologists", "Cardiologists", "Chiropractors", "Colorectal Surgeons", "Dentists", "Dermatologists", "Dietitians", "Ear, Nose & Throat Doctors", "Emergency Medicine Physicians", "Endocrinologists", "Endodontists", "Eye Doctors", "Family Physicians", "Gastroenterologists", "Hand Surgeons", "Hearing Specialists", "Hematologists", "Infectious Disease Specialists", "Infertility Specialists", "Internists", "Naturopathic Doctors", "Nephrologists", "Neurologists", "Neurosurgeons", "Nurse Practitioners", "Nutritionists", "OB-GYNs", "Oncologists", "Ophthalmologists", "Optometrists", "Oral Surgeons", "Orthodontists", "Orthopedic Surgeons", "Pain Management Specialists", "Pediatric Dentists", "Pediatricians", "Periodontists", "Physiatrists", "Physical Therapists", "Plastic Surgeons", "Podiatrists", "Doctors", "Prosthodontists", "Psychiatrists", "Psychologists", "Psychotherapists", "Pulmonologists", "Radiologists", "Rheumatologists", "Sleep Medicine Specialists", "Sports Medicine Specialists", "Surgeons", "Therapists / Counselors", "Travel Medicine Specialists", "Urologists","Primary Care Doctors","Primary Care Centers","Suboxone doctors","Lab Services"};
		for(int i = 0;i<doctors_array.length;i++)
		{
			listBusinessPersons.add(doctors_array[i]);
		}
		
	}
}