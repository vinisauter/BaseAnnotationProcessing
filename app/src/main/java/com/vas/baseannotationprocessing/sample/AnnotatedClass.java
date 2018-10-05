package com.vas.baseannotationprocessing.sample;

import com.vas.base_annotation.ClassObject;
import com.vas.base_annotation.Ignore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 04/10/2018.
 */
@ClassObject
public class AnnotatedClass extends Object {
    Object field;
    String fieldString;
    byte[] fieldBytes;
    Number fieldNumber;
    JSONArray fieldJsonArray;
    List<String> fieldList;
    Map<String, Object> fieldMap;
    JSONObject fieldJsonObject;
    Integer fieldInt;
    Double fieldDouble;
    Long fieldLong;
    Boolean fieldBoolean;
    Date fieldDate;


    @Ignore
    public Object fieldIgnore;

    public void methodVoid() {

    }

    public String methodString() {
        return "String";
    }
}

