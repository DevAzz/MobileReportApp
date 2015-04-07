package ru.kpfu.mobilereportapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Azz on 07.04.2015.
 */
public class ImageCameraHolder {
    private ImageView mImageView;
    private String mCurrentPhotoPath;

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private Activity mActivity;
    private File mFileImage;

    public ImageCameraHolder (Activity activity, ImageView imageView) {
        mActivity = activity;
        mImageView = imageView;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        Log.e("LOG", f.getAbsolutePath());
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = null;
        try {
            f = setUpPhotoFile();
            mCurrentPhotoPath = f.getAbsolutePath();
            mFileImage = new File(mCurrentPhotoPath);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            mCurrentPhotoPath = null;
        }

        mActivity.startActivityForResult(takePictureIntent, 0);
    }

    public void handleBigCameraPhoto(){

        if (mCurrentPhotoPath != null) {
            setPic();
            galleryAddPic();
            mCurrentPhotoPath = null;
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private Uri galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(mFileImage);
        mediaScanIntent.setData(contentUri);
        mActivity.sendBroadcast(mediaScanIntent);
        return contentUri;
    }

    private void setPic() {
        Picasso.with(mActivity).load(mFileImage).resizeDimen(R.dimen.image_size_w, R.dimen.image_size_h).into(mImageView);
    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(mActivity.getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    /* Photo album for this application */
    private String getAlbumName() {
        return mActivity.getString(R.string.album_name);
    }

    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /**
     * Метод, устанавливающий слушатель кнопке
     * @param btn кнопка
     * @param onClickListener слушатель
     * @param intentName Имя намерения
     */
    private void setBtnListenerOrDisable(
            Button btn,
            Button.OnClickListener onClickListener,
            String intentName
    ) {
        if (isIntentAvailable(mActivity, intentName)) {
            btn.setOnClickListener(onClickListener);
        } else {
            btn.setText(
                    mActivity.getText(R.string.cannot).toString() + " " + btn.getText());
            btn.setClickable(false);
        }
    }

}
