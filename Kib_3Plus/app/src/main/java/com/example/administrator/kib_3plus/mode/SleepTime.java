package com.example.administrator.kib_3plus.mode;

import java.io.Serializable;

/**
 * Created by cui on 2017/7/28.
 */

public class SleepTime  implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1231L;
    public long startTime;
    public long endTime;




    public SleepTime(){};

    public SleepTime(long startTime , long endTime)
    {
        super();
        this.startTime = startTime;
        this.endTime = endTime;
    }


    @Override
    public String toString()
    {
        return "startTime:" + startTime  +" endTime:"+ endTime ;
    }


}
