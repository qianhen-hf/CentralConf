package huang;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {
	public static void main(String[] args) {
		List<Menu> list = new ArrayList<Menu>();
		Menu menu1= new Menu("zhang1",1,0);
		Menu menu2= new Menu("zhang2",2,0);
		Menu menu3= new Menu("zhang3",3,0);
		Menu menu4= new Menu("zhang4",4,1);
		Menu menu5= new Menu("zhang5",5,4);
		Menu menu6= new Menu("zhang6",6,5);
		Menu menu7= new Menu("zhang7",7,1);
		Menu menu8= new Menu("zhang8",8,0);
		list.add(menu1);
		list.add(menu2);
		list.add(menu3);
		list.add(menu4);
		list.add(menu5);
		list.add(menu6);
		list.add(menu7);
		list.add(menu8);
		List<Menu> findMenuList = findMenuList(list);
		ObjectMapper object = new ObjectMapper();
		String result = null;
		try {
			result = object.writeValueAsString(findMenuList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}
	
	public static List<Menu> findMenuList(List<Menu> permList) {
		List<Menu> firstLevelMenu = new ArrayList<Menu>();
		List<Menu> sunPerm = new ArrayList<Menu>();
		for (Menu menu : permList) {
			if (menu.getPid() == 0) {
				firstLevelMenu.add(menu);
			} else {
				sunPerm.add(menu);
			}
		}
		return findSunMenu(firstLevelMenu, sunPerm);
	}

	public static List<Menu> findSunMenu(List<Menu> firstLevelMenuList, List<Menu> sunPermList) {
		for (Menu firstLevelMenu : firstLevelMenuList) {
			List<Menu> permList = new ArrayList<Menu>();
			for (int i = 0; i < sunPermList.size(); i++) {
				Menu sunPerm = sunPermList.get(i);
				if (sunPerm.getPid()== firstLevelMenu.getId()) {
					permList.add(sunPerm);
					sunPermList.remove(i);
					i--;
				}
			}
			firstLevelMenu.setSunMenu(permList);
			findSunMenu(permList, sunPermList);
		}
		return firstLevelMenuList;
	}
}
