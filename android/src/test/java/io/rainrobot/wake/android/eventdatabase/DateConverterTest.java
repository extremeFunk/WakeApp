package io.rainrobot.wake.android.eventdatabase;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class DateConverterTest {


    @Test
    public void toDate() {
        DateConverter con = new DateConverter();

        Date date = new Date() ;
        Long aLong = con.fromDate(date);
        Date date1 = con.toDate(aLong);

        assertEquals(date, date1);
    }
}