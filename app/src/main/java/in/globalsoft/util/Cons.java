package in.globalsoft.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;
import android.util.Log;

public class Cons 
{
	public static int screenWidth,screenHeight;
	public static int isNetAvail = 0;
	public static String url_login = "http://urncr.com/CarrxonWebServices/ws/patient_login.php?";
	public static String url_register = "http://urncr.com/CarrxonWebServices/ws/patient_registration.php?";
	public static String url_addPatient = "http://www.urncr.com/CarrxonWebServices/ws/add_patient_family_member.php?";
	public static String url_hospitalDetail = "http://www.urncr.com/CarrxonWebServices/ws/get_doctor_info.php?";
	public static String url_update_registration = "http://urncr.com/CarrxonWebServices/ws/update_patient_registration_info.php?";
	public static String url_get_patients = "http://urncr.com/CarrxonWebServices/ws/get_all_patient_family_members.php?";
	public static String url_update_patient = "http://urncr.com/CarrxonWebServices/ws/update_patient_family_member.php?";
	public static String url_add_insurance_info = "http://www.urncr.com/CarrxonWebServices/ws/add_insurance_info.php?";
	public static String url_update_insurance_info = "http://www.urncr.com/CarrxonWebServices/ws/update_insurance_info.php?";
	public static String url_get_insurance_info = "http://urncr.com/CarrxonWebServices/ws/get_patient_insurance_info.php?";
	public static String url_add_contactInfo = "http://www.urncr.com/CarrxonWebServices/ws/add_contact_info.php?";
	public static String url_add_checkin_info = "http://www.urncr.com/CarrxonWebServices/ws/add_checkin_info.php?";
	public static String url_add_employer_info = "http://www.urncr.com/CarrxonWebServices/ws/add_employer_info.php?";
	public static String url_get_physicianlist = "http://urncr.com/CarrxonWebServices/ws/get_all_doctor_list.php";
	public static String url_add_doctor_info = "http://urncr.com/CarrxonWebServices/ws/add_doctor.php?";
	public static String url_add_doctor_image = "http://urncr.com/CarrxonWebServices/ws/upload_doctor_image.php?";
	public static String url_add_credit_image = "http://urncr.com/CrxyrWebServices/upload_credit_card_image.php?";
	public static String url_add_hospital_image = "http://urncr.com/CarrxonWebServices/ws/upload_hospital_image.php?";
	public static String url_hospital_register = "http://urncr.com/CarrxonWebServices/ws/hospital_registration.php?";
	public static String url_get_doctors_by_hospital = "http://urncr.com/CarrxonWebServices/ws/get_doctors_by_hospital.php?";
	public static String url_get_physician_detail = "http://urncr.com/CarrxonWebServices/ws/get_doctor_info_by_id.php?";
	public static String url_addScheduleInfo = "http://urncr.com/CarrxonWebServices/ws/update_schedule_info.php?";
	public static String url_update_statusInfo = "http://urncr.com/CarrxonWebServices/ws/update_status_info.php?";
	public static String url_doctorAddress = "http://urncr.com/CarrxonWebServices/ws/search_clinic.php?";
	public static String url_booked_appointments = "http://urncr.com/CarrxonWebServices/ws/getBookedAppointmentsByDoctorAndDate.php?";
	public static String url_available_appointments = "http://urncr.com/CarrxonWebServices/ws/getAvailableAppointmentsByDoctorAndDate.php?";
	public static String url_doctorDetailWithAvailableAppointment = "http://urncr.com/CarrxonWebServices/ws/getDoctorInfoWithAvailableAppointments.php?";
	public static String url_add_docor_education = "http://urncr.com/CarrxonWebServices/ws/add_doctor_education.php?";
	public static String url_search_doctor = "http://www.urncr.com/CarrxonWebServices/ws/search_clinic_by_query.php?query=";
	public static String url_patient_online_status="http://www.urncr.com/CarrxonWebServices/ws/patientStatus.php?patient_id=";
	public static String url_doctor_online_status="http://www.urncr.com/CarrxonWebServices/ws/doctorStatus.php?doctor_id=";
    public static String url_office_online_status="http://www.urncr.com/CarrxonWebServices/ws/officeStatus.php?office_id=";
	public static String url_doctor_office_registration="http://www.urncr.com/CarrxonWebServices/ws/addDoctorOfficeInfo.php?doctor_id=";
    public static String url_getOfficeBydoctorId="http://www.urncr.com/CarrxonWebServices/ws/getDoctocByOfficeId.php?doctor_id=";
	public static String url_special_doctors_by_speciality="http://urncr.com/CarrxonWebServices/ws/list_special_doctors.php?speciality_id=";
	public static  String url_add_admin_doctor_info = "http://urncr.com/CarrxonWebServices/ws/add_admin_doctor.php?";

	public static  final int ADD_DOCTOR = 0;
	public static  final int UPDATE_DOCTOR = 1;

	public static void getScreen_Height(Activity c)
	{
		DisplayMetrics dm = new DisplayMetrics();
		c.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
	}

	public static void showDialog(final Context context, String title, String message, final String click_button)
	{
		System.out.println(context.getClass());
		AlertDialog.Builder alt_bld = new AlertDialog.Builder(context);
		alt_bld.setMessage(message).setCancelable(false)
		.setPositiveButton(click_button, new DialogInterface.OnClickListener() 
		{

			public void onClick(DialogInterface dialog, int id) 
			{
				dialog.cancel();
			}
		});

		AlertDialog alert = alt_bld.create();
		alert.setTitle("\t" + title);
		//		alert.setIcon(AlertDialog.BUTTON_NEGATIVE);
		alert.show();
	}

	public static boolean isNetworkAvailable(Context context) 
	{
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		// ARE WE CONNECTED TO THE NET

		if (connMgr.getActiveNetworkInfo() != null && connMgr.getActiveNetworkInfo().isAvailable() && connMgr.getActiveNetworkInfo().isConnected()) 
			return true;
		else
		{
			Log.v("PIPA", "Internet Connection Not Present");
			return false;
		}

	}

	public static String http_connection(String url_string) 
	{

		String str = null;
		try 
		{
			isNetAvail = 0;
			//			
			url_string = url_string.replace(" ", "%20");
			URL url = new URL(url_string);

			URLConnection connection = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			StringBuffer buffer = new StringBuffer("");

			String line = "";
			while ((line = br.readLine()) != null) 
			{
				buffer.append(line);
			}
			str = buffer.toString();
			// xpp.setInput(br);
		} 
		catch (Exception ae)
		{


		}
		return str;

	}

	public final static boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
		}
	}// Convert a date String from one format to another
	public static String changeDateFormat(String time, String fromFormat,
			String toFormat) {

		SimpleDateFormat sdf = new SimpleDateFormat(fromFormat);
		Date date = null;
		try {
			date = sdf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SimpleDateFormat sdf1 = new SimpleDateFormat(toFormat);
		String s = sdf1.format(date.getTime());
		return s;
	}

	// convert miliseconds(in long) to a string date with required format
	public static String convertMiliSecondsToString(long milliSeconds,
			String dateFormat) {
		// Create a DateFormatter object for displaying date in specified
		// format.
		DateFormat formatter = new SimpleDateFormat(dateFormat);

		// Create a calendar object that will convert the date and time value in
		// milliseconds to date.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return formatter.format(calendar.getTime());
	}

	// convert util date into String with specific format
	public static String convertDateToString(Date date, String format) {

		DateFormat df = new SimpleDateFormat(format);
		String str = df.format(date);
		return str;

	}

	// convert String to Date
	public static Date convertStringToDate(String dateString, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date convertedDate = new Date();
		try {
			convertedDate = dateFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertedDate;
	}

	//convert String date to calendar
	public static Calendar convertStringToCal(String dateString,String format)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			cal.setTime(sdf.parse(dateString));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// all done
		return cal;
	}



}
