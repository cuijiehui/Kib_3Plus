package com.example.administrator.kib_3plus;

import android.content.Context;
import android.widget.ListView;

import com.example.administrator.kib_3plus.mode.ChoresListMode;
import com.example.administrator.kib_3plus.mode.RewardsListMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by cui on 2017/6/10.
 */

public class PublicData {
    /**
     * 初始化一些数据，如身高，体重
     * @param context
     */
    public static void InitDragListData(Context context) {
        left_height_imperilal_Int=new ArrayList<>();
        for(int i=0;i<LEFT_IMPERILAL_INT_NUM;i++){
            left_height_imperilal_Int.add(LEFT_IMPERILAL_INT_START+i+"'");
        }
        right_height_imperilal_Int=new ArrayList<>();
        for(int i=0;i<RIGHT_IMPERILAL_INT_NUM;i++){
            right_height_imperilal_Int.add(RIGHT_IMPERILAL_INT_START+i+"\"");
        }

        left_height_metric_Int=new ArrayList<>();
        for(int i=0;i<LEFT_METRIC_INT_NUM;i++){
            left_height_metric_Int.add(LEFT_METRIC_INT_START+i+"");
        }
        height_metric_Int=new ArrayList<>();
        for(int i=0;i<RIGHT_METRIC_INT_NUM;i++){
            int num=RIGHT_METRIC_INT_START+i;
            height_metric_Int.add("."+num);
        }

        left_weight_imperilal_Int=new ArrayList<>();
        for(int i=0;i<LEFT_WEIGHT_IMPERILAL_INT_NUM;i++){
            left_weight_imperilal_Int.add(LEFT_WEIGHT_IMPERILAL_INT_START+i+"");
        }
        left_weight_metric_Int=new ArrayList<>();
        for(int i=0;i<LEFT_WEIGHT_METRIC_INT_NUM;i++){
            left_weight_metric_Int.add(LEFT_WEIGHT_METRIC_INT_START+i+"");
        }
        preset_sleep_time_h=new ArrayList<>();
        for(int i=0;i<24;i++){
            if(i<10){
                preset_sleep_time_h.add("0"+i);

            }else{
                preset_sleep_time_h.add(i+"");

            }
        }
        preset_sleep_time_m=new ArrayList<>();
        for(int i=0;i<=60;i++){
            if(i<10){
                preset_sleep_time_m.add("0"+i);

            }else{
                preset_sleep_time_m.add(i+"");

            }
        }
        arrStep = new ArrayList<>();
        int step = 50;
        for (int i = 0; i < 60; i++) {
            arrStep.add(step + step * i + "");
        }
        arrAlertInterval=new ArrayList<>();
        arrAlertInterval.add("15mins");
        arrAlertInterval.add("30mins");
        arrAlertInterval.add("45mins");
        arrAlertInterval.add("1h15mins");
        arrAlertInterval.add("1h30mins");
        arrAlertInterval.add("1h45mins");
        arrAlertInterval.add("2h");
        arrAlertInterval.add("2h15mins");


        choresListData=new ArrayList<>();
        choresListData.add(new ChoresListMode(context.getString(R.string.chores_brush_teeth_name),1,"0000000",R.mipmap.brush_teeth,0));
        choresListData.add(new ChoresListMode(context.getString(R.string.chores_bring_in_mail_name),2,"0000000",R.mipmap.bring_in_mail,0));
        choresListData.add(new ChoresListMode(context.getString(R.string.chores_clean_pets_home_name),3,"0000000",R.mipmap.clean_pet_s_home,0));
        choresListData.add(new ChoresListMode(context.getString(R.string.chores_clean_toilet_name),4,"0000000",R.mipmap.clean_toilet,0));
        choresListData.add(new ChoresListMode(context.getString(R.string.chores_clean_up_after_pet_name),5,"0000000",R.mipmap.clean_up_after_pet,0));
        choresListData.add(new ChoresListMode(context.getString(R.string.chores_do_homework_name),6,"0000000",R.mipmap.do_homework,0));
        choresListData.add(new ChoresListMode(context.getString(R.string.chores_dry_dishes_name),7,"0000000",R.mipmap.dry_dishes,0));
        choresListData.add(new ChoresListMode(context.getString(R.string.chores_empty_the_dis_name),8,"0000000",R.mipmap.empty_the_dishwasher,0));
        choresListData.add(new ChoresListMode(context.getString(R.string.chores_floss_name),9,"0000000",R.mipmap.f_floss,0));
        choresListData.add(new ChoresListMode(context.getString(R.string.chores_fold_the_laundry_name),10,"0000000",R.mipmap.fold_the_laundry,0));
        choresListData.add(new ChoresListMode(context.getString(R.string.chores_hang_clothes_name),11,"0000000",R.mipmap.hang_clothes,0));
        choresListData.add(new ChoresListMode(context.getString(R.string.chores_help_cook_name),12,"0000000",R.mipmap.help_cook,0));
        choresListData.add(new ChoresListMode(context.getString(R.string.chores_iron_clothes_name),13,"0000000",R.mipmap.iron_clothes,0));
        choresListData.add(new ChoresListMode(context.getString(R.string.chores_sweep_the_floor_name),14,"0000000",R.mipmap.sweep_the_floor,0));
        choresListData.add(new ChoresListMode(context.getString(R.string.chores_take_out_the_trash_name),15,"0000000",R.mipmap.take_out_the_trash,0));
        choresListData.add(new ChoresListMode(context.getString(R.string.chores_unload_washing_machine_name),16,"0000000",R.mipmap.unload_washing_machine,0));
        choresListData.add(new ChoresListMode(context.getString(R.string.chores_vaccum_name),17,"0000000",R.mipmap.v_vaccum,0));
        choresListData.add(new ChoresListMode(context.getString(R.string.chores_wash_dishes_name),18,"0000000",R.mipmap.wash_dishes,0));

        rewardsListData=new ArrayList<>();
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_allowance_icon_name),1,"0000000",R.mipmap.allowance_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_arts_and_crafts_icon_name),2,"0000000",R.mipmap.arts_and_crafts_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_bedtime_story_name),3,"0000000",R.mipmap.bedtime_story,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_bike_ride_name),4,"0000000",R.mipmap.bike_ride,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_buy_app_name),5,"0000000",R.mipmap.buy_app,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_buy_clothes_name),6,"0000000",R.mipmap.buy_clothes,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_choose_an_activity_name),7,"0000000",R.mipmap.choose_an_activity,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_choose_breakfast_name),8,"0000000",R.mipmap.choose_breakfast,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_choose_dinner_name),9,"0000000",R.mipmap.choose_dinner,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_choose_extra_snack_icon_name),10,"0000000",R.mipmap.choose_extra_snack_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_drawing_icon_name),11,"0000000",R.mipmap.drawing_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_extra_computer_time_name),12,"0000000",R.mipmap.extra_computer_time_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_extra_screen_time_icon_name),13,"0000000",R.mipmap.extra_screen_time_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_extra_tv_time_icon_name),14,"0000000",R.mipmap.extra_tv_time_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_generic_reward_icon_name),15,"0000000",R.mipmap.generic_reward_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_go_swimming_icon_name),16,"0000000",R.mipmap.go_swimming_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_go_to_amusement_park_icon_name),17,"0000000",R.mipmap.go_to_amusement_park_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_go_to_playground_icon_name),18,"0000000",R.mipmap.go_to_playground_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_go_to_the_beach_icon_name),18,"0000000",R.mipmap.go_to_the_beach_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_go_to_the_mall_icon_name),18,"0000000",R.mipmap.go_to_the_mall_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_go_to_the_movies_icon_name),18,"0000000",R.mipmap.go_to_the_movies_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_go_to_the_zoo_icon_name),18,"0000000",R.mipmap.go_to_the_zoo_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_ice_cream_icon_name),18,"0000000",R.mipmap.ice_cream_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_pack_lunch_name),18,"0000000",R.mipmap.pack_lunch,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_play_date_icon_name),18,"0000000",R.mipmap.play_date_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_play_outside_icon_name),18,"0000000",R.mipmap.play_outside_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_play_sports_icon_name),18,"0000000",R.mipmap.play_sports_icon,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_play_video_games_name),18,"0000000",R.mipmap.play_video_games,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_read_a_book_name),18,"0000000",R.mipmap.read_a_book,0));
        rewardsListData.add(new RewardsListMode(context.getString(R.string.rewards_t_toys_name),18,"0000000",R.mipmap.t_toys,0));
    }
    public static boolean weatherPrint=true;
/***********************体重身高*********************************/
    public static List<String> left_height_imperilal_Int;
    public final static int LEFT_IMPERILAL_INT_START = 3;
    public final static int LEFT_IMPERILAL_INT_NUM = 5;
    public static List<String> right_height_imperilal_Int;
    public final static int RIGHT_IMPERILAL_INT_START = 0;
    public final static int RIGHT_IMPERILAL_INT_NUM = 12;

    public static List<String> left_height_metric_Int;
    public final static int LEFT_METRIC_INT_START = 90;
    public final static int LEFT_METRIC_INT_NUM = 151;

    //这个是可以共用的
    public static List<String> height_metric_Int;
    public final static int RIGHT_METRIC_INT_START = 0;
    public final static int RIGHT_METRIC_INT_NUM = 10;

    public static List<String> left_weight_imperilal_Int;
    public final static int LEFT_WEIGHT_IMPERILAL_INT_START = 70;
    public final static int LEFT_WEIGHT_IMPERILAL_INT_NUM = 901;

    public static List<String> left_weight_metric_Int;
    public final static int LEFT_WEIGHT_METRIC_INT_START = 30;
    public final static int LEFT_WEIGHT_METRIC_INT_NUM = 371;

    public static List<String> preset_sleep_time_h;
    public static List<String> preset_sleep_time_m;
    public static List<String> arrStep;
    public static List<String>  arrAlertInterval ;

    public static List<ChoresListMode> choresListData;
    public static List<RewardsListMode> rewardsListData;
/**********************************************************************/
    public static boolean sleepState=false;                                                         //睡眠状态，默认为false
}
