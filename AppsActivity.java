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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AppsActivity extends AppCompatActivity {

    private List<AppList> installedApps;
    private AppAdapter installedAppAdapter;
    ListView userInstalledApps;
    EditText etSearch;
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
                                if (which==0){
//                                    Intent intent = new Intent(AppsActivity.this,NonMalware.class);
//                                        startActivity(intent);
                                    PopUpClass popUpClass = new PopUpClass();

                                    popUpClass.showPopupWindow(view);

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

        //Total Number of Installed-Apps(i.e. List Size)
//        String  abc = userInstalledApps.getCount()+"";
//        TextView countApps = (TextView)findViewById(R.id.countApps);
//        countApps.setText("Total Installed Apps: "+abc);
//        Toast.makeText(this, abc+" Apps", Toast.LENGTH_SHORT).show();

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

}