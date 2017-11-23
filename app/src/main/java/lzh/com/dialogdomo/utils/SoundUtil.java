package lzh.com.dialogdomo.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.SparseArray;

/**
 * 使用SoundPool播放简单时间短的音乐
 */

public class SoundUtil implements SoundPool.OnLoadCompleteListener {

    private static SoundUtil mUtil = null;

    public static SoundUtil getInstance() {
        if (mUtil == null) {
            mUtil = new SoundUtil();
        }
        return mUtil;
    }

    public static void closeInstance(){
        if (mUtil != null){
            mUtil.Close();
            mUtil = null;
        }
    }

    private SoundPool mSoundPool = null;
    private SparseArray<SoundPoolInfo> mSparseArray = null;
    private int LoadingID = 0;

    private SoundUtil() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            SoundPool.Builder SoundBuilder = new SoundPool.Builder();
            AudioAttributes.Builder AudioBuilder = new AudioAttributes.Builder();
            AudioBuilder.setLegacyStreamType(AudioManager.STREAM_RING);
            mSoundPool = SoundBuilder
                    .setMaxStreams(2)
                    .setAudioAttributes(AudioBuilder.build())
                    .build();
        } else {
            mSoundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        }
        mSoundPool.setOnLoadCompleteListener(this);
        mSparseArray = new SparseArray<>();
    }

    public boolean Play(Context context, int id, int times) {

        if (LoadingID != 0){
            return false;
        }
        SoundPoolInfo info = mSparseArray.get(id);
        if (info == null) {
            final int SoundID = mSoundPool.load(context, id, -1);
            if (SoundID == 0) {
                return false;
            }
            LoadingID = id;
            info = new SoundPoolInfo();
            info.SoundID = SoundID;
            info.Times = times;
            mSparseArray.put(LoadingID, info);
        } else {
            PlaySound(id,times);
        }
        return true;
    }


    private void PlaySound(int id) {
        SoundPoolInfo info = mSparseArray.get(id);
        PauseSound(id);
        info.StreamID = mSoundPool.play(info.SoundID, 1, 1, 0, info.Times > 0 ? info.Times - 1 : -1, 0.8f);
    }
    private void PlaySound(int id,int times) {
        SoundPoolInfo info = mSparseArray.get(id);
        PauseSound(id);
        info.StreamID = mSoundPool.play(info.SoundID, 1, 1, 0, times-1, 0.8f);
    }

    public void PauseSound(int id) {
        try {
            SoundPoolInfo info = mSparseArray.get(id);
            if (info == null){
                return;
            }
            mSoundPool.stop(info.StreamID);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void Close() {
        if (mSoundPool != null) {
            mSoundPool.autoPause();
            mSoundPool.release();
            mSoundPool = null;
        }
        mSparseArray.clear();
        mSparseArray = null;

    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        if (status == 0){
            PlaySound(LoadingID);
        }else {
            mSparseArray.remove(LoadingID);
        }
        LoadingID = 0;
    }

    private class SoundPoolInfo {
        int SoundID = 0;
        int StreamID = 0;
        int Times = 1;
    }
}
