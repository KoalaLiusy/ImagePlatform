package test.service;

import java.util.Map;

import interf.mapper.PicIdMapper;

public class PicIdService2 {
	private PicIdMapper mapper;

	public void setMapper(PicIdMapper mapper) {
		this.mapper = mapper;
	}
	
	public String getBeginStep() throws Exception{
		Long l1 = System.currentTimeMillis();
		Map map = mapper.getPicStep();
		Long l2 = System.currentTimeMillis();
		System.out.println("耗时**************************"+(l2-l1));
		if(Integer.valueOf(map.get("t_error").toString()) != 1){
			return map.get("picId") + "";
		}
		throw new Exception("获取步长失败");
	}
}
