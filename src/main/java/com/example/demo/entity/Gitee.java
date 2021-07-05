package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author zxl
 * @since 2021-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Gitee implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户授权码
     */
    private String accessToken;

    /**
     * 仓库所属空间地址(企业、组织或个人的地址path)
     */
    private String owner;

    /**
     * 仓库路径(path)
     */
    private String repo;

    /**
     * 分支名称。默认为仓库对默认分支
     */
    private String branch;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 是否启用
     */
    private Integer enable;

    @TableField(exist = false)
    private String path;
    @TableField(exist = false)
    private String content;
    @TableField(exist = false)
    private String message;
    @TableField(exist = false)
    private String committerName;
    @TableField(exist = false)
    private String committerEmail;


    @TableField(exist = false)
    private String authorName;

    @TableField(exist = false)
    private String authorEmail;
}
