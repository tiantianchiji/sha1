package rounima.com.sha1;

import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;

public class AppInfo {
    /**APP名称*/
    private String name;
    /**APP包名*/
    private String packageName;
    /**版本名称*/
    private String versionName;
    /**版本号*/
    private String versionCode;
    /**图标*/
    private Drawable icon;
    /**包信息*/
    private PackageInfo packageInfo;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }
}
