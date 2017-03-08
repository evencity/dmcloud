/*package com.apical.dmcloud.utils.datasource;


 * Copyright 1999-2101 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.spi.Configurable;
import org.hibernate.service.spi.Stoppable;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DruidConnectionProvider implements ConnectionProvider, Configurable, Stoppable {

 

    *//**
	 * 
	 *//*
	private static final long serialVersionUID = -3924949883865085909L;
	private DruidDataSource   dataSource;

    public DruidConnectionProvider(){
        dataSource = new DruidDataSource();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return dataSource.isWrapperFor(unwrapType);
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return dataSource.unwrap(unwrapType);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void closeConnection(Connection conn) throws SQLException {
        conn.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void configure(Map configurationValues) {
        try {
        	//增加独立的配置文件读取
        	Map<String,String>  userConfigurationVaules= new HashMap<String,String>();  
        	loadSqlFile("/druid.properties",userConfigurationVaules);
        	configurationValues.putAll(userConfigurationVaules);
            DruidDataSourceFactory.config(dataSource, configurationValues);
           
        } catch (SQLException e) {
            throw new IllegalArgumentException("config error", e);
        }
    }

    @Override
    public void stop() {
        dataSource.close();
    }
    
    
    *//**
     * 加载并读取配置文件 ，放入Map中
     * @param filePath
     *//*
    public static void loadSqlFile(String filePath,Map<String,String> config) {  
    	  
        if (null == filePath || "".equals(filePath.trim())) {  
            System.out.println("The file path is null,return");  
            return;  
        }  
  
        filePath = filePath.trim();  
  
        // 获取资源文件  
        InputStream is = DruidConnectionProvider.class .getResourceAsStream(filePath);  
  
        // 属性列表  
        Properties prop = new Properties();  
  
        try {  
            // 从输入流中读取属性列表  
            prop.load(is);  
        } catch (IOException e) {  
            System.out.println("load file faile:");
            e.printStackTrace();
            return;  
        } catch (Exception e) {  
            System.out.println("load file faile:");
            e.printStackTrace();
            return;  
        }  
  
        // 返回Properties中包含的key-value的Set视图  
        Set<Entry<Object, Object>> set = prop.entrySet();  
        // 返回在此Set中的元素上进行迭代的迭代器  
        Iterator<Map.Entry<Object, Object>> it = set.iterator();  
        String key = null, value = null;  
        // 循环取出key-value  
        while (it.hasNext()) {  
  
            Entry<Object, Object> entry = it.next();  
  
            key = String.valueOf(entry.getKey());  
            value = String.valueOf(entry.getValue());  
  
            key = key == null ? key : key.trim();  
            value = value == null ? value : value.trim();  
            System.err.println("key:" + key + "-------value:" + value);
            // 将key-value放入map中  
            config.put(key, value);  
        }  
    }  

}
*/