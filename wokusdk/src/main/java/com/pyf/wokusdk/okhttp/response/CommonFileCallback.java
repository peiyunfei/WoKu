package com.pyf.wokusdk.okhttp.response;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.pyf.wokusdk.okhttp.exception.OkHttpException;
import com.pyf.wokusdk.okhttp.listener.DisposeDataHandle;
import com.pyf.wokusdk.okhttp.listener.DisposeDownloadListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 监听文件下载
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/25
 */
public class CommonFileCallback implements Callback {

    // 标记下载进度更新
    private static final int PROGRESS_MESSAGE = 0x01;
    // 网络错误
    private static final int NETWORK_ERROR = -1;
    // 下载失败
    private static final int IO_ERROR = -2;
    private static final String MSG_EMPTY = "";

    // 监听下载进度
    private DisposeDownloadListener mDisposeDownloadListener;
    // 文件下载目录
    private String mFilePath;
    // 文件下载进度
    private int mProgress;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PROGRESS_MESSAGE:
                    if (mDisposeDownloadListener != null) {
                        // 更新进度
                        mDisposeDownloadListener.onProgress((int) msg.obj);
                    }
                    break;
            }
        }
    };

    public CommonFileCallback(DisposeDataHandle disposeDataHandle) {
        mDisposeDownloadListener = (DisposeDownloadListener) disposeDataHandle.mListener;
        mFilePath = disposeDataHandle.mSource;
    }

    /**
     * 请求失败
     */
    @Override
    public void onFailure(Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mDisposeDownloadListener.onFailure(new OkHttpException(NETWORK_ERROR, e));
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final File file = handleResponse(response);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (file == null) {
                    mDisposeDownloadListener.onFailure(new OkHttpException(IO_ERROR, MSG_EMPTY));
                } else {
                    mDisposeDownloadListener.onSuccess(file);
                }
            }
        });
    }

    private File handleResponse(Response response) {
        if (response == null) {
            return null;
        }
        InputStream in = null;
        FileOutputStream fos = null;
        File file = null;
        int length;
        int currentLength = 0;
        long contentLength = response.body().contentLength();
        try {
            checkLocalFilePath();
            file = new File(mFilePath);
            in = response.body().byteStream();
            fos = new FileOutputStream(file);
            byte[] buff = new byte[2048];
            while ((length = in.read(buff)) != -1) {
                fos.write(buff, 0, length);
                currentLength += length;
                mProgress = (int) (currentLength * 100 / contentLength);
                mHandler.obtainMessage(PROGRESS_MESSAGE, mProgress).sendToTarget();
            }
            fos.flush();
        } catch (Exception e) {
            file = null;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private void checkLocalFilePath() {
        File dir = new File(mFilePath.substring(0, mFilePath.lastIndexOf("/") + 1));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(mFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
