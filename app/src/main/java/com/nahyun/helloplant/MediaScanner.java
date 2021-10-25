package com.nahyun.helloplant;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.text.TextUtils;

import androidx.constraintlayout.widget.ConstraintLayout;

public class MediaScanner {
    private Context mContext;
    private static volatile MediaScanner mMediaInstance = null;
    private MediaScannerConnection mMediaScanner;

    private String mFilePath;

    public static MediaScanner getInstance (Context context) {
        if ( null == context) {
            return null;
        }
        if (null == mMediaInstance) {
            mMediaInstance = new MediaScanner(context);
        }
        return mMediaInstance;
    }

    public static void releaseInstace () {
        if (null != mMediaInstance) mMediaInstance = null;
    }

    private MediaScanner(Context context) {
        mContext = context;

        mFilePath = "";

        MediaScannerConnection.MediaScannerConnectionClient mediaScannerConnectionClient;
        mediaScannerConnectionClient = new MediaScannerConnection.MediaScannerConnectionClient() {
            @Override
            public void onMediaScannerConnected() {
                mMediaScanner.scanFile(mFilePath, null);
            }

            @Override
            public void onScanCompleted(String path, Uri uri) {
                System.out.println(":::MediaScan Success:::");
                mMediaScanner.disconnect();
            }
        };
        mMediaScanner = new MediaScannerConnection(mContext, mediaScannerConnectionClient);
    }

    public void mediaScanning (final String path) {
        if (TextUtils.isEmpty(path)) return;
        mFilePath = path;

        if (!mMediaScanner.isConnected()) mMediaScanner.connect();
    }
}
