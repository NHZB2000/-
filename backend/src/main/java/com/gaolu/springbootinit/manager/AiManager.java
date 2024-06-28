package com.gaolu.springbootinit.manager;

import com.gaolu.springbootinit.common.ErrorCode;
import com.gaolu.springbootinit.exception.BusinessException;
import com.gaolu.yucongming.dev.client.YuCongMingClient;
import com.gaolu.yucongming.dev.common.BaseResponse;
import com.gaolu.yucongming.dev.model.DevChatRequest;
import com.gaolu.yucongming.dev.model.DevChatResponse;
import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.exception.SparkException;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.SparkSyncChatResponse;
import io.github.briqt.spark4j.model.request.SparkRequest;
import io.github.briqt.spark4j.model.response.SparkTextUsage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于对接 AI 平台
 */
@Service
@Slf4j
public class AiManager {

    @Resource
    private SparkClient sparkClient;


    public String doChat(String message) {
        // 设置认证信息
        // 消息列表，可以在此列表添加历史对话记录
        List<SparkMessage> messages = new ArrayList<>();
        messages.add(SparkMessage.systemContent("你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：" +
                "分析需求：" +
                "(数据分析的需求或者目标}" +
                "原始数据：" +
                "{csv格式的原始数据，用，作为分隔符）" +
                "请根据这两部分内容，按照以下指定格式生成内容，一定要注意使用两组【【【【【把option配置对象js代码包含起来(此外千万不要输出任何多余的开头、结尾、注释)" +
                "【【【【【" +
                "{前端Echarts V5的option配置对象js代码，合理地将数据进行可视化，要求所有的键和字符串值都被双引号包围，不要生成任何多余的内容，比如注释\n以及" +
                "示例代码：{\n" +
                "  \"title\": {\"text\": \"用户增长数据分析\"},\n" +
                "  \"tooltip\": {\"trigger\": \"axis\"},\n" +
                "  \"legend\": {\"data\": [\"用户数\"]},\n" +
                "  \"toolbox\": {\"feature\": {\"saveAsImage\": {}}},\n" +
                "  \"grid\": {\"left\": \"3%\", \"right\": \"4%\", \"bottom\": \"3%\", \"containLabel\": true},\n" +
                "  \"xAxis\": {\n" +
                "    \"type\": \"category\",\n" +
                "    \"boundaryGap\": false,\n" +
                "    \"data\": [\"1号\", \"2号\", \"3号\", \"4号\", \"5号\", \"6号\", \"7号\"]\n" +
                "  },\n" +
                "  \"yAxis\": {\"type\": \"value\", \"axisLabel\": {\"formatter\": \"{value} 人\"}},\n" +
                "  \"series\": [\n" +
                "    {\n" +
                "      \"name\": \"用户数\",\n" +
                "      \"type\": \"line\",\n" +
                "      \"data\": [10, 20, 30, 90, 0, 10, 20],\n" +
                "      \"markPoint\": {\"data\": [{\"type\": \"max\", \"name\": \"最大值\"}, {\"type\": \"min\", \"name\": \"最小值\"}]}\n" +
                "    }\n" +
                "  ]\n" +
                "}" +
                "【【【【【" +
                "明确的数据分析结论、越详细越好，不要生成多余的注释]"));
        messages.add(SparkMessage.userContent(message));
// 构造请求
        SparkRequest sparkRequest = SparkRequest.builder()
// 消息列表
                .messages(messages)
// 模型回答的tokens的最大长度,非必传，默认为2048。
// V1.5取值为[1,4096]
// V2.0取值为[1,8192]
// V3.0取值为[1,8192]
                .maxTokens(2048)
// 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
                .temperature(0.4)
// 指定请求版本，默认使用最新3.5版本
                .apiVersion(SparkApiVersion.V3_5)
                .build();
        String result ="";
        String useToken = " ";
        try {
            // 同步调用
            SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
            SparkTextUsage textUsage = chatResponse.getTextUsage();
            result = chatResponse.getContent();
            useToken = "提问tokens：" + textUsage.getPromptTokens()
                    + "，回答tokens：" + textUsage.getCompletionTokens()
                    + "，总消耗tokens：" + textUsage.getTotalTokens();
            log.info(useToken);
        } catch (SparkException e) {
            log.error("Ai调用发生异常了：" + e.getMessage());
        }
        return result;
    }
}
