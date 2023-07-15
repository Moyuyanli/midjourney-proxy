package com.github.novicezk.midjourney.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.github.novicezk.midjourney.ProxyProperties;
import com.github.novicezk.midjourney.support.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class MiraiPluginCallback {

    @Resource
    private ProxyProperties env;

    /**
     * 回调mirai插件
     * @param task 任务信息
     */
    public void callback(Task task) {
        HttpUtil.post(env.getDiscord().getMiraiPluginCallbackAddress(), JSONUtil.toJsonStr(task));
        log.info("taskId = "+task.getId()+" callback success !");
    }

}
