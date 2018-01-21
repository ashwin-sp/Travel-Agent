package actio.ashcompany.com.travelagentv11.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import actio.ashcompany.com.travelagentv11.database.AgentRepository;

/**
 * Created by ashwin-4529 on 21/01/18.
 */

public class LoggerViewModel extends AndroidViewModel {

    private AgentRepository mRepository;
    private String name;
    private int phno;
    private int reg;
    public LoggerViewModel (Application application, String username, String password) {
        super(application);
        mRepository = new AgentRepository(application, username, password);
        name = mRepository.getName();
        phno = mRepository.getPhno();
        reg = mRepository.getReg();
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

    public void insert(LoggerModel loggerModel) { mRepository.insert(loggerModel); }
}
