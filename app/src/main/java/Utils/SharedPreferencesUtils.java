package Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtils {


    private static SharedPreferences sharedPreferences;

    private static Editor editor;

    public static SharedPreferences getSharedPreferences(Context context) {
        if (sharedPreferences == null) {
            context = context.getApplicationContext();
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public static Editor getEditor(Context context) {
        if (sharedPreferences == null) {
            getSharedPreferences(context);
        }
        editor = sharedPreferences.edit();
        return editor;
    }

    public static void save(Context context, String key, String value) {
        if (editor == null) {
            getEditor(context);
        }
        editor.putString(key, value);
        editor.apply();
    }


}
