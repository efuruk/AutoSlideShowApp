package jp.techacademy.eri.takashima.slideshowapptakashima;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentUris;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.content.Intent;
import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import java.security.Permissions;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISSIONS_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playbutton1 = (Button) findViewById(R.id.playbutton1);
        playbutton1.setOnClickListener(this);
        Button reversebutton1 = (Button) findViewById(R.id.reversebutton1);
        reversebutton1.setOnClickListener(this);
        Button stopbutton1 = (Button) findViewById(R.id.stopbutton1);
        stopbutton1.setOnClickListener(this);
        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView1.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {
                getContentsInfo();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            getContentsInfo();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch(requestCode){
            case PERMISSIONS_REQUEST_CODE:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getContentsInfo();
                }
                break;
            default:
                break;
        }
    }
private void getContentsInfo() {
    ContentResolver resolver = getContentResolver();
    Cursor cursor = resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            null
    );
    if(cursor.moveToFirst()) {
        int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
        Long id = cursor.getLong(fieldIndex);
        Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView1.setImageURI(imageUri);
    }
    cursor.close();
}

}
