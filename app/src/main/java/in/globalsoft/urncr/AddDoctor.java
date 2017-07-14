package in.globalsoft.urncr;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import in.globalsoft.beans.BeansAddDoctorResponse;
import in.globalsoft.urncr.R;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.util.Cons;


public class AddDoctor extends Activity
{

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT =  200;
    private static final int REQUEST_PERMISSION_SETTING = 201;
    AlertDialog dialog_upload_bill;
    private Uri mImageCaptureUri;
    Bitmap bitmap = null;
    Bitmap photo = null;
    ImageView iv_doctor_image;
    EditText et_doctorName,et_doctorPhone,et_doctorAddress;
    EditText et_doctor_email,et_doctorState;
    String responseString;
    BeansAddDoctorResponse doctorInfoBeans;

    public static String path = "";
    int serverResponseCode;
    String serverResponseMessage;
    String ts;
    Button btn_addDoctor;
    RelativeLayout layout_speciality;
    TextView tv_speciality;
    RelativeLayout layout_address;
    EditText et_address;
    AlertDialog dialog_speciality,dialog_address;
    public static String str_speciality="";
    AppPreferences appPref;
    public static String str_doctorName="",str_doctorAddress="",str_doctorEmail="",str_doctorPhone="";
    double mLatitude = 0;
    double mLongitude = 0;
    private LocationClient locationClient;
    LocationRequest mLocationRequest;

    private boolean sentToSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        appPref = new AppPreferences(this);
        init();

        iv_doctor_image.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                if (ActivityCompat.checkSelfPermission(AddDoctor.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(AddDoctor.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        //Show Information about why you need the permission
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddDoctor.this);
                        builder.setTitle("Need Storage Permission");
                        builder.setMessage("This app needs storage permission.");
                        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                ActivityCompat.requestPermissions(AddDoctor.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                }
// else if (appPref.getStoragePermission()) {
//                        //Previously Permission Request was cancelled with 'Dont Ask Again',
//                        // Redirect to Settings after showing Information about why you need the permission
//                        AlertDialog.Builder builder = new AlertDialog.Builder(AddDoctor.this);
//                        builder.setTitle("Need Storage Permission");
//                        builder.setMessage("This app needs storage permission.");
//                        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                                sentToSettings = true;
//                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                Uri uri = Uri.fromParts("package", getPackageName(), null);
//                                intent.setData(uri);
//                                startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
//                                Toast.makeText(getBaseContext(), "Go to Permissions to Grant Storage", Toast.LENGTH_LONG).show();
//                            }
//                        });
//                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        });
//                        builder.show();
//                    }
                      else {
                        //just request the permission
                        ActivityCompat.requestPermissions(AddDoctor.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                    }

                   appPref.setStoragePermission(true);


                } else {
                    //You already have the permission, just go ahead.
                    create_dialog_for_upload_bill();
                    dialog_upload_bill.show();
                }




            }
        });

        btn_addDoctor.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                checkForBlanks();

            }
        });

        layout_speciality.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if(mLatitude == 0)
                {
                    Toast.makeText(AddDoctor.this, "Current location is not available.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    dialogSpeciality();
                    dialog_speciality.show();
                }

            }
        });

        layout_speciality.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                dialogSpeciality();
                dialog_speciality.show();

            }
        });
/*		layout_address.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if(str_speciality.equals(""))
				{
					Toast.makeText(AddDoctor.this, "Enter City and Speciality", Toast.LENGTH_LONG).show();
				}
				else
				{
					dialog_address.show();
				}

			}
		});
*/
    }

    public void init() {
        // TODO Auto-generated method stub
        iv_doctor_image = (ImageView) findViewById(R.id.doctor_image);




        tv_speciality = (TextView) findViewById(R.id.doctor_speciality_text);
        layout_speciality = (RelativeLayout) findViewById(R.id.doctor_speciality_layout);

        et_address = (EditText) findViewById(R.id.etDoctorAddress);

        et_doctorName = (EditText) findViewById(R.id.doctor_name);

        et_doctorPhone = (EditText) findViewById(R.id.doctor_phone);

        et_doctor_email = (EditText) findViewById(R.id.doctor_email);


        btn_addDoctor = (Button) findViewById(R.id.add_doctor_btn);

    }

    public void create_dialog_for_upload_bill()
    {
        final String[] items = new String[] { "From Camera", "From SD Card" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(Environment
                            .getExternalStorageDirectory(), "carrxon_"
                            + String.valueOf(System.currentTimeMillis())
                            + ".jpg");
                    mImageCaptureUri = Uri.fromFile(file);

                    try {
                        intent.putExtra(
                                MediaStore.EXTRA_OUTPUT,
                                mImageCaptureUri);
                        intent.putExtra("return-data", true);

                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    dialog.cancel();
                } else {
                    Intent intent = new Intent();

                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent,
                            "Complete action using"), PICK_FROM_FILE);
                }
            }
        });

        dialog_upload_bill = builder.create();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK)
            return;

        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(AddDoctor.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                create_dialog_for_upload_bill();
                dialog_upload_bill.show();
            }
        }

        if (requestCode == PICK_FROM_FILE) {
            mImageCaptureUri = data.getData();
            path = getRealPathFromURI(mImageCaptureUri); // from Gallery

            if (path == null)
                path = mImageCaptureUri.getPath(); // from File Manager

            if (path != null) {

                bitmap = BitmapFactory.decodeFile(path);
                bitmap = Bitmap.createScaledBitmap(bitmap, 140, 140, false);
            }

        }
        else
        {
            path = mImageCaptureUri.getPath();



            try {

                //				if (photo != null) {
                //					photo.recycle();
                //				}

                //				  photo = BitmapFactory.decodeFile(path);
                InputStream stream = getContentResolver().openInputStream(
                        mImageCaptureUri);


                BitmapFactory.Options options=new BitmapFactory.Options();
                options.inSampleSize = 4;
                photo = BitmapFactory.decodeStream(stream, null, options );
                stream.close();

                ExifInterface exif = new ExifInterface(path);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                Log.d("EXIF", "Exif: " + orientation);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                }
                else if (orientation == 3) {
                    matrix.postRotate(180);
                }
                else if (orientation == 8) {
                    matrix.postRotate(270);
                }
                bitmap = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
                bitmap = Bitmap.createScaledBitmap(bitmap, 140, 140, false);
            }
            catch(Exception ae)
            {
                ae.printStackTrace();
            }
            //			bitmap = BitmapFactory.decodeFile(path);
            // bitmap=doGreyscale(bitmap);
        }

        //		iv_hospitalImage.setVisibility(View.VISIBLE);
        iv_doctor_image.setImageBitmap(bitmap);
        //		btn_captureHospitalImage.setVisibility(View.GONE);
        //		btn_chooseAnotherImage.setVisibility(View.VISIBLE);


    }
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);

        if (cursor == null)
            return null;

        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);
    }


    public void dialogSpeciality()
    {
        final String doctors_array[]= {"Urgent Care Centers","Acupuncturists", "Allergists", "Audiologists", "Cardiologists", "Chiropractors", "Colorectal Surgeons", "Dentists", "Dermatologists", "Dietitians", "Ear, Nose & Throat Doctors", "Emergency Medicine Physicians", "Endocrinologists", "Endodontists", "Eye Doctors", "Family Physicians", "Gastroenterologists", "Hand Surgeons", "Hearing Specialists", "Hematologists", "Infectious Disease Specialists", "Infertility Specialists", "Internists", "Naturopathic Doctors", "Nephrologists", "Neurologists", "Neurosurgeons", "Nurse Practitioners", "Nutritionists", "OB-GYNs", "Oncologists", "Ophthalmologists", "Optometrists", "Oral Surgeons", "Orthodontists", "Orthopedic Surgeons", "Pain Management Specialists", "Pediatric Dentists", "Pediatricians", "Periodontists", "Physiatrists", "Physical Therapists", "Plastic Surgeons", "Podiatrists", "Doctors", "Prosthodontists", "Psychiatrists", "Psychologists", "Psychotherapists", "Pulmonologists", "Radiologists", "Rheumatologists", "Sleep Medicine Specialists", "Sports Medicine Specialists", "Surgeons", "Therapists / Counselors", "Travel Medicine Specialists", "Urologists","Primary Care Doctors","Primary Care Centers","Suboxone doctors"};
        final ArrayList<String> listSpeciality = new ArrayList<String>(Arrays.asList(doctors_array));;
//		listSpeciality.add("Physician");


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
                str_speciality = String.valueOf(item+1);
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



    public void checkForBlanks()
    {

        str_doctorEmail = et_doctor_email.getText().toString();
        str_doctorAddress = et_address.getText().toString();
        str_doctorPhone = et_doctorPhone.getText().toString();
        str_doctorName = et_doctorName.getText().toString();

        if(str_doctorName.equals("") || str_doctorAddress.equals("") || str_doctorEmail.equals("") || str_doctorPhone.equals("") || str_speciality.equals("") )
        {
            Toast.makeText(AddDoctor.this, "All the fields are neccessary.", Toast.LENGTH_LONG).show();
        }
        else if(!Cons.isValidEmail(str_doctorEmail))
        {
            Toast.makeText(AddDoctor.this, "Please fill correct email", Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent i = new Intent(AddDoctor.this,HospitalRegistration2.class);
            startActivity(i);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CONSTANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //The External Storage Write Permission is granted to you... Continue your left job...
                create_dialog_for_upload_bill();
                dialog_upload_bill.show();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(AddDoctor.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddDoctor.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();


                            ActivityCompat.requestPermissions(AddDoctor.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);


                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(),"Unable to get Permission",Toast.LENGTH_LONG).show();
                }
            }
        }
    }





}
