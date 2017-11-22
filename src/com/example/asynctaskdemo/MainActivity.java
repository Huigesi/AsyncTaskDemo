package com.example.asynctaskdemo;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.support.v7.app.ActionBarActivity;
import android.R.integer;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
public class MainActivity extends ActionBarActivity {
	private ImageView img;
	private Button btn;
	private final String IMAGE_PATH="https://www.baidu.com/img/bd_logo1.png";
	private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        img=(ImageView)findViewById(R.id.imageView1);
        btn=(Button)findViewById(R.id.button1);
        dialog=new ProgressDialog(this);
        dialog.setTitle("提示");
		dialog.setMessage("正在加载图片");
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setCancelable(false);
        btn.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View arg0) {
				
				new Mytask().execute(IMAGE_PATH);
			}
		});
    }
    public class Mytask extends AsyncTask<String,Integer, byte[]>{
    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		dialog.show();
    	}
		@Override
		protected byte[] doInBackground(String... params) {
		  
			HttpClient hcClient= new DefaultHttpClient();
			HttpGet httpGet=new HttpGet(params[0]);
			byte[] result=null;
			InputStream iStream=null;
			ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
			try {
				HttpResponse httpResponse=hcClient.execute(httpGet);
				long file_length = httpResponse.getEntity().getContentLength();
				int len=0;
				int total_length=0;
				byte[] data=new byte[1024];
				if (httpResponse.getStatusLine().getStatusCode()==200) {
					//result=EntityUtils.toByteArray(httpResponse.getEntity());
					iStream=httpResponse.getEntity().getContent();
					while ((len=iStream.read(data))!=-1) {
						total_length += len;
						int progress_value=(int)((total_length/(float)file_length)*100);
						publishProgress(progress_value);//发布刻度单位
						outputStream.write(data,0,len);
					}
				}
				result=outputStream.toByteArray();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				hcClient.getConnectionManager().shutdown();
			}
			return result;
		}
		

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			dialog.setProgress(values[0]);
		}
		@Override
		protected void onPostExecute(byte[] result) {
			// 更新UI
			super.onPostExecute(result);
			Bitmap mBitmap=BitmapFactory.decodeByteArray(result, 0, result.length);
			img.setImageBitmap(mBitmap);
			dialog.dismiss();
		}
    }

}
