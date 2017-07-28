package cn.appscomm.presenter.implement;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cn.appscomm.bluetooth.BluetoothVar;
import cn.appscomm.bluetooth.implement.MBluetooth;
import cn.appscomm.bluetooth.interfaces.IBluetoothResultCallback;
import cn.appscomm.bluetooth.interfaces.PMBluetoothCall;
import cn.appscomm.bluetooth.mode.ReminderBT;
import cn.appscomm.db.mode.ReminderDB;
import cn.appscomm.ota.util.OtaUtil;
import cn.appscomm.presenter.BindDevice;
import cn.appscomm.presenter.SyncBluetoothData;
import cn.appscomm.presenter.interfaces.PVBluetoothCall;
import cn.appscomm.presenter.interfaces.PVBluetoothCallback;
import cn.appscomm.presenter.interfaces.PVDBCall;
import cn.appscomm.presenter.interfaces.PVSPCall;
import cn.appscomm.presenter.util.ModeConvertUtil;

/**
 * 作者：hsh
 * 日期：2017/3/6
 * 说明：蓝牙P
 * 1、主要是实现PVBluetoothCall接口的所有方法
 * 2、还有一些逻辑功能的处理
 */
public enum PBluetooth implements PVBluetoothCall {
    INSTANCE;

    private static final String TAG = PBluetooth.class.getSimpleName();
    private PMBluetoothCall mCall = MBluetooth.INSTANCE;                                            // 用户调用M层的方法
    private PVSPCall pvspCall = PSP.INSTANCE;
    private PVDBCall pvdbCall = PDB.INSTANCE;
    private SimpleDateFormat smsDataTimeSDF = new SimpleDateFormat("yyyyMMdd'T'HHmmss");            // SMS的时间格式



    /*-------------------------------------------P层内部调用及逻辑实现 L28T-------------------------------------------*/

    /**
     * 获取软件版本
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void getSoftwareVersion(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.getSoftwareVersion(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_SOFTWARE_VERSION;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
//                    pvspCall.setSoftVersion(bluetoothVar.softVersionL28T);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.softVersionL28T}, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }

    /**
     * 28T获取watchid
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void getWatchIDL28T(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.getWatchIDL28T(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_WATCH_ID;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setWatchIDL28T(bluetoothVar.watchIDL28T);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.watchIDL28T}, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }

    /**
     * 设置uuid
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void setUIDL28T(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.bindUID(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_BIND_UID;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
//                    pvspCall.setWatchIDL28T(bluetoothVar.watchIDL28T);
                    onSuccessCallBack(callback, null, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }
    /**
     * 设置uuid
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void setName(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.setName(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_SET_NAME_DATA;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
//                    pvspCall.setWatchIDL28T(bluetoothVar.watchIDL28T);
                    onSuccessCallBack(callback, null, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }
    /**
     * 设置个人信息
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void bindInfo(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.setBaseInfo(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_BIND_INFO;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
//                    pvspCall.setWatchIDL28T(bluetoothVar.watchIDL28T);
                    onSuccessCallBack(callback, null, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }

    /**
     * 28T设置时间
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void setBaseTime(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.setBaseTime(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_BIND_TIME;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
//                    pvspCall.setWatchIDL28T(bluetoothVar.watchIDL28T);
                    onSuccessCallBack(callback, null, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }

    /**
     * 28T设置删除
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void setDel(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.setDel(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_SET_DEL;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    onSuccessCallBack(callback, null, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }
    /**
     * 28T设置金币
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void setGold(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.setGold(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_SET_GOLD;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    onSuccessCallBack(callback, null, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }
    /**
     * 28T获取电量
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void getPower(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.getPower(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_GET_POWER;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setpower28T(bluetoothVar.batteryPowerL28T);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.batteryPowerL28T}, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }

    /**
     * 28T获取运动总数
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void getStepTotal(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.getStepTotal(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_GET_STEP_COUNT;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    //不做sp的保存这个只是一个临时数值
//                    pvspCall.setpower28T(bluetoothVar.sportCountL28T);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.sportCountL28T}, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }
    /**
     * 28T获取睡眠数据条数
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void getSleepTotal(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.getSleepTotal(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_SET_SLEEP_COUNT;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    //不做sp的保存这个只是一个临时数值
//                    pvspCall.setpower28T(bluetoothVar.sportCountL28T);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.sleepCountL28T}, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }
    /**
     * 28t获取睡眠状态
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void getSleepState(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.getSleepState(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_GET_SLEEP_MODEL;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    //不做sp的保存这个只是一个临时数值
//                    pvspCall.setpower28T(bluetoothVar.sportCountL28T);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.sportSleepModeL28T}, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }
    /**
     * 28t获取睡眠详细数据
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void getSleepDataL28T(final PVBluetoothCallback callback, int commandType,byte[] content,int len, String... macs) {
        mCall.getSleepDataL28T(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_SET_SLEEP_DATA;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    //不做sp的保存这个只是一个临时数值
//                    pvspCall.setpower28T(bluetoothVar.sportCountL28T);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.sleepBTDataListL28T}, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content,len, macs);
    }
    /**
     * 28t获取运动详细数据
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void getStepData(final PVBluetoothCallback callback, int commandType,byte[] content,int len, String... macs) {
        mCall.getStepData(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_GET_STEP_DATA;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    //不做sp的保存这个只是一个临时数值
//                    pvspCall.setpower28T(bluetoothVar.sportCountL28T);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.sportBTDataListL28T}, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content,len, macs);
    }
    /**
     * 删除命令
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void delStepData(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.delStepData(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_DELETE_STEP_DATA;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    //不做sp的保存这个只是一个临时数值
//                    pvspCall.setpower28T(bluetoothVar.sportCountL28T);
                    onSuccessCallBack(callback, null, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }

    /**
     * 删除命令
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void delSleepDataL28T(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.delSleepData(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_SET_DEL_SLEEP_DATA;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    //不做sp的保存这个只是一个临时数值
//                    pvspCall.setpower28T(bluetoothVar.sportCountL28T);
                    onSuccessCallBack(callback, null, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }
    /**
     * 设置震动强度
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void setVibration(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.setVibration(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_VIBRATION_DATA;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    onSuccessCallBack(callback, null, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }
    /**
     * 设置查找手机
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void setFindDevice(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.setFindDevice(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_SET_FIND_DEVICE;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    onSuccessCallBack(callback, null, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }
    /**
     * 设置预设睡眠
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void setPresetSleep(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.setSleepSwitch(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_SET_PRESET_SLEEP;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    onSuccessCallBack(callback, null, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }
    /**
     * 设置时间格式
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void setTimeFormat(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.setTimeFormat(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_TIME_FORMAT_DATA;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    onSuccessCallBack(callback, null, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }
    /**
     * 设置时间格式
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void setReset(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.setReset(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_SET_RESET;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    onSuccessCallBack(callback, null, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }

    /**
     * 设置久坐提醒
     * @param callback
     * @param commandType
     * @param content
     * @param macs
     */
    public void setAlert(final PVBluetoothCallback callback, int commandType,byte[] content, String... macs) {
        mCall.setAlert(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.L28T_SET_ALERT;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    onSuccessCallBack(callback, null, 1
                            , PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType,true,content, macs);
    }

    /*-----------------------------------------------蓝牙服务相关-----------------------------------------------*/
    @Override
    public void startService() {
        mCall.startService();
    }

    @Override
    public boolean resetService() {
        String mac = pvspCall.getMAC();
        boolean isSupportHeartRate = pvspCall.getHeartRateFunction();
        boolean isSend03=pvspCall.getIsSend03();
        return !TextUtils.isEmpty(mac) && reConnect(mac, isSupportHeartRate,isSend03);
    }

    @Override
    public boolean reConnect(String mac, boolean isSupportHeartRate,boolean isSend03) {
        return mCall.reConnect(mac, isSupportHeartRate, isSend03);
    }

    @Override
    public void endService() {
        mCall.endService();
    }

    @Override
    public boolean isBluetoothLeServiceRunning() {
        return mCall.isBluetoothLeServiceRunning();
    }

    @Override
    public void connect() {
        String mac = pvspCall.getMAC();
        boolean isSupportHeartRate = pvspCall.getHeartRateFunction();
        boolean isSend03=pvspCall.getIsSend03();
        if (TextUtils.isEmpty(mac)) return;
        connect(mac, isSupportHeartRate,isSend03);
    }

    @Override
    public void disConnect() {
        String mac = pvspCall.getMAC();
        if (TextUtils.isEmpty(mac)) return;
        mCall.disConnect(mac);
    }

    @Override
    public void disConnect(String mac) {
        mCall.disConnect(mac);
    }

    @Override
    public void connect(String mac, boolean isSupportHeartRate,boolean isSend03) {
        mCall.connect(mac, isSupportHeartRate,isSend03);
    }

    @Override
    public boolean isConnect() {
        String mac = pvspCall.getMAC();
        return !TextUtils.isEmpty(mac) && isConnect(mac);
    }

    @Override
    public boolean isConnect(String mac) {
        return mCall.isConnect(mac);
    }

    /*-------------------------------------------------V层调用-------------------------------------------------*/
    @Override
    public void getWatchID(final PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getWatchID(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getDeviceVersion(final PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getDeviceVersion(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getDateTime(final PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getDateTime(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setDateTime(final PVBluetoothCallback callback, int year, int month, int day, int hour, int min, int sec, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setDateTime(callback, year, month, day, hour, min, sec, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getTimeSurfaceSetting(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getTimeSurfaceSetting(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setTimeSurfaceSetting(PVBluetoothCallback callback, int dateFormat, int timeFormat, int batteryFormat, int lunarFormat, int screenFormat, int backgroundStyle, int sportDataFormat, int usernameFormat, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setTimeSurfaceSetting(callback, dateFormat, timeFormat, batteryFormat, lunarFormat, screenFormat, backgroundStyle, sportDataFormat, usernameFormat, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getScreenBrightness(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getScreenBrightness(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setScreenBrightness(PVBluetoothCallback callback, int brightnessValue, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setScreenBrightness(callback, brightnessValue, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getBatteryPower(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getBatteryPower(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getVolume(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getVolume(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setVolume(PVBluetoothCallback callback, int volume, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setVolume(callback, volume, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getShockMode(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getShockMode(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setShockMode(PVBluetoothCallback callback, int shockType, int shockMode, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setShockMode(callback, shockType, shockMode, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getLanguage(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getLanguage(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setLanguage(PVBluetoothCallback callback, int language, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setLanguage(callback, language, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getUnit(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getUnit(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setUnit(PVBluetoothCallback callback, int unit, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setUnit(callback, unit, PMBluetoothCall.COMMAND_TYPE_BIND, macs);
    }

    @Override
    public void restoreFactory(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        restoreFactory(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void enterUpdateMode(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        enterUpdateMode(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void enterUpdateMode(String touchPanelPath, String heartRatePath, String freescalePath, PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        enterUpdateMode(touchPanelPath, heartRatePath, freescalePath, callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getShockStrength(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getShockStrength(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setShockStrength(PVBluetoothCallback callback, int shockStrength, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setShockStrength(callback, shockStrength, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getWorkMode(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getWorkMode(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setWorkMode(PVBluetoothCallback callback, int workMode, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setWorkMode(callback, workMode, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getBrightScreenTime(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getBrightScreenTime(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setBrightScreenTime(PVBluetoothCallback callback, int brightScreenTime, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setBrightScreenTime(callback, brightScreenTime, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getUserInfo(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getUserInfo(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setUserInfo(PVBluetoothCallback callback, int sex, int age, int height, int weight, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setUserInfo(callback, sex, age, height, weight, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getUsageHabits(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getUsageHabits(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setUsageHabits(PVBluetoothCallback callback, int usageHabits, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setUsageHabits(callback, usageHabits, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getUserName(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getUserName(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setUserName(PVBluetoothCallback callback, String userName, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setUserName(callback, userName, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getGoal(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getGoal(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setStepGoal(PVBluetoothCallback callback, int stepGoal, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setStepGoal(callback, stepGoal, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setCaloriesGoal(PVBluetoothCallback callback, int caloriesGoal, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setCaloriesGoal(callback, caloriesGoal, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setDistanceGoal(PVBluetoothCallback callback, int sportTimeGoal, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setDistanceGoal(callback, sportTimeGoal, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setSportTimeGoal(PVBluetoothCallback callback, int sportTimeGoal, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setSportTimeGoal(callback, sportTimeGoal, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setSleepGoal(PVBluetoothCallback callback, int sleepGoal, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setSleepGoal(callback, sleepGoal, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getSportSleepMode(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getSportSleepMode(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getAllDataTypeCount(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getAllDataTypeCount(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void deleteSportData(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        deleteSportData(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getSportData(PVBluetoothCallback callback, int sportDataCount, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getSportData(callback, sportDataCount, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void deleteSleepData(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        deleteSleepData(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getSleepData(PVBluetoothCallback callback, int sleepDataCount, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getSleepData(callback, sleepDataCount, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getDeviceDisplay(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getDeviceDisplay(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getAutoSleep(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getAutoSleep(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setAutoSleep(PVBluetoothCallback callback, int enterHour, int enterMin, int quitHour, int quitMin, int cycle, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setAutoSleep(callback, enterHour, enterMin, quitHour, quitMin, cycle, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getHeartRateCount(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getHeartRateCount(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void deleteHeartRateData(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        deleteHeartRateData(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getHeartRateData(PVBluetoothCallback callback, int heartRateCount, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getHeartRateData(callback, heartRateCount, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getAutoHeartRateFrequency(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getAutoHeartRateFrequency(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setAutoHeartRateFrequency(PVBluetoothCallback callback, int heartRateFrequency, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setAutoHeartRateFrequency(callback, heartRateFrequency, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getHeartRateAlarmThreshold(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getHeartRateAlarmThreshold(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setHeartRateAlarmThreshold(PVBluetoothCallback callback, int heartRateAlarmSwitch, int heartRateMinValue, int heartRateMaxValue, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setHeartRateAlarmThreshold(callback, heartRateAlarmSwitch, heartRateMinValue, heartRateMaxValue, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getInactivityAlert(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getInactivityAlert(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setInactivityAlert(PVBluetoothCallback callback, int isOpen, int cycle, int interval, int startHour, int startMin, int endHour, int endMin, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setInactivityAlert(callback, isOpen, cycle, interval, startHour, startMin, endHour, endMin, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getCaloriesType(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getCaloriesType(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setCaloriesType(PVBluetoothCallback callback, boolean enable, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setCaloriesType(callback, enable, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getHeartRateDataEx(PVBluetoothCallback callback, int heartRateDataCount, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getHeartRateDataEx(callback, heartRateDataCount, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void sendIncomeCall(PVBluetoothCallback callback, String nameOrNumber, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        int commandType = PMBluetoothCall.COMMAND_TYPE_CALL;
        sendIncomeMissCall(callback, nameOrNumber, PMBluetoothCall.PHONE_NAME_PUSH_TYPE_INCOME_CALL, PVBluetoothCallback.BluetoothCommandType.SEND_INCOME_CALL_NAME_OR_NUMBER, commandType, macs);
        sendMessageCount(callback, 1, PVBluetoothCallback.BluetoothCommandType.SEND_INCOME_CALL_COUNT, PMBluetoothCall.MSG_PUSH_TYPE_INCOME_CALL, PMBluetoothCall.COMMAND_TYPE_CALL, macs);
    }

    @Override
    public void sendOffCall(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        sendMessageCount(callback, 1, PVBluetoothCallback.BluetoothCommandType.SEND_OFF_HOOK, PMBluetoothCall.MSG_PUSH_TYPE_OFF_CALL, PMBluetoothCall.COMMAND_TYPE_CALL, macs);
    }

    @Override
    public void sendMissCall(PVBluetoothCallback callback, String nameOrNumber, Date dateTime, int missCallCount, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        String sDateTime = smsDataTimeSDF.format(dateTime);
        int commandType = PMBluetoothCall.COMMAND_TYPE_CALL;
        sendIncomeMissCall(callback, nameOrNumber, PMBluetoothCall.PHONE_NAME_PUSH_TYPE_MISS_CALL, PVBluetoothCallback.BluetoothCommandType.SEND_MISS_CALL_NAME_OR_NUMBER, commandType, macs);
        sendIncomeMissCall(callback, sDateTime, PMBluetoothCall.PHONE_NAME_PUSH_TYPE_MISS_CALL_DATETIME, PVBluetoothCallback.BluetoothCommandType.SEND_MISS_CALL_DATETIME, commandType, macs);
        sendMessageCount(callback, missCallCount, PVBluetoothCallback.BluetoothCommandType.SEND_MISS_CALL_COUNT, PMBluetoothCall.MSG_PUSH_TYPE_MISS_CALL, PMBluetoothCall.COMMAND_TYPE_CALL, macs);
    }

    @Override
    public void sendSMS(PVBluetoothCallback callback, String name, String content, Date dateTime, int smsCount, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        String sDateTime = smsDataTimeSDF.format(dateTime);
        int smsNotifyType = PMBluetoothCall.SMS_NOTIFY_TYPE_SMS;
        int commandType = PMBluetoothCall.COMMAND_TYPE_NOTIFY;
        sendSMSAndNotify(callback, name, PMBluetoothCall.SMS_PUSH_TYPE_NAME_OR_NUMBER, PVBluetoothCallback.BluetoothCommandType.SEND_SMS_NAME_OR_NUMBER, smsNotifyType, commandType, macs);
        sendSMSAndNotify(callback, content, PMBluetoothCall.SMS_PUSH_TYPE_CONTENT, PVBluetoothCallback.BluetoothCommandType.SEND_SMS_CONTENT, smsNotifyType, commandType, macs);
        sendSMSAndNotify(callback, sDateTime, PMBluetoothCall.SMS_PUSH_TYPE_DATETIME, PVBluetoothCallback.BluetoothCommandType.SEND_SMS_DATETIME, smsNotifyType, commandType, macs);
        sendMessageCount(callback, smsCount, PVBluetoothCallback.BluetoothCommandType.SEND_SMS_COUNT, PMBluetoothCall.MSG_PUSH_TYPE_SMS, commandType, macs);
    }

    @Override
    public void sendSocial(PVBluetoothCallback callback, String title, String content, Date dateTime, int socialType, int socialCount, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        String sDateTime = smsDataTimeSDF.format(dateTime);
        int smsNotifyType = PMBluetoothCall.SMS_NOTIFY_TYPE_SOCIAL;
        int commandType = PMBluetoothCall.COMMAND_TYPE_NOTIFY;
        sendSMSAndNotify(callback, title, PMBluetoothCall.SOCIAL_PUSH_TYPE_TITLE, PVBluetoothCallback.BluetoothCommandType.SEND_SOCIAL_TITLE, smsNotifyType, commandType, macs);
        sendSMSAndNotify(callback, content, PMBluetoothCall.SOCIAL_PUSH_TYPE_CONTENT, PVBluetoothCallback.BluetoothCommandType.SEND_SOCIAL_CONTENT, smsNotifyType, commandType, macs);
        sendSMSAndNotify(callback, sDateTime, PMBluetoothCall.SOCIAL_PUSH_TYPE_DATETIME, PVBluetoothCallback.BluetoothCommandType.SEND_SOCIAL_DATETIME, smsNotifyType, commandType, macs);
        sendMessageCount(callback, socialCount, PVBluetoothCallback.BluetoothCommandType.SEND_SOCIAL_COUNT, socialType, PMBluetoothCall.COMMAND_TYPE_NOTIFY, macs);
    }

    @Override
    public void sendEmail(PVBluetoothCallback callback, String email, String content, Date dateTime, int emailCount, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        if (!TextUtils.isEmpty(email)) {
            String sDateTime = smsDataTimeSDF.format(dateTime);
            int smsNotifyType = PMBluetoothCall.SMS_NOTIFY_TYPE_EMAIL;
            int commandType = PMBluetoothCall.COMMAND_TYPE_NOTIFY;
            sendSMSAndNotify(callback, email, PMBluetoothCall.EMAIL_PUSH_TYPE_ADDRESS, PVBluetoothCallback.BluetoothCommandType.SEND_EMAIL_ADDRESS, smsNotifyType, commandType, macs);
            sendSMSAndNotify(callback, content, PMBluetoothCall.EMAIL_PUSH_TYPE_CONTENT, PVBluetoothCallback.BluetoothCommandType.SEND_EMAIL_CONTENT, smsNotifyType, commandType, macs);
            sendSMSAndNotify(callback, sDateTime, PMBluetoothCall.EMAIL_PUSH_TYPE_DATETIME, PVBluetoothCallback.BluetoothCommandType.SEND_EMAIL_DATETIME, smsNotifyType, commandType, macs);
        }
        sendMessageCount(callback, emailCount, PVBluetoothCallback.BluetoothCommandType.SEND_EMAIL_COUNT, PMBluetoothCall.MSG_PUSH_TYPE_EMAIL, PMBluetoothCall.COMMAND_TYPE_NOTIFY, macs);
    }

    @Override
    public void sendSchedule(PVBluetoothCallback callback, String content, Date dateTime, int scheduleCount, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        if (!TextUtils.isEmpty(content)) {
            String sDateTime = smsDataTimeSDF.format(dateTime);
            int smsNotifyType = PMBluetoothCall.SMS_NOTIFY_TYPE_EMAIL;
            int commandType = PMBluetoothCall.COMMAND_TYPE_NOTIFY;
            sendSMSAndNotify(callback, content, PMBluetoothCall.SCHEDULE_PUSH_TYPE_CONTENT, PVBluetoothCallback.BluetoothCommandType.SEND_SCHEDULE_CONTENT, smsNotifyType, commandType, macs);
            sendSMSAndNotify(callback, sDateTime, PMBluetoothCall.SCHEDULE_PUSH_TYPE_DATETIME, PVBluetoothCallback.BluetoothCommandType.SEND_SCHEDULE_DATETIME, smsNotifyType, commandType, macs);
        }
        sendMessageCount(callback, scheduleCount, PVBluetoothCallback.BluetoothCommandType.SEND_SCHEDULE_COUNT, PMBluetoothCall.MSG_PUSH_TYPE_CALENDAR, PMBluetoothCall.COMMAND_TYPE_NOTIFY, macs);
    }

    @Override
    public void getSwitchSetting(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getSwitchSetting(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void setSwitchSetting(PVBluetoothCallback callback, int switchType, boolean enable, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setSwitchSetting(callback, switchType, enable, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getReminderCount(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getReminderCount(callback, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getReminder(PVBluetoothCallback callback, int reminderCount, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getReminder(callback, reminderCount, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void addReminder(PVBluetoothCallback callback, ReminderBT reminderBT, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setReminder(callback, 0, null, reminderBT, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void deleteReminder(PVBluetoothCallback callback, ReminderBT reminderBT, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        int type = reminderBT == null ? 3 : 2;
        setReminder(callback, type, null, reminderBT, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void changeReminder(PVBluetoothCallback callback, ReminderBT oldReminder, ReminderBT newReminder, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setReminder(callback, 1, oldReminder, newReminder, PMBluetoothCall.COMMAND_TYPE_PAGE, macs);
    }

    @Override
    public void getUID(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        getUID(callback, PMBluetoothCall.COMMAND_TYPE_BIND, macs);
    }

    @Override
    public void setUID(PVBluetoothCallback callback, int UID, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        setUID(callback, UID, PMBluetoothCall.COMMAND_TYPE_BIND, macs);
    }

    @Override
    public void checkInit(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        checkInit(callback, PMBluetoothCall.COMMAND_TYPE_BIND, macs);
    }

    @Override
    public void bindStart(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        bindStart(callback, PMBluetoothCall.COMMAND_TYPE_BIND, macs);
    }

    @Override
    public void bindEnd(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        bindEnd(callback, PMBluetoothCall.COMMAND_TYPE_BIND, macs);
    }

    @Override
    public void sendSongName(boolean musicState, String songName, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        if (TextUtils.isEmpty(songName)) return;
        sendSongName(musicState, songName, PMBluetoothCall.COMMAND_TYPE_REMOTE_CONTROL, macs);
    }

    @Override
    public void sendStop(String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        sendStop(PMBluetoothCall.COMMAND_TYPE_REMOTE_CONTROL, macs);
    }

    @Override
    public void sendVolume(int volume, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        sendVolume(volume, PMBluetoothCall.COMMAND_TYPE_REMOTE_CONTROL, macs);
    }

    @Override
    public void syncBluetoothData(PVBluetoothCallback callback, int syncType, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        SyncBluetoothData.INSTANCE.start(callback, syncType, macs);
    }

    @Override
    public void bindDevice(PVBluetoothCallback callback, String... macs) {
        macs = checkMACs(macs);
        if (macs.length == 0) return;
        BindDevice.INSTANCE.start(callback, macs);
    }

    @Override
    public int getRealTimeHeartRateValue(String mac) {
        BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
        return bluetoothVar != null ? bluetoothVar.realTimeHeartRateValue : 0;
    }

    @Override
    public void clearSendCommand(String... macs) {
        macs = checkMACs(macs);
        mCall.clearSendCommand(macs);
    }

    /*-------------------------------------------P层内部调用及逻辑实现-------------------------------------------*/

    public void getWatchID(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getWatchID(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_WATCH_ID;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setWatchID(bluetoothVar.watchID);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.watchID}, 1, PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void getDeviceVersion(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getDeviceVersion(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_DEVICE_VERSION;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setDeviceVersion(bluetoothVar.softVersion);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.softVersion}, 1, PVBluetoothCallback.DATA_TYPE_STRING, mac, PVBluetoothCallback.BluetoothCommandType.GET_DEVICE_VERSION);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void getDateTime(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getDateTime(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_DATE_TIME;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null)
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.dateTime}, 1, PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                else
                    onFailed(mac);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setDateTime(final PVBluetoothCallback callback, int year, int month, int day, int hour, int min, int sec, int commandType, String... macs) {
        mCall.setDateTime(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_DATE_TIME;

            @Override
            public void onSuccess(String mac) {
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, year, month, day, hour, min, sec, commandType, macs);
    }

    public void getTimeSurfaceSetting(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getTimeSurfaceSetting(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_TIME_SURFACE_SETTING;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setDateFormat(bluetoothVar.dateFormat);
                    pvspCall.setTimeFormat(bluetoothVar.timeFormat);
                    pvspCall.setBatteryShow(bluetoothVar.batteryShow);
                    pvspCall.setLunarFormat(bluetoothVar.lunarFormat);
                    pvspCall.setScreenFormat(bluetoothVar.screenFormat);
                    pvspCall.setBackgroundStyle(bluetoothVar.backgroundStyle);
                    pvspCall.setSportDataShow(bluetoothVar.sportDataShow);
                    pvspCall.setUsernameFormat(bluetoothVar.usernameFormat);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.dateFormat, bluetoothVar.timeFormat, bluetoothVar.batteryShow, bluetoothVar.lunarFormat, bluetoothVar.screenFormat, bluetoothVar.backgroundStyle, bluetoothVar.sportDataShow, bluetoothVar.usernameFormat}, 8, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setTimeSurfaceSetting(final PVBluetoothCallback callback, final int dateFormat, final int timeFormat, final int batteryFormat, final int lunarFormat, final int screenFormat, final int backgroundStyle, final int sportDataFormat, final int usernameFormat, int commandType, String... macs) {
        mCall.setTimeSurfaceSetting(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_TIME_SURFACE_SETTING;

            @Override
            public void onSuccess(String mac) {
                pvspCall.setDateFormat(dateFormat);
                pvspCall.setTimeFormat(timeFormat);
                pvspCall.setBatteryShow(batteryFormat);
                pvspCall.setLunarFormat(lunarFormat);
                pvspCall.setScreenFormat(screenFormat);
                pvspCall.setBackgroundStyle(backgroundStyle);
                pvspCall.setSportDataShow(sportDataFormat);
                pvspCall.setUsernameFormat(usernameFormat);
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, dateFormat, timeFormat, batteryFormat, lunarFormat, screenFormat, backgroundStyle, sportDataFormat, usernameFormat, commandType, macs);
    }

    public void getScreenBrightness(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getScreenBrightness(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_SCREEN_BRIGHTNESS;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setScreenBrightness(bluetoothVar.screenBrightness);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.screenBrightness}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setScreenBrightness(final PVBluetoothCallback callback, final int brightnessValue, int commandType, String... macs) {
        mCall.setScreenBrightness(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_SCREEN_BRIGHTNESS;

            @Override
            public void onSuccess(String mac) {
                pvspCall.setScreenBrightness(brightnessValue);
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, brightnessValue, commandType, macs);
    }

    public void getBatteryPower(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getBatteryPower(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_BATTERY_POWER;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setBatteryPower(bluetoothVar.batteryPower);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.batteryPower}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void getVolume(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getVolume(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_VOLUME;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setVolume(bluetoothVar.volume);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.volume}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setVolume(final PVBluetoothCallback callback, final int volume, int commandType, String... macs) {
        mCall.setVolume(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_VOLUME;

            @Override
            public void onSuccess(String mac) {
                pvspCall.setVolume(volume);
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, volume, commandType, macs);
    }

    public void getShockMode(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getShockMode(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_SHOCK_MODE;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setAntiShock(bluetoothVar.antiShock);
                    pvspCall.setCallShock(bluetoothVar.callShock);
                    pvspCall.setMissCallShock(bluetoothVar.missCallShock);
                    pvspCall.setSMSShock(bluetoothVar.smsShock);
                    pvspCall.setSocialShock(bluetoothVar.socialShock);
                    pvspCall.setEmailShock(bluetoothVar.emailShock);
                    pvspCall.setCalendarShock(bluetoothVar.calendarShock);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.antiShock, bluetoothVar.clockShock, bluetoothVar.callShock, bluetoothVar.missCallShock, bluetoothVar.smsShock, bluetoothVar.socialShock, bluetoothVar.emailShock, bluetoothVar.calendarShock, bluetoothVar.sedentaryShock, bluetoothVar.lowPowerShock}, 10, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setShockMode(final PVBluetoothCallback callback, final int shockType, final int shockMode, int commandType, String... macs) {
        mCall.setShockMode(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_SHOCK_MODE;

            @Override
            public void onSuccess(String mac) {
                switch (shockType) {
                    case PVBluetoothCall.SHOCK_TYPE_ANTI:
                        pvspCall.setAntiShock(shockMode);
                        break;
                    case PVBluetoothCall.SHOCK_TYPE_CALL:
                        pvspCall.setCallShock(shockMode);
                        break;
                    case PVBluetoothCall.SHOCK_TYPE_MISS_CALL:
                        pvspCall.setMissCallShock(shockMode);
                        break;
                    case PVBluetoothCall.SHOCK_TYPE_SMS:
                        pvspCall.setSMSShock(shockMode);
                        break;
                    case PVBluetoothCall.SHOCK_TYPE_SOCIAL:
                        pvspCall.setSocialShock(shockMode);
                        break;
                    case PVBluetoothCall.SHOCK_TYPE_EMAIL:
                        pvspCall.setEmailShock(shockMode);
                        break;
                    case PVBluetoothCall.SHOCK_TYPE_CALENDAR:
                        pvspCall.setCalendarShock(shockMode);
                        break;
                }
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, shockType, shockMode, commandType, macs);
    }

    public void getLanguage(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getLanguage(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_LANGUAGE;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setLanguage(bluetoothVar.language);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.language}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setLanguage(final PVBluetoothCallback callback, final int language, int commandType, String... macs) {
        mCall.setLanguage(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_LANGUAGE;

            @Override
            public void onSuccess(String mac) {
                pvspCall.setLanguage(language);
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, language, commandType, macs);
    }

    public void getUnit(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getUnit(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_UNIT;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setUnit(bluetoothVar.unit);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.unit}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setUnit(final PVBluetoothCallback callback, final int unit, int commandType, String... macs) {
        mCall.setUnit(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_UNIT;

            @Override
            public void onSuccess(String mac) {
                pvspCall.setUnit(unit);
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, unit, commandType, macs);
    }

    public void restoreFactory(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.restoreFactory(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.RESTORE_FACTORY;

            @Override
            public void onSuccess(String mac) {
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void enterUpdateMode(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.enterUpdateMode(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.ENTER_UPDATE_MODE;

            @Override
            public void onSuccess(String mac) {
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void enterUpdateMode(String touchPanelPath, String heartRatePath, String freescalePath, final PVBluetoothCallback callback, int commandType, String... macs) {
        byte[] bTouchAddressSectorCount = OtaUtil.getAddressSectorCount(touchPanelPath);
        byte[] bHeartRateAddressSectorCount = OtaUtil.getAddressSectorCount(heartRatePath);
        byte[] bFreescaleAddressSectorCount = OtaUtil.getAddressSectorCount(freescalePath);
        byte[] bTouchAddress = new byte[4];
        byte[] bHeartRateAddress = new byte[4];
        final byte[] bFreescaleAddress = new byte[4];
        System.arraycopy(bTouchAddressSectorCount, 0, bTouchAddress, 0, 4);
        System.arraycopy(bHeartRateAddressSectorCount, 0, bHeartRateAddress, 0, 4);
        System.arraycopy(bFreescaleAddressSectorCount, 0, bFreescaleAddress, 0, 4);

        mCall.enterUpdateMode(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.ENTER_UPDATE_MODE;

            @Override
            public void onSuccess(String mac) {
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, bFreescaleAddress, bFreescaleAddressSectorCount[4], bTouchAddress, bTouchAddressSectorCount[4], bHeartRateAddress, bHeartRateAddressSectorCount[4], commandType, macs);
    }

    public void getShockStrength(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getShockStrength(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_SHOCK_STRENGTH;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setShockStrength(bluetoothVar.shockStrength);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.shockStrength}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setShockStrength(final PVBluetoothCallback callback, final int shockStrength, int commandType, String... macs) {
        mCall.setShockStrength(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_SHOCK_STRENGTH;

            @Override
            public void onSuccess(String mac) {
                pvspCall.setShockStrength(shockStrength);
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, shockStrength, commandType, macs);
    }

    public void getWorkMode(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getWorkMode(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_WORK_MODE;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null)
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.workMode}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
                else
                    onFailed(mac);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setWorkMode(final PVBluetoothCallback callback, int workMode, int commandType, String... macs) {
        mCall.setWorkMode(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_WORK_MODE;

            @Override
            public void onSuccess(String mac) {
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, workMode, commandType, macs);
    }

    public void getBrightScreenTime(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getBrightScreenTime(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_BRIGHT_SCREEN_TIME;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null)
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.brightScreenTime}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
                else
                    onFailed(mac);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setBrightScreenTime(final PVBluetoothCallback callback, int brightScreenTime, int commandType, String... macs) {
        mCall.setBrightScreenTime(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_BRIGHT_SCREEN_TIME;

            @Override
            public void onSuccess(String mac) {
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, brightScreenTime, commandType, macs);
    }

    public void getUserInfo(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getUserInfo(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_USER_INFO;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setGender(bluetoothVar.sex);
                    pvspCall.setBirthdayYear(Calendar.getInstance().get(Calendar.YEAR) - 20);
                    pvspCall.setHeight(bluetoothVar.height);
                    pvspCall.setWeight(bluetoothVar.weight / 10f);
                    onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
                } else
                    onFailed(mac);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setUserInfo(final PVBluetoothCallback callback, int sex, int age, int height, int weight, int commandType, String... macs) {
        mCall.setUserInfo(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_USER_INFO;

            @Override
            public void onSuccess(String mac) {
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, sex, age, height, weight, commandType, macs);
    }

    public void getUsageHabits(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getUsageHabits(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_USAGE_HABIT;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setUsageHabits(bluetoothVar.usageHabits);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.usageHabits}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setUsageHabits(final PVBluetoothCallback callback, final int usageHabits, int commandType, String... macs) {
        mCall.setUsageHabits(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_USAGE_HABIT;

            @Override
            public void onSuccess(String mac) {
                pvspCall.setUsageHabits(usageHabits);
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, usageHabits, commandType, macs);
    }

    public void getUserName(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getUserName(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_USER_NAME;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null)
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.userName}, 1, PVBluetoothCallback.DATA_TYPE_STRING, mac, bluetoothCommandType);
                else
                    onFailed(mac);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setUserName(final PVBluetoothCallback callback, String userName, int commandType, String... macs) {
        mCall.setUserName(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_USER_NAME;

            @Override
            public void onSuccess(String mac) {
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, userName, commandType, macs);
    }

    public void getGoal(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getGoal(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_GOAL_SETTING;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setStepGoal(bluetoothVar.stepGoalsValue);
                    pvspCall.setCaloriesGoal(bluetoothVar.calorieGoalsValue);
                    pvspCall.setDistanceGoal(bluetoothVar.distanceGoalsValue);
                    pvspCall.setSportTimeGoal(bluetoothVar.sportTimeGoalsValue);
                    pvspCall.setSleepGoal(bluetoothVar.sleepGoalsValue);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.stepGoalsValue, bluetoothVar.calorieGoalsValue, bluetoothVar.distanceGoalsValue, bluetoothVar.sportTimeGoalsValue, bluetoothVar.sleepGoalsValue}, 5, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setStepGoal(final PVBluetoothCallback callback, final int stepGoal, int commandType, String... macs) {
        mCall.setGoal(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_STEP_GOAL;

            @Override
            public void onSuccess(String mac) {
                pvspCall.setStepGoal(stepGoal);
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, PMBluetoothCall.GOAL_TYPE_STEP, stepGoal / 100, commandType, macs);
    }

    public void setCaloriesGoal(final PVBluetoothCallback callback, final int caloriesGoal, int commandType, String... macs) {
        mCall.setGoal(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_CALORIES_GOAL;

            @Override
            public void onSuccess(String mac) {
                pvspCall.setCaloriesGoal(caloriesGoal);
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, PMBluetoothCall.GOAL_TYPE_CALORIE, caloriesGoal, commandType, macs);
    }

    public void setDistanceGoal(final PVBluetoothCallback callback, final int distanceGoal, int commandType, String... macs) {
        mCall.setGoal(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_DISTANCE_GOAL;

            @Override
            public void onSuccess(String mac) {
                pvspCall.setDistanceGoal(distanceGoal);
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, PMBluetoothCall.GOAL_TYPE_DISTANCE, distanceGoal, commandType, macs);
    }

    public void setSportTimeGoal(final PVBluetoothCallback callback, final int sportTimeGoal, int commandType, String... macs) {
        mCall.setGoal(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_SPORT_TIME_GOAL;

            @Override
            public void onSuccess(String mac) {
                pvspCall.setSportTimeGoal(sportTimeGoal);
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, PMBluetoothCall.GOAL_TYPE_SPORT_TIME, sportTimeGoal, commandType, macs);
    }

    public void setSleepGoal(final PVBluetoothCallback callback, final int sleepGoal, int commandType, String... macs) {
        mCall.setGoal(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_SLEEP_GOAL;

            @Override
            public void onSuccess(String mac) {
                pvspCall.setSleepGoal(sleepGoal);
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, PMBluetoothCall.GOAL_TYPE_SLEEP, sleepGoal, commandType, macs);
    }

    public void getSportSleepMode(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getSportSleepMode(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_SPORT_SLEEP_MODE;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setSportSleepMode(bluetoothVar.sportSleepMode);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.sportSleepMode}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void getAllDataTypeCount(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getAllDataTypeCount(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_ALL_DATA_TYPE_COUNT;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null)
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.sportCount, bluetoothVar.sleepCount, bluetoothVar.heartRateCount, bluetoothVar.moodCount, bluetoothVar.bloodPressureCount}, 5, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, bluetoothCommandType);
                else
                    onFailed(mac);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void deleteSportData(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.deleteSportData(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.DELETE_SPORT_DATA;

            @Override
            public void onSuccess(String mac) {
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void getSportData(final PVBluetoothCallback callback, int sportDataCount, int commandType, String... macs) {
        mCall.getSportData(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_SPORT_DATA;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null)
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.sportBTDataList}, 1, PVBluetoothCallback.DATA_TYPE_LIST_SPORT, mac, bluetoothCommandType);
                else
                    onFailed(mac);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, sportDataCount, commandType, macs);
    }

    public void deleteSleepData(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.deleteSleepData(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.DELETE_SLEEP_DATA;

            @Override
            public void onSuccess(String mac) {
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void getSleepData(final PVBluetoothCallback callback, int sleepDataCount, int commandType, String... macs) {
        mCall.getSleepData(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_SLEEP_DATA;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null)
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.sleepBTDataList}, 1, PVBluetoothCallback.DATA_TYPE_LIST_SLEEP, mac, bluetoothCommandType);
                else
                    onFailed(mac);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, sleepDataCount, commandType, macs);
    }

    public void getDeviceDisplay(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getDeviceDisplay(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_DEVICE_DISPLAY;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null)
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.deviceDisplayStep, bluetoothVar.deviceDisplayCalorie, bluetoothVar.deviceDisplayDistance, bluetoothVar.deviceDisplaySportTime, bluetoothVar.deviceDisplaySleep, bluetoothVar.deviceDisplayHeartRate, bluetoothVar.deviceDisplayMood}, 7, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, bluetoothCommandType);
                else
                    onFailed(mac);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void getAutoSleep(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getAutoSleep(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_AUTO_SLEEP;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setBedtimeHour(bluetoothVar.bedTimeHour);
                    pvspCall.setBedTimeMin(bluetoothVar.bedTimeMin);
                    pvspCall.setAwakeTimeHour(bluetoothVar.awakeTimeHour);
                    pvspCall.setAwakeTimeMin(bluetoothVar.awakeTimeMin);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.bedTimeHour, bluetoothVar.bedTimeMin, bluetoothVar.awakeTimeHour, bluetoothVar.awakeTimeMin, bluetoothVar.remindSleepCycle}, 5, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, bluetoothCommandType);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setAutoSleep(final PVBluetoothCallback callback, final int bedTimeHour, final int bedTimeMin, final int awakeTimeHour, final int awakeTimeMin, int cycle, int commandType, String... macs) {
        mCall.setAutoSleep(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_AUTO_SLEEP;

            @Override
            public void onSuccess(String mac) {
                pvspCall.setBedtimeHour(bedTimeHour);
                pvspCall.setBedTimeMin(bedTimeMin);
                pvspCall.setAwakeTimeHour(awakeTimeHour);
                pvspCall.setAwakeTimeMin(awakeTimeMin);
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, bedTimeHour, bedTimeMin, awakeTimeHour, awakeTimeMin, cycle, commandType, macs);
    }

    public void getHeartRateCount(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getHeartRateCount(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_HEART_RATE_DATA_COUNT;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null)
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.heartRateCount}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
                else
                    onFailed(mac);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void deleteHeartRateData(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.deleteHeartRateData(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.DELETE_HEART_RATE_DATA;

            @Override
            public void onSuccess(String mac) {
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void getHeartRateData(final PVBluetoothCallback callback, int heartRateCount, int commandType, String... macs) {
        mCall.getHeartRateData(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_HEART_RATE;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null)
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.heartRateBTDataList}, 1, PVBluetoothCallback.DATA_TYPE_LIST_HEART_RATE, mac, bluetoothCommandType);
                else
                    onFailed(mac);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, heartRateCount, commandType, macs);
    }

    public void getAutoHeartRateFrequency(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getAutoHeartRateFrequency(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_HEART_RATE_FREQUENCY;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setHeartRateFrequency(bluetoothVar.heartRateFrequency);
                    pvspCall.setHeartRateAutoTrackSwitch(bluetoothVar.heartRateAutoTrackSwitch);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.heartRateFrequency, bluetoothVar.heartRateAutoTrackSwitch ? 1 : 0}, 2, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, bluetoothCommandType);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setAutoHeartRateFrequency(final PVBluetoothCallback callback, final int heartRateFrequency, int commandType, String... macs) {
        mCall.setAutoHeartRateFrequency(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_HEART_RATE_FREQUENCY;

            @Override
            public void onSuccess(String mac) {
                pvspCall.setHeartRateFrequency(heartRateFrequency);
                pvspCall.setHeartRateAutoTrackSwitch(heartRateFrequency > 0);
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, heartRateFrequency, commandType, macs);
    }

    public void getHeartRateAlarmThreshold(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getHeartRateAlarmThreshold(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_HEART_RATE_ALARM_THRESHOLD;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setHeartRateRangeAlertSwitch(bluetoothVar.heartRateAlarmSwitch);
                    pvspCall.setHeartRateMin(bluetoothVar.heartRateMinValue);
                    pvspCall.setHeartRateMax(bluetoothVar.heartRateMaxValue);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.heartRateAlarmSwitch ? 1 : 0, bluetoothVar.heartRateMinValue, bluetoothVar.heartRateMaxValue}, 3, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, bluetoothCommandType);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setHeartRateAlarmThreshold(final PVBluetoothCallback callback, final int heartRateAlarmSwitch, final int heartRateMinValue, final int heartRateMaxValue, int commandType, String... macs) {
        mCall.setHeartRateAlarmThreshold(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_HEART_RATE_ALARM_THRESHOLD;

            @Override
            public void onSuccess(String mac) {
                pvspCall.setHeartRateRangeAlertSwitch(heartRateAlarmSwitch > 0);
                pvspCall.setHeartRateMin(heartRateMinValue);
                pvspCall.setHeartRateMax(heartRateMaxValue);
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, heartRateAlarmSwitch, heartRateMinValue, heartRateMaxValue, commandType, macs);
    }

    public void getInactivityAlert(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getInactivityAlert(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_INACTIVITY_ALERT;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setInactivityAlertSwitch(bluetoothVar.inactivityAlertSwitch);
                    pvspCall.setInactivityAlertCycle(bluetoothVar.inactivityAlertCycle);
                    pvspCall.setInactivityAlertInterval(bluetoothVar.inactivityAlertInterval);
                    pvspCall.setInactivityAlertStartHour(bluetoothVar.inactivityAlertStartHour);
                    pvspCall.setInactivityAlertStartMin(bluetoothVar.inactivityAlertStartMin);
                    pvspCall.setInactivityAlertEndHour(bluetoothVar.inactivityAlertEndHour);
                    pvspCall.setInactivityAlertEndMin(bluetoothVar.inactivityAlertEndMin);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.inactivityAlertSwitch ? 1 : 0, bluetoothVar.inactivityAlertCycle, bluetoothVar.inactivityAlertInterval, bluetoothVar.inactivityAlertStartHour, bluetoothVar.inactivityAlertStartMin, bluetoothVar.inactivityAlertEndHour, bluetoothVar.inactivityAlertEndMin}, 7, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, bluetoothCommandType);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setInactivityAlert(final PVBluetoothCallback callback, final int isOpen, final int cycle, final int interval, final int startHour, final int startMin, final int endHour, final int endMin, int commandType, String... macs) {
        mCall.setInactivityAlert(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_INACTIVITY_ALERT;

            @Override
            public void onSuccess(String mac) {
                pvspCall.setInactivityAlertSwitch(isOpen > 0);
                pvspCall.setInactivityAlertCycle(cycle);
                pvspCall.setInactivityAlertInterval(interval);
                pvspCall.setInactivityAlertStartHour(startHour);
                pvspCall.setInactivityAlertStartMin(startMin);
                pvspCall.setInactivityAlertEndHour(endHour);
                pvspCall.setInactivityAlertEndMin(endMin);
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, isOpen, cycle, interval, startHour, startMin, endHour, endMin, commandType, macs);
    }

    public void getCaloriesType(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getCaloriesType(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_CALORIES_TYPE;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setCaloriesType(bluetoothVar.caloriesType);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.caloriesType}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setCaloriesType(final PVBluetoothCallback callback, final boolean enable, int commandType, String... macs) {
        mCall.setCaloriesType(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_CALORIES_TYPE;

            @Override
            public void onSuccess(String mac) {
                pvspCall.setCaloriesType(enable ? 1 : 0);
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, enable, commandType, macs);
    }

    public void getHeartRateDataEx(final PVBluetoothCallback callback, int heartRateDataCount, int commandType, String... macs) {
        mCall.getHeartRateDataEx(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_HEART_RATE_EX;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null)
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.heartRateBTDataList}, 1, PVBluetoothCallback.DATA_TYPE_LIST_HEART_RATE, mac, bluetoothCommandType);
                else
                    onFailed(mac);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, heartRateDataCount, commandType, macs);
    }

    public void sendMessageCount(final PVBluetoothCallback callback, int msgCount, final PVBluetoothCallback.BluetoothCommandType bluetoothCommandType, int msgType, int commandType, String... macs) {
        mCall.sendMessageCount(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(String mac) {
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, msgType, msgCount, commandType, macs);
    }

    public void sendIncomeMissCall(final PVBluetoothCallback callback, String nameOrNumber, int pushType, final PVBluetoothCallback.BluetoothCommandType bluetoothCommandType, int commandType, String... macs) {
        int len = (nameOrNumber.getBytes().length > PMBluetoothCall.MSG_PUSH_TYPE_MAX_NAME_LEN ? PMBluetoothCall.MSG_PUSH_TYPE_MAX_NAME_LEN : nameOrNumber.getBytes().length) + 1;
        byte[] bSendData = new byte[len - 1];
        System.arraycopy(nameOrNumber.getBytes(), 0, bSendData, 0, len - 1);
        mCall.sendPhoneName(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(String mac) {
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, len, pushType, bSendData, commandType, macs);
    }

    public void sendSMSAndNotify(final PVBluetoothCallback callback, String pushData, int pushType, final PVBluetoothCallback.BluetoothCommandType bluetoothCommandType, int smsNotifyType, int commandType, String... macs) {
        int len = (pushData.getBytes().length > PMBluetoothCall.MSG_PUSH_TYPE_MAX_NAME_LEN ? PMBluetoothCall.MSG_PUSH_TYPE_MAX_NAME_LEN : pushData.getBytes().length) + 1;
        byte[] bSendData = new byte[len - 1];
        System.arraycopy(pushData.getBytes(), 0, bSendData, 0, len - 1);
        switch (bluetoothCommandType) {
            case SEND_SMS_DATETIME:
            case SEND_SOCIAL_DATETIME:
            case SEND_EMAIL_DATETIME:
            case SEND_SCHEDULE_DATETIME:
                try {
                    len = 16;
                    bSendData = pushData.getBytes("US-ASCII");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        mCall.sendSMSAndNotify(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(String mac) {
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, len, pushType, bSendData, smsNotifyType, commandType, macs);
    }

    public void getSwitchSetting(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getSwitchSetting(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_SWITCH;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setAntiSwitch(bluetoothVar.antiLostSwitch);
//                pvspCall.setAutoSyncSwitch(bluetoothVar.autoSyncSwitch);
                    pvspCall.setAutoSleepSwitch(bluetoothVar.autoSleepSwitch);
                    pvspCall.setCallSwitch(bluetoothVar.incomeCallSwitch);
                    pvspCall.setMissCallSwitch(bluetoothVar.missCallSwitch);
                    pvspCall.setSMSSwitch(bluetoothVar.smsSwitch);
                    pvspCall.setSocialSwitch(bluetoothVar.socialSwitch);
                    pvspCall.setEmailSwitch(bluetoothVar.emailSwitch);
                    pvspCall.setCalendarSwitch(bluetoothVar.calendarSwitch);
                    pvspCall.setInactivityAlertSwitch(bluetoothVar.inactivityAlertSwitch);
                    pvspCall.setRaiseWakeSwitch(bluetoothVar.raiseWakeSwitch);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.antiLostSwitch ? 1 : 0, bluetoothVar.autoSyncSwitch ? 1 : 0, bluetoothVar.sleepSwitch ? 1 : 0, bluetoothVar.autoSleepSwitch ? 1 : 0, bluetoothVar.incomeCallSwitch ? 1 : 0, bluetoothVar.missCallSwitch ? 1 : 0, bluetoothVar.smsSwitch ? 1 : 0, bluetoothVar.socialSwitch ? 1 : 0, bluetoothVar.emailSwitch ? 1 : 0, bluetoothVar.calendarSwitch ? 1 : 0, bluetoothVar.sedentarySwitch ? 1 : 0, bluetoothVar.lowPowerSwitch ? 1 : 0, bluetoothVar.secondRemindSwitch ? 1 : 0, bluetoothVar.raiseWakeSwitch ? 1 : 0}, 14, PVBluetoothCallback.DATA_TYPE_INT_ARRAY, mac, bluetoothCommandType);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setSwitchSetting(final PVBluetoothCallback callback, final int switchType, final boolean enable, int commandType, String... macs) {
        mCall.setSwitchSetting(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_SWITCH;

            @Override
            public void onSuccess(String mac) {
                switch (switchType) {
                    case PVBluetoothCall.SWITCH_TYPE_ANTI:
                        pvspCall.setAntiSwitch(enable);
                        break;
//                    case PVBluetoothCall.SWITCH_TYPE_AUTO_SYNC:
//                        pvspCall.setAutoSyncSwitch(enable);
//                        break;
                    case PVBluetoothCall.SWITCH_TYPE_AUTO_SLEEP:
                        pvspCall.setAutoSleepSwitch(enable);
                        break;
                    case PVBluetoothCall.SWITCH_TYPE_CALL:
                        pvspCall.setCallSwitch(enable);
                        break;
                    case PVBluetoothCall.SWITCH_TYPE_MISS_CALL:
                        pvspCall.setMissCallSwitch(enable);
                        break;
                    case PVBluetoothCall.SWITCH_TYPE_SMS:
                        pvspCall.setSMSSwitch(enable);
                        break;
                    case PVBluetoothCall.SWITCH_TYPE_SOCIAL:
                        pvspCall.setSocialSwitch(enable);
                        break;
                    case PVBluetoothCall.SWITCH_TYPE_EMAIL:
                        pvspCall.setEmailSwitch(enable);
                        break;
                    case PVBluetoothCall.SWITCH_TYPE_CALENDAR:
                        pvspCall.setCalendarSwitch(enable);
                        break;
                    case PVBluetoothCall.SWITCH_TYPE_SEDENTARY:
                        pvspCall.setInactivityAlertSwitch(enable);
                        break;
                    case PVBluetoothCall.SWITCH_TYPE_RAISE_WAKE:
                        pvspCall.setRaiseWakeSwitch(enable);
                        break;

                }
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, switchType, enable, commandType, macs);
    }

    public void getReminderCount(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getReminderCount(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_REMINDER_COUNT;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null)
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.remindCount}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
                else
                    onFailed(mac);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void getReminder(final PVBluetoothCallback callback, int reminderCount, int commandType, String... macs) {
        mCall.getReminder(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_REMINDER;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    List<ReminderDB> reminderDBList = ModeConvertUtil.reminderBTToReminderDBList(bluetoothVar.reminderBTDataList);
                    if (reminderDBList != null && reminderDBList.size() > 0) {
                        pvdbCall.delAllReminder();
                        pvdbCall.addReminderList(reminderDBList);
                    }
                    onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, reminderCount, commandType, macs);
    }

    public void setReminder(final PVBluetoothCallback callback, int type, ReminderBT oldReminderData, final ReminderBT newReminderData, int commandType, String... macs) {
        PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.NEW_REMINDER;
        switch (type) {
            case 0:
                bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.NEW_REMINDER;
                break;
            case 1:
                bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.CHANGE_REMINDER;
                break;
            case 2:
                bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.DELETE_A_REMINDER;
                break;
            case 3:
                bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.DELETE_ALL_REMINDER;
                break;
        }
        final PVBluetoothCallback.BluetoothCommandType finalBluetoothCommandType = bluetoothCommandType;
        mCall.setReminder(new IBluetoothResultCallback() {
            @Override
            public void onSuccess(String mac) {
                List<ReminderBT> reminderBTList = new LinkedList<>();
                reminderBTList.add(newReminderData);
                List<ReminderDB> reminderDBList = ModeConvertUtil.reminderBTToReminderDBList(reminderBTList);
                pvdbCall.updateReminder(reminderDBList.get(0));
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, finalBluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, finalBluetoothCommandType);
            }
        }, type, oldReminderData, newReminderData, commandType, macs);
    }

    public void getUID(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.getUID(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.GET_UID;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null) {
                    pvspCall.setUID(bluetoothVar.UID);
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.UID}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
                } else {
                    onFailed(mac);
                }
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void setUID(final PVBluetoothCallback callback, final int UID, int commandType, String... macs) {
        mCall.setUID(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.SET_UID;

            @Override
            public void onSuccess(String mac) {
                pvspCall.setUID(UID);
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, UID, commandType, macs);
    }

    public void checkInit(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.checkInit(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.CHECK_INIT;

            @Override
            public void onSuccess(String mac) {
                BluetoothVar bluetoothVar = mCall.getBluetoothVarByMAC(mac);
                if (bluetoothVar != null)
                    onSuccessCallBack(callback, new Object[]{bluetoothVar.initFlag}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
                else
                    onFailed(mac);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void bindStart(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.bindStart(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.BIND_START;

            @Override
            public void onSuccess(String mac) {
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void bindEnd(final PVBluetoothCallback callback, int commandType, String... macs) {
        mCall.bindEnd(new IBluetoothResultCallback() {
            PVBluetoothCallback.BluetoothCommandType bluetoothCommandType = PVBluetoothCallback.BluetoothCommandType.BIND_END;

            @Override
            public void onSuccess(String mac) {
                onSuccessCallBack(callback, new Object[]{PVBluetoothCallback.RESULT_SUCCESS}, 1, PVBluetoothCallback.DATA_TYPE_INT, mac, bluetoothCommandType);
            }

            @Override
            public void onFailed(String mac) {
                onFailCallBack(callback, mac, bluetoothCommandType);
            }
        }, commandType, macs);
    }

    public void sendSongName(boolean musicState, String songName, int commandType, String... macs) {
        mCall.sendSongName(musicState, songName, commandType, macs);
    }

    public void sendStop(int commandType, String... macs) {
        mCall.sendStop(commandType, macs);
    }

    public void sendVolume(int volume, int commandType, String... macs) {
        mCall.sendVolume(volume, commandType, macs);
    }

    public BluetoothVar getBluetoothVarByMAC(String mac) {
        return mCall.getBluetoothVarByMAC(mac);
    }

    /*------------------------------------------------------------------本类私有方法-------------------------------------------------------------------*/

    private String[] checkMACs(String... macs) {
        if (macs == null || macs.length == 0) {
            String mac = pvspCall.getMAC();
            if (TextUtils.isEmpty(mac)) return new String[]{};
            macs = new String[]{mac};
        }
        return macs;
    }

    private void onSuccessCallBack(PVBluetoothCallback callback, Object[] objects, int len, int dataType, String mac, PVBluetoothCallback.BluetoothCommandType bluetoothCommandType) {
        if (callback != null)
            callback.onSuccess(objects, len, dataType, mac, bluetoothCommandType);
    }

    private void onFailCallBack(PVBluetoothCallback callback, String mac, PVBluetoothCallback.BluetoothCommandType bluetoothCommandType) {
        if (callback != null)
            callback.onFail(mac, bluetoothCommandType);
    }
}
