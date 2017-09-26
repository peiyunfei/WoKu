package com.pyf.woku.manager;

import com.pyf.woku.bean.User;

/**
 * 管理用户信息
 * <br/>
 * 作者：裴云飞
 * <br/>
 * 时间：2017/9/26
 */
public class UserManager {

    private User mUser;

    public static final UserManager getInstance() {
        return UserManagerHolder.INSTANCE;
    }

    private static final class UserManagerHolder {
        private static final UserManager INSTANCE = new UserManager();
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public boolean hasLogin() {
        return mUser != null;
    }

    public void removeUser() {
        mUser = null;
    }
}
