package interf.mapper;

import java.util.List;

import pojo.PicInfo;

public interface PicInfoMapper {
	public void addFile(PicInfo info);
	public void batchAddFiles(List<PicInfo> infos);
	
	public List<PicInfo> viewByCondition(PicInfo info);
	
	public int updateByPicId(PicInfo info);
}
