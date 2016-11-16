package com.example.UIhelper;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by dzt on 2015-7-1.
 */
public class LogUtils {
	public static final int VERBOSE = 1;
	public static final int DEBUG = 2;
	public static final int INFO = 3;
	public static final int WARN = 4;
	public static final int ERROR = 5;
	public static final int NOTHING = 6;
	public static final int LEVEL = VERBOSE;
	public static final String SEPARATOR = ",";
	private static final boolean isTag = true;
	private static final String TAG = "mobilenumbelong";

	public static void v(String message) {
		if (LEVEL <= VERBOSE) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			String tag = getDefaultTag(stackTraceElement);
			Log.v(tag, getLogInfo(stackTraceElement) + message);
		}
	}

	public static void v(String tag, String message) {
		if (LEVEL <= VERBOSE) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			if (TextUtils.isEmpty(tag)) {
				tag = getDefaultTag(stackTraceElement);
			}
			if (isTag)
				tag = TAG;
			Log.v(tag, getLogInfo(stackTraceElement) + message);
		}
	}

	public static void d(String message) {
		if (LEVEL <= DEBUG) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			String tag = getDefaultTag(stackTraceElement);
			Log.d(tag, getLogInfo(stackTraceElement) + message);
		}
	}

	public static void d(String tag, String message) {
		if (LEVEL <= DEBUG) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			if (TextUtils.isEmpty(tag)) {
				tag = getDefaultTag(stackTraceElement);
			}
			if (isTag)
				tag = TAG;
			Log.d(tag, getLogInfo(stackTraceElement) + message);
		}
	}

	public static void i(String message) {
		if (LEVEL <= INFO) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			String tag = getDefaultTag(stackTraceElement);
			Log.i(tag, getLogInfo(stackTraceElement) + message);
		}
	}

	public static void i(String tag, String message) {
		if (LEVEL <= INFO) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			if (TextUtils.isEmpty(tag)) {
				tag = getDefaultTag(stackTraceElement);
			}
			if (isTag)
				tag = TAG;
			Log.i(tag, getLogInfo(stackTraceElement) + message);
		}
	}

	public static void w(String message) {
		if (LEVEL <= WARN) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			String tag = getDefaultTag(stackTraceElement);
			Log.w(tag, getLogInfo(stackTraceElement) + message);
		}
	}

	public static void w(String tag, String message) {
		if (LEVEL <= WARN) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			if (TextUtils.isEmpty(tag)) {
				tag = getDefaultTag(stackTraceElement);
			}
			if (isTag)
				tag = TAG;
			Log.w(tag, getLogInfo(stackTraceElement) + message);
		}
	}

	public static void e(String message) {
		if (LEVEL <= ERROR) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			String tag = getDefaultTag(stackTraceElement);
			Log.e(tag, getLogInfo(stackTraceElement) + message);
		}
	}

	public static void e(String tag, String message) {
		if (LEVEL <= ERROR) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			if (TextUtils.isEmpty(tag)) {
				tag = getDefaultTag(stackTraceElement);
			}
			if (isTag)
				tag = TAG;
			Log.e(tag, getLogInfo(stackTraceElement) + message);
		}
	}

	public static void debugLog(Exception e) {
		if (LEVEL <= NOTHING) {
			e.printStackTrace();
		}
	}

	public static void printStack() {
		if (LEVEL <= NOTHING) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			String tag = getDefaultTag(stackTraceElement);
			Log.w(tag, Log.getStackTraceString(new Throwable()));
		}
	}

	public static String getDefaultTag(StackTraceElement stackTraceElement) {
		String fileName = stackTraceElement.getFileName();
		String stringArray[] = fileName.split("\\.");
		String tag = stringArray[0];
		if (isTag)
			tag = TAG;
		return tag;
	}

	public static String getLogInfo(StackTraceElement stackTraceElement) {
		StringBuilder logInfoStringBuilder = new StringBuilder();

		long threadID = Thread.currentThread().getId();

		// String fileName = stackTraceElement.getFileName();

		// String className = stackTraceElement.getClassName();

		String methodName = stackTraceElement.getMethodName();

		int lineNumber = stackTraceElement.getLineNumber();

		logInfoStringBuilder.append("[ ");
		logInfoStringBuilder.append("threadID=" + threadID).append(SEPARATOR);
		logInfoStringBuilder.append("methodName=" + methodName).append(
				SEPARATOR);
		logInfoStringBuilder.append("lineNumber=" + lineNumber);
		logInfoStringBuilder.append(" ] ");
		return logInfoStringBuilder.toString();
	}
}
