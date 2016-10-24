package interf.mapper;

import java.util.List;

import pojo.BizInfo;

public interface BizInfoMapper {
	public int insert(BizInfo info);
	public void batchInsert(List<BizInfo> list);
}
