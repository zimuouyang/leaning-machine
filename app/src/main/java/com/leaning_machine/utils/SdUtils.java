package com.leaning_machine.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

public class SdUtils {
    /**
     * sd卡获取根目录路径
     * @return /storage/emulated/0/
     */
    public static String getRootPath() {
        return Environment.getExternalStorageDirectory()+ File.separator;
    }
    /**
     * sd卡获取下载路径
     * @return /storage/emulated/0/Download/
     */
    public static String getDownloadPath() {
        return Environment.getExternalStorageDirectory()+ File.separator + Environment.DIRECTORY_DOWNLOADS+ File.separator;
    }

    /**
     * 相机存放图片路径
     * @return /storage/emulated/0/DCIM/Camera/
     */
    public static String getCameraPath(){
        return Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;
    }

    /**
     * Uri photoUri = data.getData();
     * 根据相册返回的Uri获取照片路径
     * @return
     */
    public static String getResultPhotoPath(Context activity, Uri photoUri){
        String[] filePathColumn={MediaStore.Audio.Media.DATA};
        Cursor cursor=activity.getContentResolver().query(photoUri,filePathColumn,null,null,null);
        cursor.moveToFirst();
        String photoPath=cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
        cursor.close();
        return photoPath;
    }


}