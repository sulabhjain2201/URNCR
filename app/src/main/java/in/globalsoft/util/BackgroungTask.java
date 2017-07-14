package in.globalsoft.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;

public class BackgroungTask extends AsyncTask<Void, Void, Void>
{
	ProgressDialog pd;
	Context con;

	public BackgroungTask(Context con)
	{
		this.con = con;
	}

//	@Override
//	protected void onPreExecute() 
//	{
//		pd = ProgressDialog.show(con, null, "Loading...");
//		super.onPreExecute();
//	}
	@Override
	protected Void doInBackground(Void... params)
	{
		try
		{
			Thread.sleep(3000);
		}
		catch(Exception ae)
		{

		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) 
	{
//		if(pd.isShowing())
//		{
//			pd.dismiss();
//		}
		Handlers handle = new Handlers(con);
		Message myMessage = new Message(); 
		myMessage.obj = "waiting_task";
		handle.sendMessage(myMessage);
		super.onPostExecute(result);

	}

}

