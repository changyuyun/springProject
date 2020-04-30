package com.ityun.modules.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public abstract class PageSqlProvider {

    protected abstract SQL preSql();

    public String findAll(@Param("start") int start, @Param("limit") int limit, @Param("channel") int channel) {
        return "1";
    }

    protected String findByCase(int start, int limit, int channel, SQL sql) {
        if (channel != 0) {
            return "1";
        }
        return "2";
    }

    protected String countByCase(SQL sql) {
        return sql.SELECT("count(*)").toString();
    }
}
