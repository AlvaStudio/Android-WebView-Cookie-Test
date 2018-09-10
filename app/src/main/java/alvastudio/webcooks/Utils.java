package alvastudio.webcooks;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.Gravity;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alexsid on 08.05.2018.
 */

public class Utils {
    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static void showToast(String text, Context context) {
        Toast toast = Toast.makeText(context,
                text,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static String getVersionInfo(Context context) {
        PackageManager manager = context.getPackageManager();
        String appVersion = "";
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            appVersion = info.versionName +" " + info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            appVersion = "";
            e.printStackTrace();
        }
        return appVersion;
    }
}
