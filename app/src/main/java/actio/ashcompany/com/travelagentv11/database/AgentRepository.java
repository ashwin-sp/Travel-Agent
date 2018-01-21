package actio.ashcompany.com.travelagentv11.database;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import actio.ashcompany.com.travelagentv11.model.LoggerModel;

/**
 * Created by ashwin-4529 on 21/01/18.
 */

public class AgentRepository {

    private String name;
    private int phno;
    private int reg;


    private AgentDao agentDao;

    public AgentRepository(Application application, String username, String password) {
        AgentDatabase db = AgentDatabase.getDatabase(application);
        agentDao = db.agentDao();
        name = agentDao.getName(username, password);
        phno = agentDao.getPhno(username, password);
        reg = agentDao.getReg(username, password);
    }

    public String getName() {
        return name;
    }

    public int getPhno() {
        return phno;
    }

    public int getReg() {
        return reg;
    }

    public void insert(LoggerModel loggerModel) {
        new insertAsyncTask(agentDao).execute(loggerModel);
    }

    private static class insertAsyncTask extends AsyncTask<LoggerModel, Void, Void> {

        private AgentDao mAsyncTaskDao;

        insertAsyncTask(AgentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final LoggerModel... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
