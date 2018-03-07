package com.lshan.boilerfaves.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.lshan.boilerfaves.Networking.MenuRetrievalTask;
import com.lshan.boilerfaves.Receivers.AlarmReceiver;
import com.lshan.boilerfaves.Utils.TimeHelper;

/**
 * Created by lshan on 3/1/2018.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NotificationJobService extends JobService{

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        System.out.println("In Job Service");

        Context context = getApplicationContext();

        Toast.makeText(context, "In Job Service", Toast.LENGTH_LONG);

        new MenuRetrievalTask(context, null).execute();

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + TimeHelper.getMillisUntil(1, 0), pendingIntent);

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

}
