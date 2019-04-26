package rounima.com.sha1;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ArrowKeyMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    BaseAdapter adapter;
    List<AppInfo> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取应用列表
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(PackageManager.GET_SIGNATURES);
        for(PackageInfo info : packages){
            if ((info.applicationInfo.flags&ApplicationInfo.FLAG_SYSTEM) == 0) {//只获取非系统自带应用
                AppInfo app = new AppInfo();
                app.setName(info.applicationInfo.loadLabel(getPackageManager()).toString());
                app.setPackageName(info.packageName);
                app.setVersionName(info.versionName);
                app.setVersionCode(info.versionCode+"");
                app.setIcon(info.applicationInfo.loadIcon(getPackageManager()));
                app.setPackageInfo(info);
                list.add(app);
            }
        }
        listView = findViewById(R.id.list_view);
        if(adapter == null){
            adapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return list.size();
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                    View view = null;
                    if (convertView == null) {//因为getView()返回的对象，adapter会自动赋给ListView
                        view = inflater.inflate(R.layout.list_item, null);
                    } else {
                        view = convertView;
                    }
                    ImageView img = view.findViewById(R.id.img);
                    img.setImageDrawable(list.get(position).getIcon());
                    TextView name = view.findViewById(R.id.name);
                    name.setText(list.get(position).getName());
                    return view;
                }
            };
        }else{
            adapter.notifyDataSetChanged();
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v = View.inflate(MainActivity.this,R.layout.dialog_view,null);
                ImageView img = v.findViewById(R.id.img);
                img.setImageDrawable(list.get(position).getIcon());
                TextView name = v.findViewById(R.id.name);
                name.setText(list.get(position).getName());
                TextView packageName = v.findViewById(R.id.package_name);
                packageName.setText(list.get(position).getPackageName());
                packageName.setTextIsSelectable(true);
                TextView md5 = v.findViewById(R.id.md5);
                md5.setTextIsSelectable(true);
                md5.setText(MD5(list.get(position).getPackageInfo()));
                TextView sha1 = v.findViewById(R.id.sha1);
                sha1.setText(SHA1(list.get(position).getPackageInfo()));
                sha1.setTextIsSelectable(true);
                TextView version = v.findViewById(R.id.version);
                version.setText("版本名:"+list.get(position).getVersionName()+";版本号:"+list.get(position).getVersionCode());
                customAlertDialog(MainActivity.this,v,true);
            }
        });
    }
    public AlertDialog customAlertDialog(Context ctx, View view,boolean cancelable){
        return new AlertDialog.Builder(ctx)
                .setView(view)
                .setCancelable(cancelable)
                .show();
    }
    public  String SHA1(PackageInfo info) {
        try {
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String MD5(PackageInfo info) {
        byte[] paramArrayOfByte = info.signatures[0].toByteArray();
        MessageDigest localMessageDigest = null;
        try {
            localMessageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        localMessageDigest.update(paramArrayOfByte);
        return toHexString(localMessageDigest.digest());
    }

    public String toHexString(byte[] paramArrayOfByte) {
        if (paramArrayOfByte == null) {
            return null;
        }
        StringBuilder localStringBuilder = new StringBuilder(2 * paramArrayOfByte.length);
        for (int i = 0; ; i++) {
            if (i >= paramArrayOfByte.length) {
                return localStringBuilder.toString();
            }
            String str = Integer.toString(0xFF & paramArrayOfByte[i], 16);
            if (str.length() == 1) {
                str = "0" + str;
            }
            localStringBuilder.append(str);
        }
    }
}
