package com.example.administrator.restoapp.utils;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kamlesh on 024 24/11/15.
 */
public class TextValidationUtils {

    public static boolean isEmpty(EditText et) {
        return TextUtils.isEmpty(et.getText().toString());
    }

    public static String convertListToString(List<String> list) {

        return android.text.TextUtils.join(",", list);

    }

    public static List<String> convertStringToList(String string) {

        return Arrays.asList(string.split("\\s*,\\s*"));

    }

    public static boolean isEmailValid(EditText etEmail) {
        String email = etEmail.getText().toString();
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if (pattern.matcher(email).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isTextLengthValid(EditText et, int length) {
        return et.getText().toString().length() >= length;
    }

    public static boolean isTextLengthValid(EditText et, int minLength, int maxLength) {
        return et.getText().toString().length() >= minLength && et.getText().toString().length() <= maxLength;
    }

    public static boolean isPasswordTextValid(EditText etPwd) {
        String pwd = etPwd.getText().toString();
        Matcher matcher = Pattern.compile("[a-zA-Z[0-9][.][-][_]]+").matcher(pwd);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean areIdentical(EditText etNewPwd, EditText etCnfNewPwd) {
        if (etNewPwd.getText().toString().equals(etCnfNewPwd.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }
}
