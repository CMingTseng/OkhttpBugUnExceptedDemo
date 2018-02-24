package com.androidmov.okhttpbugunexcepteddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.androidmov.okhttputil.FileOperation.FileDownLoader;
import com.androidmov.okhttputil.FileOperation.IFileTransferProgressCallback;
import java.io.File;

public class MainActivity extends AppCompatActivity {

  private Button btnDownload;
  private EditText etUrl, etLocalFileSaveName, etSavePath;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    btnDownload = (Button) findViewById(R.id.btnStartdownload);
    etLocalFileSaveName = (EditText) findViewById(R.id.etFileName);
    etLocalFileSaveName.setText("file.dat");
    etUrl = (EditText) findViewById(R.id.etURL);
    etUrl.setText("http://speedtest-sfo1.digitalocean.com/100mb.test");
    etSavePath = (EditText) findViewById(R.id.etPath);
    etSavePath.setText(getCacheDir().getPath());
    btnDownload.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startdownLoadAction(etUrl.getText().toString(), etLocalFileSaveName.getText().toString(),
            etSavePath.getText().toString());
      }
    });
  }

  private void startdownLoadAction(String url, String localFileSavedName, String savePath) {
    FileDownLoader.Builder builder = new FileDownLoader.Builder()
        .Url(url)          //url
        .LocalFileName(localFileSavedName) //LocalFileName is the file name to be saved locally
        //.Speed(50000)				//Speed limit，UNIT:Kb/s
        .SavePath(savePath)  //file path
        .BreakPointDownloadMode(false) //download file from break point
        .IFileTransferProgressCallback(
            new IFileTransferProgressCallback() {  //below is callback interface

              public void onProgress(long currentBytes, long totalBytes) {
                Log.i("MA", "onProgress-- "
                    + "currentBytes ="
                    + currentBytes
                    + ";  totalBytes = "
                    + totalBytes);
                Log.i("MA", "Progress Percent =" + currentBytes * 100 / totalBytes + "%");
              }

              public void onFinish(File file) {
                Log.i("MA", "onFinish-- FilePath = " + file.getAbsolutePath());
              }

              public void onFail(int statusCode, Exception e, File file) {
                Log.i("MA",
                    "onfail--statusCode =" + statusCode + " FilePath = " + file.getAbsolutePath());
              }

              public void onCancle(File file) {
                Log.i("MA", "oncancle-- file" + file);
              }
            });
    FileDownLoader fileDownLoader = builder.build();
    try {
      fileDownLoader.download();
    } catch (Exception e) {
      Log.i("MA", "failed", e);
    }
  }
}
