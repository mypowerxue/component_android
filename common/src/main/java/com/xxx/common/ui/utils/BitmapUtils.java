package com.xxx.common.ui.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.provider.MediaStore;
import android.util.Base64;

import com.xxx.common.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapUtils {

    /**
     * 采样率压缩：
     * 按照指定 宽高压缩图片
     * 默认 780 * 460
     */
    public static Bitmap loadBitmap(InputStream is, int width, int height) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 5];
        int length = 0;
        while ((length = is.read(buffer)) != -1) {
            os.write(buffer, 0, length);
            os.flush();
        }
        byte[] bytes = os.toByteArray();
        is.close();
        os.close();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
        int w = opts.outWidth / width;
        int h = opts.outHeight / height;
        int scale = w > h ? w : h;
        if (scale < 1) {
            scale = 1;
        }
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = scale;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
    }

    /**
     * 将指定bitmap对象存到文件中
     */
    public static void saveBitmapToFile(Activity context, Bitmap bitmap, File file) throws IOException {
        if (PermissionUtil.checkPermission(context,
                PermissionUtil.READ_PERMISSION,  //读写权限
                PermissionUtil.WRITE_PERMISSION)) {
            return;
        }

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, file.getName(), null);
            ToastUtil.showToast(context, R.string.save_image_success);
        } catch (Exception e) {
            ToastUtil.showToast(context, R.string.save_image_fail);
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Matrix matrix = new Matrix();
            float scaleWidth = ((float) w / width);
            float scaleHeight = ((float) h / height);
            matrix.postScale(scaleWidth, scaleHeight);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            return bitmap;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 转换为base64格式
     */
    public static String imgToBase64(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] imgBytes = out.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.NO_WRAP);//Android 类库
//        return BASE64Encoder.encode(imgBytes);// 解决换行问题
    }


}