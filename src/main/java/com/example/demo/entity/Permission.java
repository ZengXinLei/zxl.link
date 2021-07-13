package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author zxl
 * @since 2021-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Permission implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 描述
     */
    private String label;

    /**
     * 类型(0:页面路由，1:rest接口)
     */
    private Integer type;

    /**
     * 值
     */
    private String value;

    /**
     * 图标
     */
    private String icon;

    /**
     * 父级id
     */
    @TableField("parentId")
    private Integer parentId;

    /**
     * 页面组件id
     */
    private Integer pageComponentId;

    @TableField(exist = false)
    private List<Permission> children;


    @TableField(exist = false)
    private Pagecomponent pagecomponent;

}
