package com.example.team8.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.team8.database.dao.UserDao;
import com.example.team8.database.model.User;
import com.example.team8.util.Constant;



@Database(entities={User.class},version=1)

public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();

    private static UserDatabase userDB;

    public static UserDatabase getInstance(Context context) {
        if (userDB== null){
            userDB = buildDatabaseBuilder(context);

        }
        return userDB;
    }


    //creacion base de datos
    private static UserDatabase buildDatabaseBuilder(Context context) {
        return Room.databaseBuilder(context, UserDatabase.class, Constant.NAME_DATABASE).allowMainThreadQueries().build();

    }

}


