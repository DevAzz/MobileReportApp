package ru.kpfu.mobilereportapp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Badgeable;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

/**
 * Боковая навигационная панель
 * Created by Azz on 30.03.2015.
 */
public class NavigationDrawerComplaint {

    /** Экземпляр класса Drawer.Result */
    private Drawer.Result _drawer;

    /** активити, где используется панель */
    private Activity _activity;

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
     * Инициализируем Navigation Drawer
     * @return поле _drawer
     */
    public Drawer.Result init() {
        // Инициализируем Navigation Drawer
        _drawer = new Drawer()
                .withActivity(_activity)
                        //.withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
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
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    // Обработка клика
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            Toast.makeText(_activity, _activity.getString(((Nameable) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
                            Log.e("LOG", String.valueOf(position));
                        }
                        if (position == 3) {
                            Intent intent = new Intent(_activity, UserComplaintActivity.class);
                            _activity.startActivity(intent);
                        }
                        if (position == 4) {
                            Intent intent = new Intent(_activity, ComplaintActivity.class);
                            _activity.startActivity(intent);
                        }
                        if (position == 8) {
                            //FIXME Сделать выход
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
