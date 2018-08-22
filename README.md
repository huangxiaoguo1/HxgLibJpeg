<<<<<<< HEAD
### android-libjpeg压缩图片
#### 引入方式
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

	dependencies {
	        implementation 'com.github.huangxiaoguo1:HxgLibJpeg:1.0.0'
	}
```

### 使用方式

```
 private void compressImage(Uri uri) {
        try {
            final Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            final String path = CompressFileUtils.createFile(this.getApplicationContext(),
                    String.valueOf(System.currentTimeMillis()));
            new AsyncTask<Void, Void, String>() {

                @Override
                protected String doInBackground(Void... voids) {
                    NativeUtil.compressBitmap(bitmap, path);
                    return path;
                }

                @Override
                protected void onPostExecute(String path) {
                    super.onPostExecute(path);
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
```