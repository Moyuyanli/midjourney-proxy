package com.github.novicezk.midjourney.service;


import com.github.novicezk.midjourney.enums.BlendDimensions;
import com.github.novicezk.midjourney.result.Message;
import eu.maxschuster.dataurl.DataUrl;

import java.util.List;

public interface DiscordService {

	/**
	 * imagine 指令
	 * @param prompt 提示词-英文
	 * @return
	 */
	Message<Void> imagine(String prompt);

	Message<Void> upscale(String messageId, int index, String messageHash, int messageFlags);

	Message<Void> variation(String messageId, int index, String messageHash, int messageFlags);

	Message<Void> reroll(String messageId, String messageHash, int messageFlags);

	Message<Void> describe(String finalFileName);

	Message<Void> blend(List<String> finalFileNames, BlendDimensions dimensions);

	/**
	 * 上传图片，以图绘图
	 * @param fileName 任务id
	 * @param dataUrl 图片地址
	 * @return
	 */
	Message<String> upload(String fileName, DataUrl dataUrl);

	Message<String> sendImageMessage(String content, String finalFileName);

}
