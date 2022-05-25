package com.aprilz.tiny.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * @author lsh
 * @since 2022-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("file_info")
public class FileInfo extends Model<FileInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 文件id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件大小
     */
    private Long totalSize;

    /**
     * md5标识
     */
    private String identifier;

    /**
     * 类型
     */
    private String type;

    /**
     * 文件url
     */
    private String location;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
