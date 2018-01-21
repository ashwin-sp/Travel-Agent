package actio.ashcompany.com.travelagentv11.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import actio.ashcompany.com.travelagentv11.model.LoggerModel;

/**
 * Created by ashwin-4529 on 21/01/18.
 */

@Dao
public interface AgentDao {
    @Insert
    void insert(LoggerModel loggerModel);

    @Query("DELETE FROM LOGGER")
    void deleteAll();

    @Query("SELECT name FROM LOGGER where username=:username AND password=:password")
    String getName(String username, String password);

    @Query("SELECT phno FROM LOGGER where username=:username AND password=:password")
    int getPhno(String username, String password);

    @Query("SELECT reg FROM LOGGER where username=:username AND password=:password")
    int getReg(String username, String password);
}