package huang;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	private String name;
	private int id;
	private int pid;
	private List<Menu> sunMenu = new ArrayList<Menu>();

	public Menu(String name, int id, int pid) {
		this.name = name;
		this.id = id;
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public List<Menu> getSunMenu() {
		return sunMenu;
	}

	public void setSunMenu(List<Menu> sunMenu) {
		this.sunMenu = sunMenu;
	}

	

}
