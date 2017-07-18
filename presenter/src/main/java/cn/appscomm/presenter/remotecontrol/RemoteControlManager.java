package cn.appscomm.presenter.remotecontrol;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.text.TextUtils;

import java.util.List;

import cn.appscomm.bluetooth.implement.RemoteControlSend;
import cn.appscomm.bluetooth.interfaces.IRemoteControlCommand;
import cn.appscomm.presenter.PresenterAppContext;
import cn.appscomm.presenter.implement.PBluetooth;
import cn.appscomm.presenter.interfaces.IRemoteControl;
import cn.appscomm.presenter.interfaces.PVBluetoothCall;
import cn.appscomm.presenter.mode.Notifications;
import cn.appscomm.presenter.remotecontrol.music.MusicControl;
import cn.appscomm.presenter.remotecontrol.music.MusicControlEx;
import cn.appscomm.presenter.util.LogUtil;

/**
 * 作者：hsh
 * 日期：2017/4/26
 * 说明：远程命令管理者
 * 1、负责远程音乐、拍照、寻找手机的发送
 * 2、负责解析设备发送的远程命令
 */

public enum RemoteControlManager {
    INSTANCE;

    private static final String TAG = RemoteControlManager.class.getSimpleName();
    private PVBluetoothCall pvBluetoothCall = PBluetooth.INSTANCE;
    private AudioManager mAudioManager;
    private int alarmType = -1;
    private MediaPlayer mediaPlayer = null;
    private boolean isPlayRingFlag = false;
    private String songName = "";
    private boolean isUpdateSongNameFlag = false;
    private int lastVolume = 0;
    private IRemoteControl iRemoteControl;

    /**
     * 初始化(应用开始运行时需要先进行初始化)
     */
    public void init() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            MusicControlEx.INSTANCE.init();
        } else {
            MusicControl.INSTANCE.init();
        }
        RemoteControlSend.INSTANCE.init(iRemoteControlCommand);
        mAudioManager = (AudioManager) PresenterAppContext.INSTANCE.getContext().getSystemService(Context.AUDIO_SERVICE);
        registerVolumeReceiver();
    }

    /**
     * 注册拍照
     *
     * @param iRemoteControl UI需实现该接口，然后给引用
     */
    public void registerRemoteTakePhoto(IRemoteControl iRemoteControl) {
        this.iRemoteControl = iRemoteControl;
    }

    // 注册音量接收者
    private void registerVolumeReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        PresenterAppContext.INSTANCE.getContext().registerReceiver(new VolumeReceiver(), filter);
    }

    // 音量接收者
    private class VolumeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
                sendVolumes();
            }
        }
    }

    /**
     * 解释歌曲名
     *
     * @param notifications 传入自定义的通知消息
     */
    public void parseSongName(Notifications notifications) {
        if (TextUtils.isEmpty(notifications.content) && TextUtils.isEmpty(notifications.name))
            return;
        songName = TextUtils.isEmpty(notifications.content) ? notifications.name : notifications.content;
        switch (notifications.packageName) {
            case "com.google.android.music":                                                        // 谷歌音乐
                songName = notifications.name;
                break;
            case "com.tencent.qqmusic":                                                             // QQ音乐
            case "com.kugou.android":                                                               // 酷狗音乐
                songName = notifications.content;
                break;
        }
        if (isUpdateSongNameFlag) {
            isUpdateSongNameFlag = false;
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                boolean musicStatus = MusicControlEx.INSTANCE.checkMusicState();
                LogUtil.i(TAG, "准备更新歌曲...musicStatus : " + musicStatus + " 歌曲名 : " + songName);
                sendSongName(musicStatus, songName);
            } else {

            }
        }
    }

    // 蓝牙发送过来的远程命令
    private IRemoteControlCommand iRemoteControlCommand = new IRemoteControlCommand() {

    /*---------------------------------------------------音乐-------------------------------------------------*/

        @Override
        public void checkMusicStatus() {
            LogUtil.i(TAG, "音乐管理者:查询音乐状态");
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                boolean musicStatus = MusicControlEx.INSTANCE.checkMusicState();
                sendSongName(musicStatus, songName);
            } else {
                MusicControl.INSTANCE.checkMusicStatus();
            }
        }

        @Override
        public void setPhoneNextSong() {
            LogUtil.i(TAG, "音乐管理者:设置手机下一曲");
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                MusicControlEx.INSTANCE.nextSong();
            } else {
                MusicControl.INSTANCE.nextSong();
            }
        }

        @Override
        public void setPhonePreSong() {
            LogUtil.i(TAG, "音乐管理者:设置手机上一曲");
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                MusicControlEx.INSTANCE.preSong();
            } else {
                MusicControl.INSTANCE.preSong();
            }
        }

        @Override
        public void setPhonePlay() {
            LogUtil.i(TAG, "音乐管理者:设置手机播放");
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                MusicControlEx.INSTANCE.playSong();
            } else {
                MusicControl.INSTANCE.playSong();
            }
        }

        @Override
        public void setPhonePause() {
            LogUtil.i(TAG, "音乐管理者:设置手机暂停");
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                MusicControlEx.INSTANCE.pauseSong();
            } else {
                MusicControl.INSTANCE.pauseSong();
            }
        }

        @Override
        public void setPhoneVolumes(int volumes) {
            int phoneMaxValue = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            int value = phoneMaxValue * volumes / 100;
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, value, AudioManager.FLAG_SHOW_UI);
        }

        /*---------------------------------------------------拍照-------------------------------------------------*/

        @Override
        public void startTakePhoto() {
            if (iRemoteControl != null)
                iRemoteControl.startTakePhoto();
        }

        @Override
        public void endTakePhoto() {
            if (iRemoteControl != null)
                iRemoteControl.endTakePhoto();
        }

        /*-------------------------------------------------寻找手机-----------------------------------------------*/

        @Override
        public void startFindPhone() {
            try {
                moveTaskToFront();
                Vibrator vibrator = (Vibrator) PresenterAppContext.INSTANCE.getContext().getSystemService(Service.VIBRATOR_SERVICE);
                vibrator.vibrate(1500);
                alarmType = RingtoneManager.TYPE_ALARM;
                playAlarm();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void endFindPhone() {
            isPlayRingFlag = false;
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
        }

    };

    /**
     * 更新歌曲名
     */
    public void updateSongName() {
        isUpdateSongNameFlag = true;
    }

    /**
     * 发送歌曲名
     *
     * @param musicState 歌曲状态
     * @param songName   歌曲名
     */
    public void sendSongName(boolean musicState, String songName) {
        pvBluetoothCall.sendSongName(musicState, songName);
    }

    /**
     * 发送播放或暂停
     *
     * @param musicState true：播放 false：暂停
     */
    public void sendPlayPause(boolean musicState) {
        pvBluetoothCall.sendSongName(musicState, songName);
    }

    /**
     * 发送停止播放
     */
    public void sendStop() {
        pvBluetoothCall.sendStop();
    }

    /**
     * 发送音量
     */
    public void sendVolumes() {
        int phoneMaxValue = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int phoneCurValue = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int value = phoneCurValue * 100 / phoneMaxValue;
        if (lastVolume != value)
            pvBluetoothCall.sendVolume(value);
        lastVolume = value;
    }

    // app从后台切换到前台
    private void moveTaskToFront() {
        ActivityManager am = (ActivityManager) PresenterAppContext.INSTANCE.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo rti : taskList) {
            if (rti.topActivity.getPackageName().equals(PresenterAppContext.INSTANCE.getContext().getPackageName())) {
                LogUtil.i(TAG, "app在后台,移到前台...");
                am.moveTaskToFront(rti.id, 0);
                return;
            }
        }
        LogUtil.i(TAG, "在前台，不需要。。。");
    }

    // 播放铃声
    private void playAlarm() {
        try {
            if (!isPlayRingFlag && alarmType != -1) {                                               // 如果不在播放铃声，则播放
                LogUtil.i(TAG, "准备播放铃声");
                Uri uri = RingtoneManager.getActualDefaultRingtoneUri(PresenterAppContext.INSTANCE.getContext(), alarmType);
                mediaPlayer = MediaPlayer.create(PresenterAppContext.INSTANCE.getContext(), uri);
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                isPlayRingFlag = true;
//                AlertDialog.Builder builder = new AlertDialog.Builder(PresenterAppContext.INSTANCE.getContext());
//                builder.setMessage(R.string.s_otherfunction_close_ring);
//                builder.setPositiveButton(context.getString(android.R.string.yes), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        stopVibratorAlarm();
//                    }
//                });
//                dialog = builder.create();
//                dialog.show();
            } else {
//                if (dialog != null) {
//                    dialog.show();
//                }
            }
        } catch (Exception e) {
            LogUtil.i(TAG, "播放音乐异常...");
            if (alarmType == RingtoneManager.TYPE_ALARM) {
                alarmType = RingtoneManager.TYPE_RINGTONE;
                playAlarm();
            } else {
                e.printStackTrace();
            }
        }
    }
}
