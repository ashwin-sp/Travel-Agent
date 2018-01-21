package actio.ashcompany.com.travelagentv11.factory;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import actio.ashcompany.com.travelagentv11.model.LoggerViewModel;

/**
 * Created by ashwin-4529 on 21/01/18.
 */

public class LoggerFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;
    private String username;
    private String password;


    public LoggerFactory(Application application, String username, String password) {
        mApplication = application;
        this.username = username;
        this.password = password;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new LoggerViewModel(mApplication, username, password);
    }
}