package com.alei.jfr;

/**
 * @author LeiLiMin
 * @date: 2022/11/24
 */
public class T01_ErrorAndException_Event {
    /**
     *     在default.jfr中开启该配置: 默认是false
     *     <event name="jdk.JavaExceptionThrow">
     *       <setting name="enabled" control="enable-exceptions">true</setting>
     *       <setting name="stackTrace">true</setting>
     *     </event>
     *     默认开启error的配置
     *     <event name="jdk.JavaErrorThrow">
     *       <setting name="enabled" control="enable-errors">true</setting>
     *       <setting name="stackTrace">true</setting>
     *     </event>
     *
     *     开启相关配置后,可在jmc查看java Exception事件
     */
    public static void main(String[] args) {
        int i = 1 / 0;

    }
}
