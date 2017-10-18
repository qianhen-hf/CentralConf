package com.huang.centralconf.manager.util;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@SuppressWarnings("ALL")
public class ConvertUtil {



  // 日期类型的转换
  private static SimpleDateFormat simpleDateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static Date convertSTD(String date) {
    try {
      return simpleDateFormate.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String convertDTS(Date date) {
    return simpleDateFormate.format(date);
  }


  /**
   * 复制集合
   * 
   * @param <E>
   * @param source
   * @param destinationClass
   * @return
   * @throws InstantiationException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  public static <E> List<E> copyTo(List<?> source, Class<E> destinationClass) {
    if (source.size() == 0) {
        return Collections.emptyList();
    }
    List<E> res = new ArrayList<E>(source.size());
    try {
      for (Object o : source) {
        E e = destinationClass.newInstance();
        BeanUtils.copyProperties(o,e);
        res.add(e);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return res;
  }

}
