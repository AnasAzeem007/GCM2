package com.anas.gcm2;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gcm.GCMRegistrar;

public class Unregistered extends Activity {
	AsyncTask<Void, Void, Void> mUnregisterTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unregistered);

		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		final String regId = GCMRegistrar.getRegistrationId(this);
		GCMRegistrar.unregister(this);

		final Context context = this;
		mUnregisterTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// Register on our server
				// On server creates a new user
				ServerUtilities.unregister(context, regId);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				mUnregisterTask = null;
				Unregistered.this.finish();
			}

		};
		mUnregisterTask.execute(null, null, null);
	}

}
