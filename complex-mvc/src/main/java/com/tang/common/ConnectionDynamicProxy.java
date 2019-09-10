package com.tang.common;

import com.tang.util.DBUtil;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

public class ConnectionDynamicProxy implements InvocationHandler {

    private Object obj;

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {

        Object result = null;
        Connection con = null;
        boolean needMyClose = false;
        try {
            con = (Connection) AppContext.getInstance().getObjects().get("APP_REQUEST_THREAD_CONNECTION");
            if (con == null) {
                con = DBUtil.getConnection();
                DBUtil.setAutoCommit(con, false);
                needMyClose = true;
                AppContext.getInstance().getObjects().put("APP_REQUEST_THREAD_CONNECTION", con);
            }

            result = method.invoke(obj, args);

            DBUtil.commit(con);
        } catch (Throwable throwable) {
            DBUtil.rollback(con);
            throw throwable.getCause();
        } finally {
            if (needMyClose) {
                con = (Connection) AppContext.getInstance().getObjects().get("APP_REQUEST_THREAD_CONNECTION");
                DBUtil.close(con, null, null);
                AppContext.getInstance().getObjects().remove("APP_REQUEST_THREAD_CONNECTION");
                con = null;
            }
        }
        return result;
    }

}
