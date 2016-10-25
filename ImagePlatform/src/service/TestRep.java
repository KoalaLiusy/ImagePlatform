package service;

import interf.mapper.BizInfoMapper;
import interf.mapper.PicInfoMapper;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pojo.BizInfo;
import pojo.PicInfo;
import pojo.message.FileUploadAndUpdateBody;
import pojo.message.RequestDataBase;
import util.JsonUtil;

@Service
public class TestRep {
	@Autowired
	private BizInfoMapper bizInfoMapper;
	@Autowired
	private PicInfoMapper picInfoMapper;
	
	@Transactional()
	public String test(String jsonStr) throws SQLException{
		RequestDataBase<FileUploadAndUpdateBody> request = 
				JsonUtil.jsonToReferenceObject(jsonStr, 
						new TypeReference<RequestDataBase<FileUploadAndUpdateBody>>(){});//映射请求报文到对象
		final String picId = request.getHeader().getPicId();//图片唯一业务id
		final List<PicInfo> picInfos = new ArrayList<PicInfo>();
		StringBuilder sb = new StringBuilder("");
		if(picId == null || picId.length() != 24){
			sb.append("文件picId格式错误！");
		}
		BizInfo bizInfo = new BizInfo();
		bizInfo.setAppId(request.getHeader().getAppId());
		bizInfo.setPicId(picId);
		try {
		bizInfoMapper.insert(bizInfo);
		}catch (Exception e) {
			throw new SQLException("picId重复");
		}
			final long id = bizInfo.getId();
			final String dir = picId.substring(0, 8);
			File file = new File("D:\\" + dir);
			String[] childFile = file.list(new FilenameFilter() {
				
				public boolean accept(File parent, String name) {
					if(name.startsWith(picId + "_") && name.contains(".")){
						PicInfo info = new PicInfo();//构建每个图片对象
						info.setPicId(picId);//图片业务id
						info.setPicName(name);//图片名
						info.setPicType(name.substring(name.lastIndexOf(".")));//图片类型
						info.setPicPath(dir);
						picInfos.add(info);
						return true;
					}
					return false;
				}
			});
			if(picInfos.size() > 0){
				try {
					picInfoMapper.batchAddFiles(picInfos);//插入每张图片信息
				} catch (Exception e) {
					throw new SQLException("图片信息插入失败！");
				}
			}else{
				throw new RuntimeException("未找到此picId关联图片");
			}
		return "";
	}
}
