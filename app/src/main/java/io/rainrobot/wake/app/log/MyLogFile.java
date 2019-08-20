package io.rainrobot.wake.app.log;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class MyLogFile {
    PrintWriter writer = null;
    public MyLogFile() {
        try {
            writer = new PrintWriter("log.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void log(String s) {
        writer.println(s);
        writer.close();
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public void log(Exception e) {
        writer.write(e.getMessage());

        StackTraceElement[] trace = e.getStackTrace();
        for(StackTraceElement s : trace) {
            writer.write(s.toString());
        }

        writer.close();
    }
}
