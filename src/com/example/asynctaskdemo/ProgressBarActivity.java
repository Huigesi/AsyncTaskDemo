package com.example.asynctaskdemo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

public class ProgressBarActivity extends Activity{
	private ProgressBar pg;
	AsyncTaskTest myTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progressbar);
		pg=(ProgressBar)findViewById(R.id.progressBar1);
		 myTask=new AsyncTaskTest();
		myTask.execute();
		
	}
	//反复按会等前一个AsyncTask记载完再继续，会出BUG
	//可以通过与activity的生命周期同步的方法，在关闭时取消加载。
	@Override
	protected void onPause() {
		super.onPause();
		//cancel只是将AsyncTask状态标记为cancel，并不是取消行程执行
		if(myTask!=null&&myTask.getStatus()==AsyncTask.Status.RUNNING){
			myTask.cancel(true);
		}
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
				for(int i=0;i<100;i++){
					//用isCancelled方法判断是状态是否为cancel
					if(isCancelled()){
						break;
					}
					publishProgress(i);
					Thread.sleep(300);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			
			if(isCancelled()){
				return;
			}
			super.onProgressUpdate(values);
			pg.setProgress(values[0]);//更新UI
		}
		
		
	}

}
