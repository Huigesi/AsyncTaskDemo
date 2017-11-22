package com.example.asynctaskdemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

public class ProgressBarActivity extends Activity{
	private ProgressBar pg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progressbar);
		pg=(ProgressBar)findViewById(R.id.progressBar1);
		new AsyncTaskTest().execute();
		
	}
	public class AsyncTaskTest extends AsyncTask<Void, Integer, Void>{
		

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				Thread.sleep(4000);
				for(int i=0;i<100;i++){

					publishProgress(i);
					Thread.sleep(300);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			pg.setProgress(values[0]);
		}
		
		
	}

}
