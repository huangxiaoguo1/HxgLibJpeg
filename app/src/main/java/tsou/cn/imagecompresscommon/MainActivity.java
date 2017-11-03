package tsou.cn.imagecompresscommon;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import net.bither.util.NativeUtil;

import java.io.IOException;

import tsou.cn.imagecompresscommon.utils.FileUtils;
import tsou.cn.imagecompresscommon.utils.ImageLoadUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageHead;
    private ImageView image;
    public static final int REQUEST_PICK_IMAGE = 11101;
    String[] mPermissionList = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageHead = (ImageView) findViewById(R.id.iv_personal_data_head_round);
        image = (ImageView) findViewById(R.id.iv_personal_data_head);
        imageHead.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ActivityCompat.requestPermissions(MainActivity.this, mPermissionList, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100:
                boolean writeExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0 && writeExternalStorage && readExternalStorage) {
                    getImage();
                } else {
                    Toast.makeText(this, "请设置必要权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void getImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
                    REQUEST_PICK_IMAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_PICK_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PICK_IMAGE:
                    if (data != null) {
                        Uri uri = data.getData();
                        compressImage(uri);
                    } else {
                        Toast.makeText(this, "图片损坏，请重新选择", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    private void compressImage(Uri uri) {
        try {
            final Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            final String path = FileUtils.createFile(this.getApplicationContext(),
                    String.valueOf(System.currentTimeMillis()));
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... voids) {
                    NativeUtil.compressBitmap(bitmap, path);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    ImageLoadUtil.display(MainActivity.this.getApplicationContext(), image, path);
                    ImageLoadUtil.displayCircle(MainActivity.this.getApplicationContext(), imageHead, path);
                }
            }.execute();
            // NativeUtil.compressBitmap(bitmap, path);
            // ImageLoadUtil.display(this.getApplicationContext(), image, path);
            // ImageLoadUtil.displayCircle(this.getApplicationContext(), imageHead, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
