package com.spring.cjs2008.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.cjs2008.vo.NotifyVo;

public interface NotifyDao {

	public void nInput(@Param("vo") NotifyVo vo);

	public List<NotifyVo> getNotifyList();

	public void setDelete(@Param("idx") int idx);

	public NotifyVo getNUpdate(@Param("idx") int idx);

	public void setNUpdateOk(@Param("vo") NotifyVo vo);

	public void setpopupCheckUpdate(@Param("idx") int idx, @Param("popupSw") String popupSw);

}
