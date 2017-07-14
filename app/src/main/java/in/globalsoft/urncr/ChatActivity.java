package in.globalsoft.urncr;

import in.globalsoft.adapter.ChatListAdapter;
import in.globalsoft.adapter.DoctorChatListAdapter;
import in.globalsoft.urncr.R;
import in.globalsoft.interfaces.GetChatResult;
import in.globalsoft.interfaces.SendChatResponse;
import in.globalsoft.pojo.ChatListPojo;
import in.globalsoft.pojo.ChatPojo;
import in.globalsoft.pojo.DoctorPojo;
import in.globalsoft.pojo.PatientPojo;
import in.globalsoft.preferences.AppPreferences;
import in.globalsoft.tasks.SinglePatientDoctorTask;
import in.globalsoft.tasks.SendMessageTask;
import in.globalsoft.util.Cons;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

public class ChatActivity extends Activity implements GetChatResult,SendChatResponse

{

	private static ChatActivity chatActivity;
	TextView tvName;
	List<ChatPojo> chatList;
	Gson gson;
	String frnd_id;
	String doctorName;
	ListView lvChat;
	ChatListAdapter adapter;
	DoctorChatListAdapter doctorAdapter;
	DoctorPojo msg;
	PatientPojo msgPatient;
	TextView tvSend;
	EditText etMessage;

	AppPreferences pref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatting_activity);
		chatActivity = this;
		//Bundle b=i.getBundleExtra("b");
		//		frnd_id=i.getExtras().getString("frnd_id");
		//		doctorName=i.getExtras().getString("doctr_name");

		pref=new AppPreferences(this);
		if(pref.getLogintype()==1)
		{
			String url="";
			msgPatient=(PatientPojo)  getIntent().getExtras().getSerializable("PatientMessagePojo");

			pref.setCurrentChatFriendId(msgPatient.getPatient_id());
			/*if(pref.getDoctorId().equals(msgPatient.getUser_id()))
			{
				url="http://urncr.com/CarrxonWebServices/ws/getChat.php?user_id="+.getUser_id()+"&friend_id="+msgPatient.getFriend_id();
			}

			else 
			{*/
			url="http://urncr.com/CarrxonWebServices/ws/getChat.php?user_id="+pref.getDoctorId()+"&friend_id="+msgPatient.getPatient_id();
			//}



			new SinglePatientDoctorTask(ChatActivity.this,url,false).execute();

		}
		else
		{
			String url;
			msg=(DoctorPojo)  getIntent().getExtras().getSerializable("MessagePojo");
			pref.setCurrentChatFriendId(msg.getDoctor_id());
			/*	if(pref.getUserId().equals(msg.getUser_id()))
			{
				url="http://urncr.com/CarrxonWebServices/ws/getChat.php?user_id="+pref.getUserId()+"&friend_id="+msg.getFriend_id();
			}
			else {
				url="http://urncr.com/CarrxonWebServices/ws/getChat.php?user_id="+msg.getFriend_id()+"&friend_id="+msg.getUser_id();
			}*/
			url="http://urncr.com/CarrxonWebServices/ws/getChat.php?user_id="+pref.getUserId()+"&friend_id="+msg.getDoctor_id();


			new SinglePatientDoctorTask(ChatActivity.this,url,false).execute();
		}
		initAll();
		
		


		tvSend.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!etMessage.getText().toString().equals(""))
				{
					String conv_id;
					String url="";
					String sender_id;
					String receiver_id;
					if(pref.getLogintype()==1)
					{

						if((Integer.parseInt(pref.getDoctorId()))<(Integer.parseInt(msgPatient.getPatient_id())))
							conv_id=pref.getDoctorId()+"_"+msgPatient.getPatient_id();
						else {
							conv_id=msgPatient.getPatient_id()+"_"+pref.getDoctorId();	
						}

						url="http://urncr.com/CarrxonWebServices/ws/sendchat.php?user_id="+pref.getDoctorId()+"&friend_id="+msgPatient.getPatient_id()+"&chat="+etMessage.getText().toString()+"&conv_id="+conv_id+"&status=doctor&chat_time="+Cons.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss");



					}
					else
					{
						if((Integer.parseInt(pref.getUserId()))<(Integer.parseInt(msg.getDoctor_id())))
							conv_id=pref.getUserId()+"_"+msg.getDoctor_id();
						else {
							conv_id=msg.getDoctor_id()+"_"+pref.getUserId();	
						}
						url="http://urncr.com/CarrxonWebServices/ws/sendchat.php?user_id="+pref.getUserId()+"&friend_id="+msg.getDoctor_id()+"&chat="+etMessage.getText().toString()+"&conv_id="+conv_id+"&status=patient&chat_time="+Cons.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
					}

					new SendMessageTask(ChatActivity.this, url,false).execute();
				}

			}
		});






	}

	private void initAll() 
	{
		// TODO Auto-generated method stub
		tvName=(TextView) findViewById(R.id.tvName);
		if(pref.getLogintype()==1)
		{
			tvName.setText(msgPatient.getFirst_name()+" "+msgPatient.getLast_name());
		}
		else
			tvName.setText(msg.getDoctor_name());
		lvChat=(ListView) findViewById(R.id.lvChat);
		tvSend=(TextView) findViewById(R.id.tvSend);
		etMessage=(EditText) findViewById(R.id.etMsg);
		chatList =  new ArrayList<ChatPojo>();
		adapter =new ChatListAdapter(chatActivity, chatList);
		doctorAdapter = new DoctorChatListAdapter(chatActivity, chatList);
		lvChat.setAdapter(adapter);
		lvChat.setAdapter(doctorAdapter);


	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		chatActivity=this;
		
		registerReceiver(broadcastReceiver, new IntentFilter(GcmIntentService.BROADCAST_ACTION));


	}


	@Override
	public void ChatResult(String result) 
	{
		// TODO Auto-generated method stub
		if(result!=null && !result.equals(""))
		{
			gson=new Gson();

			if(pref.getLogintype()==1)
			{


				ChatListPojo chats=gson.fromJson(result, ChatListPojo.class);
				if(chats.getStatus().equals("1"))
				{
					chatList=chats.getMessage();

					doctorAdapter=new DoctorChatListAdapter(ChatActivity.this, chatList);
					lvChat.setAdapter(doctorAdapter);

				}

			}
			else
			{
				ChatListPojo chats=gson.fromJson(result, ChatListPojo.class);
				if(chats.getStatus().equals("1"))
				{
					chatList=chats.getMessage();

					adapter=new ChatListAdapter(ChatActivity.this, chatList);
					lvChat.setAdapter(adapter);


				}
			}


		}


	}


	public void updateChat(String id,String msg) 
	{
		ChatPojo pojo=new ChatPojo();


		if(pref.getLogintype()==1)
		{
			pojo.setChat(msg);
			//pojo.setChat_id(object.getString("chat_id").toString());
			pojo.setFriend_id(pref.getDoctorId());
			pojo.setUser_id(id);
			doctorAdapter.setChatList(pojo);
		}
		else
		{
			pojo.setChat(msg);
			//pojo.setChat_id(object.getString("chat_id").toString());
			pojo.setFriend_id(pref.getUserId());
			pojo.setUser_id(id);
			chatList.add(pojo);
			adapter = new ChatListAdapter(ChatActivity.this, chatList);
			lvChat.setAdapter(adapter);
		}
		
		lvChat.setSelection(chatList.size()-1);
		
	}


	@Override
	public void SendChatResult(String Result) 
	{
		// TODO Auto-generated method stub

		if(Result!=null && !Result.equals(""))
		{

			try {
				JSONObject object= new JSONObject(Result);

				if(object.getString("status").equals("1"))
				{
					if(object.getString("message").equalsIgnoreCase("Successfully inserted"))
					{
						ChatPojo pojo=new ChatPojo();


						if(pref.getLogintype()==1)
						{
							pojo.setChat(etMessage.getText().toString());
							pojo.setChat_id(object.getString("chat_id").toString());
							pojo.setFriend_id(msgPatient.getPatient_id());
							pojo.setUser_id(pref.getDoctorId());
							if(doctorAdapter != null)
							doctorAdapter.setChatList(pojo);
							else
							{
								chatList = new ArrayList<ChatPojo>();
								chatList.add(pojo);
								doctorAdapter = new DoctorChatListAdapter(chatActivity, chatList);
								lvChat.setAdapter(doctorAdapter);
							}
						}
						else
						{
							pojo.setChat(etMessage.getText().toString());
							pojo.setChat_id(object.getString("chat_id").toString());
							pojo.setFriend_id(msg.getDoctor_id());
							pojo.setUser_id(pref.getUserId());
							if(adapter != null)
								adapter.setChatList(pojo);
								else
								{
									chatList = new ArrayList<ChatPojo>();
									chatList.add(pojo);
									adapter = new ChatListAdapter(chatActivity, chatList);
									lvChat.setAdapter(adapter);
								}
						}
						lvChat.setSelection(chatList.size()-1);
						etMessage.setText("");
					}

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}




		}


	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(broadcastReceiver);
		chatActivity=null;
	}


	 private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	        	String id=intent.getStringExtra("id");
	        	String name=intent.getStringExtra("message");
	        	updateChat(id, name);   
	        }
	    };    


	public static ChatActivity getInstance()
	{
		return chatActivity;
	}

}
