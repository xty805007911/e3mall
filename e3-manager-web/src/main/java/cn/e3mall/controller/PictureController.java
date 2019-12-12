package cn.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.common.json.JSON;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;

/**
 * 图片上传表现层
* Title: PictureController <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月18日
 */
@Controller
public class PictureController {
	
	//fastDFS服务器地址
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	/**
	 * 图片上传
	 * <p>Title: fileUpLoad</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月18日
	 * @param uploadFile
	 * @return
	 */
	@RequestMapping(value="/pic/upload",produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	public String fileUpLoad(MultipartFile uploadFile) {
		try {
			//1、取文件的扩展名
			//文件名
			String filename = uploadFile.getOriginalFilename();
			//扩展名
			String extName = filename.substring(filename.lastIndexOf(".")+1);
			
			//2、创建一个FastDFS的客户端
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/fdfs_client.conf");
			
			//3、执行上传处理
			String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			
			//4、拼接返回的url和ip地址，拼装成完整的url
			String url = "http://" + IMAGE_SERVER_URL + "/" + path;
			System.out.println(url);
			
			//5、返回map
			Map<String,Object> result = new HashMap<>();
			result.put("error", 0);
			result.put("url", url);
			System.out.println("succ");
			String ret = JsonUtils.objectToJson(result);
			return ret;

		} catch (Exception e) {
			e.printStackTrace();
			//5、返回map
			Map<String,Object>  result = new HashMap<>();
			result.put("error", 1);
			result.put("message", "ERROR:图片上传失败");
			System.out.println("error");
			String ret = JsonUtils.objectToJson(result);
			return ret;

		}
		
	}
	
}
