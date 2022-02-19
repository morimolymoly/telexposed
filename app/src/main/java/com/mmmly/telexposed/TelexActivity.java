package com.mmmly.telexposed;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TelexActivity extends Activity implements View.OnClickListener{
    private Button save;
    private EditText phonenumber;

    private SharedPreferences preference;
    public static final String PREF_NAME = "custom_prefs";
    public static final String PHONE_NUM = "phone_number";

    private TelephonyManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telex);
        initControl();
    }

    private void initControl() {
        tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

        preference = getSharedPreferences(PREF_NAME, Context.MODE_WORLD_READABLE);
        phonenumber = (EditText)findViewById(R.id.phonenumber);
        save = (Button)findViewById(R.id.save);
        save.setOnClickListener(this);

        try {
            phonenumber.setText(preference.getString(PHONE_NUM, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.save:
                Editor editor = preference.edit();
                editor.putString(PHONE_NUM, phonenumber.getText().toString());
                editor.apply();
                break;
        }
    }
}