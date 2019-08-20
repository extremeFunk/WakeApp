package io.rainrobot.wake.android.eventdatabase;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {AlarmEntity.class}, version = 1, exportSchema = false)
public abstract class AndroidRoomDatabase extends RoomDatabase {
    public abstract AlarmRoomDao alarmRoomDao();
}
