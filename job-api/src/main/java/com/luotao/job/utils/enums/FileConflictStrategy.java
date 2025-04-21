package com.luotao.job.utils.enums;

/**
 * @author luotao
 * @description 文件冲突处理策略
 */
public enum FileConflictStrategy {
    OVERWRITE,   // 覆盖已存在的文件
    RENAME,      // 重命名新文件
    REJECT       // 拒绝上传
} 