package com.example.myapplication.utils; // Adjust package if necessary

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    private static final String TAG = "FileUtils";

    /**
     * Writes a string to a file in the application's internal storage.
     *
     * @param context  Context
     * @param filename The name of the file.
     * @param content  The string content to write.
     * @return true if writing was successful, false otherwise.
     */
    public static boolean writeStringToFile(Context context, String filename, String content) {
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
            osw.write(content);
            return true;
        } catch (IOException e) {
            android.util.Log.e(TAG, "Error writing to file " + filename, e);
            return false;
        }
    }

    /**
     * Reads a string from a file in the application's internal storage.
     *
     * @param context  Context
     * @param filename The name of the file.
     * @return The file content as a string, or null if an error occurred.
     */
    public static String readStringFromFile(Context context, String filename) {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fis = context.openFileInput(filename);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(isr)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            android.util.Log.e(TAG, "Error reading from file " + filename, e);
            return null;
        }
    }

    /**
     * Deletes a file from the application's internal storage.
     *
     * @param context  Context
     * @param filename The name of the file to delete.
     * @return true if the file was successfully deleted, false otherwise.
     */
    public static boolean deleteInternalFile(Context context, String filename) {
        File file = new File(context.getFilesDir(), filename);
        if (file.exists()) {
            if (file.delete()) {
                return true;
            } else {
                android.util.Log.e(TAG, "Failed to delete file: " + filename);
                return false;
            }
        }
        return true; // File doesn't exist, so considered deleted
    }
}
