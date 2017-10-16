package com.huang.centralconf.manager.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;
@Service
public class DubboImple implements DubboInteface{

	@Override
	public String test(String name) {
		name = name + new Random().nextInt(10);
		System.out.println("springboot:"+name);
		try {
			FileWriter fl = new FileWriter("a");
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("1","211");
			map.put("2","212");
			map.put("3","213");
			for(Entry<String,Object> entry :map.entrySet()){
				System.out.println(entry.getValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return name;
	}
	
	public static void main(String[] args) {
		new DubboImple().test("a");
		new LinkedBlockingDeque<Object>();
	}
}
