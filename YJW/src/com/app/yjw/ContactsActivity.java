package com.app.yjw;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.app.yjw.pojo.ContactInfo;
import com.app.yjw.thread.ForwardDealThread;
import com.app.yjw.thread.ShowMessageThread;
import com.app.yjw.widget.ContactListView;


public class ContactsActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener {

	ContactListView listview;
	Button back_button;
	Button forward_button;
	List<ContactInfo> objects;
	int select_cnt = 0;
	int type;
	final int TYPE_FORWARD = 1;
	final int TYPE_SELECT = 2;
	private String deal_id;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(ContactsActivity.this, "转介成功",
						Toast.LENGTH_SHORT).show();
				ContactsActivity.this.finish();
				break;
			case 0:
				Toast.makeText(ContactsActivity.this, "转介失败",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts);
		init();
	}
	@Override
	protected void onResume(){
		super.onResume();
		ShowMessageThread.SetCurrentContext(this);
	}
	protected void init() {
		super.init();
		// init listview

		Cursor c = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null); // 获得一个ContentResolver并读取联系人列表
		startManagingCursor(c); // 托管结果
		objects = new ArrayList<ContactInfo>();
		while (c.moveToNext()) {
			ContactInfo contact = new ContactInfo();
			contact.setId(c.getString(c
					.getColumnIndex(ContactsContract.Contacts._ID)));
			contact.setName(c.getString(c
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
			boolean has_phone = c
					.getString(
							c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
					.equals("1") ? true : false;
			if (has_phone) {
				Cursor phones = getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = " + contact.getId(), null, null);
				System.out.println("id:" + contact.getId());
				phones.moveToFirst();
				objects.add(contact);
				contact.setPhone(phones
						.getString(
								phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
						.replace("-", ""));
				phones.close();
			}
		}
		ContactListView.ContactAdapter adapter = new ContactListView.ContactAdapter(
				this, R.layout.contact_item, objects);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);

		// init state
		String from = getIntent().getStringExtra("from");
		if (from.equals(DealDetailAndReplyPageActivity.class.toString())) {
			forward_button.setVisibility(View.VISIBLE);
			type = TYPE_FORWARD;
			deal_id = getIntent().getStringExtra("deal_id");
		} else
			type = TYPE_SELECT;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_left:
			Intent intent = new Intent();
			if (type == TYPE_SELECT) {
				String[] strArray = new String[select_cnt];
				int cnt = 0;
				for (int i = 0; i < objects.size(); ++i)
					if (objects.get(i).isSelected())
						strArray[cnt++] = objects.get(i).getPhone();
				intent.putExtra("phones", strArray);
				this.setResult(RESULT_OK, intent);
			}
			this.finish();
			break;
		case R.id.bt_right:
			forward();
			break;
		}
	}

	private void forward() {
		if (select_cnt == 0) {
			Toast.makeText(this, "请选择至少一个转介对象", Toast.LENGTH_SHORT).show();
		} else {
			String[] strArray = new String[select_cnt];
			int cnt = 0;
			for (int i = 0; i < objects.size(); ++i)
				if (objects.get(i).isSelected())
					strArray[cnt++] = objects.get(i).getPhone();
			ForwardDealThread ft = new ForwardDealThread(deal_id, strArray,
					handler);
			new Thread(ft).start();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		CheckBox checkBox = (CheckBox) arg1.findViewById(R.id.checkbox);
		checkBox.toggle();
		if (checkBox.isChecked()) {
			objects.get(arg2).setSelected(true);
			++select_cnt;
		} else {
			objects.get(arg2).setSelected(false);
			--select_cnt;
		}
		System.out.println(select_cnt);
	}

	@Override
	protected void initViews() {
		listview = (ContactListView) findViewById(R.id.listview);
		// init buttons
		back_button = (Button) findViewById(R.id.bt_left);
		back_button.setOnClickListener(this);
		forward_button = (Button) findViewById(R.id.bt_right);
		forward_button.setOnClickListener(this);
	}

}
