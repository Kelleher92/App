package com.eyes.eyes;

import android.provider.MediaStore;

/**
 * Created by Ian on 04-Feb-16.
 */

interface GetVideoCallback {

    public abstract void done(MediaStore.Video returnedVideo);

}
