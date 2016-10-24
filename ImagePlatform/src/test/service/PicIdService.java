package test.service;

import interf.mapper.BizInfoMapper;
import interf.mapper.PicIdMapper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pojo.BizInfo;
import pojo.enums.ExceptionType;
import pojo.message.AskPicIdBody;
import pojo.message.RequestDataBase;

import exception.PicBaseException;

@Service
public class PicIdService {
	private long step;
	private long seqId;
	@Autowired
	private PicIdMapper picIdMapper;
	@Autowired
	private BizInfoMapper bizInfoMapper;
	private Logger log = Logger.getLogger(PicIdService.class);

	/**
	 * 获取唯一picId 需传入业务ID，业务系统号，所需id个数
	 * 
	 * @return
	 * @throws Exception
	 */
	public synchronized String getNewPicId(RequestDataBase<AskPicIdBody> req)
			throws PicBaseException {
		int num = Integer.valueOf(req.getBody().getNum());// 请求picId个数
		if (seqId + num >= (step + 10000)) {
			init();
		}
		seqId++;
		String header = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date());// 到毫秒值
		String seq = String.valueOf(seqId);
		String picId = header + "0000000".substring(seq.length()) + seq;
		log.info("起始流水号为" + picId);
		if (num > 1) {
			for (int i = 0; i < num - 1; i++) {
				seqId++;
			}
		}
		storeBizInfo(req.getHeader().getBizId(), req.getHeader().getAppId(),
				picId, num);
		return picId;
	}

	/**
	 * 存储BizInfo表(暂不删除)
	 */
	private void storeBizInfo(String bizId, String appId, String picId, int num) {
		List<BizInfo> list = new ArrayList<BizInfo>();
		for (int i = 0; i < num; i++) {
			BizInfo info = new BizInfo();
			info.setAppId(appId);
			info.setBizId(bizId);
			BigDecimal b1 = new BigDecimal(picId);
			info.setPicId(b1.add(new BigDecimal(num-1)).toString());
			list.add(info);
		}
		bizInfoMapper.batchInsert(list);
	}

	/**
	 * 初始化
	 */
	@PostConstruct
	private void init() throws PicBaseException {
		Map map = picIdMapper.getPicStep();
		if (Integer.valueOf(map.get("t_error").toString()) != 1) {
			step = (Long) map.get("picId");
			seqId = step;
			log.info("获取步长为" + step);
		} else {
			throw new PicBaseException(ExceptionType.STORE0004);
		}
	}
}
