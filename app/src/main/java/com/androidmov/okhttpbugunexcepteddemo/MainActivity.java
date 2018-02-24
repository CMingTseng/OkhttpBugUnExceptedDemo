package com.androidmov.okhttpbugunexcepteddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.androidmov.okhttputil.FileOperation.FileDownLoader;
import com.androidmov.okhttputil.FileOperation.IFileTransferProgressCallback;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button btnDownload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDownload = (Button)findViewById(R.id.btnStartdownload);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startdownLoadAction( url, localFileSavedName, savePath);
            }
        });
    }
    private void startdownLoadAction(String url,String localFileSavedName,String savePath){
        FileDownLoader.Builder builder = new FileDownLoader.Builder()
                .Url(url)     			//url
                .LocalFileName(localFileSavedName) //LocalFileName is the file name to be saved locally
                //.Speed(50000)				//Speed limit，UNIT:Kb/s
                .SavePath(savePath)	//file path
                .BreakPointDownloadMode(false) //download file from break point
                .IFileTransferProgressCallback(new IFileTransferProgressCallback() {  //below is callback interface

                    public void onProgress(long currentBytes, long totalBytes) {
                        // TODO Auto-generated method stub
                        System.out.println("onProgress-- " + "currentBytes =" + currentBytes + ";  totalBytes = " + totalBytes);
                        System.out.println("Progress Percent =" + currentBytes * 100 / totalBytes + "%");
                    }

                    public void onFinish(File file) {
                        // TODO Auto-generated method stub
                        System.out.println("onFinish-- FilePath = " + file.getAbsolutePath().toString());
                    }

                    public void onFail(int statusCode, Exception e, File file) {
                        // TODO Auto-generated method stub
                        System.out.println("onfail--statusCode =" + statusCode + " FilePath = " + file.getAbsolutePath().toString());
                    }

                    public void onCancle(File file) {
                        // TODO Auto-generated method stub
                        System.out.println("oncancle-- file" + file);
                    }
                });
        FileDownLoader fileDownLoader = builder.build();
        try {
            fileDownLoader.download();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
