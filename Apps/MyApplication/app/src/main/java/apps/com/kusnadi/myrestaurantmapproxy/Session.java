package apps.com.kusnadi.myrestaurantmapproxy;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kusnadi on 11/5/2016.
 */

public class Session {
    public static final String SHARED_PREF_NAME = "mytripapp";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context ctx;

    public Session(Context ctx) {
        this.ctx = ctx;
        sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoggedin(boolean logggedin) {
        editor.putBoolean("loggedInmode", logggedin);
        editor.commit();
    }

    public boolean loggedin() {
        return sharedPreferences.getBoolean("loggedInmode", false);
    }

}
