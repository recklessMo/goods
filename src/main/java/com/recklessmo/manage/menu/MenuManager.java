package com.recklessmo.manage.menu;

import com.recklessmo.model.menu.MenuModel;
import com.recklessmo.model.security.DefaultUserDetails;
import com.sun.javafx.css.SubCssMetaData;

import java.util.*;

import java.lang.Iterable;

/**
 *
 * 根据用户的权限返回menu菜单
 *
 * Created by hpf on 4/13/16.
 */
public class MenuManager {

    private static MenuManager instance = new MenuManager();

    public static MenuManager getInstance(){
        return instance;
    }

    public List<MenuModel> getMenus(DefaultUserDetails defaultUserDetails, boolean test){
        Menu[] menus = test? Menu.menuListTest :Menu.menuList;
        List<MenuModel> menuModels = new LinkedList<MenuModel>();
        Map<String, MenuModel> menuMap = new HashMap();
        for(Menu menu : menus){
            MenuModel menuModel = new MenuModel();
            menuModel.setText(menu.getChild());
            menuModel.setIcon(menu.getIcon());
            menuModel.setSref(menu.getHref());
            menuModel.setWeight(menu.getWeight());
            menuModel.setSubmenu(null);
            if(menu.getFather() == null){
                menuMap.put(menu.getChild(), menuModel);
                if(menu.getRoles() != null && hasAuthority(defaultUserDetails, menu.getRoles())){
                    menuModels.add(menuModel);
                }
            }else{
                MenuModel fatherModel = menuMap.get(menu.getFather());
                if(hasAuthority(defaultUserDetails, menu.getRoles())){
                    List<MenuModel> subMenus = fatherModel.getSubmenu();
                    if(subMenus == null){
                        subMenus = new LinkedList<MenuModel>();
                        fatherModel.setSubmenu(subMenus);
                    }
                    subMenus.add(menuModel);
                }
            }
        }

        Iterator<Map.Entry<String, MenuModel>> it = menuMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, MenuModel> temp = it.next();
            if(temp.getValue().getSubmenu() != null && temp.getValue().getSubmenu().size() > 0){
                menuModels.add(temp.getValue());
            }
        }

        Collections.sort(menuModels, new Comparator<MenuModel>() {
            @Override
            public int compare(MenuModel o1, MenuModel o2) {
                return o1.getWeight() - o2.getWeight();
            }
        });

        return menuModels;
    }

    private boolean hasAuthority(DefaultUserDetails defaultUserDetails, String roleStr){
        if(roleStr == null){
            return true;
        }
        String[] roles = roleStr.trim().split(",");
        List<String> roleList = defaultUserDetails.getRoles();
        for(String role : roleList){
            for(String needRole : roles){
                if(role.equals(needRole)){
                    return true;
                }
            }
        }
        return false;
    }

}
