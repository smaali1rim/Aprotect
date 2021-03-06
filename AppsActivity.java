package com.example.myapplication;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class AppsActivity extends AppCompatActivity {
    String[] permissions = new String[] {"android.permission.ACCESS_CACHE_FILESYSTEM",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.ACCESS_DOWNLOAD_MANAGER",
            "android.permission.ACCESS_DOWNLOAD_MANAGER_ADVANCED",
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS",
            "android.permission.ACCESS_MOCK_LOCATION",
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.ACCESS_WIFI_STATE",
            "android.permission.ACCESS_WIMAX_STATE",
            "android.permission.AUTHENTICATE_ACCOUNTS",
            "android.permission.BATTERY_STATS",
            "android.permission.BLUETOOTH",
            "android.permission.BLUETOOTH_ADMIN",
            "android.permission.BROADCAST_STICKY",
            "android.permission.CALL_PHONE",
            "android.permission.CALL_PRIVILEGED",
            "android.permission.CAMERA",
            "android.permission.CHANGE_COMPONENT_ENABLED_STATE",
            "android.permission.CHANGE_CONFIGURATION",
            "android.permission.CHANGE_NETWORK_STATE",
            "android.permission.CHANGE_WIFI_MULTICAST_STATE",
            "android.permission.CHANGE_WIFI_STATE",
            "android.permission.CHANGE_WIMAX_STATE",
            "android.permission.CLEAR_APP_CACHE",
            "android.permission.DELETE_CACHE_FILES",
            "android.permission.DELETE_PACKAGES",
            "android.permission.DEVICE_POWER",
            "android.permission.DISABLE_KEYGUARD",
            "android.permission.EXPAND_STATUS_BAR",
            "android.permission.FLASHLIGHT",
            "android.permission.GET_ACCOUNTS",
            "android.permission.GET_PACKAGE_SIZE",
            "android.permission.GET_TASKS",
            "android.permission.GLOBAL_SEARCH_CONTROL",
            "android.permission.HARDWARE_TEST",
            "android.permission.INSTALL_PACKAGES",
            "android.permission.INTERNET",
            "android.permission.KILL_BACKGROUND_PROCESSES",
            "android.permission.MANAGE_ACCOUNTS",
            "android.permission.MODIFY_AUDIO_SETTINGS",
            "android.permission.MODIFY_PHONE_STATE",
            "android.permission.MOUNT_UNMOUNT_FILESYSTEMS",
            "android.permission.NFC",
            "android.permission.PERSISTENT_ACTIVITY",
            "android.permission.PROCESS_OUTGOING_CALLS",
            "android.permission.READ_CALENDAR",
            "android.permission.READ_CALL_LOG",
            "android.permission.READ_CONTACTS",
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.READ_LOGS",
            "android.permission.READ_PHONE_STATE",
            "android.permission.READ_SMS",
            "android.permission.READ_SYNC_SETTINGS",
            "android.permission.READ_SYNC_STATS",
            "android.permission.REBOOT",
            "android.permission.RECEIVE_BOOT_COMPLETED",
            "android.permission.RECEIVE_MMS",
            "android.permission.RECEIVE_SMS",
            "android.permission.RECEIVE_WAP_PUSH",
            "android.permission.RECORD_AUDIO",
            "android.permission.RESTART_PACKAGES",
            "android.permission.SEND_DOWNLOAD_COMPLETED_INTENTS",
            "android.permission.SEND_SMS",
            "android.permission.SET_PREFERRED_APPLICATIONS",
            "android.permission.SET_WALLPAPER",
            "android.permission.SET_WALLPAPER_HINTS",
            "android.permission.UPDATE_DEVICE_STATS",
            "android.permission.USE_CREDENTIALS",
            "android.permission.VIBRATE",
            "android.permission.WAKE_LOCK",
            "android.permission.WRITE_APN_SETTINGS",
            "android.permission.WRITE_CALENDAR",
            "android.permission.WRITE_CALL_LOG",
            "android.permission.WRITE_CONTACTS",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.WRITE_SECURE_SETTINGS",
            "android.permission.WRITE_SETTINGS",
            "android.permission.WRITE_SMS",
            "android.permission.WRITE_SYNC_SETTINGS",
            "com.android.alarm.permission.SET_ALARM",
            "com.android.browser.permission.READ_HISTORY_BOOKMARKS",
            "com.android.browser.permission.WRITE_HISTORY_BOOKMARKS",
            "com.android.launcher.permission.INSTALL_SHORTCUT",
            "com.android.launcher.permission.READ_SETTINGS",
            "com.android.launcher.permission.UNINSTALL_SHORTCUT",
            "com.android.launcher.permission.WRITE_SETTINGS"};
    String[] permvec = new String[87];
    String url = "https://android-predict.herokuapp.com/predict";
    private List<AppList> installedApps;
    private AppAdapter installedAppAdapter;
    ListView userInstalledApps;
   public  int n;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        userInstalledApps = (ListView) findViewById(R.id.installed_app_list);

        installedApps = getInstalledApps();
        installedAppAdapter = new AppAdapter(AppsActivity.this, installedApps);
        userInstalledApps.setAdapter(installedAppAdapter);
        userInstalledApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                String[] colors = {" Scan App", " App Info"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AppsActivity.this);
                builder.setTitle("Choose Action")
                        .setItems(colors, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {

                                // The 'which' argument contains the index position of the selected item
                                if (which==0) {
//                                    Intent intent = new Intent(AppsActivity.this,NonMalware.class);
//                                        startActivity(intent);
                                    String[] perms = null;
                                    try {
                                        PackageInfo pi = getPackageManager().getPackageInfo(installedApps.get(i).packages, PackageManager.GET_PERMISSIONS);
                                        perms = pi.requestedPermissions;
//                                        for (int m = 0; m<pi.requestedPermissions.length; m++){
//                                            System.out.println(pi.requestedPermissions[m]);
//                                        }
                                    } catch (PackageManager.NameNotFoundException e) {
                                        e.printStackTrace();
                                    }

                                    //System.out.println(Arrays.asList(getPermissionsByPackageName(installedApps.get(i).packages)));
                                       for (int k=0;k<perms.length;k++){
                                           for(int j=0;j<permissions.length;j++){
                                                  if(Objects.equals(permissions[j], perms[k]))
                                                  { System.out.println(j);
                                                      permvec[j]="1";
                                                  }
                                           }
                                       }
                                       for (int k=0;k<permvec.length;k++){
                                           if(permvec[k]==null){
                                               permvec[k]="0";
                                           }
                                       }
                                    JSONArray permj= new JSONArray();
                                    for (int i = 0; i < permvec.length; i++) {
                                        permj.put(permvec[i]);
                                    }
                                    JSONObject json = new JSONObject();
                                    try {
                                        json.put("permissions",permj);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    JsonObjectRequest req=new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
                                       @Override
                                       public void onResponse(JSONObject response) {

                                           if (response.toString().equals("{\"prediction\":\"0\"}")){
                                               System.out.println(response);
                                               n =0;
                                               PopUpClass popUpClass = new PopUpClass();
                                               popUpClass.showPopupWindow(view,n);
                                           }

                                       }

                                   }, new Response.ErrorListener() {
                                       @Override
                                       public void onErrorResponse(VolleyError error) {
                                           VolleyLog.e("Error: ", error.getMessage());
                                       }
                                   });
                                   RequestQueue queue= Volley.newRequestQueue(AppsActivity.this);
                                    queue.add(req);




                                }
                                if (which==1){
                                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.setData(Uri.parse("package:" + installedApps.get(i).packages));
                                    Toast.makeText(AppsActivity.this, installedApps.get(i).packages, Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                            }
                        });
                builder.show();

            }
        });



    }

    private List<AppList> getInstalledApps() {
        PackageManager pm = getPackageManager();
        List<AppList> apps = new ArrayList<AppList>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        //List<PackageInfo> packs = getPackageManager().getInstalledPackages(PackageManager.GET_PERMISSIONS);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!isSystemPackage(p))) {
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
                String packages = p.applicationInfo.packageName;
                apps.add(new AppList(appName, icon, packages));
            }
        }
        return apps;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    public class AppAdapter extends BaseAdapter {

        public LayoutInflater layoutInflater;
        public List<AppList> listStorage;

        public AppAdapter(Context context, List<AppList> customizedListView) {
            layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listStorage = customizedListView;
        }

        @Override
        public int getCount() {
            return listStorage.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder listViewHolder;
            if(convertView == null){
                listViewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.installed_app_list, parent, false);

                listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.list_app_name);
                listViewHolder.imageInListView = (ImageView)convertView.findViewById(R.id.app_icon);
                listViewHolder.packageInListView=(TextView)convertView.findViewById(R.id.app_package);
                convertView.setTag(listViewHolder);
            }else{
                listViewHolder = (ViewHolder)convertView.getTag();
            }
            listViewHolder.textInListView.setText(listStorage.get(position).getName());
            listViewHolder.imageInListView.setImageDrawable(listStorage.get(position).getIcon());
            listViewHolder.packageInListView.setText(listStorage.get(position).getPackages());

            return convertView;
        }

        class ViewHolder{
            TextView textInListView;
            ImageView imageInListView;
            TextView packageInListView;
        }
    }

    public class AppList {
        private String name;
        Drawable icon;
        private String packages;
        public AppList(String name, Drawable icon, String packages) {
            this.name = name;
            this.icon = icon;
            this.packages = packages;
        }
        public String getName() {
            return name;
        }
        public Drawable getIcon() {
            return icon;
        }
        public String getPackages() {
            return packages;
        }

    }


    // Custom method to get app requested and granted permissions from package name
    protected String getPermissionsByPackageName(String packageName){
        // Initialize a new string builder instance
        StringBuilder builder = new StringBuilder();

        try {
            // Get the package info
            PackageInfo packageInfo = getPackageManager().getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);

            // Permissions counter
            int counter = 1;

            /*
                PackageInfo
                    Overall information about the contents of a package. This corresponds to all of
                    the information collected from AndroidManifest.xml.
            */
            /*
                String[] requestedPermissions
                    Array of all <uses-permission> tags included under <manifest>, or null if there
                    were none. This is only filled in if the flag GET_PERMISSIONS was set. This list
                    includes all permissions requested, even those that were not granted or known
                    by the system at install time.
            */
            /*
                int[] requestedPermissionsFlags
                    Array of flags of all <uses-permission> tags included under <manifest>, or null
                    if there were none. This is only filled in if the flag GET_PERMISSIONS was set.
                    Each value matches the corresponding entry in requestedPermissions, and will
                    have the flag REQUESTED_PERMISSION_GRANTED set as appropriate.
            */
            /*
                int REQUESTED_PERMISSION_GRANTED
                    Flag for requestedPermissionsFlags: the requested permission is currently
                    granted to the application.
            */

            // Loop through the package info requested permissions
            for (int i = 0; i < packageInfo.requestedPermissions.length; i++) {
                if ((packageInfo.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                    String permission =packageInfo.requestedPermissions[i];
                    // To make permission name shorter
                    //permission = permission.substring(permission.lastIndexOf(".")+1);
                    builder.append(""+counter + ". " + permission + "\n");
                    counter++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

}