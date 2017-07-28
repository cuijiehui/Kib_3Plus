package com.example.administrator.kib_3plus;

import android.content.Context;
import android.widget.ListView;

import com.example.administrator.kib_3plus.mode.ChoresListMode;
import com.example.administrator.kib_3plus.mode.RaceContinentListMode;
import com.example.administrator.kib_3plus.mode.RewardsListMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        europeContinent=new ArrayList<>();
        europeContinent.add(new RaceContinentListMode(context.getString(R.string.continent_europe_tower_tv),"300",R.mipmap.tower_of_pisa_icon));
        europeContinent.add(new RaceContinentListMode(context.getString(R.string.continent_europe_arc_tv),"330",R.mipmap.arc_de_triumph_icon));
        europeContinent.add(new RaceContinentListMode(context.getString(R.string.continent_europe_big_tv),"400",R.mipmap.big_ben_icon));
        europeContinent.add(new RaceContinentListMode(context.getString(R.string.continent_europe_london_tv),"650",R.mipmap.london_tower_bridge_icon));
        europeContinent.add(new RaceContinentListMode(context.getString(R.string.continent_europe_axe_tv),"1000",R.mipmap.st_mary_axe_icon));
        europeContinent.add(new RaceContinentListMode(context.getString(R.string.continent_europe_eiffel_tv),"1700",R.mipmap.eiffel_tower));
        europeContinent.add(new RaceContinentListMode(context.getString(R.string.continent_europe_gate_tv),"2000",R.mipmap.brandenbug_gate_icon));
        europeContinent.add(new RaceContinentListMode(context.getString(R.string.continent_europe_milan_tv),"3000",R.mipmap.milan_cathedral_icon));
        europeContinent.add(new RaceContinentListMode(context.getString(R.string.continent_europe_cathedral_tv),"5000",R.mipmap.st_basils_catherdral_icon));
        europeContinent.add(new RaceContinentListMode(context.getString(R.string.continent_europe_stonehenge_tv),"7500",R.mipmap.stonehenge_icon));
        europeContinent.add(new RaceContinentListMode(context.getString(R.string.continent_europe_windmills_tv),"9000",R.mipmap.windmills_of_kinderdijk_icon));
        europeContinent.add(new RaceContinentListMode(context.getString(R.string.continent_europe_parthenon_tv),"12600",R.mipmap.parthenon_icon));
        europeContinent.add(new RaceContinentListMode(context.getString(R.string.continent_europe_colosseum_tv),"15000",R.mipmap.colosseum_icon));
        europeContinent.add(new RaceContinentListMode(context.getString(R.string.continent_europe_matterhorn_tv),"17000",R.mipmap.matterhorn_icon));

        africaContinent=new ArrayList<>();
        africaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_africa_sphinx_tv),"750",R.mipmap.sphinx_icon));
        africaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_africa_giza_tv),"850",R.mipmap.giza_pyramids_icon));
        africaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_africa_cape_tv),"1000",R.mipmap.tower_of_pisa_icon));
        africaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_africa_mosque_tv),"2500",R.mipmap.mosque_of_touba_icon));
        africaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_africa_freetown_tv),"3500",R.mipmap.freetown_mosque_icon));
        africaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_africa_victiria_tv),"6000",R.mipmap.victoria_falls_icon));
        africaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_africa_cairo_tv),"7000",R.mipmap.cairo_citadel));
        africaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_africa_mt_tv),"150000",R.mipmap.mt_kilimanjaro_icon));


        asiaContinent=new ArrayList<>();
        asiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_asia_itsukushima_tv),"500",R.mipmap.itsukushima_shrine_icon));
        asiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_asia_angkor_tv),"750",R.mipmap.angkor_wat_icon));
        asiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_asia_gate_tv),"800",R.mipmap.gate_of_india_icon));
        asiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_asia_tokyo_tv),"900",R.mipmap.sphinx_icon));
        asiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_asia_osaka_tv),"1000",R.mipmap.osaka_castle));
        asiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_asia_temple_tv),"1200",R.mipmap.temple_of_heaven_icon));
        asiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_asia_merlion_tv),"2500",R.mipmap.merlion_icon));
        asiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_asia_taj_tv),"2800",R.mipmap.taj_mahal_icon));
        asiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_asia_summer_tv),"3000",R.mipmap.summer_palace_icon));
        asiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_asia_taipei_tv),"4500",R.mipmap.taipei_icon));
        asiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_asia_kotoku_tv),"7000",R.mipmap.kotoku_in));
        asiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_asia_forbidden_tv),"9500",R.mipmap.forbidden_city_icon));
        asiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_asia_fuji_tv),"15000",R.mipmap.mt_fuji));
        asiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_asia_great_tv),"25000",R.mipmap.great_wall_icon));


        australiaContinent=new ArrayList<>();
        australiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_australia_syndey_tv),"1000",R.mipmap.sydney_harbor_bridge_icon));
        australiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_australia_house_tv),"2000",R.mipmap.sydney_opera_house_icon));
        australiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_australia_royal_tv),"3500",R.mipmap.royal_building_icon));
        australiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_australia_uluru_tv),"5500",R.mipmap.uiuru_icon));
        australiaContinent.add(new RaceContinentListMode(context.getString(R.string.continent_australia_great_tv),"8000",R.mipmap.great_barrier_reef_icon));

        northContinent=new ArrayList<>();
        northContinent.add(new RaceContinentListMode(context.getString(R.string.continent_north_statue_tv),"350",R.mipmap.statue_of_liberty_icon));
        northContinent.add(new RaceContinentListMode(context.getString(R.string.continent_north_space_tv),"850",R.mipmap.space_needle_icon));
        northContinent.add(new RaceContinentListMode(context.getString(R.string.continent_north_gateway_tv),"1250",R.mipmap.gateway_arch_icon));
        northContinent.add(new RaceContinentListMode(context.getString(R.string.continent_north_mount_tv),"1250",R.mipmap.mount_rushmore_icon));
        northContinent.add(new RaceContinentListMode(context.getString(R.string.continent_north_empire_tv),"1580",R.mipmap.empire_state_building_icon));
        northContinent.add(new RaceContinentListMode(context.getString(R.string.continent_north_tower_tv),"1770",R.mipmap.cn_tower_icon));
        northContinent.add(new RaceContinentListMode(context.getString(R.string.continent_north_washington_tv),"1900",R.mipmap.washington_monument_icon));
        northContinent.add(new RaceContinentListMode(context.getString(R.string.continent_north_lincoln_tv),"1900",R.mipmap.lincoln_memorial_icon));
        northContinent.add(new RaceContinentListMode(context.getString(R.string.continent_north_golden_tv),"2750",R.mipmap.golden_gate_bridge_icon));
        northContinent.add(new RaceContinentListMode(context.getString(R.string.continent_north_square_tv),"3000",R.mipmap.ny_times_square_icon));
        northContinent.add(new RaceContinentListMode(context.getString(R.string.continent_north_sitka_tv),"4250",R.mipmap.sitka_totem_pole));
        northContinent.add(new RaceContinentListMode(context.getString(R.string.continent_north_hollywood_tv),"6500",R.mipmap.hollywood_icon));
        northContinent.add(new RaceContinentListMode(context.getString(R.string.continent_north_las_tv),"8900",R.mipmap.las_vegas_icon));
        northContinent.add(new RaceContinentListMode(context.getString(R.string.continent_north_niagra_tv),"9000",R.mipmap.niagara_falls_icon));
        northContinent.add(new RaceContinentListMode(context.getString(R.string.continent_north_grand_tv),"15000",R.mipmap.grand_canyon_icon));
        northContinent.add(new RaceContinentListMode(context.getString(R.string.continent_north_route_tv),"26000",R.mipmap.route_66_icon));


        southContinent=new ArrayList<>();
        southContinent.add(new RaceContinentListMode(context.getString(R.string.continent_south_pyramid_tv),"250",R.mipmap.pyramid_of_the_sun_icon));
        southContinent.add(new RaceContinentListMode(context.getString(R.string.continent_south_iguazu_tv),"2300",R.mipmap.iguazu_falls_icon));
        southContinent.add(new RaceContinentListMode(context.getString(R.string.continent_south_chichen_tv),"4600",R.mipmap.chicken_itza_icon));
        southContinent.add(new RaceContinentListMode(context.getString(R.string.continent_south_copacobana_tv),"5000",R.mipmap.copacabana_beach_icon));
        southContinent.add(new RaceContinentListMode(context.getString(R.string.continent_south_corcovado_tv),"6000",R.mipmap.copacabana_beach_icon));
        southContinent.add(new RaceContinentListMode(context.getString(R.string.continent_south_chan_tv),"8000",R.mipmap.chan_chan_icon));
        southContinent.add(new RaceContinentListMode(context.getString(R.string.continent_south_galapagos_tv),"10000",R.mipmap.galapagos_island_icon));
        southContinent.add(new RaceContinentListMode(context.getString(R.string.continent_south_moai_tv),"11000",R.mipmap.moai_head_easter_island_icon));
        southContinent.add(new RaceContinentListMode(context.getString(R.string.continent_south_machu_tv),"14000",R.mipmap.machu_pichu_icon));
        southContinent.add(new RaceContinentListMode(context.getString(R.string.continent_south_mount_tv),"15000",R.mipmap.mount_roraima_icon));

        iconListData=new ArrayList<>();
        iconListData.add(R.mipmap.b_bison);
        iconListData.add(R.mipmap.bear);
        iconListData.add(R.mipmap.c_cheetah);
        iconListData.add(R.mipmap.c_cow);
        iconListData.add(R.mipmap.cat);
        iconListData.add(R.mipmap.cat_2);
        iconListData.add(R.mipmap.d_deer);
        iconListData.add(R.mipmap.dog_3);
        iconListData.add(R.mipmap.dog_4);
        iconListData.add(R.mipmap.dog_5);
        iconListData.add(R.mipmap.duck_2);
        iconListData.add(R.mipmap.e_elephant);
        iconListData.add(R.mipmap.f_ferret);
        iconListData.add(R.mipmap.f_frog);
        iconListData.add(R.mipmap.fox_2);
        iconListData.add(R.mipmap.g_giraffe);
        iconListData.add(R.mipmap.g_gorilla);
        iconListData.add(R.mipmap.h_hamster);
        iconListData.add(R.mipmap.h_hedgehog);
        iconListData.add(R.mipmap.h_hippo);
        iconListData.add(R.mipmap.h_horse);
        iconListData.add(R.mipmap.k_koala);
        iconListData.add(R.mipmap.lionmale);
        iconListData.add(R.mipmap.m_monkey);
        iconListData.add(R.mipmap.moose);
        iconListData.add(R.mipmap.owl);
        iconListData.add(R.mipmap.ox);
        iconListData.add(R.mipmap.penguin);
        iconListData.add(R.mipmap.pig);
        iconListData.add(R.mipmap.raccoon);
        iconListData.add(R.mipmap.rhino);
        iconListData.add(R.mipmap.sloth);
        iconListData.add(R.mipmap.tiger);
        iconListData.add(R.mipmap.walrus);
        iconListData.add(R.mipmap.water_buffalo);
        iconListData.add(R.mipmap.zebra);

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

    public static List<RaceContinentListMode> europeContinent;
    public static List<RaceContinentListMode> africaContinent;
    public static List<RaceContinentListMode> asiaContinent;
    public static List<RaceContinentListMode> australiaContinent;
    public static List<RaceContinentListMode> northContinent;
    public static List<RaceContinentListMode> southContinent;

    public static List<Integer> iconListData;
/**********************************************************************/
    public static boolean sleepState=false;                                                         //睡眠状态，默认为false

    public static Calendar curShowCal = null;        // 当前详细睡眠界面显示的日期
    public static boolean isScrollable = true;        // ViewPager是否可滑动

}
