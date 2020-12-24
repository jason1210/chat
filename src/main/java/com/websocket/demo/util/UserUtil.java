package com.websocket.demo.util;

import com.websocket.demo.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jason
 * @Date: 2020-12-23
 */
public class UserUtil {

    private static List<User> list = new ArrayList();

    static {
        list.add(User.builder().userId(1).username("jason").build());
        list.add(User.builder().userId(2).username("thea").build());
        list.add(User.builder().userId(3).username("cong").build());
        list.add(User.builder().userId(4).username("shunxiang").build());
    }

    public static List<User> getUser() {
        return list;
    }

    public static User findById(Integer id) {
        return list.stream().filter(e -> e.getUserId() == id.intValue()).findFirst().orElse(null);
    }

    public static String getUsername(Integer id) {
        User user = findById(id);
        if (user == null) {
            return "null";
        }
        return user.getUsername();
    }
}
