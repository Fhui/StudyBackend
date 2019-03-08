package com.harry.mapper;

import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author harry
 * @create 2019-03-08 14:30
 **/
public class MapperGenerate {

    private static String table = "orders";


    @Test
    public void generateModel() {
        List<Map<String,String>> list = getColumns(table);

        String t = getCamelName(table);
        System.out.println("model name:  " + t.substring(0,1).toUpperCase() + t.substring(1) + "\n");

        System.out.println("//DB field");
        for(Map<String,String> m: list) {
            String jName = getCamelName(m.get("name"));
            System.out.println("private " + m.get("type") + " " + jName + ";");
        }

        System.out.println("\n//Ref field");
        System.out.println("\n//Non Dd field");
    }

    @SuppressWarnings("Duplicates")
    @Test
    public void generateMapper() {
        List<Map<String,String>> list = getColumns(table);

        System.out.println();
        System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        System.out.println("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");

        System.out.println();

        String s = getSystemName(table);
        String t = getCamelName(table);
        System.out.println("<mapper namespace=\"com.harry.dao." + s + "." + t.substring(0,1).toUpperCase() + t.substring(1) + "Mapper\">");

        StringBuffer sb = new StringBuffer("\t<resultMap id=\"" + getCamelName(table) + "Map\" type=\"" + getCamelName(table) + "\">\n");
        boolean first = true;
        for(Map<String,String> m: list){
            if(first){
                sb.append("\t\t<id property=\""+getCamelName(m.get("name"))+"\" column=\""+m.get("name")+"\"/>\n");
            }else{
                sb.append("\t\t<result property=\""+getCamelName(m.get("name"))+"\" column=\""+m.get("name")+"\"/>\n");
            }
            first = false;
        }
        sb.append("\t</resultMap>\n");
        sb.append("\n");


        sb.append("\t<sql id=\"allColumns\">\n");
        sb.append("\t\t");

        first = true;
        for(Map<String,String> m: list){
            if (!first) sb.append(",");
            sb.append( m.get("name"));
            first = false;
        }
        sb.append("\n");
        sb.append("\t</sql>\n");
        sb.append("\t<sql id=\"selectAllColumns\">\n");
        sb.append("\t\tselect <include refid=\"allColumns\"/> \n\t\tfrom " + table +" \n");
        sb.append("\t</sql>\n");

        sb.append("\n\t<select id=\"select\" resultMap=\"" + getCamelName(table) + "Map\" parameterType=\"java.lang."+list.get(0).get("type")+"\">");
        sb.append("\n\t\t<include refid=\"selectAllColumns\"/> \n\t\twhere ");
        sb.append(list.get(0).get("name") +" = #{"+getCamelName(list.get(0).get("name"))+"}");
        sb.append("\n\t</select>\n\n");

        sb.append("\t<insert id=\"insert\" parameterType=\"" + getCamelName(table) +"\">\n");
        sb.append("\t\tinsert into " + table + "(\n\t\t\t");
        first = true;
        for(Map<String,String> m: list){
            if (!first) sb.append(",");
            sb.append( m.get("name"));
            first = false;
        }
        sb.append("\n");
        sb.append("\t\t) values(\n");
        sb.append("\t\t\t");
        first = true;
        for(Map<String,String> m: list){
            if (!first) sb.append(",");
            sb.append("#{" + getCamelName(m.get("name")) + "}");
            first = false;
        }
        sb.append("\n\t\t)");
        sb.append("\n\t</insert>\n");


        sb.append("\n\t<update id=\"update\" parameterType=\""+ getCamelName(table) +"\">\n");
        sb.append("\t\tupdate "+ table +" set\n");

        first = true;
        for(Map<String,String> m: list.subList(1, list.size())){
            if (!first) sb.append(",\n");
            sb.append("\t\t\t" + m.get("name") + " = #{" + getCamelName(m.get("name")) + "}");
            first = false;
        }
        sb.append("\n\t\twhere ");
        sb.append(list.get(0).get("name") +" = #{"+getCamelName(list.get(0).get("name"))+"}\n");

        sb.append("\t</update>\n");
        sb.append("\n");

        sb.append("\t<delete id=\"delete\" parameterType=\"java.lang." + list.get(0).get("type") + "\">\n");
        sb.append("\tdelete from "+ table +" where ");
        sb.append(list.get(0).get("name") +" = #{"+getCamelName(list.get(0).get("name"))+"}\n");
        sb.append("\t</delete>");
        sb.append("\n");

        sb.append("\n\t<select id=\"selectList\" resultMap=\""+ getCamelName(table) +"Map\" parameterType=\"pagingParam\">");
        sb.append("\n\t\t<include refid=\"selectAllColumns\"/>\n");
        sb.append("\t\twhere ${where} order by ${sort} ");
        sb.append("\n\t</select>");

        System.out.println(sb);

        System.out.println("\n\n</mapper>");
    }

    private List<Map<String,String>> getColumns(String table) {
        String url = "jdbc:mysql://localhost:3306/information_schema?allowMultiQueries=true&useSSL=true&autoReconnect=true&useUnicode=true&characterEncoding=utf-8" ;
        String username = "root";
        String password = "123456";

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url , username , password );

            stmt = con.createStatement();

            List<Map<String,String>> list = new ArrayList<>();

            rs = stmt.executeQuery("select COLUMN_NAME,DATA_TYPE from COLUMNS where TABLE_NAME = '" + table + "' and TABLE_SCHEMA='harry' order by ORDINAL_POSITION") ;
            while(rs.next()){
                String name = rs.getString("COLUMN_NAME") ;
                String type = rs.getString("DATA_TYPE") ;

                String jType = "";
                if (type.equals("bigint")) {
                    jType = "Long";
                } else if (type.equals("varchar")) {
                    jType = "String";
                } else if (type.equals("datetime")) {
                    jType = "Date";
                } else if (type.equals("int")) {
                    jType = "Integer";
                } else if (type.equals("decimal")) {
                    jType = "BigDecimal";
                }

                Map<String,String> m = new HashMap<String, String>();
                m.put("name", name);
                m.put("type", jType);
                list.add(m);
            }

            return list;
        }catch(Exception se){
            se.printStackTrace();
        }
        finally {
            if(rs != null){
                try{
                    rs.close() ;
                }catch(SQLException e){
                    e.printStackTrace() ;
                }
            }
            if(stmt != null){
                try{
                    stmt.close() ;
                }catch(SQLException e){
                    e.printStackTrace() ;
                }
            }
            if(con != null){
                try{
                    con.close() ;
                }catch(SQLException e){
                    e.printStackTrace() ;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("Duplicates")
    private String getCamelName(String param) {
        String s = param.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '_') {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    private String getSystemName(String table) {
        String t = table.substring(0, 2);
        switch (t) {
            case "ba":
                return "basic";
            case "sy":
                return "system";
            case "pr":
                return "purchase";
            case "st":
                return "inventory";
            case "ap":
                return "api";
            default:

        }

        return "";
    }

    @Test
    public void test_number() {

        double a = 100000000000016.0208d;
        double b = 0.01;

        System.out.println(a);
        System.out.println(1.0-0.42);
        System.out.println(4.015*100);
    }
}
