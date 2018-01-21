package actio.ashcompany.com.travelagentv11.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import actio.ashcompany.com.travelagentv11.model.LoggerModel;

/**
 * Created by ashwin-4529 on 21/01/18.
 */

@Database(entities = {LoggerModel.class}, version = 2)
public abstract class AgentDatabase extends RoomDatabase {

    public abstract AgentDao agentDao();

    private static AgentDatabase INSTANCE;


    public static AgentDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AgentDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AgentDatabase.class, "Travel2.db")
                           /* .addCallback(sRoomDatabaseCallback)*/
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AgentDao mDao;

        PopulateDbAsync(AgentDatabase db) {
            mDao = db.agentDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            return null;
        }
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // old table:: create table LOGGER (name TEXT,age NUMBER,gender TEXT,address TEXT,username TEXT,password TEXT,reg NUMBER,phno NUMBER)
            database.execSQL("ALTER TABLE LOGGER RENAME TO _logger_old");
            database.execSQL("CREATE TABLE LOGGER (name TEXT,age INTEGER NOT NULL,gender TEXT,address TEXT,username TEXT,password TEXT,reg INTEGER PRIMARY KEY NOT NULL,phno INTEGER NOT NULL)");
            database.execSQL("INSERT INTO LOGGER (name, age, gender, address, username, password, reg, phno) SELECT name, age, gender, address, username, password, reg, phno FROM _logger_old");
            database.execSQL("DROP TABLE _logger_old");
        }
    };

}
