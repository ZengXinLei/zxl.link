package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Article implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 标题
     */
    private String title;


    /**
     * md主题
     */
    private String theme;

    /**
     * html内容
     */
    @TableField(exist = false)
    private String contentHtml;

    /**
     * https://gitee.com/api/v5/repos/:owner/:rep/contents/:path
     */
    private String contentUrl;
    /**
     * 一部分原内容
     */
    private String contentText;

    /**
     * 时间
     */
    private Long time;

    /**
     * 发布形式 (1.公开,2.私密,3.好友可见）
     */

    private char publishType;


    /**
     * 文章类型(1.原创,2.转载,3.翻译）
     */
    private char articleType;


    @TableField(exist = false)
    private List<Tag> tags;

    @TableField(exist = false)
    private List<Category> categories;

    @TableField(exist = false)
    private String userName;

}
