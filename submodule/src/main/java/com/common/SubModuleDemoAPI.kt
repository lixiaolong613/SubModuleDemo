package com.common

/**
 * SubModuleDemo API
 * 这个类提供了给外部项目调用的主要接口
 */
object SubModuleDemoAPI {

    /**
     * 获取问候语
     * @return 问候语字符串
     */
    fun getGreeting(): String {
        return Utils.hello()
    }

    /**
     * 获取模块版本信息
     * @return 版本字符串
     */
    fun getVersion(): String {
        return "1.0.0"
    }

    /**
     * 初始化模块（如果需要的话）
     */
    fun initialize() {
        // 在这里执行初始化逻辑
    }

    /**
     * 示例方法：计算两个数字的和
     * @param a 第一个数字
     * @param b 第二个数字
     * @return 计算结果
     */
    fun add(a: Int, b: Int): Int {
        return a + b
    }
}
