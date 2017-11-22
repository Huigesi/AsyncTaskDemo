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
	//���������ǰһ��AsyncTask�������ټ��������BUG
	//����ͨ����activity����������ͬ���ķ������ڹر�ʱȡ�����ء�
	@Override
	protected void onPause() {
		super.onPause();
		//cancelֻ�ǽ�AsyncTask״̬���Ϊcancel��������ȡ���г�ִ��
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
					//��isCancelled�����ж���״̬�Ƿ�Ϊcancel
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
			pg.setProgress(values[0]);//����UI
		}
		
		
	}

}
