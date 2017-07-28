package cn.appscomm.presenter.implement;

import android.content.ContentValues;
import android.util.SparseArray;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.appscomm.db.implement.MDB;
import cn.appscomm.db.interfaces.PMDBCall;
import cn.appscomm.db.mode.BandSettingDB;
import cn.appscomm.db.mode.ChildInfoDB;
import cn.appscomm.db.mode.ChoresL28TDB;
import cn.appscomm.db.mode.HeartRateDB;
import cn.appscomm.db.mode.RaceDB;
import cn.appscomm.db.mode.ReminderDB;
import cn.appscomm.db.mode.RewardsL28TDB;
import cn.appscomm.db.mode.SleepDB;
import cn.appscomm.db.mode.SpoetL28TDB;
import cn.appscomm.db.mode.SportCacheDB;
import cn.appscomm.db.mode.SportCacheL28TDB;
import cn.appscomm.db.mode.SportDB;
import cn.appscomm.presenter.util.LogUtil;
import cn.appscomm.presenter.util.TimeUtil;
import cn.appscomm.presenter.interfaces.PVDBCall;

import static android.R.attr.id;
import static org.litepal.crud.DataSupport.deleteAll;
import static org.litepal.crud.DataSupport.where;

/**
 * 作者：hsh
 * 日期：2017/3/6
 * 说明：数据库的P
 * 1、处理所有的数据库操作
 */
public enum PDB implements PVDBCall {
    INSTANCE;

    private static final String TAG = PDB.class.getSimpleName();
    private PMDBCall mCall = MDB.INSTANCE;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdf_h = new SimpleDateFormat("hh");
    /*--------------------------------------------------------------------孩子的信息--------------------------------------------------------------------*/
    public void addChildInfo(ChildInfoDB childInfoDB){
        childInfoDB.save();
    }

    public void updateAllChildInfo(ContentValues values, int uId){
        DataSupport.updateAll(ChildInfoDB.class, values, "uId = ?", uId+"");
    }

    public ChildInfoDB getChildInfo(int uId){
        List<ChildInfoDB> childInfoDBs = where("uId = ?", uId+"").find(ChildInfoDB.class);
        return childInfoDBs!=null&&childInfoDBs.size()>0?childInfoDBs.get(0):null;
    }
    public ChildInfoDB getChildInfo(String mac){
        List<ChildInfoDB> childInfoDBs = where("mac = ?", mac+"").find(ChildInfoDB.class);
        return childInfoDBs!=null&&childInfoDBs.size()>0?childInfoDBs.get(0):null;
    }
    public List<ChildInfoDB> getAllChildInfo(){
        return DataSupport.findAll(ChildInfoDB.class);
    }

    public void deleteAllChildInfo(){
        deleteAll(ChildInfoDB.class);
    }
    public void deleteChildInfo(int id){
        deleteAll(ChildInfoDB.class, "id = ?", id+"");
    }
    public void deleteChildInfo(String mac){
        deleteAll(ChildInfoDB.class, "mac = ?", mac);
    }

    /*---------------------------------------------------------------------L28T运动-----------------------------------------------------------------------*/
    public void addSportL28T(SpoetL28TDB spoetL28TDB){
        spoetL28TDB.save();
    }
    public void updateAllSportL28T(SpoetL28TDB spoetL28TDB,int id){
        spoetL28TDB.update(id);
    }

    public void updateAllSportL28T(ContentValues values, int uId){
        DataSupport.updateAll(SpoetL28TDB.class, values, "uId = ?", uId+"");

    }

    public SpoetL28TDB getSportL28T(int id){
        return DataSupport.find(SpoetL28TDB.class,id);
    }
    public List<SpoetL28TDB> getSportL28T(){
        return DataSupport.findAll(SpoetL28TDB.class);
    }
    public SpoetL28TDB getSportL28T(String date,int uId) {
        List<SpoetL28TDB> sportDBs = where("date = ?and uId =?", date,uId+"").find(SpoetL28TDB.class);
        return sportDBs != null && sportDBs.size() > 0 ? sportDBs.get(0) : null;
    }
    public SpoetL28TDB getSportL28T(String date,String mac) {
        List<SpoetL28TDB> sportDBs = where("date = ?and mac =?", date,mac).find(SpoetL28TDB.class);
        return sportDBs != null && sportDBs.size() > 0 ? sportDBs.get(0) : null;
    }
    public List<SpoetL28TDB> getSportListL28T(String startDate, String endDate,int uId) {
        return where("date >= ? and date <= ? and uId = ?", startDate, endDate,uId+"").find(SpoetL28TDB.class);
    }

    public void delSportL28T(String date) {
        deleteAll(SpoetL28TDB.class, "date = ?", date);
    }
    public void deleteSportL28T(int id){
        DataSupport.delete(SpoetL28TDB.class,id);
    }
    public void deleteSportL28T(String mac){
        deleteAll(SpoetL28TDB.class, "mac = ?", mac);

    }
    public void deleteAllSportL28T(){
        deleteAll(SpoetL28TDB.class);
    }
    /*---------------------------------------------------------------------L28T设置-----------------------------------------------------------------------*/
    public void addBandSettingDB(BandSettingDB bandSettingDB){
        bandSettingDB.save();
    }
    public BandSettingDB getBandSettingDB(String mac){
        List<BandSettingDB> bandSettingDBs = where("mac = ?", mac).find(BandSettingDB.class);
        return bandSettingDBs!=null&& bandSettingDBs.size()>0?bandSettingDBs.get(0):null;

    }
    public void updateBandSettingDB(ContentValues values,String mac){
        DataSupport.updateAll(BandSettingDB.class, values, "mac = ?", mac+"");
    }
    public void deleteBandSettingDB(String mac){
        deleteAll(BandSettingDB.class, "mac = ?", mac+"");
    }
    public void deleteAllBandSettingDB(){
        deleteAll(BandSettingDB.class);
    }
        /*---------------------------------------------------------------------L28T待上传数据-----------------------------------------------------------------------*/
    public void addSportCacheL28TDB(SportCacheL28TDB sportCacheL28TDB){
        sportCacheL28TDB.save();
    }
    public void delSportCacheL28TDB(int id){
        DataSupport.delete(SportCacheL28TDB.class, id);
    }
    public List<SportCacheL28TDB> getSportCacheAllL28TDB(){
        return DataSupport.findAll(SportCacheL28TDB.class);
    }
    public void deleteAllSportCacheL28TDB(){
        deleteAll(SportCacheL28TDB.class);
    }
    public void deleteSportCacheL28TDB(String uId){
        deleteAll(SportCacheL28TDB.class, "uId = ?", uId+"");
    }
     /*---------------------------------------------------------------------L28T任务列表-----------------------------------------------------------------------*/

    public void addChoresL28TDB(ChoresL28TDB choresL28TDB){
        choresL28TDB.save();
    }
    public List<ChoresL28TDB> getChoresL28TDBs(int uId){
       List<ChoresL28TDB> choresL28TDBs= DataSupport.where("uId = ?",uId+"").find(ChoresL28TDB.class);
        return choresL28TDBs;
    }
    public void deleteChoresL28TDB(int id){
        DataSupport.delete(ChoresL28TDB.class,id);
    }
    public void deleteChoresL28TDB(int uId,int choresId){
        DataSupport.deleteAll(ChoresL28TDB.class,"uId = ? and choresId = ?",uId+"",choresId+"");
    }

    public void updateChoresL28TDB(ContentValues values , int id){
        DataSupport.update(ChoresL28TDB.class,values,id);
    }
    public void deleteAllChoresL28TDB(){
        deleteAll(ChoresL28TDB.class);
    }
    public void deleteChoresL28TDB(String uId){
        deleteAll(ChoresL28TDB.class, "uId = ?", uId+"");
    }
       /*---------------------------------------------------------------------L28T奖励列表-----------------------------------------------------------------------*/

    public void addRewardsL28TDB(RewardsL28TDB rewardsL28TDB){
        rewardsL28TDB.save();
    }
    public List<RewardsL28TDB> getRewardsL28TDBs(int uId){
        List<RewardsL28TDB> rewardsL28TDBs= DataSupport.where("uId = ?",uId+"").find(RewardsL28TDB.class);
        return rewardsL28TDBs;
    }
    public void deleteRewardsL28TDB(int id){
        DataSupport.delete(RewardsL28TDB.class,id);
    }
    public void deleteRewardsL28TDB(int uId,int choresId){
         DataSupport.deleteAll(RewardsL28TDB.class,"uId = ? and choresId = ?",uId+"",choresId+"");
    }
    public void updateRewardsL28TDB(ContentValues values , int id){
        DataSupport.update(RewardsL28TDB.class,values,id);
    }
    public void deleteAllRewardsL28TDB(){
        deleteAll(RewardsL28TDB.class);
    }
    public void deleteRewardsL28TDB(String uId){
        deleteAll(RewardsL28TDB.class, "uId = ?", uId+"");
    }
     /*---------------------------------------------------------------------L28T竞赛地图-----------------------------------------------------------------------*/
     public void addRaceDB(RaceDB raceDB){
         raceDB.save();
     }

    public RaceDB getRaceDBs(String uId){
        List<RaceDB> raceDBs= DataSupport.where("uId = ?",uId).find(RaceDB.class);
        return raceDBs != null && raceDBs.size() > 0 ? raceDBs.get(0) : null;
    }
    public List<RaceDB> getAllRaceDBs(){
        return DataSupport.findAll(RaceDB.class);
    }
    public void updateRaceDB(ContentValues values,String uId){
        DataSupport.updateAll(RaceDB.class, values, "uId = ?", uId);
    }
    public void delRaceDB(String uId){
        DataSupport.deleteAll(RaceDB.class,"uId = ? ",uId);
    }
    public void deleteAllRaceDB(){
        deleteAll(RaceDB.class);
    }
    public void deleteRaceDB(String uId){
        deleteAll(RaceDB.class, "uId = ?", uId+"");
    }
    /*---------------------------------------------------------------------运动-----------------------------------------------------------------------*/

    @Override
    public void addSport(SportDB sportDB) {
        mCall.addSport(sportDB);
    }

    @Override
    public void addSportList(List<SportDB> sportDBList) {
        mCall.addSportList(sportDBList);
    }

    @Override
    public void addSportBySportCache(List<SportCacheDB> synSportCacheDBList) {
        Map<String, SportDB> sportDBMap = new HashMap<>();
        for (SportCacheDB sportCacheDB : synSportCacheDBList) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(sportCacheDB.getTimeStamp() * 1000L);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            String date = year + "-" + (month > 9 ? month + "" : "0" + month) + "-" + (day > 9 ? day + "" : "0" + day);
            int step = sportCacheDB.getStep();
            int distance = sportCacheDB.getDistance();
            int calories = sportCacheDB.getCalories();
            int sportTime = sportCacheDB.getSportTime();
            SportDB tempSportDB = sportDBMap.get(date);
            if (tempSportDB == null) {
                tempSportDB = new SportDB();
                sportDBMap.put(date, tempSportDB);
            }
            tempSportDB.setStep(tempSportDB.getStep() + sportCacheDB.getStep());
            tempSportDB.setDistance(tempSportDB.getDistance() + sportCacheDB.getDistance());
            tempSportDB.setCalories(tempSportDB.getCalories() + sportCacheDB.getCalories());
            tempSportDB.setSportTime(tempSportDB.getSportTime() + sportCacheDB.getSportTime());
            tempSportDB.setStepDetail(addSportDetail(tempSportDB.getStepDetail(), hour, step));
            tempSportDB.setCaloriesDetail(addSportDetail(tempSportDB.getCaloriesDetail(), hour, calories));
            tempSportDB.setDistanceDetail(addSportDetail(tempSportDB.getDistanceDetail(), hour, distance));
            tempSportDB.setSportTimeDetail(addSportDetail(tempSportDB.getSportTimeDetail(), hour, sportTime));
            tempSportDB.setDate(date);
        }
        if (sportDBMap.size() > 0) {
            for (Map.Entry<String, SportDB> entry : sportDBMap.entrySet()) {
                String date = entry.getKey();
                SportDB addSportDB = entry.getValue();
                SportDB oldSportDB = getSport(date);
                SportDB newSportDB = combineSportDB(oldSportDB, addSportDB);
                mCall.addOrUpdateSport(date, newSportDB);
            }
        }
    }

    @Override
    public void delSport(int id) {
        mCall.delSport(id);
    }

    @Override
    public void delSports(List<Integer> ids) {
        for (Integer id : ids) {
            delSport(id);
        }
    }

    @Override
    public void delAllSport() {
        mCall.delAllSport();
    }

    @Override
    public SportDB getSport(String date) {
        return mCall.getSport(date);
    }

    @Override
    public List<SportDB> getSports(String startDate, String endDate) {
        return mCall.getSportList(startDate, endDate);
    }

    @Override
    public List<SportDB> getSportByCache(String startDate, String endDate) {
        List<SportDB> sportDBList = null;
        try {
            startDate += " 00:00:00";
            endDate += " 23:59:59";
            long startTimeStamp = sdf.parse(startDate).getTime();
            long endTimeStamp = sdf.parse(endDate).getTime();
            List<SportCacheDB> sportCacheDBList = mCall.getSportCacheList(startTimeStamp / 1000L, endTimeStamp / 1000L);
            if (sportCacheDBList != null && sportCacheDBList.size() > 0) {
                Map<String, SportDB> sportDBMap = new HashMap<>();
                for (SportCacheDB sportCacheDB : sportCacheDBList) {
                    String date = sdf.format(sportCacheDB.getTimeStamp() * 1000L).split(" ")[0];
                    int hour = Integer.parseInt(sdf_h.format(sportCacheDB.getTimeStamp() * 1000L));
                    SportDB tempSportDB = sportDBMap.get(date);
                    if (!sportDBMap.containsKey(date)) {
                        tempSportDB = new SportDB();
                        sportDBMap.put(date, tempSportDB);
                    }
                    tempSportDB.setDate(date);
                    tempSportDB.setStep(tempSportDB.getStep() + sportCacheDB.getStep());
                    tempSportDB.setCalories(tempSportDB.getCalories() + sportCacheDB.getCalories());
                    tempSportDB.setDistance(tempSportDB.getDistance() + sportCacheDB.getDistance());
                    tempSportDB.setSportTime(tempSportDB.getSportTime() + sportCacheDB.getSportTime());
                    tempSportDB.setStepDetail(proSportDetail(tempSportDB.getStepDetail(), hour, sportCacheDB.getStep()));
                    tempSportDB.setCaloriesDetail(proSportDetail(tempSportDB.getCaloriesDetail(), hour, sportCacheDB.getCalories()));
                    tempSportDB.setDistanceDetail(proSportDetail(tempSportDB.getDistanceDetail(), hour, sportCacheDB.getDistance()));
                    tempSportDB.setSportTimeDetail(proSportDetail(tempSportDB.getSportTimeDetail(), hour, sportCacheDB.getSportTime()));
                }
                if (sportDBMap.size() > 0) {
                    sportDBList = new ArrayList<>(sportDBMap.values());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sportDBList;
    }

    /*-------------------------------------------------------------------运动缓存---------------------------------------------------------------------*/

    @Override
    public void addSportCacheList(List<SportCacheDB> sportCacheDBList) {
        mCall.addSportCacheList(sportCacheDBList);
    }

    @Override
    public void delSportCache(int id) {
        mCall.delSportCache(id);
    }

    @Override
    public void delAllSportCache() {
        mCall.delAllSportCache();
    }

    @Override
    public SportCacheDB getSportCache(int id) {
        return mCall.getSportCache(id);
    }

    @Override
    public List<SportCacheDB> getSportCacheList() {
        return mCall.getSportCacheList();
    }

    @Override
    public SparseArray<float[]> getWeekSport(Calendar calendar, boolean isMondayFirstDay) {
        String weekFirstDay = TimeUtil.getFirstDayOfWeek(calendar, isMondayFirstDay);
        String weekLastDay = TimeUtil.getLastDayOfWeek(calendar, isMondayFirstDay);
        LogUtil.i(TAG, "获取运动周数据 指定日期:" + calendar.getTimeInMillis() + " 周首日 :" + weekFirstDay + " 周末日:" + weekLastDay);
        List<SportDB> sportDBList = getSports(weekFirstDay, weekLastDay);
        List<SportDB> sportCacheDBList = getSportByCache(weekFirstDay, weekLastDay);
        SparseArray<float[]> weekSportDataArray = new SparseArray<>();
        float[] weekStepData = new float[7];
        float[] weekCaloriesData = new float[7];
        float[] weekDistanceData = new float[7];
        float[] weekSportTimeData = new float[7];
        weekSportDataArray.put(0, weekStepData);
        weekSportDataArray.put(1, weekCaloriesData);
        weekSportDataArray.put(2, weekDistanceData);
        weekSportDataArray.put(3, weekSportTimeData);
        proWeekSportData(sportDBList, weekStepData, isMondayFirstDay, 0);
        proWeekSportData(sportCacheDBList, weekStepData, isMondayFirstDay, 0);
        proWeekSportData(sportDBList, weekCaloriesData, isMondayFirstDay, 1);
        proWeekSportData(sportCacheDBList, weekCaloriesData, isMondayFirstDay, 1);
        proWeekSportData(sportDBList, weekDistanceData, isMondayFirstDay, 2);
        proWeekSportData(sportCacheDBList, weekDistanceData, isMondayFirstDay, 2);
        proWeekSportData(sportDBList, weekSportTimeData, isMondayFirstDay, 3);
        proWeekSportData(sportCacheDBList, weekSportTimeData, isMondayFirstDay, 3);
        return weekSportDataArray;
    }

    @Override
    public SparseArray<float[]> getMonthSport(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH) + 1;
        String monthFirstDay = calendar.get(Calendar.YEAR) + "-" + (month > 9 ? "" + month : "0" + month) + "-01";
        String monthLastDay = calendar.get(Calendar.YEAR) + "-" + (month > 9 ? "" + month : "0" + month) + "-31";
        LogUtil.i(TAG, "指定日期:" + calendar.getTimeInMillis() + " 月首日 :" + monthFirstDay + " 月末日:" + monthLastDay);
        List<SportDB> sportDBList = getSports(monthFirstDay, monthLastDay);
        List<SportDB> sportCacheDBList = getSportByCache(monthFirstDay, monthLastDay);
        SparseArray<float[]> monthSportDataArray = new SparseArray<>();
        float[] monthStepData = new float[31];
        float[] monthCaloriesData = new float[31];
        float[] monthDistanceData = new float[31];
        float[] monthSportTimeData = new float[31];
        monthSportDataArray.put(0, monthStepData);
        monthSportDataArray.put(1, monthCaloriesData);
        monthSportDataArray.put(2, monthDistanceData);
        monthSportDataArray.put(3, monthSportTimeData);
        proMonthSportData(sportDBList, monthStepData, 0);
        proMonthSportData(sportCacheDBList, monthStepData, 0);
        proMonthSportData(sportDBList, monthCaloriesData, 1);
        proMonthSportData(sportCacheDBList, monthCaloriesData, 1);
        proMonthSportData(sportDBList, monthDistanceData, 2);
        proMonthSportData(sportCacheDBList, monthDistanceData, 2);
        proMonthSportData(sportDBList, monthSportTimeData, 3);
        proMonthSportData(sportCacheDBList, monthSportTimeData, 3);
        return monthSportDataArray;
    }

    /*---------------------------------------------------------------------睡眠-----------------------------------------------------------------------*/

    @Override
    public void addSleep(SleepDB sleepDB) {
        mCall.addSleep(sleepDB);
    }

    @Override
    public void addSleepList(List<SleepDB> sleepDBList) {
        mCall.addSleepList(sleepDBList);
    }

    @Override
    public void delSleep(int id) {
        mCall.delSport(id);
    }

    @Override
    public void delSleeps(List<Integer> idList) {
        mCall.delSleepByIdList(idList);
    }

    @Override
    public void delAllSleep() {
        mCall.delAllSleep();
    }

    @Override
    public List<SleepDB> getSleepList(String date) {
        return mCall.getSleepList(date);
    }

    @Override
    public List<SleepDB> getNoUploadSleepList() {
        return mCall.getNoUploadSleepList();
    }

    @Override
    public int[] getWeekSleep(Calendar calendar, boolean isMondayFirstDay) {
        String weekFirstDay = TimeUtil.getFirstDayOfWeek(calendar, isMondayFirstDay);
        String weekLastDay = TimeUtil.getLastDayOfWeek(calendar, isMondayFirstDay);
        LogUtil.i(TAG, "获取睡眠周数据 指定日期:" + calendar.getTimeInMillis() + " 周首日 :" + weekFirstDay + " 周末日:" + weekLastDay);
        List<SleepDB> sleepDBList = mCall.getSleepList(weekFirstDay, weekLastDay);
        int[] weekSleep = new int[7];
        for (SleepDB sleepDB : sleepDBList) {
            int index = TimeUtil.getWeekIndexByDate(sleepDB.getDate(), isMondayFirstDay);
            weekSleep[index] += sleepDB.getTotal();
        }
        return weekSleep;
    }

    @Override
    public int[] getMonthSleep(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH) + 1;
        String monthFirstDay = calendar.get(Calendar.YEAR) + "-" + (month > 9 ? "" + month : "0" + month) + "-01";
        String monthLastDay = calendar.get(Calendar.YEAR) + "-" + (month > 9 ? "" + month : "0" + month) + "-31";
        LogUtil.i(TAG, "获取睡眠月数据 指定日期:" + calendar.getTimeInMillis() + " 月首日 :" + monthFirstDay + " 月末日:" + monthLastDay);
        List<SleepDB> sleepDBList = mCall.getSleepList(monthFirstDay, monthLastDay);
        int[] monthSleep = new int[31];
        for (SleepDB sleepDB : sleepDBList) {
            int index = Integer.parseInt(sleepDB.getDate().split("-")[2]) - 1;
            monthSleep[index] += sleepDB.getTotal();
        }
        return monthSleep;
    }

    @Override
    public void uploadSleepFlag(int id) {
        mCall.uploadSleepFlag(id);
    }

    /*---------------------------------------------------------------------心率-----------------------------------------------------------------------*/

    @Override
    public void delAllHeartRate() {
        mCall.delAllHeartRate();
    }

    @Override
    public List<HeartRateDB> getNoUploadHeartRateList() {
        return mCall.getNoUploadHeartRateList();
    }

    @Override
    public List<HeartRateDB> getHeartRateList(String date) {
        return mCall.getHeartRateList(date);
    }

    @Override
    public void addHeartRateSubmitList(Map<String, String> dateSubmitMap) {
        mCall.updateHeartRateSubmitList(dateSubmitMap);
    }

    @Override
    public void updateHeartRateSubmitToDetail(int id) {
        mCall.updateHeartRateSubmitToDetail(id);
    }

    @Override
    public void updateHeartRateDetailList(List<HeartRateDB> heartRateDBList) {
        mCall.updateHeartRateDetailList(heartRateDBList);
    }

    /*---------------------------------------------------------------------提醒-----------------------------------------------------------------------*/

    @Override
    public void addReminder(ReminderDB reminderDB) {
        mCall.addReminder(reminderDB);
    }

    @Override
    public void addReminderList(List<ReminderDB> reminderDBList) {
        mCall.addReminderList(reminderDBList);
    }

    @Override
    public void delReminder(int id) {
        mCall.delReminder(id);
    }

    @Override
    public void delAllReminder() {
        mCall.delAllReminder();
    }

    @Override
    public void getReminderCount() {
        mCall.getReminderCount();
    }

    @Override
    public ReminderDB getReminder(int hour, int min) {
        return mCall.getReminder(hour, min);
    }

    @Override
    public List<ReminderDB> gerReminderList() {
        return mCall.gerReminderList();
    }

    @Override
    public void updateReminder(ReminderDB reminderDB) {
        mCall.updateReminder(reminderDB);
    }

    /*------------------------------------------------------------------本类私有方法-------------------------------------------------------------------*/

    private String proSportDetail(String detail, int hour, int value) {
        String[] details = detail.split(",");
        details[hour] = (Integer.parseInt(details[hour]) + value) + "";
        detail = Arrays.toString(details);
        return detail.replace(" ", "").replace("[", "").replace("]", "");
    }

    private void proWeekSportData(List<SportDB> sportDBList, float[] weekSportData, boolean isMondayFirstDay, int type) {
        if (sportDBList != null && sportDBList.size() > 0) {
            for (SportDB sportDB : sportDBList) {
                int index = TimeUtil.getWeekIndexByDate(sportDB.getDate(), isMondayFirstDay);
//                LogUtil.i("test", "index : " + index + " notificationType : " + notificationType + " date : " + sportDB.getDate() + " step : " + sportDB.getStep());
                switch (type) {
                    case 0:
                        weekSportData[index] += sportDB.getStep();
                        break;
                    case 1:
                        weekSportData[index] += sportDB.getCalories();
                        break;
                    case 2:
                        weekSportData[index] += sportDB.getDistance();
                        break;
                    case 3:
                        weekSportData[index] += sportDB.getSportTime();
                        break;
                }
            }
        }
    }

    private void proMonthSportData(List<SportDB> sportDBList, float[] monthSportData, int type) {
        if (sportDBList != null && sportDBList.size() > 0) {
            for (SportDB sportDB : sportDBList) {
                int index = Integer.parseInt(sportDB.getDate().split("-")[2]) - 1;
//                LogUtil.i("test", "index : " + index + " notificationType : " + notificationType + " date : " + sportDB.getDate() + " step : " + sportDB.getStep());
                switch (type) {
                    case 0:
                        monthSportData[index] += sportDB.getStep();
                        break;
                    case 1:
                        monthSportData[index] += sportDB.getCalories();
                        break;
                    case 2:
                        monthSportData[index] += sportDB.getDistance();
                        break;
                    case 3:
                        monthSportData[index] += sportDB.getSportTime();
                        break;
                }
            }
        }
    }

    private String addSportDetail(String oldDetail, int hour, int val) {
        String[] details = oldDetail.split(",");
        if (details.length > hour) {
            details[hour] = (Integer.parseInt(details[hour]) + val) + "";
        }
        return Arrays.toString(details).replace("[", "").replace("]", "").replace(" ", "");
    }

    private String combineSportDetail(String oldDetail, String addDetail) {
        String[] addDetails = addDetail.split(",");
        String[] oldDetails = oldDetail.split(",");
        int[] newDetails = new int[24];
        if (addDetails.length == oldDetails.length) {
            for (int i = 0; i < addDetails.length; i++) {
                newDetails[i] = Integer.parseInt(oldDetails[i]) + Integer.parseInt(addDetails[i]);
            }
        }
        return Arrays.toString(newDetails).replace("[", "").replace("]", "").replace(" ", "");
    }

    private SportDB combineSportDB(SportDB oldSportDB, SportDB addSportDB) {
        if (oldSportDB != null) {
            addSportDB.setStep(oldSportDB.getStep() + addSportDB.getStep());
            addSportDB.setCalories(oldSportDB.getCalories() + addSportDB.getCalories());
            addSportDB.setDistance(oldSportDB.getDistance() + addSportDB.getDistance());
            addSportDB.setSportTime(oldSportDB.getSportTime() + addSportDB.getSportTime());
            addSportDB.setStepDetail(combineSportDetail(oldSportDB.getStepDetail(), addSportDB.getStepDetail()));
            addSportDB.setCaloriesDetail(combineSportDetail(oldSportDB.getCaloriesDetail(), addSportDB.getCaloriesDetail()));
            addSportDB.setDistanceDetail(combineSportDetail(oldSportDB.getDistanceDetail(), addSportDB.getDistanceDetail()));
            addSportDB.setSportTimeDetail(combineSportDetail(oldSportDB.getSportTimeDetail(), addSportDB.getSportTimeDetail()));
            addSportDB.setDate(oldSportDB.getDate());
            addSportDB.setId(oldSportDB.getId());
        }
        return addSportDB;
    }
}
