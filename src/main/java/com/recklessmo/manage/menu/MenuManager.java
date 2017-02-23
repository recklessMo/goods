package com.recklessmo.manage.menu;

import com.recklessmo.model.menu.MenuModel;
import com.recklessmo.model.role.Permissions;
import com.recklessmo.model.security.DefaultUserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

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

    public MenuManager(){

    }

    /**
     *
     * 根据权限获取对应的菜单列表
     *
     * @param defaultUserDetails
     * @return
     */
    public List<MenuModel> getMenus(DefaultUserDetails defaultUserDetails){
        List<GrantedAuthority> authorities  = (List<GrantedAuthority>)defaultUserDetails.getAuthorities();
        Set<Long> idSet  = new HashSet<>();
        for(GrantedAuthority grantedAuthority : authorities){
            Long t = null;
            try {
                t = Long.parseLong(grantedAuthority.getAuthority());
            }catch(Exception e){

            }
            if(t != null){
                idSet.add(Long.parseLong(grantedAuthority.getAuthority()));
            }
        }

        List<MenuModel> menuModels = new LinkedList<>();
        Menu[] menus = Menu.menuList;
        Map<String, MenuModel> menuMap = new HashMap();
        for(Menu menu : menus){
            if(menu.getFather() == null){
                MenuModel menuModel = new MenuModel();
                menuModel.setId(menu.getId());
                menuModel.setText(menu.getChild());
                menuModel.setIcon(menu.getIcon());
                menuModel.setSref(menu.getHref());
                menuModel.setSubmenu(null);
                if(menuMap.get(menu.getChild()) == null) {
                    menuMap.put(menu.getChild(), menuModel);
                    menuModels.add(menuModel);
                }
            }else if(menu.getFather() != null && idSet.contains(menu.getId())){
                MenuModel menuModel = new MenuModel();
                menuModel.setId(menu.getId());
                menuModel.setText(menu.getChild());
                menuModel.setIcon(menu.getIcon());
                menuModel.setSref(menu.getHref());
                menuModel.setSubmenu(null);
                MenuModel fatherModel = menuMap.get(menu.getFather());
                List<MenuModel> subMenus = fatherModel.getSubmenu();
                if(subMenus == null){
                    subMenus = new LinkedList<>();
                    fatherModel.setSubmenu(subMenus);
                }
                subMenus.add(menuModel);
            }
        }

        Iterator<MenuModel> it = menuModels.iterator();
        while(it.hasNext()){
            MenuModel menuModel = it.next();
            if(menuModel.getSubmenu() == null || menuModel.getSubmenu().size() < 1){
                it.remove();
            }
        }
        return menuModels;
    }

    public List<Permissions> getPermissions(){
        List<Permissions> data = new LinkedList<>();
        for(Menu menu: Menu.menuList){
            if(menu.getFather() != null && !menu.getFather().equals("超管站")){
                data.add(new Permissions(menu.getId(), menu.getFather() + "->" + menu.getChild()));
            }
        }
        return data;
    }

}
