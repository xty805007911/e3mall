package cn.e3main.test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3mall.common.utils.FastDFSClient;

public class FastDFSTest {

	@Test
	public void test1() throws FileNotFoundException, IOException, MyException {
		// 1、加载配置文件，配置文件中的内容就是tracker服务的地址。
		ClientGlobal.init("D:/eclipse-win64/workspace/e3-manager-web/src/main/resources/conf/fdfs_client.conf");
		// 2、创建一个TrackerClient对象。直接new一个。
		TrackerClient trackerClient = new TrackerClient();
		// 3、使用TrackerClient对象创建连接，获得一个TrackerServer对象。
		TrackerServer trackerServer = trackerClient.getConnection();
		// 4、创建一个StorageServer的引用，值为null
		StorageServer storageServer = null;
		// 5、创建一个StorageClient对象，需要两个参数TrackerServer对象、StorageServer的引用
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		// 6、使用StorageClient对象上传图片。
		//扩展名不带“.”
		String[] strings = storageClient.upload_file("E:/1542528655(1).jpg", "jpg", null);
		// 7、返回数组。包含组名和图片的路径。
		for (String string : strings) {
			System.out.println(string);
		}
		//8.访问http://192.168.25.133/group1/M00/00/00/wKgZhVvxHy-AJi8BAAIrUa7zlsc736.jpg
	}
	
	@Test
	public void test2() throws Exception {
		FastDFSClient fastDFSClient = new FastDFSClient("D:/eclipse-win64/workspace/e3-manager-web/src/main/resources/conf/fdfs_client.conf");
		String fileName = "E:/1.jpg";
		String extName = fileName.substring(fileName.lastIndexOf(".")+1);
		String file = fastDFSClient.uploadFile(fileName, extName);
		System.out.println(file);
	}

}
