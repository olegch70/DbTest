package com.app.dbtest;

import android.content.Context;
import android.util.Log;
import com.app.dbtest.dto.BillsDto;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Created by oleg on 25.05.13.
 */
public class DbFiller {
    private static String[] kind = {"Еда", "Обед", "Прочее", "Машина"};
    private static String[] description = {"", "Носки", "Олег", "Книги", "Брюки"};

    private static Db db;

    public static void fillDb(Context ctx, boolean clearBeforeFill) {
        db = new Db(ctx);
        db.open();
        if(clearBeforeFill) {
            db.clear();
        }

        Random rnd = new Random();
        Calendar calendar = new GregorianCalendar();
        Date currentDate = new Date();
        calendar.setTime(currentDate);
        Calendar calendarCurretDateWoTime = new GregorianCalendar(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        Date currentDateWoTime = calendarCurretDateWoTime.getTime();
        BillsDto dto = new BillsDto();
        for(int i = 0; i < 30; i++) {
            // Сумма платежа
            dto.cash = ""+rnd.nextInt(30000)+"."+rnd.nextInt(10)+rnd.nextInt(10);
            //                         Рубли     копейки

            // Установим дату платежа - это дата без времени
            calendar.setTime(currentDateWoTime);
            int _day = rnd.nextInt(60);
            calendar.add(Calendar.DAY_OF_MONTH, - _day );
            dto.payDate = calendar.getTime();

            // Установим дату ввода чека в базу данных - это дата с временем
            calendar.setTime(currentDate);
            while(true) {
                try {
                    int seconds = _day == 0 ? 0 : rnd.nextInt(_day * 24 * 60 * 60 ) - 60 * 60;
                    if(seconds < 0) {
                        seconds = 0;
                    }
                    Log.d("fillDb", "seconds "+ seconds);
                    calendar.add(Calendar.SECOND, -seconds );
                    break;
                } catch (Exception ignore) {
                    Log.d("fillDb", ignore.toString());
                }
            }
            dto.inputDate = calendar.getTime();


            dto.kind = kind[rnd.nextInt(kind.length)];
            dto.description = description[rnd.nextInt(kind.length)];

            db.insert(dto);
        }


        db.close();
    }
}
