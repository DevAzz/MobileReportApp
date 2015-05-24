package ru.kpfu.mobilereportapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Badgeable;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import ru.kpfu.mobilereportapp.Entity.UserEntity;

/**
 * Боковая навигационная панель
 * Created by Azz on 30.03.2015.
 */
public class NavigationDrawerComplaint {

    private static final String DEFAULT_PATH = "android.resource://ru.kpfu.mobilereportapp/drawable/answer";

    private static final String PATH_KEY = "photo_path";

    public static final String APP_PREFERENCES = "mysettings";

    /** Экземпляр класса Drawer.Result */
    private Drawer.Result _drawer;

    /** активити, где используется панель */
    private Activity _activity;

    SharedPreferences sPref;

    /**
     * Пользователь
     */
    private UserEntity user;

    /**
     * Конструктор боковой панели
     * @param aDrawer Экземпляр класса Drawer.Result
     * @param aActivity активити, где используется панель
     */
    public NavigationDrawerComplaint (Drawer.Result aDrawer, Activity aActivity) {
        _drawer = aDrawer;
        _activity = aActivity;
    }

    /**
     * Конструктор боковой панели
     * @param aDrawer Экземпляр класса Drawer.Result
     * @param aActivity активити, где используется панель
     */
    public NavigationDrawerComplaint (Drawer.Result aDrawer, Activity aActivity, UserEntity user, SharedPreferences sPref) {
        _drawer = aDrawer;
        _activity = aActivity;
        this.user = user;
        this.sPref = sPref;
    }

    /**
     * Инициализируем Navigation Drawer
     * @return поле _drawer
     */
    public Drawer.Result init() {
        com.mikepenz.materialdrawer.util.DrawerImageLoader.init(new DrawerImageLoader.IDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx) {
                return null;
            }
        });
        String userPic = "";
        if (null != sPref) {
            userPic = sPref.getString(PATH_KEY, "");
        }

        IProfile profile = new ProfileDrawerItem().withName(user.getNameUser()).withEmail(user.getFacultyUser()).withIcon(userPic);
        AccountHeader.Result headerResult = new AccountHeader()
                .withActivity(_activity)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(profile)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        // Инициализируем Navigation Drawer
        _drawer = new Drawer()
                .withActivity(_activity)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_user).withIcon(FontAwesome.Icon.faw_user),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_custom).withIcon(FontAwesome.Icon.faw_comment).withBadge("over 9000").withIdentifier(9000),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_all).withIcon(FontAwesome.Icon.faw_comment).withBadge("over 9000").withIdentifier(9000),
                        new SectionDrawerItem().withName(R.string.drawer_item_settings),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_heart),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_out).withIcon(FontAwesome.Icon.faw_outdent),
                        new DividerDrawerItem()

                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Скрываем клавиатуру при открытии Navigation Drawer
                        InputMethodManager inputMethodManager = (InputMethodManager) _activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(_activity.getCurrentFocus().getWindowToken(), 0);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }

                    @Override
                    public void onDrawerSlide(View view, float v) {

                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    // Обработка клика
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            Toast.makeText(_activity, _activity.getString(((Nameable) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
                            Log.e("LOG", String.valueOf(position));
                        }
                        if (position == 2) {
                            Intent intent = new Intent(_activity, UserComplaintActivity.class);
                            intent.putExtra(UserEntity.class.getCanonicalName(), user);
                            _activity.startActivity(intent);
                        }
                        if (position == 3) {
                            Intent intent = new Intent(_activity, ComplaintActivity.class);
                            intent.putExtra(UserEntity.class.getCanonicalName(), user);
                            _activity.startActivity(intent);
                        }
                        if (position == 5) {
                            Intent intent = new Intent(_activity, SettingActivity.class);
                            intent.putExtra(UserEntity.class.getCanonicalName(), user);
                            _activity.startActivity(intent);
                        }
                        if (position == 8) {
                        }
                        if (drawerItem instanceof Badgeable) {
                            Badgeable badgeable = (Badgeable) drawerItem;
                            if (badgeable.getBadge() != null) {
                                try {
                                    int badge = Integer.valueOf(badgeable.getBadge());
                                    if (badge > 0) {
                                        _drawer.updateBadge(String.valueOf(badge - 1), position);
                                    }
                                } catch (Exception e) {
                                    Log.d("test", "Не нажимайте на бейдж, содержащий плюс! :)");
                                }
                            }
                        }
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    // Обработка длинного клика, например, только для SecondaryDrawerItem
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof SecondaryDrawerItem) {
                            Toast.makeText(_activity, _activity.getString(((SecondaryDrawerItem) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                })
                .build();

        return _drawer;
    }

}
