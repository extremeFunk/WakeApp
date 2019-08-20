package io.rainrobot.wake.android.eventdatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AlarmRoomDao {
    @Query("SELECT * FROM alarmentity")
    List<AlarmEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(AlarmEntity... users);

    @Delete
    void delete(AlarmEntity event);

    @Query("DELETE FROM alarmentity")
    void clear();
}
