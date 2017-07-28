package com.example.administrator.kib_3plus.Utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import com.example.administrator.kib_3plus.PublicData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.data;

/**
 * Created by cui on 2017/7/24.
 */

public enum CameraUtils {
    INSTANCE;
    public Activity mActivity;
    public static final int CROP_REQUEST_CODE = 1305;
    public static final int TYPE_TAKE_PHOTO = 1;//Uri获取类型判断
    public static final int CODE_TAKE_PHOTO = 10086;//相机RequestCode
    public static final int CODE_TAKE_PHOTO_IMAGE = 10000;//相机RequestCode
    public String SAVE_IMG_PATH="";
    public String CROPED_FACE_IMG="";

    public void init(Activity activity){
        mActivity=activity;
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "3Plus");
        SAVE_IMG_PATH=mediaStorageDir.getPath();
    }

    public Uri getMediaFileUri(int type){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "3Plus");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        //创建Media File
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == TYPE_TAKE_PHOTO) {
            CROPED_FACE_IMG="IMG_" + timeStamp + ".jpg";
            mediaFile = new File(SAVE_IMG_PATH + File.separator + CROPED_FACE_IMG);
        } else {
            return null;
        }
        return Uri.fromFile(mediaFile);
    }
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
//        Logger.i("", "uri" + uri);
        intent.setDataAndType(uri, "image/*");
//        Logger.i("", "uri" + intent);
//
//        degree= readPictureDegree(uri+"");
//        Logger.i("","degree"+degree);

        // 设置裁剪
//        intent.putExtra("crop", "true");
//        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 120);
//        intent.putExtra("outputY", 120);
        intent.putExtra("return-data", true);
        mActivity.startActivityForResult(intent, CROP_REQUEST_CODE);
    }
    public void getImage(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
//            Bitmap photo = extras.getParcelable("data");//context.getFilesDir().toString();
            Bitmap photo= BitmapFactory.decodeFile(CameraUtils.INSTANCE.SAVE_IMG_PATH+ File.separator +CameraUtils.INSTANCE.CROPED_FACE_IMG);

            File file_phto = new File(SAVE_IMG_PATH, CROPED_FACE_IMG);

            if (file_phto.exists()) {
                file_phto.delete();
            }
            OutputStream stream = null;
            try {
                stream = new FileOutputStream(file_phto);
                photo.compress(Bitmap.CompressFormat.JPEG, 50, stream);
//                ImageUtil.writeToFile(photo, sdCardDirPath + APP_DIR, fileName);
                try {
                    stream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            OutputStream stream = null;
            Bitmap bitmap =null;
            if (!data.getData().toString().equals("") && data.getData().toString() != null) {
                try {
                     bitmap = MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(), data.getData());
                    File file_phto = new File(SAVE_IMG_PATH, CROPED_FACE_IMG);

                    if (file_phto.exists()) {
                        file_phto.delete();
                    }
                    stream = new FileOutputStream(file_phto);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
//                ImageUtil.writeToFile(photo, sdCardDirPath + APP_DIR, fileName);
                    stream.close();

                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    try {
                        if (stream != null) {
                            stream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    //把bitmap转换成String
    public  String bitmapToString(String filePath) {
        Bitmap bm = getSmallBitmap(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        //1.5M的压缩后在100Kb以内，测试得值,压缩后的大小=94486字节,压缩后的大小=74473字节
        //这里的JPEG 如果换成PNG，那么压缩的就有600kB这样
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();
        Log.d("d", "压缩后的大小=" + b.length);
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
    // 根据路径获得图片并压缩，返回bitmap用于显示
    public  Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }
    //计算图片的缩放值
    public  int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     *  改变本地的图片名字与服务器一致方便管理
     */
    public boolean changeIconName(String filePath,String newFilePath){
        File file=new File(filePath);
        File newFsile=new File(newFilePath);
        if (newFsile.exists()) {
            newFsile.delete();
        }
        boolean ret =  file.renameTo(newFsile);
        Log.i("", "Rename---改名成功？ " + ((ret) ? "yes!" : "no!"));
        file=null;
        return ret;
    }

    /**
     * 根据给的url获取图片
     * 首先获取本地的，然后去网络获取
     * 获取网络，缓存在本地
     * 使用完一定要清空bitmap
     * @param url 服务器图片url
     */
    public String getIcon(String url){
        LogUtils.i("getIcon");
        Bitmap bitmap=null;
        String[] urls=url.split("/");
        String fileName=urls[urls.length-1];
       String poth= SAVE_IMG_PATH+File.separator +fileName;
        File icon=new File(poth);
        LogUtils.i("poth="+poth);
        LogUtils.i("url="+url);
        LogUtils.i("fileName="+fileName);

        if(icon.exists()){
            return poth;
        }else{
            //TODO 下载  图片
            return "";
        }
    }
}
