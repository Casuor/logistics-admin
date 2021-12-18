package com.aki.springbootlogisticsadmin.common;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SysMenuDto implements Serializable {
    /*
    *
     name: "menuInfo",
     path: "/main/menuInfo",
     component: "system/MenuInfo",
     icon: "el-icon-menu",
     title: "菜单管理",
    * */
    private Long id;
    private String name;
    private String path;
    private String component;
    private String icon;
    private String title;
    private List<SysMenuDto> children = new ArrayList<>();
}
